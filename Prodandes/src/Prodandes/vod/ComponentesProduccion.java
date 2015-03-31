package Prodandes.vod;

/**
 * @author javie_000
 * @version 1.0
 * @created 14-Mar-2015 20:30:02
 */
public class ComponentesProduccion {

	private String nombre;
	private int volumenInicial;

	public ComponentesProduccion(String pNombre, int pVolumenInicial)
	{
setNombre(pNombre);
setVolumenInicial(pVolumenInicial);

	}

	public void finalize() throws Throwable {

	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setVolumenInicial(int volumenInicial) {
		this.volumenInicial = volumenInicial;
	}

	public int getVolumenInicial() {
		return volumenInicial;
	}
}//end ComponentesProduccion