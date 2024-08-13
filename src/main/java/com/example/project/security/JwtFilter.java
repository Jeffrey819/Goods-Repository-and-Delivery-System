package com.example.project.security;

import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import

@Component
@WebFilter(urlPatterns = "/*")
public class JwtFilter implements Filter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            //First extract the url, to exclude the sign in and sign up requests
            String url = request.getRequestURI();

            if(url.contains("/signin") || url.contains("/signup")) {
                //Do nothing, just skip the token validation
                filterChain.doFilter(request, response);

            }
            else
            {
                //Not signin or signup requests, do the token validation
                String token = request.getHeader("Authorization");
                int result_validation = jwtUtil.validateToken(token);
                if(result_validation == 0)
                {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token is invalid!");
                    response.getWriter().flush();
                }
                else if(result_validation == 2)
                {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token has expired, please sign in again!");
                    response.getWriter().flush();
                }
                else{
                    //result_validation=1, which means the validation success.
                    filterChain.doFilter(request, response);
                }

            }
        }
        catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

}
