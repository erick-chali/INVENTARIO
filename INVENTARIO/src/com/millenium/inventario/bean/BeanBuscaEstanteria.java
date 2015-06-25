package com.millenium.inventario.bean;

public class BeanBuscaEstanteria {
	public BeanBuscaEstanteria(String codigoProd, String descProd){
		this.setCodigoProd(codigoProd);
		this.setDescProd(descProd);
	}
	public BeanBuscaEstanteria(){
		
	}
//	private int respuesta;
	private String codigoEstanteria;
//	private String descripcionEstanteria;
	
	private String codigoProd;
	private String descProd;
	
//	public int getRespuesta() {
//		return respuesta;
//	}
//	public void setRespuesta(int respuesta) {
//		this.respuesta = respuesta;
//	}
	public String getCodigoEstanteria() {
		return codigoEstanteria;
	}
	public void setCodigoEstanteria(String codigoEstanteria) {
		this.codigoEstanteria = codigoEstanteria;
	}
//	public String getDescripcionEstanteria() {
//		return descripcionEstanteria;
//	}
//	public void setDescripcionEstanteria(String descripcionEstanteria) {
//		this.descripcionEstanteria = descripcionEstanteria;
//	}
	public String getCodigoProd() {
		return codigoProd;
	}
	public void setCodigoProd(String codigoProd) {
		this.codigoProd = codigoProd;
	}
	public String getDescProd() {
		return descProd;
	}
	public void setDescProd(String descProd) {
		this.descProd = descProd;
	}
	
	
	
}
