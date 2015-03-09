package controller.uc;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clientServer.parameter.ParametersI;
import clientServer.service.ServiceI;
import clientServer.utility.Utilities;

/**
 * A controller for database initialization.
 * 
 * Servlet implementation class DBInitializationController
 */
@WebServlet("/DBInitializationController")
public class DBInitializationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private ServiceI serviceStateless;

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
		
		doPost(request, response);
		
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
		
		try {
			switch (action) {
				case "/bdinitialiser":
					doInitialize(request, response);
				break;

				default :
					throw new Exception(this.getClass().getName() + " : accès interdit");
			}
			if (true) {return;}
		} catch (Exception e) {
			doError(request, response, e);
		}
		
		Utilities.trace(this.getClass().getName(), ".doPost() ########################### FIN ###########################", "");
	}

	/**
	 * Initializes the database with required entities.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws ServletException 
	 * @throws IOException 
	 */
	private void doInitialize(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doInitialize()", "");
		
		boolean status = false;
		boolean giveFeedback = false;
		String feedbackMessage = "";
		
		this.setServiceEJB(request, response);
		
		status = this.serviceStateless.initializeDB();
		
		Utilities.trace(this.getClass().getName(), ".doInitialize() status = " + status, "");

		if (status == false) {
			giveFeedback = true;
			feedbackMessage = "Echec de l'initialisation de la base de données.";
		} else {
			giveFeedback = true;
			feedbackMessage = "Initialisation effectuée.";
		}
		
		request.setAttribute("giveFeedback", giveFeedback);
		request.setAttribute("feedbackMessage", feedbackMessage);
		
		this.dispatcher = request.getRequestDispatcher("/view/referentiel/formesLister.jsp");
		this.dispatcher.include(request, response);
		
		Utilities.trace(this.getClass().getName(), ".doInitialize() ########################### FIN ###########################", "");
	}

	/**
	 * Sets the service EJB.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void setServiceEJB(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".setServiceEJB()", "");
		
		InitialContext initialContext;

		try {
			initialContext = new InitialContext();
			this.serviceStateless  = (ServiceI) initialContext.lookup( ParametersI.SERVICE_EJB );
		} catch (Exception e) {
			doError(request, response, e);
		}
		
		Utilities.trace(this.getClass().getName(), ".setServiceEJB() ########################### FIN ###########################", "");
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
				errorMessage = errorMessage + "\n " +  this.getClass().getName() + " : "  + exception.toString();
			} else {
				errorMessage = this.getClass().getName() + " : "  + exception.toString();
			}
			request.setAttribute("errorMessage", errorMessage);
		}
		
		this.dispatcher = request.getRequestDispatcher("/do/erreur");
		this.dispatcher.forward(request, response);
		
		Utilities.trace(this.getClass().getName(), ".doError() ########################### FIN ###########################", "");
	}
	
}
