package com.hyunsb.blog.kakao;

public enum KakaoTokenParams {
    grant_type("grant_type", "authorization_code"),
    client_id("client_id", "2937b7fab5557493b86a57c07067c2a8"),
    redirect_uri("redirect_uri", "http://localhost:8000/auth/kakao/callback");

    private final String paramName;
    private final String paramValue;

    KakaoTokenParams(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
    }
}
