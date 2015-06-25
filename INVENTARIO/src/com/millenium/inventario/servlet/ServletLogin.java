package com.millenium.inventario.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.millenium.inventario.bean.BeanLogin;
import com.millenium.inventario.dao.ImplementLogin;
import com.millenium.inventario.dao.InterfaceLogin;

/**
 * Servlet implementation class ServletLogin
 */
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
//		ServletContext context = request.getServletContext();
		
		BeanLogin bean = new BeanLogin();
		InterfaceLogin iface = new ImplementLogin();
		
		String username = request.getParameter("usuario");
		String password = request.getParameter("clave");
		String noToma = request.getParameter("noToma");
		String txtToma = request.getParameter("texto");
		bean.setNoToma(noToma);
		bean.setUsuario(username);
		bean.setClave(password);
		bean = iface.validarLogin(bean);
		if(bean.getRespuesta()==1){
			HttpSession sesion = request.getSession();
			sesion.setAttribute("usuarioGlobal", bean.getUsuario());
			sesion.setAttribute("tomaGlobal", bean.getNoToma());
			sesion.setAttribute("textoToma", txtToma);
			sesion.setAttribute("userId", bean.getUserID());
			sesion.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("usuarioGlobal", bean.getUsuario());
			
			response.addCookie(userName);
			response.sendRedirect("agregar.jsp");
			
		}else if(bean.getRespuesta()==0){
			request.setAttribute("mensaje", bean.getNotificacion());
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			
		}
	}

}
