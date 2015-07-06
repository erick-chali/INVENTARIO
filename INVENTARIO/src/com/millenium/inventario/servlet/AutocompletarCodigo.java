package com.millenium.inventario.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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
import com.millenium.inventario.dao.DatosProducto;
import com.millenium.inventario.dao.DatosProductoRemoto;
import com.millenium.inventario.dao.DatosProductoResultado;
import com.millenium.inventario.dao.ImplementaAutoCompletar;
import com.millenium.inventario.dao.ImplementaBuscaProducto;
import com.millenium.inventario.dao.InterfaceAutoCompletar;

/**
 * Servlet implementation class AutocompletarCodigo
 */
public class AutocompletarCodigo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private String codp,codb,toma,code,cods;
//    private int code,cods;
    @EJB
    static DatosProductoResultado miBean;
    public AutocompletarCodigo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		codp = (String) request.getParameter("codP");
//		codb = (String) request.getParameter("codb");
//		cods = ((String) request.getParameter("cods"));
//		code = ((String) request.getParameter("code"));
		toma = (String) request.getSession().getAttribute("tomaGlobal");
		
		request.getSession().setAttribute("codp", request.getParameter("codP"));
		request.getSession().setAttribute("codb", request.getParameter("codb"));
		request.getSession().setAttribute("cods", request.getParameter("cods"));
		request.getSession().setAttribute("code", request.getParameter("code"));
		DatosProducto bean = null;
		bean = new DatosProducto();
		bean.setNoToma(toma);
		
		DatosProductoRemoto remoto = new DatosProductoResultado();
		bean = new DatosProducto();
		bean = remoto.obtenerDatosProducto(bean);
		
		ArrayList<DatosProducto> datos = null;
		datos = new ArrayList<DatosProducto>();
		datos = DatosProductoResultado.obtenerDatos(
				(String) request.getSession().getAttribute("codp"), 
				(String) request.getSession().getAttribute("cods"), 
				(String) request.getSession().getAttribute("codb"), 
				(String) request.getSession().getAttribute("code")
				);
//		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(datos, new TypeToken<List<DatosProducto>>(){}.getType());
		JsonArray arreglo = elemento.getAsJsonArray();
		bean = null;
		datos = null;
		response.setContentType("application/json");
		
		response.getWriter().print(arreglo);
		
	}

}
