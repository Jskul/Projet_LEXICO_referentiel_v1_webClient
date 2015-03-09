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
import entity.Lemme;

/**
 * A controller for lemme edition.
 * 
 * Servlet implementation class lemmeEditionController
 */
@WebServlet("/LemmeEditionController")
public class LemmeEditionController extends HttpServlet {
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
				case "/lemmecreer":
					doCreate(request, response);
				break;
				
				case "/lemmemodifier":
					doModify(request, response);
				break;
				
				case "/lemmeappelersuppression":
					doDeletion(request, response);
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
	 * Creates a lemme.
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response.
	 * @throws ServletException 
	 * @throws IOException 
	 */
	private void doCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doCreate()", "");
		
//		String s_cgram			= request.getParameter("cgram");
//		String s_scgram			= request.getParameter("scgram"); // TODO 
//		s_scgram = "1"; // TODO bouchon a supprimer
//		String s_flexion		= request.getParameter("flexion"); // TODO
//		s_flexion = "1"; // TODO bouchon a supprimer
//		String s_orthographe	= request.getParameter("orthographe");
//		String s_phonologie		= request.getParameter("phonologie");
		String s_freqlivres		= request.getParameter("freqlivres");
		String s_freqfilms		= request.getParameter("freqfilms");

//		int i_cgram = -1;
//		int i_scgram = -1;
//		int i_flexion = -1;
		Float F_freqlivres = null;
		Float F_freqfilms = null;
		
		boolean giveFeedback = false;
		String feedbackMessage = "";
		
//		String referrer = request.getHeader("referer");
//		String contextPath = request.getContextPath();
//		String path = referrer.substring( referrer.indexOf(contextPath) + contextPath.length() );
		
//		if (s_cgram == null || s_cgram.matches("^\\d+$") == false) {			
//			feedbackMessage += "La catégorie grammaticale n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		} else {
//			i_cgram = Integer.parseInt(s_cgram);
//		}
		
//		if (s_cgram == null || s_cgram.matches("^\\d+$") == false) {			
//			feedbackMessage += "La sous-catégorie n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		} else {
//			i_scgram = Integer.parseInt(s_scgram);
//		}
		
//		if (s_flexion == null || s_flexion.matches("^\\d+$") == false) {			
//			feedbackMessage += "La flexion n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		} else {
//			i_flexion = Integer.parseInt(s_flexion);
//		}
			
//		if (s_orthographe == null || s_orthographe.matches("^[ a-zàâçéèêëîñôùû'-]+$") == false) {
//			feedbackMessage = "L'orthographe n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		}
//
//		if (s_phonologie == null || s_phonologie.matches("^[aiyuoOeE°2951@§3j8wpbtdkgfvszSZmnNlRxG]+$") == false) {
//			feedbackMessage = "La phonologie n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		}
		
		if (s_freqlivres == null || s_freqlivres.matches("^(\\d{0,}(\\.\\d{0,2}){0,1}){0,1}$") == false) {
			feedbackMessage = "La fréquence pour les livres n'a pas pu être récupérée.\n";
			giveFeedback = true;
		} else {
			F_freqlivres = Float.parseFloat(s_freqlivres);
		}
		
		if (s_freqfilms == null || s_freqfilms.matches("^(\\d{0,}(\\.\\d{0,2}){0,1}){0,1}$") == false) {
			feedbackMessage = "La fréquence pour les films n'a pas pu être récupérée.\n";
			giveFeedback = true;
		} else {
			F_freqfilms = Float.parseFloat(s_freqfilms);
		}

		if(giveFeedback == true) {
			request.setAttribute("giveFeedback", giveFeedback);
			request.setAttribute("feedbackMessage", feedbackMessage);
			
			this.dispatcher = request.getRequestDispatcher("/FormsListController");
			this.dispatcher.include(request, response);
		} else {			
			giveFeedback = true;
			
			this.setServiceEJB(request, response);
			
			Lemme lemme = new Lemme();
			lemme.setFrequenceLivres(F_freqlivres);
			lemme.setFrequenceFilms(F_freqfilms);
			
			boolean status = false;
			
			status = this.serviceStateless.createLemme(lemme);
			
			if (status) {
				feedbackMessage = "Lemme créé avec succès.";
			} else {
				feedbackMessage = "Echec de la création du lemme.";
			}
			
			request.setAttribute("giveFeedback", giveFeedback);
			request.setAttribute("feedbackMessage", feedbackMessage);
			
			this.dispatcher = request.getRequestDispatcher("/FormsListController");
//			this.dispatcher = request.getRequestDispatcher("/FormsListController");
			this.dispatcher.include(request, response);
		}

