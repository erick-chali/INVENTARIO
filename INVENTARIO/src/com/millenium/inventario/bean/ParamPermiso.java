package com.millenium.inventario.bean;

public class ParamPermiso {
	
	public ParamPermiso(){}
	public ParamPermiso(String permiso){
		this.setPermiso(permiso);
	}
	
	private String permiso;
	private String userID;
	
	
	public String getPermiso() {
		return permiso;
	}
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}
