package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanAutoCompletar;

public class ImplementaAutoCompletar implements InterfaceAutoCompletar{
	public static String codigo, nuevoCodigo;
	@Override
	public BeanAutoCompletar obtenerProducto(BeanAutoCompletar obj) {
		// TODO Auto-generated method stub
		codigo  = obj.getCodigoProducto();
		return null;
	}
	public static ArrayList<BeanAutoCompletar> obtenerProductos(){
		Connection conectar = null;
		CallableStatement ps = null;
		ResultSet rs = null;
		ArrayList<BeanAutoCompletar> listaCodigo = new ArrayList<BeanAutoCompletar>();
		
		try{
			conectar = new ConectarDB().getConnection();
//			sb.append("SELECT TOP 1000 tf.codigo_producto, inp.descripcion_larga FROM [in_tomafisica_det] AS tf INNER JOIN [in_productos] AS inp ON tf.codigo_producto = inp.codigo_producto");
			
			ps = conectar.prepareCall("{call stp_obtenerDigitos(?)}");
			ps.setString(1, codigo);
			rs = ps.executeQuery();
			if(rs.next()){
				nuevoCodigo = rs.getString("Codigo");
				System.out.println(nuevoCodigo);
			}
			
			conectar.close();
			ps.close();
			rs.close();
			
			conectar = null;
			ps= null;
			rs = null;
			conectar = new ConectarDB().getConnection();
			
			
			ps = conectar.prepareCall("{call stp_ProductoAutoCompletacion(?)}");
			ps.setString(1, nuevoCodigo);
			rs = ps.executeQuery();
			if(rs.next()){
				BeanAutoCompletar bean = new BeanAutoCompletar();
				bean.setCodigoProducto(rs.getString("codigo_producto"));
				bean.setDescripProducto(rs.getString("descripcion_larga"));
				bean.setUnidadMedida(rs.getString("descripcion"));
				listaCodigo.add(bean);
				System.out.println(bean.getCodigoProducto() + " " + bean.getUnidadMedida() + " " + bean.getDescripProducto());
				
			}else{
				BeanAutoCompletar bean = new BeanAutoCompletar();
				bean.setCodigoProducto("000000");
				bean.setDescripProducto("No existe producto");
				bean.setUnidadMedida("No existe producto");
				listaCodigo.add(bean);
			}

			conectar.close();
			ps.close();
			rs.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return listaCodigo;
	}
}
