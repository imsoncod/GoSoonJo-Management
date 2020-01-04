package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_Chat;
import kr.ac.itc.cms.cse.oracle.Oracle_ChatDTO;

@SuppressWarnings("serial")
public class Frame_UserInfo extends JFrame implements ActionListener {

	private Container UserFind = getContentPane();
	private JButton btn_cancel;
	private JButton btn_useradd;
	private JPanel UserPanel;

	private String id = null;
	private String phone = null;

	private Oracle_Chat oracle_chat = new Oracle_Chat();
	private Frame_UserList frame_UserList = new Frame_UserList();

	public Frame_UserInfo(int x, int y, String id, String find) {
		this.id = id;
		setTitle("À¯Àú °Ë»ö");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(320, 200);
		setLayout(null);
		setResizable(false);   
		setLocation(x + 50, y + 50);
		oracle_chat.OracleUserInfo(find);

		setUserFind();

		setVisible(true);
	}
	private void setUserFind() {
		UserFind.setBackground(Color.WHITE);

		setUserPanel();

		btn_useradd = new JButton("Ä£±¸Ãß°¡");
		btn_useradd.setBounds(60, 112, 86, 35);
		btn_useradd.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 13));
		btn_useradd.setBackground(new Color(32, 33, 42));
		btn_useradd.setForeground(Color.WHITE);
		btn_useradd.setFocusPainted(false);
		btn_useradd.addActionListener(this);

		btn_cancel = new JButton("Ãë¼Ò");
		btn_cancel.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 13));
		btn_cancel.setBackground(new Color(32, 33, 42));
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setFocusPainted(false);
		btn_cancel.setBounds(190, 112, 60, 35);
		btn_cancel.addActionListener(this);

		UserFind.add(btn_useradd);
		UserFind.add(btn_cancel);
		UserFind.add(UserPanel);
	}

	private void setUserPanel() {
		phone = Oracle_ChatDTO.db_phone.substring(0, 3) + "-" + Oracle_ChatDTO.db_phone.substring(3, 7) + "-"
				+ Oracle_ChatDTO.db_phone.substring(7, 11);

		UserPanel = new JPanel();
		UserPanel.setBackground(Color.WHITE);
		UserPanel.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		UserPanel.setLayout(null);
		UserPanel.setBounds(10, 10, 285, 90);

		Image unkown_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/À¯Àú¸ñ·Ï.png"));
		JButton user_img = new JButton(new ImageIcon(unkown_img));
		user_img.setBorder(new LineBorder(new Color(32, 33, 42),1));
		user_img.setContentAreaFilled(false);
		user_img.setFocusPainted(false);
		user_img.setBounds(13, 10, 70, 70);

		JLabel label_name = new JLabel(Oracle_ChatDTO.db_name);
		label_name.setBounds(90, 5, 60, 30);
		label_name.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		label_name.setForeground(Color.BLACK);

		JLabel label_mail = new JLabel(Oracle_ChatDTO.db_id);
		label_mail.setBounds(90, 27, 190, 30);
		label_mail.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));

		JLabel label_phone = new JLabel(phone);

		label_phone.setBounds(90, 52, 150, 30);
		label_phone.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));

		UserPanel.add(user_img);
		UserPanel.add(label_mail);
		UserPanel.add(label_phone);
		UserPanel.add(label_name);

	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btn_cancel) {
			dispose();
		}
		if (obj == btn_useradd) {
			if (oracle_chat.OracleChatListAdd(id, Oracle_ChatDTO.db_id) == 0) {
				frame_UserList.setTableAdd(Oracle_ChatDTO.db_name, Oracle_ChatDTO.db_gender,
						Oracle_ChatDTO.db_phone.substring(0, 3) + "-" + Oracle_ChatDTO.db_phone.substring(3, 7) + "-"
								+ Oracle_ChatDTO.db_phone.substring(7, 11));
				dispose();
			} else {
				dispose();
			}
		}

	}

}
