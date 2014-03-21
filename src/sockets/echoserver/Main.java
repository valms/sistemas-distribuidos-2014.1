package sockets.echoserver;

import java.util.concurrent.Semaphore;


public class Main {
	public static Semaphore s = new Semaphore(2);
	
	public static void main(String[] args) {
		try {
			s.acquire(1);
			new Thread(new Runnable() {
				public void run() {
					EchoServer.start();
					s.release(1);
				}
			}).start();
			s.acquire(1);
			new Thread(new Runnable() {
				public void run() {
					Client.start();
					s.release(1);
				}
			}).start();
			s.acquire(2);
			s.release(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}