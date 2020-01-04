package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import kr.ac.itc.cms.cse.client.Client_Main;
import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

@SuppressWarnings("serial")
public class Frame_Login extends JFrame implements ActionListener, FocusListener, KeyListener {

	public static Logger log = Logger.getLogger(Frame_Login.class);

	private JLabel label_logo;
	private Container LoginFrame = getContentPane();
	private JTextField txt_id_input;
	private JPasswordField txt_pw_input;
	private JButton btn_Find, btn_Create, btn_Login;
	public static String email;
	private Oracle_DAO Oracle = new Oracle_DAO();

	public Frame_Login() {
		log.info("Login Frame creation completed");
		setTitle("로그인 창");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(410, 285);
		setResizable(false);
		setLayout(null);

		setLoginFrame();

		setVisible(true);
	}

	private void setLoginFrame() {
		Image img_logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/고순조로고.png"));
		label_logo = new JLabel(new ImageIcon(img_logo));
		label_logo.setOpaque(true);
		label_logo.setBounds(10, 0, 400, 125);

		Image img_login = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/로그인.png"));
		btn_Login = new JButton(new ImageIcon(img_login));
		btn_Login.setBounds(292, 140, 99, 97);
		btn_Login.setBorderPainted(false);
		btn_Login.setContentAreaFilled(false);
		btn_Login.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_Login.addActionListener(this);

		txt_id_input = new JTextField("아이디");
		txt_id_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_id_input.setBounds(92, 145, 183, 30);
		txt_id_input.addFocusListener(this);

		txt_pw_input = new JPasswordField("****");
		txt_pw_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_pw_input.setBounds(92, 185, 183, 30);
		txt_pw_input.setEchoChar('*');
		txt_pw_input.addFocusListener(this);
		txt_pw_input.addKeyListener(this);

		Image img_find = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/아디비번찾기.png"));
		btn_Find = new JButton(new ImageIcon(img_find));
		btn_Find.setBorderPainted(false);
		btn_Find.setContentAreaFilled(false);
		btn_Find.setBounds(70, 214, 140, 30);
		btn_Find.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_Find.addActionListener(this);

		Image img_create = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/회원가입.png"));
		btn_Create = new JButton(new ImageIcon(img_create));
		btn_Create.setBorderPainted(false);
		btn_Create.setContentAreaFilled(false);
		btn_Create.setBounds(180, 214, 140, 30);
		btn_Create.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_Create.addActionListener(this);

		JLabel label_id = new JLabel("아이디");
		label_id.setBounds(25, 143, 183, 30);
		label_id.setForeground(new Color(31, 31, 31));
		label_id.setFont(new Font("맑은 고딕", Font.BOLD, 16));

		JLabel label_pw = new JLabel("비밀번호");
		label_pw.setBounds(15, 185, 183, 30);
		label_pw.setForeground(new Color(31, 31, 31));
		label_pw.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		Image img_stick = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/막대기.png"));
		JLabel label_Stick = new JLabel(new ImageIcon(img_stick));
		label_Stick.setBounds(2, 114, 400, 30);

		JLabel label_line = new JLabel("l");
		label_line.setBounds(215, 214, 30, 28);
		label_line.setForeground(new Color(25, 25, 25));
		label_line.setFont(new Font("맑은 고딕", Font.PLAIN, 18));

		LoginFrame.setBackground(Color.WHITE);
		LoginFrame.add(label_logo);
		LoginFrame.add(label_Stick);
		LoginFrame.add(btn_Login);

		LoginFrame.add(txt_id_input);
		LoginFrame.add(txt_pw_input);

		LoginFrame.add(btn_Find);
		LoginFrame.add(btn_Create);

		LoginFrame.add(label_line);
		LoginFrame.add(label_id);
		LoginFrame.add(label_pw);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btn_Create) {
			if (Frame_NewAccount.opencheck_newaccount == 0) {
				new Frame_NewAccount();
			} else {
				return;
			}
		} else if (obj == btn_Login) {
			email = txt_id_input.getText();
			if (Oracle.OracleLogin(txt_id_input.getText(), txt_pw_input.getText()) == 0) {
				new Client_Main("183.91.253.86", email);
				if (Oracle.OracleLoginCheck(email) == 0) {
					log.info("Login Success");
					if ((Oracle.OracleGetData("userinfo", email, "userjob")).equals("사장")) {
						new Frame_EmployerMain(txt_id_input.getText());
						dispose();
					} else {
						if (Oracle.OracleGetData("userinfo", email, "useremployercheck").equals("N")) {
							new Frame_RegistEmployer(email);
							dispose();
						} else if (Oracle.OracleGetData("userinfo", email, "useremployercheck").equals("Y")) {
							new Frame_EmployeeMain(txt_id_input.getText());
							dispose();
						} else {
							new Frame_MessageBox("메시지", "현재 상대방이 요청을 수락하지 않았습니다.");
							if(Oracle.OracleLogOut(txt_id_input.getText()) == 0) {
								return;
							}
						}
					}
				} else if (Oracle.OracleLoginCheck(email) == 1) {
					new Frame_MessageBox("오류발생", "이미 접속중인 아이디입니다!");
					log.error("Login Failed : Your ID is currently Login.");
				}
			} else if (Oracle.OracleLogin(email, txt_pw_input.getText()) == -1) {
				new Frame_MessageBox("오류발생", "존재하지 않는 아이디입니다.");
				log.info("Login Failed : ID does not exist");
			} else if (Oracle.OracleLogin(email, txt_pw_input.getText()) == 1) {
				new Frame_MessageBox("오류발생", "비밀번호가 일치하지 않습니다.");
				log.info("Login Failed : Password is not correct");
			} 
		} else if (obj == btn_Find) {
			if (Frame_IDFind.opencheck_idfind == 0 && Frame_PWFind.opencheck_pwfind == 0) {
				new Frame_IDFind(getX(), getY(), "");
			} else {
				return;
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();

		if (obj == txt_id_input) {
			if (txt_id_input.getText().equals("아이디")) {
				txt_id_input.setText("");
			}
		} else if (obj == txt_pw_input) {
			if (txt_pw_input.getText().equals("****")) {
				txt_pw_input.setText("");
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if (obj == txt_id_input) {
			if (txt_id_input.getText().length() == 0) {
				txt_id_input.setText("아이디");
			}
		} else if (obj == txt_pw_input) {
			if (txt_pw_input.getText().length() == 0) {
				txt_pw_input.setText("****");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			email = txt_id_input.getText();
			if (Oracle.OracleLogin(txt_id_input.getText(), txt_pw_input.getText()) == 0) {
				if (Oracle.OracleLoginCheck(email) == 0) {
					new Client_Main("183.91.253.86", email);
					log.info("Login Success");
					if ((Oracle.OracleGetData("userinfo", email, "userjob")).equals("사장")) {
						new Frame_EmployerMain(txt_id_input.getText());
						dispose();
					} else {
						if (Oracle.OracleGetData("userinfo", email, "useremployercheck").equals("N")) {
							new Frame_RegistEmployer(email);
							dispose();
						} else if (Oracle.OracleGetData("userinfo", email, "useremployercheck").equals("Y")) {
							new Frame_EmployeeMain(txt_id_input.getText());
							dispose();
						} else {
							Oracle.OracleLogOut( txt_id_input.getText());
							new Frame_MessageBox("메시지", "현재 상대방이 요청을 수락하지 않았습니다.");
						}
					}
				} else if (Oracle.OracleLogOut(email) == 0) {
					new Frame_MessageBox("오류발생", "이미 접속중인 아이디입니다!");
					log.error("Login Failed : Your ID is currently Login.");
				}
			} else if (Oracle.OracleLogin(email, txt_pw_input.getText()) == -1) {
				new Frame_MessageBox("오류발생", "존재하지 않는 아이디입니다.");
				log.error("Login Failed : ID does not exist");
			} else if (Oracle.OracleLogin(email, txt_pw_input.getText()) == 1) {
				new Frame_MessageBox("오류발생", "비밀번호가 일치하지 않습니다.");
				log.error("Login Failed : Password is not correct");
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// 미사용

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// 미사용

	}
}