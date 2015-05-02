package Prodandes.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.io.*;
import java.sql.*;
import java.util.*;

import Prodandes.vod.Cliente;
import Prodandes.vod.ComponentesProduccion;
import Prodandes.vod.EtapasDeProducion;
import Prodandes.vod.MateriasPrimas;
import Prodandes.vod.Operario;
import Prodandes.vod.PedidoMaterial;
import Prodandes.vod.Personas;
import Prodandes.vod.Producto;
import Prodandes.vod.Proveedores;

public class consultaDAO {

	//----------------------------------------------------
	//Constantes
	//----------------------------------------------------
	/**
	 * ruta donde se encuentra el archivo de conexión.
	 */
	private static final String ARCHIVO_CONEXION = "data/conexion.properties";

	private static final String ARCHIVO = "./data/log.txt";

	/**
	 * nombre de la tabla videos
	 */
	private static final String tablaVideo = "videos";


	/**
	 * nombre de la columna titulo_original en la tabla videos.
	 */
	private static final String tituloVideo = "titulo_original";

	/**
	 * nombre de la columna anyo en la tabla videos.
	 */
	private static final String anyoVideo = "anyo";


	//----------------------------------------------------
	//Consultas
	//----------------------------------------------------

	/**
	 * Consulta que devuelve isan, titulo, y año de los videos en orden alfabetico
	 */
	private static final String consultaVideosDefault="SELECT *, FROM "+tablaVideo;


	//----------------------------------------------------
	//Atributos
	//----------------------------------------------------
	/**
	 * conexion con la base de datos
	 */
	public Connection conexion;


	private static String usuario = "ISIS2304091510";
	private static String password = "ncullbuilt";
	private static String url = "jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod";

	
	Savepoint primero = null;
	Savepoint segundo = null;
	Savepoint tercero = null;

	
	public File archivo;


	/**
	 * constructor de la clase. No inicializa ningun atributo.
	 */
	public consultaDAO() 
	{		

	}

	// -------------------------------------------------
	// Métodos
	// -------------------------------------------------

