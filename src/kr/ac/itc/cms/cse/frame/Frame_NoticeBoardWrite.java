package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_NoticeBoardWrite extends JFrame implements ActionListener, WindowListener{
	private Container Frame_NoticeBoardWrite = getContentPane();
	private JLabel label_main, label_title, label_content;
	public static JTextArea txt_title_input;
	public static  JTextArea txt_content_input;
	private JButton btn_write, btn_cancel;
	public static String year, month, date, now;
	public static int opencheck_noticeboardwrite = 0;

	public Frame_NoticeBoardWrite() {
		setTitle("게시물 작성");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800, 100);
		setSize(379, 423);
		setLayout(null);
		setResizable(false);
		addWindowListener(this);
		
		setNoticeBoardWriteFrame();
		
		opencheck_noticeboardwrite = 1;
		
		setVisible(true);
	}

	private void setNoticeBoardWriteFrame() {
		Frame_NoticeBoardWrite.setBackground(Color.WHITE);
		
		label_main = new JLabel("게시물 작성");
		label_main.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		label_main.setForeground(Color.WHITE);
		label_main.setHorizontalAlignment(SwingConstants.CENTER);
		label_main.setBounds(0, 0, 373, 41);
		label_main.setOpaque(true);
		label_main.setBackground(new Color(32, 33, 42));
		Frame_NoticeBoardWrite.add(label_main);
		
		label_title = new JLabel("제목");
		label_title.setBounds(22, 69, 37, 15);
		Frame_NoticeBoardWrite.add(label_title);
		
		txt_title_input = new JTextArea();
		txt_title_input.setBorder(new LineBorder(new Color(37,47,66)));
		txt_title_input.setBounds(64, 63, 297, 27);
		txt_title_input.setLineWrap(false);
		Frame_NoticeBoardWrite.add(txt_title_input);
		
		label_content = new JLabel("내용");
		label_content.setBounds(22, 119, 37, 15);
		Frame_NoticeBoardWrite.add(label_content);
		
		txt_content_input = new JTextArea();
		txt_content_input.setBorder(new LineBorder(new Color(37,47,66)));
		txt_content_input.setLineWrap(true);
		
		JScrollPane sc = new JScrollPane(txt_content_input);
		sc.setBounds(64, 116, 297, 214);
		Frame_NoticeBoardWrite.add(sc);
		
		btn_write = new JButton("완료");
		btn_write.setBounds(189, 346, 80, 38);
		btn_write.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_write.setBackground(new Color(32, 33, 42));
		btn_write.setForeground(Color.WHITE);
		btn_write.setFocusable(false);
		Frame_NoticeBoardWrite.add(btn_write);
		btn_write.addActionListener(this);
		
		btn_cancel = new JButton("취소");
		btn_cancel.setBounds(281, 346, 80, 38);
		btn_cancel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_cancel.setBackground(new Color(32, 33, 42));
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setFocusable(false);
		Frame_NoticeBoardWrite.add(btn_cancel);
		btn_cancel.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		Oracle_DAO Oracle = new Oracle_DAO();
		Calendar cal = Calendar.getInstance();
		year = String.valueOf(cal.get(cal.YEAR));
		month = String.valueOf(cal.get(cal.MONTH)+1);
		date = String.valueOf(cal.get(cal.DATE));
		now = year+"/"+month+"/"+date;
		if(command == btn_write) {
			Oracle.OracleSetRowBoard(Frame_Login.email, Oracle.OracleGetData("userinfo", Frame_Login.email, "username"), txt_title_input.getText(), now, txt_content_input.getText());
			Frame_NoticeBoard.addNoticeBoard();
			dispose();
			opencheck_noticeboardwrite = 0;
		}
		if(command == btn_cancel) {
			int result = JOptionPane.showConfirmDialog(null, "정말 나가시겠습니까?", "Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == 0) {
				dispose();
				opencheck_noticeboardwrite = 0;
			}
		}
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		opencheck_noticeboardwrite = 0;
		
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