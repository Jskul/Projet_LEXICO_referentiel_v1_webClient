package controller.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

/**
 * Servlet implementation class messageEJB
 */
@MessageDriven(name = "MessageMDBSample", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/test"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class messageEJB implements MessageListener {

	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
		
		try {
			System.out.println("### EJB Message : " + tm.getText() + " ###");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}




