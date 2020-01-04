package kr.ac.itc.cms.cse.frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

public class Frame_EmployerMain extends JFrame implements ActionListener, WindowListener {

	private Container Frame_EmployerMain = getContentPane();
	private JLabel label_info_name;
	private JButton btn_setting, btn_management, btn_board, btn_chat, btn_logout;
	private String id = null;
	private Oracle_DAO oracle_DAO = new Oracle_DAO();

	public Frame_EmployerMain(String id) {
		this.id = id;
		setTitle("메인 화면");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800,200);
		getContentPane().setLayout(null);
		setSize(353,295);
		setResizable(false);
		setEmployerMainFrame();
		
		addWindowListener(this);

		setVisible(true);
	}
	

	private void setEmployerMainFrame() {
		Frame_EmployerMain.setBackground(Color.WHITE);
		Oracle_DAO Oracle = new Oracle_DAO();
		label_info_name = new JLabel(" " + Oracle.OracleGetData("userinfo",Frame_Login.email, "username") + " 사장님");
		label_info_name.setFont(new Font("휴먼모음T", Font.PLAIN, 20));
		label_info_name.setHorizontalAlignment(SwingConstants.CENTER);
		label_info_name.setForeground(Color.WHITE);
		label_info_name.setBackground(new Color(32, 33, 42));
		label_info_name.setBounds(7, 5, 335, 41);
		label_info_name.setOpaque(true);
		Frame_EmployerMain.add(label_info_name);

		btn_logout = new JButton("로그아웃");
		btn_logout.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_logout.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_logout.setBackground(Color.WHITE);
		btn_logout.setForeground(new Color(32, 33, 42));
		btn_logout.setBounds(7, 219, 335, 42);
		btn_logout.setOpaque(true);
		Frame_EmployerMain.add(btn_logout);
		btn_logout.addActionListener(this);

		btn_management = new JButton("직원 출근부 관리");
		btn_management.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_management.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_management.setForeground(new Color(32, 33, 42));
		btn_management.setBackground(Color.WHITE);
		btn_management.setBounds(5, 51, 170, 165);
		Frame_EmployerMain.add(btn_management);
		btn_management.addActionListener(this);

		btn_board = new JButton("게시판");
		btn_board.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_board.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_board.setForeground(new Color(32, 33, 42));
		btn_board.setBackground(Color.WHITE);
		btn_board.setBounds(177, 51, 165, 80);
		Frame_EmployerMain.add(btn_board);
		btn_board.addActionListener(this);

		btn_chat = new JButton("채팅방");
		btn_chat.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_chat.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_chat.setForeground(new Color(32, 33, 42));
		btn_chat.setBackground(Color.WHITE);
		btn_chat.setBounds(177, 136, 165, 80);
		Frame_EmployerMain.add(btn_chat);
		btn_chat.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		if(command == btn_management) {
			if(Frame_Management.opencheck_management == 0) {
				new Frame_Management();
			}else {
				return;
			}
		}
		if(command == btn_board) {
			if(Frame_NoticeBoard.opencheck_noticeboard == 0) {
				new Frame_NoticeBoard();
			}else {
				return;
			}
		}
		if(command == btn_chat) {
			if(Frame_UserList.opencheck_userlist == 0) {
				new Frame_UserList(700, 200, id);
			}else {
				return;
			}
		}
		if(command == btn_logout) {
			System.out.println(id);
			if(oracle_DAO .OracleLogOut(id) == 0) {
				new Frame_Login();
				dispose();
			}
		}
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		if(oracle_DAO.OracleLogOut(id) == 0) {
			new Frame_Login();
			dispose();
		}
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
