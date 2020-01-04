package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.client.Client_Main;

public class Frame_ChatServerCreate extends JFrame implements ActionListener{

	public static JTextField txt_chatname;
	private String id = null;
	private Container ChatServerCreate = getContentPane();
	private JButton btn_create;
	private JButton btn_cancel;
	
	public Frame_ChatServerCreate(int x, int y, String id) {
		this.id = id;
		setTitle("Ã¤ÆÃ¹æ »ý¼ºÇÏ±â");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);   
		setSize(300, 150);
		setLayout(null);
		setLocation(x+50, y+50);
		setChatServerCreate();
		
		setVisible(true);
	}
	
	
	private void setChatServerCreate() {
		
		ChatServerCreate.setBackground(Color.WHITE);
		
		JLabel label_name = new JLabel("Ã¤ÆÃ¹æ ÀÌ¸§ :");
		label_name.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		label_name.setBounds(24, 18, 100, 30);
		
		txt_chatname = new JTextField();
		txt_chatname.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		txt_chatname.setBorder(new LineBorder(Color.BLACK, 1));
		txt_chatname.setBounds(120, 20, 150, 30);
		
		btn_create = new JButton("»ý¼ºÇÏ±â");
		btn_create.setBounds(40, 70, 110, 30);
		btn_create.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		btn_create.setBackground(new Color(32, 33, 42));
		btn_create.setForeground(Color.WHITE);
		btn_create.setFocusPainted(false);
		btn_create.addActionListener(this);
		
		btn_cancel = new JButton("Ãë¼Ò");
		btn_cancel.setBounds(180, 70, 70, 30);
		btn_cancel.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		btn_cancel.setBackground(new Color(32, 33, 42));
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setFocusPainted(false);
		btn_cancel.addActionListener(this);

		ChatServerCreate.add(label_name);
		ChatServerCreate.add(txt_chatname);
		ChatServerCreate.add(btn_create);
		ChatServerCreate.add(btn_cancel);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btn_create) {
			Client_Main c = new Client_Main("183.91.253.86", id);
			c.Sender(id);
			dispose();
		} else {
			dispose();
		}
	}

}
