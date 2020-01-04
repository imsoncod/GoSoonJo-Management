package kr.ac.itc.cms.cse.frame;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;

import javax.swing.JComboBox;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_Calendar extends JFrame implements ActionListener {
	
	JPanel mainpanel = new JPanel();

	// North 패널 설정 - 연도,달 콤보 박스

	JPanel panel_north = new JPanel();

	JLabel label_year = new JLabel("년");
	JLabel label_month = new JLabel("월");

	public static JComboBox<Integer> combo_year = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> combo_model_year = new DefaultComboBoxModel<Integer>();

	public static JComboBox<Integer> combo_month = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> combo_model_month = new DefaultComboBoxModel<Integer>();

	// Center 패널 설정 - 요일, 날짜

	JPanel panel_center = new JPanel(new BorderLayout());
	JPanel panel_week = new JPanel(new GridLayout(1, 7));
	String panel_day[] = { "일", "월", "화", "수", "목", "금", "토" };
	JPanel panel_date = new JPanel(new GridLayout(0, 7, 1, 1));

	Calendar cal_now;
	int data_year, data_month, data_date;
	public static JButton[] btn_date = new JButton[50];
	

	public void setCalendarFrame() {
		// 현재 날짜를 넣을 객체 생성
		cal_now = Calendar.getInstance();
		data_year = cal_now.get(Calendar.YEAR);
		data_month = cal_now.get(Calendar.MONTH) + 1;
		data_date = cal_now.get(Calendar.DATE);

		// North 패널

		// 년도 선택 콤보박스 설정
		for (int i = data_year; i <= data_year + 5; i++) {

			combo_model_year.addElement(i);

		}
		combo_year.setModel(combo_model_year);
		combo_year.setBackground(Color.WHITE);
		combo_year.setSelectedItem(data_year); // 현재 년도 선택

		// 월 선택 콤보박스 설정
		for (int i = 1; i <= 12; i++) {

			combo_model_month.addElement(i);

		}

		combo_month.setModel(combo_model_month);
		combo_month.setBackground(Color.WHITE);
		combo_month.setSelectedItem(data_month); // 현재 월 선택

		// North 패널에 버튼 라벨 추가 및 배경색 변경

		panel_north.setBackground(new Color(36,38,42));
		panel_north.add(combo_year);
		label_year.setFont(new Font("맑은고딕", Font.BOLD, 14));
		label_year.setForeground(Color.WHITE);
		panel_north.add(label_year);
		panel_north.add(combo_month);
		label_month.setFont(new Font("맑은고딕", Font.BOLD, 14));
		label_month.setForeground(Color.WHITE);
		panel_north.add(label_month);

		mainpanel.add(panel_north, "North");

		// Center 패널

		// 요일 라벨 설정
		for (int i = 0; i < panel_day.length; i++) {

			JLabel lbl = new JLabel(panel_day[i], JLabel.CENTER);

			if (i == 0) {
				lbl.setForeground(Color.red);

			} else if (i == 6) {
				lbl.setForeground(Color.blue);
			}

			panel_week.add(lbl);

		}
		
		panel_week.setBorder(new LineBorder(new Color(37,47,66)));
		panel_week.setBackground(Color.white);

		// Center 패널에 요일 패널과 날짜 패널 삽입
		panel_center.setBorder(new LineBorder(new Color(37,47,66)));
		panel_center.add(panel_week, "North");
		panel_center.add(panel_date, "Center");
		mainpanel.add(panel_center, "Center");

		// 날짜 출력 함수
		dayPrint(data_year, data_month);
		
		panel_north.setBounds(0, 0, 405, 40);
		panel_center.setBounds(0,40,405,377);
		mainpanel.setLayout(null);
		mainpanel.setBounds(0,0,415,417);

		combo_year.addActionListener(this);
		combo_month.addActionListener(this);

		
	}
	public static int j;

	public void actionPerformed(ActionEvent e) {
		Oracle_DAO Oracle = new Oracle_DAO();
		Object obj = e.getSource();
			for(int i = 1; i<=31; i++) {
				if(btn_date[i] == obj) {
					if(Oracle.OracleGetData("userinfo", Frame_Login.email, "userjob").equals("직원")) {
					j=i-1;
					if(Oracle.OracleGetWorkCalendarExist(Frame_Login.email, combo_year.getSelectedItem().toString(), combo_model_month.getSelectedItem().toString(), String.valueOf(j+1)).equals("1")) {
						new Frame_RegistWorkChange();
					}else {
						new Frame_RegistWorkActive();
					}
				}else {
					return;
				}
			}
		}

		if (obj instanceof JComboBox) { // 콤보박스 이벤트 발생시

			createDayStart();
			try {
				if(Oracle.OracleGetData("userinfo", Frame_Login.email, "userjob").equals("사장")) {
					Oracle.OracleGetWorkCalendar(Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString());
				}else {
					Oracle.OracleGetWorkCalendar(Frame_Login.email);
				}
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
			if(Oracle.OracleGetData("userinfo", Frame_Login.email, "userjob").equals("사장")) {
				try {
					Frame_Management.txt_workinfo.setText(Oracle.OracleGetData("userinfo", Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString(), "userworkinfo"));
					int timepay = Integer.parseInt(Oracle.OracleGetData("userinfo", Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString(), "usertimepay"));
					Frame_Management.txt_timepay.setText(String.valueOf(timepay));
					int worktime = Integer.parseInt(Oracle.OracleGetWorkTime(Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString(), Frame_Calendar.combo_year.getSelectedItem().toString(), Frame_Calendar.combo_month.getSelectedItem().toString()));
					if(worktime%60>30) {
						worktime = (worktime/60) + 1;
					}else {
						worktime = worktime/60;
					}
					Frame_Management.txt_time.setText(String.valueOf(worktime));
					Frame_Management.txt_monthpay.setText(""+worktime*timepay);
				} catch (Exception e2) {
					try {
						Frame_Management.txt_time.setText("");
						Frame_Management.txt_monthpay.setText("");
					} catch (Exception e3) {
						
					}
				}
			}else {
				try {	
					Frame_RegistWork.txt_workinfo.setText(Oracle.OracleGetData("userinfo", Frame_Login.email, "userworkinfo"));
					int timepay = Integer.parseInt(Oracle.OracleGetData("userinfo", Frame_Login.email, "usertimepay"));
					Frame_RegistWork.txt_timepay.setText(String.valueOf(timepay));
					int worktime = Integer.parseInt(Oracle.OracleGetWorkTime(Frame_Login.email, Frame_Calendar.combo_year.getSelectedItem().toString(), Frame_Calendar.combo_month.getSelectedItem().toString()));					
					if(worktime%60>30) {
						worktime = (worktime/60) + 1;
					}else {
						worktime = worktime/60;
					}
					Frame_RegistWork.txt_time.setText(String.valueOf(worktime));
					Frame_RegistWork.txt_monthpay.setText(""+worktime*timepay);
				} catch (Exception e2) {
					try {
						Frame_RegistWork.txt_time.setText("");
						Frame_RegistWork.txt_monthpay.setText("");
					} catch (Exception e3) {
						
					}
				}
			}
		}

		}


	public void createDayStart() {

		panel_date.setVisible(false); // 패널 숨기기

		panel_date.removeAll(); // 날짜 출력한 라벨 지우기

		dayPrint((Integer) combo_year.getSelectedItem(), (Integer) combo_month.getSelectedItem());

		panel_date.setVisible(true); // 패널 재출력

	}

	public static int lastDate;
	
	public void dayPrint(int y, int m) {

		Calendar cal = Calendar.getInstance();

		cal.set(y, m - 1, 1); // 출력할 첫날의 객체 만든다.

		int week = cal.get(Calendar.DAY_OF_WEEK); // 1일에 대한 요일 일요일 : 0

		lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 그 달의 마지막 날

		for (int i = 1; i < week; i++) { // 날짜 출력 전까지의 공백 출력

			panel_date.add(new JLabel(" "));
			panel_date.setBackground(Color.WHITE);

		}

		for (int i = 1; i <= lastDate; i++) {

			btn_date[i] = new JButton(String.valueOf(i));
			btn_date[i].addActionListener(this);
			btn_date[i].setBackground(Color.WHITE);
			btn_date[i].setBorder(new LineBorder(Color.BLACK));
				
			cal.set(y, m - 1, i);

			int outWeek = cal.get(Calendar.DAY_OF_WEEK);

			if (outWeek == 1) {

				btn_date[i].setForeground(Color.red);

			} else if (outWeek == 7) {

				btn_date[i].setForeground(Color.BLUE);

			}

			panel_date.add(btn_date[i]);

		}

	}

}
