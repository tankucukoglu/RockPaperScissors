import java.io.*;
import java.net.*;

public class RockPaperScissors{
    
    private ServerSocket serverSocket;
    private final int port;
    
    public RockPaperScissors(int port){
        this.port = port;
    }
    
    public void start() throws IOException{
        System.out.println("Starting the server at port: " + port);
        serverSocket = new ServerSocket(port);
        
        System.out.println("Waiting for client...");
        
        Socket client = serverSocket.accept();
        
        play(client);
    }
    
    private void play(Socket client) throws IOException{
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        
        writer.write("Client connected to server");
        writer.flush();
        writer.close();
        
    }
    
    public static void main(String[] args){
        
        String ipAddress;
        int portNumber;
        
        if(args.length == 2){
            
            // client
            ipAddress = args[0];
            portNumber = Integer.parseInt(args[1]);
            
            // client code ********************************
        }
        else if(args.length == 1){
            
            // server
            portNumber = Integer.parseInt(args[0]);
            
            try{
                RockPaperScissors rps = new RockPaperScissors(portNumber);

                rps.start();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Missing arguments!");
        }
        
    }
}