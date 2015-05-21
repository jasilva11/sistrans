package prodAndes.vos;

public class ProductoPedido 
{
	/**
	 * Cantidad del producto pedido
	 */
	private int cantidad;
	
	/**
	 * El producto pedido
	 */
	private int id_producto;

	public ProductoPedido(int cantidad, int id_producto) {
		super();
		this.cantidad = cantidad;
		this.id_producto = id_producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
}
