package com.oskarwiedeweg.cloudwork.auth.sso.provider;

import com.oskarwiedeweg.cloudwork.auth.sso.SSOConnection;
import com.oskarwiedeweg.cloudwork.auth.sso.SSODao;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserService;
import dev.samstevens.totp.secret.SecretGenerator;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.Optional;

public abstract class AbstractSSOProvider {

    private final SSODao ssoDao;
    private final UserService userService;
    private final SecretGenerator secretGenerator;

    protected AbstractSSOProvider(SSODao ssoDao, UserService userService, SecretGenerator secretGenerator) {
        this.ssoDao = ssoDao;
        this.userService = userService;
        this.secretGenerator = secretGenerator;
    }

    public abstract Map<String, Object> verifyAndGet(String token);

    public abstract String getProviderName();

    @SneakyThrows
    public final User ssoLogin(String ssoToken) {
        Map<String, Object> token = verifyAndGet(ssoToken);
        String id = (String) token.get("id");
        String email = (String) token.get("email");

        Optional<SSOConnection> ssoConnection = ssoDao.findByAccountId(id, getProviderName());

        if (ssoConnection.isPresent()) {
            return ssoConnection.get().getUser();
        }

        Long userId = userService.createUser(
                getProviderName() + "_" + System.currentTimeMillis() + "_" + id,
                email,
                secretGenerator.generate()
        );
        ssoDao.createSSOConnection(userId, id, getProviderName());

        return userService.getUserDao().findUserById(userId).orElseThrow();
    }

}
