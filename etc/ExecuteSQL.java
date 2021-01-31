package jdbc25.etc;

import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import jdbc25.service.IConnectImpl;

public class ExecuteSQL extends IConnectImpl {
	String id, pwd;
	String querys="";
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd HH:mm:ss yyyy");
	StringBuffer sb=new StringBuffer();
	int titleNum;
	Scanner sc = new Scanner(System.in);

	public void start() {
		while(true) {
			System.out.print("C:\\Users\\kosmo>");
			querys=sc.nextLine();
			try {
				if(querys.equalsIgnoreCase("sqlplus")) {
					System.out.print("SQL*Plus: Release 11.2.0.1.0 Production on 월 "+dateFormat.format(new Date())+" \r\n"
							+ "\r\n"
							+ "Copyright (c) 1982, 2010, Oracle.  All rights reserved.\r\n"
							+ "\r\n");
					System.out.print("사용자명 입력:");
					id = sc.nextLine();
					userId = id;
					System.out.print("비밀번호 입력:");
					pwd = sc.nextLine();
					conn = DriverManager.getConnection(ORACLE_URL, id, pwd);
					System.out.println("다음에 접속됨:\r\n" + 
							"Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production\r\n" + 
							"With the Partitioning, OLAP, Data Mining and Real Application Testing options");
					break;
				}else if(querys.trim().startsWith("sqlplus")) {
					System.out.print("SQL*Plus: Release 11.2.0.1.0 Production on 월 "+dateFormat.format(new Date())+" \r\n"
							+ "\r\n"
							+ "Copyright (c) 1982, 2010, Oracle.  All rights reserved.\r\n"
							+ "\r\n");
					if(querys.trim().contains("/")) {
						conn = DriverManager.getConnection(ORACLE_URL, 
								querys.substring(querys.lastIndexOf(" ")+1,querys.lastIndexOf("/")).trim(), querys.substring(querys.lastIndexOf("/")+1).trim());
						userId = querys.substring(querys.lastIndexOf(" ")+1,querys.lastIndexOf("/")).trim();
					}else if(querys.trim().contains(" ")) {
						System.out.println("비밀번호 입력:");
						String pwd = sc.nextLine();
						conn = DriverManager.getConnection(ORACLE_URL,querys.substring(querys.lastIndexOf(" ")+1).trim(), pwd);
						userId = querys.substring(querys.lastIndexOf(" ")+1).trim();
					}
					System.out.println("다음에 접속됨:\r\n" + 
							"Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production\r\n" + 
							"With the Partitioning, OLAP, Data Mining and Real Application Testing options");
				}else
					System.out.println("'"+querys+"'은(는) 내부 또는 외부 명령,"+ " 실행할 수 있는 프로그램, 또는\r\n" +"배치 파일이 아닙니다.\r\n" + "");
				break;
			}catch(Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}//while

	@Override
	public void execute() throws Exception {

		start();

		while(true) {
			//1]쿼리문 준비
			String query = getQueryString();

			if("EXIT".equalsIgnoreCase(query.trim())) {
				System.out.println("Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production\r\n" + 
						"With the Partitioning, OLAP, Data Mining and Real Application Testing options에서 분리되었습니다.");
				//자원반납]
				close();
				//프로그램 종료]
				System.exit(0);
			}

			if(query.trim().toUpperCase().startsWith("DESC") && query.trim().length() != 4 && query.trim().charAt(4)==' '){
                
	               String descTable = query.trim().substring(4, query.trim().length()).trim();
	               String descQuery = String.format("select * from %s",descTable);
	               psmt = conn.prepareStatement(descQuery);
	               rs=psmt.getResultSet();
	               try {
	                  psmt.execute();
	               }catch(SQLException e){
	                  System.out.println(e.getMessage());
	                  continue;
	               }
	               ResultSetMetaData rsmd=rs.getMetaData();
	               
	               
	               System.out.println(String.format("%-15s%-10s%-15s","NAME","NULL","TYPE"));
	               System.out.println("-------------- --------- ---------------");
	               for(int i = 1 ; i <= rsmd.getColumnCount(); i++) {
	                  
	               String type = rsmd.getScale(i) != 0 ? String.format("%s(%s,%s)",rsmd.getColumnTypeName(i),rsmd.getPrecision(i),rsmd.getScale(i))
	                  : rsmd.getPrecision(i) == 0 ? rsmd.getColumnTypeName(i)
	                     : String.format("%s(%s)",rsmd.getColumnTypeName(i),rsmd.getPrecision(i));   
	               
	               
	                  
	               System.out.println(String.format("%-15s%-10s%-15s",
	                     rsmd.getColumnName(i),rsmd.isNullable(i) == 1 ? "":"NOT NULL" ,type));
	               }
	               System.out.println();
	               
	               continue;      
	            }

			try {
				if("CONN".equalsIgnoreCase(query.trim())) {
					System.out.print("사용자명 입력:");
					id = sc.nextLine();
					userId = id;
					System.out.print("비밀번호 입력:");
					pwd = sc.nextLine();
					conn = DriverManager.getConnection(ORACLE_URL, id, pwd);
					System.out.println("연결되었습니다.");
					continue;
				}else if(query.trim().startsWith("conn")) {
					if(query.trim().contains("/")) {
						conn = DriverManager.getConnection(ORACLE_URL, 
								query.substring(query.lastIndexOf(" ")+1,query.lastIndexOf("/")).trim(), query.substring(query.lastIndexOf("/")+1).trim());
						userId = query.substring(query.lastIndexOf(" ")+1,query.lastIndexOf("/")).trim();
						System.out.println("연결되었습니다.");
					}else if(query.trim().contains(" ")) {
						System.out.print("비밀번호 입력:");
						String pwd = sc.nextLine();
						conn = DriverManager.getConnection(ORACLE_URL,query.substring(query.lastIndexOf(" ")+1).trim(), pwd);
						userId = query.substring(query.lastIndexOf(" ")+1).trim();
						System.out.println("연결되었습니다.");
					}
					continue;
				}
			}
			catch(SQLException e) {
				System.out.println("계정 연결 오류");
			}
			try {
				
				psmt = conn.prepareStatement(query);
				
				if("SHOW USER".equalsIgnoreCase(query)) {
					System.out.println("USER은\""+userId+"\"입니다");
					continue;
				}

				boolean Flag=psmt.execute();
				if(Flag) {//쿼리문이 SELECT
					//ResultSet객체 얻기]
					rs=psmt.getResultSet();
					//가]ResultSet객체의 getMetaData()로 ResultSetMetaData얻기
					ResultSetMetaData rsmd= rs.getMetaData();
					//나]총 컬럼수 얻기-ResultSetMetaData의 int getColumnCount()
					int columnCount = rsmd.getColumnCount();
					List<Integer> dashCount = new Vector<Integer>();
					for(int i=1; i <=columnCount ;i++) {
						//컬럼타입]
						int types = rsmd.getColumnType(i);
						//컬럼의 자리수]
						int length = rsmd.getPrecision(i);
						switch(types) {
						case Types.NCHAR:
						case Types.NVARCHAR:
							dashCount.add(length*2);break;
						case Types.TIMESTAMP:
						case Types.NUMERIC:
							dashCount.add(10);break;
						default:dashCount.add(length);
						}

						String columnName = rsmd.getColumnName(i).length() > dashCount.get(i-1) ?
								rsmd.getColumnName(i).substring(0,dashCount.get(i-1)) :
									rsmd.getColumnName(i);
								System.out.print(String.format("%-"+(dashCount.get(i-1)+1)+"s", columnName));

					}////////for
					System.out.println();//줄바꿈
					//(-)DASH출력]
					for(Integer dash:dashCount) {
						for(int i=0;i < dash;i++) System.out.print("-");
						System.out.print(" ");
					}
					System.out.println();//줄바꿈
					//데이터 출력]
					while(rs.next()) {
						//각 컬럼값 뽑아오기]
						for(int i=1;i<=columnCount;i++) {
							int type=rsmd.getColumnType(i);
							if(type == Types.TIMESTAMP) {
								System.out.print(String.format("%-11s",rs.getDate(i)==null ? "" : rs.getDate(i)));
							}
							else {
								System.out.print(String.format("%-"+(dashCount.get(i-1)+1)+"s",rs.getString(i)==null ? "" : rs.getString(i)));
							}
						}///
						System.out.println();//줄바꿈
					}///////////while
				}
				else {//기타 쿼리문
					//영향받은 행의 수 얻기]
					int affected=psmt.getUpdateCount();
					if(query.trim().toUpperCase().startsWith("UPDATE")) {
						System.out.println(affected+"행이 수정되었어요");
					}
					else if(query.trim().toUpperCase().startsWith("DELETE")) {
						System.out.println(affected+"행이 삭제되었어요");
					}
					else if(query.trim().toUpperCase().startsWith("INSERT")) {
						System.out.println(affected+"행이 입력되었어요");
					}

				}
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}

		}/////////while


	}///////execute()
	public static void main(String[] args) throws Exception {
		new ExecuteSQL().execute();
	}/////////main

}/////////////class