package Prodandes.vod;

import java.util.ArrayList;

/**
 * @author javie_000
 * @version 1.0
 * @created 14-Mar-2015 20:30:03
 */
public class Producto {

	private int costoProduccion;
	private String Nombre;
	private int precio;
	private int cantidad;

	public  ArrayList<EtapasDeProducion> m_EtapasDeProducion;
	public ArrayList<MateriasPrimas> m_MateriasPrimas;
	public ArrayList<ComponentesProduccion> m_ComponentesProduccion;
	public TipoDeProceso m_TipoDeProceso;
	public Logistica m_Logistica;
	public Inventario m_Inventario;
	public boolean terminado;

	public Producto(int pcostoProduccion,String pNombre,int pprecio,int pcantidad,ArrayList <ComponentesProduccion>comp,ArrayList<EtapasDeProducion>etp, ArrayList<MateriasPrimas>map)
	{
		terminado = false;
		costoProduccion= pcostoProduccion;
		Nombre= pNombre;
		precio= pprecio;
		cantidad = pcantidad;
		m_EtapasDeProducion = etp;
		m_MateriasPrimas = map;
		m_ComponentesProduccion = comp;

	}

	public Producto(int pcostoProduccion, String nombre2, boolean terminado2,
			int idEtapa)
	{
		terminado = terminado2;
		costoProduccion = pcostoProduccion;
		Nombre = nombre2;
		
		// TODO Auto-generated constructor stub
	}

	public void finalize() throws Throwable {

	}

	public void agregarEtapaDeProduccion(EtapasDeProducion papitas)
	{
		m_EtapasDeProducion.add(papitas);
	}
	public ArrayList<EtapasDeProducion> darEtapas()
	{
		return m_EtapasDeProducion;
	}
	
	
	public void setCostoProduccion(int costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public int getCostoProduccion() {
		return costoProduccion;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getPrecio() {
		return precio;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}
	public boolean darEstado()
	{
		return terminado;
	}

public ArrayList<MateriasPrimas> darMaterialesrequeridos()
{
	return 
	m_MateriasPrimas;
	}

public ArrayList<ComponentesProduccion> darComponentesrequeridos()
{
	return 
	m_ComponentesProduccion;
	}

public ArrayList<EtapasDeProducion> darEtapasrequeridos()
{
	return 
	m_EtapasDeProducion;
	}


	public void fabricar()
	{
		
	
		
	}
}//end Producto