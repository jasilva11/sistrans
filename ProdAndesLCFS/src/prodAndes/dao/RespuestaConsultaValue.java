package prodAndes.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;

import javax.activation.DataSource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RespuestaConsultaValue 
{
	private static final String ARCHIVO_CONEXION = "/conexion.properties";
	
	/**
	 * nombre del usuario para conectarse a la base de datos.
	 */
	private String usuario;
	
	/**
	 * clave de conexi�n a la base de datos.
	 */
	private String clave;
	
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	private String cadenaConexion;
	
	public java.sql.Connection conexion;

	private InitialContext context;

	private DataSource ds1;

	private ConnectionFactory cf;

	private Queue colaDefinida;

	private Connection conm;

	/**
	 * Constructor del encargado de enviar mensajes JMS.
	 */
	public RespuestaConsultaValue() 
	{
		try 
		{
			context = new InitialContext();
			ds1 = (DataSource) context.lookup("java:transaccionesConsulta");
			cf =(ConnectionFactory) context.lookup("java:JmsXA");
			colaDefinida = (Queue) context.lookup("queue/WebApp2");
			//UserTransaction utx =(UserTransaction) context.list("/UserTransaction");
			iniciarConexion();
			//utx.begin();
			Statement st = conexion.createStatement();
			st.close();
		
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer= session.createProducer(colaDefinida);
			
			TextMessage msg = session.createTextMessage();
			//msg.setText(peticion);
			producer.send(msg);
			
			//utx.xommit();
			closeConnection();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (JMSException e) 
		{
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que se encarga de crear la conexi�n con el Driver Manager
	 * a partir de los parametros recibidos.
	 * @param url direccion url de la base de datos a la cual se desea conectar
	 * @param usuario nombre del usuario que se va a conectar a la base de datos
	 * @param clave clave de acceso a la base de datos
	 * @throws SQLException si ocurre un error generando la conexi�n con la base de datos.
	 */
    private void iniciarConexion() 
    {
    	try
		{
			File arch= new File("/Applications/jboss-4.2.2.GA/server/default/data/prodAndes"+ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream( arch );

		    prop.load( in );
		    in.close( );

			cadenaConexion = prop.getProperty("url");
			usuario = prop.getProperty("usuario");	
			clave = prop.getProperty("clave");	
			final String driver = prop.getProperty("driver");
			Class.forName(driver);
			conexion = DriverManager.getConnection(cadenaConexion,usuario,clave);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    /**
 	 *Cierra la conexi�n activa a la base de datos. Adem�s, con=null.
     * @param con objeto de conexi�n a la base de datos
     * @throws SistemaCinesException Si se presentan errores de conexi�n
     */
    public void closeConnection() throws Exception {        
		conexion.close();
		conexion = null;
    } 
}
