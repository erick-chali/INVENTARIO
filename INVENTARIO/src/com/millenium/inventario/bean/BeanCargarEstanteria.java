package com.millenium.inventario.bean;

public class BeanCargarEstanteria {
	public BeanCargarEstanteria(String idEstanteria, String descEstanteria, String idSeccion, String descSeccion){
		this.setIdEstanteria(idEstanteria);
		this.setDescEstanteria(descEstanteria);
		this.setIdSeccion(idSeccion);
		this.setDescSeccion(descSeccion);
	}
	public BeanCargarEstanteria(){}
	
	private String idEstanteria;
	private String descEstanteria;
	private String idSeccion;
	private String descSeccion;
	public String getIdEstanteria() {
		return idEstanteria;
	}
	public void setIdEstanteria(String idEstanteria) {
		this.idEstanteria = idEstanteria;
	}
	public String getDescEstanteria() {
		return descEstanteria;
	}
	public void setDescEstanteria(String descEstanteria) {
		this.descEstanteria = descEstanteria;
	}
	public String getIdSeccion() {
		return idSeccion;
	}
	public void setIdSeccion(String idSeccion) {
		this.idSeccion = idSeccion;
	}
	public String getDescSeccion() {
		return descSeccion;
	}
	public void setDescSeccion(String descSeccion) {
		this.descSeccion = descSeccion;
	}
	
	
}
