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
import com.millenium.inventario.dao.InterfaceBuscaProducto;

/**
 * Servlet implementation class BuscaProducto
 */
public class BuscaProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscaProducto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BeanBuscaProducto bean = new BeanBuscaProducto();
		InterfaceBuscaProducto iface = new ImplementaBuscaProducto();
		String codP = request.getParameter("codigo");
		String descP = request.getParameter("desc");
		System.out.println("Producto: " + codP + " Descripcion" + descP);
		bean.setCodigoProducto(codP);
		bean.setDescripcionProducto(descP);
		
		bean = iface.buscarProducto(bean);
		
//		if(bean.getResultado()==1){
			ArrayList<BeanBuscaProducto> datos = new ArrayList<BeanBuscaProducto>();
			
			
			datos = ImplementaBuscaProducto.obtenerProductos();
			
			Gson gson = new Gson();
			JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanBuscaProducto>>(){}.getType());
			JsonArray arreglo = elemento.getAsJsonArray();
			response.setContentType("application/json");
			response.getWriter().print(arreglo);
//		}else if(bean.getResultado()==0){
//			response.getWriter().print(bean.getNotificacion());
//		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
