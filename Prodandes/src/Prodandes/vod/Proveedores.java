package Prodandes.vod;

/**
 * @author javie_000
 * @version 1.0
 * @created 14-Mar-2015 20:30:03
 */
public class Proveedores extends Personas {

	private int cotizacion;
	private String materiaPrima;
	private int volumenMaximo;
	private int tiempoEntrega;
	private String nomRepresentanteLegal;

	public Proveedores(String pdireccion,String pnombre,int ptelefono,String pciudad, int pidentificacion,String ptipoId)
	{
		super( pdireccion, pnombre, ptelefono, pciudad,  pidentificacion, ptipoId);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
}//end Proveedores