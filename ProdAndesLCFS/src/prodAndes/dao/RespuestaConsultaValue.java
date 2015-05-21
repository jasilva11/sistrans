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
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

public class RespuestaConsultaValue 
{
	private static final String ARCHIVO_CONEXION = "/conexion.properties";
	//base de datos
	private DataSource ds2;
    private QueueSession queueSession;

	private java.sql.Connection conn2;
	
	private InitialContext ictx;
	
	private ConnectionFactory cf;

	private Connection c;

 private Session s;		
	private Queue cola;
	private MessageProducer mp;

	

	/**
	 * Constructor del encargado de enviar mensajes JMS.
	 */
	public RespuestaConsultaValue() 
	{
		try {
				ictx = new InitialContext();
				ds2 = (DataSource)ictx.lookup("java:jesusRules");
	            cf = (ConnectionFactory)ictx.lookup("java:JmsXA");
	        

				
				
				Hashtable<String, String> env = new Hashtable<String, String>();
		        env.put(Context.INITIAL_CONTEXT_FACTORY,
		                "org.jnp.interfaces.NamingContextFactory");
		        env.put(Context.PROVIDER_URL, "jnp://localhost:8080");
		        env.put(Context.URL_PKG_PREFIXES,
		                "org.jboss.naming:org.jnp.interfaces");
		        
	            Context context = new InitialContext(env);


				
				  QueueConnectionFactory queueConectionFactory = ( QueueConnectionFactory )context.lookup( "ConnectionFactory" );
				  cola = ( Queue )context.lookup( "queue/queue_request" );
			        QueueConnection queueConnection = queueConectionFactory.createQueueConnection( );
			        queueSession = queueConnection.createQueueSession( false, QueueSession.AUTO_ACKNOWLEDGE );
			        queueConnection.start( );
				
				
				
				
		        
		        
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
		 * Envia un mensaje a JMS.
		 * @param mensaje
		 */
		public void send(String mensaje) throws Exception{
			try {
				
				 QueueSender queueSender = queueSession.createSender( cola );
			        TextMessage textMessage = queueSession.createTextMessage( mensaje );
			        queueSender.send( textMessage );
			        queueSender.close( );
				
				
				
			} catch (JMSException e) {
				e.printStackTrace();
			} finally {
				c.close();
			}
		}

	}
