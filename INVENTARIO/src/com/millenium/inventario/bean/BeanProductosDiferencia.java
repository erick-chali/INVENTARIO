package com.millenium.inventario.bean;

public class BeanProductosDiferencia {
	
	public BeanProductosDiferencia(String codP, String descP, String cantidad,
			String uniP, String bodega, String seccion, String estanteria){
		this.setBodega(bodega);
		this.setCantidad(cantidad);
		this.setCodP(codP);
		this.setDescP(descP);
		this.setEstanteria(estanteria);
		this.setSeccion(seccion);
		this.setUniP(uniP);
	}
	public BeanProductosDiferencia(){}
	
	private String codP;
	private String codB;
	
	private String descP;
	private String uniP;
	private String seccion;
	private String estanteria;
	private String bodega;
	private String cantidad;
	
	public String getCodP() {
		return codP;
	}
	public void setCodP(String codP) {
		this.codP = codP;
	}
	public String getCodB() {
		return codB;
	}
	public void setCodB(String codB) {
		this.codB = codB;
	}
	public String getDescP() {
		return descP;
	}
	public void setDescP(String descP) {
		this.descP = descP;
	}
	public String getUniP() {
		return uniP;
	}
	public void setUniP(String uniP) {
		this.uniP = uniP;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public String getEstanteria() {
		return estanteria;
	}
	public void setEstanteria(String estanteria) {
		this.estanteria = estanteria;
	}
	public String getBodega() {
		return bodega;
	}
	public void setBodega(String bodega) {
		this.bodega = bodega;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
