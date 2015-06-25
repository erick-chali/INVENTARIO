package com.millenium.inventario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.millenium.db.conectar.ConectarDB;
import com.millenium.inventario.bean.BeanBuscaEstanteria;

public class ImplementaBuscaEstanteria implements InterfaceBuscaEstanteria{
	public static String idEstanteria;
	@Override
	public BeanBuscaEstanteria buscaEstanteria(BeanBuscaEstanteria obj) {
		// TODO Auto-generated method stub
		idEstanteria = obj.getCodigoEstanteria();
		return null;
	}
	public static ArrayList<BeanBuscaEstanteria> obtenerDatos(){
		Connection conectar = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<BeanBuscaEstanteria> listaDatos = new ArrayList<BeanBuscaEstanteria>();
		
		try{
			conectar = new ConectarDB().getConnection();
			sb.append("SELECT * FROM in_productos");
			ps = conectar.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					BeanBuscaEstanteria bean = new BeanBuscaEstanteria();
					bean.setCodigoProd(rs.getString("codigo_producto"));
					bean.setDescProd(rs.getString("descripcion_larga"));
//					bean.setCodMarca(rs.getString("codigo_marca"));
//					bean.setCodFamilia(rs.getString("codigo_familia"));
					listaDatos.add(bean);
//					System.out.println(bean.getCodigoProd() + " " + bean.getDescProd() + " " + bean.getCodMarca() + " " + bean.getCodFamilia());
				}
				conectar.close();
				ps.close();
				rs.close();
				sb=null;
			}else{
				System.out.println("No hay");
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return listaDatos;
	}

	
}
