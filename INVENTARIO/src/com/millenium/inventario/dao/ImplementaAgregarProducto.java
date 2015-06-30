package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanAgregarProducto;

public class ImplementaAgregarProducto implements InterfaceAgregarProducto{
	public static int toma;
	@Override
	public BeanAgregarProducto agregarProducto(BeanAgregarProducto obj) {
		// TODO Auto-generated method stub
		Connection con = null;
		CallableStatement stmt = null;
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BeanAgregarProducto bean = new BeanAgregarProducto();
		String unidad = "";
		
		ObtenerToma notoma = new ObtenerToma();
		toma = notoma.numToma(obj.getNoToma());
		try {
			con = new ConectarDB().getConnection();
			sb = new StringBuilder();
			sb.append("SELECT unidad_medida FROM in_unidades where descripcion='"+obj.getUnidadMedida()+"';");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				unidad = rs.getString("unidad_medida");
			}
			ps=null;
			rs = null;
			ps = null;
			if(toma==2){
				
				double cant = Double.parseDouble(obj.getCantProducto());
				con = new ConectarDB().getConnection();
				stmt = con.prepareCall("{call stp_UDinActualizaProducto(?,?,?,?,?,?,?,?,?)}");
				stmt.setString(1, obj.getNoToma());
				stmt.setString(2, obj.getCodigoBodega());
				stmt.setString(3, obj.getCodigoProducto());
				stmt.setString(4, unidad);
				stmt.setDouble(5, cant);
				stmt.setString(6, "");
				stmt.setString(7, obj.getCodigoSeccion());
				stmt.setString(8, obj.getCodigoEstanteria());
				stmt.setString(9, obj.getUsuario());
				rs = stmt.executeQuery();
				
				if(rs.next()){
					bean.setExiste(rs.getInt(1));
				}
				bean.setNotificacion("Producto: "+obj.getCodigoProducto()+" Agregado -- Toma: 1");
				//cerrar conexiones
				stmt = null;
				rs = null;
			}else if(toma==3){
				double cant = Double.parseDouble(obj.getCantProducto());
				stmt = con.prepareCall("{call stp_UDinActualizaProducto2(?,?,?,?,?,?,?,?,?)}");
				
				stmt.setString(1, obj.getNoToma());
				
				stmt.setString(2, obj.getCodigoBodega());
				
				stmt.setString(3, obj.getCodigoProducto());
				stmt.setString(4, unidad);
				stmt.setDouble(5, cant);
				stmt.setString(6, "");
				stmt.setString(7, obj.getCodigoSeccion());
				stmt.setString(8, obj.getCodigoEstanteria());
				stmt.setString(9, obj.getUsuario());
				
				rs = stmt.executeQuery();
				if(rs.next()){
					bean.setExiste(rs.getInt(1));
				}
				bean.setNotificacion("Producto: "+obj.getCodigoProducto()+" Agregado -- Toma: 2");
				//cerrar conexiones
				stmt = null;
				rs = null;
				con.close();
			}else{
				bean.setExiste(0);
				bean.setNotificacion("No se pudo agregar el producto a la toma");
			}
			con.close();
		} catch (SQLException sqlex) {
			// TODO: handle exception
			bean.setExiste(0);
			bean.setNotificacion("Error: " + sqlex.getMessage());
			System.out.println("Error Mostrado" + sqlex.getMessage());
		}
		
		return bean;
	}

}
