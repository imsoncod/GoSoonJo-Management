package kr.ac.itc.cms.cse.frame;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

import javax.swing.ListSelectionModel;

public class Frame_Waiting extends JFrame implements ActionListener, WindowListener {

	private Container Frame_Waiting = getContentPane();
	JLabel label_info_frame;
	public static DefaultTableModel tablemodel;
	private DefaultTableCellRenderer tablesort;
	public static JTable table_waitingList;
	private String attribute[] = {"이름","성별","생년월일","전화번호","eMail"};
	private String tuple[][];
	private JButton btn_accept, btn_exit, btn_refuse;
	public static int opencheck_waiting = 0;
	Oracle_DAO Oracle = new Oracle_DAO();

	public Frame_Waiting() {
		setTitle("대기중인 요청");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800, 100);
		setSize(400, 300);
		setResizable(false);
		setLayout(null);
		addWindowListener(this);
		
		setWaitingFrame();
		setWaitingListTable();
		
		opencheck_waiting = 1;

		setVisible(true);
	}

	private void setWaitingListTable() {
		
		tablemodel = new DefaultTableModel(tuple, attribute) {
			public boolean isCellEditable(int tuple, int attribute) {
				return false;
			}
		};
		table_waitingList = new JTable(tablemodel);
		table_waitingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_waitingList.setRowHeight(30);
		table_waitingList.getTableHeader().setReorderingAllowed(false);
		table_waitingList.getTableHeader().setResizingAllowed(false);
		table_waitingList.getTableHeader().setBackground(new Color(36,38,42));
		table_waitingList.getTableHeader().setForeground(Color.WHITE);
		table_waitingList.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
		table_waitingList.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

		// 각 열의 넓이 지정(비율적 조정)
		table_waitingList.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_waitingList.getColumnModel().getColumn(1).setPreferredWidth(40);
		table_waitingList.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_waitingList.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_waitingList.getColumnModel().getColumn(4).setPreferredWidth(120);

		// 가운데 정렬
		tablesort = new DefaultTableCellRenderer();
		tablesort.setHorizontalAlignment(tablesort.CENTER);

		table_waitingList.getColumnModel().getColumn(0).setCellRenderer(tablesort);
		table_waitingList.getColumnModel().getColumn(1).setCellRenderer(tablesort);
		table_waitingList.getColumnModel().getColumn(2).setCellRenderer(tablesort);
		table_waitingList.getColumnModel().getColumn(3).setCellRenderer(tablesort);
		table_waitingList.getColumnModel().getColumn(4).setCellRenderer(tablesort);

		Frame_Waiting.setBackground(Color.WHITE);
		
		JScrollPane sc = new JScrollPane(table_waitingList);
		sc.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		sc.getViewport().setBackground(Color.WHITE);
		sc.setBounds(0, 48, 394, 147);
		
		setTable();
		
		Frame_Waiting.add(sc);
	}

	private void setWaitingFrame() {
		label_info_frame = new JLabel("대기중인 요청 목록");
		label_info_frame.setForeground(Color.BLACK);
		label_info_frame.setHorizontalAlignment(SwingConstants.CENTER);
		label_info_frame.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_info_frame.setBounds(0, 0, 394, 48);
		label_info_frame.setBackground(Color.WHITE);
		label_info_frame.setOpaque(true);
		Frame_Waiting.add(label_info_frame);

		btn_accept = new JButton("수락");
		btn_accept.setBorder(new LineBorder(new Color(32, 33, 42),1));
		btn_accept.setFocusPainted(false);
		btn_accept.setBackground(new Color(32, 33, 42));
		btn_accept.setForeground(Color.WHITE);
		btn_accept.setBounds(12, 218, 97, 36);
		Frame_Waiting.add(btn_accept);
		btn_accept.addActionListener(this);

		btn_refuse = new JButton("거절");
		btn_refuse.setBorder(new LineBorder(new Color(32, 33, 42),1));
		btn_refuse.setFocusPainted(false);
		btn_refuse.setBackground(new Color(32, 33, 42));
		btn_refuse.setForeground(Color.WHITE);
		btn_refuse.setBounds(149, 218, 97, 36);
		Frame_Waiting.add(btn_refuse);
		btn_refuse.addActionListener(this);

		btn_exit = new JButton("나가기");
		btn_exit.setBorder(new LineBorder(new Color(32, 33, 42),1));
		btn_exit.setFocusPainted(false);
		btn_exit.setBackground(new Color(32, 33, 42));
		btn_exit.setForeground(Color.WHITE);
		btn_exit.setBounds(285, 218, 97, 36);
		Frame_Waiting.add(btn_exit);
		btn_exit.addActionListener(this);

	}
	
	public static void addWaiting() {
		Frame_Management.tablemodel.addRow(new Object[] {
				table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 0),
				table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 1),
				table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 2),
				table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 3),
				table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 4)
		});
	}
	
	public static void removeWaiting() {
		tablemodel.removeRow(table_waitingList.getSelectedRow());
	}
	
	private void setTable() {
		Oracle_DAO Oracle = new Oracle_DAO();
		Oracle.OracleGetRow1(Frame_Login.email);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		if (command == btn_accept) {
			try {
				Oracle.OracleSetData("userinfo",table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 4).toString(), "useremployercheck", "Y");
				Oracle.OracleSetData("userinfo",table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 4).toString(), "useremployerid", Frame_Login.email);
				int data_waiting = Integer.parseInt(Oracle.OracleGetData("userinfo",Frame_Login.email, "userwaiting"));
				Oracle.OracleSetData("userinfo",Frame_Login.email, "userwaiting", String.valueOf(--data_waiting));
				addWaiting();
				removeWaiting();
				--Frame_Management.waiting_count;
				Frame_Management.btn_waiting.setText("요청대기중( "+ Frame_Management.waiting_count +" )");
			} catch (ArrayIndexOutOfBoundsException e2) {
				new Frame_MessageBox("메시지", "항목이 선택되지 않았습니다.");
			}
		}
		if (command == btn_refuse) {
			try {
				Oracle.OracleSetData("userinfo",table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 4).toString(), "useremployercheck", "N");
				Oracle.OracleSetData("userinfo",table_waitingList.getValueAt(table_waitingList.getSelectedRow(), 4).toString(), "useremployerid", "");
				int data_waiting = Integer.parseInt(Oracle.OracleGetData("userinfo",Frame_Login.email, "userwaiting"));
				Oracle.OracleSetData("userinfo",Frame_Login.email, "userwaiting", String.valueOf(--data_waiting));
				removeWaiting();
				--Frame_Management.waiting_count;
				Frame_Management.btn_waiting.setText("요청대기중( "+ Frame_Management.waiting_count +" )");
			} catch (ArrayIndexOutOfBoundsException e2) {
				new Frame_MessageBox("메시지", "항목이 선택되지 않았습니다.");
			}
		}
		if (command == btn_exit) {
			dispose();
			opencheck_waiting = 0;
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
		opencheck_waiting = 0;
		
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
