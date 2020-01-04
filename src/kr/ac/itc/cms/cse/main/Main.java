package kr.ac.itc.cms.cse.main;

import org.apache.log4j.Logger;

import kr.ac.itc.cms.cse.frame.Frame_Login;
import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Main {
	
	public static Logger log = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		Oracle_DAO Oracle = new Oracle_DAO();
		
		if(Oracle.OracleLoading() == 0) {
			new Frame_Login();
		}
	}

}