package jdbc25.etc;

import jdbc25.service.IConnectImpl;

public class TransactionSQL extends IConnectImpl {

	public TransactionSQL() {
		connect(ORACLE_URL, "JDBC", "JDBC");
	}/////
	@Override
	public void execute() throws Exception {
		//1]자동 커밋이 일어나지 않도록 설정
		conn.setAutoCommit(false);
		try {
			//2]첫번째 쿼리문 작성-존재하는 아이디로 삭제
			String sql="DELETE member WHERE id=?";
			//3-1]PreparedStatement객체 생성
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, getValue("삭제할 아이디"));
			//3-2]첫번째 쿼리문 실행
			System.out.println(psmt.executeUpdate()+"행이 삭제되었어요");
			//4]두번째 쿼리문 작성-중복된 아이디 입력
			sql ="INSERT INTO member(id,pwd,name) VALUES(?,?,?)";
			//4-1]PreparedStatement객체 생성
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, getValue("아이디"));
			psmt.setString(2, getValue("비번"));
			psmt.setString(3, getValue("이름"));
			//4-2]두번째 쿼리문 실행
			System.out.println(psmt.executeUpdate()+"행이 입력되었어요");
			//커밋]
			conn.commit();
			System.out.println("커밋 되었어요");
		}
		catch(Exception e) {
			//롤백]
			conn.rollback();
			System.out.println("롤백 되었어요");
			
		}
		finally {
			close();
		}
		
	}////////execute()
	public static void main(String[] args) throws Exception {
		new TransactionSQL().execute();
	}////main

}