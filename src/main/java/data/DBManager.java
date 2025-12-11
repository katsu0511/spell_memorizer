package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager extends SpellMemorizerDAO {
	public UserDTO getLoginUser(String email, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = """
			SELECT USERID, EMLADR
			FROM USER
			WHERE EMLADR = BINARY ?
			AND PASSWD = BINARY ?
		""";
		UserDTO user = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				user = new UserDTO();
				user.setUserId(Integer.parseInt(rset.getString(1)));
				user.setEmail(rset.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}

		return user;
	}
}
