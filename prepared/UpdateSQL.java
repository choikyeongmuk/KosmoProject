package jdbc25.prepared;

import java.sql.SQLException;

import jdbc25.service.IConnectImpl;

public class UpdateSQL extends IConnectImpl {

	@Override
	public void execute() throws Exception {
		//db연결
		connect(ORACLE_URL, "JDBC", "JDBC");
		psmt = conn.prepareStatement("UPDATE member SET pwd=?,name=? WHERE id=?");
		
		psmt.setString(3,getValue("수정할 아이디"));
		psmt.setString(1,getValue("비번"));
		psmt.setString(2,getValue("이름"));
		try {
			System.out.println(psmt.executeUpdate()+"행이 수정되었어요");
		}
		catch(SQLException e) {
			System.out.println("수정시 오류");
		}
		finally {
			close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new UpdateSQL().execute();

	}

}
