package Prodandes.dao;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class mensajesDAO 
{
	
	//base de datos
	private DataSource ds2;

	private java.sql.Connection conn2;
	
	private InitialContext ictx;
	
	private ConnectionFactory cf;

	private Connection c;

 private Session s;		
	private Queue cola;
	private MessageProducer mp;

	
	public mensajesDAO()
	{
		try {
			ictx = new InitialContext();
			ds2 = (DataSource)ictx.lookup("java:jesusRules");
            cf = (ConnectionFactory)ictx.lookup("java:JmsXA");
            cola = (Queue) ictx.lookup("que/WebAPP2");
			c = (Connection) cf.createConnection();
			c.start();
			


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
			TextMessage tm = this.s.createTextMessage(mensaje);
			this.mp.send(tm);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			c.close();
		}
	}


}
