package com.hyunsb.blog.kakao;

public enum TokenParameter {
    RequestParams("authorization_code", "2937b7fab5557493b86a57c07067c2a8", "http://localhost:8000/auth/kakao/callback");

    private final String grantType;
    private final String clientId;
    private final String redirectUri;

    TokenParameter(String grantType, String clientId, String redirectUri) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getGrantType() {
        return grantType;
    }
}