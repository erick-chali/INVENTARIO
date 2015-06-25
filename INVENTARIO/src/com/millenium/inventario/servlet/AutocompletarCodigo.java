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
import com.millenium.inventario.bean.BeanBuscaProducto;
import com.millenium.inventario.dao.ImplementaAutoCompletar;
import com.millenium.inventario.dao.ImplementaBuscaProducto;
import com.millenium.inventario.dao.InterfaceAutoCompletar;

/**
 * Servlet implementation class AutocompletarCodigo
 */
public class AutocompletarCodigo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutocompletarCodigo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BeanAutoCompletar bean = new BeanAutoCompletar();
		InterfaceAutoCompletar iface = new ImplementaAutoCompletar();
		
		bean.setCodigoProducto(request.getParameter("codP"));
		bean = iface.obtenerProducto(bean);
		
		ArrayList<BeanAutoCompletar> datos = new ArrayList<BeanAutoCompletar>();
		
		
		datos = ImplementaAutoCompletar.obtenerProductos();
		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanAutoCompletar>>(){}.getType());
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
