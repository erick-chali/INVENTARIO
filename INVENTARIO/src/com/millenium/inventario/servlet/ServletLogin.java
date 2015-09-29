package com.millenium.inventario.servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.millenium.db.conectar.ConectarDB;
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
		System.out.println(username + " " + password + " " + noToma + " " + txtToma);
		bean.setNoToma(noToma);
		bean.setUsuario(username);
		bean.setClave(password);
		bean = iface.validarLogin(bean);
		Connection conectar = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		int respuesta=0;
		HttpSession sesion = request.getSession();
		try {
			conectar = new ConectarDB().getConnection();
			stmt = conectar.prepareCall("{call looginSelectUser(?)}");
			stmt.setString(1, username);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString("estado_empleado").equals("A")){
						if(rs.getString("UserName").equals(username)&&rs.getString("Password").equals(password)){
							respuesta =1;
							System.out.println("buen usuario");
							sesion.setAttribute("usuarioGlobal", rs.getString("UserName"));
							sesion.setAttribute("tomaGlobal", noToma);
							sesion.setAttribute("textoToma", txtToma);
							sesion.setAttribute("userId", rs.getString("UserID"));
							sesion.setMaxInactiveInterval(30*60);
							
						}else{
							respuesta =0;
							System.out.println("mal usuario");
						}
				}else{
					respuesta =2;
					System.out.println("no existe usuario");
				}
			}
			stmt.close();
			rs.close();
			conectar.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
		}
		if(respuesta == 1){
//			response.sendRedirect("/agregar.jsp");
			response.setContentType("text/html");
			response.getWriter().write("1");
			System.out.println("Hecho!!");
		}else if(respuesta == 0){
			System.out.println("Fail!!");
//			request.setAttribute("mensaje", "clave o usuario incorrectos.");
//			request.getRequestDispatcher("/index.jsp").forward(request, response);
			response.setContentType("text/html");
			response.getWriter().write("0");
		}else if(respuesta ==2){
//			request.setAttribute("mensaje", "usuario no esta activo verificar con admin de sistemas.");
//			request.getRequestDispatcher("/index.jsp").forward(request, response);
			response.setContentType("text/html");
			response.getWriter().write("2");
		}
		
	}

}
