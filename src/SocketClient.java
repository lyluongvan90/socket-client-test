import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SocketClient {
	private String hostname;
	private int port;
	private Socket socket;
	String[] exitCmdArr = {"exit", "quit", "1", "2", "3", "4", "5", "6"};
	public SocketClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(hostname, port);
	}

	public void operateConsoleAction() throws IOException {
		Scanner in = new Scanner(System.in);
        boolean isContinue = true;
        do {
			printGuideLine();
        	String line = in.nextLine();
        	boolean inputValid = validateInput(line);
        	if (inputValid) {
				isContinue = handleValidCommand(line);
			}
        } while(isContinue);
        in.close();
        System.out.println("Quit!");
	}

	private boolean handleValidCommand(String line) throws IOException {
		OutputStream socketOutput = this.socket.getOutputStream();
		boolean isNotExitCmds = !hasInputExitCmd(line);
		if (isNotExitCmds) {
			sendFileContentToSocket(socketOutput, line);
		}
		return isNotExitCmds;
	}

	private void printGuideLine() {
		System.out.println("Choose action: \n");
		int exitCmdArrLength = exitCmdArr.length;
		for (int i = 0; i < exitCmdArrLength - 2; i ++) {
			int index = i + 1;
			System.out.printf("  %d: Read data%d.txt\n", index, index);
		}
		System.out.println("  exit or quit: exit this command");
	}

	private void sendFileContentToSocket(OutputStream socketOutput, String line) throws IOException {
		String inputFile = "data" + line.trim() +".txt";
		FileReader fileReader = new FileReader();
		byte[] allBytesInFirstFile = fileReader.read(inputFile);
        socketOutput.write(allBytesInFirstFile);
        fileReader.close();
        System.out.println("Sent successfully!");
	}

	private boolean hasInputExitCmd(String line) {
		String[] exitCmdArr = {"exit", "quit", "e!", "q!"};
    	List<String> exitCmds = Arrays.asList(exitCmdArr);
    	if (exitCmds.contains(line.trim())) {
    		return true;
    	}
		return false;
	}

	private boolean validateInput(String line) {
    	List<String> validCmds = Arrays.asList(exitCmdArr);
    	if (validCmds.contains(line.trim())) {
    		return true;
    	}
		return false;
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		if (null != socket) socket.close();
	}
}
