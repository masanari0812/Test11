package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;

public class ServerManager extends Thread {

	private Socket socket;
	private PlayGameScreen pgs;
	private int num = 0;

	public ServerManager(PlayGameScreen pgs) {
		this.pgs = pgs;
		pgs.setOnlineMgr(this);
	}

	@Override
	public void run() {
		try {
			System.out.println(num++);
			ServerSocket serverSocket = new ServerSocket(8782);
			InetAddress localhost = InetAddress.getLocalHost();
			DatagramSocket handShakeSocket = new DatagramSocket();
			InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
			byte[] sendData = localhost.getAddress();
			DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcastAddress, 1182);
			handShakeSocket.send(packet);
			handShakeSocket.close();
			serverSocket.setSoTimeout(2500);
			System.out.println(num++);
			this.socket = serverSocket.accept();
			System.out.println(num++);
			serverSocket.close();
			if (socket.isConnected()) {
				System.out.println(num++);
				if(pgs==null)
					System.out.println("err");
				pgs.setHost(true);
				
				pgs.setObjectInputStream(socket.getInputStream());
				System.out.println(num++);
				pgs.setObjectOutputStream(socket.getOutputStream());
				System.out.println(num++);
				pgs.makeBoard();
			} else
				System.out.println("x");
			while (pgs.getOnline())
				;
			System.out.println(num++);
			socket.close();
			this.interrupt();
		} catch (SocketTimeoutException e) {
			System.out.println(num+"!");
			ClientManager cm = new ClientManager(pgs);
			pgs.setOnlineMgr(cm);
			cm.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
