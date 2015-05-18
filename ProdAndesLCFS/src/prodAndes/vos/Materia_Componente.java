package prodAndes.vos;

public class Materia_Componente 
{
	/**
	 * Identificador de la materia o componente
	 */
	private int id;
	
	/**
	 * Nombre de la materia o componente
	 */
	private String nombre;
	
	/**
	 * La unidad de medida de la materia o componente
	 */
	private String unidadMedida;
	
	/**
	 * La cantidad que hay de la materia o componente
	 */
	private int cantidad;
	
	/**
	 * La cantidad reservada que hay de la materia o componente
	 */
	private int cantidadReservada;
	
	public Materia_Componente(int id, String nombre, String unidadMedida,int cantidad, int cantidadReservada) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.unidadMedida = unidadMedida;
		this.cantidad = cantidad;
		this.cantidadReservada = cantidadReservada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadReservada() {
		return cantidadReservada;
	}

	public void setCantidadReservada(int cantidadReservada) {
		this.cantidadReservada = cantidadReservada;
	}
}
