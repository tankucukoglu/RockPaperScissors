import java.io.*;
import java.net.*;

public class RockPaperScissors{
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int port;
    private final InetAddress address;
    
    public RockPaperScissors(InetAddress address, int port){
        this.address = address;
        this.port = port;
    }
    
    private void openConnection() throws IOException{
        
        System.out.println("Starting the server at port: " + port);
        System.out.println("Server IP Address: " + address.toString());
        
        serverSocket = new ServerSocket(port, 50, address); // default backlog is 50
        
        System.out.println("Waiting for client...");
        
        serverSocket.accept();
        
        // listening for connection...
    }
    private void clientConnect() throws UnknownHostException, IOException{
        
        System.out.println("Attempting to connect to: " + address.toString() + ":" + port);
        
        clientSocket = new Socket(address, port);
        
        System.out.println("Connection established.");
        
        // do read and write
        clientWrite(clientSocket);
        //serverRead();
        //serverWrite(clientSocket);
        clientRead();
    }
    
    private void clientWrite(Socket socket) throws IOException{
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        writer.write("Client to server");
        writer.flush();
        writer.close();
    }
    private void serverRead() throws IOException{
        
        String userInput;
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        System.out.println("Response from client: ");
        while((userInput = input.readLine()) != null){
            System.out.println(userInput);
        }
    }
    private void serverWrite(Socket socket) throws IOException{
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        writer.write("Server to client");
        writer.flush();
        writer.close();
        
    }
    private void clientRead() throws IOException{
        
        String userInput;
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        System.out.println("Response from server: ");
        while((userInput = input.readLine()) != null){
            System.out.println(userInput);
        }
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
                
                rpsClient.clientConnect();
                
                // loop
                rpsClient.clientWrite(null);
                rpsClient.clientRead();
                
            }catch(UnknownHostException e){
                System.out.println(e.getMessage());
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
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

                rpsServer.openConnection();
                
                // loop
                rpsServer.serverRead();
                rpsServer.serverWrite(null);
                
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Invalid arguments!");
        }
    }
}