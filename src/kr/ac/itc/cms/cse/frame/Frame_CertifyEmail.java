package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import kr.ac.itc.cms.cse.mail.MailListener;
import kr.ac.itc.cms.cse.oracle.Oracle_DAO;

@SuppressWarnings("serial")
public class Frame_CertifyEmail extends JFrame implements ActionListener{
	
	public static Logger log = Logger.getLogger(Frame_CertifyEmail.class);
	
   Container c;
   Font font;
   JLabel label_main, label_email, label_cfnum;
   JTextArea txt_email;
   JTextField txt_cfnum_input;
   JButton btn_sendemail, btn_certify;
   MailListener ml = new MailListener();
   Frame_MessageBox msg;
   
   private Oracle_DAO Oracle = new Oracle_DAO(); 
   
   public boolean check = false;
   
   public void setCheck(boolean check) {
	   this.check = check;
   }
   
   public boolean getCheck() {
	   return this.check;
   }
   
   public Frame_CertifyEmail() {}
   
   public Frame_CertifyEmail(String title) {
	   log.info("Completed creating email authentication frame");
      setTitle(title);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLocation(1200,100);
      setLayout(null);
      setSize(400,260);
      setResizable(false);            
      
      setFont();
      setCertifyEmailFrame();
      
      setVisible(true);
   }

   private void setCertifyEmailFrame() {
      c = getContentPane();
      c.setBackground(Color.WHITE);
      
      label_main = new JLabel("이메일 인증", JLabel.CENTER);
      label_main.setSize(400, 40);
      label_main.setLocation(0, 0);
      label_main.setFont(new Font("휴먼모음T", Font.BOLD, 18));
      label_main.setBackground(new Color(39,47,66));
      label_main.setForeground(Color.WHITE);
      label_main.setOpaque(true);
      
      label_email = new JLabel("이메일");
      label_email.setSize(60, 15);
      label_email.setLocation(20, 60);
      label_email.setFont(font);
      
      txt_email = new JTextArea(Frame_NewAccount.txt_id_input.getText());
      txt_email.setSize(200, 30);
      txt_email.setLocation(20, 85);
      txt_email.setEditable(false);
      txt_email.setFont(font);
      txt_email.setBorder(new LineBorder(Color.BLACK));
      
      Image img_send = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/인증번호전송.png"));
      btn_sendemail = new JButton(new ImageIcon(img_send));
      btn_sendemail.setSize(140, 30);
      btn_sendemail.setLocation(220, 85);
      btn_sendemail.setBorderPainted(false);
      btn_sendemail.setContentAreaFilled(false);
      btn_sendemail.setFocusPainted(false);
      btn_sendemail.setBackground(Color.WHITE);
      btn_sendemail.setFont(font);
      btn_sendemail.setCursor(new Cursor(Cursor.HAND_CURSOR));
      btn_sendemail.addActionListener(this);
      
      label_cfnum = new JLabel("인증번호");
      label_cfnum.setSize(60, 15);
      label_cfnum.setLocation(20, 140);
      label_cfnum.setFont(font);
      
      txt_cfnum_input = new JTextField();
      txt_cfnum_input.setSize(120, 30);
      txt_cfnum_input.setLocation(20, 165);
      txt_cfnum_input.setFont(font);
      txt_cfnum_input.setBorder(new LineBorder(Color.BLACK));
      
      Image img_check = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/인증.png"));
      btn_certify = new JButton(new ImageIcon(img_check));
      btn_certify.setSize(70, 30);
      btn_certify.setLocation(150, 165);
      btn_certify.setBorderPainted(false);
      btn_certify.setContentAreaFilled(false);
      btn_certify.setFocusPainted(false);
      btn_certify.setBackground(Color.WHITE);
      btn_certify.setFont(font);
      btn_certify.setCursor(new Cursor(Cursor.HAND_CURSOR));
      btn_certify.addActionListener(this);
      
      
      c.add(label_main);
      c.add(label_email);
      c.add(txt_email);
      c.add(btn_sendemail);
      
      c.add(label_cfnum);
      c.add(txt_cfnum_input);
      c.add(btn_certify);
      
   }
   
   @SuppressWarnings("static-access")
private void setFont() {
      font = new Font("맑은 고딕", font.BOLD, 14);
   }

@Override
public void actionPerformed(ActionEvent e) {
	Object obj = e.getSource();
	
	if(obj == btn_sendemail) {
		if(Oracle.OracleDateOverlap(Frame_NewAccount.txt_id_input.getText()) == 0 ) {
			ml.SendMail(Frame_NewAccount.txt_id_input.getText());
		} else {
			 log.error("This ID already exists");
			 msg = new Frame_MessageBox("메세지", " 존재하는 아이디입니다. ");
		}
	}
	else if( obj == btn_certify ) {
		ml.CodeCheck(txt_cfnum_input.getText());
		setCheck(ml.getCheck());
		if(ml.getCheck() == true) {
			msg = new Frame_MessageBox("메세지", "이메일 인증이 완료되었습니다!");
			dispose();
		} 
		else {
			msg = new Frame_MessageBox("오류발생", "인증번호를 틀리셨습니다.");
		}
	}
	
}

}