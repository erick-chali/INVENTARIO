package com.millenium.inventario.bean;

public class BeanActualizarProducto {
//	BeanActualizarProducto(String userID,String codigoProd,String cantidad,String codigoEst,String codigoSec){}
//	BeanActualizarProducto(){}
	private String codigoEstanteria;
	private String codigoSeccion;
	private String codigoBodega;
	private String codigoProducto;
	private String descripProducto;
	private String cantProducto;
	private String unidadMedida;
	private String noToma;
	private String usuario;
	private String notificacion;
	private int existe;
	public String getCodigoEstanteria() {
		return codigoEstanteria;
	}
	public void setCodigoEstanteria(String codigoEstanteria) {
		this.codigoEstanteria = codigoEstanteria;
	}
	public String getCodigoSeccion() {
		return codigoSeccion;
	}
	public void setCodigoSeccion(String codigoSeccion) {
		this.codigoSeccion = codigoSeccion;
	}
	public String getCodigoBodega() {
		return codigoBodega;
	}
	public void setCodigoBodega(String codigoBodega) {
		this.codigoBodega = codigoBodega;
	}
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getDescripProducto() {
		return descripProducto;
	}
	public void setDescripProducto(String descripProducto) {
		this.descripProducto = descripProducto;
	}
	public String getCantProducto() {
		return cantProducto;
	}
	public void setCantProducto(String cantProducto) {
		this.cantProducto = cantProducto;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public String getNoToma() {
		return noToma;
	}
	public void setNoToma(String noToma) {
		this.noToma = noToma;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getExiste() {
		return existe;
	}
	public void setExiste(int existe) {
		this.existe = existe;
	}
	public String getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}
	
}
