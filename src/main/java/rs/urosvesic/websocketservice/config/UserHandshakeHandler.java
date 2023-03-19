package rs.urosvesic.websocketservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author UrosVesic
 */
@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final Logger log = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String token = (String) attributes.get("auth_token");
        final DecodedJWT decodedIdToken = JWT.decode(token);
        String username = decodedIdToken.getClaim("username").asString();
        log.info("User with username: "+username+" opened page");
        return new UserPrincipal(username);
    }



}
