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

    public static void main(String[] args) {
        Claims claims = parseToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3Iiwic3ViIjoiYmxhY2tib3kiLCJpYXQiOjE2MDkyOTE1NjYsImV4cCI6MTYwOTM3Nzk2NiwiaWQiOjcsInVzZXJuYW1lIjoieGlhbGkifQ.xrBcaFwS52_qrMR5C9-Lt6yg2YnUdXnxM1Z0eCKnfWA");

        System.out.println(claims);
    }
}
