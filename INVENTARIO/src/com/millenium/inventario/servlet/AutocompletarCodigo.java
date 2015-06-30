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
    private String codp,code,cods,codb,toma;
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
		codp = (String) request.getParameter("codP");
		codb = (String) request.getParameter("codb");
		cods = (String) request.getParameter("cods");
		code = (String) request.getParameter("code");
		toma = (String) request.getParameter("tomaGlobal");
//		BeanAutoCompletar bean = new BeanAutoCompletar();
//		InterfaceAutoCompletar iface = new ImplementaAutoCompletar();
		
//		bean.setCodigoProducto(request.getParameter("codP"));
//		bean.setNoToma(request.getParameter("tomaGlobal"));
//		bean.setCodigoBod(request.getParameter("codb"));
//		bean.setCodigoEst(request.getParameter("code"));
//		bean.setCodigoSec(request.getParameter("cods"));
//		bean = iface.obtenerProducto(bean);
//		ArrayList<BeanAutoCompletar> datos = new ArrayList<BeanAutoCompletar>();
//		datos = ImplementaAutoCompletar.obtenerProductos();
//		
//		Gson gson = new Gson();
//		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanAutoCompletar>>(){}.getType());
//		JsonArray arreglo = elemento.getAsJsonArray();
		response.setContentType("application/json");
		response.getWriter().print(operar(codp,toma,codb,code,cods));
		
	}
	protected JsonArray operar(String codP, String numToma, String codB, String codE, String codS){
		BeanAutoCompletar bean = new BeanAutoCompletar();
		InterfaceAutoCompletar iface = new ImplementaAutoCompletar();
		
		bean.setCodigoProducto(codP);
		bean.setNoToma(numToma);
		bean.setCodigoBod(codB);
		bean.setCodigoSec(codS);
		bean.setCodigoEst(codE);
		bean = iface.obtenerProducto(bean);
		
		ArrayList<BeanAutoCompletar> datos = new ArrayList<BeanAutoCompletar>();
		datos = ImplementaAutoCompletar.obtenerProductos();
		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<BeanAutoCompletar>>(){}.getType());
		JsonArray arreglo = elemento.getAsJsonArray();
		return arreglo;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
