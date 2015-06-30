package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanProductosDiferencia;

public class ImplementaProductosDiferencia implements InterfaceProductosDiferencia{
	public static String codigoP, codigoB;
	
	@Override
	public BeanProductosDiferencia cargarProductos(BeanProductosDiferencia obj) {
		codigoP = obj.getCodP();
		codigoB = obj.getCodB();
		System.out.println(codigoP + " " + codigoB);
		return null;
	}
	
	public static ArrayList<BeanProductosDiferencia> obtenerProductos(){
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		ArrayList<BeanProductosDiferencia> lista = new ArrayList<>();
		
		
		
		try{
			
			con = new ConectarDB().getConnection();
			stmt = con.prepareCall("{call stp_cargarProductosDiferencia(?,?)}");
			stmt.setString(1, codigoP);
			stmt.setString(2, codigoB);
			rs = stmt.executeQuery();
//			if(rs!=null){
				while(rs.next()){
//					if(rs.getString("conteo2")!=null){
						BeanProductosDiferencia bean = new BeanProductosDiferencia();
						bean.setCodP(rs.getString("codigo_producto"));
						System.out.println(rs.getString("codigo_producto"));
						bean.setDescP(rs.getString("descripcion_larga"));
						bean.setEstanteria(rs.getString("estanteria_ID"));
						System.out.println(rs.getString("estanteria_ID"));
						bean.setSeccion(rs.getString("seccion_ID"));
						System.out.println(rs.getString("seccion_ID"));
						bean.setBodega(rs.getString("codigo_bodega"));
						bean.setCantidad(rs.getString("conteo2"));
						bean.setUniP(rs.getString("descripcion"));
						lista.add(bean);
//					}
					
					
				}
//			}
			
			con.close();
			stmt=null;
			rs=null;
		}catch(SQLException e){
			System.out.println("Error" + e.getMessage());
		}
		
		return lista;
	}

}
