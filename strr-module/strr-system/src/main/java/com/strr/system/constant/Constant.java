package com.strr.system.constant;

public interface Constant {
    String CLIENT_ID = "WEB_CLIENT";
    String CLIENT_SECRET = "$2a$10$6/8lI7dg2gAX3msCTEh6ZefoTyOSpiTunbm2vYCgQ.sT9a6mHh1Eu";  // WEB_SECRET
    String[] GRANT_TYPES = new String[] { "refresh_token", "password", "client_credentials" };
    String[] SCOPES = new String[] { "web" };

    String JWT_SECRET = "JWT_SECRET";
}
