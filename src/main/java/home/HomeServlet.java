package home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.SpellMemorizerDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		SpellMemorizerDAO db = new SpellMemorizerDAO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Map<String, String>> books = new ArrayList<Map<String, String>>();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT BOOKCD,BOOKNM FROM BKTB ORDER BY BOOKCD ASC";
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Map<String, String> book = new HashMap<>();
				book.put("book_code", rset.getString(1));
				book.put("book_name", rset.getString(2));
				books.add(book);
			}
			
			request.setAttribute("books", books);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {}
			
			try {
				rset.close();
			} catch (SQLException e) {}
			
			try {
				conn.close();
			} catch (SQLException e) { }
		}
		
		request.getRequestDispatcher("/WEB-INF/app/home/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
