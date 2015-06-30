package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanBuscaSeccion;

public class ImplementaBuscaSeccion implements InterfaceBuscaSeccion{
	public static int estID;
	
	@Override
	public BeanBuscaSeccion buscaSeccion(BeanBuscaSeccion obj) {
		estID = Integer.parseInt((String) obj.getEstanteriaID());
		System.out.println("Codigo de estanteria seleccionada: " + estID);
		return null;
	}
	public static ArrayList<BeanBuscaSeccion> obtenerSecciones(){
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		ArrayList<BeanBuscaSeccion> secciones = new ArrayList<BeanBuscaSeccion>();
		
		
		try{
			con = new ConectarDB().getConnection();
			stmt = con.prepareCall("{call stp_buscaseccion(?)}");
			stmt.setInt(1, estID);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				BeanBuscaSeccion bean = new BeanBuscaSeccion();
				bean.setCodigoS(rs.getString("Seccion_Id"));
				bean.setDescS(rs.getString("descripcion"));
				secciones.add(bean);
			}
		}catch(SQLException e){
			System.out.println("Error: " + e.getMessage());
		}
		return secciones;
	}
}	
