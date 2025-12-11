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
			String id = request.getParameter("id");
			String curStr = request.getParameter("cur");
			int total = 0;
			int end = 0;
			int cur = Integer.parseInt(curStr);
			String book_code = null;
			String book_name = null;
			
			if (cur <= 0) {
				request.getRequestDispatcher("/WEB-INF/app/404/404.jsp").forward(request, response);
				return;
			}
			
			try {
				conn = db.getConnection();
				
				String sql1 = "SELECT BOOKCD,BOOKNM FROM BKTB WHERE BOOKCD=("
				    + "SELECT BOOKCD FROM CPTB WHERE CPTRCD=?"
				    + ")";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, id);
				rset1 = pstmt1.executeQuery();
				
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
				
				String sql3 = "SELECT WORDCD,WORDNU,WRDSPL,WRDSND FROM WDTB WHERE CPTRCD=? ORDER BY WORDNU ASC LIMIT 100 OFFSET " + (cur - 1) * 100;
				pstmt3 = conn.prepareStatement(sql3);
				pstmt3.setString(1, id);
				rset3 = pstmt3.executeQuery();
				List<Map<String, String>> words = new ArrayList<>();
				
				while (rset3.next()) {
					Map<String, String> word = new HashMap<>();
					word.put("word_code", rset3.getString(1));
					word.put("word_number", rset3.getString(2));
					word.put("word_spell", rset3.getString(3));
					word.put("word_sound", rset3.getString(4));
					words.add(word);
				}
				
				request.setAttribute("words", words);
				
				String sql4 = "SELECT WORDCD, CRRCTD FROM MARK WHERE USERID=? AND WORDCD BETWEEN ? AND ? ORDER BY WORDCD ASC";
				pstmt4 = conn.prepareStatement(sql4);
				pstmt4.setString(1, String.valueOf(USER_ID));
				pstmt4.setString(2, words.get(0).get("word_code"));
				pstmt4.setString(3, words.get(words.size() - 1).get("word_code"));
				rset4 = pstmt4.executeQuery();
				Map<String, Boolean> mark = new HashMap<>();
				while (rset4.next()) mark.put(rset4.getString(1), rset4.getInt(2) == 1);
				
				request.setAttribute("mark", mark);
				
				String sql5 = "SELECT COUNT(*) FROM WDTB WHERE CPTRCD=?";
				pstmt5 = conn.prepareStatement(sql5);
				pstmt5.setString(1, id);
				rset5 = pstmt5.executeQuery();
				String totalStr = null;
				if (rset5.next()) totalStr = rset5.getString(1);
				total = Integer.parseInt(totalStr);
				end = total / 100;
				if (total % 100 != 0) end++;
				
				String url = "/index/word?id=" + id + "&cur=";
				ArrayList<Map<String, String>> pages = new ArrayList<>();
				Map<String, String> left = makePage("left", cur - 1, url, false);
				Map<String, String> first = makePage("1", 1, url, false);
				Map<String, String> prev = makePage(Integer.toString(cur - 1), cur - 1, url, false);
				Map<String, String> current = makePage(curStr, cur, url, true);
				Map<String, String> next = makePage(Integer.toString(cur + 1), cur + 1, url, false);
				Map<String, String> last = makePage(Integer.toString(end), end, url, false);
				Map<String, String> right = makePage("right", cur + 1, url, false);
				Map<String, String> interval = makePage("...", cur, url, true);
				
				if (cur == 1) {
					pages.add(current);
					if (end >= 3) pages.add(next);
					if (end >= 4) pages.add(interval);
					if (end >= 2) {
						pages.add(last);
						pages.add(right);
					}
				} else if (cur == end) {
					pages.add(left);
					pages.add(first);
					if (end >= 4) pages.add(interval);
					if (end >= 3) pages.add(prev);
					pages.add(current);
				} else {
					pages.add(left);
					pages.add(first);
					if (end >= 5 && cur >= 4) pages.add(interval);
					if (end >= 4 && cur >= 3) pages.add(prev);
					pages.add(current);
					if (end >= 4 && cur <= end - 2) pages.add(next);
					if (end >= 5 && cur <= end - 3) pages.add(interval);
					pages.add(last);
					pages.add(right);
				}
				
				request.setAttribute("pages", pages);
				
				request.setAttribute("userId", USER_ID);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt1.close();
					pstmt2.close();
					pstmt3.close();
					pstmt4.close();
					pstmt5.close();
					rset1.close();
					rset2.close();
					rset3.close();
					rset4.close();
					rset5.close();
					conn.close();
				} catch (SQLException e) {}
			}
			
			if (book_code == null && book_name == null || total == 0 || end < cur) request.getRequestDispatcher("/WEB-INF/app/404/404.jsp").forward(request, response);
			else request.getRequestDispatcher("/WEB-INF/app/index/index_word.jsp").forward(request, response);
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
