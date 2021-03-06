package com.bootx.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

    public static String create(String id, Map<String,Object> map){
        JwtBuilder builder= Jwts.builder().setId(id)
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"wangmh")
                .setExpiration(DateUtils.getNextDay(1));
        for (String key: map.keySet()) {
            builder.claim(key,map.get(key));
        }
        return builder.compact();
    }

    public static Claims parseToken(String token){
        Claims claims = Jwts.parser().setSigningKey("wangmh").parseClaimsJws(token).getBody();
        return claims;
    }
}
