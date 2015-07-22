package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.ParamPermiso;

public class ImplementaPermisos implements InterfazPermisos{
	public static String op,usrID; 
	@Override
	public ParamPermiso permisos(ParamPermiso obj) {
		// TODO Auto-generated method stub
		op = obj.getPermiso();
		usrID = obj.getUserID();
		return null;
	}
	public static ArrayList<ParamPermiso> obtenerPermisos(){
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		
		ArrayList<ParamPermiso> permisos = new ArrayList<ParamPermiso>();
		
		try{
			/**parametro 1*/
			con = new ConectarDB().getConnection();
			stmt = con.prepareCall("{call _OpcionPermisos(?,?)}");
			stmt.setString(1, op);
			stmt.setInt(2, Integer.parseInt(usrID));
			System.out.println(op + " " + usrID);
			rs = stmt.executeQuery();
			while(rs.next()){
				ParamPermiso param = new ParamPermiso();
				param.setPermiso(rs.getString("ejecutar"));
				System.out.println(rs.getString("ejecutar"));
				permisos.add(param);
			}
			con.close();
			stmt.close();
			rs.close();
		}catch(SQLException e){
			System.out.println("Error: " + e.getMessage());
		}
		return permisos;
	}

	
}
