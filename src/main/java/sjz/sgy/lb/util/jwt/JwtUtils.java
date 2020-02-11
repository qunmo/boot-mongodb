package sjz.sgy.lb.util.jwt;
import java.security.SignatureException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.util.encoders.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtUtils {
	/**
	 * 签发JWT
	 * @Author  科帮网 
	 * @param id
	 * @param subject 可以是JSON数据 尽可能少
	 * @param ttlMillis
	 * @return  String
	 * @Date	2017年11月24日
	 * 2017年11月24日  张志朋  首次创建
	 *
	 */
	public static String createJWT(String id, String subject, long ttlMillis) {
		//指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//生成JWT的时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。
	     //它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
		SecretKey secretKey = generalKey();
		//下面就是在为payload添加各种标准声明和私有声明了
		JwtBuilder builder = Jwts.builder()
				.setId(id) //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setSubject(subject)    //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
				.setIssuer("cnn")     // 签发者
				.setIssuedAt(now)      // 签发时间
				.signWith(signatureAlgorithm, secretKey); //设置签名使用的签名算法和签名使用的秘钥
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate); // 过期时间
		}
		
		return builder.compact();
	}
	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static CheckResult validateJWT(String jwtStr) {
		CheckResult checkResult = new CheckResult();
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setErrCode(TokenConstant.JWT_ERRCODE_EXPIRE);
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setErrCode(TokenConstant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(TokenConstant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
	/**
	 * 公钥
	 * @return
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(TokenConstant.JWT_SECERT);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}
	
	/**
	 * 
	 * 解析JWT字符串
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody();
	}
	/**
	 * 解析拿到对应的唯一码
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static String parseJWTGetCode(String jwt) throws Exception {
		Claims claims=parseJWT(jwt);
		String code=claims.getId();
		//解析获取
		return code;
	}
	/**
	 * 获取用户信息
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static String parseJWTGetSubject(String jwt) throws Exception {
		Claims claims=parseJWT(jwt);
		String subject=claims.getSubject();
		//获取用户信息
		return subject;
	}
	
	/**
	 * 根据request，获取token
	 * @author 张盼盼
	 * @param request
	 * @return token的字符串
	 * @throws Exception
	 */
	public static String getToken(HttpServletRequest request) throws Exception {
		Map<String,String> mapHeader = new HashMap<>();
		Enumeration headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String key = headerNames.nextElement().toString();
			String value = request.getHeader(key);
			mapHeader.put(key, value);
		}
		
		return mapHeader.get(TokenConstant.TOKEN).toString();
	}
	
	public static void main(String[] args) throws Exception {
		//小明失效 10s
		String sc = createJWT("lisi","24", 10000);//5分钟
		System.out.println(sc);
		System.out.println(parseJWT(sc).getId());
		System.out.println(validateJWT(sc).getClaims().getSubject());
		Thread.sleep(4000);
		System.out.println(validateJWT(sc).getClaims().getSubject());
		Thread.sleep(2000);
		System.out.println(validateJWT(sc).getClaims().getSubject());
		Thread.sleep(6000);
		System.out.println(validateJWT(sc));
		Thread.sleep(6000);
		System.out.println(validateJWT(sc));
	}

}
