package com.strr.system.constant;

public interface Constant {
    String CLIENT_ID = "STRR_CLIENT";
    String CLIENT_SECRET = "$2a$10$Sh5.zur4fLl2x6OYuqB1gezUypaCY61TY3hk2DFgEevOJdoHW.JFW";  // STRR_SECRET
    String[] GRANT_TYPES = new String[] { "refresh_token", "password", "client_credentials" };
    String[] SCOPES = new String[] { "web" };
}
