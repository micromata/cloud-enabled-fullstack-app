package com.oskarwiedeweg.cloudwork.auth.sso.provider;

import java.util.Map;

public interface SSOProvider {

    Map<String, Object> verifyAndGet(String token);

    String getProviderName();

}
