package sockets.sniffer;

import java.net.*;
import java.io.*;

public class MulticastSniffer {
	public static void main(String[] args) {
		InetAddress group = null;
		int port = 0;
		// Lê o endereço do grupo (IP + porta) da linha de comando
		try {
//			group = InetAddress.getByName(args[0]);
//			port = Integer.parseInt(args[1]);
			
			group = InetAddress.getByName("all-systems.mcast.net");
			port = Integer.parseInt("60000");
			
		} catch (Exception e) {
			// Erro na leitura dos argumentos ou endereço inválido
			System.err.println("Uso: java MulticastSniffer endereço porta");
			System.exit(1);
		}

		MulticastSocket ms = null;
		try {
			// Cria um socket associado ao endereço do grupo
			ms = new MulticastSocket(port);
			ms.joinGroup(group);
			// Cria uma Ã¡rea de dados para receber conteúdo dos pacotes
			byte[] buffer = new byte[80];
			// LaÃ§o para recebimento de pacotes e impressão do conteúdo
			while (true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);
				String s = new String(dp.getData());
				System.out.println(s);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		// Em caso de erro ou interrupção do programa,
		// sinaliza saÃ­da do grupo e fecha socket
		finally {
			if (ms != null) {
				try {
					ms.leaveGroup(group);
					ms.close();
				} catch (IOException e) {
				}
			} // if
		} // finally
	} // main
} // class
