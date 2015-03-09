package controller.uc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

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
import entity.CGram;

/**
 * A controller for database interrogation.
 * 
 * Servlet implementation class DBInterrogationController
 */
@WebServlet("/DBInterrogationController")
public class DBInterrogationController extends HttpServlet {
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
		
        Enumeration<String> parameterNames = request.getParameterNames();
        String parameterName = null;
        String parameterValue = null;

        try {
	    	while (parameterNames.hasMoreElements()) {
	    		parameterName = (String) parameterNames.nextElement();
	    		parameterValue = (String) request.getParameter(parameterName);
	    		
	    		switch (parameterName) {
	    			
	    			case "cgram":
	    				if (parameterValue.equals("options")) {
	    					doWriteCgramOptions(request, response); 
	    				}
	    			break;

	    		}
	    	}
        } catch (Exception e) {
			doError(request, response, e);
		}
 
		Utilities.trace(this.getClass().getName(), ".doGet() ########################### FIN ###########################", "");
	}

	/**
	 * Writes CGram instances from database as <code>&lt;select&gt;</code> options. 
	 * 
	 * @param	request		HttpServletRequest	A servlet request.
	 * @param	response	HttpServletResponse	A servlet response
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doWriteCgramOptions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilities.trace(this.getClass().getName(), ".doWriteCgramOptions()", "");
		
        this.setServiceEJB(request, response);

    	try {
        	response.setContentType("text/html;" + ParametersI.PROJECT_ENCODING);
        	PrintWriter writer = response.getWriter();
        	List<CGram> cgrams = null;
    		
	    	cgrams = this.serviceStateless.getCGrams();
    		
	    	Utilities.trace(this.getClass().getName(), ".doWriteCgramOptions() cgrams = " + cgrams, "");
    	
	    	for (CGram c : cgrams) {
	    		writer.write("<option value=\"" + c.getId() + "\">" + c.getLibelle() + "</option>");
	    	}

	    	writer.close();
    	} catch (Exception e) {
    		doError(request, response, e);
    	}

        Utilities.trace(this.getClass().getName(), ".doWriteCgramOptions() ########################### FIN ###########################", "");
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
		
		
		Utilities.trace(this.getClass().getName(), ".doPost() ########################### FIN ###########################", "");
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
