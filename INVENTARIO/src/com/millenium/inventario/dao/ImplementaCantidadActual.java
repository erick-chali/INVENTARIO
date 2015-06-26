package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanCantidadActual;

public class ImplementaCantidadActual implements InterfaceCantidadActual{

	public static String codP,toma;
	public static String codE,codB,codS,temp,codCompleto;
	public static int estado;
	@Override
	public BeanCantidadActual buscarCantidadActual(BeanCantidadActual obj) {
		// TODO Auto-generated method stub
		codE = (obj.getCodigoEstanteria());
		codS = (obj.getCodigoSeccion());
		codB = (obj.getCodigoBodega());
		codP = obj.getCodigoProducto();
		ObtenerToma toma = new ObtenerToma();
		estado = toma.numToma(obj.getNoToma());
		return null;
		
	}
	public static ArrayList<BeanCantidadActual> obtenerCantidad(){
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		ArrayList<BeanCantidadActual> lista = new ArrayList<BeanCantidadActual>();
		BeanCantidadActual bean = new BeanCantidadActual();
		
		try{
			conn = new ConectarDB().getConnection();
			stmt = conn.prepareCall("{call stp_obtenerDigitos(?)}");
			stmt.setString(1, codP);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				codCompleto = rs.getString("Codigo");
			}
			stmt=null;
			rs=null;
			conn =null;
			if(estado==2){
				System.out.println("Buscando cantidad en estado 2");
				conn = new ConectarDB().getConnection();
				stmt = conn.prepareCall("{call stp_CantidadProductoActual(?,?,?,?)}");
				stmt.setString(1, codCompleto);
				System.out.println(codCompleto);
				stmt.setString(2, codS);
				System.out.println(codS);
				stmt.setString(3, codE);
				System.out.println(codE);
				stmt.setString(4, codB);
				System.out.println(codB);
				rs = stmt.executeQuery();
				
//				if(rs!=null){
					bean.setConteo("0");
					lista.add(bean);
					while(rs.next()){
//						BeanCantidadActual bean = new BeanCantidadActual();
						String cantidad = rs.getString("conteo1");
						if(rs.getString("conte1")!=null){
							bean.setConteo(cantidad);
							System.out.println("Encontro cantidad: " + cantidad);
							lista.add(bean);
						}else{
							bean.setConteo("0");
							System.out.println("Encontro cantidad: " + cantidad);
							lista.add(bean);
						}
					}
					
//				}else{
//					BeanCantidadActual bean = new BeanCantidadActual();
//					String cantidad = "0";
//					bean.setConteo(cantidad);
//					System.out.println("Encontro cantidad: " + cantidad);
//					lista.add(bean);
//				}
					
					
				rs=null;
				stmt=null;
			}else if(estado==3){
				System.out.println("Buscando cantidad en estado 3");
				conn = new ConectarDB().getConnection();
				stmt = conn.prepareCall("{call stp_CantidadProductoActual(?,?,?,?)}");
				stmt.setString(1, codCompleto);
				stmt.setString(2, codS);
				stmt.setString(3, codE);
				stmt.setString(4, codB);
				rs = stmt.executeQuery();
				
//				if(rs!=null){
				bean.setConteo("0");
				lista.add(bean);
					while(rs.next()){
//						BeanCantidadActual bean = new BeanCantidadActual();
						String cantidad = rs.getString("conteo2");
						if(rs.getString("conteo2")!=null){
							bean.setConteo(cantidad);
							System.out.println("Encontro cantidad " + cantidad);
							lista.add(bean);
						}else{
							bean.setConteo("0");
							System.out.println("Encontro cantidad " + cantidad);
							lista.add(bean);
						}
					}
//				}else{
////					BeanCantidadActual bean = new BeanCantidadActual();
//					
//					lista.add(bean);
//				}
				
				stmt=null;
				rs=null;
			}
			conn.close();
		}catch(SQLException e){
			System.out.println("Error: " + e);
		}
		return lista;
	}
}
