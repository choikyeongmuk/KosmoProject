package jdbc25.prepared;

import java.sql.SQLException;

import jdbc25.service.IConnectImpl;

public class DeleteSQLMore extends IConnectImpl {

	@Override
	public void execute() throws Exception {
		//0]데이터베이스 연결
		connect(ORACLE_URL, "JDBC", "JDBC");
		//1]미리 쿼리문 준비
		String sql="DELETE member WHERE id=?";

		//2]PreparedStatement객체 생성
		psmt = conn.prepareStatement(sql);
		while(true) {
			//3]인파라미터 설정
			psmt.setString(1, getValue("삭제할 아이디"));
			//4]쿼리 실행
			try {
				System.out.println(psmt.executeUpdate()+"행이 삭제되었습니다");
			}
			catch(SQLException e) {
				System.out.println("삭제시 오류:"+e.getMessage());
			}
		}
	}

	public static void main(String[] args) throws Exception {

		new DeleteSQLMore().execute();
	}
}
