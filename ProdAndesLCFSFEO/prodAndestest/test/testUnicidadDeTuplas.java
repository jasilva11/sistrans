package test;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import junit.framework.TestCase;

public class testUnicidadDeTuplas extends TestCase
{
	//----------------------------------------------------
	//Constantes
	//----------------------------------------------------
	/**
	 * ruta donde se encuentra el archivo de conexi�n.
	 */
	private static final String ARCHIVO_CONEXION = "/conexion.properties";

	private Connection conexion;
	private String usuario;
	private String clave;
	private String cadenaConexion;
	/**
	 * Obtiene los datos necesarios para establecer una conexion
	 * Los datos se obtienen a partir de un archivo properties.
	 * @param path ruta donde se encuentra el archivo properties.
	 */
	public void inicializar(String path)
	{
		try
		{
			File arch = new File (path+ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);

			prop.load(in);
			in.close();

			cadenaConexion = prop.getProperty("url");
			usuario = prop.getProperty("usuario");
			clave = prop.getProperty("clave");
			final String driver = prop.getProperty("driver");
			Class.forName(driver);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que se encarga de crear la conexion con el driver manager
	 * a partir de los parametros recibidos.
	 * @param url direccion url de la base de datos a la que se desea conectar.
	 * @param usuario nombre del usuario que se va a conectar a la base de datos.
	 * @param clave clave de acceso a la base de datos.
	 * @throws SQLException si ocurre un error generando la conexion con la base de datos.
	 */
	private void establecerConexion(String url, String usuario, String clave)throws SQLException
	{
		try
		{
			conexion = DriverManager.getConnection(url, usuario, clave);
		}
		catch(SQLException e)
		{
			throw new SQLException ("ERROR: ConsultaDAO obteniendo una conexion.");
		}
	}

	/**
	 * Cierra la conexion activa a la base de datos.
	 * @param con objeto de conexion a la bse de datos.
	 * @throws SQLException Si se presentan errores cerrando la conexion a la base de datos.
	 */
	public void cerrarConexion(Connection connection)throws SQLException
	{
		try
		{
			connection.close();
			connection = null;
		}
		catch(SQLException e)
		{
			throw e;
		}
	}

	public void testUsuario()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO USUARIOS VALUES (700,'nombre','nacionalidad','direccion','email','ciudad','departamento',111,'tipo',1234567)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO USUARIOS VALUES (700,'nombre','nacionalidad','direccion','email','ciudad','departamento',111,'tipo',1234567)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public void testCliente()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO CLIENTES VALUES (1234631,160,'nombre','ciudad','direccion',1234567)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally{

			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO CLIENTES VALUES (1234631,160,'nombre','ciudad','direccion',1234567)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public void testProveedor()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO PROVEEDORES VALUES (100,20,10,'nombre','ciudad','direccion',1234567);";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO PROVEEDORES VALUES (100,20,10,'nombre','ciudad','direccion',1234567);";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public void testGerente()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO GERENTES VALUES (40)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO GERENTES VALUES (40)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public void testSupervisor()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO SUPERVISOR VALUES (200)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO SUPERVISOR VALUES (200)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public void testAdministrador()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO ADMINISTRADORES VALUES (1)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO ADMINISTRADORES VALUES (1)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testOperario()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO OPERARIOS VALUES (12,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO OPERARIOS VALUES (12,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testComponente()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO COMPONENTE VALUES ('nombre','kilogramos',102,12,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO COMPONENTE VALUES ('nombre','kilogramos',102,12,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testCompraComponenteMateria()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO COMPRACOMPONENTEMATERIA VALUES (102,10,10,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO COMPRACOMPONENTEMATERIA VALUES (102,10,10,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testEstacionProduccion()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO ESTACION_PRODUCCION VALUES (102,12,10)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO ESTACION_PRODUCCION VALUES (102,12,10)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testEtapaProduccion()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO ETAPA_PRODUCCION VALUES (102,'nombre',12,12,15)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO ETAPA_PRODUCCION VALUES (102,'nombre',12,12,15)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testMateriaPrima()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO MATERIA_PRIMA VALUES (102,'nombre','kilogramos',12,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO MATERIA_PRIMA VALUES (102,'nombre','kilogramos',12,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testNecesitaComponente()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO NECESITACOMPONENTE VALUES (89,88,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO NECESITACOMPONENTE VALUES (89,88,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testNecesitaMaterial()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO NECESITAMATERIAL VALUES (20,22,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO NECESITAMATERIAL VALUES (20,22,12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testPedidoCliente()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO PEDIDO_CLIENTE VALUES (102,1,TO_DATE('02/02/2014','DD/MM/YYYY'),'T',TO_DATE('04/02/2014','DD/MM/YYYY'))";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO PEDIDO_CLIENTE VALUES (102,1,TO_DATE('02/02/2014','DD/MM/YYYY'),'T',TO_DATE('04/02/2014','DD/MM/YYYY'))";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testPedidoProveedor()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO PEDIDO_PROVEEDOR VALUES (102,TO_DATE('02/02/2014','DD/MM/YYYY'),12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO PEDIDO_PROVEEDOR VALUES (102,TO_DATE('02/02/2014','DD/MM/YYYY'),12)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testProcesoProduccion()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO PROCESO_PRODUCCION VALUES (26,26)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO PROCESO_PRODUCCION VALUES (26,26)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testProducto()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO PRODUCTO VALUES (102,'nombre',12000)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO PRODUCTO VALUES (102,'nombre',12000)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testProductoStock()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO PRODUCTO_STOCK VALUES (101,10,'nombre')";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO PRODUCTO_STOCK VALUES (101,10,'nombre')";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void testProductoPedidos()
	{
		PreparedStatement state = null;
		String consulta = "INSERT INTO PRODUCTOSPEDIDOS VALUES (10,15,120)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta);
			state.execute(consulta);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta);
		}
		finally
		{

			if(state != null){
				try{
					state.close();
				}catch(SQLException e){
					fail("No deberia arrojar excepcion.");
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		String consulta2 = "INSERT INTO PRODUCTOSPEDIDOS VALUES (10,15,120)";
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			state = conexion.prepareStatement(consulta2);
			state.execute(consulta2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println(consulta2);
		}
		finally
		{
			if(state != null)
			{
				try
				{
					state.close();
				}
				catch(SQLException e)
				{
					//Debe pasar por aca.
				}
			}
			try 
			{
				cerrarConexion(conexion);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
