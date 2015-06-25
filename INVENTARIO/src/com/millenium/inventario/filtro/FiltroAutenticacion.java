package com.millenium.inventario.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FiltroAutenticacion
 */
public class FiltroAutenticacion implements Filter {
	private ServletContext context;
    
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("Filtro de Autenticacion inicializado");
    }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
         
        String uri = req.getRequestURI();
        this.context.log("Requested Resource::"+uri);
         
        HttpSession session = req.getSession(false);
         
        if(session == null && !(uri.endsWith("html") || uri.endsWith("ServletLogin"))){
            this.context.log("Acceso Restringido, debe iniciar sesion");
            res.sendRedirect("login.html");
        }else{
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
         
         
    }
    public void destroy() {
        //close any resources here
    }

}
