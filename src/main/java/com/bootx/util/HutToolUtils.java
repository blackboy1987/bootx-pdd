package com.bootx.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;

public final class HutToolUtils {

    private static final SM2 sm2 = SmUtil.sm2();

    public static String signHex (String content) {
        String sign = sm2.signHex(HexUtil.encodeHexStr(content));
        return sign;
    }
    public static boolean verifyHex (String content,String sign) {
        return sm2.verifyHex(HexUtil.encodeHexStr(content), sign);
    }


}
