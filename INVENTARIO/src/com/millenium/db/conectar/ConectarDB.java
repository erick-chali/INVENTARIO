package com.millenium.db.conectar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarDB {
	
	public Connection getConnection(){
//		leer UDL
		BufferedReader reader=null;
		String[] datos = new String[14];
		String[] clave = new String[2];
		String[] usuario = new String[2];
		String[] db = new String[2];
		String[] servidor = new String[2];
		String pass = "", user = "", bd = "", server = "";
		File direccion = new File("c:\\INVENTARIO\\millenium.udl");
		try {
			try {
				reader = new BufferedReader(
							new InputStreamReader(new FileInputStream(direccion), "UTF-16"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			reader = new BufferedReader(
//					new FileReader(
//				"C:/millenium.udl"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		// Read lines from file.
		while (true) {
		    String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if (line == null) {
			break;
		    }
		    // Split line on comma.
		    String[] parts = line.split(";");
		    int x = 0;
		    for (String part : parts) {
		    	datos[x] = part;
		    	x++;
		    }
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		leer UDL
		Connection conectar = null;
		pass = datos[1];
		user = datos[3];
		bd = datos[4];
		server = datos[5];
		clave = pass.split("=");
		usuario = user.split("=");
		db = bd.split("=");
		servidor = server.split("=");
		try {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//RECORDAR QUE ESTE DEBE SACAR LA INFORMACION DEL UDL.
			String url="jdbc:sqlserver://"+(String)servidor[1]+";databaseName="+(String)db[1]+";user="+ (String)usuario[1] + ";password=" + (String)clave[1] + ";";
			//			String usr = datos[3];
//			String pass = datos[4];
			conectar = DriverManager.getConnection(url);
//			conectar = DriverManager.getConnection("jdbc:sqlserver://vmsoluciones.cloudapp.net;databaseName=MilleniumNEW;user=sa;password=SOLUCION");
//			conectar = DriverManager.getConnection("jdbc:sqlserver://186.64.110.212;databaseName=PbMillenium2;"+datos[3]+";"+datos[1]+";"+"integratedSecurity=true");
//			conectar = DriverManager.getConnection("jdbc:sqlserver://186.64.110.212;databaseName=PbMillenium2;user=consultas;password=consultas*");
		} catch (SQLException sqlex) {
			System.out.println("Error de Conexion: " + sqlex.getMessage());
		}
		return conectar;
	}
}
