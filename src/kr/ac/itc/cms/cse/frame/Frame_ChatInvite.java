package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.ac.itc.cms.cse.oracle.Oracle_Chat;

public class Frame_ChatInvite extends JFrame implements MouseListener{
	
	private JTable table_userlist;
	private static DefaultTableModel tablemodel;
	private DefaultTableCellRenderer tablesort;
	private int port = -1;
	private String id = null;
	
	private String attribute[] = { "¿Ã∏ß", "º∫∫∞", "¿¸»≠π¯»£" };
	
	private Container ChatInvite = getContentPane();

	private Oracle_Chat oracle_chat = new Oracle_Chat();
	
	public Frame_ChatInvite(int x, int y, String id, int port) {
		this.port = port;
		this.id = id;
		setTitle("√ ¥Î«œ±‚");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);   
		setSize(300, 500);
		setLocation(x+50, y);
		setUserTable();
		oracle_chat.OracleUserList(id, 2);
		
		setVisible(true);
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
		table_userlist.getTableHeader().setBackground(new Color(39,47,66));
		table_userlist.getTableHeader().setForeground(Color.WHITE);
		table_userlist.getTableHeader().setBorder(new LineBorder(Color.DARK_GRAY, 1));
		table_userlist.getTableHeader().setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.BOLD, 13));
		table_userlist.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 12));
		
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
		ChatInvite.add(scroll);
		
	}
	
	public static void setTableAdd(String name, String gender, String phone) {
		Object [] Tablerow = {name, gender, phone};
		tablemodel.addRow(Tablerow);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2) {
			int row = table_userlist.getSelectedRow();
			
			Object value = table_userlist.getValueAt(row, 2);
			String [] data = String.valueOf(value).split("-");
			String phone = data[0] + data[1] + data[2] + "";
			oracle_chat.OracleChatInvite(phone, port);
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
