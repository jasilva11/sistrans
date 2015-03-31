package Prodandes.vod;

import java.sql.Date;

/**
 * @author je.camargo10
 * @version 1.0
 * @created 14-Mar-2015 20:30:02
 */
public class Inventario {

	private int costoProduccion;
	private Date fecha;
	private int tiempoInventariado;
	private int ventasRealizadas;
	public Producto inventario;
	public MateriasPrimas m_MateriasPrimas;

	public Inventario(){

	}

	public void finalize() throws Throwable {

	}
}//end Inventario