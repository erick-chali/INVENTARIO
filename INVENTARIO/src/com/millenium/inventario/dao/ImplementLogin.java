package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanLogin;


public class ImplementLogin implements InterfaceLogin{
	public static String usuario,pass,noToma;
	@Override
	public BeanLogin validarLogin(BeanLogin obj) {
		// TODO Auto-generated method stub
		usuario = obj.getUsuario();
		pass = obj.getClave();
		noToma = obj.getNoToma();
		Connection conectar = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		BeanLogin bean = new BeanLogin();
		
		try {
			conectar = new ConectarDB().getConnection();
			stmt = conectar.prepareCall("{call looginSelectUser(?)}");
			stmt.setString(1, obj.getUsuario());
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString("estado_empleado").equals("A")){
//					if(!rs.getBoolean("login")){
						if(rs.getString("UserName").equals(usuario)&&rs.getString("Password").equals(pass)){
							bean.setUsuario(rs.getString("UserName"));
							bean.setNoToma(noToma);
							bean.setUserID(rs.getString("UserID"));
							
//							int userId = Integer.parseInt(rs.getString("UserID"));
//							boolean log = true;
//							stmt =null;
//							conectar = new ConectarDB().getConnection();
//							stmt = conectar.prepareCall("{call setLog(?,?)}");
//							stmt.setBoolean(1, log);
//							stmt.setInt(2, userId);
//							stmt.executeUpdate();
//							while(rs.next()){
								bean.setRespuesta(1);
//							}
							
						}else{
							bean.setNotificacion("Usuario o clave incorrectos");
							bean.setRespuesta(0);
						}
//					}else{
//						bean.setNotificacion("El usuario ya tiene una sesion activa, cierrela e intente de nuevo");
//						bean.setRespuesta(0);
//					}
					
				}else{
					bean.setNotificacion("El usuario no tiene permisos para ingresar");
					bean.setRespuesta(0);
				}
			}
			stmt.close();
			rs.close();
			conectar.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
		}
		
		return bean;
	}

}
