package kr.ac.itc.cms.cse.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import kr.ac.itc.cms.cse.frame.Frame_ChatServerCreate;
import kr.ac.itc.cms.cse.frame.Frame_MessageBox;

public class Client_Main {

	public static Socket socket;
	private DataOutputStream out;

	public Client_Main() {
	}

	public Client_Main(String ip, int port) {
			Thread t = new Thread() {
				public void run() {
					try {
						socket = new Socket(ip, 7575);
						Sender(port);
					} catch (ConnectException e) {
						System.out.println("접속실패");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
	}

	public Client_Main(String ip, String id) {
		Thread t = new Thread() {
			public void run() {
		try {
			socket = new Socket(ip, 7575);
			Sender();
		} catch (ConnectException e) {
			System.out.println("접속실패");
		} catch (Exception e) {
			e.printStackTrace();
		}
			}
		};
		t.start();
	}

	public void Sender(int port) {
		try {
			new Frame_MessageBox("메세지", "     다시 접속해주세요!     ");
			out = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.writeUTF("null" + "!@#" + "null" + "!@#" + port + "!@#" + "open");

		} catch (IOException e) {
			System.out.println("예외:" + e);
		}
	}
	
	public void Sender() {
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.writeUTF("null" + "!@#" + "null" + "!@#" + "null" + "!@#" + "null");

		} catch (IOException e) {
			System.out.println("예외:" + e);
		}
	}

	public void Sender(String id) {
		try {
			new Frame_MessageBox("메세지", "         서버 생성완료!         ");
			out = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.writeUTF(id + "!@#" + Frame_ChatServerCreate.txt_chatname.getText() + "!@#" + "0"
					+ "!@#" + "create");
		} catch (IOException e) {
			System.out.println("예외:" + e);
		}

	}

}
