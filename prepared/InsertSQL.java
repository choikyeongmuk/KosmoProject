package jdbc25.prepared;

import java.sql.Date;
import java.sql.SQLException;

import jdbc25.service.IConnectImpl;

public class InsertSQL extends IConnectImpl {

	public InsertSQL(String url, String user, String password) {
		super(url,user,password);
	}
	
	@Override
	public void execute() throws Exception {
		//1]쿼리문 미리 준비
		//1-1]인파라미터(?)가 없는 쿼리문
		//String sql = "INSERT INTO member VALUES('KIM1','1234','김길동1',SYSDATE)";
		//1-2]인파라미터(?)가 있는 쿼리문
		String sql = "INSERT INTO member VALUES(?,?,?,?)";
		
		//2]쿼리 실행을 위한 Statement계열 객체 생성
		psmt = conn.prepareStatement(sql);
		
		//3]쿼리 실행-실행 시 쿼리문 전달 불 필요
		//3-1]인파라미터가 없는 쿼리문은 설정 불필요
		
		//3-2]인파라미터 설정
		psmt.setString(1,getValue("아이디"));
		psmt.setString(2,getValue("비밀번호"));
		psmt.setString(3,getValue("이름"));
		//날짜를 문자열로 설정시]
		//psmt.setString(4,getValue("가입일"));
		//날짜를 java.sql.Date타입으로 설정시]
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		psmt.setDate(4,date);
		try {
			System.out.println(psmt.executeUpdate()+"행이 입력되었어요");
		}
		catch(SQLException e) {
			System.out.println("입력시 오류:"+e.getMessage());
		}
		finally {
			//4]자원 반납
			close();
		}
	}

	public static void main(String[] args){
		
		try {
			new InsertSQL(ORACLE_URL,"JDBC","JDBC").execute();
		} 
		catch (Exception e) {
			System.out.println("오류:"+e.getMessage());
		}

	}

}
