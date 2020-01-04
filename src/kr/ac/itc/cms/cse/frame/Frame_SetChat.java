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

public class Frame_SetChat extends JFrame implements ActionListener{
	
	Container MainChat = getContentPane();
	private JButton btn_userlist;
	private JButton btn_chatlist;
	private JButton btn_setchat;
	private JPanel pan_left;
	private JLabel label_name;
	private JLabel label_email;
	private JButton btn_chatcreate;
	
	private String id = null;
	
	private Oracle_Chat oracle_chat = new Oracle_Chat();
	
	public Frame_SetChat(int x, int y, String id) {
		this.id = id;
		//setUndecorated(true);
		setTitle("°í¼øÁ¶Åå");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(410, 550);
		setLayout(null);
		setResizable(false);   
		setLocation(x, y);
		
		setChat();
		
		setVisible(true);
	}
	
	private void setChat() {
		
		setLeftPan();
		
		oracle_chat.OracleUserInfo(id);
		
		MainChat.setBackground(Color.WHITE);
		
		JLabel label_user = new JLabel("Ã¤ÆÃ ¼³Á¤");
		label_user.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 19));
		label_user.setForeground(Color.BLACK);
		label_user.setBounds(90, 5, 90, 50);
		
		Image unkown_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/À¯Àú¸ñ·Ï.png"));
		JButton unkown = new JButton(new ImageIcon(unkown_img));
		unkown.setBorderPainted(false);
		unkown.setContentAreaFilled(false);
		unkown.setFocusPainted(false);
		unkown.setBounds(95, 63, 70, 60);

		label_name = new JLabel(Oracle_ChatDTO.db_name);
		label_name.setBounds(180, 65, 70, 30);
		label_name.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		label_name.setForeground(Color.BLACK);

		label_email = new JLabel(Oracle_ChatDTO.db_id);
		label_email.setBounds(180, 90, 160, 26);
		label_email.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_email.setForeground(Color.GRAY);
	
		btn_chatcreate = new JButton("Ã¤ÆÃ¹æ »ý¼ºÇÏ±â");
		btn_chatcreate.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		btn_chatcreate.setBackground(new Color(32, 33, 42));
		btn_chatcreate.setForeground(Color.WHITE);
		btn_chatcreate.setFocusPainted(false);
		btn_chatcreate.setBounds(95, 150, 284, 50);
		btn_chatcreate.addActionListener(this);
		
		MainChat.add(label_user);
		MainChat.add(unkown);
		MainChat.add(label_name);
		MainChat.add(label_email);
		
		MainChat.add(btn_chatcreate);
		
		MainChat.add(pan_left);
		
	}

	private void setLeftPan() {
		pan_left = new JPanel();
		pan_left.setBackground(Color.WHITE);
		pan_left.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		pan_left.setLayout(null);
		pan_left.setBounds(-3, -3, 80, 550);

		Image img_userlist = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/À¯Àú¸ñ·Ï.png"));
		btn_userlist = new JButton(new ImageIcon(img_userlist));
		btn_userlist.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_userlist.setBorderPainted(false);
		btn_userlist.setContentAreaFilled(false);
		btn_userlist.setBounds(10, 10, 60, 50);
		btn_userlist.addActionListener(this);

		Image img_chatlist = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/Ã¤ÆÃ.png"));
		btn_chatlist = new JButton(new ImageIcon(img_chatlist));
		btn_chatlist.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_chatlist.setBounds(10, 70, 60, 50);
		btn_chatlist.setBorderPainted(false);
		btn_chatlist.setContentAreaFilled(false);
		btn_chatlist.addActionListener(this);

		Image img_setchat = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/Ã¤ÆÃ¼³Á¤.png"));
		btn_setchat = new JButton(new ImageIcon(img_setchat));
		btn_setchat.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_setchat.setBounds(10, 130, 60, 50);
		btn_setchat.setBorderPainted(false);
		btn_setchat.setContentAreaFilled(false);
		btn_setchat.addActionListener(this);
		
		pan_left.add(btn_userlist);
		pan_left.add(btn_chatlist);
		pan_left.add(btn_setchat);

		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if ( obj == btn_userlist ) {
			new Frame_UserList(getX(), getY(), id);
			dispose();
		}
		else if ( obj == btn_chatlist ) {
			new Frame_ChatList(getX(), getY(), id, Oracle_ChatDTO.db_name);
			dispose();
		}
		else if( obj == btn_chatcreate ) {
			new Frame_ChatServerCreate(getX(), getY(), id);
		}
		
	}

}
