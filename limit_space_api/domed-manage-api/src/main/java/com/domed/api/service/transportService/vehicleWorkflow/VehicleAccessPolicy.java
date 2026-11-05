package com.domed.api.service.transportService.vehicleWorkflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import static com.domed.api.service.transportService.vehicleWorkflow.VehicleWorkflowException.require;

/** Scoped signature verification: never trusts the legacy unsigned payload header. */
@Component
public class VehicleAccessPolicy {
    @PersistenceContext private EntityManager em;
    @Value("${vehicle.workflow.jwt-public-key:}") private String publicKey;
    @Value("${vehicle.workflow.jwt-audience:}") private String audience;
    private final ObjectMapper json = new ObjectMapper();

    public String verifySubject(String authorization) {
        require(publicKey != null && !publicKey.trim().isEmpty() && audience != null && !audience.isEmpty(),
                503, "车辆业务尚未配置受信任的 JWT 公钥和 audience");
        require(authorization != null && authorization.regionMatches(true, 0, "Bearer ", 0, 7), 401, "请重新登录");
        try {
            String token = authorization.substring(7).trim();
            require(token.length() < 16384, 401, "无效令牌");
            String[] parts = token.split("\\.", -1);
            require(parts.length == 3, 401, "无效令牌");
            JsonNode header = json.readTree(Base64.getUrlDecoder().decode(parts[0]));
            require("RS256".equals(header.path("alg").asText()), 401, "不支持的令牌签名");
            String pem = publicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");
            PublicKey key = KeyFactory.getInstance("RSA").generatePublic(
                    new X509EncodedKeySpec(Base64.getDecoder().decode(pem)));
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(key);
            verifier.update((parts[0] + "." + parts[1]).getBytes(StandardCharsets.US_ASCII));
            require(verifier.verify(Base64.getUrlDecoder().decode(parts[2])), 401, "令牌签名无效");
            JsonNode claims = json.readTree(Base64.getUrlDecoder().decode(parts[1]));
            long now = System.currentTimeMillis() / 1000;
            require(claims.path("exp").isNumber() && claims.path("exp").asLong() > now, 401, "令牌已过期");
            require(!claims.has("nbf") || (claims.path("nbf").isNumber() && claims.path("nbf").asLong() <= now),
                    401, "令牌尚未生效");
            JsonNode aud = claims.path("aud");
            boolean match = audience.equals(aud.asText());
            if (aud.isArray()) for (JsonNode value : aud) match |= audience.equals(value.asText());
            require(match, 401, "令牌 audience 不匹配");
            String subject = claims.path("user_name").asText("");
            // Existing SysUserDetails stores the original login JSON as its username.
            if (subject.startsWith("{")) subject = json.readTree(subject).path("userName").asText("");
            require(!subject.isEmpty() && subject.length() <= 100, 401, "令牌缺少登录账号");
            return subject;
        } catch (VehicleWorkflowException e) { throw e; }
        catch (Exception e) { throw new VehicleWorkflowException(401, "无效令牌或签名配置"); }
    }

    @Transactional(readOnly = true)
    public VehicleActor authenticate(String authorization) {
        String subject = verifySubject(authorization);
        List<?> users = em.createNativeQuery("SELECT GID, NAME, XZQH_CODE, XZJG_CODE, YHCD_GID " +
                "FROM TAB_YHWH WHERE CODE = :code AND STATUS = 1")
                .setParameter("code", subject).setMaxResults(2).getResultList();
        require(users.size() == 1, 403, "账号不可用或不唯一");
        Object[] user = (Object[]) users.get(0);
        String role = str(user[4]);
        List<?> urls = em.createNativeQuery("SELECT m.URL FROM TAB_MENU m " +
                "JOIN TAB_CDGL g ON g.MENU_CODE = m.CODE " +
                "JOIN TAB_YHCD r ON r.GID = g.YHCD_GID " +
                "WHERE r.GID = :role AND r.STATUS = 1 AND m.STATUS = 0")
                .setParameter("role", role).getResultList();
        Set<String> grants = new HashSet<>();
        for (Object url : urls) grants.add(str(url));
        String region = str(user[2]);
        require(!region.isEmpty(), 403, "账号缺少行政区划");
        return new VehicleActor(str(user[0]), str(user[1]), region, str(user[3]),
                grants.contains("/frontPage/vehicleWorkflow_index"),
                grants.contains("/frontPage/servicePreAccept_index") || grants.contains("/roadFreight/servicePreAccept_index"),
                grants.contains("/frontPage/serviceAccept_index") || grants.contains("/roadFreight/serviceAccept_index"));
    }
    private static String str(Object value) { return value == null ? "" : value.toString(); }
}
