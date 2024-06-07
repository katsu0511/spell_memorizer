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
 * Servlet implementation class IndexChapterServlet
 */
@WebServlet("/index/chapter")
public class IndexChapterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexChapterServlet() {
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
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		String id = request.getParameter("id");
		
		try {
			conn = db.getConnection();
			
			String sql1 = "SELECT BOOKNM FROM BKTB WHERE BOOKCD=?";
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, id);
			rset1 = pstmt1.executeQuery();
			String book_name = null;
			
			while (rset1.next()) {
			  book_name = rset1.getString(1);
			}
			
			request.setAttribute("book_name", book_name);
			
			String sql2 = "SELECT CPTRCD,CPTRNM FROM CPTB WHERE BOOKCD=? ORDER BY CPTRCD ASC";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, id);
			rset2 = pstmt2.executeQuery();
			ArrayList<Map<String, String>> chapters = new ArrayList<Map<String, String>>();
			
			while (rset2.next()) {
				Map<String, String> chapter = new HashMap<>();
				chapter.put("chapter_code", rset2.getString(1));
				chapter.put("chapter_name", rset2.getString(2));
				chapters.add(chapter);
			}
			
			request.setAttribute("chapters", chapters);
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
				rset1.close();
			} catch (SQLException e) {}
			
			try {
        rset2.close();
      } catch (SQLException e) {}
			
			try {
				conn.close();
			} catch (SQLException e) {}
		}
		
		request.getRequestDispatcher("/WEB-INF/app/index/index_chapter.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
