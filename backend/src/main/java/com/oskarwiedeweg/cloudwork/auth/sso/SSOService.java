package com.oskarwiedeweg.cloudwork.auth.sso;

import com.oskarwiedeweg.cloudwork.auth.dto.SSOLogin;
import com.oskarwiedeweg.cloudwork.auth.sso.provider.AbstractSSOProvider;
import com.oskarwiedeweg.cloudwork.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Data
@Service
public class SSOService {

    private final Map<String, AbstractSSOProvider> ssoProviders;
    private final SSODao ssoDao;

    public SSOService(@Qualifier("ssoProviders") Map<String, AbstractSSOProvider> ssoProviders, SSODao ssoDao) {
        this.ssoProviders = ssoProviders;
        this.ssoDao = ssoDao;
    }

    public User ssoLogin(String token, String provider) {
        AbstractSSOProvider ssoProvider = getSsoProvider(provider);

        return ssoProvider.ssoLogin(token);
    }

    public void addSSOProvider(Long userId, String provider, SSOLogin ssoLogin) {
        AbstractSSOProvider ssoProvider = getSsoProvider(provider);

        Map<String, Object> providerDetails = ssoProvider.verifyAndGet(ssoLogin.getIdToken());
        String accountId = (String) providerDetails.get("id");

        if (ssoDao.findByAccountId(accountId, provider).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This account is already connected!");
        }

        ssoDao.createSSOConnection(userId, accountId, provider);
    }

    private AbstractSSOProvider getSsoProvider(String provider) {
        AbstractSSOProvider ssoProvider = ssoProviders.get(provider);

        if (ssoProvider == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found!");
        }
        return ssoProvider;
    }
}
