package sockets.echoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Client {
	public static void start() {
		System.out.println("Subindo cliente...");
		DatagramSocket analisador = null;
		int porta = 60000;
		int pacotes = 1000;
		try {
			
			
			InetAddress localhost = InetAddress.getByName("127.0.0.1");
			DatagramSocket client = new DatagramSocket();
			DatagramPacket pacoteEnviado;
			for (int i = 0; i < pacotes; i++) {
				byte[] dados = Integer.toString(i).getBytes();
				pacoteEnviado = new DatagramPacket(dados, dados.length, localhost, porta);

				client.send(pacoteEnviado);

			}
			analisador = new DatagramSocket(porta + 1);
			client.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] buffer = new byte[1000];
		DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
		int valorAnterior = -1;
		int pacotesRecebidos = 0;
		boolean mudouOrdem = false;
		boolean pulouPacotes = false;
		try {
			analisador.setSoTimeout(5000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				analisador.receive(pacoteRecebido);
				String conteudo = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
				int valor = Integer.parseInt(conteudo);
				if (valor != pacotesRecebidos) {
					pulouPacotes = true;
				}
				if (valor <= valorAnterior) {
					mudouOrdem = true;
				}
				 System.out.println("Pacotes Recebidos: "+pacotesRecebidos);
				// System.out.println("Valor: "+valor);
				pacotesRecebidos++;
				valorAnterior = valor;
			} catch (SocketTimeoutException e) {
				// caso solte um sockettimeoutexception, sai do loop, n�o tem
				// mais pacotes pra receber
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Fechando cliente...");
		analisador.close();
		System.out.println("Pacotes enviados: " + pacotes);
		System.out.println("Pacotes recebidos: " + pacotesRecebidos);
		if (mudouOrdem) {
			System.out.println("Mudou ordem de entrega do recebimento? Sim");
		} else {
			System.out.println("Mudou ordem de entrega do recebimento? N�o");
		}
		if (pulouPacotes) {
			System.out.println("Pulou pacotes no recebimento? Sim");
		} else {
			System.out.println("Pulou pacotes no recebimento? N�o");
		}

	}

	public static void main(String[] args) {
		start();
	}
}
