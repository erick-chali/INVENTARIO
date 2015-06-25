package com.millenium.inventario.bean;

public class BeanCargarProductos {
	public BeanCargarProductos(String codP,String codB,String codS,String codE,String usuario,
			String descP,String descB,String descS,String descE,String conteo,String unidad,String noToma){
		this.setCodP(codP);
		this.setCodB(codB);
		this.setCodS(codS);
		this.setCodE(codE);
		this.setDescP(descP);
		this.setDescE(descE);
		this.setDescB(descB);
		this.setDescS(descS);
		this.setConteo1(conteo);
		this.setUnidad(unidad);
		this.setUsuario(usuario);
		this.setNoToma(noToma);
	}
	public BeanCargarProductos(){}
	
	private String codP;
	private String codB;
	private String codS;
	private String codE;
	private String descP;
	private String descB;
	private String descS;
	private String descE;
	private String conteo;
	private String usuario;
	private String unidad;
	private String noToma;
	
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
	public String getDescP() {
		return descP;
	}
	public void setDescP(String descP) {
		this.descP = descP;
	}
	public String getDescB() {
		return descB;
	}
	public void setDescB(String descB) {
		this.descB = descB;
	}
	public String getDescS() {
		return descS;
	}
	public void setDescS(String descS) {
		this.descS = descS;
	}
	public String getDescE() {
		return descE;
	}
	public void setDescE(String descE) {
		this.descE = descE;
	}
	public String getConteo1() {
		return conteo;
	}
	public void setConteo1(String conteo1) {
		this.conteo = conteo1;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getNoToma() {
		return noToma;
	}
	public void setNoToma(String noToma) {
		this.noToma = noToma;
	}
	

}
