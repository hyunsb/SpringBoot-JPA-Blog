package com.hyunsb.blog.kakao;

public enum KakaoUserInfoHeaders {
    Authorization("Authorization", "Bearer "),
    Content_Type("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    private final String headerKey;
    private final String headerValue;
    private String code;

    KakaoUserInfoHeaders(String headerKey, String headerValue) {
        this.headerKey = headerKey;
        this.headerValue = headerValue;
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setCode(String code){
        this.code = code;
    }
}
