// package com.techriff.userdetails.jwt.utlis;

// import java.util.Date;
// import java.util.concurrent.TimeUnit;

// import org.springframework.stereotype.Component;

// import ch.qos.logback.core.util.TimeUtil;
// import io.jsonwebtoken.JwtBuilder;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.micrometer.core.instrument.util.TimeUtils;

// @Component
// public class JwtUtils {
//     private String SECRET="SECRET";

//     //2.READ CLAIMS


//     //1.Generate Token
//     public String generateToken(String subject)
//     {

        
//         return Jwts.builder()
//                     .setSubject(subject)
//                     .setIssuedAt(new Date(System.currentTimeMillis()))
//                     .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
//                     .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
//                     .compact();
        
//     }
//     //2.
    

// }
