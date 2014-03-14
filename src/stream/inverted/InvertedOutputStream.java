package stream.inverted;
import java.io.IOException;
import java.io.OutputStream;


public class InvertedOutputStream extends OutputStream {
	private OutputStream out;
	
	public InvertedOutputStream(OutputStream dest){
		out = dest;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		for (int i = 0; i < b.length; i++) {
			write(b[b.length - i - 1]);
		}
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		for (int i = 0; i < len; i++) {
			write(b[off+len-i-1]);
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
