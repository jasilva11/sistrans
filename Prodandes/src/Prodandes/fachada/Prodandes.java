package Prodandes.fachada;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import Prodandes.dao.consultaDAO;
import Prodandes.vod.Cliente;
import Prodandes.vod.ComponentesProduccion;
import Prodandes.vod.EtapasDeProducion;
import Prodandes.vod.MateriasPrimas;
import Prodandes.vod.Producto;
import Prodandes.vod.Usuario;


public class Prodandes 
{
	/**
	 * Conexión con la clase que maneja la base de datos
	 */
	private consultaDAO dao;
	
    /**
     * Conexión a la base de datos
     */
    private Connection conexion;
	
	private ArrayList<Cliente> clientes;
	private ArrayList<Usuario> usuarios;


	

    
    // -----------------------------------------------------------------
    // Singleton
    // -----------------------------------------------------------------


    /**
     * Instancia única de la clase
     */
    private static Prodandes instancia;
    
    /**
     * Devuelve la instancia única de la clase
     * @return Instancia única de la clase
     */
    public static Prodandes darInstancia( )
    {
        if( instancia == null )
        {
            instancia = new Prodandes( );
        }
        return instancia;
    }
	
	/**
	 * contructor de la clase. Inicializa el atributo dao.
	 */
	private Prodandes()
	{
		dao = new consultaDAO();
		clientes = new ArrayList<Cliente>();
		usuarios= new ArrayList<Usuario>();
	}
	
	/**
	 * inicializa el dao, dándole la ruta en donde debe encontrar
	 * el archivo properties.
	 * @param ruta ruta donde se encuentra el archivo properties
	 */
	public void inicializarRuta(String ruta)
	{
		dao.inicializar();
	}
	
	public Cliente darCliente(int id)
	{
		for (int i = 0; i < clientes.size(); i++) 
		{
		if (clientes.get(i).getIdentificacion() == id)
		{
			return clientes.get(i);
		}
		}
		return null;
	}
	
	
	public void agregaCliente(Cliente z)
	{
	clientes.add(z);
dao.agregarCliente(z);
	}
	

	
	public String estaEnCapacidad(String nombre, int volumen, Date fecha)
	{
		//Existe el producto
		Producto x = dao.buscarProducto(nombre);
		String rta = "";
		//Hay en el inventario
		if ( x != null && x.getCantidad() >= volumen&& x.darEstado()== true)
		{
			int cantidad = x.getCantidad();
			x.setCantidad(cantidad - volumen);
			dao.actualizarProducto(x);
			rta = "Se despachara el producto protamente";
		}
		//hay que hacerlo
		else if (x != null)
		{
			rta = "Se necesitan: ";
			// se los materiales necesitados
			ArrayList<MateriasPrimas> mat = x.darMaterialesrequeridos();
			for (int i = 0; i < mat.size(); i++)
			{
				MateriasPrimas z = dao.darMaterial(mat.get(i).darNombre(), nombre);
				if (	z == null && z.darVolumen() == 0 )
					{
					rta += mat.get(i).darNombre();
					}
				

			}
			
			ArrayList<ComponentesProduccion> comp = x.darComponentesrequeridos();
			for (int i = 0; i < comp.size(); i++)
			{

				if (	dao.componentes(comp.get(i).getNombre()) == null)
					{
					rta += comp.get(i).getNombre();
					}

			}
			
			if (rta.equals(""))
			{
				
			return "Se producira en los siguientes dias";

			}
			else return rta;
			
		

		
		}
		else
		{
			return "no se conoce el producto";
		}
		
		
		
		
		return null;
		
		

		
		
		
		
	}
	
	
	public  String darEtapasEntreAnios(String x, String y) throws SQLException
	{
		dao.inicializar();
		return dao.darEtapasProduccion(x, y);
		
		
	}
	
	
	public boolean registrarMaterial(String nombre, double costo, String tipo, String unidad, int cantidad) throws SQLException
	{
		return dao.registrarMaterial(nombre, costo, tipo, unidad, cantidad);
	}
	
	public boolean registrarEtapa(String nombre, int id, int secuencia, int empleados, String fechaIncio, String fechaFin) throws SQLException
	{
		return dao.registrarEtapa(nombre, id, secuencia, empleados, fechaIncio, fechaFin);
	}
	
	public String actualizarInventario(String nombreP, int z) 
	{
		Producto x = dao.buscarProducto(nombreP);
		int asd = x.getCantidad();
		x.setCantidad(asd - z);
		dao.actualizarProducto(x);
		return "Se ha entregado exitosamente" + x.getNombre();
	
	}
	
	
    // ---------------------------------------------------
    // Métodos asociados a los casos de uso: Consulta
    // ---------------------------------------------------
    
	public ArrayList conMateriales(String parametro, String parametro2, String tipo) throws SQLException
	{
		return dao.buscarMateriales(parametro, parametro2, tipo);
	}
	
	public ArrayList conProveedores(String parametro, int numero, String tipo) throws SQLException
	{
		return dao.buscarProveedores(parametro, numero, tipo);
	}
	
	public ArrayList conOperarios(String pEtapa) throws SQLException
	{
		return dao.buscarOperarios(pEtapa);
	}

	public String darTipoUsuario(String usr,String pass) 
	{
		return dao.darTipoUsuario(usr,pass);
	}


}
