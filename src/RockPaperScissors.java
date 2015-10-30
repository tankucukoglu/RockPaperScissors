import java.io.*;
import java.net.*;

// default backlog is 50

public class RockPaperScissors{
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int port;
    private final InetAddress address;
    
    public RockPaperScissors(InetAddress address, int port){
        this.address = address;
        this.port = port;
    }
    
    public void start() throws IOException{
        System.out.println("Starting the server at port: " + port);
        System.out.println("Server IP Address: " + address.toString());
        serverSocket = new ServerSocket(port, 50, address);
        
        System.out.println("Waiting for client...");
        
        Socket client = serverSocket.accept();
        
        play(client);
    }
    public void connect() throws UnknownHostException, IOException{
        
        System.out.println("Attempting to connect to: " + address.toString() + ":" + port);
        clientSocket = new Socket(address, port);
        System.out.println("Connection established");
    }
    
    private void play(Socket client) throws IOException{
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        
        writer.write("Client connected to server");
        writer.flush();
        writer.close();
        
    }
    
    public static void main(String[] args){
        
        InetAddress ipAddress;
        int portNumber;
        
        if(args.length == 2){
            
             // client
            
            try{
               ipAddress = InetAddress.getByName(args[0]);
            }catch(UnknownHostException e){
                System.out.println(e.getMessage());
                ipAddress = null;
            }
            portNumber = Integer.parseInt(args[1]);
            
            try{
                RockPaperScissors rpsClient = new RockPaperScissors(ipAddress, portNumber);
                
                rpsClient.connect();
            }catch(UnknownHostException e){
                System.out.println(e.getMessage());
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            // client code ********************************
        }
        else if(args.length == 1){
            
            // server
            portNumber = Integer.parseInt(args[0]);
            try{
                ipAddress = InetAddress.getByName("127.0.0.1");
            }catch(UnknownHostException e){
                System.out.println(e.getMessage());
                ipAddress = null;
            }
            
            try{
                RockPaperScissors rpsServer = new RockPaperScissors(ipAddress, portNumber);

                rpsServer.start();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Invalid arguments!");
        }
        
    }
}