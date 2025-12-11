package home;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DBManager;
import data.UserDTO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final HttpSession SESSION = request.getSession();
		final Integer USER_ID = (Integer) SESSION.getAttribute("userId");
		final String EMAIL = (String) SESSION.getAttribute("email");
		if (USER_ID == null || EMAIL == null) request.getRequestDispatcher("/WEB-INF/app/home/login.jsp").forward(request, response);
		else response.sendRedirect(request.getContextPath() + "/home");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String errorMessage = null;
		final DBManager DB_MANAGER = new DBManager();
		final UserDTO USER = DB_MANAGER.getLoginUser(email, password);
		
		if (email.equals("") || password.equals("")) {
			errorMessage = "Eメールとパスワードを入力してください。";
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("email", email);
			request.setAttribute("password", password);
			request.getRequestDispatcher("/WEB-INF/app/home/login.jsp").forward(request, response);
		} else if (USER != null) {
			final HttpSession SESSION = request.getSession();
			SESSION.setAttribute("userId", USER.getUserId());
			SESSION.setAttribute("email", email);
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			errorMessage = "Eメールかパスワードが間違っています。";
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("email", email);
			request.setAttribute("password", password);
			request.getRequestDispatcher("/WEB-INF/app/home/login.jsp").forward(request, response);
		}
	}

}
