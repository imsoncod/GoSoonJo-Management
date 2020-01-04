package kr.ac.itc.cms.cse.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import kr.ac.itc.cms.cse.frame.Frame_Chat;
import kr.ac.itc.cms.cse.frame.Frame_ChatInvite;
import kr.ac.itc.cms.cse.frame.Frame_ChatList;
import kr.ac.itc.cms.cse.frame.Frame_MessageBox;
import kr.ac.itc.cms.cse.frame.Frame_UserList;

public class Oracle_Chat {

	final private String commit = "COMMIT";
	final private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final private String user = "project";
	final private String password = "root";

	private String sql = null;
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int check = -1;
	private String id = null;
	private int num = 0;
	
	public static ArrayList<String> username = new ArrayList<String>();
	public static ArrayList<String> usercheck = new ArrayList<String>();
	
	public void OracleUserInfo(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userinfo where userid = '" + id + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next() == false) {
				new Frame_MessageBox("메시지", "존재하지 않는 아이디입니다.");
				return;
			} else {
				sql = "select * from userinfo where userid = '" + id + "'";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Oracle_ChatDTO.db_id = rs.getString("userid");
					Oracle_ChatDTO.db_pw = rs.getString("userpw");
					Oracle_ChatDTO.db_name = rs.getString("username");
					Oracle_ChatDTO.db_job = rs.getString("userjob");
					Oracle_ChatDTO.db_gender = rs.getString("usergender");
					Oracle_ChatDTO.db_year = rs.getString("useryear");
					Oracle_ChatDTO.db_month = rs.getString("usermonth");
					Oracle_ChatDTO.db_date = rs.getString("userdate");
					Oracle_ChatDTO.db_phone = rs.getString("userphone");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 껏다 켯을때 디비값 불러와서 친구목록 생성
	public void OracleUserList(String id, int check) {
		try {
			ArrayList<String> list = new ArrayList<String>();

			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userlist where userid = '" + id + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				list.add(rs.getString("list"));
			}

			for (int i = 0; i < list.size(); i++) {
				sql = "select * from userinfo where userid = '" + list.get(i) + "'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					if (check == 1) {
						Frame_UserList.setTableAdd(rs.getString("username"), rs.getString("usergender"),
								rs.getString("userphone").substring(0, 3) + "-"
										+ rs.getString("userphone").substring(3, 7) + "-"
										+ rs.getString("userphone").substring(7, 11));
					}
					if (check == 2) {
						try {
							Frame_ChatInvite.setTableAdd(rs.getString("username"), rs.getString("usergender"),
									rs.getString("userphone").substring(0, 3) + "-"
											+ rs.getString("userphone").substring(3, 7) + "-"
											+ rs.getString("userphone").substring(7, 11));
						} catch (Exception e) {
							return;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void OracleChatDelete(String name, String chatname) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "delete from chatlist where username ='" + name + "' and chatname = '" + chatname + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs = stmt.executeQuery(commit);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void OracleChatOpen(int x, int y, String id, String chatname, int index) {
		try {
			con = DriverManager.getConnection(url, user, password);

			String ip = null;

			sql = "select * from chatlist where userid = '" + id + "' and chatname = '" + chatname + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				ip = rs.getString("chatip");
				new Frame_Chat(x, y, rs.getString("chatname"), Oracle_ChatDTO.db_name, id, rs.getInt("chatport"), index);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void OracleChatList(String id) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from chatlist where userid = '" + id + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Frame_ChatList.setTableAdd(rs.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int OracleLogin(String id, String pw) {
		try {
			con = DriverManager.getConnection(url, user, password);

			sql = "select * from userinfo where userid = '" + id + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if ((id.isEmpty() == true)) {
				check = -1;
			} else {
				sql = "select * from userinfo where userid = '" + id + "'";
				rs = stmt.executeQuery(sql);
				while (rs.next() == true) {
					if (rs.getString("userpw").equals(pw)) {
						this.id = id;
						check = 0;
					} else {
						check = 1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;
	}

	public void OracleChatInvite(String phone, int port) {
		try {
			con = DriverManager.getConnection(url, user, password);
			String chatname = null;
			String id = null;
			String name = null;

			sql = "select * from userinfo where userphone ='" + phone + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getString("userid");
				name = rs.getString("username");
			}

			sql = "select * from chatlist where chatport =" + port + "";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				chatname = rs.getString("chatname");
			}
			
			sql = "select count(*) from chatlist ";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql = "select * from chatlist where username = '" + name + "' and chatport = " + port + "";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next() == true) {
				new Frame_MessageBox("메세지", "       이미 초대하셨습니다!         ");
			} else {
				sql = "insert into chatlist values(" + (num + 1) + ", '" + name + "', '" + id + "', '183.91.253.86', '" + chatname + "', " + port + ", '" + "○" + "')";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(commit);
				new Frame_MessageBox("메세지", "            초대했습니다.             ");
			} 

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int OracleChatListAdd(String id, String list) {
		try {
			con = DriverManager.getConnection(url, user, password);
			sql = "select count(*) from userlist";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next() == true) {
				num = rs.getInt(1);
			}

			sql = "select * from userlist where userid = '" + id + "' and list = '" + list + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next() == true) {
				new Frame_MessageBox("메세지", "이미 친구목록에 존재 합니다! ");
			} else {
				new Frame_MessageBox("메세지", "       친구 추가 완료!       ");
				sql = "insert into userlist values(" + (num + 1) + ",'" + id + "', '" + list + "')";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(commit);
				check = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;
	}

}
