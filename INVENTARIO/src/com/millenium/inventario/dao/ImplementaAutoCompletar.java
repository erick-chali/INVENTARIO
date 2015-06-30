package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanAutoCompletar;

public class ImplementaAutoCompletar implements InterfaceAutoCompletar{
	public static String codigo, nuevoCodigo, noToma,codB;
	public static int estado,codE,codS;
	@Override
	public BeanAutoCompletar obtenerProducto(BeanAutoCompletar obj) {
		// TODO Auto-generated method stub
		codigo  = obj.getCodigoProducto();
		noToma = obj.getNoToma();
		codB = obj.getCodigoBod();
		codE = Integer.parseInt( (String) obj.getCodigoEst());
		codS = Integer.parseInt( (String) obj.getCodigoSec());
		
		ObtenerToma toma = new ObtenerToma();
		estado = toma.numToma(noToma);
		return null;
	}
	public static ArrayList<BeanAutoCompletar> obtenerProductos(){
		Connection conectar = null;
		CallableStatement ps = null;
		ResultSet rs = null;
		ArrayList<BeanAutoCompletar> listaCodigo = new ArrayList<BeanAutoCompletar>();
		BeanAutoCompletar bean = new BeanAutoCompletar();
		try{
			
			if(estado == 2){
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_ProductoAutoCompletacion(?,?,?,?)}");
				ps.setString(1, codigo);
				ps.setInt(2, codS);
				ps.setString(3, codB);
				ps.setInt(4, codE);
				rs = ps.executeQuery();
				while(rs.next()){
					System.out.println(rs.getString("conteo1"));
					if(rs.getString("conteo1")!=null){
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad(rs.getString("conteo1"));
						listaCodigo.add(bean);
					}else{
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad("0");
						listaCodigo.add(bean);
					}
					
					
				}

				conectar.close();
				ps.close();
				rs.close();

			}else{
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_ProductoAutoCompletacion(?,?,?,?)}");
				ps.setString(1, codigo);
				ps.setInt(2, codS);
				ps.setString(3, codB);
				ps.setInt(4, codE);
				rs = ps.executeQuery();
				while(rs.next()){
					if(rs.getString("conteo2")!=null){
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad(rs.getString("conteo2"));
						listaCodigo.add(bean);
					}else{
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad("0");
						listaCodigo.add(bean);
					}
					
					
				}
			}
		}catch(SQLException ex){
			System.out.println("Error: " + ex);
		}
		return listaCodigo;
	}
}
