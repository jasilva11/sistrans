package prodAndes.vos;

public class Producto 
{
	/**
	 * El identificador del producto
	 */
	private int idProducto;
	
	/**
	 * Nombre del producto
	 */
	private String nombre;
	
	/**
	 * El costo del producto
	 */
	private int costoVenta;

	public Producto(int idProducto, String nombre, int costoVenta) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.costoVenta = costoVenta;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCostoVenta() {
		return costoVenta;
	}

	public void setCostoVenta(int costoVenta) {
		this.costoVenta = costoVenta;
	}
}
