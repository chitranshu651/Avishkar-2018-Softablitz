package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;

public class clientHandler extends Thread{

    ServerSocket server;
    DataOutputStream dos;
    DataInputStream dios;

    public clientHandler(ServerSocket server, DataInputStream dios , DataOutputStream dos){
        this.server=server;
        this.dos= dos;
        this.dios=dios;
    }

    @Override
    public void start(){

    }
}
