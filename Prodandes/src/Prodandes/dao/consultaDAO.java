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
	
	
	public ArrayList<MateriasPrimas> darMateriasDeUnProducto(String x ) throws SQLException
	{
		PreparedStatement prepStmt = null;
		ArrayList<MateriasPrimas> jesus = new ArrayList<MateriasPrimas>();
		inicializar();
		establecerConexion();
		



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
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE_PRODUCTO FROM PROVEEDORES RIGHT OUTER JOIN PRODUCTO ON MATERIA_PRIMA = MATERIAL WHERE MATERIA_PRIMA =" + "'" + parametro + "'" ;
		}

		else if(tipo.equalsIgnoreCase("vmenor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE_PRODUCTO, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM (PROVEEDORES RIGHT OUTER JOIN PRODUCTO ON MATERIA_PRIMA = MATERIAL)RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE VOLUMEN_MAXIMO <" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("vmayor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE_PRODUCTO, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM (PROVEEDORES RIGHT OUTER JOIN PRODUCTO ON MATERIA_PRIMA = MATERIAL)RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE VOLUMEN_MAXIMO >" + "'" + parametro+ "'" ;
		}

		else if(tipo.equalsIgnoreCase("tmenor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE_PRODUCTO, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM (PROVEEDORES RIGHT OUTER JOIN PRODUCTO ON MATERIA_PRIMA = MATERIAL)RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE TIEMPO_ENTREGA <" + "'" + parametro+ "'" ;
		}
		else if(tipo.equalsIgnoreCase("tmayor"))
		{
			sql ="SELECT IDENTIFICACION, MATERIA_PRIMA, VOLUMEN_MAXIMO, TIEMPO_ENTREGA, NOMBRE_PRODUCTO, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID FROM (PROVEEDORES RIGHT OUTER JOIN PRODUCTO ON MATERIA_PRIMA = MATERIAL)RIGHT OUTER JOIN PERSONAS ON IDENTIFICACION = ID_PERSONA WHERE TIEMPO_ENTREGA >" + "'" + parametro+ "'" ;
		}

		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );

		int contador = 0;
		System.out.println("A");

		while( resultado.next( ) && contador < numero) 
		{
			String nombre = resultado.getString( 6 );
			String codPos = resultado.getString( 7 );
			String direccion = resultado.getString( 8 );
			int telefono = Integer.parseInt(resultado.getString( 9 ));
			String tipoId = resultado.getString( 10 );
			int volumen = Integer.parseInt(resultado.getString(3));
			int tiempo = Integer.parseInt(resultado.getString(4));
			String material = resultado.getString(2);
			String producto = resultado.getString(5);
			int id = contador;
			System.out.println(nombre);
			registro = new Proveedores(direccion, nombre, telefono, codPos, tipoId, material,id, volumen, tiempo, producto, 0);
			proveedores.add(registro);
			contador++;
		}
		return proveedores;
	}

	public String cambiarEstado(int estacion) throws SQLException
	{
		String sql = "SELECT ESTADO FROM ESTACION_PRODUCCION WHERE CODIGO =" + estacion;

		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );

		String estado = "";

		while( resultado.next( )) 
		{
			estado = resultado.getString( 1 );
		}

		String sql4 = "SELECT max(ID_ESTACION) FROM ETAPAS_DE_ESTACION";

		PreparedStatement prepStmt4 = conexion.prepareStatement(sql4);
		ResultSet resultado2 = prepStmt4.executeQuery( sql4 );

		int mayor = 0;

		while( resultado2.next( ))  
		{
			mayor = Integer.parseInt(resultado2.getString( 1 ));
		}

		String sql2 = "UPDATE ETAPAS_DE_ESTACION SET ID_ESTACION = 1 WHERE ID_ESTACION = 1";

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

			PreparedStatement prepStmt5 = conexion.prepareStatement(sql5);
			ResultSet resultado3 = prepStmt5.executeQuery( sql5 );

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
			PreparedStatement prepStmt6 = conexion.prepareStatement(sql6);
			ResultSet resultado4 = prepStmt6.executeQuery( sql6 );
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

		PreparedStatement prepStmt2 = conexion.prepareStatement(sql);
		PreparedStatement prepStmt3 = conexion.prepareStatement(sql2);

		ResultSet rs = prepStmt2.executeQuery();
		ResultSet rs2 = prepStmt3.executeQuery();

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

		String sql = "SELECT CANTIDAD_MATERIAL, MATERIAL FROM MATERIALESDEPRODUCTOS WHERE PRODUCTO = " + pProducto;

		inicializar();

		establecerConexion();

		PreparedStatement prepStmt = conexion.prepareStatement(sql);
		ResultSet resultado = prepStmt.executeQuery( sql );

		while( resultado.next( )) 
		{
			materiales.add(resultado.getString( 2 ));
			cantidad.add(Integer.parseInt(resultado.getString( 2 )));
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
			
			PreparedStatement prepStmt2 = conexion.prepareStatement(sql2);
			ResultSet resultado2 = prepStmt2.executeQuery( sql2 );

			while( resultado2.next( )) 
			{
					String nombre = resultado2.getString( 7 );
					String codPos = resultado2.getString( 8 );
					String direccion = resultado2.getString( 9 );
					int cotizacion = Integer.parseInt(resultado2.getString( 3 ));
					int id = Integer.parseInt(resultado2.getString( 4 ));
					int telefono = Integer.parseInt(resultado2.getString( 10 ));
					String tipoId = resultado2.getString( 11 );
					int volumen = (Integer) cantidad.get(i);
					int tiempo = Integer.parseInt(resultado2.getString(2));
					String material = (String) materiales.get(i);
					String producto = pProducto;
					registro = new Proveedores(direccion, nombre, telefono, codPos, tipoId, material,id, volumen, tiempo, producto, cotizacion);
					proveedores.add(registro);
			}
		}
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
		PreparedStatement prepStmt = null;

		try {
			inicializar();
			establecerConexion();
			prepStmt = conexion.prepareStatement("SELECT ROL_EN_SISTEMA FROM USUARIOS WHERE LOGIN =" +usr+ "AND PALABRACLAVE = " +pass+ ";" );

			ResultSet rs = prepStmt.executeQuery();


			String tipo = rs.getString("ROL_EN_SISTEMA");
			return tipo;
		}
		catch (Exception e)
		{
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

	public boolean registrarMaterial(String nombre, double costo, String tipo, String unidad, int cantidad) throws SQLException
	{
		PreparedStatement prepStmt = null;
		String agregar = "INSERT INTO MATERIALES (NOMBRE, COSTO, TIPO, UNIDAD_MEDIDA, CANTIDAD_INICIAL)values (" + nombre + ",'" + costo + "', '" + tipo + "', '" + unidad +"',  " + cantidad +"');";


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

	public ComponentesProduccion componentes(String nombre)
	{




		String agregar = "SELECT * FROM COMPONENTE_PRODUCCION CP WHERE CP.NOMBRE = " + nombre + ";";
		PreparedStatement prepStmt = null;
		ComponentesProduccion x = null;

		try {
			establecerConexion();
			prepStmt = conexion.prepareStatement(agregar);
			ResultSet rs = prepStmt.executeQuery();

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


}
