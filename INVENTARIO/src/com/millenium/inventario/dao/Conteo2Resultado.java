package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanActualizarProducto;

public class Conteo2Resultado implements InterfazConteo2{
	
	public static int codE,codS;
	@Override
	public BeanActualizarProducto obtenerDatos(BeanActualizarProducto obj) {
		// TODO Auto-generated method stub
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		
		try{
			con = new ConectarDB().getConnection();
			stmt = con.prepareCall("{call stp_obtenerID(?,?,?,?)}");
			stmt.setString(1, obj.getCodigoProducto());
			stmt.setString(2, obj.getCodigoBodega());
			stmt.setString(3, obj.getCodigoEstanteria());
			stmt.setString(4, obj.getCodigoSeccion());
			rs = stmt.executeQuery();
			while(rs.next()){
				codE = rs.getInt("estanteria_ID");
				codS = rs.getInt("seccion_ID");
				obj.setUnidadMedida(rs.getString("unidad_medida"));
			}
			con.close();
			stmt.close();
			rs.close();
			
			con = new ConectarDB().getConnection();
			stmt = con.prepareCall("{call stp_UDinActualizaProducto2(?,?,?,?,?,?,?,?,?)}");
			stmt.setInt(1, Integer.parseInt(obj.getNoToma()));
			stmt.setString(2, obj.getCodigoBodega());
			stmt.setString(3, obj.getCodigoProducto());
			stmt.setString(4, obj.getUnidadMedida());
			stmt.setDouble(5, Double.parseDouble(obj.getCantProducto()));
			stmt.setString(6, "");
			stmt.setInt(7, codS);
			stmt.setInt(8, codE);
			stmt.setInt(9, Integer.parseInt(obj.getUsuario()));
			
			rs = stmt.executeQuery();
			
			obj.setNotificacion("Cantidad Actualizada: Producto" + obj.getCodigoProducto() + 
					" " + obj.getCantProducto());
		}catch(SQLException e){}
		return null;
	}
	

}
