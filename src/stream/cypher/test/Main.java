package stream.cypher.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import stream.cypher.CypherReader;
import stream.cypher.CypherWriter;

public class Main {
	public static void main(String[] args) {
		OutputStreamWriter out = new OutputStreamWriter(System.out);
		InputStreamReader in = new InputStreamReader(System.in);
		CypherWriter cw = new CypherWriter(out);
		CypherReader cr = new CypherReader(in);
		try {
			char [] nome = new char[] {'A','r','g','e','u'};
			cw.write(nome);
			cw.flush();
			System.out.println();
			cw.write(nome, 0, 3);
			cw.flush();
			System.out.println();
			cw.write("Argeu Aprigio Alcantara");
			cw.flush();
			System.out.println();
			cw.write("Thiago Freitas da Ponte");
			cw.flush();
			System.out.println();
			cw.write("Daniel 'rapier' Santiago");
			cw.flush();
			System.out.println();
			System.out.println("'SAY MY NAME!!!' by Xemvis Xjovi: ");
			char[] b = new char[255];
			int size = cr.read(b);
			for (int i = 0; i < size; i++) {
				if(b[i] != 0){
					System.out.print((char)b[i]);
				}else{
					System.out.print(b[i]);
				}
			}
			System.out.println();
			cw.close();
			cr.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
