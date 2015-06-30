package com.millenium.inventario.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.millenium.inventario.bean.BeanBuscaProducto;
import com.millenium.inventario.bean.BeanBuscaSeccion;
import com.millenium.inventario.dao.ImplementaBuscaProducto;
import com.millenium.inventario.dao.ImplementaBuscaSeccion;
import com.millenium.inventario.dao.InterfaceBuscaSeccion;

/**
 * Servlet implementation class BuscaSeccion
 */
public class BuscaSeccion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscaSeccion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Entro a Seccion");
		BeanBuscaSeccion bean = new BeanBuscaSeccion();
		InterfaceBuscaSeccion iface = new ImplementaBuscaSeccion();
		
		String estanteriaID = request.getParameter("codigoe");
		bean.setEstanteriaID(estanteriaID);
		
		bean = iface.buscaSeccion(bean);
		
		ArrayList<BeanBuscaSeccion> secciones = new ArrayList<BeanBuscaSeccion>();
		secciones = ImplementaBuscaSeccion.obtenerSecciones();
		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(secciones, new TypeToken<List<BeanBuscaSeccion>>(){}.getType());
		JsonArray arreglo = elemento.getAsJsonArray();
		response.setContentType("application/json");
		response.getWriter().print(arreglo);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
	}

}
