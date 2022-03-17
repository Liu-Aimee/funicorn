package com.funicorn.basic.common.base.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aimee
 * @since 2022/3/17 15:02
 */
public class CheckUtil {

    /**
     * 校验 http || https 是否合法
     * @param url url
     * @return boolean
     * */
    public static boolean isHttpOrHttps(String url) {
        String regex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern. compile(regex);
        url = url.toLowerCase(Locale.ROOT);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    /**
     * 校验 ftp 是否合法
     * @param url url
     * @return boolean
     * */
    public static boolean isFtp(String url) {
        String regex = "^(ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        url = url.toLowerCase(Locale.ROOT);
        Pattern pattern = Pattern. compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    /**
     * 校验 file地址 是否合法
     * @param url url
     * @return boolean
     * */
    public static boolean isFileUrl(String url) {
        String regex = "^(file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        url = url.toLowerCase(Locale.ROOT);
        Pattern pattern = Pattern. compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
