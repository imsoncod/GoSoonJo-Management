package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_Management extends JFrame implements ActionListener,MouseListener, WindowListener {

	public Container Frame_management = getContentPane();
	private JLabel label_info_frame, label_timepay, label_monthpay, label_time, label_workinfo;
	private JPanel panel_pay_info, panel_calender;
	public static JButton btn_waiting, btn_exit, btn_fire, btn_setemployee;
	public static DefaultTableModel tablemodel;
	private DefaultTableCellRenderer tablesort;
	public static JTable table_employeeList;
	private String attribute[] = {"이름","성별","생년월일","전화번호","eMail"};
	private String tuple[][];
	public static JTextField txt_timepay, txt_monthpay, txt_time, txt_workinfo;
	public static int waiting_count=0;
	public static int opencheck_management = 0;
	
	public Frame_Management() {
		setTitle("직원 출근부 관리");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800,100);
		setLayout(null);
		setSize(797,698);
		setResizable(false);
		addWindowListener(this);
		
		setManagementFrame();
		setEmployeeListTable();
		
		opencheck_management = 1;
		
		setVisible(true);
	}
		

	private void setEmployeeListTable() {
		tablemodel = new DefaultTableModel(tuple, attribute) {
			public boolean isCellEditable(int tuple, int attribute) {
				return false;
			}
		};
		table_employeeList = new JTable(tablemodel);
		table_employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_employeeList.setRowHeight(70);
		table_employeeList.getTableHeader().setBackground(new Color(36,38,42));
		table_employeeList.getTableHeader().setReorderingAllowed(false);
		table_employeeList.getTableHeader().setResizingAllowed(false);
		table_employeeList.getTableHeader().setForeground(Color.WHITE);
		table_employeeList.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
		table_employeeList.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		table_employeeList.addMouseListener(this);
		
		
		//각 열의 넓이 지정(비율적 조정)
		table_employeeList.getColumnModel().getColumn(0).setPreferredWidth(50);
		table_employeeList.getColumnModel().getColumn(1).setPreferredWidth(35);
		table_employeeList.getColumnModel().getColumn(2).setPreferredWidth(70);
		table_employeeList.getColumnModel().getColumn(3).setPreferredWidth(90);
		table_employeeList.getColumnModel().getColumn(4).setPreferredWidth(120);
		
		
		//가운데 정렬
		tablesort = new DefaultTableCellRenderer();
		tablesort.setHorizontalAlignment(tablesort.CENTER);
		
		table_employeeList.getColumnModel().getColumn(0).setCellRenderer(tablesort);
		table_employeeList.getColumnModel().getColumn(1).setCellRenderer(tablesort);
		table_employeeList.getColumnModel().getColumn(2).setCellRenderer(tablesort);
		table_employeeList.getColumnModel().getColumn(3).setCellRenderer(tablesort);
		table_employeeList.getColumnModel().getColumn(4).setCellRenderer(tablesort);
		
		
		JScrollPane sc = new JScrollPane(table_employeeList);
		sc.setBounds(5,72,369,520);
		sc.getViewport().setBackground(Color.WHITE);
		sc.setBorder(new LineBorder(new Color(37,47,66)));
		
		setTable();
		
		Frame_management.add(sc);
	}

	private void setManagementFrame() {
		Frame_management.setBackground(Color.WHITE);
		
		label_info_frame = new JLabel("직원 출근부 관리", JLabel.CENTER);
		label_info_frame.setBackground(Color.WHITE);
		label_info_frame.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_info_frame.setBounds(127, 0, 560, 67);
		label_info_frame.setOpaque(true);
		Frame_management.add(label_info_frame);
		
		Oracle_DAO Oracle = new Oracle_DAO();
		waiting_count = Integer.parseInt(Oracle.OracleGetData("userinfo", Frame_Login.email, "userwaiting"));
		btn_waiting = new JButton("요청대기중( "+ waiting_count +" )");
		btn_waiting.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_waiting.setBounds(5, 19, 119, 36);
		btn_waiting.setBackground(new Color(36,38,42));
		btn_waiting.setForeground(Color.WHITE);
		btn_waiting.setFocusable(false);
		Frame_management.add(btn_waiting);
		btn_waiting.addActionListener(this);
		
		btn_exit = new JButton("나가기");
		btn_exit.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_exit.setBounds(710, 19, 70, 36);
		btn_exit.setBackground(new Color(36,38,42));
		btn_exit.setForeground(Color.WHITE);
		btn_exit.setFocusable(false);
		Frame_management.add(btn_exit);
		btn_exit.addActionListener(this);
		
		panel_calender = new JPanel();
		panel_calender.setLayout(null);

		panel_calender.setBounds(379, 72, 405, 417);
		Frame_Calendar fc = new Frame_Calendar();
		fc.setCalendarFrame();
		panel_calender.add(fc.mainpanel);
		Frame_management.add(panel_calender);
		
		panel_pay_info = new JPanel();
		panel_pay_info.setBackground(Color.WHITE);
		panel_pay_info.setBounds(379, 493, 405, 169);
		panel_pay_info.setBorder(new LineBorder(new Color(37,47,66)));
		Frame_management.add(panel_pay_info);
		panel_pay_info.setLayout(null);
		
		label_workinfo = new JLabel("근무정보 :", JLabel.RIGHT);
		label_workinfo.setBounds(12, 15, 71, 15);
		panel_pay_info.add(label_workinfo);
		
		txt_workinfo = new JTextField();
		txt_workinfo.setEditable(false);
		txt_workinfo.setBorder(new LineBorder(new Color(37,47,66)));
		txt_workinfo.setBackground(Color.WHITE);
		txt_workinfo.setBounds(100,12,200,20);
		panel_pay_info.add(txt_workinfo);
		
		label_time = new JLabel("근무시간 :", JLabel.RIGHT);
		label_time.setBounds(12, 55, 71, 15);
		panel_pay_info.add(label_time);
		
		txt_time = new JTextField();
		txt_time.setEditable(false);
		txt_time.setBorder(new LineBorder(new Color(37,47,66)));
		txt_time.setBackground(Color.WHITE);
		txt_time.setBounds(100,52,60,20);
		panel_pay_info.add(txt_time);
		
		label_timepay = new JLabel("시급 :", JLabel.RIGHT);
		label_timepay.setBounds(12, 95, 71, 15);
		panel_pay_info.add(label_timepay);
		
		txt_timepay = new JTextField();
		txt_timepay.setEditable(false);
		txt_timepay.setBorder(new LineBorder(new Color(37,47,66)));
		txt_timepay.setBackground(Color.WHITE);
		txt_timepay.setBounds(100,92,80,20);
		panel_pay_info.add(txt_timepay);
		
		label_monthpay = new JLabel("예상월급 :", JLabel.RIGHT);
		label_monthpay.setBounds(12, 135, 71, 15);
		panel_pay_info.add(label_monthpay);
		
		txt_monthpay = new JTextField();
		txt_monthpay.setEditable(false);
		txt_monthpay.setBorder(new LineBorder(new Color(37,47,66)));
		txt_monthpay.setBackground(Color.WHITE);
		txt_monthpay.setBounds(100,132,100,20);
		panel_pay_info.add(txt_monthpay);
		
		btn_fire = new JButton("퇴사");
		btn_fire.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_fire.setForeground(new Color(36,38,42));
		btn_fire.setBackground(Color.WHITE);
		btn_fire.setBorder(new LineBorder(new Color(36,38,42),2));
		btn_fire.setBounds(275, 600, 100, 60);
		Frame_management.add(btn_fire);
		btn_fire.addActionListener(this);
		
		btn_setemployee = new JButton("직원 근무 정보 설정");
		btn_setemployee.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btn_setemployee.setBackground(new Color(36,38,42));
		btn_setemployee.setForeground(Color.WHITE);
		btn_setemployee.setFocusable(false);
		btn_setemployee.setBounds(5, 600, 265, 60);
		Frame_management.add(btn_setemployee);
		btn_setemployee.addActionListener(this);

	}
	
	public static void removeWaiting() {
		tablemodel.removeRow(table_employeeList.getSelectedRow());
	}
	
	private void setTable() {
			Oracle_DAO Oracle = new Oracle_DAO();
			Oracle.OracleGetRow2(Frame_Login.email);
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		if(command == btn_waiting) {
			if(Frame_Waiting.opencheck_waiting == 0) {
				new Frame_Waiting();
			}else {
				return;
			}
		}
		if(command == btn_fire) {
			int result = JOptionPane.showConfirmDialog(null, "정말 퇴사시키겠습니까?", "메시지", JOptionPane.OK_CANCEL_OPTION);
			if(result == 0) {
				Oracle_DAO Oracle = new Oracle_DAO();
				try {
					Oracle.OracleSetData("userinfo",table_employeeList.getValueAt(table_employeeList.getSelectedRow(), 4).toString(), "useremployerid", "Empty");
					Oracle.OracleSetData("userinfo",table_employeeList.getValueAt(table_employeeList.getSelectedRow(), 4).toString(), "useremployercheck", "N");				
					new Frame_MessageBox("메시지", "직원 ' " + Oracle.OracleGetData("userinfo", table_employeeList.getValueAt(table_employeeList.getSelectedRow(), 4).toString(), "username")+" '이(가) 퇴사 처리 되었습니다.");
					removeWaiting();
				} catch (ArrayIndexOutOfBoundsException e2) {
					new Frame_MessageBox("메시지", "항목이 선택되지 않았습니다.");
				}
			}
		}
		if(command == btn_exit) {
			dispose();
			opencheck_management = 0;
		}
		if(command == btn_setemployee) {
			if(Frame_SetEmployeeWorkData.opencheck_setemployeeworkdata == 0) {
				new Frame_SetEmployeeWorkData();
			}else {
				return;
			}
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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
	public void mousePressed(MouseEvent e) {
		Object focus = e.getSource();
		Oracle_DAO Oracle = new Oracle_DAO();
		int timepay = 0;
		if(focus == table_employeeList) {
			try {
				timepay = Integer.parseInt(Oracle.OracleGetData("userinfo", table_employeeList.getValueAt(table_employeeList.getSelectedRow(), 4).toString(), "usertimepay"));
				txt_timepay.setText(String.valueOf(timepay));
				txt_workinfo.setText(Oracle.OracleGetData("userinfo", table_employeeList.getValueAt(table_employeeList.getSelectedRow(), 4).toString(), "userworkinfo"));
			} catch (Exception e2) {
				txt_workinfo.setText("");
				txt_timepay.setText("");
				new Frame_MessageBox("메시지", "직원 근무 정보를 설정해주세요.");
			}
			try {
				int worktime = Integer.parseInt(Oracle.OracleGetWorkTime(table_employeeList.getValueAt(table_employeeList.getSelectedRow(), 4).toString(), Frame_Calendar.combo_year.getSelectedItem().toString(), Frame_Calendar.combo_month.getSelectedItem().toString()));
				if(worktime%60>30) {
					worktime = (worktime/60) + 1;
				}else {
					worktime = worktime/60;
				}
				txt_time.setText(String.valueOf(worktime));
				txt_monthpay.setText(""+worktime*timepay);
			} catch (Exception e2) {
				txt_time.setText("");
				txt_monthpay.setText("");
			}
			try {
				for(int k=1; k<=Frame_Calendar.lastDate; k++) {
					Frame_Calendar.btn_date[k].setBackground(Color.WHITE);
				}
				Oracle.OracleGetWorkCalendar(table_employeeList.getValueAt(table_employeeList.getSelectedRow(), 4).toString());
				String comboyear = Frame_Calendar.combo_year.getSelectedItem().toString();
				String combomonth = Frame_Calendar.combo_month.getSelectedItem().toString();
				for(int k = 0; k<Oracle_DAO.count; k++) {
					if(Oracle_DAO.array_year[k].equals(comboyear)) {
						if(Oracle_DAO.array_month[k].equals(combomonth)) {
							Frame_Calendar.btn_date[Integer.parseInt(Oracle_DAO.array_date[k])].setBackground(Color.ORANGE);
						}
					}
				}
			} catch (Exception e2) {
			}	
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
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
		opencheck_management = 0;
		
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
