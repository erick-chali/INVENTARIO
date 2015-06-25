package com.millenium.inventario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanCargarTomas;

public class ImplementaCargarTomas {
	public static ArrayList<BeanCargarTomas> cargarTomas(){
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		ArrayList<BeanCargarTomas> tomas = new ArrayList<BeanCargarTomas>();
		
		try{
			conn = new ConectarDB().getConnection();
			stmt = conn.prepareCall("{call stp_Seleccionartomas}");
			rs = stmt.executeQuery();
			if(rs.next()){
				while(rs.next()){
					BeanCargarTomas bean = new BeanCargarTomas();
					bean.setNoToma(rs.getInt("no_toma"));
					bean.setObservaciones(rs.getString("observaciones"));
					tomas.add(bean);
					System.out.println(bean.getNoToma() + " " + bean.getObservaciones());
				}
			}else{
				BeanCargarTomas bean = new BeanCargarTomas();
				bean.setNoToma(0);
				bean.setObservaciones("No hay tomas disponibles");
				tomas.add(bean);
			}
			conn.close();
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return tomas;
	}
}
