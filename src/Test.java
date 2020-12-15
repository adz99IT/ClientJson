import ComunicationObjects.*;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

//ClientListener si mette in attesa di connessioni.
public class Test extends Thread {

    public static void main(String[] args) {
        ThreadGroup clients;
        ObjectOutputStream outStream;
        ObjectInputStream inStream;
        Socket s = null;


        try {
            try {
                s = new Socket("poggivpn.ddns.net", 8189);
            }catch(ConnectException e){
                System.out.println("Server offline");
                return;
            }
            outStream = new ObjectOutputStream(s.getOutputStream());
            inStream = new ObjectInputStream(s.getInputStream());

            switch(4){
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
                    outStream.writeObject((new RequestSendEmail(new Login("michelefiorelli@progtre.it", "c1be5c740e77452a4970bd2ab3cf78ea09158e5266e8fc5be005a274ce86003a"), a,"News", "Iscriviti per l migliori news")));
                    ReplySendEmail r = (ReplySendEmail)inStream.readObject();
                    if( r.getExitCode() == 1 )
                        System.out.println("Mail inviata a tutti");
                    else if(r.getExitCode() == -1)
                        System.out.println("login fallito");
                    else if(r.getExitCode() == -2) {
                        System.out.println("Invio fallito per i seguenti indirizzi:");
                        if(r.getNotDelivered() != null)
                        for (String st : r.getNotDelivered()){
                            System.out.println(st+" - ");
                        }
                    }
                    else if(r.getExitCode() == -3) {
                        System.out.println("Invio fallito per tutti gli indirizzi.");
                    }
                    break;

                case 3:
                    ArrayList<UUID> b = new ArrayList<>();
                    b.add(UUID.fromString("ce3f88f7-555b-4c97-991d-faa0b6a553bf"));
                    b.add(UUID.fromString("3aff8f2c-4068-4a0e-b472-54f50079c742"));
                    outStream.writeObject(new RequestEmailCancellation("alessandrodizitti@progtre.it", "3f557ea4f0c3fec215ff5cf0727884113757c477f1a3d7f60d7fa623df00ccf0", b));
                    ReplyEmailCancellation rr = (ReplyEmailCancellation)inStream.readObject();
                    if( rr.getExitCode() == 1 )
                        System.out.println("Tutte le mail eliminate");
                    else if(rr.getExitCode() == -1)
                        System.out.println("login fallito");
                    else if(rr.getExitCode() == -2) {
                        System.out.println("Solo le seguenti mail eliminate:");
                        if(rr.getDeleted() != null)
                            for (UUID id : rr.getDeleted()){
                                System.out.println(id+" - ");
                            }
                    }
                    else if(rr.getExitCode() == -3) {
                        System.out.println("Nessuna email eliminata.");
                    }
                    break;

                case 4:
                    Long l = 1607888580000L;
                    Date d = new Date(l);
                    outStream.writeObject(new RequestDownloadEmail("alessandrodizitti@progtre.it","3f557ea4f0c3fec215ff5cf0727884113757c477f1a3d7f60d7fa623df00ccf0", d));
                    ReplyDownloadEmail z = (ReplyDownloadEmail)inStream.readObject();
                    if(z.getExitCode() == 1){
                        if(z.getEmails() == null || z.getEmails().size() < 1)
                            System.out.println("Nessuna nuova email disponibile");
                        else{
                            for (Email e : z.getEmails()){
                                System.out.println("\n----------------");
                                System.out.println("Data: "+e.getDate());
                                System.out.println("Da: "+e.getFrom());
                                System.out.println("Subject: "+e.getSubject());
                                System.out.println("TEXT:\n "+e.getText());
                            }
                        }
                    }else if(z.getExitCode() == -1)
                        System.out.println("login fallito");
                    else // -3
                        System.out.println("Errore");
                    break;
            }
            s.close();


        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

