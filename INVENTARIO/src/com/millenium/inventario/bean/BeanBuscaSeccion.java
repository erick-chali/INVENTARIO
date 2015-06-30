package com.millenium.inventario.bean;

public class BeanBuscaSeccion {
	public BeanBuscaSeccion(String codigoS, String descS){
		this.setCodigoS(codigoS);
		this.setDescS(descS);
	}
	public BeanBuscaSeccion(){}
	
	private String codigoS;
	private String descS;
	private String estanteriaID;
	
	public String getCodigoS() {
		return codigoS;
	}
	public void setCodigoS(String codigoS) {
		this.codigoS = codigoS;
	}
	
	public String getDescS() {
		return descS;
	}
	public void setDescS(String descS) {
		this.descS = descS;
	}
	public String getEstanteriaID() {
		return estanteriaID;
	}
	public void setEstanteriaID(String estanteriaID) {
		this.estanteriaID = estanteriaID;
	}
	
	
	
	
	
	
}
