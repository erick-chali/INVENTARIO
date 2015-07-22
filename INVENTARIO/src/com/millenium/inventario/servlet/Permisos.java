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
import com.millenium.inventario.bean.ParamPermiso;
import com.millenium.inventario.dao.ImplementaPermisos;
import com.millenium.inventario.dao.InterfazPermisos;

/**
 * Servlet implementation class Permisos
 */
public class Permisos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Permisos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Entro a permisos");
		request.getSession().setAttribute("op", (String) request.getParameter("opcion"));
		InterfazPermisos interfaz = new ImplementaPermisos();
		ParamPermiso parametros = null;
		parametros = new ParamPermiso();
		parametros.setPermiso((String) request.getSession().getAttribute("op"));
		parametros.setUserID((String) request.getSession().getAttribute("userId"));
		
		parametros = interfaz.permisos(parametros);
		ArrayList<ParamPermiso> permisos = null;
		permisos = new ArrayList<ParamPermiso>();
		
		permisos = ImplementaPermisos.obtenerPermisos();
		
		Gson gson = new Gson();
		JsonElement elemento = gson.toJsonTree(permisos, new TypeToken<List<ParamPermiso>>(){}.getType());
		JsonArray arreglo = elemento.getAsJsonArray();
		parametros = null;
		permisos = null;
		request.getSession().removeAttribute("op");
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
