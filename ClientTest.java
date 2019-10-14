import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTest {
	
	public ClientTest() throws UnknownHostException, IOException{
		
		System.out.println("Welcome to the word guessing game!");
        System.out.println("Please wait while the server determines a word ..");
		
        
		try(
		     Socket socket = new Socket("localhost", 3005);
		     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     Scanner scanner = new Scanner(System.in)){
			
			 // Infinite loop to read messages from server
            while (true) {
                // Read messages from server
                String serverMessage = in .readLine();

                // Conditional statements, do various actions depending on message received
                if (serverMessage.equals("GuessLetter")) {
                    System.out.println("Guess a letter in the word:");
                    String guessedLetter = scanner.nextLine();
                    out.println(guessedLetter);
                } else if (serverMessage.indexOf("GuessWord") >= 0) {
                    System.out.println("You can now attempt to guess the word ..");
                    String guessedWord = scanner.nextLine();
                    out.println(guessedWord);
                } else if (serverMessage.equals("NewGame")) {
                    String action;
                    System.out.println("Would you like to play another game? Type Y or y to play again.");
                    action = scanner.nextLine();
                    out.println(action);
                    if (action.toLowerCase().equals("y")) {
                        System.out.println("Please wait while the server determines a word ..");
                    } else {
                        System.out.println("Hope you enjoyed playing, bye!");
                        // Break out of infinite loop so application/client will close
                        break;
                    }
                    
                } else {
                    System.out.println(serverMessage);
                }
            }
        }
    }
		

		public static void main(String[] args) throws UnknownHostException, IOException {

		ClientTest client = new ClientTest();

		}
}

