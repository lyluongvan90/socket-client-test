import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader implements AutoCloseable {
	private FileInputStream fileInputStream = null;
	private InputStreamReader inputStreamReader = null;
	private BufferedReader reader = null;
	public byte[] read(String fileName) throws IOException {
		fileInputStream = new FileInputStream(fileName);
		inputStreamReader = new InputStreamReader(fileInputStream);
		reader = new BufferedReader(inputStreamReader);
		
		//Read only first line
        String line = reader.readLine();
        byte[] allBytes = new byte[0];
        if (null != line) {
        	String[] byteValueTexts = line.split(",");
        	allBytes = new byte[byteValueTexts.length];
        	for (int index = 0; index < byteValueTexts.length; index ++) {
        		String byteValueText = byteValueTexts[index];
        		Integer byteValueInt = Integer.parseInt(byteValueText);
        		byte byteValue = byteValueInt.byteValue();
        		allBytes[index] = byteValue;
        	}
        }
    	return allBytes;
	}

	@Override
	public void close() throws IOException {
		if (null != reader) reader.close();
		if (null != inputStreamReader) inputStreamReader.close();
		if (null != fileInputStream) fileInputStream.close();
	}
}
