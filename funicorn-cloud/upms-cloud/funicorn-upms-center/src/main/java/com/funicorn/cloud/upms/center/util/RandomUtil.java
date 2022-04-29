package com.funicorn.cloud.upms.center.util;

/**
 * @author Aimee
 * @since 2022/4/13 15:53
 */
public class RandomUtil {

    /**
     * 随机字符串，剔除了 0，O，o
     * */
    private static final String[] CHAR_ARRAY = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5",
            "6", "7", "8", "9","A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 获取指定长度的随机数，不保证唯一性
     * @param length 长度
     * @return String
     * */
    public static String getRandomStr(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int random = (int) (Math.random() * 59);
            stringBuilder.append(CHAR_ARRAY[random]);
        }
        return stringBuilder.toString();
    }
}
