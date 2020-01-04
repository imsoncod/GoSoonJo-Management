package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Frame_MessageBox extends JFrame implements ActionListener{

	private String msg;
	private JButton btn_ok;
	private Container msgbox = getContentPane();
	private JLabel Message;
	
	public Frame_MessageBox(String title, String msg) {
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(410, 170);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.CENTER, 60, 30));
		
		Message = new JLabel(msg);
		Message.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.BOLD, 16));
		
		Image img_ok = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/»Æ¿Œ.png"));
		btn_ok = new JButton(new ImageIcon(img_ok));
		btn_ok.setBorderPainted(false);
		btn_ok.setContentAreaFilled(false);
		btn_ok.setFocusPainted(false);
		btn_ok.addActionListener(this);
		btn_ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		msgbox.setBackground(Color.WHITE);
		
		msgbox.add(Message);
		msgbox.add(btn_ok);
		
		setVisible(true);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btn_ok) {
			msgbox.remove(Message);
			msgbox.remove(btn_ok);
			dispose();
		}
	}
}
