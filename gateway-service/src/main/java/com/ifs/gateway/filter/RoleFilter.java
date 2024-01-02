package com.ifs.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.ifs.gateway.exception.UnauthorizedAccessException;
import com.ifs.gateway.utils.JwtService;

@Component
public class RoleFilter extends AbstractGatewayFilterFactory<RoleFilter.Config> {
	Logger logger = LoggerFactory.getLogger(RoleFilter.class);
	@Autowired
	private RouteValidator validator;

	@Autowired
	JwtService jwtService;

	public RoleFilter() {
		super(Config.class);
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			String userRole = exchange.getAttribute("userRole");
			logger.info("check role: {}", userRole);

	        if (userRole != null) {
	        	if (validator.isSecuredCommon.test(exchange.getRequest())) {
	                return chain.filter(exchange);
	            }
	        	
	        	if (validator.isSecuredHR.test(exchange.getRequest()) &&  (!userRole.equals("ROLE_HR"))) {
	                    throw new UnauthorizedAccessException("Unauthorized access to HR resources");
	                
	            }

	            if (validator.isSecuredInterviewer.test(exchange.getRequest()) &&  (!userRole.equals("ROLE_Interviewer"))) {
	                    throw new UnauthorizedAccessException("Unauthorized access to Interviewer resources");
	                
	            }
	        }
			
			return chain.filter(exchange);
		});

	}

}
