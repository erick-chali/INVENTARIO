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
import com.millenium.inventario.bean.BeanCantidadActual;
import com.millenium.inventario.dao.ImplementaCantidadActual;
import com.millenium.inventario.dao.InterfaceCantidadActual;

/**
 * Servlet implementation class CantidadActual
 */
public class CantidadActual extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CantidadActual() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Entro a cantidad Actual");
		BeanCantidadActual bean = new BeanCantidadActual();
		InterfaceCantidadActual iface = new ImplementaCantidadActual();
		
		bean.setCodigoEstanteria(request.getParameter("codE"));
		bean.setCodigoSeccion(request.getParameter("codS"));
		bean.setCodigoProducto(request.getParameter("codP"));
		bean.setCodigoBodega(request.getParameter("codB"));
		bean.setNoToma((String) request.getSession().getAttribute("tomaGlobal"));
		bean.setConteo("0");
		bean = iface.buscarCantidadActual(bean);
		
		
		ArrayList<BeanCantidadActual> cantidadActual = new ArrayList<BeanCantidadActual>();
		
		cantidadActual = ImplementaCantidadActual.obtenerCantidad();
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(cantidadActual, new TypeToken<List<BeanCantidadActual>>(){}.getType());
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
