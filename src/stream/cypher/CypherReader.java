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
		int c = in.read();
		if(Character.isLetter(c)){
			if(StringUtils.isVowel((char)c)){
				if((char)c == 'a'){
					c = 'u';
				}else if((char)c == 'A'){
					c = 'U';
				}else{
					do{
						c--;
					}while(!StringUtils.isVowel((char)c));
				}
			}else{
				if((char)c == 'b'){
					c = 'z';
				}else if((char)c == 'B'){
					c = 'Z';
				}else{
					do{
						c--;
					}while(StringUtils.isVowel((char)c));
				}
			}
		}
		/*if((c > 64 && c < 91) || (c > 96 && c < 123)){
			switch(c){
				case 69:
					c = (65);break;
				case 70:
					c = (68);break;
				case 73:
					c = (69);break;
				case 72:
					c = (74);break;
				case 79:
					c = (73);break;
				case 80:
					c = (78);break;
				case 85:
					c = (79);break;
				case 65:
					c = (85);break;
				case 86:
					c = (84);break;
				case 66:
					c = (90);break;
				case 101:
					c = (97);break;
				case 102:
					c = (100);break;
				case 105:
					c = (101);break;
				case 111:
					c = (105);break;
				case 106:
					c = (104);break;
				case 117:
					c = (111);break;
				case 97:
					c = (117);break;
				case 118:
					c = (116);break;
				case 112:
					c = (110);break;
				case 98:
					c = (122);break;
				default:
					c = (c-1);
			}
		}*/
		return c;
	}
	
	@Override
	public int read(char[] b) throws IOException {
		int size = lerInputStream(b);
		return size;
	}
	
	@Override
	public int read(char[] b, int off, int len) throws IOException {
		if(off < len){
			char[] t = new char[b.length];
			lerInputStream(t);
			for (int i = 0; i < len; i++) {
				b[i+off] = t[i];
			}
		}
		return len+off;
	}
	
	private int lerInputStream(char[] b) throws IOException {
		int n = 0, size = 0;
		while(n != 10 && n != 13){
			n = read();
			if(n != 10 && n != 13){
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