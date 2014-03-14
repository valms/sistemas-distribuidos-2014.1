package stream.inverted;

import java.io.IOException;
import java.io.InputStream;

import util.ArrayUtils;

public class InvertedInputStream extends InputStream{
	private InputStream in;
	
	public InvertedInputStream(InputStream src){
		in = src;
	}
	
	@Override
	public int read() throws IOException {
		return in.read();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		int n = 0, size = 0;
		while(n != 10 && n != 13){
			n = read();
			if(n != 10 && n != 13){
				b[size++] = (byte) n;
			}
		}
		ArrayUtils.invertArray(b, size);
		return size;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if(off < len){
			byte[] t = new byte[b.length];
			int n = 0, size = 0;
			while(n != 10 && n != 13){
				n = read();
				if(n != 10 && n != 13){
					t[size++] = (byte) n;
				}
			}
			ArrayUtils.invertArray(t, size);
			for (int i = 0; i < len; i++) {
				b[i+off] = t[i];
			}
		}
		return len+off;
	}

	@Override
	public void close() throws IOException {
		in.close();
		super.close();
	}
}
