package com.millenium.inventario.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.millenium.inventario.bean.BeanActualizarProducto;
import com.millenium.inventario.dao.Conteo2Resultado;
import com.millenium.inventario.dao.InterfazConteo2;

/**
 * Servlet implementation class Conteo2
 */
public class Conteo2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Conteo2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String toma = (String) request.getSession().getAttribute("tomaGlobal");
		String userID = (String) request.getSession().getAttribute("userId");
		request.getSession().setAttribute("codp", (String) request.getParameter("codigop"));
		request.getSession().setAttribute("codb", (String) request.getParameter("codigob"));
		request.getSession().setAttribute("code", (String) request.getParameter("codigoe"));
		request.getSession().setAttribute("cods", (String) request.getParameter("codigos"));
		request.getSession().setAttribute("unidad", (String) request.getParameter("unidad"));
		request.getSession().setAttribute("cantidad", (String) request.getParameter("cantidad"));
		
		BeanActualizarProducto bean = null;
		bean = new BeanActualizarProducto();
		bean.setNoToma(toma);
		bean.setCodigoProducto((String) request.getSession().getAttribute("codp"));
		bean.setCodigoBodega((String) request.getSession().getAttribute("codb"));
		bean.setCodigoEstanteria((String) request.getSession().getAttribute("code"));
		bean.setCodigoSeccion((String) request.getSession().getAttribute("cods"));
		bean.setCantProducto((String) request.getSession().getAttribute("cantidad"));
		bean.setUnidadMedida((String) request.getSession().getAttribute("unidad"));
		bean.setUsuario(userID);
		
		InterfazConteo2 interfaz = new Conteo2Resultado();
		bean = interfaz.obtenerDatos(bean);
		
		response.setContentType("text/html");
		response.getWriter().print(Double.parseDouble((String) request.getSession().getAttribute("cantidad")));
		
		
	}

}
