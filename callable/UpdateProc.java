package jdbc25.callable;

import java.sql.Types;

import jdbc25.service.IConnectImpl;

public class UpdateProc extends IConnectImpl {

	public UpdateProc() {
		super(ORACLE_URL,"JAVA","JAVA");
	}
	
	@Override
	public void execute() throws Exception {
		csmt = conn.prepareCall("{call SP_UP_MEMBER(?,?,?,?)}");
		
		csmt.setString(1, getValue("수정할 아이디"));
		csmt.setString(1, getValue("수정할 비번"));
		csmt.setString(1, getValue("수정할 이름"));
		csmt.registerOutParameter(4, Types.NUMERIC);
		csmt.execute();
		
		System.out.println(csmt.getString(4));
		
	}
	public static void main(String[] args) throws Exception {
		new UpdateProc().execute();

	}
}
