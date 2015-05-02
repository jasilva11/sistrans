package Prodandes.vod;

import java.util.Date;

public class MaterialPedido 
{
	private int id;
	private int idPedido;
	private String nombre;
	private String tipo;
	private int volumen;
	private int costo;
	private int etapa;
	private Date inicio;
	private Date fin;

	public MaterialPedido(int pId, int pIdPedido, String pNombre, String pTipo, int pVolumen, int pCosto, int pEtapa, Date pInicio, Date pFin)
	{
		id = pId;
		idPedido = pIdPedido;
		nombre = pNombre;
		tipo = pTipo;
		volumen = pVolumen;
		costo = pCosto;
		etapa = pEtapa;
		inicio = pInicio;
		fin = pFin;
	}

	public int darId()
	{
		return id;
	}
	
	public int darIdPedido()
	{
		return idPedido;
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
	
	public int darEtapa()
	{
		return etapa;
	}
	
	public int darVolumen()
	{
		return volumen;
	}
	
	public Date darInicio()
	{
		return inicio;
	}
	
	public Date darFin()
	{
		return fin;
	}
}
