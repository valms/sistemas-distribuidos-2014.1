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
			InetAddress localhost = InetAddress.getLocalHost();
			DatagramSocket client = new DatagramSocket();
			DatagramPacket pacoteEnviado;

			// gerar pacotes com os valores de i e enviá-los, assim fica fácil
			// testar ordem de recebimento depois
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
		try {
			analisador.setSoTimeout(5000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		// analisar os pacotes enviados pelo servidor de eco
		while (true) {
			try {
				analisador.receive(pacoteRecebido);
			} catch (SocketTimeoutException e) {
				// caso solte um sockettimeoutexception, sai do loop, não tem
				// mais pacotes pra receber
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}

			pacotesRecebidos++;
			String conteudo = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
			int valor = Integer.parseInt(conteudo);
			if (valor <= valorAnterior) {
				mudouOrdem = true;
			}
			valorAnterior = valor;

		}
		System.out.println("Fechando cliente...");
		analisador.close();
		System.out.println("Pacotes enviados: " + pacotes);
		System.out.println("Pacotes recebidos: " + pacotesRecebidos);
		if (mudouOrdem) {
			System.out.println("Mudou ordem de entrega do recebimento? Sim");
		} else {
			System.out.println("Mudou ordem de entrega do recebimento? Não");
		}

	}
	
	public static void main(String[] args) {
		start();
	}
}
