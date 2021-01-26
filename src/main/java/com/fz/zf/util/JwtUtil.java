package com.fz.zf.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.util.Date;

public class JwtUtil {

    public static void main(String[] args) throws Exception {
        /*JwtBuilder builder = Jwts.builder().setId("123")
                                 .setSubject("所面向的用户")
                                 .setIssuedAt(new Date())
                                 .signWith(SignatureAlgorithm.HS256,TextCodec.BASE64.encode("abc"));
        String s = builder.compact();
        System.out.println(s);*/

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLmiYDpnaLlkJHnmoTnlKjmiLciLCJpYXQiOjE2MDk5MjMzNzd9.RNfypRof869hzg-5ykP66UDQZMfJVbb6Kutz0QKoznc";

        Claims claims =
                Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.encode("abc1"))
                    .parseClaimsJws(token)
                    .getBody();

        System.out.println("id---:" + claims.getId());
        System.out.println("Subject-----:" + claims.getSubject());
        System.out.println("IssuedAt---:" + claims.getIssuedAt());

    }

    public static String getJwt(String id) {
        JwtBuilder builder = Jwts.builder()
                                 .setId("123")
                                 .setSubject("所面向的用户")
                                 .setIssuedAt(new Date())
                                 .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode("abc"));
        return builder.compact();
    }

}
