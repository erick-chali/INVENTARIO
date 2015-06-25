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
import com.millenium.inventario.bean.BeanBuscaEstanteria;
import com.millenium.inventario.dao.ImplementaBuscaEstanteria;

/**
 * Servlet implementation class BuscarEstanteria
 */
public class BuscarEstanteria extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarEstanteria() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BeanBuscaEstanteria bean = new BeanBuscaEstanteria();
		System.out.println("Entro al servlet");
		ArrayList<BeanBuscaEstanteria> datos = new ArrayList<BeanBuscaEstanteria>();
		
		String codigoEst = request.getParameter("codigo"); 
		String descEst = request.getParameter("desc");
		System.out.println(codigoEst + " " + descEst);
		bean.setCodigoEstanteria(codigoEst);
//		bean.setDescripcionEstanteria(descEst);
		
		datos = ImplementaBuscaEstanteria.obtenerDatos();
		
		Gson gson = new Gson();
//		String json = new Gson().toJson(datos);
		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanBuscaEstanteria>>(){}.getType());
		JsonArray arreglo = elemento.getAsJsonArray();
		response.setContentType("application/json");
		response.getWriter().print(arreglo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
////		BeanBuscaEstanteria bean = new BeanBuscaEstanteria();
//		System.out.println("Entro al servlet");
//		ArrayList<BeanBuscaEstanteria> datos = new ArrayList<BeanBuscaEstanteria>();
//		
//		String codigoEst = request.getParameter("codigoEstanteria"); 
//		String descEst = request.getParameter("descEstanteria");
//		System.out.println(codigoEst + " " + descEst);
////		bean.setCodigoEstanteria(codigoEst);
////		bean.setDescripcionEstanteria(descEst);
//		
//		datos = ImplementaBuscaEstanteria.obtenerDatos();
//		
//		Gson gson = new Gson();
////		String json = new Gson().toJson(datos);
//		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanBuscaEstanteria>>(){}.getType());
//		JsonArray arreglo = elemento.getAsJsonArray();
//		response.setContentType("application/json");
//		response.getWriter().print(arreglo);
	}

}
