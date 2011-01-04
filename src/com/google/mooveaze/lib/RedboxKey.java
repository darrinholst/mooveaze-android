package com.google.mooveaze.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedboxKey {
    private static String key10;
    private static String key20;

    public static String get20key() {
        if(key20 == null) {
            RestClient.Response response = new RestClient().get("http://www.redbox.com", new RestClient.Header[]{new RestClient.Header("Cookie", "RB_2.0=1")});
            Pattern pattern = Pattern.compile(".*rb\\.api\\.key *= * [',\"](.*?)[',\"].*");
            Matcher matcher = pattern.matcher(response.entity);

            if(matcher.matches()) {
                key20 = matcher.group(1);
                Log.debug("Found 2.0 key - " + key20);
            }
        }

        return key20;

    }
}
