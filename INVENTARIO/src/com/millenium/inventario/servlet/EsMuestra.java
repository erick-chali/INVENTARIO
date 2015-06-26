package com.millenium.inventario.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.millenium.db.conectar.ConectarDB;

/**
 * Servlet implementation class EsMuestra
 */
public class EsMuestra extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EsMuestra() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Entro a muestras");
		String codigoE = request.getParameter("codigoe");
		String respuesta = "";
		Connection con = null;
		PreparedStatement ps = null;
		StringBuilder sb = null;
		ResultSet rs = null;
		
		
		try {
			con = new ConectarDB().getConnection();
			sb = new StringBuilder();
			sb.append("Select es_muestra from in_estanteria where Estanteria_Id="+codigoE+";");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			
			while(rs.next()){
				respuesta = rs.getString("es_muestra");
			}
			con.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
		if(respuesta.equals("1")){
			response.setContentType("text/html");
			response.getWriter().print(respuesta);
		}else{
			response.setContentType("text/html");
			response.getWriter().print("");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
