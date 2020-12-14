package com.bootx.util;

import java.util.Random;

public final class CodeUtils {

    private static final String[] STR = new String[]{
            "0","1","2","3","4","5","6","7","8","9"
    };

    private static final Random random = new Random();

    public static String create(Integer length){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<length;i++) {
            stringBuffer.append(STR[random.nextInt(STR.length)]);
        }
        return stringBuffer.toString();
    }
}
