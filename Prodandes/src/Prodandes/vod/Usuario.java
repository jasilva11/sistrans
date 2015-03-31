package Prodandes.vod;

/**
 * @author javie_000
 * @version 1.0
 * @created 14-Mar-2015 20:30:03
 */
public class Usuario extends Personas {

	private String rolEnSistema;
	private String tipoId;
	private String nacionalidad;
	private String direccionElectronica;
	private String departamento;
	private int codigoPostal;
	private String login;
	private String clave;

	public Usuario(Personas x)
	{
	
		super( x.getDireccion(), x.getNombre(), x.getTelefono(), x.getcPostal(),  x.getIdentificacion(), x.getTipoId());
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	
	public boolean esCliente()
	{
		if (rolEnSistema.equals("Cliente"))
		{
			return true;
		}
		else
		{
		return false;
	}
	}
	
	
	
	
	
	
	
	
}//end Usuario