package com.millenium.inventario.filtro;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.millenium.db.conectar.ConectarDB;

/**
 * Application Lifecycle Listener implementation class Sesiones
 *
 */
public class Sesiones implements HttpSessionListener {
	private int activas=0;
    /**
     * Default constructor. 
     */
    public Sesiones() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    	synchronized (this) {
			activas++;
		}
    	System.out.println("Sesion Creada: " + event.getSession().getAttribute("usuarioGlobal"));
    	System.out.println("Total de sesiones activas: " + activas);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    	synchronized (this) {
			activas--;
		}
    	int id = Integer.parseInt( (String) event.getSession().getAttribute("userId"));
    	boolean log = false;
    	Connection con = null;
    	CallableStatement stmt = null;
    	System.out.println("Sesion Cerrada: " + event.getSession().getAttribute("usuarioGlobal"));
    	System.out.println("Total de sesiones activas: " + activas);
    	try{
    		con = new ConectarDB().getConnection();
    		stmt = con.prepareCall("{call setLog(?,?)}");
    		stmt.setBoolean(1, log);
    		stmt.setInt(2, id);
    		stmt.executeUpdate();
    		
    	}catch(SQLException e ){
    		System.out.println("Error: " + e.getMessage());
    	}
    	
    }
	
}