	/**
	 * obtiene ls datos necesarios para establecer una conexion
	 * Los datos se obtienen a partir de un archivo properties.
	 * @param path ruta donde se encuentra el archivo properties.
	 */
	public void inicializar()
	{
		try
		{
			final String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	

	}

	/**
	 * Método que se encarga de crear la conexión con el Driver Manager
	 * a partir de los parametros recibidos.
	 * @param url direccion url de la base de datos a la cual se desea conectar
	 * @param usuario nombre del usuario que se va a conectar a la base de datos
	 * @param clave clave de acceso a la base de datos
	 * @throws SQLException si ocurre un error generando la conexión con la base de datos.
	 */
	private void establecerConexion( ) throws SQLException
	{
		try
		{
			conexion = DriverManager.getConnection(url,usuario,password);
		}
		catch( SQLException exception )
		{
			throw new SQLException( "ERROR: ConsultaDAO obteniendo una conexi—n." );
		}
	}

	/**
	 *Cierra la conexión activa a la base de datos. Además, con=null.
	 * @param con objeto de conexión a la base de datos
	 * @throws SistemaCinesException Si se presentan errores de conexión
	 */
	public void closeConnection(Connection connection) throws Exception {        
		try {
			connection.close();
			connection = null;
		} catch (SQLException exception) {
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexión.");
		}
	} 


	// ---------------------------------------------------
	// Métodos asociados a los casos de uso: Consulta
	// ---------------------------------------------------  



	public String darEtapasProduccion(String x , String y) throws SQLException
	{
		PreparedStatement prepStmt = null;
		ArrayList<Cliente> jesus = new ArrayList<Cliente>();

		establecerConexion();
		prepStmt = conexion.prepareStatement("SELECT MAX(ETP.CAPACIDAD_PRODUCCION) FROM ESTACION_PRODUCCION ETP INNER JOIN CAPACIDADDEPRODUCCION CA ON ETP.CODIGO = CA.CODIGO_ESTACION_PROD WHERE FECHA_INICIO > TO_DATE('" + x + "') and FECHA_FIN< TO_DATE('" +y +"'); ");
		ResultSet rs = prepStmt.executeQuery();

		int pdeudas = (int) rs.getLong("MAX(CA.NUMPRODUCTOSPRODUCIDOS)");
		int pnumeroRegistro = (int) rs.getLong("NUMEROREGISTRO");
		String pdireccion = rs.getString("DIRRECION");



		return pdireccion;



	}
	public MateriasPrimas darMaterial(String nombre , String producto)
	{
		String agregar = "SELECT NOMBRE, NOMBRE_COMPONENTE as LO_COMPONEN, COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL as VOLUMEN, COSTO, ID_ETAPA, FECHA_INICIO,FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN (SELECT NOMBRE, NOMBRE_COMPUESTO as COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL, COSTO, ID_ETAPA, FECHA_INICIO, FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN ((MATERIALES RIGHT OUTER JOIN PRODUCTO ON Nombre = Material)RIGHT OUTER JOIN ETAPAS_PRODUCCION ON Id_Etapa = identificador) ON NOMBRE_COMPONENTE = NOMBRE) ON NOMBRE_COMPUESTO = NOMBRE WHERE NOMBRE = '" + nombre + "'" ;

		PreparedStatement prepStmt = null;
		MateriasPrimas x = null;

		try {
			establecerConexion();

			ResultSet rs = prepStmt.executeQuery();

			String pNombre = rs.getString( "NOMBRE" );
			int pCosto = Integer.parseInt(rs.getString("COSTO"));
			String pTipo = rs.getString("TIPO");
			String pUnidadDeMedida = rs.getString("UNIDAD_MEDIDA");
			int pCantidad = Integer.parseInt(rs.getString("CANTIDAD_INICIAL"));
			int pCantidadRes = Integer.parseInt(rs.getString("CANTIDAD_RESERVADA"));

			String componente = rs.getString("NOMBRE_COMPONENTE"); 
			String compuesto = rs.getString("NOMBRE_COMPUESTO");
			Date inicio = rs.getDate("FRECHA_INICIO"); 
			Date ultimo = rs.getDate("FECHA_FINAL"); 


			x = new MateriasPrimas(pNombre,componente,compuesto, producto,  pTipo, pCosto, pCantidad, pCantidadRes, inicio, ultimo);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				closeConnection(conexion);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		}


		return x;
	}


	public boolean agREGAR(String x) throws Exception
	{
		PreparedStatement prepStmt = null;
		ArrayList<Cliente> jesus = new ArrayList<Cliente>();

		establecerConexion();
		prepStmt = conexion.prepareStatement(x);
		boolean rs = prepStmt.execute();
		return rs;

	}





	public ArrayList<Cliente> darClientes() throws SQLException
	{
		PreparedStatement prepStmt = null;
		ArrayList<Cliente> jesus = new ArrayList<Cliente>();

		establecerConexion();
		prepStmt = conexion.prepareStatement("SELECT * FROM CLIENTES C INNER JOIN PERSONAS P ON C.IDENTIFICACION =P.IDENTIFICACION ");
		ResultSet rs = prepStmt.executeQuery();

		int x = 0;		
		while(rs.next())
		{




			int pdeudas = (int) rs.getLong("DEUDAS");
			int pnumeroRegistro = (int) rs.getLong("NUMEROREGISTRO");

			String pdireccion = rs.getString("DIRRECION");
			String pnombre = rs.getString("NOMBRE");
			int ptelefono = (int) rs.getLong("TELEFONO");

			String pciudad = rs.getString("CODIGO_POSTAL");
			String ptipoId = rs.getString("TIPO_ID");
			int pAntiguedad = (int) rs.getLong("ANTIGUEDAD");

			String pnomRepresentanteLegal = rs.getString("NOM_RP_LEGAL");
			int pidentificacion = (int) rs.getLong("IDENTIFICACION");

			String soy = rs.getString("PERSONANATURAL");
			boolean ppersonaNatural = false;
			if (soy != null &&soy.equals("SI"))
			{
				ppersonaNatural = true;
			}




			Cliente asd = new Cliente(pdireccion, pnombre, ptelefono, pciudad, pidentificacion, ptipoId, pAntiguedad, pdeudas, pnumeroRegistro, pnomRepresentanteLegal, ppersonaNatural);

			jesus.add(asd);			
			x++;


		}
		return jesus;



	}



	public boolean agregarCliente(Cliente x)
	{


		PreparedStatement prepStmt = null;
		String agregar = "INSERT INTO Personas (Identificacion, NOMBRE  , CODIGO_POSTAL     , DIRRECION      , TELEFONO, TIPO_ID)values (" + x.getIdentificacion() + ",'"+x.getNombre()+"', '"+x.getcPostal() + "', '" +  x.getDireccion() +"',  " + x.getTelefono()+", '" +x.getTipoId()+"');";


		try {
			establecerConexion();

			prepStmt = conexion.prepareStatement(agregar);

			ResultSet rs = prepStmt.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;


		}
		return true;

	}

	public boolean registrarEtapa(String nombre, int secuencia, int empleados, Date fechaIncio, Date fechaFin) throws SQLException
	{
		PreparedStatement prepStmt = null;
		String agregar = "INSERT INTO ETAPAS (PERSONAL_REQUERIDO, NUMEROSECUENCIA, NOMBRE, IDENTIFICADOR)values (" + empleados + ",'" + secuencia + "', '" + nombre + "', '" +   "', " + fechaIncio +", '" + fechaFin +"');";


		try {
			establecerConexion();

			prepStmt = conexion.prepareStatement(agregar);

			ResultSet rs = prepStmt.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;


		}
		return true;
	}

	public Producto buscarProducto(String nombre)
	{


		PreparedStatement prepStmt = null;
		String agregar = "SELECT * FROM PRODUCTO p WHERE p.nombre ='" +nombre+"'" ;


		try {
			establecerConexion();

			prepStmt = conexion.prepareStatement(agregar);

			ResultSet rs = prepStmt.executeQuery();

			int cantidad = rs.getInt("cantidad");
			int pcostoProduccion = rs.getInt("costo_venta");
			String terminado = rs.getString("terminado");
			int idEtapa = rs.getInt("id_etapa");
			boolean resp  = false;
			if (terminado.equals("SI") )
			{
				resp =true;
			}





			Producto x = new Producto(pcostoProduccion, nombre ,resp,idEtapa);			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		}
		return null;

	}


	public ArrayList<String> darMateriasDeUnProducto(String x ) throws SQLException
	{
		ArrayList<String> jesus = new ArrayList<String>();
		inicializar();
		establecerConexion();
		String sql = "SELECT * FROM MATERIALESDEPRODUCTOS WHERE PRODUCTO = '"+x+"'";
		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );

		int id = 0;

		while( resultado.next( )) 
		{
			if(resultado.getString(1) != null)
			{
				jesus.add(resultado.getString(1) ) ;
				jesus.add(resultado.getString(3) );
			}
			else
			{
				break;
			}
		}


		return jesus;

	}

	public ArrayList<EtapasDeProducion> darEtapasDeUnProducto(String x ) throws SQLException
	{
		PreparedStatement prepStmt = null;
		ArrayList<EtapasDeProducion> jesus = new ArrayList<EtapasDeProducion>();
		inicializar();
		establecerConexion();




		return jesus;

	}
	public ArrayList<ComponentesProduccion> darComponenteDeUnProducto(String x ) throws SQLException
	{
		PreparedStatement prepStmt = null;
		ArrayList<ComponentesProduccion> jesus = new ArrayList<ComponentesProduccion>();
		inicializar();
		establecerConexion();




		return jesus;

	}




