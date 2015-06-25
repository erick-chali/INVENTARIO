package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanCargarEstanteria;

public class ImplementaCargarEstanteria {
	
	public static ArrayList<BeanCargarEstanteria> obtenerDatos(){
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		ArrayList<BeanCargarEstanteria> listaDatos = new ArrayList<BeanCargarEstanteria>();
		BeanCargarEstanteria bean = new BeanCargarEstanteria();
		try{
			conn = new ConectarDB().getConnection();
			stmt = conn.prepareCall("{call stp_buscaEstanteria}");
			rs = stmt.executeQuery();
			
			if(rs.next()){
				bean.setIdEstanteria(rs.getString("Estanteria_Id"));
				bean.setDescEstanteria(rs.getString("descripcion"));
				listaDatos.add(bean);
			}else{
				bean.setIdEstanteria("");
				bean.setDescEstanteria("No hay etanterias");
				listaDatos.add(bean);
			}
			stmt=null;
			rs=null;
			conn = new ConectarDB().getConnection();
			stmt = conn.prepareCall("{call stp_buscaseccion}");
			rs = stmt.executeQuery();
			
			if(rs.next()){
				bean.setIdEstanteria(rs.getString("Seccion_Id"));
				bean.setDescEstanteria(rs.getString("descripcion"));
				listaDatos.add(bean);
			}else{
				bean.setIdEstanteria("");
				bean.setDescEstanteria("No hay etanterias");
				listaDatos.add(bean);
			}
			
			conn.close();
			stmt=null;
			rs=null;
		}catch(SQLException e){}
		return listaDatos;
	} 
}
