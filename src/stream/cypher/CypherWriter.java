package stream.cypher;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import util.StringUtils;

public class CypherWriter extends Writer {
	private OutputStreamWriter out;

	public CypherWriter(OutputStreamWriter out) {
		this.out = out;
	}

	@Override
	public void write(int caracter) throws IOException {
		if (Character.isLetter(caracter)) {
			if (StringUtils.isVowel((char) caracter)) {
				if ((char) caracter == 'u') {
					caracter = 'a';
				} else if ((char) caracter == 'U') {
					caracter = 'A';
				} else {
					do {
						caracter++;
					} while (!StringUtils.isVowel((char) caracter));
				}
			} else {
				if ((char) caracter == 'z') {
					caracter = 'b';
				} else if ((char) caracter == 'Z') {
					caracter = 'B';
				} else {
					do {
						caracter++;
					} while (StringUtils.isVowel((char) caracter));
				}
			}
		}
		out.write(caracter);

	}

	@Override
	public void write(String string) throws IOException {
		byte[] b = string.getBytes();
		char[] c = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = (char) b[i];
		}
		write(c, 0, string.length());
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		write(cbuf, 0, cbuf.length);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (int i = off; i < len; i++) {
			write(cbuf[i]);
		}
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}

}