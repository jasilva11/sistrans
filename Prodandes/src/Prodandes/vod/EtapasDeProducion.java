package Prodandes.vod;

import java.sql.Date;

/**
 * @author javie_000
 * @version 1.0
 * @created 14-Mar-2015 20:30:02
 */
public class EtapasDeProducion {

	private int id;
	private String nombre;
	private Date inicio;
	private Date fin;
	private int personalRequerido;
	private int numeroSecuencia;
	public Producto m_Producto;
	public ComponentesProduccion m_ComponentesProduccion;
	public MateriasPrimas m_MateriasPrimas;
	public EstacionesProduccion m_EstacionesProduccion;

	public EtapasDeProducion(int pId, String pNombre, Date pInicio, Date pFin)
	{
		id = pId;
		nombre = pNombre;
		inicio = pInicio;
		fin = pFin;
	}
	
	public int darId()
	{
		return id;
	}
	
	public String darNombre()
	{
		return nombre;
	}
	
	public Date darInicio()
	{
		return inicio;
	}
	
	public Date darFin()
	{
		return fin;
	}

	public void finalize() throws Throwable {

	}
}//end EtapasDeProducion