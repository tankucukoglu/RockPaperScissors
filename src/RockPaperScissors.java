import java.io.*;
import java.net.*;
import java.util.*;

public class RockPaperScissors{
    
    static void startServer(int portNumber){
        
        while(true){
        	
	        int tie = 0, client = 0, server = 0;
	        int rounds = 0;
        
	        try{
	        	
	            ServerSocket serverSocket = new ServerSocket(portNumber); 
	            Socket socket = serverSocket.accept();
            
	            BufferedReader ed = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            String tmp = ed.readLine();
	            System.out.println("Server Recieved :" + tmp);
	            
	            // Receive message (SHAPES) and parse
	            String delims = "/";
	            String[] tokens = tmp.split(delims);
	            rounds = Integer.parseInt(tokens[0]);
            
	            String[] userEntries = tmp.split(delims);
	            for(int i = 1; i < (rounds+1); i++){
	                userEntries[i - 1] = tokens[i];
	            }
            
            
	            // Creates random SHAPES for server and stores them in an array
	            Random rg = new Random();
	            int[] randomArray = new int [rounds];
	            for(int i = 0; i < rounds; i++){
	                randomArray[i] = rg.nextInt(3) + 1;
	            }
            
	            String[] programEntries = new String[rounds];
	            for(int i = 0; i < rounds; i++){
	                if(randomArray[i] == 1)
	                    programEntries[i] = "rock";
	                else if (randomArray[i] == 2)
	                    programEntries[i] = "paper";
	                else if (randomArray[i] == 3)
	                    programEntries[i] = "scissors";
	            }
	            /* ------------------------------------------------------------ */
	            
	            /* Compares the server array elements w/ client array elements
	               Keeps the results in an array and increases one of rock, paper or scissors */
	            
	            String[] resultArray = new String[rounds];
            
	            for(int i = 0; i < rounds; i++){
	            	
	                if(userEntries[i].toLowerCase().equals("rock")){
	                    if(programEntries[i].equals("rock")){
	                        resultArray[i] = "(tie)";
	                        tie++;
	                    }
	                    else if(programEntries[i].equals("scissors")){
	                        resultArray[i] = "(client wins)";
	                        client++;
	                    }
	                    else if(programEntries[i].equals("paper")){
	                        resultArray[i] = "(server wins)";
	                        server++;
	                    }
	                }
	                else if(userEntries[i].toLowerCase().equals("scissors")){
	                    if(programEntries[i].equals("rock")){
	                        resultArray[i] = "(server wins)";
	                        server++;
	                    }
	                    else if(programEntries[i].equals("scissors")){
	                        resultArray[i] = "(tie)";
	                        tie++;
	                    }
	                    else if(programEntries[i].equals("paper")){
	                        resultArray[i] = "(client wins)";
	                        client++;
	                    }
	                }
	                else if(userEntries[i].toLowerCase().equals("paper")){
	                    if(programEntries[i].equals("rock")){
	                        resultArray[i] = "(client wins)";
	                        client++;
	                    }
	                    else if(programEntries[i].equals("scissors")){
	                        resultArray[i] = "(server wins)";
	                        server++;
	                    }
	                    else if(programEntries[i].equals("paper")){
	                        resultArray[i] = "(tie)";
	                        tie++;
	                    }
	                }
	            }
            
	            /* ------------------------------------------------------------ */
	            
	            PrintStream pr = new PrintStream(socket.getOutputStream());
	            
	            // Prints the (random) choices of server and who wins
	            String msgToClient = "";
	            for(int i = 0; i < rounds; i++){
	                msgToClient += ("Round-" + (i + 1) + ":" + "server chooses " + programEntries[i] + " " + resultArray[i] + "/");
	            }
	            msgToClient += ("Client: " + client + "/" + "Tie: " + tie + "/" + "Server: " + server);
	            pr.println(msgToClient);
	            
	            serverSocket.close();
	            
	        }catch(Exception ex){
	        	System.out.println(ex.getMessage());
	        	
	        }
	    }
    }
    /* ------------------------------------------ */
    
    static void startClient(InetAddress ipAddress, int portNumber){
    
        Scanner scanner = new Scanner(System.in);
        int rounds = 0;
        String roundNumber ="a";
        
        
        while(!roundNumber.toLowerCase().equals("q")){
	        try
	        {
	            Socket sock = new Socket(ipAddress, portNumber);
	            PrintStream pr = new PrintStream(sock.getOutputStream());
	            
	            System.out.println("Enter the number of rounds (Press Q to quit): ");
	            
	            roundNumber = scanner.nextLine();
	            
	            if(roundNumber.toLowerCase().equals("q")){
	                System.exit(0);
	            }else{
	                rounds = Integer.parseInt(roundNumber);
	                if(rounds == 0){
	                    System.out.println("You cannot enter 0. ");
	                }
	            }
	            
	            // Gets shapes from the user and stores them in an array
	            String[] userEntries = new String[rounds];
	            for(int i = 0; i < rounds; i++){
	                System.out.print("Round-" + (i + 1) + ": ");
	                userEntries[i] = scanner.nextLine();
	            }
	            
	            // Message to server
	            String msgToServer = roundNumber;
	            for(int i = 0; i < rounds; i++){
	                msgToServer += '/' + userEntries[i];
	            }
	            
	            InputStream stream = new ByteArrayInputStream(msgToServer.getBytes("UTF-8"));
	            
	            InputStreamReader rd = new InputStreamReader(stream);
	            BufferedReader ed = new BufferedReader(rd);
	            
	            String temp = ed.readLine();
	            
	            pr.println(temp);
	            
	            // Getting the result from server
	            BufferedReader gt = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	            String tm = gt.readLine();
	            String[] tokens = tm.split("/");
	            
	            for(String t : tokens){
	            	System.out.println(t);
	            }
	            
	            sock.close();
	            
	        }
	        
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
        }
        
        scanner.close();
    }

    /*-------------------- MAIN METHOD --------------------*/
    public static void main(String[] args){
        
        InetAddress ipAddress;
        int portNumber;
        
        if(args.length == 2){
            
            // Start Client
            
            try{
                ipAddress = InetAddress.getByName(args[0]);
            }catch(UnknownHostException e){
                System.out.println(e.getMessage());
                ipAddress = null;
            }
            portNumber = Integer.parseInt(args[1]);
            
            try{
                startClient(ipAddress, portNumber);
                
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        }
        else if(args.length == 1){
            
            // Start Server
            
            portNumber = Integer.parseInt(args[0]);
            
            try{
                startServer(portNumber);

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Invalid arguments!");
        }
    }
}