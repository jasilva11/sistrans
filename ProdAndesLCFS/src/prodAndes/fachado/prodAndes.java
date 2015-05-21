package prodAndes.fachado;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import prodAndes.dao.ConsultaDAO;
import prodAndes.vos.EstacionProduccion;
import prodAndes.vos.EtapaProduccion;
import prodAndes.vos.Materia_Componente;
import prodAndes.vos.PedidoCliente;
import prodAndes.vos.Producto;
import prodAndes.vos.ProductoPedido;


public class prodAndes {
	private final static String ADMIN = "admin";

	/**
	 * Conexion con la clase que maneja la base de datos
	 */
	private ConsultaDAO dao;
	
	/**
	 * 
	 * @return
	 */
	private boolean RF10;
	
	/**
	 * 
	 */
	private boolean RF11;
	
	/**
	 * 
	 */
	private boolean RF13;
	
	/**
	 * 
	 */
	private int idUsuarioLogueado;

	//--------------------------
	//Instance
	//--------------------------
	/**
	 * Instancia de la clase.
	 */
	private static prodAndes instance;

	/**
	 * Devuelve la instancia unica de la clase.
	 */
	public static prodAndes getInstance(){
		if(instance == null){
			instance = new prodAndes();
		
		}
		return instance;
	}

	private prodAndes()
	{
		dao = new ConsultaDAO();
		RF10=false;
		RF13=false;
		idUsuarioLogueado = 10 ;
	}
	
	/**
	 * inicializa el dao, dandole la ruta en donde debe encontrar
	 * el archivo properties.
	 * @param ruta ruta donde se encuentra el archivo properties
	 */
	public void inicializarRuta()
	{
		dao.inicializar();
	}
	
	  public void iniciarPaginacion()
	    {
	    	dao.iniciarPaginacion();
	    }
	    
	    public int siguientePaginacion()
	    {
	    	return dao.siguientePaginacion();
	    }
	    
	    public void anteriorPaginacion()
	    {
	    	dao.anteriorPaginacion();
	    }
	    
	public ArrayList<EstacionProduccion> darEstacionesActivadas() 
	{
		
		try {
			ArrayList<EstacionProduccion> resp =dao.darEstacionesActivadas();
			
			return resp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("aaaaa");e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<EstacionProduccion> darEstacionesDesActivadas() 
	{
		
		try {
			return dao.darEstacionesDesActivadas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String logIN(int id)
	{
		try 
		{
			return dao.logIN(id);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public ArrayList<ProductoPedido> getRF12() {
		return dao.getRF12();
	}
	
	public ArrayList<Producto> darProductos()
	{
		ArrayList<Producto> resp =new ArrayList<Producto>();;
		try 
		{
			resp=dao.darProductos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	public ArrayList<PedidoCliente> darPedidosPorEntregar()
	{
		ArrayList<PedidoCliente> resp =new ArrayList<PedidoCliente>();;
		try 
		{
			resp=dao.darPedidosClientePorEntregar(idUsuarioLogueado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

	
	public ArrayList<PedidoCliente> darPedidos()
	{
		ArrayList<PedidoCliente> resp =new ArrayList<PedidoCliente>();;
		try 
		{
			resp=dao.darPedidos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	
	public void inicRF12()
	{
		dao.inicRF12();
	}
	

	public ArrayList<PedidoCliente> getRFC5(String filtrar, String info)
	{
		ArrayList<PedidoCliente> resp =new ArrayList<PedidoCliente>();
		try 
		{
			resp=dao.getRFC5(filtrar, info);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return resp;
	}
	
	public ArrayList<PedidoCliente> getRFC10(String tipo, int cantidad)
	{
		ArrayList<PedidoCliente> resp =new ArrayList<PedidoCliente>();
		try 
		{
			resp=dao.getRFC10(tipo, cantidad);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return resp;
	}
	
	public ArrayList<PedidoCliente> getRFC11(String tipo, int id)
	{
		ArrayList<PedidoCliente> resp =new ArrayList<PedidoCliente>();
		try 
		{
			resp=dao.getRFC11(tipo, id);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return resp;
	}
	
	public boolean empezoRF10()
	{
		return RF10;
	}
	public void empezarRF10()
	{
		RF10=true;
	}
	public boolean RF10(String idPedido, String idProducto, String nombreEtapa, int numSecuencia, int prueba) throws SQLException
	{
		return dao.rf10(idPedido,idProducto,nombreEtapa,numSecuencia, prueba);
	}
	public boolean empezoRF11()
	{
		return RF11;
	}
	public void empezarRF11()
	{
		RF11=true;
	}
	public boolean empezoRF13() {
		// TODO Auto-generated method stub
		return RF13;
	}
	public void empezarRF13()
	{
		RF13=true;
	}
	public boolean RF11(int idPedido) throws SQLException
	{
		return dao.rf11(idPedido);
	}
	public ArrayList getRFC1(String tipo, int rangoi, int rangof, Date fechaS, Date fechaE)
	{
		return dao.getRFC1(tipo, rangoi,rangof,fechaS, fechaE);
	}
	public ArrayList<ProductoPedido> agregarCarrito(int idProducto, int cantidad)
	{
		ArrayList<ProductoPedido> resp =new ArrayList<ProductoPedido>();
		try 
		{
			resp=dao.agregarCarrito(idProducto, cantidad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	
	public PedidoCliente terminarRF12(String fechaEsperada)
	{
		PedidoCliente resp = null;
		try 
		{
			resp=dao.terminarRF12(fechaEsperada);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	
	public ArrayList<Materia_Componente> terminarRF14(String fechaEntrega, int id)
	{
		ArrayList<Materia_Componente> resp =new ArrayList<Materia_Componente>();
		try 
		{
			resp=dao.RF14(fechaEntrega, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

	public boolean RF17Desactivar(int id)
	{
		try {
			return dao.RF17Desactivar(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();return false;
		}
	}
	
	public boolean RF17Activar(int id)
	{
		try {
			return dao.RF17Activar(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();return false;
		}
	} 
	public boolean RF13(int pedido) throws Exception {
		// TODO Auto-generated method stub
		return dao.RF13(pedido);
	}
	
	public ArrayList<EtapaProduccion> RFC8(String filtro, String info, String tiempoMin, String tiempoMax, String tipoMaterial, int cantMinMaterial, int cantMaxMaterial) throws Exception 
	{
		return dao.RFC8(filtro, info,tiempoMin,tiempoMax, tipoMaterial,cantMinMaterial, cantMaxMaterial);
	}

	public ArrayList<EtapaProduccion> RFC9(String filtro, String info, String tiempoMin, String tiempoMax, String tipoMaterial, int cantMinMaterial, int cantMaxMaterial) throws Exception 
	{
		return dao.RFC9(filtro, info,tiempoMin,tiempoMax, tipoMaterial,cantMinMaterial, cantMaxMaterial);
	}

	public void inicRF18() {
		// TODO Auto-generated method stub
		dao.inicRF18();
	}

	public ArrayList<ProductoPedido> getRF18() {
		// TODO Auto-generated method stub
		return dao.getRF18();
	}

	public PedidoCliente terminarRF18(String fechaEsperada) {
		PedidoCliente resp = null;
		try 
		{
			resp=dao.terminarRF18(fechaEsperada);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

}
