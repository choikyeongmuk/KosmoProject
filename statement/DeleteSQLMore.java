package jdbc25.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteSQLMore {

	private Connection conn;
	private Statement stmt;

	public DeleteSQLMore() {
		try {
			//1.jdbc용 오라클 드라이버를 메모리에 로딩
			Class.forName("oracle.jdbc.OracleDriver");
			//2.오라클에 연결시도:DriverManager의 getConnection()메소드로
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "JDBC", "JDBC");
		}
		catch(ClassNotFoundException e) {
			System.out.println("드라이버클래스가 없습니다." +e.getMessage());
		}
		catch(SQLException e) {
			System.out.println("데이터베이스 연결에 실패했습니다."+e.getMessage());
		}
	}

	private void execute() {
		try {
			stmt = conn.createStatement();
			while(true) {
				try {
					int affected = stmt.executeUpdate("DELETE member WHERE id='"+InsertSQLMore.getValue("삭제할 아이디").toString()+"'");
					System.out.println(affected+"행이 삭제되었어요.");
				}
				catch(SQLException e) {
					System.out.println("쿼리문 실행에 실패했습니다."+e.getMessage());
				}
				catch(NullPointerException e) {
					System.out.println("삭제를 종료합니다.");
					break;
				}
			}
		} 
		catch (SQLException e) {
			System.out.println("statment객체생성 실패"+e.getMessage());
		}
		finally {
			close();
		}
	}

	private void close() {
		try {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
		catch(SQLException e) {
			System.out.println("안닫힘.."+e.getMessage());
		}
	}

	public static void main(String[] args) {
		new DeleteSQLMore().execute();
	}

}