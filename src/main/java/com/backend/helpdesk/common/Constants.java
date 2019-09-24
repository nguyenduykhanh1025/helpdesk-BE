package com.backend.helpdesk.common;

public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    // const sort follow skill
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_NAME_DESC = 2;

    // const sort follow user
    public static final int SORT_BY_EMAIL = 1;
    public static final int SORT_BY_AGE = 2;
    public static final int SORT_BY_STARTING_DAY = 3;
    public static final int SORT_BY_FIRST_NAME = 4;
    public static final int SORT_BY_LAST_NAME = 5;

}