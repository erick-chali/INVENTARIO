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
import com.millenium.inventario.bean.BeanCargarProductos;
import com.millenium.inventario.dao.ImplementaCargarProductos;
import com.millenium.inventario.dao.InterfaceCargarProducto;

/**
 * Servlet implementation class CargarProductos
 */
public class CargarProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarProductos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BeanCargarProductos bean = new BeanCargarProductos();
		InterfaceCargarProducto iface = new ImplementaCargarProductos();
		
		bean.setUsuario((String) request.getSession().getAttribute("userId"));
		System.out.println((String) request.getSession().getAttribute("userId"));
		bean.setNoToma((String) request.getSession().getAttribute("tomaGlobal"));
		System.out.println("Numero Toma" + (String) request.getSession().getAttribute("tomaGlobal"));
		
		bean = iface.cargarProductos(bean);
		
		ArrayList<BeanCargarProductos> datos = new ArrayList<BeanCargarProductos>();
		datos = ImplementaCargarProductos.obtenerProductos();
		
		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanCargarProductos>>(){}.getType());
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
