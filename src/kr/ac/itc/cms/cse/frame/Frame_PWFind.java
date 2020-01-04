package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import kr.ac.itc.cms.cse.mail.MailListener;
import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

@SuppressWarnings("serial")
public class Frame_PWFind extends JFrame implements ActionListener, WindowListener {

	public static Logger log = Logger.getLogger(Frame_PWFind.class);
	
	private Container FrameFindPW = getContentPane();
	private JButton btn_id_find, btn_pw_find, btn_find, btn_cancel, btn_check, btn_send;
	private JTextField txt_mail_input, txt_code_input;
	
	private Oracle_DAO oracle = new Oracle_DAO();
	private MailListener mail = new MailListener();
	
	public static int opencheck_pwfind = 0;

	public Frame_PWFind(int x, int y) {
		log.info("Password find frame creation completed");
		setTitle("아이디 / 비밀번호 찾기");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(x, y);
		setSize(404, 300);
		setLayout(null);
		setResizable(false);
		addWindowListener(this);
		
		opencheck_pwfind = 1;

		setFrameFindPW();

		setVisible(true);
	}

	private void setFrameFindPW() {

		Image img_idfind = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/아이디찾기.png"));
		btn_id_find = new JButton(new ImageIcon(img_idfind));
		btn_id_find.setBounds(0, 11, 93, 35);
		btn_id_find.setFocusPainted(false);
		btn_id_find.setBorderPainted(false);
		btn_id_find.setContentAreaFilled(false);
		btn_id_find.addActionListener(this);
		btn_id_find.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_pwfind = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/비밀번호찾기선택탭.png"));
		btn_pw_find = new JButton(new ImageIcon(img_pwfind));
		btn_pw_find.setBounds(91, 11, 116, 35);
		btn_pw_find.setFocusPainted(false);
		btn_pw_find.setBorderPainted(false);
		btn_pw_find.setContentAreaFilled(false);
		btn_pw_find.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_find = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/비밀번호찾기버튼.png"));
		btn_find = new JButton(new ImageIcon(img_find));
		btn_find.setBounds(85, 210, 130, 35);
		btn_find.setFocusPainted(false);
		btn_find.setBorderPainted(false);
		btn_find.setContentAreaFilled(false);
		btn_find.addActionListener(this);
		btn_find.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_cancel = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/취소버튼.png"));
		btn_cancel = new JButton(new ImageIcon(img_cancel));
		btn_cancel.setBounds(225, 210, 90, 35);
		btn_cancel.setFocusPainted(false);
		btn_cancel.setBorderPainted(false);
		btn_cancel.setContentAreaFilled(false);
		btn_cancel.addActionListener(this);
		btn_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_stick = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/막대기.png"));
		JLabel label_stick = new JLabel(new ImageIcon(img_stick));
		label_stick.setBounds(-7, 42, 400, 13);

		Image img_send = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/인증번호전송.png"));
		btn_send = new JButton(new ImageIcon(img_send));
		btn_send.setBounds(250, 67, 130, 75);
		btn_send.setFocusPainted(false);
		btn_send.setBorderPainted(false);
		btn_send.setContentAreaFilled(false);
		btn_send.addActionListener(this);
		btn_send.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_check = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/인증.png"));
		btn_check = new JButton(new ImageIcon(img_check));
		btn_check.setBounds(240, 151, 70, 30);
		btn_check.setFocusPainted(false);
		btn_check.setBorderPainted(false);
		btn_check.setContentAreaFilled(false);
		btn_check.addActionListener(this);
		btn_check.setCursor(new Cursor(Cursor.HAND_CURSOR));

		txt_mail_input = new JTextField();
		txt_mail_input.setBounds(75, 89, 160, 30);
		txt_mail_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_mail_input.setFont(new Font("맑은 고딕", Font.BOLD, 14));

		txt_code_input = new JTextField();
		txt_code_input.setBounds(155, 150, 70, 30);
		txt_code_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_code_input.setFont(new Font("맑은 고딕", Font.BOLD, 14));

		JLabel label_name = new JLabel("이메일");
		label_name.setBounds(15, 52, 60, 100);
		label_name.setForeground(new Color(31, 31, 31));
		label_name.setFont(new Font("맑은 고딕", Font.BOLD, 16));

		JLabel label_code = new JLabel("인증번호");
		label_code.setBounds(85, 112, 70, 100);
		label_code.setForeground(new Color(31, 31, 31));
		label_code.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		FrameFindPW.setBackground(Color.WHITE);

		FrameFindPW.add(btn_id_find);
		FrameFindPW.add(btn_pw_find);

		FrameFindPW.add(txt_mail_input);
		FrameFindPW.add(txt_code_input);

		FrameFindPW.add(label_stick);

		FrameFindPW.add(label_name);
		FrameFindPW.add(label_code);

		FrameFindPW.add(btn_send);
		FrameFindPW.add(btn_check);

		FrameFindPW.add(btn_find);
		FrameFindPW.add(btn_cancel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_id_find) {
			dispose();
			new Frame_IDFind(getX(), getY(), "");
		} else if (obj == btn_cancel) {
			dispose();
		} else if (obj == btn_find) {
			if(mail.getCheck() == true) {
				oracle.OracleFindPW(txt_mail_input.getText());
			} else {
				new Frame_MessageBox("오류발생", "이메일을 인증해주세요.");
			}
		} else if (obj == btn_send) {
			mail.SendMail(txt_mail_input.getText());
		} else if (obj == btn_check) {
			mail.CodeCheck(txt_code_input.getText());
			if(mail.getCheck() == true) { 
				new Frame_MessageBox("메세지", "이메일이 인증되었습니다.");
			} 
			else {
				new Frame_MessageBox("오류발생", "인증번호를 틀리셨습니다.");
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		opencheck_pwfind = 0;
		
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