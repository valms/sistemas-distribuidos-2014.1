package stream.cypher;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import util.StringUtils;

public class CypherReader extends Reader {
	private InputStreamReader in;

	public CypherReader(InputStreamReader in) {
		this.in = in;
	}

	@Override
	public int read() throws IOException {
		int caracter = in.read();
		if (Character.isLetter(caracter)) {
			if (StringUtils.isVowel((char) caracter)) {
				if ((char) caracter == 'a') {
					caracter = 'u';
				} else if ((char) caracter == 'A') {
					caracter = 'U';
				} else {
					do {
						caracter--;
					} while (!StringUtils.isVowel((char) caracter));
				}
			} else {
				if ((char) caracter == 'b') {
					caracter = 'z';
				} else if ((char) caracter == 'B') {
					caracter = 'Z';
				} else {
					do {
						caracter--;
					} while (StringUtils.isVowel((char) caracter));
				}
			}
		}
		return caracter;
	}

	@Override
	public int read(char[] buffer) throws IOException {
		int size = lerInputStream(buffer);
		return size;
	}

	@Override
	public int read(char[] b, int off, int len) throws IOException {
		if (off < len) {
			char[] t = new char[b.length];
			lerInputStream(t);
			for (int i = 0; i < len; i++) {
				b[i + off] = t[i];
			}
		}
		return len + off;
	}

	private int lerInputStream(char[] b) throws IOException {
		int n = 0, size = 0;
		while (n != 10 && n != 13) {
			n = read();
			if (n != 10 && n != 13) {
				b[size++] = (char) n;
			}
		}
		return size;
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

}