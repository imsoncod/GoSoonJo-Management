package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_RegistWorkActive extends JFrame implements ActionListener{

	private Container Frame_RegistWorkActive = getContentPane();
	private JLabel label_workdate, label_year, label_month, label_date, label_startwork, label_endwork, label_starthour, label_startminute, label_endhour, label_endminute, label_timepay, label_krw, label_main;
	private JTextField txt_timepay;
	public static JComboBox<String> combo_year, combo_month, combo_date, combo_starthour, combo_startminute, combo_endhour, combo_endminute;
	private JButton btn_regist, btn_cancel;
	private String arrayYear[], arrayMonth[], arrayDate[], arrayHour[], arrayMinute[];
	
	
	public Frame_RegistWorkActive() {
		getContentPane().setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		setTitle("출근부 등록");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800, 100);
		setSize(369, 316);
		setResizable(false);
		getContentPane().setLayout(null);

		setArray();
		setRegistWorkActiveFrame();

		setVisible(true);
	}
	
	   private void setArray() {
		      arrayYear = new String[6];
		      arrayMonth = new String[12];
		      arrayDate = new String[31];
		      arrayHour = new String[24];
		      arrayMinute = new String[12];
		      for(int i = 0; i<6; i++) {
		         arrayYear[i] = "" + (2019 + i);
		      }
		      for(int i = 0; i<12; i++) {
		         arrayMonth[i] = "" + (1 + i);
		      }
		      for(int i = 0; i<31; i++) {
		         arrayDate[i] = "" + (1 + i);
		      }
		      for(int i = 0; i<24; i++) {
			         arrayHour[i] = "" + (1 + i);
			      }
		      for(int i = 0; i<=11; i++) {
			         arrayMinute[i] = "" + (i*5);
			      }
		      
		   }

	private void setRegistWorkActiveFrame() {
		Frame_RegistWorkActive.setBackground(Color.WHITE);
		
		label_workdate = new JLabel("근무 일자");
		label_workdate.setBounds(31, 62, 57, 15);
		Frame_RegistWorkActive.add(label_workdate);
		
		combo_year = new JComboBox<String>(arrayYear);
		combo_year.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		combo_year.setBackground(Color.WHITE);
		combo_year.setBounds(100, 59, 61, 21);
		combo_year.setSelectedItem(String.valueOf(Frame_Calendar.combo_year.getSelectedItem()));
		Frame_RegistWorkActive.add(combo_year);
		
		combo_month = new JComboBox<String>(arrayMonth);
		combo_month.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		combo_month.setBackground(Color.WHITE);
		combo_month.setBounds(195, 59, 45, 21);
		combo_month.setSelectedItem(String.valueOf(Frame_Calendar.combo_month.getSelectedItem()));
		Frame_RegistWorkActive.add(combo_month);
		
		combo_date = new JComboBox<String>(arrayDate);
		combo_date.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		combo_date.setBackground(Color.WHITE);
		combo_date.setBounds(272, 59, 45, 21);
		combo_date.setSelectedIndex(Frame_Calendar.j);
		Frame_RegistWorkActive.add(combo_date);
		
		label_year = new JLabel("년");
		label_year.setHorizontalAlignment(SwingConstants.CENTER);
		label_year.setBounds(162, 62, 21, 15);
		Frame_RegistWorkActive.add(label_year);
		
		label_month = new JLabel("월");
		label_month.setHorizontalAlignment(SwingConstants.CENTER);
		label_month.setBounds(236, 62, 26, 15);
		Frame_RegistWorkActive.add(label_month);
		
		label_date = new JLabel("일");
		label_date.setHorizontalAlignment(SwingConstants.CENTER);
		label_date.setBounds(314, 62, 31, 15);
		Frame_RegistWorkActive.add(label_date);
		
		label_startwork = new JLabel("시작 시간");
		label_startwork.setBounds(31, 110, 57, 15);
		Frame_RegistWorkActive.add(label_startwork);
		
		combo_starthour = new JComboBox<String>(arrayHour);
		combo_starthour.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		combo_starthour.setBackground(Color.WHITE);
		combo_starthour.setBounds(141, 107, 45, 21);
		Frame_RegistWorkActive.add(combo_starthour);
		
		label_starthour = new JLabel("시");
		label_starthour.setHorizontalAlignment(SwingConstants.CENTER);
		label_starthour.setBounds(187, 110, 21, 15);
		Frame_RegistWorkActive.add(label_starthour);
		
		combo_startminute = new JComboBox<String>(arrayMinute);
		combo_startminute.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		combo_startminute.setBackground(Color.WHITE);
		combo_startminute.setBounds(220, 107, 45, 21);
		Frame_RegistWorkActive.add(combo_startminute);
		
		label_startminute = new JLabel("분");
		label_startminute.setHorizontalAlignment(SwingConstants.CENTER);
		label_startminute.setBounds(261, 110, 26, 15);
		Frame_RegistWorkActive.add(label_startminute);
		
		label_endwork = new JLabel("종료 시간");
		label_endwork.setBounds(31, 154, 57, 15);
		Frame_RegistWorkActive.add(label_endwork);
		
		combo_endhour = new JComboBox<String>(arrayHour);
		combo_endhour.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		combo_endhour.setBackground(Color.WHITE);
		combo_endhour.setBounds(141, 151, 45, 21);
		Frame_RegistWorkActive.add(combo_endhour);
		
		label_endhour = new JLabel("시");
		label_endhour.setHorizontalAlignment(SwingConstants.CENTER);
		label_endhour.setBounds(187, 154, 21, 15);
		Frame_RegistWorkActive.add(label_endhour);
		
		combo_endminute = new JComboBox<String>(arrayMinute);
		combo_endminute.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		combo_endminute.setBackground(Color.WHITE);
		combo_endminute.setBounds(220, 151, 45, 21);
		Frame_RegistWorkActive.add(combo_endminute);
		
		label_endminute = new JLabel("분");
		label_endminute.setHorizontalAlignment(SwingConstants.CENTER);
		label_endminute.setBounds(261, 154, 26, 15);
		Frame_RegistWorkActive.add(label_endminute);
		
		label_timepay = new JLabel("시급");
		label_timepay.setBounds(57, 201, 31, 15);
		Frame_RegistWorkActive.add(label_timepay);
				
		txt_timepay = new JTextField(Frame_RegistWork.txt_timepay.getText());
		txt_timepay.setBorder(new LineBorder(new Color(32, 33, 42),1));
		txt_timepay.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		txt_timepay.setBounds(146, 198, 116, 21);
		Frame_RegistWorkActive.add(txt_timepay);
		txt_timepay.setEditable(false);
		txt_timepay.setBackground(Color.WHITE);
		
		label_krw = new JLabel("원");
		label_krw.setHorizontalAlignment(SwingConstants.CENTER);
		label_krw.setBounds(261, 201, 26, 15);
		Frame_RegistWorkActive.add(label_krw);
		
		label_main = new JLabel("출근부 등록");
		label_main.setForeground(Color.WHITE);
		label_main.setBackground(new Color(32, 33, 42));
		label_main.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_main.setHorizontalAlignment(SwingConstants.CENTER);
		label_main.setBounds(0, 0, 363, 37);
		label_main.setOpaque(true);
		Frame_RegistWorkActive.add(label_main);
		
		btn_regist = new JButton("등록");
		btn_regist.setForeground(Color.WHITE);
		btn_regist.setBackground(new Color(32, 33, 42));
		btn_regist.setFocusPainted(false);
		btn_regist.setBounds(64, 248, 97, 29);
		btn_regist.addActionListener(this);
		Frame_RegistWorkActive.add(btn_regist);
		
		btn_cancel = new JButton("취소");
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setBackground(new Color(32, 33, 42));
		btn_cancel.setFocusPainted(false);
		btn_cancel.setBounds(190, 248, 97, 29);
		btn_cancel.addActionListener(this);
		Frame_RegistWorkActive.add(btn_cancel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		Oracle_DAO Oracle = new Oracle_DAO();
		if(command == btn_regist) {
			int starthour = Integer.parseInt(combo_starthour.getSelectedItem().toString());
			int startminute = Integer.parseInt(combo_startminute.getSelectedItem().toString());
			int endhour = Integer.parseInt(combo_endhour.getSelectedItem().toString());
			int endminute = Integer.parseInt(combo_endminute.getSelectedItem().toString());
			int workhour=0, workminute=0, worktime=0;
			if(endhour >= starthour && endminute >= startminute) {
				workhour = endhour - starthour;
				workminute = endminute - startminute;
			}else if(endhour >= starthour && endminute < startminute) {
				workhour = endhour - starthour - 1;
				workminute = (endminute+60) - startminute;
			}else if(endhour < starthour && endminute >= startminute) {
				workhour = (endhour+24) - starthour;
				workminute = endminute - startminute;
			}else if(endhour < starthour && endminute < startminute) {
				workhour = (endhour+24) - starthour - 1;
				workminute = (endminute+60) - startminute;
			}
			worktime = (workhour*60) + workminute;
			if(Frame_RegistWork.txt_timepay.getText().isEmpty()) {
				new Frame_MessageBox("메시지", "아직 근무정보가 등록되지 않았습니다.");
				dispose();
				return;
			}else {
				Oracle.OracleRegistWorkCalendar(Frame_Login.email, combo_year.getSelectedItem().toString(), combo_month.getSelectedItem().toString(), combo_date.getSelectedItem().toString(), Oracle.OracleGetData("userinfo", Frame_Login.email, "userworkinfo"), txt_timepay.getText(), String.valueOf(worktime));
				try {
					worktime = Integer.parseInt(Oracle.OracleGetWorkTime(Frame_Login.email, Frame_Calendar.combo_year.getSelectedItem().toString(), Frame_Calendar.combo_month.getSelectedItem().toString()));
					if(worktime%60>30) {
						worktime = (worktime/60) + 1;
					}else {
						worktime = worktime/60;
					}
					Frame_RegistWork.txt_time.setText(String.valueOf(worktime));
					Frame_RegistWork.txt_monthpay.setText(""+worktime*Integer.parseInt(Frame_RegistWork.txt_timepay.getText()));
				} catch (Exception e2) {
					return;
				}
			}
			new Frame_MessageBox("메시지", "  출근부가 등록되었습니다.  ");
			Frame_Calendar.btn_date[Integer.parseInt(combo_date.getSelectedItem().toString())].setBackground(Color.ORANGE);
			dispose();
		}
		if(command == btn_cancel) {
			dispose();
		}
		
	}
	
	}
