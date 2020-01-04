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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

@SuppressWarnings("serial")
public class Frame_IDFind extends JFrame implements ActionListener, FocusListener, WindowListener {

	public static Logger log = Logger.getLogger(Frame_IDFind.class);

	private Container IDFindFrame = getContentPane();
	private JTextField txt_name_input, txt_phone_input;
	private JComboBox<String> combo_year, combo_month, combo_date;
	private String arrayYear[], arrayMonth[], arrayDate[];
	static JButton btn_find, btn_cancel, btn_pw_find, btn_id_find;
	
	public static int opencheck_idfind = 0;

	private Oracle_DAO Oracle = new Oracle_DAO();

	public Frame_IDFind() {

	}

	public Frame_IDFind(int x, int y, String name) {
		log.info("ID find frame creation completed");
		setTitle("아이디 / 비밀번호 찾기");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(x, y);
		setSize(404, 350);
		setResizable(false);
		setLayout(null);
		setName(name);
		opencheck_idfind = 1;
		addWindowListener(this);
		
		setIDFindFrame();

		setVisible(true);
	}

	private void setIDFindFrame() {

		setBirthArray();

		Image img_idfind = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/아이디찾기선택탭.png"));
		btn_id_find = new JButton(new ImageIcon(img_idfind));
		btn_id_find.setBounds(0, 11, 93, 35);
		btn_id_find.setFocusPainted(false);
		btn_id_find.setBorderPainted(false);
		btn_id_find.setContentAreaFilled(false);
		btn_id_find.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_pwfind = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/비밀번호찾기.png"));
		btn_pw_find = new JButton(new ImageIcon(img_pwfind));
		btn_pw_find.setBounds(91, 11, 116, 35);
		btn_pw_find.setFocusPainted(false);
		btn_pw_find.setBorderPainted(false);
		btn_pw_find.setContentAreaFilled(false);
		btn_pw_find.addActionListener(this);
		btn_pw_find.setCursor(new Cursor(Cursor.HAND_CURSOR));

		txt_name_input = new JTextField();
		txt_name_input.setBounds(160, 87, 91, 30);
		txt_name_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_name_input.setFont(new Font("맑은 고딕", Font.BOLD, 14));

		txt_phone_input = new JTextField(" - 없이 입력하세요.");
		txt_phone_input.setBounds(160, 207, 160, 30);
		txt_phone_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_phone_input.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		txt_phone_input.addFocusListener(this);

		combo_year = new JComboBox<String>(arrayYear);
		combo_year.setBackground(Color.WHITE);
		combo_year.setBounds(160, 148, 60, 30);
		
		JLabel label_year = new JLabel("년");
		label_year.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		label_year.setBounds(225, 148, 50, 30);

		combo_month = new JComboBox<String>(arrayMonth);
		combo_month.setBackground(Color.WHITE);
		combo_month.setBounds(250, 148, 40, 30);
		
		JLabel label_month = new JLabel("월");
		label_month.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		label_month.setBounds(295, 148, 50, 30);

		combo_date = new JComboBox<String>(arrayDate);
		combo_date.setBackground(Color.WHITE);
		combo_date.setBounds(320, 148, 40, 30);
		
		JLabel label_date = new JLabel("일");
		label_date.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		label_date.setBounds(365, 148, 50, 30);

		Image img_find = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/아이디찾기버튼.png"));
		btn_find = new JButton(new ImageIcon(img_find));
		btn_find.setBounds(90, 260, 120, 35);
		btn_find.setFocusPainted(false);
		btn_find.setBorderPainted(false);
		btn_find.setContentAreaFilled(false);
		btn_find.addActionListener(this);
		btn_find.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_cancel = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/취소버튼.png"));
		btn_cancel = new JButton(new ImageIcon(img_cancel));
		btn_cancel.setBounds(220, 260, 90, 35);
		btn_cancel.setFocusPainted(false);
		btn_cancel.setBorderPainted(false);
		btn_cancel.setContentAreaFilled(false);
		btn_cancel.addActionListener(this);
		btn_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Image img_stick = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/막대기.png"));
		JLabel label_stick = new JLabel(new ImageIcon(img_stick));
		label_stick.setBounds(-7, 42, 400, 13);

		JLabel label_name = new JLabel("이름 :");
		label_name.setBounds(101, 52, 60, 100);
		label_name.setForeground(new Color(31, 31, 31));
		label_name.setFont(new Font("맑은 고딕", Font.BOLD, 16));

		JLabel label_birth = new JLabel("생년월일 :");
		label_birth.setBounds(75, 112, 70, 100);
		label_birth.setForeground(new Color(31, 31, 31));
		label_birth.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		JLabel label_phone = new JLabel("휴대전화 :");
		label_phone.setBounds(75, 172, 70, 100);
		label_phone.setForeground(new Color(31, 31, 31));
		label_phone.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		IDFindFrame.setBackground(Color.WHITE);

		IDFindFrame.add(btn_id_find);
		IDFindFrame.add(btn_pw_find);

		IDFindFrame.add(txt_name_input);
		IDFindFrame.add(txt_phone_input);
		IDFindFrame.add(combo_year);
		IDFindFrame.add(combo_month);
		IDFindFrame.add(combo_date);

		IDFindFrame.add(label_stick);

		IDFindFrame.add(label_name);
		IDFindFrame.add(label_birth);
		IDFindFrame.add(label_phone);
		IDFindFrame.add(label_year);
		IDFindFrame.add(label_month);
		IDFindFrame.add(label_date);

		IDFindFrame.add(btn_find);
		IDFindFrame.add(btn_cancel);
	}

	private void setBirthArray() {

		arrayYear = new String[120];
		arrayMonth = new String[12];
		arrayDate = new String[31];

		for (int i = 0; i < 120; i++) {
			arrayYear[i] = "" + (2019 - i);
		}
		for (int i = 0; i < 12; i++) {
			arrayMonth[i] = "" + (1 + i);
		}
		for (int i = 0; i < 31; i++) {
			arrayDate[i] = "" + (1 + i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btn_pw_find) {
			dispose();
			new Frame_PWFind(getX(), getY());
		} else if (obj == btn_cancel) {
			dispose();
		} else if (obj == btn_find) {
			String year = arrayYear[combo_year.getSelectedIndex()];
			String month = arrayMonth[combo_month.getSelectedIndex()];
			String date = arrayDate[combo_date.getSelectedIndex()];

			if (Oracle.OracleFindID(txt_name_input.getText(),txt_phone_input.getText(), year, month, date) == null) {
				new Frame_MessageBox("오류발생", "일치하는 아이디가 없습니다.");
			} else{
				new Frame_MessageBox("메세지", "ID : " + Oracle.OracleFindID(txt_name_input.getText(),txt_phone_input.getText(), year, month, date));
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object focus = e.getSource();
		if (focus == txt_phone_input) {
			if (txt_phone_input.getText().equals(" - 없이 입력하세요.")) {
				txt_phone_input.setText(null);
			}
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		Object focus = e.getSource();
		if (focus == txt_phone_input) {
			if (txt_phone_input.getText().length() == 0) {
				txt_phone_input.setText(" - 없이 입력하세요.");
			}

		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		opencheck_idfind = 0;
		
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