package com.online.test.api.config.security;

import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
 
@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
 
    @Override
    public void commence(final HttpServletRequest request, 
            final HttpServletResponse response, 
            final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.
    	
    	if(authException.getMessage().equalsIgnoreCase("Bad credentials"))
    	{
    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    	}
    	else if(authException.getMessage().equalsIgnoreCase("Full authentication is required to access this resource"))
    	{
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	}
        
        //response.addHeader("WWW-Authenticate", "Basic realm=" + super.getRealmName() + "");
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authException.getMessage());
    }
     
    @Override
    public void afterPropertiesSet() throws Exception {
        super.setRealmName(ProtectedAPIConfiguration.realName);
        super.afterPropertiesSet();
    }
}
