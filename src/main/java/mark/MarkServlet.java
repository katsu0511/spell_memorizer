package mark;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.SpellMemorizerDAO;

/**
 * Servlet implementation class MarkServlet
 */
@WebServlet("/mark")
public class MarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MarkServlet() {
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
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			SpellMemorizerDAO db = new SpellMemorizerDAO();
			Connection conn = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rset1 = null;
			Boolean isAnswerCorrect = null;
			int userId = Integer.parseInt(request.getParameter("userId"));
			String wordCode = request.getParameter("wordCode");
			boolean isCorrect = Boolean.parseBoolean(request.getParameter("isCorrect"));
			final boolean USER_AUTHENTICATION = userId == USER_ID ? true : false;
			
			try {
				conn = db.getConnection();
				
				String sql1 = "SELECT CRRCTD FROM MARK WHERE USERID=? AND WORDCD=?";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setInt(1, USER_ID);
				pstmt1.setString(2, wordCode);
				rset1 = pstmt1.executeQuery();
				if (rset1.next()) isAnswerCorrect = rset1.getBoolean(1);
				
				if (USER_AUTHENTICATION) {
					if (isAnswerCorrect == null) {
						
						String sql2 = "INSERT INTO MARK (USERID,WORDCD,CRRCTD) VALUES (?, ?, ?)";
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setInt(1, USER_ID);
						pstmt2.setString(2, wordCode);
						pstmt2.setBoolean(3, isCorrect);
						pstmt2.executeUpdate();
						
					} else if (isAnswerCorrect != isCorrect) {
						
						String sql2 = "UPDATE MARK SET CRRCTD=? WHERE USERID=? AND WORDCD=?";
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setBoolean(1, isCorrect);
						pstmt2.setInt(2, USER_ID);
						pstmt2.setString(3, wordCode);
						pstmt2.executeUpdate();
						
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt1.close();
					if (USER_AUTHENTICATION && (isAnswerCorrect == null || isAnswerCorrect != isCorrect)) pstmt2.close();
					rset1.close();
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

}
