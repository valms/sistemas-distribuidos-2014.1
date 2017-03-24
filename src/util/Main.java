package util;

import java.net.InetAddress;

public class Main {

	
	public static void main(String[] args) throws Exception {
		
		InetAddress [] localaddr = InetAddress.getAllByName("facebook.com");
		
		for (InetAddress inetAddress : localaddr) {
			System.out.println(inetAddress.getHostAddress());
		}
		System.out.println("Quantidade:");
		System.out.println(localaddr.length);
		System.exit(0);
		
		
	}

}
