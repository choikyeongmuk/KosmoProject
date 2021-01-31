package jdbc25.callable;

import java.sql.Types;
import jdbc25.service.IConnectImpl;

public class InsertProc extends IConnectImpl {
	
	public InsertProc() {
		super(ORACLE_URL,"java","JAVA");
	}
	
	public void execute() throws Exception{
		//1] 프로시저를 실행하기 위한 CallableStatement 객체 얻기
		/*
		 * Connection객체의 prepareCall("{call 프로시저명(?,?,...)}")
		 * 메소드 호출
		 * - 인 파라미터 설정시에는 setXXX(파라미터인덱스,값)로
		 * - 아웃 파라미터 설정시에는
		 * registerOutParameter(파라미터 인덱스,java.sql.Types클래스의 int형 상수)
		 */
		csmt = conn.prepareCall("{call SP_INS_MEMBER(?,?,?,?)}");
		/*
		 * 2] 파라미터 설정
		 * 2-1] 인파라미터(?) 설정
		 * 오라클의 IN 파라미터에 해당하는 ? 설정 setXXXX()로
		 */
		csmt.setString(1, getValue("아이디"));
		csmt.setString(2, getValue("비번"));
		csmt.setString(3, getValue("이름"));
		/*
		 * 2-2] 오라클의 Out 파라미터에 해당하는 ? 설정
		 * 
		 *  registerOutParameter()로
		 */
		csmt.registerOutParameter(4, Types.NVARCHAR);
		//3] 프로시저 실행 - execute()
		System.out.println(csmt.execute());
		//4] out파라미터에 저장된 값 읽어오기
		// CallableStatement객체의 getXXX()계열 메소드
		System.out.println("프로시저의 아웃 파라미터 값 : "+csmt.getNString(4));
		//5] 자원반납
		close();
		
	}
	
	public static void main(String[] args) throws Exception{
		new InsertProc().execute();
	}
}