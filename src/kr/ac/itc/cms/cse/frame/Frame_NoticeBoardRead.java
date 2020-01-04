package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_NoticeBoardRead extends JFrame implements ActionListener{
	private Container Frame_NoticeBoardRead = getContentPane();
	private JLabel label_main;
	private JLabel label_title;
	public static JTextArea txt_title_input;
	private JLabel label_content;
	public static  JTextArea txt_content_input;
	private JButton btn_exit;
	public Frame_NoticeBoardRead() {
		setTitle("게시물");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800, 100);
		setSize(379, 423);
		setLayout(null);
		setResizable(false);
		
		setNoticeBoardReadFrame();
		
		setVisible(true);
	}

	private void setNoticeBoardReadFrame() {
		Frame_NoticeBoardRead.setBackground(Color.WHITE);
		
		Oracle_DAO Oracle = new Oracle_DAO();
		label_main = new JLabel("게시물");
		label_main.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		label_main.setForeground(Color.WHITE);
		label_main.setHorizontalAlignment(SwingConstants.CENTER);
		label_main.setBounds(0, 0, 373, 41);
		label_main.setOpaque(true);
		label_main.setBackground(new Color(32, 33, 42));
		Frame_NoticeBoardRead.add(label_main);
		
		String title = Frame_NoticeBoard.table_noticelist.getValueAt(Frame_NoticeBoard.table_noticelist.getSelectedRow(), 1).toString();
		label_title = new JLabel("제목");
		label_title.setBounds(22, 69, 37, 15);
		Frame_NoticeBoardRead.add(label_title);
		
		txt_title_input = new JTextArea();
		txt_title_input.setBorder(new LineBorder(new Color(37,47,66)));
		txt_title_input.setText(title);
		txt_title_input.setBounds(64, 63, 297, 27);
		txt_title_input.setEditable(false);
		Frame_NoticeBoardRead.add(txt_title_input);
		
		label_content = new JLabel("내용");
		label_content.setBounds(22, 119, 37, 15);
		Frame_NoticeBoardRead.add(label_content);
		
		txt_content_input = new JTextArea();
		if(Oracle.OracleGetData("userinfo", Frame_Login.email, "userjob").equals("사장")==true) {
			txt_content_input.setText(Oracle.OracleGetNoticeBoardData("noticeboard", Frame_Login.email, title, "content"));
		}else {
			String employerid = Oracle.OracleGetData("userinfo", Frame_Login.email, "useremployerid");
			txt_content_input.setText(Oracle.OracleGetNoticeBoardData("noticeboard",employerid, title, "content"));
		}
		txt_content_input.setBorder(new LineBorder(new Color(37,47,66)));
		txt_content_input.setLineWrap(true);
		txt_content_input.setEditable(false);
		
		JScrollPane sc = new JScrollPane(txt_content_input);
		sc.setBounds(64, 116, 297, 214);
		Frame_NoticeBoardRead.add(sc);
		
		btn_exit = new JButton("나가기");
		btn_exit.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_exit.setBackground(new Color(32, 33, 42));
		btn_exit.setForeground(Color.WHITE);
		btn_exit.setFocusable(false);
		btn_exit.setBounds(281, 340, 80, 38);
		btn_exit.addActionListener(this);
		Frame_NoticeBoardRead.add(btn_exit);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		if(command == btn_exit) {
				dispose();
		}
		
	}
}