	public void actualizarProducto(Producto x) 
	{

		PreparedStatement prepStmt = null;
		String agregar = "UPDATE  PRODUCTO SET CANTIDAD= " + x.getCantidad()   +" WHERE p.nombre ='" +x.getNombre()+"'" ;


		try {
			establecerConexion();

			prepStmt = conexion.prepareStatement(agregar);
			prepStmt.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				closeConnection(conexion);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		}
		try {
			closeConnection(conexion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}


	public ArrayList buscarMateriales(String parametro, String parametro2, String tipo) throws SQLException
	{
		ArrayList materiales = new ArrayList();


		MateriasPrimas registro = null;

		String sql = "";

		if(tipo.equalsIgnoreCase("tipo"))
		{
			sql ="SELECT NOMBRE, NOMBRE_COMPONENTE as LO_COMPONEN, COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL as VOLUMEN, COSTO, ID_ETAPA, FECHA_INICIO,FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN (SELECT NOMBRE, NOMBRE_COMPUESTO as COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL, COSTO, ID_ETAPA, FECHA_INICIO, FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN ((MATERIALES RIGHT OUTER JOIN PRODUCTO ON Nombre = Material)RIGHT OUTER JOIN ETAPAS_PRODUCCION ON Id_Etapa = identificador) ON NOMBRE_COMPONENTE = NOMBRE) ON NOMBRE_COMPUESTO = NOMBRE WHERE TIPO =" + "'" + parametro + "'" ;
		}

		else if(tipo.equalsIgnoreCase("volumen"))
		{
			sql ="SELECT NOMBRE, NOMBRE_COMPONENTE as LO_COMPONEN, COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL as VOLUMEN, COSTO, ID_ETAPA, FECHA_INICIO,FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN (SELECT NOMBRE, NOMBRE_COMPUESTO as COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL, COSTO, ID_ETAPA, FECHA_INICIO, FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN ((MATERIALES RIGHT OUTER JOIN PRODUCTO ON Nombre = Material)RIGHT OUTER JOIN ETAPAS_PRODUCCION ON Id_Etapa = identificador) ON NOMBRE_COMPONENTE = NOMBRE) ON NOMBRE_COMPUESTO = NOMBRE WHERE CANTIDAD_INICIAL =" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("costo"))
		{
			sql ="SELECT NOMBRE, NOMBRE_COMPONENTE as LO_COMPONEN, COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL as VOLUMEN, COSTO, ID_ETAPA, FECHA_INICIO,FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN (SELECT NOMBRE, NOMBRE_COMPUESTO as COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL, COSTO, ID_ETAPA, FECHA_INICIO, FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN ((MATERIALES RIGHT OUTER JOIN PRODUCTO ON Nombre = Material)RIGHT OUTER JOIN ETAPAS_PRODUCCION ON Id_Etapa = identificador) ON NOMBRE_COMPONENTE = NOMBRE) ON NOMBRE_COMPUESTO = NOMBRE WHERE COSTO =" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("rango"))
		{
			sql ="SELECT NOMBRE, NOMBRE_COMPONENTE as LO_COMPONEN, COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL as VOLUMEN, COSTO, ID_ETAPA, FECHA_INICIO,FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN (SELECT NOMBRE, NOMBRE_COMPUESTO as COMPONE, NOMBRE_PRODUCTO, TIPO, CANTIDAD_INICIAL, COSTO, ID_ETAPA, FECHA_INICIO, FECHA_FINAL FROM COMPONE RIGHT OUTER JOIN ((MATERIALES RIGHT OUTER JOIN PRODUCTO ON Nombre = Material)RIGHT OUTER JOIN ETAPAS_PRODUCCION ON Id_Etapa = identificador) ON NOMBRE_COMPONENTE = NOMBRE) ON NOMBRE_COMPUESTO = NOMBRE WHERE to_date('"+parametro+"','dd/mm/yyyy') < FECHA_INICIO AND to_date('"+parametro2+"','dd/mm/yyyy')>FECHA_FINAL";
		}

		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );

		System.out.println("A");

		while( resultado.next( )) 
		{
			String nombre = resultado.getString( 1 );
			String loComponen = resultado.getString( 2 );
			String compone = resultado.getString( 3 );
			String producto = resultado.getString( 4 );
			String pTipo = resultado.getString( 5 );
			int volumen = Integer.parseInt(resultado.getString(6));
			int costo = Integer.parseInt(resultado.getString(7));
			int etapa = Integer.parseInt(resultado.getString(8));
			Date inicio = resultado.getDate( 9 );
			Date fin = resultado.getDate( 10 );
			System.out.println(nombre);
			registro = new MateriasPrimas(nombre, loComponen, compone, producto, tipo, volumen, costo, etapa, inicio, fin);
			materiales.add(registro);
		}
		return materiales;
	}

	public ArrayList buscarProveedores(String parametro, int numero, String tipo) throws SQLException
	{
		ArrayList proveedores = new ArrayList();

		Proveedores registro = null;

		String sql = "";

		if(tipo.equalsIgnoreCase("material"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM PROVEEDORES RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE MATERIA_PRIMA =" + "'" + parametro + "'" ;
		}

		else if(tipo.equalsIgnoreCase("vmenor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM PROVEEDORES RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE VOLUMEN_MAXIMO <" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("vmayor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM PROVEEDORES RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE VOLUMEN_MAXIMO >" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("tmenor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM PROVEEDORES RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE TIEMPO_ENTREGA <" + "'" + parametro+ "'" ;
		}
		else if(tipo.equalsIgnoreCase("tmayor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM PROVEEDORES RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE TIEMPO_ENTREGA >" + "'" + parametro+ "'" ;
		}

		inicializar();

		establecerConexion();

		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(conexion.TRANSACTION_READ_COMMITTED);
		Savepoint save1 = conexion.setSavepoint();

		ResultSet resultado = null;

		try 
		{
			PreparedStatement prepStmt = conexion.prepareStatement(sql);
			resultado = prepStmt.executeQuery( sql );
			prepStmt.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}
		
		int contador = 0;
		System.out.println("A");

		String sql2 ="";

		while( resultado.next( ) && contador < numero) 
		{
			String nombre = resultado.getString( 5 );
			String codPos = resultado.getString( 6 );
			String direccion = resultado.getString( 7 );
			int telefono = Integer.parseInt(resultado.getString( 8 ));
			String tipoId = resultado.getString( 9 );
			int volumen = Integer.parseInt(resultado.getString(3));
			int tiempo = Integer.parseInt(resultado.getString(4));
			String material = resultado.getString(2);
			int id = Integer.parseInt(resultado.getString(1));
			System.out.println(nombre);
			registro = new Proveedores(direccion, nombre, telefono, codPos, tipoId, material, id, volumen, tiempo, 0);

			sql2 = "SELECT ID_PEDIDO FROM PEDIDOS_MATERIAL WHERE ID_PROVEEDOR = " + id;

			ResultSet resultado2 = null;
			try 
			{
				PreparedStatement prepStmt2 = conexion.prepareStatement(sql2);
				resultado2 = prepStmt2.executeQuery( sql2 );
				prepStmt2.setQueryTimeout(100);
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
				conexion.rollback(save1);
			}

			while( resultado2.next( )) 
			{
				registro.agregarPedido(Integer.parseInt(resultado2.getString( 1 )));
			}

			sql2 = "SELECT PRODUCTO FROM MATERIALESDEPRODUCTOS WHERE MATERIAL = '" + material + "'";

			ResultSet resultado3 = null;
			try 
			{
				PreparedStatement prepStmt3 = conexion.prepareStatement(sql2);
				resultado3 = prepStmt3.executeQuery( sql2 );
				prepStmt3.setQueryTimeout(100);
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
				conexion.rollback(save1);
			}

			while( resultado3.next( )) 
			{
				registro.agregarProducto(resultado3.getString( 1 ));
			}

			proveedores.add(registro);
			contador++;

		}
		conexion.commit();
		return proveedores;
	}

	public String cambiarEstado(int estacion) throws SQLException
	{
		String sql = "SELECT ESTADO FROM ESTACION_PRODUCCION WHERE CODIGO =" + estacion;

		inicializar();

		establecerConexion();

		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(conexion.TRANSACTION_READ_COMMITTED);
		Savepoint save1 = conexion.setSavepoint();

		ResultSet resultado = null;
		try 
		{
			PreparedStatement prepStmt = conexion.prepareStatement(sql);
			resultado = prepStmt.executeQuery( sql );
			prepStmt.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}

		String estado = "";

		while( resultado.next( )) 
		{
			estado = resultado.getString( 1 );
		}

		String sql4 = "SELECT max(ID_ESTACION) FROM ETAPAS_DE_ESTACION";

		ResultSet resultado2 = null;
		try 
		{
			PreparedStatement prepStmt4 = conexion.prepareStatement(sql4);
			resultado2 = prepStmt4.executeQuery( sql4 );
			prepStmt4.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}

		int mayor = 0;

		while( resultado2.next( ))  
		{
			mayor = Integer.parseInt(resultado2.getString( 1 ));
		}

		String sql2 = "";

		if(estado.equalsIgnoreCase("ACTIVA"))
		{
			sql = "UPDATE ESTACION_PRODUCCION SET ESTADO = 'INACTIVA' WHERE CODIGO =" + estacion;
			if(estacion == mayor)
			{
				int x = estacion - 1;
				sql2 = "UPDATE ETAPAS_DE_ESTACION SET ID_ESTACION = " + x +" WHERE ID_ESTACION = "+ estacion;
			}
			else
			{
				int x = estacion + 1;
				sql2 = "UPDATE ETAPAS_DE_ESTACION SET ID_ESTACION = " + x +" WHERE ID_ESTACION = "+ estacion;
			}
		}
		else
		{
			sql = "UPDATE ESTACION_PRODUCCION SET ESTADO = 'ACTIVA' WHERE CODIGO =" + estacion;

			String sql5 = "SELECT COUNT (ID_ESTACION), ID_ESTACION FROM ETAPAS_DE_ESTACION GROUP BY ID_ESTACION";

			ResultSet resultado3 = null;
			try 
			{
				PreparedStatement prepStmt5 = conexion.prepareStatement(sql5);
				resultado3 = prepStmt5.executeQuery( sql5 );
				prepStmt5.setQueryTimeout(100);
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
				conexion.rollback(save1);
			}

			int mayor2 = 0;
			int cont = 0;
			int num = 0;

			while( resultado3.next( ))  
			{
				cont = Integer.parseInt(resultado3.getString( 1 ));
				if(cont > mayor2)
				{
					mayor2 = cont;
					num = Integer.parseInt(resultado3.getString( 2 ));
				}
			}

			String sql6 = "SELECT ID_ETAPA FROM ETAPAS_DE_ESTACION WHERE ID_ESTACION =" + num;

			ResultSet resultado4 = null;
			try 
			{
				PreparedStatement prepStmt6 = conexion.prepareStatement(sql6);
				resultado4 = prepStmt6.executeQuery( sql6 );
				prepStmt6.setQueryTimeout(100);
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
				conexion.rollback(save1);
			}

			ArrayList etapas = new ArrayList();
			int contador = 0;
			int res = 0;
			while( resultado4.next( ) && contador<cont/2)  
			{
				res = Integer.parseInt(resultado4.getString( 1 ));
				etapas.add(res); 
				contador++;
			}

			int i = 0;
			while(i<etapas.size())
			{
				sql2 = "UPDATE ETAPAS_DE_ESTACION SET ID_ESTACION = " + estacion +" WHERE ID_ESTACION = "+ num + " AND ID_ETAPA = " + etapas.get(i);
				i++;
			}
		}

		try 
		{
			PreparedStatement prepStmt2 = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt2.executeQuery();
			prepStmt2.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}

		try 
		{
			PreparedStatement prepStmt3 = conexion.prepareStatement(sql2);
			ResultSet rs2 = prepStmt3.executeQuery();
			prepStmt3.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}
		conexion.commit();
		return estado;
	}

	public ArrayList buscarOperarios(String etapa) throws SQLException
	{
		ArrayList operarios = new ArrayList();

		Operario registro = null;

		String sql = "SELECT * FROM OPERARIOS RIGHT OUTER JOIN PERSONAS ON OPERARIOS.IDENTIFICACION_PERSONA = PERSONAS.IDENTIFICACION WHERE PRODUCIDO >= all(SELECT PRODUCIDO FROM OPERARIOS WHERE AREA_ENCARGADA = "+ etapa +") AND AREA_ENCARGADA = "+ etapa;

		inicializar();

		establecerConexion();

		Statement st = conexion.createStatement( );

		ResultSet resultado = st.executeQuery( sql );

		System.out.println("A");

		while( resultado.next( )) 
		{
			String cargo = resultado.getString( 2 );
			int cuenta = Integer.parseInt(resultado.getString(3));
			int duracion = Integer.parseInt(resultado.getString(4));
			int id = Integer.parseInt(resultado.getString(5));
			int producido = Integer.parseInt(resultado.getString(6));
			String nombre = resultado.getString( 8 );
			String codigoPostal = resultado.getString( 9 );
			String direccion = resultado.getString( 10 );
			int telefono = Integer.parseInt(resultado.getString(11));
			String tipoID = resultado.getString( 12 );
			System.out.println(nombre);
			registro = new Operario(direccion, nombre, telefono, codigoPostal, id, tipoID, Integer.parseInt(etapa), cargo, cuenta, duracion);
			operarios.add(registro);
		}
		return operarios;
	}

	public ArrayList generarPedidos(String pProducto, String tipo) throws SQLException
	{
		ArrayList proveedores = new ArrayList();

		ArrayList materiales = new ArrayList();

		ArrayList cantidad = new ArrayList();

		Proveedores registro = null;

		String sql = "SELECT CANTIDAD_MATERIAL, MATERIAL FROM MATERIALESDEPRODUCTOS WHERE PRODUCTO = '" + pProducto +"'";

		inicializar();

		establecerConexion();

		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(conexion.TRANSACTION_READ_COMMITTED);
		Savepoint save1 = conexion.setSavepoint();

		ResultSet resultado = null;
		try 
		{
			PreparedStatement prepStmt = conexion.prepareStatement(sql);
			resultado = prepStmt.executeQuery( sql );
			prepStmt.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}

		while( resultado.next( )) 
		{
			materiales.add(resultado.getString( 2 ));
			cantidad.add(Integer.parseInt(resultado.getString( 1 )));
		}

		String sql2 ="";

		for(int i = 0; i < materiales.size() && i < materiales.size(); i++)
		{
			if(tipo.equalsIgnoreCase("costo"))
			{
				sql2="SELECT * FROM PROVEEDORES RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE MATERIA_PRIMA = '" + materiales.get(i) + "' AND VOLUMEN_MAXIMO >= "+ cantidad.get(i) +" AND COTIZACION <= all(SELECT COTIZACION FROM PROVEEDORES WHERE MATERIA_PRIMA = '"+ materiales.get(i) +"')";
			}
			else
			{
				sql2="SELECT * FROM PROVEEDORES RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE MATERIA_PRIMA = '" + materiales.get(i) + "' AND VOLUMEN_MAXIMO >= "+ cantidad.get(i) +" AND TIEMPO_ENTREGA <= all(SELECT TIEMPO_ENTREGA FROM PROVEEDORES WHERE MATERIA_PRIMA = '"+ materiales.get(i) +"')";
			}

			ResultSet resultado2 = null;
			try 
			{
				PreparedStatement prepStmt2 = conexion.prepareStatement(sql2);
				resultado2 = prepStmt2.executeQuery( sql2 );
				prepStmt2.setQueryTimeout(100);
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
				conexion.rollback(save1);
			}

			while( resultado2.next( )) 
			{
				String nombre = resultado2.getString( 7 );
				String codPos = resultado2.getString( 8 );
				String direccion = resultado2.getString( 9 );
				int cotizacion = Integer.parseInt(resultado2.getString( 3 ));
				int id = Integer.parseInt(resultado2.getString( 1 ));
				int telefono = Integer.parseInt(resultado2.getString( 10 ));
				String tipoId = resultado2.getString( 11 );
				int volumen = (Integer) cantidad.get(i);
				int tiempo = Integer.parseInt(resultado2.getString(4));
				String material = (String) materiales.get(i);
				String producto = pProducto;
				registro = new Proveedores(direccion, nombre, telefono, codPos, tipoId, material, id, volumen, tiempo, cotizacion);
				proveedores.add(registro);
			}
		}
		conexion.commit();
		return proveedores;
	}

	public ArrayList<EtapasDeProducion> darMayorMovimientoSistema ()
	{
		PreparedStatement prepStmt = null;

		ArrayList<EtapasDeProducion> videos = new ArrayList<EtapasDeProducion>();
		EtapasDeProducion vidValue = new EtapasDeProducion();

		return videos;
	}

	public String darTipoUsuario(String usr, String pass) 
	{

		try {
			inicializar();

			establecerConexion();

			String sql= "SELECT * FROM USUARIOS U WHERE U.LOGIN = '" +usr+ "' AND U.PALABRACLAVE = '" +pass+ "'" ;

			PreparedStatement prepStmt = conexion.prepareStatement(sql);
			ResultSet resultado = prepStmt.executeQuery( sql );
            
			String tipo = "";

			while( resultado.next( )) 
			{
				if(resultado.getString(1) != null)
				{
					tipo = resultado.getString(1) ;

				}
			}
			return tipo;
		}
		catch (Exception e)
		{
			System.out.print(e);

			return "No existe";
		}
	}

	public boolean registrarEtapa(String nombre, int id, int secuencia, int empleados, String fechaIncio, String fechaFin) throws SQLException
	{
		PreparedStatement prepStmt = null;
		String agregar = "INSERT INTO ETAPAS (PERSONAL_REQUERIDO, NUMEROSECUENCIA, NOMBRE, IDENTIFICADOR)values (" + empleados + ",'" + secuencia + "', '" + nombre + "', '" + id +"',  " + fechaIncio +", '" + fechaFin +"');";


		try 
		{
			inicializar();

			establecerConexion();

			prepStmt = conexion.prepareStatement(agregar);

			ResultSet rs = prepStmt.executeQuery();

		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public PedidoMaterial crearPedido(int pIdProveedor, String pMaterial, int pCantidad, int pTiempo, int pCosto) throws SQLException
	{
		String sql = "SELECT max(ID_PEDIDO) FROM PEDIDOS_MATERIAL";

		inicializar();

		establecerConexion();

		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(conexion.TRANSACTION_READ_COMMITTED);
		Savepoint save1 = conexion.setSavepoint();

		ResultSet resultado = null;
		try 
		{
			PreparedStatement prepStmt = conexion.prepareStatement(sql);
			resultado = prepStmt.executeQuery( sql );
			prepStmt.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}

		int id = 0;

		while( resultado.next( )) 
		{
			if(resultado.getString(1) != null)
			{
				id = Integer.parseInt(resultado.getString(1)) + 1;
			}
		}

		String sql2 = "INSERT INTO PEDIDOS_MATERIAL (ID_PEDIDO, ID_PROVEEDOR, MATERIAL, CANTIDAD, TIEMPO_ENTREGA, COSTO) VALUES (" + id + ", " + pIdProveedor + ", '" + pMaterial + "'," + pCantidad + ", " + pTiempo + ", " + pCosto + ")";

		ResultSet resultado2 = null;
		try 
		{
			PreparedStatement prepStmt2 = conexion.prepareStatement(sql2);
			resultado2 = prepStmt2.executeQuery( sql2 );
			prepStmt2.setQueryTimeout(100);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			conexion.rollback(save1);
		}

		PedidoMaterial pedido = new PedidoMaterial(id, pIdProveedor, pMaterial, pCantidad, pTiempo, pCosto);

		conexion.commit();
		return pedido;
	}

	public boolean registrarMaterial(String nombre, double costo, String tipo, String unidad, int cantidad) throws SQLException
	{
		PreparedStatement prepStmt = null;
		String agregar = "INSERT INTO MATERIALES (NOMBRE, COSTO, TIPO, UNIDAD_MEDIDA, CANTIDAD_INICIAL)values (" + nombre + ",'" + costo + "', '" + tipo + "', '" + unidad +"',  " + cantidad +"');";


		try {
			establecerConexion();

			prepStmt = conexion.prepareStatement(agregar);

			ResultSet rs = prepStmt.executeQuery();
			prepStmt.setQueryTimeout(10);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;


		}
		return true;
	}

	public ComponentesProduccion componentes(String nombre)
	{




		String agregar = "SELECT * FROM COMPONENTE_PRODUCCION CP WHERE CP.NOMBRE = " + nombre + ";";
		PreparedStatement prepStmt = null;
		ComponentesProduccion x = null;

		try {
			establecerConexion();
			prepStmt = conexion.prepareStatement(agregar);
			ResultSet rs = prepStmt.executeQuery();
			prepStmt.setQueryTimeout(10);

			String pNombre = rs.getString( "NOMBRE" );
			int id = rs.getInt("ID_COMPONENTE");
			int vol = rs.getInt("VOLUMEN_INICIAL");



			x = 	new ComponentesProduccion(pNombre, vol);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				closeConnection(conexion);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		}


		return x;


	}

	public ArrayList darPedidosBusqueda(String parametro, int numero, String tipo)throws Exception
	{
		ArrayList clientes = new ArrayList();


		Cliente jesus = null;

		String sql = "";

		if(tipo.equalsIgnoreCase("UNIANDEADES_REQMAYOR"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE UNIANDEADES_REQ " + ">'"	+ parametro +"'"  ;

		}

		else if(tipo.equals("UNIANDEADES_REQMENOR"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE UNIANDEADES_REQ " + "<'"	+ parametro +"'"  ;
		}
		else if(tipo.equals("COSTO_VENTAMAYOR"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE COSTO_VENTA " + ">'"	+ parametro +"'"  ;
		}
		else if(tipo.equals("COSTO_VENTAMENOR"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE COSTO_VENTA " + "<'"	+ parametro +"'"  ;
		}
		else if(tipo.equals("UNIDADES_PRODUCCIONMAYOR"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE UNIDADES_PRODUCCION " + ">'"	+ parametro +"'"  ;
		}
		else if(tipo.equals("UNIDADES_PRODUCCIONMENOR"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE UNIDADES_PRODUCCION " + "<'"	+ parametro +"'"  ;
		}

		else if(tipo.equals("CANTIDAD_MATERIALMAYORA"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE CANTIDAD_MATERIAL " + ">'"	+ parametro +"'"  ;
		}
		else if(tipo.equals("CANTIDAD_MATERIALMENORA"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE CANTIDAD_MATERIAL " + "<'"	+ parametro +"'"  ;
		}
		else if(tipo.equalsIgnoreCase("TIEMPO_ENTREGAMAYOR"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE TIEMPO_ENTREGA " + ">'"	+ parametro +"'"  ;
		}
		else if(tipo.equalsIgnoreCase("TIEMPO_ENTREGAMenor"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE TIEMPO_ENTREGA " + "<'"	+ parametro +"'"  ;
		}


		else if(tipo.equalsIgnoreCase("TIEMPO_ENTREGAMenor"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE TIEMPO_ENTREGA " + "<'"	+ parametro +"'"  ;
		}
		else if(tipo.equalsIgnoreCase("TIEMPO_ENTREGAMenor"))
		{
			sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE TIEMPO_ENTREGA " + "<'"	+ parametro +"'"  ;
		}

		else
		{			

			sql = "SELECT * FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE LEFT OUTER JOIN CLIENTES C ON PC.ID__CLIENTE= C.IDENTIFICACION LEFT OUTER JOIN PEDIDOS_MATERIAL PD ON M.NOMBRE = PD.MATERIAL WHERE "+ tipo + " = '"+ parametro +"'";
		}


		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );
		prepStmt.setQueryTimeout(10);

		int contador = 0;

		while( resultado.next( ) && contador != numero) 
		{
			contador++;

			int id = Integer.parseInt(resultado.getString( 1 ));
			int deudas = Integer.parseInt(resultado.getString( 19));
			int numReg = Integer.parseInt(resultado.getString( 20 ));
			int antiguedad = Integer.parseInt(resultado.getString( 21 ));
			String nomREPL = resultado.getString( 22 );

			String person = resultado.getString( 23 );
			boolean personaNatural = false;
			if (person == null ||person.equals("TRUE") )
			{
				personaNatural = true;
			}

			jesus = new Cliente("", "", 0, "", id, "", antiguedad, deudas, numReg, nomREPL, personaNatural);

			String material = resultado.getString( 9 );

			String infoProducto = "NO";
			boolean aux = false;
			if (material != null )
			{
				String producto = resultado.getString( 2 );

				int unidades = Integer.parseInt(resultado.getString( 3 ));
				int unidadesDisponibles = Integer.parseInt(resultado.getString( 6 ));
				int costo = Integer.parseInt(resultado.getString( 5 ));
				int unidadesEnEspera = 0;

				boolean estado= false;
				String jesusMio = resultado.getString( 7 );
				if (jesusMio != null && jesusMio.equals("TRUE"))
				{
					estado = true;
				}
				if(!estado)
				{
					unidadesEnEspera = Integer.parseInt(resultado.getString( 8 ));

				}
				String idPedido = resultado.getString( 24 );



				for (int i = 0; i < clientes.size() ; i++)
				{

					Cliente algo =(Cliente) clientes.get(i);
					if (algo.getIdentificacion() == id)
					{

						ArrayList<String> mat = algo.darProductos();

						for (int j = 0; j < mat.size(); j++)
						{

							String productoDesado = mat.get(j);
							System.out.print( productoDesado);

							if(productoDesado.startsWith(producto))
							{

								String suma = "";

								if(idPedido==null)
								{
									suma = "</p> - " + resultado.getString( 9 ) + " con  " + resultado.getString( 11 )+  " de cantidad necesitada. " + "Este material es de tipo: " +resultado.getString( 14 )+ " y en el inventario hay " + resultado.getString( 16 ) +  resultado.getString( 15 ) + " con " + resultado.getString( 18 ) +  resultado.getString( 15 )+ " reservado " + "</p>";

								}
								else
								{

									suma = "</p> -" + resultado.getString( 9 ) + " con  " + resultado.getString( 11 )+  " de cantidad necesitada. " + "Este material es de tipo: " +resultado.getString( 14 )+ " y en el inventario hay " + resultado.getString( 16 ) +  resultado.getString( 15 ) + " con " + resultado.getString( 18 ) +  resultado.getString( 15 )+ " reservado " + " Dado que no estan todos materiales, existe un pedido en progreso, con el id: "
											+idPedido + " hecho al proveedor " + resultado.getString( 24 )+ ", de donde se solicitan" + resultado.getString( 25 )+  " unidades,con un tiempo de entrega aproximado de: " + resultado.getString( 26 ) + " horas "+ "a un costo de" +resultado.getString( 27 ) +   "</p>"  ;


								}

								productoDesado =productoDesado + suma;
								mat.remove(j);
								mat.add(productoDesado);

								aux = true;
								break;
							}


						}

					}
				}


				if (idPedido == null && !aux)
				{
					infoProducto = producto +  " tiene estas unidades requeridas: " + unidades + ", se disponen de "+ unidadesDisponibles + " unidades en el inventario, con un costo unitario de " +costo + " y existen " + unidadesEnEspera + " unidades en espera de ser producidas."
							+ "</p>" + "El producto esta compuesto de los siguientes materiales: "  + "</p> -" + (resultado.getString( 9 ) + " con  " + resultado.getString( 11 )+  " de cantidad necesitada. " + "Este material es de tipo: " +resultado.getString( 14 )+ " y en el inventario hay " + resultado.getString( 16 ) +  resultado.getString( 15 ) + " con " + resultado.getString( 18 ) +  resultado.getString( 15 )+ " reservado " + "</p>");
				}
				else
				{
					infoProducto = producto +  " tiene estas unidades requeridas: " + unidades + ", se disponen de "+ unidadesDisponibles + " unidades en el inventario, con un costo unitario de " +costo + " y existen " + unidadesEnEspera + " unidades en espera de ser producidas."
							+ "</p>  " + "El producto esta compuesto de los siguientes materiales: "  + " </p> -" + (resultado.getString( 9 ) + " con  " + resultado.getString( 11 )+  " de cantidad necesitada. " + "Este material es de tipo: " +resultado.getString( 14 )+ " y en el inventario hay " + resultado.getString( 16 ) +  resultado.getString( 15 ) + " con " + resultado.getString( 18 ) +  resultado.getString( 15 )+ " reservado "
									+ " Dado que no estan todos materiales, existe un pedido en progreso, con el id: "
									+idPedido + " hecho al proveedor de id " + resultado.getString( 24 )+ ", de donde se solicitan " + resultado.getString( 25 )+  " unidades,con un tiempo de entrega aproximado de: " + resultado.getString( 28 ) + " horas "+ "a un costo de" +resultado.getString( 29 ) +   "</p>" ) ;

				}

			}
			else
			{
				String producto = resultado.getString( 2 );

				int unidades = Integer.parseInt(resultado.getString( 3 ));
				int unidadesDisponibles = Integer.parseInt(resultado.getString( 6 ));
				int costo = Integer.parseInt(resultado.getString( 5 ));
				int unidadesEnEspera = 0;

				boolean estado= false;
				String jesusMio = resultado.getString( 7 );
				if (jesusMio != null && jesusMio.equals("TRUE"))
				{
					estado = true;
				}
				if(estado)
				{
					unidadesEnEspera = Integer.parseInt(resultado.getString( 8 ));

				}

				infoProducto = producto +  " tiene estas unidades requeridas: " + unidades + ", se disponen de "+ unidadesDisponibles + " unidades en el inventario, con un costo unitario de " +costo + " y " + unidadesEnEspera + " unidades en espera de ser producidas"
						+ "\n" + "Este producto, no necesita ningun material para su fabricacion "  ;


			}
			boolean papitas = false;

			if(!aux)
			{
				for (int i = 0; i < clientes.size() ; i++)
				{

					Cliente algo =(Cliente) clientes.get(i);
					if (algo.getIdentificacion() == id)
					{

						algo.agregarProducto(infoProducto);
						papitas = true;
					}
				}
				if (!papitas)
				{

					jesus.agregarProducto(infoProducto);
					clientes.add(jesus);
				}
			}
		}

		return clientes;
	}




	public ArrayList darClientesBusqueda(String parametro, int numero, String tipo)throws Exception
	{
		ArrayList clientes = new ArrayList();


		Cliente jesus = null;

		String sql = "";

		if(tipo.equalsIgnoreCase("id"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE IDENTIFICACION =" + "'" + parametro + "'" ;
		}

		else if(tipo.equalsIgnoreCase("deudasMayor"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE DEUDAS >" + "'" + parametro+ "'" ;
		}
		else if(tipo.equalsIgnoreCase("deudasMenor"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE DEUDAS <" + "'" + parametro+ "'" ;
		}


		else if(tipo.equalsIgnoreCase("numReg"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE NUMEROREGISTRO =" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("antiguedadMayor"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE ANTIGUEDAD >" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("antiguedadMenor"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE ANTIGUEDAD <" + "'" + parametro+ "'" ;
		}
		else if(tipo.equalsIgnoreCase("nombreRepLeg"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE NOM_RP_LEGAL =" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("pnNatural"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE PERSONANATURAL =" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("numPedidosMayor"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE UNIANDEADES_REQ >" + "'" + parametro+ "'" ;
		}
		else if(tipo.equalsIgnoreCase("numPedidosMenor"))
		{
			sql ="SSELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE UNIANDEADES_REQ <" + "'" + parametro+ "'" ;
		}
		else if(tipo.equalsIgnoreCase("prodPed"))
		{
			sql ="SELECT * FROM CLIENTES C LEFT OUTER JOIN PEDIDOSCLIENTE PC ON C.IDENTIFICACION=PC.ID__CLIENTE  LEFT OUTER JOIN PERSONAS P ON C.IDENTIFICACION = P.ID_PERSONA WHERE NOM_PRODUCTO =" + "'" + parametro+ "'" ;
		}


		inicializar();

		establecerConexion();
		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );
		prepStmt.setQueryTimeout(10);
		
	

		int contador = 0;
		String sql2 ="";

		while( resultado.next( ) && contador != numero) 
		{
			contador++;

			int id = Integer.parseInt(resultado.getString( 1 ));
			int deudas = Integer.parseInt(resultado.getString( 2));
			int numReg = Integer.parseInt(resultado.getString( 3 ));
			int antiguedad = Integer.parseInt(resultado.getString( 4 ));
			String nomREPL = resultado.getString( 5 );

			String person = resultado.getString( 6 );
			boolean personaNatural = false;
			if (person == null ||person.equals("TRUE") )
			{
				personaNatural = true;
			}
			String nombre = resultado.getString( 11 );

			jesus = new Cliente("", nombre, 0, "", id, "", antiguedad, deudas, numReg, nomREPL, personaNatural);

			String producto = resultado.getString( 8 );

			String infoProducto = "NO";
			if (producto != null )
			{
				int unidades = Integer.parseInt(resultado.getString( 9 ));
				infoProducto = producto +  " con estas unidades requeridas: " + unidades;
			}
			boolean papitas = false;
			for (int i = 0; i < clientes.size() ; i++)
			{
				Cliente algo =(Cliente) clientes.get(i);
				if (algo.getIdentificacion() == id)
				{
					algo.agregarProducto(infoProducto);
					papitas = true;
				}
			}
			if (!papitas)
			{
				clientes.add(jesus);
			}
		}
		return clientes;
	}



	public void cacelarPedidosCliente(int i, String producto) throws Exception 
	{
		String sql = "DELETE FROM PEDIDOSCLIENTE WHERE ID__CLIENTE = "+ i+"  AND NOM_PRODUCTO = '"+producto+ "'";

		inicializar();

		establecerConexion();
		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(conexion.TRANSACTION_READ_COMMITTED);

		primero = conexion.setSavepoint("jesusCristo");
		

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
        
		int resultado = prepStmt.executeUpdate( sql );
		prepStmt.setQueryTimeout(10);
        conexion.commit();
		// TODO Auto-generated method stub

	}

	public int darUnidadesEnEsperaDeSerProducidas(int i,
			String producto) throws Exception
	{

		String sql = "SELECT UNIANDEADES_REQ FROM PEDIDOSCLIENTE WHERE ID__CLIENTE = "+ i+" AND NOM_PRODUCTO = '"+producto+ "'";

		inicializar();

		establecerConexion();
		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );
		prepStmt.setQueryTimeout(10);

		int id = 0;

		while( resultado.next( )) 
		{
			if(resultado.getString(1) != null)
			{
				id = Integer.parseInt(resultado.getString(1)) ;
			}
			else
			{
				throw new Exception("ERROR");
			}
		}
		return id;


	}

	public int darUnidadesProducto(String producto) throws Exception 
	{
		String sql = "SELECT UNIDADES_ESPERA FROM PRODUCTO WHERE NOMBRE_PRODUCTO = '"+producto+ "'";

		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );
		prepStmt.setQueryTimeout(10);

		int id = 0;

		while( resultado.next( )) 
		{
			if(resultado.getString(1) != null)
			{
				id = Integer.parseInt(resultado.getString(1)) ;
			}
			else
			{
				break;
			}
		}
		return id;
	}

	public void actualizarUnidadesEnEspera(int unidadesFinales,String producto) throws SQLException 
	{
		String sql = "UPDATE PRODUCTO SET UNIDADES_ESPERA = "+ unidadesFinales+" WHERE NOMBRE_PRODUCTO = '"+producto+ "'";

		inicializar();
		establecerConexion();
		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(conexion.TRANSACTION_READ_COMMITTED);
		segundo = conexion.setSavepoint("opeth");
		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		int resultado = prepStmt.executeUpdate( sql );
		prepStmt.setQueryTimeout(10);
		conexion.commit();
	}

	public void actualizarCantidadReservada(int unidadesFinales,String producto) throws SQLException 
	{
		String sql = "UPDATE MATERIALES SET CANTIDAD_RESERVADA =  "+ unidadesFinales+" WHERE NOMBRE = '"+producto+ "'";

		inicializar();

		establecerConexion();

		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(conexion.TRANSACTION_READ_COMMITTED);

		tercero = conexion.setSavepoint("yeah");
		
		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		int resultado = prepStmt.executeUpdate( sql );
		prepStmt.setQueryTimeout(10);
		conexion.commit();

	}

	public int darCantidadReservadaMaterial(String material) throws SQLException
	{

		String sql = "SELECT CANTIDAD_RESERVADA FROM MATERIALES WHERE NOMBRE = '"+material+ "'";

		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );
		prepStmt.setQueryTimeout(10);


		int id = 0;

		while( resultado.next( )) 
		{
			if(resultado.getString(1) != null)
			{
				id = Integer.parseInt(resultado.getString(1)) ;
			}
			else
			{
				break;
			}
		}
		return id;
	}

	public void hiperRollback() throws SQLException 
	{
		if (primero != null)
		{
		conexion.rollback(primero);
		}
		if (segundo != null)
		{
		conexion.rollback(segundo);
		}
		if (tercero != null)
		{
		conexion.rollback(tercero);

		}

		// TODO Auto-generated method stub
		
	}

	public ArrayList darPedidosBusqueda(String tipo, int costoMayor) throws SQLException
	{
		ArrayList pedidos = new ArrayList();


		String jesus = "";

		String sql = "SELECT *FROM PEDIDOSCLIENTE PC LEFT OUTER JOIN PRODUCTO P ON P.NOMBRE_PRODUCTO = PC.NOM_PRODUCTO LEFT OUTER JOIN MATERIALESDEPRODUCTOS MDP ON PC.NOM_PRODUCTO = MDP.PRODUCTO LEFT OUTER JOIN MATERIALES M ON  MDP.MATERIAL = M.NOMBRE WHERE TIPO = " + tipo + " AND COSTO_VENTA >'"	+ costoMayor +"'"  ;
		

	


		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );
		prepStmt.setQueryTimeout(10);

		int contador = 0;

		while( resultado.next( )) 
		{
			contador++;

			int id = Integer.parseInt(resultado.getString( 1 ));
			String material = resultado.getString( 9 );

			String infoProducto = "NO";
			boolean aux = false;
			if (material != null )
			{
				String producto = resultado.getString( 2 );

				int unidades = Integer.parseInt(resultado.getString( 3 ));
				int unidadesDisponibles = Integer.parseInt(resultado.getString( 6 ));
				int costo = Integer.parseInt(resultado.getString( 5 ));
				int unidadesEnEspera = 0;

				boolean estado= false;
				String jesusMio = resultado.getString( 7 );
				if (jesusMio != null && jesusMio.equals("TRUE"))
				{
					estado = true;
				}
				if(!estado)
				{
					unidadesEnEspera = Integer.parseInt(resultado.getString( 8 ));

				}
				String idPedido = resultado.getString( 24 );



				
	}
}
		return pedidos;

	}
}
