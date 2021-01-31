package jdbc25.callable;

import java.sql.Types;

import jdbc25.service.IConnectImpl;

public class DeleteProc extends IConnectImpl{
	
	public DeleteProc() {
		super(ORACLE_URL,"JAVA","JAVA");
	}
	
	@Override
	public void execute() throws Exception {
		csmt = conn.prepareCall("{call SP_DEL_MEMBER(?,?)}");
		
		String id = getValue("삭제할 아이디");

		csmt.setString(1, id);
		csmt.registerOutParameter(2, Types.NUMERIC);
		csmt.execute();
		
		int rtnum = csmt.getInt(2);
		switch(rtnum) {
			case 1:System.out.println(id+"가 삭제되었습니다");break;
			case -1:System.out.println(id+"는(은) 존재하지 않는 아이디입니다.");break;
			default : System.out.println("자식이 참조하고 있어요.");
		}
	}
	public static void main(String[] args) throws Exception {
		new DeleteProc().execute();

	}

}
