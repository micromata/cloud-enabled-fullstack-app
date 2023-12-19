package com.oskarwiedeweg.cloudwork.auth.sso;

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

    public SSOService(@Qualifier("ssoProviders") Map<String, AbstractSSOProvider> ssoProviders) {
        this.ssoProviders = ssoProviders;
    }

    public User ssoLogin(String token, String provider) {
        AbstractSSOProvider ssoProvider = ssoProviders.get(provider);

        if (ssoProvider == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found!");
        }

        return ssoProvider.ssoLogin(token);
    }

}
