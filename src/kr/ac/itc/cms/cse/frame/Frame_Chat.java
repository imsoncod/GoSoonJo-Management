package kr.ac.itc.cms.cse.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import kr.ac.itc.cms.cse.client.Client_Main;
import kr.ac.itc.cms.cse.oracle.Oracle_Chat;

public class Frame_Chat extends JFrame implements ActionListener, KeyListener, WindowListener {

	public JTextPane chat_txt;
	public JTextField txt_input;
	private String name = null;
	private Container c = getContentPane();
	private JButton btn;
	private JButton btn_invite;
	private DataOutputStream out;
	private String id = null;
	private int port = 0;
	private JScrollPane scroll;
	private Socket socket;
	private DataInputStream in;

	private String message;
	private SimpleAttributeSet left;
	private SimpleAttributeSet right;
	private StyledDocument doc;
	private String chatname;
	private int index;
	private JButton label_title;
	
	private Oracle_Chat oracle_Chat = new Oracle_Chat();
	public Frame_ChatList frame_ChatList = new Frame_ChatList();
	
	public Frame_Chat() {
	}

	public Frame_Chat(int x, int y, String chatname, String name, String id, int port, int index) {
		this.id = id;
		this.name = name;
		this.port = port;
		this.chatname = chatname;
		this.index = index;
		setTitle(chatname);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(323, 525);
		setResizable(false);
		setLocation(x+50, y+10);
		addWindowListener(this);

		setLayout(null);

		label_title = new JButton(chatname);
		label_title.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_title.setFocusable(false);
		label_title.setOpaque(true);
		label_title.setBackground(new Color(32,33,42));
		label_title.setForeground(Color.WHITE);
		label_title.setBounds(0, 0, 228, 30);
		label_title.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		label_title.addActionListener(this);

		btn_invite = new JButton("초대하기");
		btn_invite.setBounds(227, 0, 90, 30);
		btn_invite.setBackground(Color.WHITE);
		btn_invite.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		btn_invite.setFocusPainted(false);
		btn_invite.addActionListener(this);

		chat_txt = new JTextPane();
		chat_txt.setEditorKit(new WrapEditorKit());
		chat_txt.setEditable(false);

		doc = chat_txt.getStyledDocument();

		left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);

		right = new SimpleAttributeSet();
		StyleConstants.setForeground(right, Color.DARK_GRAY);
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);

		scroll = new JScrollPane(chat_txt);
		scroll.setBounds(0, 30, 334, 435);
		scroll.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		scroll.getViewport().getView().setFont(new Font("HY강B", Font.PLAIN, 14));
		scroll.getViewport().getView().setForeground(Color.BLACK);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		txt_input = new JTextField("");
		txt_input.setBounds(0, 465, 237, 31);
		txt_input.addKeyListener(this);
		txt_input.setFont(new Font("HY강B", Font.PLAIN, 14));
		txt_input.setBorder(new LineBorder(Color.DARK_GRAY, 1));

		btn = new JButton("입력");
		btn.setBounds(237, 465, 80, 31);
		btn.addActionListener(this);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(32,33,42));
		btn.setForeground(Color.WHITE);
		btn.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		c.setBackground(Color.WHITE);

		c.add(scroll);
		c.add(label_title);
		c.add(btn);
		c.add(txt_input);
		c.add(btn_invite);

		setVisible(true);
		txt_input.requestFocus();
	}

	public void addchat(String msg) {

		String[] data = msg.split("!@#");

		if (name.equals(data[0]) == true) {
			try {
				message = data[1];
				doc.setParagraphAttributes(doc.getLength(), 1, right, false);
				doc.insertString(doc.getLength(), message + "\n", right);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				message = data[0] + " : " + data[1];
				doc.setParagraphAttributes(doc.getLength(), 1, left, false);
				doc.insertString(doc.getLength(), message + "\n", left);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		chat_txt.setCaretPosition(chat_txt.getDocument().getLength());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btn) {
			if (txt_input.getText().isEmpty()) {
				txt_input.setText("");
				return;
			} else {
				Sender(name);
			}
		} 
		else if (obj == btn_invite) {
			new Frame_ChatInvite(getX(), getY(), id, port);
		} 
		else if ( obj == label_title ) {
			
			int result = JOptionPane.showConfirmDialog(null, "채팅방을 나가시겠습니까?", "메세지", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.CLOSED_OPTION) {
				
			} else {
				oracle_Chat.OracleChatDelete(name, chatname);
				frame_ChatList.setTableDel(index);
				dispose();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			if (txt_input.getText().isEmpty()) {
				txt_input.setText("");
				return;
			} else {
				Sender(name);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		Frame_ChatList.opencheck[Frame_ChatList.checkrow] = 0;
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
		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					Frame_ChatList.opencheck[Frame_ChatList.checkrow] = 1;
					socket = new Socket("183.91.253.86", port);
					Sender(name);
					Receiver();
				} catch (ConnectException e) {
					new Client_Main("183.91.253.86", port);
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		thread.start();
	}

	public void Sender(String name) {
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.writeUTF(name + "!@#" + txt_input.getText() + "");
			txt_input.setText("");
		} catch (IOException e) {
			System.out.println("예외123:" + e);
		}
	}

	public void Receiver() {

		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			System.out.println("예외456:" + e);
		}
		try {
			while (in != null) {
				addchat(in.readUTF());
			}
		} catch (Exception e) {
			System.out.println("예외789:" + e);
		}
	}
}

class WrapEditorKit extends StyledEditorKit {
	ViewFactory defaultFactory = new WrapColumnFactory();

	public ViewFactory getViewFactory() {
		return defaultFactory;
	}
}

class WrapColumnFactory implements ViewFactory {
	public View create(Element elem) {
		String kind = elem.getName();
		if (kind != null) {
			if (kind.equals(AbstractDocument.ContentElementName)) {
				return new WrapLabelView(elem);
			} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
				return new ParagraphView(elem);
			} else if (kind.equals(AbstractDocument.SectionElementName)) {
				return new BoxView(elem, View.Y_AXIS);
			} else if (kind.equals(StyleConstants.ComponentElementName)) {
				return new ComponentView(elem);
			} else if (kind.equals(StyleConstants.IconElementName)) {
				return new IconView(elem);
			}
		}
		return new LabelView(elem);
	}
}

class WrapLabelView extends LabelView {
	public WrapLabelView(Element elem) {
		super(elem);
	}

	public float getMinimumSpan(int axis) {
		switch (axis) {
		case View.X_AXIS:
			return 0;
		case View.Y_AXIS:
			return super.getMinimumSpan(axis);
		default:
			throw new IllegalArgumentException("Invalid axis: " + axis);
		}
	}
}
