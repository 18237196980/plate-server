package com.fz.zf.util;

import java.util.Random;

public class CodeUtils {

    /**
     * 生成5位随机数(字母和数字)
     * @return
     */
    public static String getCheckCode() {
        String ZiMu = "QWERTYUPASDFGJKLZXCVBNM123456789";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(ZiMu.length());
            char c = ZiMu.charAt(index);
            result += c;
        }
        return result;
    }

    /**
     * 生成4位随机数(数字)
     * @return
     */
    public static String getCode(int x) {
        String ZiMu = "0123456789";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < x; i++) {
            int index = random.nextInt(ZiMu.length());
            char c = ZiMu.charAt(index);
            result += c;
        }
        return result;
    }
}
