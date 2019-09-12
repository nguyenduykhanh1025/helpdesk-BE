package com.backend.helpdesk.common;

import java.util.regex.Pattern;

public class CommonMethods {

    public static boolean isEmailNovaHub(String email) {
        String query = "[a-z][a-z0-9_\\.]{5,32}@novahub.vn";

        return Pattern.matches(query, email);
    }
}
