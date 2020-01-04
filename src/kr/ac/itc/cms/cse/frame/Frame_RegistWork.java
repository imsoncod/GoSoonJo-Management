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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_RegistWork extends JFrame implements ActionListener, WindowListener {

	private Container Frame_RegistWork = getContentPane();
	private JLabel label_timepay, label_monthpay, label_time, label_workinfo;
	public  JPanel panel_calender;
	public JPanel panel_pay_info;
	private JButton btn_fire;
	private JButton btn_exit;
	public static JTextField txt_workinfo;
	public static JTextField txt_time;
	public static JTextField txt_timepay;
	public static JTextField txt_monthpay;
	Frame_Calendar fc = new Frame_Calendar();
	public static int opencheck_registwork = 0;
	
	private Oracle_DAO oracle_DAO = new Oracle_DAO();

	public Frame_RegistWork() {
		setTitle("출근부 등록 메인");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Frame_RegistWork.setBackground(Color.WHITE);
		setLocation(800, 100);
		setSize(400, 630);
		setResizable(false);
		setLayout(null);
		addWindowListener(this);

		setRegistWorkFrame();
		
		opencheck_registwork = 1;

		setVisible(true);
	}

	public void setRegistWorkFrame() {
		panel_calender = new JPanel();
		panel_calender.setBackground(Color.WHITE);
		panel_calender.setBounds(0, 0, 394, 415);
		panel_calender.setLayout(null);
		fc.setCalendarFrame();
		Oracle_DAO Oracle = new Oracle_DAO();
		try {
			Oracle.OracleGetWorkCalendar(Frame_Login.email);
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
		fc.panel_north.setBounds(0, 0, 394, 40);
		fc.panel_center.setBounds(0, 40, 394, 375);
		fc.panel_center.setBorder(new LineBorder(Color.BLACK));
		panel_calender.add(fc.mainpanel);
		Frame_RegistWork.add(panel_calender);

		panel_pay_info = new JPanel();
		panel_pay_info.setBorder(new LineBorder(new Color(37,47,66)));
		panel_pay_info.setBounds(0, 414, 394, 135);
		Frame_RegistWork.add(panel_pay_info);
		panel_pay_info.setBackground(Color.WHITE);
		panel_pay_info.setLayout(null);
		
		label_workinfo = new JLabel("근무정보 :", JLabel.RIGHT);
		label_workinfo.setFont((new Font("맑은 고딕", Font.BOLD, 12)));
		label_workinfo.setBounds(12, 10, 71, 15);
		panel_pay_info.add(label_workinfo);
		
		txt_workinfo = new JTextField();
		txt_workinfo.setText(Oracle.OracleGetData("userinfo", Frame_Login.email, "userworkinfo"));
		txt_workinfo.setBorder(new LineBorder(new Color(37,47,66)));
		txt_workinfo.setEditable(false);
		txt_workinfo.setBackground(Color.WHITE);
		txt_workinfo.setBounds(95,9,200,20);
		panel_pay_info.add(txt_workinfo);

		label_time = new JLabel("근무시간 :", JLabel.RIGHT);
		label_time.setBounds(12, 40, 70, 15);
		label_time.setFont((new Font("맑은 고딕", Font.BOLD, 12)));
		panel_pay_info.add(label_time);
		
		int worktime = 0;
		txt_time = new JTextField();
		try {
			worktime = Integer.parseInt(Oracle.OracleGetWorkTime(Frame_Login.email, Frame_Calendar.combo_year.getSelectedItem().toString(), Frame_Calendar.combo_month.getSelectedItem().toString()));
			if(worktime%60>30) {
				worktime = (worktime/60) + 1;
			}else {
				worktime = worktime/60;
			}
			txt_time.setText(String.valueOf(worktime));
		} catch (Exception e) {
			txt_time.setText("");
		}
		txt_time.setBorder(new LineBorder(new Color(37,47,66)));
		txt_time.setEditable(false);
		txt_time.setBackground(Color.WHITE);
		txt_time.setBounds(95,39,60,20);
		panel_pay_info.add(txt_time);

		label_timepay = new JLabel("시급 :", JLabel.RIGHT);
		label_timepay.setBounds(36, 70, 46, 15);
		label_timepay.setFont((new Font("맑은 고딕", Font.BOLD, 12)));
		panel_pay_info.add(label_timepay);
		
		int timepay = 0;
		txt_timepay = new JTextField();
		try {
			timepay = Integer.parseInt(Oracle.OracleGetData("userinfo", Frame_Login.email, "usertimepay"));
			txt_timepay.setText(String.valueOf(timepay));
		} catch (Exception e) {
			txt_timepay.setText("");
		}
		txt_timepay.setBorder(new LineBorder(new Color(37,47,66)));
		txt_timepay.setEditable(false);
		txt_timepay.setBackground(Color.WHITE);
		txt_timepay.setBounds(95,69,80,20);
		panel_pay_info.add(txt_timepay);
	
		label_monthpay = new JLabel("예상월급 :", JLabel.RIGHT);
		label_monthpay.setBounds(12, 100, 70, 15);
		label_monthpay.setFont((new Font("맑은 고딕", Font.BOLD, 12)));
		panel_pay_info.add(label_monthpay);
		
		txt_monthpay = new JTextField();
		if(worktime*timepay==0) {
			txt_monthpay.setText("");
		}else{
			txt_monthpay.setText(""+worktime*timepay);
		}
		txt_monthpay.setBorder(new LineBorder(new Color(37,47,66)));
		txt_monthpay.setEditable(false);
		txt_monthpay.setBackground(Color.WHITE);
		txt_monthpay.setBounds(95,99,100,20);
		panel_pay_info.add(txt_monthpay);

		btn_fire = new JButton("퇴사 신청");
		btn_fire.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_fire.setForeground(new Color(32, 33, 42));
		btn_fire.setBackground(Color.WHITE);
		btn_fire.setBorder(new LineBorder(new Color(32, 33, 42),1));
		btn_fire.setFocusPainted(false);
		btn_fire.setBounds(280, 555, 110, 41);
		Frame_RegistWork.add(btn_fire);
		btn_fire.addActionListener(this);

		btn_exit = new JButton("나가기");
		btn_exit.setForeground(Color.WHITE);
		btn_exit.setBackground(new Color(32, 33, 42));
		btn_exit.setFocusPainted(false);
		btn_exit.setBounds(5, 555, 270, 41);
		Frame_RegistWork.add(btn_exit);
		btn_exit.addActionListener(this);

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		if (command == btn_fire) {
			Oracle_DAO Oracle = new Oracle_DAO();
			int result = JOptionPane.showConfirmDialog(null, "정말 퇴사하시겠습니까?", "메시지", JOptionPane.OK_CANCEL_OPTION);
			if(result == 0) {
				Oracle.OracleSetData("userinfo", Frame_Login.email, "useremployerid", "Empty");
				Oracle.OracleSetData("userinfo", Frame_Login.email, "useremployercheck", "N");		
				Oracle.OracleSetData("userinfo", Frame_Login.email, "usertimepay", "");		
				Oracle.OracleSetData("userinfo", Frame_Login.email, "userworkinfo", "");		
				Oracle.OracleDeleteData("workcalendar", Frame_Login.email);
				JOptionPane.showConfirmDialog(null, "퇴사 처리 되었습니다.", "메시지", JOptionPane.CLOSED_OPTION , JOptionPane.INFORMATION_MESSAGE);
				if(Oracle.OracleLogOut(Frame_Login.email) == 0) {
				System.exit(0);
				}
			}
		}
		if (command == btn_exit)
				dispose();
				opencheck_registwork = 0;
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
			dispose();
			opencheck_registwork = 0;
		
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
