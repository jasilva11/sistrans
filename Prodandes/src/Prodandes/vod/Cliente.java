
package Prodandes.vod;

import java.util.ArrayList;

/**
 * @author je.camargo10
 * @version 1.0
 * @created 14-Mar-2015 20:30:02
 */
public class Cliente extends Personas 
{

	private int antiguedad;
	private int deudas;
	private int numeroRegistro;
	private String nomRepresentanteLegal;
	private boolean personaNatural;
	public MetodoDePago metodoDePago;
	
	public ArrayList<String> productos;


	public Cliente(String pdireccion,String pnombre,int ptelefono,String pciudad, int pidentificacion,String ptipoId,int pAntiguedad, int pdeudas, int pnumeroRegistro,String pnomRepresentanteLegal, boolean ppersonaNatural)
	{
		
		super( pdireccion, pnombre, ptelefono, pciudad,  pidentificacion, ptipoId);
		setAntiguedad(pAntiguedad);
		setDeudas(pdeudas);
		setNumeroRegistro(pnumeroRegistro);
		setNomRepresentanteLegal(pnomRepresentanteLegal);
		setPersonaNatural(ppersonaNatural);
		
		productos = new ArrayList<String>();

	}
	
	public void agregarProducto(String x)
	{
		productos.add(x);
	}
  public ArrayList<String> darProductos()
  {
	  return productos;
  }
	public void setAntiguedad(int antiguedad) 
	{
		antiguedad = antiguedad;
	}

	public int getAntiguedad() 
	{
		return antiguedad;
	}

	public void setDeudas(int deudas) {
		this.deudas = deudas;
	}

	public int getDeudas() {
		return deudas;
	}

	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNomRepresentanteLegal(String nomRepresentanteLegal) {
		this.nomRepresentanteLegal = nomRepresentanteLegal;
	}

	public String getNomRepresentanteLegal() {
		return nomRepresentanteLegal;
	}

	public void setPersonaNatural(boolean personaNatural) {
		this.personaNatural = personaNatural;
	}

	public boolean isPersonaNatural() {
		return personaNatural;
	}


}//end Cliente