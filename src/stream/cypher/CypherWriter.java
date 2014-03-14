package stream.cypher;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import util.StringUtils;

public class CypherWriter extends Writer{
	private OutputStreamWriter out;
	
	public CypherWriter(OutputStreamWriter out){
		this.out = out;
	}
	
	@Override
	public void write(int c) throws IOException {
		if(Character.isLetter(c)){
			if(StringUtils.isVowel((char)c)){
				if((char)c == 'u'){
					c = 'a';
				}else if((char)c == 'U'){
					c = 'A';
				}else{
					do{
						c++;
					}while(!StringUtils.isVowel((char)c));
				}
			}else{
				if((char)c == 'z'){
					c = 'b';
				}else if((char)c == 'Z'){
					c = 'B';
				}else{
					do{
						c++;
					}while(StringUtils.isVowel((char)c));
				}
			}
		}
		out.write(c);
		/*if((c > 64 && c < 91) || (c > 96 && c < 123)){
			switch(c){
				case 65:
					out.write(69);break;
				case 68:
					out.write(70);break;
				case 69:
					out.write(73);break;
				case 72:
					out.write(74);break;
				case 73:
					out.write(79);break;
				case 78:
					out.write(80);break;
				case 79:
					out.write(85);break;
				case 85:
					out.write(65);break;
				case 84:
					out.write(86);break;
				case 90:
					out.write(66);break;
				case 97:
					out.write(101);break;
				case 100:
					out.write(102);break;
				case 101:
					out.write(105);break;
				case 105:
					out.write(111);break;
				case 104:
					out.write(106);break;
				case 111:
					out.write(117);break;
				case 117:
					out.write(97);break;
				case 116:
					out.write(118);break;
				case 110:
					out.write(112);break;
				case 122:
					out.write(98);break;
				default:
					out.write(c+1);
			}
		}else{
			out.write(c);
		}*/
	}
	
	@Override
	public void write(String str) throws IOException {
		byte [] b = str.getBytes();
		char [] c = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = (char) b[i];
		}
		write(c, 0, str.length());
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