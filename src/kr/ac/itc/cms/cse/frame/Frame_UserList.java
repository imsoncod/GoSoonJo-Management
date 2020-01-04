package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.ac.itc.cms.cse.oracle.Oracle_Chat;
import kr.ac.itc.cms.cse.oracle.Oracle_ChatDTO;
import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_UserList extends JFrame implements ActionListener, FocusListener, MouseListener, WindowListener {

	Container MainChat = getContentPane();
	private JButton btn_userlist;
	private JButton btn_chatlist;
	private JButton btn_setchat;
	private JPanel pan_left;
	private JTextField txt_userfind;
	private JButton btn_userfind;
	private JTable table_userlist;
	private static DefaultTableModel tablemodel;
	private DefaultTableCellRenderer tablesort;
	private String id = null;
	public static int opencheck_userlist=0;
	
	private String attribute[] = { "이름", "성별", "전화번호" };
	
	public Frame_UserList() {}
	
	private Oracle_Chat oracle_chat = new Oracle_Chat();
	private Oracle_DAO Oracle = new Oracle_DAO();
	
	public Frame_UserList(int x, int y, String id) {
		this.id = id;
		setTitle("고순조톡");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(410, 550);
		setResizable(false);   
		setLayout(null);
		setLocation(x, y);
		addWindowListener(this);

		setUserList();
		opencheck_userlist=1;

		setVisible(true);
	}

	private void setUserList() {

		setLeftPan();
		setUserTable();
		
		oracle_chat.OracleUserInfo(id);
		oracle_chat.OracleUserList(id, 1);
		
		MainChat.setBackground(Color.WHITE);

		JLabel label_user = new JLabel("유저 목록");
		label_user.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		label_user.setForeground(Color.BLACK);
		label_user.setBounds(90, 5, 90, 50);

		txt_userfind = new JTextField("이메일검색");
		txt_userfind.setBounds(90, 55, 220, 30);
		txt_userfind.addFocusListener(this);
		txt_userfind.setBorder(new LineBorder(Color.BLACK, 1));

		btn_userfind = new JButton("검색");
		btn_userfind.setBounds(320, 55, 60, 29);
		btn_userfind.addActionListener(this);
		btn_userfind.setForeground(Color.WHITE);
		btn_userfind.setBackground(new Color(32, 33, 42));

		MainChat.add(pan_left);
		MainChat.add(txt_userfind);
		MainChat.add(btn_userfind);
		MainChat.add(label_user);

	}

	private void setUserTable() {
		
		tablemodel = new DefaultTableModel(null, attribute) {
			public boolean isCellEditable(int tuple, int attribute) {
				return false;
			}
		};
		
		table_userlist = new JTable(tablemodel);
		table_userlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_userlist.setRowHeight(40);
		table_userlist.getTableHeader().setReorderingAllowed(false);
		table_userlist.getTableHeader().setResizingAllowed(false);
		table_userlist.getTableHeader().setBackground(new Color(32, 33, 42));
		table_userlist.getTableHeader().setForeground(Color.WHITE);
		table_userlist.getTableHeader().setBorder(new LineBorder(Color.DARK_GRAY, 1));
		table_userlist.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));
		table_userlist.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		table_userlist.getColumnModel().getColumn(0).setMaxWidth(60); 
		table_userlist.getColumnModel().getColumn(1).setMaxWidth(45); 
		table_userlist.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		tablesort = new DefaultTableCellRenderer();
		tablesort.setHorizontalAlignment(tablesort.CENTER);
		
		table_userlist.getColumnModel().getColumn(0).setCellRenderer(tablesort);
		table_userlist.getColumnModel().getColumn(1).setCellRenderer(tablesort);
		table_userlist.getColumnModel().getColumn(2).setCellRenderer(tablesort);
		table_userlist.addMouseListener(this);
		table_userlist.setSelectionBackground(Color.gray);
		
		table_userlist.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(table_userlist);
		scroll.setBounds(76, 105, 319, 407);
		scroll.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		scroll.getViewport().setBackground(Color.WHITE);
		MainChat.add(scroll);
		
	}
	
	public static void setTableAdd(String name, String gender, String phone) {
		Object [] Tablerow = {name, gender, phone};
		tablemodel.addRow(Tablerow);
	}
	
	private void setLeftPan() {
		pan_left = new JPanel();
		pan_left.setBackground(Color.WHITE);
		pan_left.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		pan_left.setLayout(null);
		pan_left.setBounds(-3, -3, 80, 550);

		Image img_userlist = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/유저목록.png"));
		btn_userlist = new JButton(new ImageIcon(img_userlist));
		btn_userlist.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_userlist.setBorderPainted(false);
		btn_userlist.setContentAreaFilled(false);
		btn_userlist.setBounds(10, 10, 60, 50);
		btn_userlist.addActionListener(this);

		Image img_chatlist = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/채팅.png"));
		btn_chatlist = new JButton(new ImageIcon(img_chatlist));
		btn_chatlist.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_chatlist.setBounds(10, 70, 60, 50);
		btn_chatlist.setBorderPainted(false);
		btn_chatlist.setContentAreaFilled(false);
		btn_chatlist.addActionListener(this);

		Image img_setchat = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/채팅설정.png"));
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
		
		if ( obj == btn_setchat ) {
			new Frame_SetChat(getX(), getY(), id);
			dispose();
		}
		else if ( obj == btn_chatlist ) {
			new Frame_ChatList(getX(), getY(), id, Oracle_ChatDTO.db_name);
			dispose();
		}
		else if ( obj == btn_userfind ) {
			if(Oracle.OracleGetData("userinfo", txt_userfind.getText(), "userid") == null) {
				new Frame_MessageBox("오류발생", "  존재하지 않는 아이디입니다. ");
			} else {
				new Frame_UserInfo(getX(), getY(), id, txt_userfind.getText());
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		
		if( obj == txt_userfind ) {
			if(txt_userfind.getText().equals("이메일검색")) {
				txt_userfind.setText("");
			}
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		
		if( obj == txt_userfind ) {
			if(txt_userfind.getText().equals("")) {
				txt_userfind.setText("이메일검색");
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int row = table_userlist.getSelectedRow();
		int col = table_userlist.getSelectedColumn();
		
		Object value = table_userlist.getValueAt(row, 0);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Frame_UserList.opencheck_userlist = 0;
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
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
