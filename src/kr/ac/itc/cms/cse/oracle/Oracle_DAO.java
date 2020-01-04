package kr.ac.itc.cms.cse.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import kr.ac.itc.cms.cse.frame.Frame_Management;
import kr.ac.itc.cms.cse.frame.Frame_NoticeBoard;
import kr.ac.itc.cms.cse.frame.Frame_Waiting;
import kr.ac.itc.cms.cse.mail.MailListener;

public class Oracle_DAO {

	public static Logger log = Logger.getLogger(Oracle_DAO.class);

	final private String commit = "COMMIT";
	final private String driver = "oracle.jdbc.driver.OracleDriver";
	final private String url = "jdbc:oracle:thin:@183.91.253.86:1521:xe";
	final private String user = "project";
	final private String password = "root";

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String time = null;
	private String sql = null;
	private int check = -1;
	private int num = 0;
	private MailListener mail = new MailListener();

	// Oracle Loading
	public int OracleLoading() {
		try {
			log.info("Loading Oracle driver");
			Class.forName(driver);
			log.info("Oracle driver Load Complete");
			log.info("Connecting to Oracle");
			DriverManager.getConnection(url, user, password);
			log.info("Oracle Connection Successful");
			check = 0;
		} catch (ClassNotFoundException e) {
			log.error("Failed to load Oracle driver");
			e.printStackTrace();
		} catch (SQLException e) {
			log.error("Oracle connection failed");
			e.printStackTrace();
		}
		return check;
	}

