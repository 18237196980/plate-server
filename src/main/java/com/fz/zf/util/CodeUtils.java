package com.fz.zf.util;

import java.util.Random;

public class CodeUtils {

    /**
     * 生成5位随机数(字母和数字)
     *
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
     *
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

    /**
     * 生成省简称
     *
     * @return
     */
    public static String getNike(int x) {
        String ZiMu = "京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < x; i++) {
            int index = random.nextInt(ZiMu.length());
            char c = ZiMu.charAt(index);
            result += c;
        }
        return result;
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 返回手机号码
     */
    public static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    public static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000)
                              .substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000)
                             .substring(1);
        return first + second + third;
    }

}
