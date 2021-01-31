package jdbc25;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {

	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Properties props = new Properties();
			props.load(new FileReader("src/jdbc25/config.properties"));
			//System.out.println(props.getProperty("driver"));
			
			Class.forName(props.getProperty("driver"));
			//오라클 주소]
			String url = props.getProperty("url");
			//계정 정보]
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			con = DriverManager.getConnection(url,user,password);
			System.out.println("연결 성공:con ="+con);
			
			try {
				stmt = con.createStatement();
				
				String sql = "SELECT * FROM emp ORDER BY hiredate DESC";
				try {
					rs = stmt.executeQuery(sql);
					
					while(rs.next()) {//레코드가 없으면 fales반환
						int empno = rs.getInt(1);
						String ename = rs.getString(2);
						String job = rs.getString("JOB");
						int sal = rs.getInt(6);
						Date hiredate = rs.getDate(5);
						
						System.out.println(String.format("%-5d%-10s%-9s%-6d%s", empno,ename,job,sal,hiredate));
					}
				}
				catch(SQLException e){
					System.out.println("쿼리 실행 실패:"+e.getMessage());
				}
				
			}
			catch(SQLException e) {
				System.out.println("Statement 객체 생성 실패:"+e.getMessage());
			}
			
		}
		catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 클래스가 없어요:"+e.getMessage());
		}
		catch (SQLException e) {
			System.out.println("데이터 베이스 연결 실패:"+e.getMessage());
		}
		catch (IOException e) {
			System.out.println("파일이 존재하지 않아요:"+e.getMessage());
		}
		finally {
				try {
					if(rs != null) rs.close();
					if(stmt != null) stmt.close();
					if(con != null) con.close();
				} catch (SQLException e) {
				}
		}
	}
}
