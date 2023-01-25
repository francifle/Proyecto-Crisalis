package com.crisalis.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SesionFilter implements Filter {

	private Logger log = LoggerFactory.getLogger(SesionFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session != null && session.getAttribute("usuario") == null && checkURI(req.getRequestURI())) {
			res.sendRedirect("/");
		}
        chain.doFilter(req, res);
        
	}
	public boolean checkURI(String URI) {
		if (URI.contains("assets"))
			return false;
		if (URI.equals("/home")) 
			return false;
		if (URI.equals("/register"))
			return false;
		if (URI.equals("/")) 
			return false;
	return true; 
	}
}
