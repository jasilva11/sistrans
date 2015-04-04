package Prodandes.vod;

import java.util.ArrayList;

/**
 * @author javie_000
 * @version 1.0
 * @created 14-Mar-2015 20:30:03
 */
public class Proveedores extends Personas 
{

	private String materiaPrima;
	private int volumenMaximo;
	private int tiempoEntrega;
	private int cotizacion;
	private ArrayList productos;
	private ArrayList pedidos;
	private String nomRepresentanteLegal;

	public Proveedores(String pdireccion,String pnombre,int ptelefono,String pciudad, String ptipoId, String pMateriaPrima, int pId, int vol, int tiempo, int pCotizacion)
	{
		super( pdireccion, pnombre, ptelefono, pciudad,  pId, ptipoId);
		materiaPrima = pMateriaPrima;
		volumenMaximo = vol;
		tiempoEntrega = tiempo;
		productos = new ArrayList();
		pedidos = new ArrayList();
		cotizacion = pCotizacion;
	}
	
	public String darMaterial()
	{
		return materiaPrima;
	}
	
	public int darVolumen()
	{
		return volumenMaximo;
	}
	
	public int darCotizacion()
	{
		return cotizacion;
	}
	
	public int darTiempo()
	{
		return tiempoEntrega;
	}
	
	public void agregarProducto(String pProducto)
	{
		productos.add(pProducto);
	}
	
	public void agregarPedido(int pIdPedido)
	{
		pedidos.add(pIdPedido);
	}
	
	public ArrayList darProductos()
	{
		return productos;
	}
	
	public ArrayList darPedidos()
	{
		return pedidos;
	}


	public void finalize() throws Throwable
	{
		super.finalize();
	}
}//end Proveedores