	public int OracleDateOverlap(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userinfo where userid = '" + id + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next() == false) {
				check = 0;
				log.info("Your PIN number has been emailed.");
			} else {
				check = -1;
			}
		} catch (Exception e) {
			log.error("Oracle connection failed");
			e.printStackTrace();
		}

		return check;
	}

	// Create Userinfo
	public void OracleCreate(String id, String pw, String name, String phone, String job, String gender, String year,
			String month, String date, int logincheck, String employercheck, String employerid, String waiting,
			String timepay, String workinfo) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select count(*) from userinfo";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs == null) {
				System.out.println(time + "레코드가 없습니다.");
			} else {
				if (rs.next() == true) {
					num = rs.getInt(1);
				}
			}

			if (OracleDateOverlap(id) == 0) {
				sql = "INSERT INTO USERINFO VALUES(" + (num + 1) + ", '" + id + "', '" + pw + "', '" + name + "', '"
						+ phone + "', '" + job + "', '" + gender + "', '" + year + "', '" + month + "', '" + date
						+ "', " + logincheck + ", '" + employercheck + "', '" + employerid + "', '" + waiting + "','"
						+ timepay + "','" + workinfo + "')";
				rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(commit);
				log.info("Account creation completed");
				log.info("User ID : " + id);
			}
		} catch (Exception e) {
			log.error("Account creation failed");
			e.printStackTrace();
		}
	}

	// Find ID
	public String OracleFindID(String name, String phone, String year, String month, String date) {
		try {
			con = DriverManager.getConnection(url, user, password);

			stmt = con.createStatement();

			if (name.isEmpty() == true) {
				check = -1;
			} else if (name.isEmpty() == false) {
				sql = "select * from userinfo where username = '" + name + "' and userphone = '" + phone + "'";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					if (rs.getString("useryear").equals(year) == true && rs.getString("usermonth").equals(month) == true
							&& rs.getString("userdate").equals(date) == true) {
						check = 0;
						return rs.getString("userid");
					} else if (rs.getString("useryear").equals(year) == false
							|| rs.getString("usermonth").equals(month) == false
							|| rs.getString("userdate").equals(date) == false) {
						check = -1;
					}
				}
			}
		} catch (Exception e) {
			log.error("Find ID failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
		return null;
	}

	// Find Password
	public int OracleFindPW(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userinfo where userid = '" + id + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (id.isEmpty() == true) {
				check = -1;
			} else {
				sql = "select * from userinfo where userid = '" + id + "'";
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					mail.SendPWMail(id, rs.getString("userpw"));
					check = 0;
					break;
				}
			}
		} catch (Exception e) {
			log.error("Find Password failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
		return check;
	}

	public int OracleLogOut(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userinfo where userid ='" + id + "' and userlogincheck = 0";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next() == true) {
				sql = "update userinfo set userlogincheck =-1 where userid ='" + id + "'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(commit);
				log.info("Logout");
				check = 0;
			} else {
				check = -1;
			}

		} catch (Exception e) {
			log.error("Login failed");
			e.printStackTrace();
		}
		return check;
	}

	public int OracleLoginCheck(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userinfo where userid ='" + id + "' and userlogincheck =-1";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next() == true) {
				sql = "update userinfo set userlogincheck= 0 where userid ='" + id + "'";
				rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(commit);
			} else {
				check = 1;
			}
		} catch (Exception e) {
			log.error("Login failed");
			e.printStackTrace();
		}
		return check;
	}

	// Check Login Possible
	public int OracleLogin(String id, String pw) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userinfo where userid ='" + id + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if ((id.isEmpty() == true)) {
				check = -1;
			} else {
				sql = "select * from userinfo where userid ='" + id + "'";
				rs = stmt.executeQuery(sql);
				while (rs.next() == true) {
					if (rs.getString("userpw").equals(pw)) {
						check = 0;
					} else {
						check = 1;
					}
				}
			}
		} catch (Exception e) {
			log.error("Login failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
		return check;
	}

	// Get Data Only One from DataBase
	public String OracleGetData(String table, String id, String attribute) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select " + attribute + " from " + table + " where userid = '" + id + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				return rs.getString(attribute);
			}
		} catch (Exception e) {
			log.error("Data get failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
		return null;
	}

	// Modify Data Only One Of DataBase
	public void OracleSetData(String table, String id, String attribute, String changedata) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "update " + table + " set " + attribute + " = '" + changedata + "' where userid = '" + id + "'";
			rs = stmt.executeQuery(sql);
			rs = stmt.executeQuery(commit);
		} catch (Exception e) {
			log.error("Data Modify failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Delete Post From NoticeBoard
	public void OracleDeletePost(String table, String id, String title) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "delete from " + table + " where userid = '" + id + "' and title = '" + title + "'";
			rs = stmt.executeQuery(sql);
			rs = stmt.executeQuery(commit);
		} catch (Exception e) {
			log.error("Post Delete failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Delete Data Of DataBase
	public void OracleDeleteData(String table, String id) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "delete from " + table + " where userid = '" + id + "'";
			rs = stmt.executeQuery(sql);
			rs = stmt.executeQuery(commit);
		} catch (Exception e) {
			log.error("Data Delete failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Set WaitingList JTable Data
	public void OracleGetRow1(String employerid) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select username, usergender, useryear,usermonth,userdate, userphone,userid from userinfo where useremployercheck = 'W' and useremployerid = '"
					+ employerid + "' order by username";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Frame_Waiting.tablemodel.addRow(new Object[] { rs.getString(1), rs.getString(2),
						rs.getString(3) + "/" + rs.getString(4) + "/" + rs.getString(5), rs.getString(6),
						rs.getString(7) });
			}
		} catch (SQLException e) {
			log.error("WaitingList Set failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Set EmployeeList JTable Data
	public void OracleGetRow2(String employerid) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select username, usergender, useryear,usermonth,userdate, userphone,userid from userinfo where useremployercheck = 'Y' and useremployerid = '"
					+ employerid + "' order by username";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Frame_Management.tablemodel.addRow(new Object[] { rs.getString(1), rs.getString(2),
						rs.getString(3) + "/" + rs.getString(4) + "/" + rs.getString(5), rs.getString(6),
						rs.getString(7) });
			}
		} catch (SQLException e) {
			log.error("EmployeeList Set failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Create Post on NoticeBoard
	public void OracleSetRowBoard(String id, String name, String title, String time, String content) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "insert into noticeboard(userid, username, title, time, content) values('" + id + "','" + name + "','"
					+ title + "','" + time + "','" + content + "')";
			rs = stmt.executeQuery(sql);
			rs = stmt.executeQuery(commit);
		} catch (Exception e) {
			log.error("Post Insert failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Get Post From NoticeBoard
	public void OracleGetRowBoard(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select username, title, time from noticeboard where userid = '" + id + "' order by time desc";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Frame_NoticeBoard.tablemodel.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) });
			}
		} catch (SQLException e) {
			log.error("Post Get failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Get Content From NoticeBoard
	public String OracleGetNoticeBoardData(String table, String id, String title, String attribute) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select " + attribute + " from " + table + " where userid = '" + id + "' and title = '" + title + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				return rs.getString(attribute);
			}
		} catch (Exception e) {
			log.error("Content Get failed");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
		return null;
	}

	public static int count = 0;
	public static String array_year[], array_month[], array_date[];
	public static int number = 0;

	// Get WorkDate From DataBase
	public void OracleGetWorkCalendar(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select count(*) from workcalendar where userid = '" + id + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt("COUNT(*)");
			}
			array_year = new String[count];
			array_month = new String[count];
			array_date = new String[count];
			sql = "select workyear, workmonth, workdate from workcalendar where userid = '" + id + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				array_year[number] = rs.getString(1);
				array_month[number] = rs.getString(2);
				array_date[number] = rs.getString(3);
				number++;
			}
		} catch (Exception e) {
			log.error("Get Workdate failed");
			e.printStackTrace();
		} finally {
			number = 0;
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Regist Workinfo to Calendar
	public void OracleRegistWorkCalendar(String id, String year, String month, String date, String workinfo,
			String timepay, String time) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "insert into workcalendar(userid, workyear, workmonth, workdate, workinfo, worktimepay, worktime) values('"
					+ id + "','" + year + "','" + month + "','" + date + "','" + workinfo + "','" + timepay + "','"
					+ time + "')";
			rs = stmt.executeQuery(sql);
			rs = stmt.executeQuery(commit);
		} catch (Exception e) {
			log.error("Regist Workinfo failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Modify Workinfo of DataBase
	public void OracleChangeWorkCalendar(String id, String attribute, String changedata, String year, String month,
			String date) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "update workcalendar set " + attribute + "='" + changedata + "' where userid = '" + id
					+ "' and workyear='" + year + "'and workmonth ='" + month + "'and workdate='" + date + "'";
			rs = stmt.executeQuery(sql);
			rs = stmt.executeQuery(commit);
		} catch (Exception e) {
			log.error("Modify Workinfo failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
	}

	// Check WorkDate Exist From Calendar
	public String OracleGetWorkCalendarExist(String id, String year, String month, String date) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select count(*) from workcalendar where userid = '" + id + "'and workyear='" + year
					+ "'and workdate = '" + date + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			log.error("Check Workdate Exist failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
		return null;
	}

	// Get Worktime From DataBase
	public String OracleGetWorkTime(String id, String year, String month) {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			sql = "select sum(worktime) 총시간 from workcalendar where userid = '" + id + "'and + workyear ='" + year
					+ "'and workmonth ='" + month + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			log.error("Get Worktime failed");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			;
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			;
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			;
		}
		return null;
	}
}