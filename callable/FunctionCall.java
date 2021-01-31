package jdbc25.callable;

import jdbc25.service.IConnectImpl;

/*
 *  create or replace function to_asterisk(str varchar2)
	   return varchar2
	is
	   first varchar2(20);
	begin
	   first := rpad(substr(str,1,1),length(str),'*');
	   return first;
	end;
 */


public class FunctionCall extends IConnectImpl {
	public FunctionCall() {
		super(ORACLE_URL,"SCOTT","scott");
	}
	@Override
	public void execute() throws Exception {

	}

	public static void main(String[] args) throws Exception {
		new FunctionCall().execute();

	}

}
