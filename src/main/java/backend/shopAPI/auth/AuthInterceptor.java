package backend.shopAPI.auth;

import backend.shopAPI.grpc.AuthGrpcClient;
import backend.shop21auth.AuthProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthGrpcClient authGrpcClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        RequireAuth requireAuth = method.getMethodAnnotation(RequireAuth.class);
        if (requireAuth == null) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            response.setStatus(401);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\":\"Токен не предоставлен\"}");
            return false;
        }

        String token = authHeader.substring(7);

        try {
            AuthProto.ValidateTokenReply reply = authGrpcClient.validateToken(token);
            if (reply.getUserId() == null || reply.getUserId().isEmpty()) {
                response.setStatus(401);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"error\":\"" + reply.getMessage() + "\"}");
                return false;
            }
            
            request.setAttribute("userId", reply.getUserId());
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\":\"Ошибка проверки токена\"}");
            return false;
        }
    }
}
