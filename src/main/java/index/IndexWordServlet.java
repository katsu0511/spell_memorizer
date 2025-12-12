package index;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			PreparedStatement pstmt3 = null;
			PreparedStatement pstmt4 = null;
			PreparedStatement pstmt5 = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;
			ResultSet rset3 = null;
			ResultSet rset4 = null;
			ResultSet rset5 = null;
			String chapterCode = request.getParameter("chapter_code");
			String currentStr = request.getParameter("current");
			int current = Integer.parseInt(currentStr);
			int end = 0;
			int totalWords = 0;
			String bookCode = null;
			String bookName = null;
			
			if (current <= 0) {
				request.getRequestDispatcher("/WEB-INF/app/404/404.jsp").forward(request, response);
				return;
			}
			
			try {
				conn = db.getConnection();
				
				String sql1 = """
					SELECT BOOKCD, BOOKNM
					FROM BKTB
					WHERE BOOKCD = (
							SELECT BOOKCD
							FROM CPTB
							WHERE CPTRCD = ?
					)
				""";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, chapterCode);
				rset1 = pstmt1.executeQuery();
				
				if (rset1.next()) {
					bookCode = rset1.getString(1);
					bookName = rset1.getString(2);
				}
				
				request.setAttribute("bookCode", bookCode);
				request.setAttribute("bookName", bookName);
				
				String sql2 = """
					SELECT CPTRNM
					FROM CPTB
					WHERE CPTRCD = ?
				""";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, chapterCode);
				rset2 = pstmt2.executeQuery();
				String chapterName = null;
				if (rset2.next()) chapterName = rset2.getString(1);
				
				request.setAttribute("chapterName", chapterName);
				
				String sql3 = """
					SELECT WORDCD, WORDNU, WRDSPL, WRDSND
					FROM WDTB
					WHERE CPTRCD = ?
					ORDER BY WORDNU ASC
					LIMIT 100 OFFSET
				""" + (current - 1) * 100;
				pstmt3 = conn.prepareStatement(sql3);
				pstmt3.setString(1, chapterCode);
				rset3 = pstmt3.executeQuery();
				List<Map<String, String>> words = new ArrayList<>();
				
				while (rset3.next()) {
					Map<String, String> word = new HashMap<>();
					word.put("wordCode", rset3.getString(1));
					word.put("wordNumber", rset3.getString(2));
					word.put("wordSpell", rset3.getString(3));
					word.put("wordSound", rset3.getString(4));
					words.add(word);
				}
				
				request.setAttribute("words", words);
				
				String sql4 = """
					SELECT COUNT(*)
					FROM WDTB
					WHERE CPTRCD = ?
				""";
				pstmt4 = conn.prepareStatement(sql4);
				pstmt4.setString(1, chapterCode);
				rset4 = pstmt4.executeQuery();
				String totalWordsStr = null;
				if (rset4.next()) totalWordsStr = rset4.getString(1);
				totalWords = Integer.parseInt(totalWordsStr);
				end = totalWords / 100;
				if (totalWords % 100 != 0) end++;
				
				if (end < current) {
					request.getRequestDispatcher("/WEB-INF/app/404/404.jsp").forward(request, response);
					return;
				}
				
				String url = "/index/word?chapter_code=" + chapterCode + "&current=";
				ArrayList<Map<String, String>> pages = new ArrayList<>();
				Map<String, String> left = makePage("left", current - 1, url, false);
				Map<String, String> first = makePage("1", 1, url, false);
				Map<String, String> prev = makePage(Integer.toString(current - 1), current - 1, url, false);
				Map<String, String> currentPage = makePage(currentStr, current, url, true);
				Map<String, String> next = makePage(Integer.toString(current + 1), current + 1, url, false);
				Map<String, String> last = makePage(Integer.toString(end), end, url, false);
				Map<String, String> right = makePage("right", current + 1, url, false);
				Map<String, String> interval = makePage("...", current, url, true);
				
				if (current == 1) {
					pages.add(currentPage);
					if (end >= 3) pages.add(next);
					if (end >= 4) pages.add(interval);
					if (end >= 2) {
						pages.add(last);
						pages.add(right);
					}
				} else if (current == end) {
					pages.add(left);
					pages.add(first);
					if (end >= 4) pages.add(interval);
					if (end >= 3) pages.add(prev);
					pages.add(currentPage);
				} else {
					pages.add(left);
					pages.add(first);
					if (end >= 5 && current >= 4) pages.add(interval);
					if (end >= 4 && current >= 3) pages.add(prev);
					pages.add(currentPage);
					if (end >= 4 && current <= end - 2) pages.add(next);
					if (end >= 5 && current <= end - 3) pages.add(interval);
					pages.add(last);
					pages.add(right);
				}
				
				request.setAttribute("pages", pages);
				
				String sql5 = """
					SELECT WORDCD, CRRCTD
					FROM MARK
					WHERE USERID = ?
					AND WORDCD BETWEEN ? AND ?
					ORDER BY WORDCD ASC
				""";
				pstmt5 = conn.prepareStatement(sql5);
				pstmt5.setString(1, String.valueOf(USER_ID));
				pstmt5.setString(2, words.get(0).get("wordCode"));
				pstmt5.setString(3, words.get(words.size() - 1).get("wordCode"));
				rset5 = pstmt5.executeQuery();
				Map<String, Boolean> marks = new HashMap<>();
				while (rset5.next()) marks.put(rset5.getString(1), rset5.getInt(2) == 1);
				
				request.setAttribute("marks", marks);
				
				request.setAttribute("userId", USER_ID);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				db.close(pstmt1);
				db.close(pstmt2);
				db.close(pstmt3);
				db.close(pstmt4);
				db.close(pstmt5);
				db.close(rset1);
				db.close(rset2);
				db.close(rset3);
				db.close(rset4);
				db.close(rset5);
				db.close(conn);
			}
			
			if (bookCode == null && bookName == null || totalWords == 0) request.getRequestDispatcher("/WEB-INF/app/404/404.jsp").forward(request, response);
			else request.getRequestDispatcher("/WEB-INF/app/index/index-word.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private Map<String,String> makePage(String display, int pageNum, String baseUrl, boolean isCurrent) {
		Map<String,String> page = new HashMap<>();
		page.put("display", display);
		page.put("link", isCurrent ? "" : baseUrl + pageNum);
		return page;
	}

}
