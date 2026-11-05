package local.vehicle.testing;

import com.domed.api.service.transportService.vehicleWorkflow.*;
import com.domed.common.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public final class LocalRequestFilter extends OncePerRequestFilter {
    private final byte[] key;
    private final ObjectMapper json = new ObjectMapper();
    private static final Set<String> POSTS = new HashSet<>(Arrays.asList(
        "/vehicleWorkflow/options.json", "/vehicleWorkflow/drafts.json",
        "/vehicleWorkflow/getAcceptCarByLetterGid.json", "/vehicleWorkflow/holdRoadFirstIssueTemp.json",
        "/vehicleWorkflow/submit.json", "/vehicleWorkflow/updateCarForecastAccept.json",
        "/vehicleWorkflow/updateCarAccept.json", "/vehicleLocalTest/inbox.json",
        "/dlhwjyxk/saveOrUpdateDlhwjyxk.json", "/dlhwjyxk/getDlhwjyxkInfoByGid.json", "/dlhwjyxk/queryDraftList.json",
        "/yhwhlb/queryUserList.json", "/yhwhlb/saveOrUpdateUserInfo.json", "/yhwhlb/bindUserRole.json",
        "/yhcd/queryYhList.json", "/yhcd/saveCdgl.json", "/cd/getTreeMenuList.json", "/localSystem/saveRole.json",
        "/servicePreAccept/queryAllPreAccept.json", "/serviceAccept/queryAllAccept.json"));
    public LocalRequestFilter(String key) { this.key = key.getBytes(StandardCharsets.UTF_8); }
    @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        res.setHeader("Cache-Control", "no-store"); res.setHeader("X-Content-Type-Options", "nosniff");
        try {
            need(loopback(req.getRemoteAddr()) && loopback(req.getLocalAddr()), "Loopback requests only");
            need("127.0.0.1:18092".equals(req.getHeader("Host")), "Unexpected host");
            need(req.getHeader("Forwarded") == null && req.getHeader("X-Forwarded-For") == null &&
                 req.getHeader("X-Forwarded-Host") == null && req.getHeader("X-Forwarded-Proto") == null, "Forwarded requests are not allowed");
            String origin = req.getHeader("Origin");
            need(origin == null || LocalProfileGuard.FRONTEND_ORIGIN.equals(origin), "Unexpected origin");
            String supplied = req.getHeader("X-Vehicle-Local-Key");
            need(supplied != null && MessageDigest.isEqual(key, supplied.getBytes(StandardCharsets.UTF_8)), "Missing local access key");
            String path = req.getRequestURI();
            need(("GET".equals(req.getMethod()) && "/vehicleLocalTest/session.json".equals(path)) ||
                 ("POST".equals(req.getMethod()) && POSTS.contains(path)), "API unavailable in the local harness");
            req.setAttribute(LocalTestActors.ATTRIBUTE, LocalTestActors.actor(req.getHeader("X-Vehicle-Local-Actor")));
            chain.doFilter(req, res);
        } catch (VehicleWorkflowException e) {
            res.setStatus(e.getStatus()); res.setCharacterEncoding("UTF-8"); res.setContentType("application/json");
            json.writeValue(res.getWriter(), Result.result("V" + e.getStatus(), e.getMessage(), null));
        }
    }
    private static boolean loopback(String ip) { return "127.0.0.1".equals(ip) || "::1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip); }
    private static void need(boolean ok, String reason) { if (!ok) throw new VehicleWorkflowException(403, reason); }
}
