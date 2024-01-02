package com.ifs.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;
import com.ifs.gateway.exception.AuthorizationHeaderMissing;
import com.ifs.gateway.exception.UnauthorizedAccessException;
import com.ifs.gateway.utils.JwtService;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtService jwtService;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {

		logger.info("Outside GatewayFilter");

		return ((exchange, chain) -> {

//			System.out.println(validator.isSecured.test(exchange.getRequest()));
			if (validator.isSecured.test(exchange.getRequest())) {
				logger.info("Inside GatewayFilter");

				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

					throw new AuthorizationHeaderMissing("Authorization Header is Missing");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

				logger.info("Token with Bearer {}", authHeader);

				if (authHeader != null && authHeader.startsWith("Bearer ")) {

					authHeader = authHeader.substring(7);

					logger.info("Substring {}", authHeader);

				}
				try {
//					System.out.println(authHeader);
					jwtService.validateToken(authHeader);
					
					String userRole = jwtService.getRole(authHeader);

					exchange.getAttributes().put("userRole", userRole);

				} catch (Exception e) {
					throw new UnauthorizedAccessException(
							"Unauthorized access to application" + jwtService.extractUsername(authHeader));
				}
			}
			return chain.filter(exchange);
		});
	}

	public static class Config {
	}
}
