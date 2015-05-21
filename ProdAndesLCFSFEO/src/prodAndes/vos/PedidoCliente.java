package prodAndes.vos;

import java.util.ArrayList;
import java.util.Date;

public class PedidoCliente{
	
	/**
	 * El cliente del pedido
	 */
	private int id_cliente;
	
	/**
	 * E: Entregado
	 * F:Rechazado
	 * T: Aceptador
	 */
	private char estado;
	
	/**
	 * Fecha en la que se entrego el producto
	 */
	private String fechaEntrega;
	
	/**
	 * Fecha en la que el usuario espera recibir el pedido
	 */
	private String fechaEsperada;
	
	private String fechaPedido;
	
	private String fechaCancelado;
	
	/**
	 * Los productos que hacen parte del pedido.
	 */
	private ArrayList<ProductoPedido> productos;
	
	private int idPedido;

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public void setProductos(ArrayList<ProductoPedido> productos) {
		this.productos = productos;
	}

	public PedidoCliente(int id_cliente, char estado, String fechaEntrega,
			String fechaEsperada, ArrayList<ProductoPedido> ag, int id) {
		this.id_cliente = id_cliente;
		this.estado = estado;
		this.fechaEntrega = fechaEntrega;
		this.fechaEsperada = fechaEsperada;
		productos=new ArrayList<ProductoPedido>();
		productos=ag;
		idPedido=id;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getFechaEsperada() {
		return fechaEsperada;
	}

	public void setFechaEsperada(String fechaEsperada) {
		this.fechaEsperada = fechaEsperada;
	}

	public ArrayList<ProductoPedido> getProductos() {
		return productos;
	}
	
	public void agregarProductos(ProductoPedido agregar)
	{
		this.productos.add(agregar);
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getFechaCancelado() {
		return fechaCancelado;
	}

	public void setFechaCancelado(String fechaCancelado) {
		this.fechaCancelado = fechaCancelado;
	}
}
