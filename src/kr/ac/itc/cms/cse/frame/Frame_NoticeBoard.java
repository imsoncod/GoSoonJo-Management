package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_NoticeBoard extends JFrame implements ActionListener, WindowListener{
	private Container Frame_NoticeBoard = getContentPane();
	private JLabel label_main;
	private JButton btn_write, btn_read, btn_delete, btn_exit;
	private String attribute[] = {"작성자","제목","작성시간"};
	private String tuple[][];
	public static DefaultTableModel tablemodel;
	private DefaultTableCellRenderer tablesort;
	public static JTable table_noticelist;
	public static int opencheck_noticeboard = 0;

	public Frame_NoticeBoard() {
		setTitle("게시판");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800, 100);
		setSize(455, 410);
		setLayout(null);
		setResizable(false);
		addWindowListener(this);
		
		setNoticeBoardFrame();
		setNoticeBoardTable();
		
		opencheck_noticeboard = 1;
		
		setVisible(true);
	}

	private void setNoticeBoardTable() {
		tablemodel = new DefaultTableModel(tuple, attribute) {
			public boolean isCellEditable(int tuple, int attribute) {
				return false;
			}
		};
		table_noticelist = new JTable(tablemodel);
		table_noticelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_noticelist.setRowHeight(40);
		table_noticelist.getTableHeader().setBackground(new Color(32, 33, 42));
		table_noticelist.getTableHeader().setReorderingAllowed(false);
		table_noticelist.getTableHeader().setResizingAllowed(false);
		table_noticelist.getTableHeader().setForeground(Color.WHITE);
		table_noticelist.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
		table_noticelist.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

		// 각 열의 넓이 지정(비율적 조정)
		table_noticelist.getColumnModel().getColumn(0).setPreferredWidth(10);
		table_noticelist.getColumnModel().getColumn(1).setPreferredWidth(250);
		table_noticelist.getColumnModel().getColumn(2).setPreferredWidth(30);

		// 가운데 정렬
		tablesort = new DefaultTableCellRenderer();
		tablesort.setHorizontalAlignment(tablesort.CENTER);

		table_noticelist.getColumnModel().getColumn(0).setCellRenderer(tablesort);
		table_noticelist.getColumnModel().getColumn(1).setCellRenderer(tablesort);
		table_noticelist.getColumnModel().getColumn(2).setCellRenderer(tablesort);

		JScrollPane sc = new JScrollPane(table_noticelist);
		sc.setBounds(10, 48, 430, 271);
		sc.setBorder(new LineBorder(new Color(37,47,66)));
		sc.getViewport().setBackground(Color.WHITE);
		
		setTable();
		
		Frame_NoticeBoard.add(sc);
		
	}
	
	public static void addNoticeBoard() {
		Oracle_DAO Oracle = new Oracle_DAO();
		tablemodel.addRow(new Object[] {
				Oracle.OracleGetData("userinfo", Frame_Login.email, "username"),
				Frame_NoticeBoardWrite.txt_title_input.getText(),
				Frame_NoticeBoardWrite.now
		});
	}
	
	public static void removeNoticeBoard() {
		tablemodel.removeRow(table_noticelist.getSelectedRow());
	}

	private void setTable() {
		Oracle_DAO Oracle = new Oracle_DAO();
		if(Oracle.OracleGetData("userinfo", Frame_Login.email, "userjob").equals("사장")==true) {
			Oracle.OracleGetRowBoard(Frame_Login.email);
		}else {
			Oracle.OracleGetRowBoard(Oracle.OracleGetData("userinfo", Frame_Login.email, "useremployerid"));
		}
	}

	private void setNoticeBoardFrame() {
		Frame_NoticeBoard.setBackground(Color.WHITE);
		
		label_main = new JLabel("공지사항");
		label_main.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		label_main.setForeground(Color.WHITE);
		label_main.setHorizontalAlignment(SwingConstants.CENTER);
		label_main.setBounds(0, 0, 449, 41);
		label_main.setOpaque(true);
		label_main.setBackground(new Color(32, 33, 42));
		Frame_NoticeBoard.add(label_main);
		
		btn_write = new JButton("게시물 작성");
		btn_write.setBounds(12, 336, 123, 32);
		btn_write.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_write.setBackground(new Color(32, 33, 42));
		btn_write.setForeground(Color.WHITE);
		btn_write.setFocusable(false);
		Frame_NoticeBoard.add(btn_write);
		btn_write.addActionListener(this);
		
		btn_read = new JButton("읽기");
		btn_read.setBounds(147, 336, 86, 32);
		btn_read.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_read.setBackground(new Color(32, 33, 42));
		btn_read.setForeground(Color.WHITE);
		btn_read.setFocusable(false);
		Frame_NoticeBoard.add(btn_read);
		btn_read.addActionListener(this);
		
		btn_delete = new JButton("삭제");
		btn_delete.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_delete.setBackground(new Color(32, 33, 42));
		btn_delete.setForeground(Color.WHITE);
		btn_delete.setFocusable(false);
		btn_delete.setBounds(245, 336, 86, 32);
		Frame_NoticeBoard.add(btn_delete);
		btn_delete.addActionListener(this);
		
		btn_exit = new JButton("나가기");
		btn_exit.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_exit.setBackground(new Color(32, 33, 42));
		btn_exit.setForeground(Color.WHITE);
		btn_exit.setFocusable(false);
		btn_exit.setBounds(343, 336, 97, 32);
		Frame_NoticeBoard.add(btn_exit);
		btn_exit.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		Oracle_DAO Oracle = new Oracle_DAO();
		if(command == btn_write) {
			if(Oracle.OracleGetData("userinfo", Frame_Login.email, "userjob").equals("사장")==true) {
				if(Frame_NoticeBoardWrite.opencheck_noticeboardwrite == 0) {
					new Frame_NoticeBoardWrite();
				}else {
					return;
				}
			}else {
				new Frame_MessageBox("메시지", "게시물은 '사장'만 작성할 수 있습니다.");
			}
		}
		if(command == btn_read) {
			try {
				new Frame_NoticeBoardRead();
			} catch (ArrayIndexOutOfBoundsException e2) {
				new Frame_MessageBox("메시지", "항목이 선택되지 않았습니다.");
			}
		}
		if(command == btn_delete) {
			try {
				if(Oracle.OracleGetData("userinfo", Frame_Login.email, "userjob").equals("사장")==true) {
					int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result == 0) {
						Oracle.OracleDeletePost("noticeboard", Frame_Login.email, table_noticelist.getValueAt(table_noticelist.getSelectedRow(), 1).toString());
						removeNoticeBoard();
						new Frame_MessageBox("메시지", "게시물이 삭제되었습니다.");
						}
				}else {
					new Frame_MessageBox("메시지", "     권한이 필요합니다.     ");
				}
			} catch (ArrayIndexOutOfBoundsException e3) {
				new Frame_MessageBox("메시지", "항목이 선택되지 않았습니다.");
			}
		}
		if(command == btn_exit) {
			dispose();
			opencheck_noticeboard = 0;
		}
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		opencheck_noticeboard = 0;
		
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