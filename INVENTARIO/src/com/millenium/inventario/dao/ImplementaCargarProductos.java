package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanCargarProductos;

public class ImplementaCargarProductos implements InterfaceCargarProducto{
	public static String usuario,toma;
	public static int estado;
	
	@Override
	public BeanCargarProductos cargarProductos(BeanCargarProductos obj) {
		// TODO Auto-generated method stub
		usuario = obj.getUsuario();
		toma = obj.getNoToma();
		ObtenerToma tom = new ObtenerToma();
		estado = tom.numToma(toma);
		return null;
	}
	public static ArrayList<BeanCargarProductos> obtenerProductos(){
		Connection conectar = null;
		CallableStatement ps = null;
//		StringBuilder sb = null;
//		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<BeanCargarProductos> productosUsuario = new ArrayList<BeanCargarProductos>();
		
		
		try{
//			conectar = new ConectarDB().getConnection();
//			sb = new StringBuilder();
//			sb.append("SELECT estado_toma from in_tomafisica_enc where no_toma='"+toma+"';");
//			st = conectar.prepareStatement(sb.toString());
//			rs = st.executeQuery();
//			if(rs.next()){
//				estado = rs.getInt("estado_toma");
//			}
//			conectar=null;
//			sb=null;
//			st=null;
//			rs=null;
			System.out.println("Estado de toma actual: " + estado);
			if(estado==2){
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_ConsultaProductos(?,?)}");
				ps.setString(1, usuario);
				ps.setString(2, toma);
				rs = ps.executeQuery();
//				if(rs.next()){
					while(rs.next()){
						if(rs.getString("conteo1")!=null){
							BeanCargarProductos bean = new BeanCargarProductos();
							bean.setCodP(rs.getString("codigo_producto"));
							bean.setDescP(rs.getString("descripcion_larga"));
							bean.setCodB(rs.getString("codigo_bodega"));
							bean.setCodE(rs.getString("estanteria_ID"));
							bean.setCodS(rs.getString("seccion_ID"));
							bean.setConteo1(rs.getString("conteo1"));
							bean.setDescB(rs.getString("bodega"));
							bean.setDescE(rs.getString("estanteria"));
							bean.setDescS(rs.getString("seccion"));
							bean.setUnidad(rs.getString("unidad"));
							productosUsuario.add(bean);
						}
					}
					conectar.close();
					ps.close();
					rs.close();
//				}else{
//					System.out.println("No hay");
//				}
			}else if(estado==3){
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_ConsultaProductos(?,?)}");
				ps.setString(1, usuario);
				ps.setString(2, toma);
				rs = ps.executeQuery();
//				if(rs.next()){
					while(rs.next()){
						if(rs.getString("conteo2")!=null){
							BeanCargarProductos bean = new BeanCargarProductos();
							bean.setCodP(rs.getString("codigo_producto"));
							bean.setDescP(rs.getString("descripcion_larga"));
							bean.setCodB(rs.getString("codigo_bodega"));
							bean.setCodE(rs.getString("estanteria_ID"));
							bean.setCodS(rs.getString("seccion_ID"));
							bean.setConteo1(rs.getString("conteo2"));
							bean.setDescB(rs.getString("bodega"));
							bean.setDescE(rs.getString("estanteria"));
							bean.setDescS(rs.getString("seccion"));
							bean.setUnidad(rs.getString("unidad"));
							productosUsuario.add(bean);
						}
						
					}
					conectar.close();
					ps.close();
					rs.close();
//				}else{
//					System.out.println("No hay");
//				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return productosUsuario;
	}

	
}
