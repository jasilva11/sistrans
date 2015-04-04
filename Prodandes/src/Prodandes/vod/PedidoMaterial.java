package Prodandes.vod;

public class PedidoMaterial
{
	int id;
	int idProveedor;
	String material;
	int cantidad;
	int tiempo;
	int costo;
	
	public PedidoMaterial(int pId, int pIdProveedor, String pMaterial, int pCantidad, int pTiempo, int pCosto)
	{
		id = pId;
		idProveedor = pIdProveedor;
		material = pMaterial;
		cantidad = pCantidad;
		tiempo = pTiempo;
		costo = pCosto;
	}

	public int darId()
	{
		return id;
	}
	
	public int darIdProveedor()
	{
		return idProveedor;
	}
	
	public String darMaterial()
	{
		return material;
	}
	
	public int darCantidad()
	{
		return cantidad;
	}
	
	public int darTiempo()
	{
		return tiempo;
	}
	
	public int darCosto()
	{
		return costo;
	}
}
