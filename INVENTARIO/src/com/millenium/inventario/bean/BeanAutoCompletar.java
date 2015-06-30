package com.millenium.inventario.bean;

public class BeanAutoCompletar {
	public BeanAutoCompletar(String codigoProducto, String descripProducto, String unidadMedida, 
			String codigoEst, String codigoSec, String cantidad){
		
		this.setCodigoProducto(codigoProducto);
		this.setDescripProducto(descripProducto);
		this.setUnidadMedida(unidadMedida);
		this.setCodigoEst(codigoEst);
		this.setCodigoSec(codigoSec);
		this.setCantidad(cantidad);
	}
	public BeanAutoCompletar(){}
	private String codigoProducto;
	private String descripProducto;
	private String cantidadActualProducto;
	private String unidadMedida;
	private String codigoEst;
	private String codigoSec;
	private String codigoBod;
	private String cantidad;
	private String noToma;
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
	public String getCantidadActualProducto() {
		return cantidadActualProducto;
	}
	public void setCantidadActualProducto(String cantidadActualProducto) {
		this.cantidadActualProducto = cantidadActualProducto;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public String getCodigoEst() {
		return codigoEst;
	}
	public void setCodigoEst(String codigoEst) {
		this.codigoEst = codigoEst;
	}
	public String getCodigoSec() {
		return codigoSec;
	}
	public void setCodigoSec(String codigoSec) {
		this.codigoSec = codigoSec;
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
	public String getCodigoBod() {
		return codigoBod;
	}
	public void setCodigoBod(String codigoBod) {
		this.codigoBod = codigoBod;
	}
	
}
