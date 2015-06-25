package com.millenium.inventario.bean;

public class BeanBuscaProducto {
	
	public BeanBuscaProducto(String codigoProducto, String descripcionProducto, String unidadMedida, String descripcionUnidad){
		this.setCodigoProducto(codigoProducto);
		this.setDescripcionProducto(descripcionProducto);
		this.setUnidadMedida(unidadMedida);
		this.setDescripcionUnidad(descripcionUnidad);
	}
	public BeanBuscaProducto(){
		
	}
	
	private String codigoProducto;
	private String descripcionProducto;
	private String unidadMedida;
	private String descripcionUnidad;
	private String notificacion;
	private int resultado;
	
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public String getDescripcionUnidad() {
		return descripcionUnidad;
	}
	public void setDescripcionUnidad(String descripcionUnidad) {
		this.descripcionUnidad = descripcionUnidad;
	}
	public String getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}
	public int getResultado() {
		return resultado;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	
}
