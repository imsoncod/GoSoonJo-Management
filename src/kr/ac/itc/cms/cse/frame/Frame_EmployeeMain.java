package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_EmployeeMain extends JFrame implements ActionListener, WindowListener {

	private Container Frame_EmployerMain = getContentPane();
	private JLabel label_info_name;
	private JButton btn_setting, btn_registwork, btn_board, btn_chat, btn_logout;
	private String id = null;
	private Oracle_DAO oracle_DAO = new Oracle_DAO();

	public Frame_EmployeeMain(String id) {
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
		label_info_name = new JLabel(" " + Oracle.OracleGetData("userinfo",Frame_Login.email, "username") + " 직원");
		label_info_name.setFont(new Font("휴먼모음T", Font.PLAIN, 20));
		label_info_name.setBackground(SystemColor.activeCaption);
		label_info_name.setHorizontalAlignment(SwingConstants.CENTER);
		label_info_name.setForeground(Color.WHITE);
		label_info_name.setBackground(new Color(32, 33, 42));
		label_info_name.setBounds(7, 5, 335, 41);
		label_info_name.setOpaque(true);
		Frame_EmployerMain.add(label_info_name);

		btn_logout = new JButton("로그아웃");
		btn_logout.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_logout.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_logout.setForeground(new Color(32, 33, 42));
		btn_logout.setBackground(Color.WHITE);
		btn_logout.setBounds(7, 219, 335, 42);
		btn_logout.setOpaque(true);
		Frame_EmployerMain.add(btn_logout);
		btn_logout.addActionListener(this);

		btn_registwork = new JButton("출근부 등록");
		btn_registwork.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_registwork.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_registwork.setForeground(new Color(32, 33, 42));
		btn_registwork.setBounds(5, 51, 170, 165);
		btn_registwork.setBackground(Color.WHITE);
		Frame_EmployerMain.add(btn_registwork);
		btn_registwork.addActionListener(this);

		btn_board = new JButton("게시판");
		btn_board.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_board.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_board.setForeground(new Color(32, 33, 42));
		btn_board.setBounds(177, 51, 165, 80);
		btn_board.setBackground(Color.WHITE);
		Frame_EmployerMain.add(btn_board);
		btn_board.addActionListener(this);

		btn_chat = new JButton("채팅방");
		btn_chat.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_chat.setBorder(new LineBorder(new Color(32, 33, 42),2));
		btn_chat.setForeground(new Color(32, 33, 42));
		btn_chat.setBounds(177, 136, 165, 80);
		btn_chat.setBackground(Color.WHITE);
		Frame_EmployerMain.add(btn_chat);
		btn_chat.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		if(command == btn_registwork) {
			if(Frame_RegistWork.opencheck_registwork == 0) {
				new Frame_RegistWork();
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
			if(oracle_DAO.OracleLogOut(id) == 0) {
				new Frame_Login();
				dispose();
			}
		}
	}


	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent arg0) {
		if(oracle_DAO.OracleLogOut(id) == 0) {
			new Frame_Login();
			dispose();
		}
		
	}


	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
