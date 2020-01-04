package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.ac.itc.cms.cse.oracle.Oracle_Chat;
import kr.ac.itc.cms.cse.oracle.Oracle_ChatDTO;

public class Frame_ChatList extends JFrame implements ActionListener, MouseListener {

	Container MainChat = getContentPane();
	private JButton btn_userlist;
	private JButton btn_chatlist;
	private JButton btn_setchat;
	private JPanel pan_left;
	private String id = null;
	private String name = null;
	private JTable table_chatlist;
	private static DefaultTableModel tablemodel;
	private DefaultTableCellRenderer tablesort;

	private String attribute[] = { "Ã¤ÆÃ¹æ" };

	private Oracle_Chat oracle_chat = new Oracle_Chat();

	public Frame_ChatList() {}
	
	public Frame_ChatList(int x, int y, String id, String name) {
		// setUndecorated(true);
		this.name = name;
		this.id = id;
		setTitle("°í¼øÁ¶Åå");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(410, 550);
		setResizable(false);   
		setLayout(null);
		setLocation(x, y);

		setChatList();

		setVisible(true);
	}

	private void setChatList() {

		setLeftPan();
		setChatRoomList();
		
		oracle_chat.OracleChatList(id);

		MainChat.setBackground(Color.WHITE);

		JLabel label_chatlist = new JLabel("Ã¤ÆÃ ¸ñ·Ï");
		label_chatlist.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 19));
		label_chatlist.setForeground(Color.BLACK);
		label_chatlist.setBounds(90, 5, 90, 50);

		MainChat.add(pan_left);
		MainChat.add(label_chatlist);

	}

	private void setChatRoomList() {

		tablemodel = new DefaultTableModel(null, attribute) {
			public boolean isCellEditable(int tuple, int attribute) {
				return false;
			}
		};

		table_chatlist = new JTable(tablemodel);
		table_chatlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_chatlist.setRowHeight(40);
		table_chatlist.getTableHeader().setReorderingAllowed(false);
		table_chatlist.getTableHeader().setResizingAllowed(false);
		table_chatlist.getTableHeader().setBackground(new Color(32, 33, 42));
		table_chatlist.getTableHeader().setForeground(Color.WHITE);
		table_chatlist.getTableHeader().setBorder(new LineBorder(Color.DARK_GRAY, 1));
		table_chatlist.getTableHeader().setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 13));
		table_chatlist.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));

		tablesort = new DefaultTableCellRenderer();
		tablesort.setHorizontalAlignment(tablesort.CENTER);

		table_chatlist.getColumnModel().getColumn(0).setCellRenderer(tablesort);

		table_chatlist.addMouseListener(this);

		JScrollPane scroll = new JScrollPane(table_chatlist);
		scroll.setBounds(76, 50, 319, 462);
		scroll.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		scroll.getViewport().setBackground(Color.WHITE);

		MainChat.add(scroll);

	}

	public static void setTableAdd(String chatname) {
		Object[] Tablerow = { chatname};
		tablemodel.addRow(Tablerow);
		tablemodel.fireTableDataChanged();
	}
	
	public void setTableDel(int index) {
		tablemodel.removeRow(index);
		tablemodel.fireTableDataChanged();
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

		if (obj == btn_userlist) {
			new Frame_UserList(getX(), getY(), id);
			dispose();
		} else if (obj == btn_setchat) {
			new Frame_SetChat(getX(), getY(), id);
			dispose();
		}
	}

	public static int opencheck[] = new int[100];
	public static int checkrow = 0;

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table_chatlist.getSelectedRow();
		checkrow = row;
		Object chatname = table_chatlist.getValueAt(row, 0);
		if (opencheck[checkrow] == 0) {
			oracle_chat.OracleUserInfo(id);
			oracle_chat.OracleChatOpen(getX(), getY(), id, (String) chatname, table_chatlist.getSelectedRow());
		} else {
			return;
		}
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

}
