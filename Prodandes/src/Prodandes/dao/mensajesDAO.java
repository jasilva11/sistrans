package Prodandes.dao;


import java.util.Hashtable;
import java.util.Properties;

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
 class mensajesDAO 
{
	
	//base de datos
    private QueueSession queueSession;

	
	private InitialContext ictx;
	

	private QueueConnection conn;
	
	private Connection c;

	private Queue cola;
	

	public mensajesDAO()
	{
		try {
			
			final Properties env = new Properties();
		    env.put("org.jboss.ejb.client.scoped.context", true);
            env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            env.put("endpoint.name", "endpoint-client"); 
            
            ictx = new InitialContext(env);


            Object tmp = ictx.lookup("ConnectionFactory");
            QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
            conn = qcf.createQueueConnection();
            this.cola = (Queue) ictx.lookup("queue/test");

            queueSession = conn.createQueueSession(false,   QueueSession.AUTO_ACKNOWLEDGE);

            conn.start();
           
      
	        

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 public void stop()
		        throws JMSException
		    {
		        conn.stop();
		        queueSession.close();
		        conn.close();
		    }
		    

//Manda mesaje
	public void send(String mensaje) throws Exception{
		try {
			
			 QueueSender queueSender = queueSession.createSender( cola );
		        TextMessage textMessage = queueSession.createTextMessage( mensaje );
		        queueSender.send( textMessage );
		        queueSender.close( );

			
			
		} catch (JMSException e) {
			e.printStackTrace();
		} 
	}


}
