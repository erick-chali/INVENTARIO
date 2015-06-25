package com.millenium.inventario.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.millenium.inventario.bean.BeanAgregarProducto;
import com.millenium.inventario.dao.ImplementaAgregarProducto;
import com.millenium.inventario.dao.InterfaceAgregarProducto;

/**
 * Servlet implementation class AgregarProducto
 */
public class AgregarProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgregarProducto() {
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
		/*
		 * */
		System.out.println("Entro a servlet");
		BeanAgregarProducto bean = new BeanAgregarProducto();
		InterfaceAgregarProducto iface = new ImplementaAgregarProducto();
		
//		String codigoBodega = request.getParameter("codigoBodega");
//		String codigoSeccion = request.getParameter("seccion");
//		String codigoEstanteria = request.getParameter("estanteria");
//		String codigoProducto = request.getParameter("codigoProducto");
//		String descripcion = request.getParameter("descripcion");
//		String cantidad = request.getParameter("cantidad");
//		String unidad = request.getParameter("unidad");
//		String toma = (String) request.getSession().getAttribute("tomaGlobal");;
//		String usuario = (String) request.getSession().getAttribute("userId");
		
		
		String codigoBodega = request.getParameter("codigob");
		String codigoSeccion = request.getParameter("codigos");
		String codigoEstanteria = request.getParameter("codigoe");
		String codigoProducto = request.getParameter("codigop");
		String descripcion = request.getParameter("descrip");
		String cantidad = request.getParameter("cantidad");
		String unidad = request.getParameter("unidad");
		String toma = (String) request.getSession().getAttribute("tomaGlobal");;
		String usuario = (String) request.getSession().getAttribute("userId");
		
		bean.setCodigoSeccion(codigoSeccion);
		bean.setCodigoEstanteria(codigoEstanteria);
		bean.setCodigoBodega(codigoBodega);
		bean.setCodigoProducto(codigoProducto);
		bean.setDescripProducto(descripcion);
		bean.setCantProducto(cantidad);
		bean.setUnidadMedida(unidad);
		bean.setUsuario(usuario);
		bean.setNoToma(toma);
		System.out.println("se lleno el bean");
		bean = iface.agregarProducto(bean);
		if(bean.getExiste()==1){
			response.setContentType("text/html");
			response.getWriter().print(bean.getNotificacion());
		}else if(bean.getExiste()==0){
			response.setContentType("text/html");
			response.getWriter().print(bean.getNotificacion());
		}
		
	}

}
