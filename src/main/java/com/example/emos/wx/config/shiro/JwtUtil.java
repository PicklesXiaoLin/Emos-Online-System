package com.example.emos.wx.config.shiro;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.emos.wx.exception.EmosException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 周凡
 * @date 2021/12/11 0011 下午 19:35
 */
@Component
@Slf4j
public class JwtUtil {
    @Value("${emos.jwt.secret}")
    private String secret ;
    @Value("${emos.jwt.expire}")
    private int expire ;

    /**
     * 密钥 + 过期时间 + 用户id = Token
     * @param userId
     * @return Token
     */
    public String createToken(int userId){
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, expire).toJdkDate();
        //创建加密算法对象
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        return token ;
    }

    /**
     * 从token中获取用户id
     * @param token
     * @return
     */
    public int getUserId(String token){
        try {
            DecodedJWT decode = JWT.decode(token);
            Integer userId = decode.getClaim("userId").asInt();
            return userId ;
        } catch (JWTDecodeException e) {
            throw new EmosException("令牌无效") ;
        }
    }

    public void verifierToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token) ;
    }
}
