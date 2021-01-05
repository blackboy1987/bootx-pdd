package com.bootx.pdd.service.impl;

/**
 * @author blackoy
 */
public interface PddService {

    String token(String code);
    String refreshToken(String refreshToken);

    void addProduct();
}
