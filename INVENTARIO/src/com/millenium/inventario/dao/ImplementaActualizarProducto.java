package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanActualizarProducto;

public class ImplementaActualizarProducto implements InterfaceActualizarProducto{
	public static int toma;
	@Override
	public BeanActualizarProducto actualizar(BeanActualizarProducto obj) {
		Connection con = null;
		CallableStatement stmt = null;
		PreparedStatement ps = null;
		StringBuilder sb = null;
		ResultSet rs = null;
		BeanActualizarProducto bean = new BeanActualizarProducto();
		String estToma = "";
		
		String unidad = "";
		
		try {
//			con = new ConectarDB().getConnection();
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT estado_toma from in_tomafisica_enc where no_toma='"+obj.getNoToma()+"';");
//			ps = con.prepareStatement(sb.toString());
//			rs = ps.executeQuery();
//			if(rs.next()){
//				estToma =rs.getString("estado_toma");
//				toma = Integer.parseInt(estToma);
//			}else{
//				System.out.println("no existe");
//			}
			ObtenerToma tom = new ObtenerToma();
			toma=tom.numToma(obj.getNoToma());
			ps= null;
			rs=null;
			sb = null;
			
			con = new ConectarDB().getConnection();
			sb = new StringBuilder();
			sb.append("SELECT unidad_medida FROM in_unidades where descripcion='"+obj.getUnidadMedida()+"';");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				unidad = rs.getString("unidad_medida");
			}else{
			}
			ps=null;
			rs = null;
			ps = null;
//			boolean existente = false;
//			con = new ConectarDB().getConnection();
//			stmt = con.prepareCall("{call stp_verficarProductoUsuario(?,?)}");
//			stmt.setString(1, obj.getCodigoProducto());
//			stmt.setString(2, obj.getUsuario());
//			rs = stmt.executeQuery();
//			if(rs.next()){existente=true;}else{existente=false;}
//			rs=null;
//			stmt=null;
//			if(existente){
				if(toma==2){
					stmt=null;
					bean.setExiste(1);
					con = new ConectarDB().getConnection();
					int noToma = Integer.parseInt(obj.getNoToma());
					double cantidad = Double.parseDouble(obj.getCantProducto());
					int codS = Integer.parseInt(obj.getCodigoSeccion());
					int codE = Integer.parseInt(obj.getCodigoEstanteria());
					int codU = Integer.parseInt(obj.getUsuario());
					stmt = con.prepareCall("{call stp_UDinActualizaProducto(?,?,?,?,?,?,?,?,?)}");
					stmt.setInt(1, noToma);
					stmt.setString(2, obj.getCodigoBodega());
					stmt.setString(3, obj.getCodigoProducto());
					stmt.setString(4, unidad);
					stmt.setDouble(5, cantidad);
					stmt.setString(6, "");
					stmt.setInt(7, codS);
					stmt.setInt(8, codE);
					stmt.setInt(9, codU);
					rs = stmt.executeQuery();
					System.out.println("Producto Actualizado estado-2");
					bean.setNotificacion("Producto Actualizado");
					stmt=null;
					rs = null;
					con.close();
				}else if(toma==3){
					stmt=null;
					bean.setExiste(1);
					con = new ConectarDB().getConnection();
					int noToma = Integer.parseInt(obj.getNoToma());
					double cantidad = Double.parseDouble(obj.getCantProducto());
					int codS = Integer.parseInt(obj.getCodigoSeccion());
					int codE = Integer.parseInt(obj.getCodigoEstanteria());
					int codU = Integer.parseInt(obj.getUsuario());
					stmt = con.prepareCall("{call stp_UDinActualizaProducto2(?,?,?,?,?,?,?,?,?)}");
					stmt.setInt(1, noToma);
					stmt.setString(2, obj.getCodigoBodega());
					stmt.setString(3, obj.getCodigoProducto());
					stmt.setString(4, unidad);
					stmt.setDouble(5, cantidad);
					stmt.setString(6, "");
					stmt.setInt(7, codS);
					stmt.setInt(8, codE);
					stmt.setInt(9, codU);
					stmt.executeQuery();
					bean.setNotificacion("Producto Actualizado Toma: 2");
					System.out.println("Producto Actualizado estado-3");
					//cerrar conexiones
					stmt=null;
					rs = null;
					rs = null;
					con.close();
				}else{
					rs=null;
					ps=null;
					con.close();
					bean.setNotificacion("El estado de la Toma es el incorrecto");
				}
//			}else{
//				bean.setExiste(0);
//				bean.setNotificacion("No puede editar un producto que su usuario no ha ingresado");
//			}
		} catch (SQLException sqlex) {
			// TODO: handle exception
			System.out.println("Error: " + sqlex.getMessage());
			bean.setNotificacion("Error: " + sqlex.getMessage());
		}
		
		return bean;
	}
	
}
