package prodAndes.vos;

import java.sql.Date;



/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class EtapaProduccion
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */	
	private int numeroSecuencia;

	/**
	 * 
	 */
	private int id;
	
	/**
	 * 
	 */
	private int idProceso;
	
	/**
	 * 
	 */
	private int idProdStock;
	
	/**
	 * 
	 */
	private int idProdStockNecesitado;
	
	/**
	 * 
	 */
	private Date tiempoInicioEjecucion;
	
	/**
	 * 
	 */
	private Date tiempoFinEjecucion;
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String nombre;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */	
	private boolean completada;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public EtapaProduccion(int numSecuencia, String nom, int i, int idProc, int idProdSt, int idProdStNec, Date tiempoInicEjec, Date tiempoFinEjec){
		numeroSecuencia= numSecuencia;
		nombre= nom;
		completada=false;
		id=i;
		idProceso=idProc;
		idProdStock=idProdSt;
		idProdStockNecesitado=idProdStNec;
		tiempoInicioEjecucion=tiempoInicEjec;
		tiempoFinEjecucion=tiempoFinEjec;
	}
	
	/**
	 * 
	 */
	public int darID()
	{
		return id;
	}
	
	public int darIdProceso()
	{
		return idProceso;
	}
	public int darIdProdStock()
	{
		return idProdStock;
	}
	public int darIdProdStockNecesitado()
	{
		return idProdStockNecesitado;
	}
	public Date darTiempoInicioEjecucion()
	{
		return tiempoInicioEjecucion;
	}
	public Date darTiempoFinEjecucion()
	{
		return tiempoFinEjecucion;
	}
	public String darNombre()
	{
		return nombre;
	}
	public boolean Completada()
	{
		return completada;
	}
	public int darNumeroSecuencia()
	{
		return numeroSecuencia;
	}

}

