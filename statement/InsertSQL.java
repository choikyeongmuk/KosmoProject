package jdbc25.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertSQL {
	//[멤버변수]
	private Connection conn;
	private Statement stmt;
	//[생성자]
	public InsertSQL() {
		try {
			//1]JDBC용 오라클 드라이버를 메모리에 로딩
			Class.forName("oracle.jdbc.OracleDriver");
			//2]오라클에 연결 시도 :DriverManager의 getConnection()메소드로
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","JDBC","JDBC");
		}
		catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스가 없어요. 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("데이터 베이스 연결 실패");
		}
		
	}
	private void execute() {
		try {
			//3]쿼리 실행하기 위한 Statement객체 생성
			//	연결된 Connection객체로....
			stmt = conn.createStatement();
			//3-1]쿼리문 작성
			String sql="INSERT INTO MEMBER(id,pwd,name) VALUES('PARK','1234','박길동')";
			//4]Statement계열 객체로 쿼리 실행
			/*
			 * DELETE/UPDATE/INSERT => int executeUpdate()
			 * SELECT => ResultSet executeQuery()
			 */
			try {
				int affected = stmt.executeUpdate(sql);
				System.out.println(affected+"행이 입력되었어요");
			}
			catch(SQLException e) {
				System.out.println("INSERT쿼리문 실행 오류:"+e.getMessage());
			}
		}
		catch(SQLException e) {
			System.out.println("Statement객체 생성 실패");
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
		catch (SQLException e){}
	}
	public static void main(String[] args) {
		new InsertSQL().execute();
	}
	
}	
