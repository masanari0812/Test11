package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;

public class ClientManager extends Thread {

	private Socket socket;
	private PlayGameScreen pgs;
	private int num=0;
	
	public ClientManager(PlayGameScreen pgs) {
		this.pgs = pgs;
		pgs.setOnlineMgr(this);
	}

	@Override
	public void run() {
		try {
			System.out.println(num++);
			DatagramSocket handShakeSocket = new DatagramSocket(1182);
			byte[] receiveData = new byte[InetAddress.getLocalHost().getAddress().length];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			handShakeSocket.receive(receivePacket);
			System.out.println(num++);
			byte[] receivedData = receivePacket.getData();
			InetAddress localhost = InetAddress.getByAddress(receivedData);
			handShakeSocket.close();
			System.out.println(num++);
			System.out.println(localhost.getHostAddress());
			this.socket = new Socket(localhost, 8782);
			if (socket.isConnected()) {
				System.out.println(num++);
				pgs.setHost(false);
				pgs.setObjectInputStream(socket.getInputStream());
				pgs.setObjectOutputStream(socket.getOutputStream());
				System.out.println(num++);
				pgs.makeBoard();
			}else
				System.out.println("x");
			System.out.println(num++);
			while (pgs.getOnline())
				;
			socket.close();
			this.interrupt();
		} catch (IOException e) {
			System.out.println(num+"!");
			e.printStackTrace();
		}
	}
}
