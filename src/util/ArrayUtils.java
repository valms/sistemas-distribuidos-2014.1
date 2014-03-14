package util;

public class ArrayUtils {
	public static byte[] invertArray(byte[] origem, int size){
		byte b = 0;
		for (int i = 0; i < size/2; i++) {
			b = origem[i];
			origem[i] = origem[size-i-1];
			origem[size-i-1] = b;
		}
		return origem;
	}
	public static byte[] invertArray(byte[] origem){
		return invertArray(origem, origem.length);
	}
}
