package com.millenium.inventario.dao;

import java.util.ArrayList;

import javax.ejb.Remote;

@Remote
public interface DatosProductoRemoto {
	
	DatosProducto obtenerDatosProducto(DatosProducto datos);
	
}
