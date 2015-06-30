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
import com.millenium.inventario.bean.BeanProductosDiferencia;
import com.millenium.inventario.dao.ImplementaBuscaProducto;
import com.millenium.inventario.dao.ImplementaProductosDiferencia;
import com.millenium.inventario.dao.InterfaceProductosDiferencia;

/**
 * Servlet implementation class CargarProductosDiferencia
 */
public class CargarProductosDiferencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarProductosDiferencia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("entro a productos diferencias");
		BeanProductosDiferencia bean = new BeanProductosDiferencia();
		InterfaceProductosDiferencia iface = new ImplementaProductosDiferencia();
		String codP = request.getParameter("codigoP");
		String codB = request.getParameter("codigoB");
		System.out.println(codP + " " + codB);
		bean.setCodB(codB);
		bean.setCodP(codP);
		
		bean = iface.cargarProductos(bean);
		
		ArrayList<BeanProductosDiferencia> datos = new ArrayList<>();
		
		datos = ImplementaProductosDiferencia.obtenerProductos();
		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanProductosDiferencia>>() {}.getType());
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
