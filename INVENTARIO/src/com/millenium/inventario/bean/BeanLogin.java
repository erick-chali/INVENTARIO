package com.millenium.inventario.bean;

public class BeanLogin {
	
	private String usuario;
	private String noToma;
	private String userID;
	private int respuesta;
	private String clave;
	private String notificacion;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(int respuesta) {
		this.respuesta = respuesta;
	}
	public String getNoToma() {
		return noToma;
	}
	public void setNoToma(String noToma) {
		this.noToma = noToma;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}
	
}
