package home;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final HttpSession SESSION = request.getSession();
		final Integer USER_ID = (Integer) SESSION.getAttribute("userId");
		final String EMAIL = (String) SESSION.getAttribute("email");
		if (USER_ID == null || EMAIL == null) response.sendRedirect(request.getContextPath() + "/login");
		else {
			SESSION.removeAttribute("userId");
			SESSION.removeAttribute("email");
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

}
