package com.millenium.inventario.bean;

public class BeanCargarTomas {
	
	public BeanCargarTomas(int noToma, String observaciones){
		this.setNoToma(noToma);
		this.setObservaciones(observaciones);
	}
	public BeanCargarTomas(){
		
	}
	private int noToma;
	private String observaciones;
	
	public int getNoToma() {
		return noToma;
	}
	public void setNoToma(int noToma) {
		this.noToma = noToma;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}
