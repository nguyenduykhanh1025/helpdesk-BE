package com.backend.helpdesk.common;

import java.util.HashMap;

public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    // const for sort skill
    public static final HashMap<Integer, String> HM_SORT_SKILLS = new HashMap<Integer, String>(){
        {
            put(0, "order by id"); // sort follow id
            put(1, "order by name"); // sort follow name asc
            put(2, "order by name desc"); // sort follow name desc
        }
    };

}