import ComunicationObjects.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

//ClientListener si mette in attesa di connessioni.
public class Test extends Thread {

    public static void main(String[] args) {
        ThreadGroup clients;
        ObjectOutputStream outStream;
        ObjectInputStream inStream;


        try {
            Socket s = new Socket("poggivpn.ddns.net",8189);
            outStream = new ObjectOutputStream(s.getOutputStream());
            inStream = new ObjectInputStream(s.getInputStream());

            switch(2){
                case 1:
                    System.out.println("Scrivo");
                    outStream.writeObject(new Login("michelefiorelli@progtre.it", "c1be5c740e77452a4970bd2ab3cf78ea09158e5266e8fc5be005a274ce86003a"));
                    System.out.println("Fatto");

                    Object obj = inStream.readObject();
                    if(obj instanceof ReplyLogin){
                        if(((ReplyLogin)obj).getExitCode() > 0)
                            System.out.println("Loggato");
                        else
                            System.out.println("NON Loggato");
                    }
                    break;

                case 2:
                    ArrayList<String> a = new ArrayList<>();
                    a.add("alessandrodizitti@progtre.it");
                    a.add("michelefiorelli@progtre.it");
                    outStream.writeObject((new RequestSendEmail(new Login("michelefiorelli@progtre.it", "c1be5c740e77452a4970bd2ab3cf78ea09158e5266e8fc5be005a274ce86003a"), a,"Ciao!", "Come va? Test Prog3")));
                    System.out.println( " "+((ReplySendEmail)inStream.readObject()).getExitCode() ) ;
                    break;
            }
            s.close();


        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

