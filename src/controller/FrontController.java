package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import clientServer.utility.Utilities;

/**
 * The front controller.
 * 
 * Servlet implementation class FrontController.
 */
//@WebServlet("/FrontController")
@WebServlet(
	urlPatterns = {"/do/*"}
)
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;

	/**
	 * Processes get requests.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doGet()", "");
		
		doPost(request, response); //TODO
		
		Utilities.trace(this.getClass().getName(), ".doGet() ########################### FIN ###########################", "");
	}

	/**
	 * Processes post requests.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doPost()", "");
		
		String action  = request.getPathInfo().toLowerCase();
		
		/*** LOG PAR EJB MESSAGE ***/

		String destinationName = "queue/test";
		Context ic = null;
		ConnectionFactory cf = null;
		Connection connection = null;
		
		try {
			ic = new InitialContext();
			cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Queue queue = (Queue) ic.lookup(destinationName);
			MessageProducer publisher = session.createProducer(queue);
			
			connection.start();
			
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			TextMessage message = session.createTextMessage(" Entrée dans le doPost avec l'action " + action + " à " + timestamp);
			
			publisher.send(message);
			
			connection.close();
		} catch (Exception e) {
			
		}
		
		/***************************/

		Utilities.trace(this.getClass().getName(), ".doPost() action : " + action, "");
		
		if (action == null) {
			doDefault(request, response);
		}
	
		try {
			switch (action) {
			
				case "/bdinitialiser":
					doInitializeDB(request, response);
				break;
			
				case "/dbinterrogationcontroller":
					doInterrogateDB(request, response);
				break;
				
				case "/formeslister":
					doListForms(request, response);
				break;
				
				case "/lemmeappelercreation":
					doCallLemmeCreation(request, response);
				break;
				
				case "/lemmecreer":
					doCreateLemme(request, response);
				break;
				
				case "/lemmeappelermodification":
					doCallLemmeModification(request, response);
				break;
				
				case "/lemmemodifier":
					doModifyLemme(request, response);
				break;
				
				case "/lemmeappelersuppression":
					doCallLemmeDeletion(request, response);
				break;
				
				
				case "/formeappelercreation":
					doCallFormCreation(request, response);
				break;
				
				// TODO here
				
				case "/annuler":
					doDefault(request, response);
				break;
				
				case "/erreur":
					doError(request, response, null);
				break;
				
				default :
					doDefault(request, response);
				break;
			}
			if (true) {return;}
		} catch (Exception e) {
			doError(request, response, e);
		}
		
		Utilities.trace(this.getClass().getName(), ".doPost() ########################### FIN ###########################", "");
	}

	/**
	 * Redirects to the lemme edition controller.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doCreateLemme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doCreateLemme()", "");
		
		this.dispatcher = request.getRequestDispatcher("/LemmeEditionController");
		this.dispatcher.include(request, response);
		
		Utilities.trace(this.getClass().getName(), ".doCreateLemme() ########################### FIN ###########################", "");
	}
	
	private void doModifyLemme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doCreateLemme()", "");
		
		this.dispatcher = request.getRequestDispatcher("/LemmeEditionController");
		this.dispatcher.include(request, response);
		
		Utilities.trace(this.getClass().getName(), ".doCreateLemme() ########################### FIN ###########################", "");
	}

	/**
	 * Redirects to the database interrogation controller.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doInterrogateDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doInterrogateDB()", "");
		
		this.dispatcher = request.getRequestDispatcher("/DBInterrogationController");
		this.dispatcher.forward(request, response);
	
		Utilities.trace(this.getClass().getName(), ".doInterrogateDB() ########################### FIN ###########################", "");
	}

	/**
	 * Launches the initialization of the database.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doInitializeDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doInitializeDB()", "");
		
		this.dispatcher = request.getRequestDispatcher("/DBInitializationController");
		this.dispatcher.include(request, response);
	
		Utilities.trace(this.getClass().getName(), ".doInitializeDB() ########################### FIN ###########################", "");
	}

	/**
	 * Redirects to the results page.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doListForms(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doListForms()", "");
		
		this.dispatcher = request.getRequestDispatcher("/FormsListController");
		this.dispatcher.include(request, response);
		
		//response.sendRedirect(request.getContextPath() + "/view/referentiel/formesLister.jsp");
	
		Utilities.trace(this.getClass().getName(), ".doListForms() ########################### FIN ###########################", "");
	}

	/**
	 * Redirects the form creation page.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doCallFormCreation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doCallFormCreation()", "");
		
		request.setAttribute("errorMessage", "Désolé, la création de forme n'est pas encore implémentée.");
		doError(request, response, null);
		
		Utilities.trace(this.getClass().getName(), ".doCallFormCreation() ########################### FIN ###########################", "");
	}

	/**
	 * Redirects to the lemme creation page.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void doCallLemmeCreation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doCallLemmeCreation()", "");

		this.dispatcher = request.getRequestDispatcher("/view/referentiel/lemmeCreer.jsp");
		this.dispatcher.forward(request,response);
		
		Utilities.trace(this.getClass().getName(), ".doCallLemmeCreation() ########################### FIN ###########################", "");
	}
	
	

	private void doCallLemmeModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doCallLemmeModification()", "");

		Utilities.trace(this.getClass().getName(), ".doCallLemmeModification() id = " + request.getAttribute("id") , "");
		
		this.dispatcher = request.getRequestDispatcher("/view/referentiel/lemmeModifier.jsp");
		this.dispatcher.include(request,response);
		
		Utilities.trace(this.getClass().getName(), ".doCallLemmeModification() ########################### FIN ###########################", "");
	}
	
	private void doCallLemmeDeletion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doCallLemmeDeletion()", "");

		this.dispatcher = request.getRequestDispatcher("/LemmeEditionController");
		this.dispatcher.include(request, response);
		
		Utilities.trace(this.getClass().getName(), ".doCallLemmeDeletion() ########################### FIN ###########################", "");
	}
	
	/**
	 * Redirects to the default page.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void doDefault(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doDefault()", "");
		
		doWelcome(request, response);
		
		Utilities.trace(this.getClass().getName(), ".doDefault() ########################### FIN ###########################", "");
	}
	
	/**
	 * Redirects to a welcome page.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void doWelcome(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doWelcome()", "");
		
		doListForms(request, response);
	
		Utilities.trace(this.getClass().getName(), ".doWelcome() ########################### FIN ###########################", "");
	}
	
	/**
	 * Redirects to an error page.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * @param	exception	Exception			An exception whose message is to be displayed on the error page (optional).
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doError(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doError()", "");
		
		if (exception != null ) {
			String errorMessage = request.getParameter("errorMessage");
			if (errorMessage != null) {
				errorMessage = errorMessage + "\n " + this.getClass().getName() + " : "  +  exception.toString();
			} else {
				errorMessage = this.getClass().getName() + " : "  + exception.toString();
			}
			request.setAttribute("errorMessage", errorMessage);
		}
		this.dispatcher = request.getRequestDispatcher("/view/default/erreur.jsp");
		this.dispatcher.forward(request,response);
		
		Utilities.trace(this.getClass().getName(), ".doError() ########################### FIN ###########################", "");
	}
	
}
