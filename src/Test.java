import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

//ClientListener si mette in attesa di connessioni.
public class Test extends Thread {



    public static void main(String[] args) {
        ThreadGroup clients;
        ObjectInputStream inStream;
        ObjectOutputStream outStream;

        try {
            System.out.println("A");
            Socket s = new Socket("dizitti.ddns.net",8189);
            System.out.println("ok");
            inStream = new ObjectInputStream(s.getInputStream());
            outStream = new ObjectOutputStream(s.getOutputStream());

            System.out.println("Scrivo");
            outStream.writeObject(new Date());
            System.out.println("Fatto");
            s.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

