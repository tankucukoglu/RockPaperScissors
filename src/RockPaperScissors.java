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
        
        int portNumber = 8080;
        
        try{
            RockPaperScissors rps = new RockPaperScissors(portNumber);
            
            rps.start();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}