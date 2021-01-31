package jdbc25.statement;

import java.sql.SQLException;

import jdbc25.service.IConnectImpl;

public class UpdateSQLMore extends IConnectImpl{
	
	@Override
	public void execute() throws Exception {
		connect(ORACLE_URL,"JDBC","JDBC");
		
		stmt = conn.createStatement();
		while(true) {
			String sql="UPDATE member SET name='"+getValue("이름")+"',pwd='"+getValue("비번")+"' WHERE id='"+getValue("아이디")+"'";
			try {
				System.out.println(stmt.executeUpdate(sql)+"행이 수정되었어요");
			}
			catch(SQLException e) {
				System.out.println("수정 시 오류:"+e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new UpdateSQLMore().execute();

	}
}
