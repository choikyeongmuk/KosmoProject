package jdbc25.statement;

import jdbc25.service.IConnectImpl;

public class UpdateSQL extends IConnectImpl{
	
	public UpdateSQL(String url, String user, String password) {
		super(url,user,password);
	}

	@Override
	public void execute() throws Exception {
		stmt = conn.createStatement();
		
		String sql="UPDATE member SET name='수정명',pwd='8989' WHERE id='LEE'";
		System.out.println(stmt.executeUpdate(sql)+"행이 수정되었어요");
		close();
	}
	
	public static void main(String[] args) throws Exception {
		new UpdateSQL(ORACLE_URL,"JDBC","JDBC").execute();

	}

}
