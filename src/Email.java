import java.util.ArrayList;
import java.util.Date;

public class Email {
    private String uuid;
    private Date date;
    private String from;
    private ArrayList<String> to;
    private String subject;
    private String text;

    public Email(String uuid, Date date, String from, ArrayList<String> to, String subject, String text) {
        this.uuid = uuid;
        this.date = date;
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
