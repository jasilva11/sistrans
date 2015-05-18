package prodAndes.vos;

public class EstacionProduccion 
{
	private int codigo;
	
	private int capacidad;
	
	
	private char estado;

	public EstacionProduccion(int codigo, int capacidad,
			char estado) {
		super();
		this.codigo = codigo;
		this.capacidad = capacidad;

		this.estado = estado;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}


}
