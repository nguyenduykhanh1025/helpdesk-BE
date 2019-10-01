package com.backend.helpdesk.common;

public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
    public static final int DAYOFFBYRULE=15;
    public static final String PENDING="pending";
    public static final String APPROVED="approved";
    public static final String REJECTED="rejected";

    // const sort follow skill
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_NAME_DESC = 2;

}