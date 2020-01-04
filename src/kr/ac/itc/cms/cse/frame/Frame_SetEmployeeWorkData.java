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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

public class Frame_SetEmployeeWorkData extends JFrame implements ActionListener, WindowListener {

	private Container Frame_SetEmployeeWorkData = getContentPane();
	private JTextField txt_workinfo_input;
	private JTextField txt_timepay_input;
	private JLabel label_workinfo, label_timepay;
	private JButton btn_setting, btn_cancel;
	public static int opencheck_setemployeeworkdata = 0;

	public Frame_SetEmployeeWorkData() {
		setTitle("직원 근무 정보 설정");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(800, 100);
		setSize(318, 225);
		setResizable(false);
		setLayout(null);
		addWindowListener(this);

		setSetEmployeeWorkDataFrame();
		
		opencheck_setemployeeworkdata = 1;

		setVisible(true);
	}

	private void setSetEmployeeWorkDataFrame() {
		
		Frame_SetEmployeeWorkData.setBackground(Color.WHITE);
		
		label_workinfo = new JLabel("근무 정보");
		label_workinfo.setHorizontalAlignment(SwingConstants.RIGHT);
		label_workinfo.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_workinfo.setBounds(12, 30, 76, 25);
		Frame_SetEmployeeWorkData.add(label_workinfo);
		
		label_timepay = new JLabel("시급");
		label_timepay.setHorizontalAlignment(SwingConstants.RIGHT);
		label_timepay.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_timepay.setBounds(12, 80, 76, 25);
		Frame_SetEmployeeWorkData.add(label_timepay);
		
		txt_workinfo_input = new JTextField(Frame_Management.txt_workinfo.getText());
		txt_workinfo_input.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		txt_workinfo_input.setBounds(100, 30, 188, 25);
		Frame_SetEmployeeWorkData.add(txt_workinfo_input);
		txt_workinfo_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_workinfo_input.setColumns(10);
		
		txt_timepay_input = new JTextField(Frame_Management.txt_timepay.getText());
		txt_timepay_input.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		txt_timepay_input.setColumns(10);
		txt_timepay_input.setBorder(new LineBorder(Color.BLACK, 1));
		txt_timepay_input.setBounds(100, 82, 188, 25);
		Frame_SetEmployeeWorkData.add(txt_timepay_input);
		
		btn_setting = new JButton("설정");
		btn_setting.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn_setting.setBounds(49, 137, 97, 38);
		btn_setting.setForeground(Color.WHITE);
		btn_setting.setBackground(new Color(32, 33, 42));
		btn_setting.setFocusPainted(false);
		Frame_SetEmployeeWorkData.add(btn_setting);
		btn_setting.addActionListener(this);
		
		btn_cancel = new JButton("취소");
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setBackground(new Color(32, 33, 42));
		btn_cancel.setFocusPainted(false);
		btn_cancel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn_cancel.setBounds(174, 137, 97, 38);
		Frame_SetEmployeeWorkData.add(btn_cancel);
		btn_cancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command = e.getSource();
		Oracle_DAO Oracle = new Oracle_DAO();
		if(command == btn_setting) {
			try {
				if(txt_workinfo_input.getText().isEmpty()) {
					new Frame_MessageBox("메시지", "  근무정보를 입력해주세요.  ");
				}else if(txt_timepay_input.getText().isEmpty()) {
					new Frame_MessageBox("메시지", "  시급을 입력해주세요.  ");
				}else {
					Oracle.OracleSetData("userinfo", Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString(), "userworkinfo", txt_workinfo_input.getText());
					Oracle.OracleSetData("userinfo", Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString(), "usertimepay", txt_timepay_input.getText());
					Frame_Management.txt_workinfo.setText(Oracle.OracleGetData("userinfo", Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString(), "userworkinfo"));
					Frame_Management.txt_timepay.setText(Oracle.OracleGetData("userinfo", Frame_Management.table_employeeList.getValueAt(Frame_Management.table_employeeList.getSelectedRow(), 4).toString(), "usertimepay"));
					new Frame_MessageBox("메시지", "  정상적으로 설정되었습니다.  ");
					dispose();
					opencheck_setemployeeworkdata = 0;
				}
			} catch (Exception e2) {
				
			}if(Frame_Management.txt_time.getText().isEmpty()){
				Frame_Management.txt_monthpay.setText("");
			}else {
				Frame_Management.txt_monthpay.setText("" + (Integer.parseInt(txt_timepay_input.getText()))*(Integer.parseInt(Frame_Management.txt_time.getText())));
			}
			
		}
		if(command == btn_cancel) {
			dispose();
			opencheck_setemployeeworkdata = 0;
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
		opencheck_setemployeeworkdata = 0;
		
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
