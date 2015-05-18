package prodAndes.dao;

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

public class RespuestaConsultaValue 
{
	private DataSource ds;
	
	private ConnectionFactory cf;
	
	private InitialContext context;

	private Connection conn;

	private Session s;

	private Destination d;

	private MessageProducer mp;
	
	private Queue cola;
	

	/**
	 * Constructor del encargado de enviar mensajes JMS.
	 */
	public RespuestaConsultaValue() {
		InitialContext init;
		try {
			init = new InitialContext();
			this.cf = (ConnectionFactory) init.lookup("RemoteConnectionFactory");
			this.d = (Destination) init.lookup("queue/test");
			this.c = (Connection) this.cf.createConnection("sistrans", "test");
			((javax.jms.Connection) this.c).start();
			this.s = ((javax.jms.Connection) this.c).createSession(false, Session.AUTO_ACKNOWLEDGE);
			this.mp = this.s.createProducer(this.d);
		} catch (Exception e) {
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
