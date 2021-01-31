package jdbc25.statement;

import jdbc25.service.IConnectImpl;

public class SelectSQL extends IConnectImpl{

	@Override
	public void execute() throws Exception {
		//1] 데이터 연결
		connect(ORACLE_URL, "SCOTT","scott");
			try {
			//2] statement 객체 생성
			stmt = conn.createStatement();
			
			//3] 쿼리문 작성
			//3-1]여러개 레코드(행:row)를 반환하는 SELECT문
			//String sql="SELECT * FROM emp ORDER BY hiredate DESC";
			//3-2]한 개의 레코드를 반환하거나 '선택된 레코드가 없습니다'라고 반환하는 경우
			//String sql = "SELECT * FROM emp WHERE empno="+getValue("사원번호");
			//3-3]무조건 레코드 하나 반환하는 경우-그룹함수 사용시
			//String sql = "SELECT AVG(sal) FROM emp";
			//3-4]Like연산자를 포함하는 경우
			//String sql = "SELECT * FROM emp WHERE ename LIKE '%S%'";
			//String sql = "SELECT * FROM emp WHERE ename LIKE '%'||'S'||'%'";
			
			//String sql = "SELECT * FROM emp WHERE ename LIKE '%"+getValue("찾는 문자열")+"%'";
			String sql = "SELECT * FROM emp WHERE ename LIKE '%'||'"+getValue("찾는 문자열")+"'||'%'";
			//4] 쿼리문 실행
			rs = stmt.executeQuery(sql);
			
			/*
			 * SELECT문 실행시 실행결과는 RS타입의 객체에 저장된다.
			 * 최초 커서는 첫번째 레코드 바로 위에 가 있다.
			 * RS객체의 NEXT()메소드로 커서를 아래로 이동시키면서 더이상 꺼내올 레코드가 없을때까지 반복하면서 추출한다
			 * 				없으면 false를 반환한다.
			 * 
			 * RS객체의 getXXX()계열 메소드로 해당 레코드의 각 컬럼에 저장된 값을 릭어온다.
			 * 예를들면
			 * ORACLE자료형	:	ResultSet
			 * NUMBER		:	getInt(인덱스 혹은 컬럼명)
			 * CHAR/NCHAR/VARCHAR2/NVARCHAR2	:		getString(인덱스 혹은 컬럼명)
			 * DATE			:	getDate(인덱스 혹은 컬렴명)
			 * 
			 * 인덱스 SQL에서는 1부터 시작
			 * 	*단, ORACLE의 자료형에 상관없이 getString(인덱스 혹은 컬럼명) 으로 읽어도 무방하다.
			 * 		DATE는 getString으로 읽어오면 차이는 있다.
			 
			 * 오라클에 저장된 데이터가 없는 경우 즉 null인 경우
			 * rs.getInt(인덱스 번호 혹은 컬럼명) = 0 반환
			 * rs.getString(인덱스 번호 혹은 컬럼명) = null 반환
			 */
			
			//3-1]여러개 레코드를 반환하는 경우
			while(rs.next()) { //더 이상 꺼내올 경우가 없을때
				int empno = rs.getInt(1);
				String ename = rs.getString("ename");
				String job = rs.getString("JOB");
				//int comm = rs.getInt("comm"); //값이 없는경우 0반환
				String comm = rs.getNString("comm")==null?"":rs.getNString("comm");
				
				//오라클의 Date타입을 getDate()로 가져오는 경우
				//-년월일만 반환
				//java.sql.Date hiredate = rs.getDate(5);
				
				//getString()으로 가져오는 경우
				//-년월일시분초까지 반환
				String hiredate = rs.getString(5).substring(0,10);
				
				System.out.println(String.format
						("%-5d%-10s%-10s%-5s%s",
						empno,ename,job,comm,hiredate));
				
			}
			//3-2]한 개의 레코드를 반환하거나 '선택된 레코드가 없습니다'라고 반환하는 경우
//			if(rs.next()) {
//				System.out.println(String.format("%-5s%-10s%-10s%-5s%s", 
//													rs.getString(1),
//													rs.getString(2),
//													rs.getString(3),
//													rs.getString(7)==null?"":rs.getString(7),
//													rs.getDate(5)));
//			}
			//3-3]무조건 레코드 하나 반환하는 경우
//			rs.next();
//			System.out.println("평균 연봉:"+rs.getFloat(1));
			
		}
		catch(Exception e) {
			System.out.println("오류 발생"+e.getMessage());
		}
		finally {
			//4] 자원반납
			close();
		}
	}
	
	public static void main(String[] args) throws Exception{
		new SelectSQL().execute();

	}

}
