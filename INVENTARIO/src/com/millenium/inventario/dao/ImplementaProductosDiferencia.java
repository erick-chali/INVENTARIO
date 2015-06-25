package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanProductosDiferencia;

public class ImplementaProductosDiferencia implements InterfaceProductosDiferencia{
	public static String codigoP, codigoB;
	
	@Override
	public BeanProductosDiferencia cargarProductos(BeanProductosDiferencia obj) {
		codigoP = obj.getCodP();
		codigoB = "07";
		System.out.println(codigoP + " " + codigoB);
		return null;
	}
	
	public static ArrayList<BeanProductosDiferencia> obtenerProductos(){
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		ArrayList<BeanProductosDiferencia> lista = new ArrayList<BeanProductosDiferencia>();
		BeanProductosDiferencia bean = new BeanProductosDiferencia();
		
		try{
			System.out.println("1");
			con = new ConectarDB().getConnection();
			stmt = con.prepareCall("{call stp_cargarProductosDiferencia(?,?)}");
			stmt.setString(1, codigoP);
			System.out.println(codigoP);
			stmt.setString(2, codigoB);
			System.out.println(codigoB);
			System.out.println("2");
			rs = stmt.executeQuery();
			if(rs!=null){
				System.out.println("No esta vacio el rs");
				while(rs.next()){
					System.out.println("No esta vacio");
					bean.setCodP(rs.getString("codigo_producto"));
					bean.setDescP(rs.getString("descripcion_larga"));
					bean.setEstanteria(rs.getString("estanteria_ID"));
					bean.setSeccion(rs.getString("seccion_ID"));
					bean.setBodega(rs.getString("codigo_bodega"));
					bean.setCantidad(rs.getString("conteo2"));
					bean.setUniP(rs.getString("descripcion"));
					lista.add(bean);
				}
			}
				
			System.out.println("4");
			con.close();
			stmt=null;
			rs=null;
		}catch(SQLException e){
			System.out.println("Error" + e.getMessage());
		}
		
		return lista;
	}

}
