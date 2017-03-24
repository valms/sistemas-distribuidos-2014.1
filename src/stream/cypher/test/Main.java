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
			
			// VOGAIS: A E I O U
			//CONSOANTES: B C D F G H J K L M N P Q R S T V W Y X Z
			
			char [] nome = new char[] {'V','a','l','m','a', 'R'};
			cw.write(nome);
			cw.flush();
			System.out.println();
			cw.write(nome, 0, 3);
			cw.flush();
			System.out.println();
			cw.write("Rebeca Mendes Feitoza");
			cw.flush();
			System.out.println();
			cw.write("Francisco Valmar Isaias Silva Júniro");
			cw.flush();
			System.out.println();
			cw.write("Jouderian");
			cw.flush();
			System.out.println();
			System.out.println("'SAY MY NAME!!!' Bitch ");
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
