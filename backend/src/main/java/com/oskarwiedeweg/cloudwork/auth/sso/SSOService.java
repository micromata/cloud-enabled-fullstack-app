package com.oskarwiedeweg.cloudwork.auth.sso;

import com.oskarwiedeweg.cloudwork.auth.dto.SSOConnectionDto;
import com.oskarwiedeweg.cloudwork.auth.dto.SSOLogin;
import com.oskarwiedeweg.cloudwork.auth.sso.provider.SSOProvider;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserService;
import dev.samstevens.totp.secret.SecretGenerator;
import lombok.Data;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Service
public class SSOService {

    private final Map<String, SSOProvider> ssoProviders;
    private final ModelMapper modelMapper;
    private final SecretGenerator secretGenerator;
    private final UserService userService;
    private final SSODao ssoDao;

    public SSOService(@Qualifier("ssoProviders") Map<String, SSOProvider> ssoProviders, ModelMapper modelMapper, SecretGenerator secretGenerator, UserService userService, SSODao ssoDao) {
        this.ssoProviders = ssoProviders;
        this.modelMapper = modelMapper;
        this.secretGenerator = secretGenerator;
        this.userService = userService;
        this.ssoDao = ssoDao;
    }

    public User ssoLogin(String token, String provider) {
        SSOProvider ssoProvider = getSsoProvider(provider);

        return ssoLogin(ssoProvider, token);
    }

    public void addSSOProvider(Long userId, String provider, SSOLogin ssoLogin) {
        SSOProvider ssoProvider = getSsoProvider(provider);

        Map<String, Object> providerDetails = ssoProvider.verifyAndGet(ssoLogin.getIdToken());
        String accountId = (String) providerDetails.get("id");
        String accountEmail = (String) providerDetails.get("email");

        if (ssoDao.findByAccountId(accountId, provider).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This account is already connected!");
        }

        ssoDao.createSSOConnection(userId, accountId, provider, accountEmail);
    }

    public List<SSOConnectionDto> getAllFromUser(Long userId) {
        return ssoDao.findAllByUserId(userId).stream()
                .map(c -> modelMapper.map(c, SSOConnectionDto.class))
                .toList();
    }

    private SSOProvider getSsoProvider(String provider) {
        SSOProvider ssoProvider = ssoProviders.get(provider);

        if (ssoProvider == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found!");
        }
        return ssoProvider;
    }

    @SneakyThrows
    private  User ssoLogin(SSOProvider ssoProvider, String ssoToken) {
        Map<String, Object> token = ssoProvider.verifyAndGet(ssoToken);
        String id = (String) token.get("id");
        String email = (String) token.get("email");

        Optional<SSOConnection> ssoConnection = ssoDao.findByAccountId(id, ssoProvider.getProviderName());

        if (ssoConnection.isPresent()) {
            return ssoConnection.get().getUser();
        }

        Long userId = userService.createUser(
                ssoProvider.getProviderName() + "_" + System.currentTimeMillis() + "_" + id,
                email,
                secretGenerator.generate()
        );
        ssoDao.createSSOConnection(userId, id, ssoProvider.getProviderName(), email);

        return userService.getUserDao().findUserById(userId).orElseThrow();
    }

    public void removeSSOProvider(Long userId, Long providerId) {
        if (!ssoDao.removeById(providerId, userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SSO Provider not found!");
        }
    }
}
