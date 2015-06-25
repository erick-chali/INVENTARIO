package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanBuscaProducto;

public class ImplementaBuscaProducto implements InterfaceBuscaProducto{
	public static String codigoP, codigoCompleto;
	public static String descP;
	@Override
	public BeanBuscaProducto buscarProducto(BeanBuscaProducto obj) {
		// TODO Auto-generated method stub
		codigoP = obj.getCodigoProducto();
		descP = obj.getDescripcionProducto();
		return null;
	}
	public static ArrayList<BeanBuscaProducto> obtenerProductos(){
		Connection conectar = null;
		CallableStatement ps = null;
		ResultSet rs = null;
		ArrayList<BeanBuscaProducto> listaDatos = new ArrayList<BeanBuscaProducto>();
		BeanBuscaProducto bean = new BeanBuscaProducto();
		try{
			conectar = new ConectarDB().getConnection();
			ps = conectar.prepareCall("{call stp_obtenerDigitos(?)}");
			ps.setString(1, codigoP);
			rs = ps.executeQuery();
			if(rs.next()){
				codigoCompleto = rs.getString("Codigo");
			}
			ps=null;
			rs=null;
			System.out.println("Codigo Completo: "+codigoCompleto);
			if(descP.equals("")){
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_CoincidenciasProductoCodigo(?)}");
				ps.setString(1, codigoCompleto);
				rs = ps.executeQuery();
				if(rs!=null){
					System.out.println("Encontro Codigo");
					while(rs.next()){
//						BeanBuscaProducto bean = new BeanBuscaProducto();
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripcionProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("unidad_inventario"));
						bean.setDescripcionUnidad(rs.getString("descripcion"));
						
						listaDatos.add(bean);
					}
					bean.setResultado(1);
					
					
				}else{
					System.out.println("No hay");
				}
				rs=null;
				ps=null;
			}else if(codigoP.equals("")){
				
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_CoincidenciasProductoDescripcion(?)}");
				ps.setString(1, descP);
				rs = ps.executeQuery();
				if(rs!=null){
					System.out.println("Encontro Producto");
					while(rs.next()){
//						BeanBuscaProducto bean = new BeanBuscaProducto();
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripcionProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("unidad_inventario"));
						bean.setDescripcionUnidad(rs.getString("descripcion"));
						
						listaDatos.add(bean);
					}
					bean.setResultado(1);
				}else{
					System.out.println("No hay");
				};
				ps=null;
				rs=null;
			}else if(!descP.equals("") && !codigoP.equals("")){
				
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_CoincidenciasProductoCodigo(?)}");
				ps.setString(1, codigoCompleto);
				rs = ps.executeQuery();
				if(rs!=null){
					System.out.println("Encontro Codigo");
					while(rs.next()){
//						BeanBuscaProducto bean = new BeanBuscaProducto();
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripcionProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("unidad_inventario"));
						bean.setDescripcionUnidad(rs.getString("descripcion"));
						
						listaDatos.add(bean);
					}
					bean.setResultado(1);
					
				}else{
					System.out.println("No hay");
				}
				ps=null;
				rs=null;
			}else if(descP.equals("")&&codigoP.equals("")){
//				BeanBuscaProducto bean = new BeanBuscaProducto();
				bean.setResultado(0);
				bean.setNotificacion("Necesita ingresar un campo");
			}
		}catch(SQLException ex){
			System.out.println("Error: " + ex);
		}
		return listaDatos;
	}

	
}