		Utilities.trace(this.getClass().getName(), ".doCreate() ########################### FIN ###########################", "");
	}

	/**
	 * TODO
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doModify()", "");
		
		String s_id				= request.getParameter("id");
		
//		String s_cgram			= request.getParameter("cgram");
//		String s_scgram			= request.getParameter("scgram"); // TODO 
//		s_scgram = "1"; // TODO bouchon a supprimer
//		String s_flexion		= request.getParameter("flexion"); // TODO
//		s_flexion = "1"; // TODO bouchon a supprimer
//		String s_orthographe	= request.getParameter("orthographe");
//		String s_phonologie		= request.getParameter("phonologie");
		String s_freqlivres		= request.getParameter("freqlivres");
		String s_freqfilms		= request.getParameter("freqfilms");

		int i_id = -1;
//		int i_cgram = -1;
//		int i_scgram = -1;
//		int i_flexion = -1;
		Float F_freqlivres = null;
		Float F_freqfilms = null;
		
		boolean giveFeedback = false;
		String feedbackMessage = "";
		
		
		if (s_id == null || s_id.matches("^\\d+$") == false) {
			feedbackMessage = "L'id de lemme n'a pas pu être récupérée.\n";
			giveFeedback = true;
		} else {
			i_id = Integer.parseInt(s_id);
		}
		
//		String referrer = request.getHeader("referer");
//		String contextPath = request.getContextPath();
//		String path = referrer.substring( referrer.indexOf(contextPath) + contextPath.length() );
		
//		if (s_cgram == null || s_cgram.matches("^\\d+$") == false) {			
//			feedbackMessage += "La catégorie grammaticale n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		} else {
//			i_cgram = Integer.parseInt(s_cgram);
//		}
		
//		if (s_cgram == null || s_cgram.matches("^\\d+$") == false) {			
//			feedbackMessage += "La sous-catégorie n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		} else {
//			i_scgram = Integer.parseInt(s_scgram);
//		}
		
//		if (s_flexion == null || s_flexion.matches("^\\d+$") == false) {			
//			feedbackMessage += "La flexion n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		} else {
//			i_flexion = Integer.parseInt(s_flexion);
//		}
			
//		if (s_orthographe == null || s_orthographe.matches("^[ a-zàâçéèêëîñôùû'-]+$") == false) {
//			feedbackMessage = "L'orthographe n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		}
//
//		if (s_phonologie == null || s_phonologie.matches("^[aiyuoOeE°2951@§3j8wpbtdkgfvszSZmnNlRxG]+$") == false) {
//			feedbackMessage = "La phonologie n'a pas pu être récupérée.\n";
//			giveFeedback = true;
//		}
		
		if (s_freqlivres == null || s_freqlivres.matches("^(\\d{0,}(\\.\\d{0,2}){0,1}){0,1}$") == false) {
			feedbackMessage = "La fréquence pour les livres n'a pas pu être récupérée.\n";
			giveFeedback = true;
		} else {
			F_freqlivres = Float.parseFloat(s_freqlivres);
		}
		
		if (s_freqfilms == null || s_freqfilms.matches("^(\\d{0,}(\\.\\d{0,2}){0,1}){0,1}$") == false) {
			feedbackMessage = "La fréquence pour les films n'a pas pu être récupérée.\n";
			giveFeedback = true;
		} else {
			F_freqfilms = Float.parseFloat(s_freqfilms);
		}

		if(giveFeedback == true) {
			request.setAttribute("giveFeedback", giveFeedback);
			request.setAttribute("feedbackMessage", feedbackMessage);
			
			this.dispatcher = request.getRequestDispatcher("/FormsListController");
			this.dispatcher.include(request, response);
		} else {			
			giveFeedback = true;
			
			this.setServiceEJB(request, response);
			
			Lemme lemme = new Lemme();
			lemme.setId(i_id);
			lemme.setFrequenceLivres(F_freqlivres);
			lemme.setFrequenceFilms(F_freqfilms);
			
			boolean status = false;
			
			status = this.serviceStateless.modifyLemme(lemme);
			
			if (status) {
				feedbackMessage = "Lemme modifié avec succès.";
			} else {
				feedbackMessage = "Echec de la modification du lemme.";
			}
			
			request.setAttribute("giveFeedback", giveFeedback);
			request.setAttribute("feedbackMessage", feedbackMessage);
			
			this.dispatcher = request.getRequestDispatcher("/FormsListController");
			this.dispatcher.include(request, response);
		}

		Utilities.trace(this.getClass().getName(), ".doModify() ########################### FIN ###########################", "");
	}

	/**
	 * TODO
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doDeletion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities.trace(this.getClass().getName(), ".doDeletion()", "");
		
		String s_id				= request.getParameter("id");
		int i_id = -1;

		boolean giveFeedback = false;
		String feedbackMessage = "";
		
		if (s_id == null || s_id.matches("^\\d+$") == false) {
			feedbackMessage = "L'id de lemme n'a pas pu être récupérée.\n";
			giveFeedback = true;
		} else {
			i_id = Integer.parseInt(s_id);
		}
		
		if(giveFeedback == true) {
			request.setAttribute("giveFeedback", giveFeedback);
			request.setAttribute("feedbackMessage", feedbackMessage);
			
			this.dispatcher = request.getRequestDispatcher("/FormsListController");
			this.dispatcher.include(request, response);
		} else {			
			giveFeedback = true;
			
			this.setServiceEJB(request, response);
			
			Lemme lemme = new Lemme();
			lemme.setId(i_id);

			boolean status = false;
			
			status = this.serviceStateless.deleteLemme(lemme);
			
			if (status) {
				feedbackMessage = "Lemme supprimé avec succès.";
			} else {
				feedbackMessage = "Echec de la suppression du lemme.";
			}
			
			request.setAttribute("giveFeedback", giveFeedback);
			request.setAttribute("feedbackMessage", feedbackMessage);
			
			this.dispatcher = request.getRequestDispatcher("/FormsListController");
			this.dispatcher.include(request, response);
		}

		Utilities.trace(this.getClass().getName(), ".doDeletion() ########################### FIN ###########################", "");
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
