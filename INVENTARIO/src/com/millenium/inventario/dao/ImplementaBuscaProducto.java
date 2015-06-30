package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanBuscaProducto;

public class ImplementaBuscaProducto implements InterfaceBuscaProducto{
	public static String codigoP;
	public static String codBod;
	public static int codEst, codSec, estado;
	public static String descP,noToma;
	@Override
	public BeanBuscaProducto buscarProducto(BeanBuscaProducto obj) {
		// TODO Auto-generated method stub
		codigoP = obj.getCodigoProducto();
		descP = obj.getDescripcionProducto();
		codBod = obj.getCodB();
		codEst = Integer.parseInt( (String) obj.getCodE() );
		codSec = Integer.parseInt( (String) obj.getCodS() );
		ObtenerToma toma = new ObtenerToma();
		estado = toma.numToma((String) obj.getNoToma());
		System.out.println(codBod + " " + codEst + " " + codSec);
		return null;
	}
	public static ArrayList<BeanBuscaProducto> obtenerProductos(){
		Connection conectar = null;
		CallableStatement ps = null;
		ResultSet rs = null;
		ArrayList<BeanBuscaProducto> listaDatos = new ArrayList<>();
		
		try{
			if(descP.equals("")){
				if(estado==2){
					BeanBuscaProducto bean = new BeanBuscaProducto();
					conectar = new ConectarDB().getConnection();
					ps = conectar.prepareCall("{call stp_CoincidenciasProductoCodigo(?,?,?,?)}");
					ps.setString(1, codigoP);
					ps.setString(2, codBod);
					ps.setInt(3, codEst);
					ps.setInt(4, codSec);
					rs = ps.executeQuery();
						while(rs.next()){
							if(rs.getString("conteo1")!=null){
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad(rs.getString("conteo1"));
								listaDatos.add(bean);
							}else{
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad("0");
								listaDatos.add(bean);
							}
							
						}
						bean.setResultado(1);
						
					conectar.close();
					ps.close();
					rs.close();
				}else{
					BeanBuscaProducto bean = new BeanBuscaProducto();
					conectar = new ConectarDB().getConnection();
					ps = conectar.prepareCall("{call stp_CoincidenciasProductoCodigo(?,?,?,?)}");
					ps.setString(1, codigoP);
					ps.setString(2, codBod);
					ps.setInt(3, codEst);
					ps.setInt(4, codSec);
					rs = ps.executeQuery();
						while(rs.next()){
							if(rs.getString("conteo2")!=null){
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad(rs.getString("conteo2"));
								listaDatos.add(bean);
							}else{
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad("0");
								listaDatos.add(bean);
							}
							
						}
						bean.setResultado(1);
						
						conectar.close();
						ps.close();
						rs.close();
				}
				
			}else if(codigoP.equals("")){
				if(estado==2){
					
					conectar = new ConectarDB().getConnection();
					ps = conectar.prepareCall("{call stp_CoincidenciasProductoDescripcion(?,?,?,?)}");
					ps.setString(1, descP);
					ps.setString(2, codBod);
					ps.setInt(3, codEst);
					ps.setInt(4, codSec);
					rs = ps.executeQuery();
						while(rs.next()){
							BeanBuscaProducto bean = new BeanBuscaProducto();
//							if(rs.getString("conteo1")!=null){
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad(rs.getString("conteo1"));
								System.out.println(bean.getCodigoProducto() + " " + bean.getDescripcionProducto()
										+ " " + bean.getCantidad()
										);
								listaDatos.add(bean);
								
//							}else{
//								bean.setCodigoProducto(rs.getString("codigo_producto"));
//								
//								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
//								bean.setUnidadMedida(rs.getString("unidad_inventario"));
//								bean.setDescripcionUnidad(rs.getString("descripcion"));
//								bean.setCantidad("0");
//								System.out.println(bean.getCodigoProducto() + " " + bean.getDescripcionProducto()
//										+ " " + bean.getCantidad()
//										);
//								listaDatos.add(bean);
//							}
						}
						
						conectar.close();
						ps.close();
						rs.close();
				}else if(estado==3){
					
					conectar = new ConectarDB().getConnection();
					ps = conectar.prepareCall("{call stp_CoincidenciasProductoDescripcion(?,?,?,?)}");
					ps.setString(1, descP);
					ps.setString(2, codBod);
					ps.setInt(3, codEst);
					ps.setInt(4, codSec);
					rs = ps.executeQuery();
						while(rs.next()){
							BeanBuscaProducto bean = new BeanBuscaProducto();
							if(rs.getString("conteo2")!=null){
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad(rs.getString("conteo2"));
								System.out.println(bean.getCodigoProducto() + " " + bean.getDescripcionProducto()
										+ " " + bean.getCantidad()
										);
								listaDatos.add(bean);
							}else{
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad("0");
								System.out.println(bean.getCodigoProducto() + " " + bean.getDescripcionProducto()
										+ " " + bean.getCantidad()
										);
								listaDatos.add(bean);
							}
							
						}
						
						conectar.close();
						ps.close();
						rs.close();
				}
				
			}else if(!descP.equals("") && !codigoP.equals("")){
				
				if(estado==2){
					BeanBuscaProducto bean = new BeanBuscaProducto();
					conectar = new ConectarDB().getConnection();
					ps = conectar.prepareCall("{call stp_CoincidenciasProductoCodigo(?,?,?,?)}");
					ps.setString(1, codigoP);
					ps.setString(2, codBod);
					ps.setInt(3, codEst);
					ps.setInt(4, codSec);
					rs = ps.executeQuery();
						while(rs.next()){
							if(rs.getString("conteo1")!=null){
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad(rs.getString("conteo1"));
								listaDatos.add(bean);
							}else{
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad("0");
								listaDatos.add(bean);
							}
							
						}
						bean.setResultado(1);
						
						conectar.close();
						ps.close();
						rs.close();
				}else{
					BeanBuscaProducto bean = new BeanBuscaProducto();
					conectar = new ConectarDB().getConnection();
					ps = conectar.prepareCall("{call stp_CoincidenciasProductoCodigo(?,?,?,?)}");
					ps.setString(1, codigoP);
					ps.setString(2, codBod);
					ps.setInt(3, codEst);
					ps.setInt(4, codSec);
					rs = ps.executeQuery();
						while(rs.next()){
							if(rs.getString("conteo2")!=null){
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad(rs.getString("conteo2"));
								
								listaDatos.add(bean);
							}else{
								bean.setCodigoProducto(rs.getString("codigo_producto"));
								bean.setDescripcionProducto(rs.getString("descripcion_larga"));
								bean.setUnidadMedida(rs.getString("unidad_inventario"));
								bean.setDescripcionUnidad(rs.getString("descripcion"));
								bean.setCantidad("0");
								listaDatos.add(bean);
							}
							
						}
						bean.setResultado(1);
						
						conectar.close();
						ps.close();
						rs.close();
				}
			}else if(descP.equals("")&&codigoP.equals("")){
				BeanBuscaProducto bean = new BeanBuscaProducto();
				bean.setResultado(0);
				bean.setNotificacion("Necesita ingresar un campo");
			}
		}catch(SQLException ex){
			System.out.println("Error: " + ex.getMessage());
		}
		return listaDatos;
	}

	
}
