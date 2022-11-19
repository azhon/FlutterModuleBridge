package com.azhon.bridge.util;

/**
 * createDate: 2022/11/18 on 14:25
 * desc:
 *
 * @author azhon
 */


public class StringUtil {

    /**
     * 将下划线格式转换成驼峰命名并首字母大写
     */
    public static String toCamelCase(String str) {
        final char underLine = '_';
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = Character.toLowerCase(str.charAt(i));
            if (c == underLine) {
                if (++i < str.length()) {
                    sb.append(Character.toUpperCase(str.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        //首字母大写
        char[] chars = sb.toString().toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }

    /**
     * 首字母小写同时加"_"
     */
    public static String firstLowCase(String str) {
        String s = str.substring(0, 1).toLowerCase();
        return "_" + s + str.substring(1);
    }

}
