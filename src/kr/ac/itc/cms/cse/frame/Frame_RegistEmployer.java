package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;

public class Frame_RegistEmployer extends JFrame implements ActionListener, WindowListener {

	private Container Frame_RegisterEmployer = getContentPane();
	private JLabel label_regist, label_email;
	private JTextField txt_email_input;
	private JButton btn_regist, btn_cancel;
	private int data_waiting;
	private int check = -1;
	public static String email;
	
	private Oracle_DAO oracle_DAO = new Oracle_DAO();
	
	public Frame_RegistEmployer(String id) {
		setTitle("사장님 등록");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800, 100);
		setLayout(null);
		setSize(353, 219);
		setResizable(false);
		addWindowListener(this);

		setRegisterEmployerFrame();
		setVisible(true);
	}

	private void setRegisterEmployerFrame() {
		Frame_RegisterEmployer.setBackground(Color.white);
		
		label_regist = new JLabel("사장님 등록");
		label_regist.setBackground(new Color(32, 33, 42));
		label_regist.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_regist.setForeground(Color.WHITE);
		label_regist.setHorizontalAlignment(SwingConstants.CENTER);
		label_regist.setBounds(0, 0, 347, 42);
		label_regist.setOpaque(true);
		Frame_RegisterEmployer.add(label_regist);

		label_email = new JLabel("이메일 :");
		label_email.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_email.setHorizontalAlignment(SwingConstants.CENTER);
		label_email.setBounds(34, 68, 72, 27);
		Frame_RegisterEmployer.add(label_email);

		txt_email_input = new JTextField();
		txt_email_input.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		txt_email_input.setBounds(107, 67, 205, 30);
		Frame_RegisterEmployer.add(txt_email_input);
		txt_email_input.setColumns(10);

		btn_regist = new JButton("등록");
		btn_regist.setBounds(83, 129, 72, 36);
		btn_regist.setBackground(new Color(32, 33, 42));
		btn_regist.setForeground(Color.WHITE);
		btn_regist.setFocusable(false);
		Frame_RegisterEmployer.add(btn_regist);
		btn_regist.addActionListener(this);

		btn_cancel = new JButton("취소");
		btn_cancel.setBackground(new Color(32, 33, 42));
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setFocusable(false);
		btn_cancel.setBounds(194, 129, 72, 36);
		Frame_RegisterEmployer.add(btn_cancel);
		btn_cancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		if (btn_regist == command) {
			email = txt_email_input.getText();
			Oracle_DAO Oracle = new Oracle_DAO();
			try {
				data_waiting = Integer.parseInt(Oracle.OracleGetData("userinfo", email, "userwaiting"));
				check = 0;
			} catch (NumberFormatException nfe) {
				new Frame_MessageBox("메시지", "존재하지 않는 아이디이거나 대상이 직원입니다.");
			}
			if(check == 0) {
				if(data_waiting == 4) {
					new Frame_MessageBox("메시지", "상대방의 요청 대기목록이 가득 찼습니다.");
				}else {
					Oracle.OracleSetData("userinfo",email, "userwaiting", String.valueOf(++data_waiting));
					Oracle.OracleSetData("userinfo",Frame_Login.email, "useremployercheck", "W");
					Oracle.OracleSetData("userinfo",Frame_Login.email, "useremployerid", email);
					new Frame_MessageBox("메시지", "등록 요청이 완료되었습니다.");
					if(oracle_DAO.OracleLogOut(Frame_Login.email) == 0) {
						new Frame_Login();
						dispose();
					}
				}
			}
		}
		if (btn_cancel == command) {
			if(oracle_DAO.OracleLogOut(Frame_Login.email) == 0) {
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
		if(oracle_DAO.OracleLogOut(Frame_Login.email) == 0) {
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
