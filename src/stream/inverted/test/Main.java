package stream.inverted.test;
import stream.inverted.InvertedInputStream;
import stream.inverted.InvertedOutputStream;

import java.io.IOException;



public class Main {
	public static void main(String[] args) {
		InvertedOutputStream out = new InvertedOutputStream(System.out);
		InvertedInputStream in = new InvertedInputStream(System.in);
		byte [] b = "TESTE!!!!".getBytes();
		try {
			out.write(b);
			System.out.println();
			out.flush();
			byte[] t = new byte[255];
			System.out.println("Digite o seu nome:");
			int size = in.read(t);
			System.out.println("Invertido pelo input");
			for (int i = 0; i < size; i++) {
				if(t[i] != 0){
					System.out.print((char)t[i]);
				}
			}
			System.out.println();
			System.out.println("invertendo a inverção do input pelo output.");
			out.write(t, 0, size);
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
