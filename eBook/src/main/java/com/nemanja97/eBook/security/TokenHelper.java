package com.nemanja97.eBook.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.mobile.device.Device;

import com.nemanja97.eBook.common.TimeProvider;
import com.nemanja97.eBook.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenHelper {

    @Value("spring-security-demo")
    private String APP_NAME;

    @Value("somesecret")
    public String SECRET;

    @Value("30000")
    private int EXPIRES_IN;

    @Value("600")
    private int MOBILE_EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    @Autowired
    TimeProvider timeProvider;

    //HMAC sa SHA-512 hash algoritmom
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    //Metoda koja iz sadrzaja tokena (claims) pronalazi username korisnika
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

//	public String refreshToken(String token) {
//		String refreshedToken;
//		Date a = timeProvider.now();
//		try {
//			final Claims claims = this.getAllClaimsFromToken(token);
//			claims.setIssuedAt(a);
//			refreshedToken = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
//					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
//		} catch (Exception e) {
//			refreshedToken = null;
//		}
//		return refreshedToken;
//	}

    //Funkcija koja pri autentifikaciji korisnika kreira novi JWT token
    public String generateToken(String username, Device device) {
        String audience = generateAudience(device);
        return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setAudience(audience)
                .setIssuedAt(timeProvider.now()).setExpiration(generateExpirationDate(device))
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(Device device) {
        long expiresIn = device.isTablet() || device.isMobile() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
        return new Date(timeProvider.now().getTime() + expiresIn * 1000);
    }

    public int getExpiredIn(Device device) {
        return device.isMobile() || device.isTablet() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
    }


    //FUnkcija koja proverava da li je token validan u odnosu na korisnika koji ga je prosledio serveru
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (username != null && username.equals(userDetails.getUsername()));
    }

    public String getToken(HttpServletRequest request) {
        /**
         * Getting the token from Authentication header e.g Bearer your_token
         */
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

}
