package index;

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
 * Servlet implementation class IndexWordServlet
 */
@WebServlet("/index/word")
public class IndexWordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexWordServlet() {
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
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		ResultSet rset3 = null;
		String id = request.getParameter("id");
		
		try {
			conn = db.getConnection();
			
			String sql1 = "SELECT BOOKCD,BOOKNM FROM BKTB WHERE BOOKCD=("
			    + "SELECT BOOKCD FROM CPTB WHERE CPTRCD=?"
			    + ")";
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, id);
			rset1 = pstmt1.executeQuery();
			String book_code = null;
			String book_name = null;
			
			while (rset1.next()) {
			  book_code = rset1.getString(1);
			  book_name = rset1.getString(2);
			}
			
			request.setAttribute("book_code", book_code);
			request.setAttribute("book_name", book_name);
			
			String sql2 = "SELECT CPTRNM FROM CPTB WHERE CPTRCD=?";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, id);
			rset2 = pstmt2.executeQuery();
			String chapter_name = null;
			
			while (rset2.next()) {
			  chapter_name = rset2.getString(1);
			}
			
			request.setAttribute("chapter_name", chapter_name);
			
			String sql3 = "SELECT WORDNU,WRDSPL,WRDSND FROM WDTB WHERE CPTRCD=? ORDER BY WORDNU ASC";
			pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setString(1, id);
			rset3 = pstmt3.executeQuery();
			ArrayList<Map<String, String>> words = new ArrayList<Map<String, String>>();
			
			while (rset3.next()) {
			  Map<String, String> word = new HashMap<>();
				word.put("word_number", rset3.getString(1));
				word.put("word_spell", rset3.getString(2));
				word.put("word_sound", rset3.getString(3));
				words.add(word);
			}
			
			request.setAttribute("words", words);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt1.close();
			} catch (SQLException e) {}
			
			try {
        pstmt2.close();
      } catch (SQLException e) {}
			
			try {
        pstmt3.close();
      } catch (SQLException e) {}
			
			try {
				rset1.close();
			} catch (SQLException e) {}
			
			try {
        rset2.close();
      } catch (SQLException e) {}
			
			try {
        rset3.close();
      } catch (SQLException e) {}
			
			try {
				conn.close();
			} catch (SQLException e) {}
		}
		
		request.getRequestDispatcher("/WEB-INF/app/index/index_word.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
