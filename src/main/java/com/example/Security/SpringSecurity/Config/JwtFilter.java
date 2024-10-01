//package com.example.Security.SpringSecurity.Config;

//
//import java.io.IOException;
//
//import org.apache.catalina.core.ApplicationContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.example.Security.SpringSecurity.Service.JWTService;
//import com.example.Security.SpringSecurity.Service.MyUserDetailsService;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter{
//	@Autowired
//	private JWTService jwtService;
//	@Autowired
//	org.springframework.context.ApplicationContext context;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException 
//	{
//		// Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJDaGlndXJ1IiwiaWF0IjoxNzI3NzY5MzMzLCJleHAiOjE3Mjc3Njk0NDF9.PM8n55qWqPcesWMo3DMn7_eHIlO6NOLvYN4EqtNx8Xw
//		String authHeader = request.getHeader("Authorization");
//		String token = null;
//		String username = null;
//		
//		if(authHeader != null && authHeader.startsWith("Bearer "));
//		{
//			token = authHeader.substring(7);			// This is beacuse we only need the token and not the bearer so it will return the characters only after 7 characters
//			username = jwtService.extractUserName(token);
//		}
//		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null)
//		{
//			UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
//			if(jwtService.validateToken(token, userDetails))
//			{
//				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authToken);
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//
//}
package com.example.Security.SpringSecurity.Config;

import com.example.Security.SpringSecurity.Service.JWTService;
import com.example.Security.SpringSecurity.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//  Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzIzMTgzNzExLCJleHAiOjE3MjMxODM4MTl9.5nf7dRzKRiuGurN2B9dHh_M5xiu73ZzWPr6rbhOTTHs
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}