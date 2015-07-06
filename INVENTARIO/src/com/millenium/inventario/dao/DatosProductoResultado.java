
package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.apache.openejb.util.Connect;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanAutoCompletar;

@Stateless
public class DatosProductoResultado implements DatosProductoRemoto{
	
	
	public static int estado;
	public DatosProductoResultado() {
		// TODO Auto-generated constructor stub
		
	}
	public static ArrayList<DatosProducto> obtenerDatos(String codigoP, String codigoS, String codigoB, String codigoE) {
		// TODO Auto-generated method stub
		Connection conectar = null;
		CallableStatement ps = null;
		ResultSet rs = null;
		ArrayList<DatosProducto> resultado= new ArrayList<DatosProducto>();
		
		try{
			if(estado == 2){
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_ProductoAutoCompletacion(?,?,?,?)}");
				int codS,codE;
				codS = Integer.parseInt(codigoS);
				codE = Integer.parseInt(codigoE);
				ps.setString(1, codigoP);
				ps.setInt(2, codS);
				ps.setString(3, codigoB);
				ps.setInt(4, codE);
				rs = ps.executeQuery();
				while(rs.next()){
					DatosProducto bean = new DatosProducto();
					System.out.println(rs.getString("conteo1"));
					if(rs.getString("conteo1")!=null){
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad(rs.getString("conteo1"));
						resultado.add(bean);
					}else{
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad("0");
						resultado.add(bean);
					}
					
					
				}

				conectar.close();
				ps.close();
				rs.close();

			}else{
				conectar = new ConectarDB().getConnection();
				ps = conectar.prepareCall("{call stp_ProductoAutoCompletacion(?,?,?,?)}");
				int codS,codE;
				codS = Integer.parseInt(codigoS);
				codE = Integer.parseInt(codigoE);
				ps.setString(1, codigoP);
				ps.setInt(2, codS);
				ps.setString(3, codigoB);
				ps.setInt(4, codE);
				rs = ps.executeQuery();
				while(rs.next()){ 
					DatosProducto bean = new DatosProducto();
					if(rs.getString("conteo2")!=null){
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad(rs.getString("conteo2"));
						resultado.add(bean);
					}else{
						bean.setCodigoProducto(rs.getString("codigo_producto"));
						bean.setDescripProducto(rs.getString("descripcion_larga"));
						bean.setUnidadMedida(rs.getString("descripcion"));
						bean.setCantidad("0");
						resultado.add(bean);
					}
					
					
				}
			}
			conectar.close();
			ps.close();
			rs.close();
			
			
		}catch(SQLException e){}
		return resultado;
	}
@Override
public  DatosProducto obtenerDatosProducto(DatosProducto datos) {
	// TODO Auto-generated method stub
	
	ObtenerToma toma = new ObtenerToma();
	estado = toma.numToma(datos.getNoToma());
	return null;
}

	

}
