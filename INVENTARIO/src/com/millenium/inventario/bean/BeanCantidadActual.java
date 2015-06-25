package com.millenium.inventario.bean;

public class BeanCantidadActual {
	
	public BeanCantidadActual(String conteo){
		this.setConteo(conteo);
	}
	public BeanCantidadActual(){
		
	}
	private String codigoProducto;
	private String usuarioID;
	private String codigoEstanteria;
	private String codigoSeccion;
	private String codigoBodega;
	private String conteo;
	private String noToma;
	
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
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
	public String getUsuarioID() {
		return usuarioID;
	}
	public void setUsuarioID(String usuarioID) {
		this.usuarioID = usuarioID;
	}
	public String getConteo() {
		return conteo;
	}
	public void setConteo(String conteo) {
		this.conteo = conteo;
	}
	public String getNoToma() {
		return noToma;
	}
	public void setNoToma(String noToma) {
		this.noToma = noToma;
	}
	public String getCodigoBodega() {
		return codigoBodega;
	}
	public void setCodigoBodega(String codigoBodega) {
		this.codigoBodega = codigoBodega;
	}
	
}
