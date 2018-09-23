package sample.Server_Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String [] args) throws IOException {
        ServerSocket server= new ServerSocket(5005);

        while(true){
            Socket clientConnection=null;

            try{

                clientConnection = server.accept();
                DataInputStream dios =new DataInputStream(clientConnection.getInputStream());
                DataOutputStream dos= new DataOutputStream(clientConnection.getOutputStream());

                Thread t = new clientHandler(server, dios, dos);
                t.start();
            }
            catch(Exception e) {
                clientConnection.close();
                e.printStackTrace();

            }

        }
    }

}
