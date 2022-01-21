package com.funicorn.basic.common.cloud.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Aimee
 * @since 2021/10/14 14:59
 */
@SuppressWarnings("unused")
public class CloudUtil {

    /**
     * 获取当前接入的ip地址
     * @return String ip地址
     * */
    public static String getRemoteAddr() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            if (address==null){
                throw new UnknownHostException();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }

        return address.getHostAddress();
    }
}
