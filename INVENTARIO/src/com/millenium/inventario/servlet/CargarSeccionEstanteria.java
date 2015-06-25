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
import com.millenium.inventario.bean.BeanAutoCompletar;
import com.millenium.inventario.bean.BeanCargarEstanteria;
import com.millenium.inventario.dao.ImplementaAutoCompletar;
import com.millenium.inventario.dao.ImplementaCargarEstanteria;

/**
 * Servlet implementation class CargarSeccionEstanteria
 */
public class CargarSeccionEstanteria extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarSeccionEstanteria() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<BeanCargarEstanteria> datos = new ArrayList<BeanCargarEstanteria>();
		
		
		datos = ImplementaCargarEstanteria.obtenerDatos();
		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanCargarEstanteria>>(){}.getType());
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
