package com.millenium.inventario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanBuscaSeccion;

public class ImplementaBuscaSeccion implements InterfaceBuscaSeccion{

	@Override
	public BeanBuscaSeccion buscaSeccion(BeanBuscaSeccion obj) {
		// TODO Auto-generated method stub
		Connection conectar = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BeanBuscaSeccion bean=null;
		try {
			conectar = new ConectarDB().getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("{aqui stored procedure}");
			ps = conectar.prepareCall(sb.toString());
			ps.setString(1, obj.getCodigoSeccion());
			ps.setString(2, obj.getDescripcionSeccion());
			rs = ps.executeQuery();
			if(rs.next()){
				//obtener datos si existe
				bean.setRespuesta(1);
				bean.setCodigoSeccion("1234");
				
			}else{
				obj.setRespuesta(0); 
				return obj;
			}
			ps.close();
			rs.close();
			conectar.close();
		} catch (SQLException sqlez) {
			// TODO: handle exception
			sqlez.printStackTrace();
		}
		
		return bean;
	}
}	
