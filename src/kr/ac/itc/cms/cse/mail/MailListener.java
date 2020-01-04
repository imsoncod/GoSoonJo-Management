package kr.ac.itc.cms.cse.mail;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import kr.ac.itc.cms.cse.frame.Frame_MessageBox;

public class MailListener {
   
     public static Logger log = Logger.getLogger(MailListener.class);
   
     private final String host = "smtp.naver.com";
     private final String user = "Email";
     private final String password = "Password";
     
     private boolean Check = false;
     private int size;
     public String Key = setKey(4);
   
   public String setKey(int size) {
      this.size = size;
      return result();
   }
   
   public void setCheck(boolean check) {
      this.Check = check;
   }
   
   public boolean getCheck() {
      return this.Check;
   }
   
   public String result() {
      Random random = new Random();
      StringBuffer sb = new StringBuffer();
      
      int num = 0;
      
      do {
         num = random.nextInt(75) + 48;
         if((num >= 48 && num <= 57) || (num>=65 && num<=90)) {
            sb.append(num);
         } else {
            continue;
         }
      } while(sb.length() < size);
      
      if(Check) {
         return sb.toString().toLowerCase();
      }
      return sb.toString();
   }
   
   public void SendMail(String Mail) {
        String to = Mail;
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(user, password);
         }
        });
        try {
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(user));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         message.setSubject("고순조 프로젝트");
         
         message.setText("인증번호 : " + Key);

         Transport.send(message);
         log.info("PIN : " + Key);
         log.info("Your PIN number has been emailed.");
         new Frame_MessageBox("메세지", "인증번호가 전송되었습니다!");
         
        } catch (AddressException e) {
           new Frame_MessageBox("오류발생", "이메일을 작성해주세요!");
           log.error("Please fill out the e-mail.");
           return;
        } catch (SendFailedException e) {
           new Frame_MessageBox("오류발생", "이메일 형식에 맞게 작성해주세요");
           log.error("Please fill out the e-mail format.");
           return;
        } catch (MessagingException e) {
           e.printStackTrace();
        }
   }
   
   public void SendPWMail(String Mail, String pw) {
        String to = Mail;
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(user, password);
         }
        });
        try {
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(user));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         message.setSubject("고순조 프로젝트");
         
         message.setText("비밀번호 : " + pw);

         Transport.send(message);
         log.info("Your PassWord number has been emailed.");
         new Frame_MessageBox("메세지", "비밀번호가 전송되었습니다!");
         

        } catch (AddressException e) {
           new Frame_MessageBox("오류발생", "이메일을 작성해주세요!");
           log.error("Please fill out the e-mail.");
           return;
        } catch (SendFailedException e) {
           new Frame_MessageBox("오류발생", "이메일 형식에 맞게 작성해주세요");
           log.error("Please fill out the e-mail format.");
           return;
        } catch (MessagingException e) {
           e.printStackTrace();
        }
   }
   
   public void CodeCheck(String input) {
      
      if(Key.equals(input)) {
          log.info("E-mail authentication succeeded!");
         setCheck(true);
      } else {
         log.info("E-mail authentication failed!");
         setCheck(false);
      }
   }

}
