package com.millenium.inventario.bean;

public class BeanBuscaProducto {

	public BeanBuscaProducto(){
		
	}
	public BeanBuscaProducto(String codigoProducto, String descripcionProducto, 
			String unidadMedida, String descripcionUnidad, String cantidad){
		
		this.setCodigoProducto(codigoProducto);
		this.setDescripcionProducto(descripcionProducto);
		this.setUnidadMedida(unidadMedida);
		this.setDescripcionUnidad(descripcionUnidad);
		this.setCantidad(cantidad);
	}
	
	private String codigoProducto;
	private String descripcionProducto;
	private String unidadMedida;
	private String descripcionUnidad;
	private String notificacion;
	private String codB;
	private String codS;
	private String codE;
	private String cantidad;
	private int resultado;
	private String noToma;
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
	public String getCodB() {
		return codB;
	}
	public void setCodB(String codB) {
		this.codB = codB;
	}
	public String getCodS() {
		return codS;
	}
	public void setCodS(String codS) {
		this.codS = codS;
	}
	public String getCodE() {
		return codE;
	}
	public void setCodE(String codE) {
		this.codE = codE;
	}
	public int getResultado() {
		return resultado;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getNoToma() {
		return noToma;
	}
	public void setNoToma(String noToma) {
		this.noToma = noToma;
	}
	
	
	
	
}
