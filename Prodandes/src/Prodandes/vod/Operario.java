package Prodandes.vod;

/**
 * @author je.camargo10
 * @version 1.0
 * @created 14-Mar-2015 20:30:02
 */
public class Operario extends Personas {

	private int areaEncargada;
	private String cargo;
	private int cuentaBancaria;
	private int duracionContrato;
	public int id;

	public Operario(String pdireccion,String pnombre,int ptelefono,String pciudad, int pidentificacion,String ptipoId, int area, String pCargo, int cuenta, int duracion)
	{
		super( pdireccion, pnombre, ptelefono, pciudad,  pidentificacion, ptipoId);
		areaEncargada = area;
		cargo = pCargo;
		cuentaBancaria = cuenta;
		duracionContrato = duracion;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	public int darArea()
	{
		return areaEncargada;
	}
	
	public String darCargo()
	{
		return cargo;
	}
	
	public int darCuenta()
	{
		return cuentaBancaria;
	}
	
	public int darDuracion()
	{
		return duracionContrato;
	}
	
	public int darId()
	{
		return id;
	}
}//end Operario