package Prodandes.vod;

import java.util.Date;

/**
 * @author je.camargo10
 * @version 1.0
 * @created 14-Mar-2015 20:30:02
 */
public class MateriasPrimas {

	private String nombre;
	private String loComponen;
	private String compone;
	private String producto;
	private String tipo;
	private int volumen;
	private int costo;
	private int etapa;
	private Date inicio;
	private Date fin;

	public MateriasPrimas(String pNombre, String pLoComponen, String pCompone, String pProducto, String pTipo, int pVolumen, int pCosto, int pEtapa, Date pInicio, Date pFin)
	{
		nombre = pNombre;
		loComponen = pLoComponen;
		compone = pCompone;
		producto = pProducto;
		tipo = pTipo;
		volumen = pVolumen;
		costo = pCosto;
		etapa = pEtapa;
		inicio = pInicio;
		fin = pFin;
	}

	public String darNombre()
	{
		return nombre;
	}
	
	public int darCosto()
	{
		return costo;
	}

	public String darTipo()
	{
		return tipo;
	}
	
	public String darComponentes()
	{
		return loComponen;
	}
	
	public int darEtapa()
	{
		return etapa;
	}
	
	public int darVolumen()
	{
		return volumen;
	}

	public String darProducto()
	{
		return producto;
	}
	
	public String darCompone()
	{
		return compone;
	}
	
	public Date darInicio()
	{
		return inicio;
	}
	
	public Date darFin()
	{
		return fin;
	}
}//end MateriasPrimas