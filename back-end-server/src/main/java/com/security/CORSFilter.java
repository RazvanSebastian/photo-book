package com.security;

import java.io.IOException;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;


public class CORSFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    if (((HttpServletRequest) req).getMethod().equals("OPTIONS")) {

      HttpServletResponse response = (HttpServletResponse) res;
      response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
      response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, "
      		+ "X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

    } else {

      chain.doFilter(req, res);

    }
  }
}
