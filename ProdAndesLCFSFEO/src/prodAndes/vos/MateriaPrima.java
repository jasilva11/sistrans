package prodAndes.vos;

public class MateriaPrima 
{
	
	private String nombre;
	
	private String unidadDeMedida;
	
	private String id;
	
	private int cantidadInicial;
	
	private int cantidadReservada;
	
	public MateriaPrima(String nom, String udm, String idp, int canti, int cantR)
	{
		nombre=nom;
		unidadDeMedida=udm;
		id=idp;
		cantidadInicial=canti;
		cantidadReservada=cantR;
	}

}
