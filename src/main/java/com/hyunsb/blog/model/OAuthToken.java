package com.hyunsb.blog.model;

import lombok.Data;

@Data
public class OAuthToken {
    public String access_token;
    public String token_type;
    public String refresh_token;
    public String expires_in;
    public String scope;
    public String refresh_token_expires_in;
}
