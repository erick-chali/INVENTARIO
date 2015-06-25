package com.millenium.inventario.bean;

public class BeanAutoCompletar {
	public BeanAutoCompletar(String codigoProducto, String descripProducto, String unidadMedida, String codigoEst, String codigoSec){
		
		this.setCodigoProducto(codigoProducto);
		this.setDescripProducto(descripProducto);
		this.setUnidadMedida(unidadMedida);
		this.setCodigoEst(codigoEst);
		this.setCodigoSec(codigoSec);
	}
	public BeanAutoCompletar(){}
	private String codigoProducto;
	private String descripProducto;
	private String cantidadActualProducto;
	private String unidadMedida;
	private String codigoEst;
	private String codigoSec;
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
	
}
