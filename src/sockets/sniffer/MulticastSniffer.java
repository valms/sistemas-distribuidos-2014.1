package sockets.sniffer;

import java.net.*;
import java.io.*;

public class MulticastSniffer {
	public static void main(String[] args) {
		InetAddress group = null;
		int port = 0;
		// L� o endere�o do grupo (IP + porta) da linha de comando
		try {
//			group = InetAddress.getByName(args[0]);
//			port = Integer.parseInt(args[1]);
			
			group = InetAddress.getByName("all-systems.mcast.net");
			port = Integer.parseInt("60000");
			
		} catch (Exception e) {
			// Erro na leitura dos argumentos ou endere�o inv�lido
			System.err.println("Uso: java MulticastSniffer endere�o porta");
			System.exit(1);
		}

		MulticastSocket ms = null;
		
		File file = new File("src"+File.separator+"resources"+File.separator+"snifferOutput.txt");
		FileWriter fw = null;
		try {
			file.createNewFile();
			fw = new FileWriter(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			// Cria um socket associado ao endere�o do grupo
			ms = new MulticastSocket(port);
			ms.joinGroup(group);
			// Cria uma área de dados para receber conte�do dos pacotes
			byte[] buffer = new byte[80];
			// Laço para recebimento de pacotes e impress�o do conte�do
			while (true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);
				String s = new String(dp.getData());
				System.out.println(s);
				fw.write(s+"\n");
				fw.flush();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		// Em caso de erro ou interrup��o do programa,
		// sinaliza saída do grupo e fecha socket
		finally {
			if (ms != null) {
				try {
					fw.close();
					ms.leaveGroup(group);
					ms.close();
				} catch (IOException e) {
				}
			} // if
		} // finally
	} // main
} // class
