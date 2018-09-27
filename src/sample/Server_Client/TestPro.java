package sample.Server_Client;

import java.io.IOException;
import java.net.ConnectException;

public class TestPro {

    public static void main(String[] args){
            Client test= new Client("127.0.0.1",5005);
            test.sendString("Login");
            test.sendString("Signup");
            test.sendString("Exit");
        System.out.println(test.recieveString());
        System.out.println(test.recieveString()  );

    }
}
