package Prodandes.vod;

/**
 * @author javie_000
 * @version 1.0
 * @created 14-Mar-2015 20:30:02
 */
public class Personas {

	private String direccion;
	private String nombre;
	private int telefono;
	private String codigoPostal;
	private int identificacion;
	private String tipoId;

	public Personas(String pdireccion,String pnombre,int ptelefono,String pciudad, int pidentificacion,String ptipoId)
	{
		setDireccion(pdireccion);
		setNombre(pnombre);
		setTelefono(ptelefono);
		setCiudad(pciudad);
		setIdentificacion(pidentificacion);
		setTipoId(ptipoId);
	}

	public void finalize() throws Throwable {

	}

	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}

	public int getIdentificacion() {
		return identificacion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setCiudad(String ciudad) {
		this.codigoPostal = ciudad;
	}

	public String getcPostal() {
		return codigoPostal;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getTipoId() {
		return tipoId;
	}

}//end Personas