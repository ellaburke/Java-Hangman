import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerTest  {
	
	public ServerTest() throws IOException {
	
	   // Objects will be automatically closed using try-with-resources
       try (ServerSocket server= new ServerSocket(3005)) {
    	  
    	  System.out.println("Waiting for client connection.. ");
       
    	  while (true) {
    	   	// Client has connected, store reference to socket
    	   	Socket clientSocket = server.accept();

		   try
		   (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		   Scanner scanner = new Scanner(System.in)) {
		  
           String action;

           // Start game
           do {
               System.out.println("Enter a word for this game: ");
               String actualWord = scanner.nextLine();
               System.out.println("Starting a new game with the word: " + actualWord);
               out.println("Game ready, you have 5 attempts to guess letters in the word ..");

               int guessesRemaining = 5;
               int guessesUsed = 0;
              
               while (true) {
                   if (guessesUsed < 5) {
                       out.println("GuessLetter");
                       String guessedLetter = in .readLine();
                       System.out.println("Client guessed the letter: " + guessedLetter);
                      // for(int i = 0; i < actualWord.length(); i++) {
                       if (actualWord.indexOf(guessedLetter) >= 0) {
                           //out.println("Word contains (" + guessedLetter + ") at position " + (actualWord.indexOf(guessedLetter) + 1));
                    	   out.println("Word contains " + guessedLetter );
                           out.println(actualWord.replaceAll("[^" + guessedLetter + "]", "_ "));
                          
                       System.out.println("Word contains (" + guessedLetter + ") at position " + (actualWord.indexOf(guessedLetter) + 1));
                       } else {
                           out.println("Word does not contain (" + guessedLetter + ")");
                           System.out.println("Word does not contain (" + guessedLetter + ")");
                       }
                    
                       out.println("You have " + (guessesRemaining - (guessesUsed + 1)) + " attempts remaining");
                       System.out.println("Client has " + (guessesRemaining - (guessesUsed + 1)) + " attempts remaining");
                       } else if (guessesUsed == 5) {
                       out.println("GuessWord");
                       break;
                   }
                   guessesUsed++;
               }
               
               String guessedWord = in .readLine();
               System.out.println("Client guessed the word: " + guessedWord);

               if (guessedWord.equals(actualWord)) {
                   out.println("Correct!");
                   System.out.println("Client guessed the correct word!");
               } else {
                   out.println("Incorrect!");
                   out.println("The actual word was: " + actualWord);
                   System.out.println("Client guessed an incorrect word!");
               }

               out.println("NewGame");
               action = in .readLine();
           } while (action.toLowerCase().equals("y")); // Client wants to play game again

              System.out.println("Client exited game.");
              break;
		   }
    	  
       }
   }
       

   System.out.println("Shutting down server.");
     }

		public static void main(String[] args) throws IOException {
		ServerTest server = new ServerTest();

		}
}
