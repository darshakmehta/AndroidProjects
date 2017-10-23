package mad.com.listviewemail;

public class Email {
    String subject, summary, sender;

    public Email(String subject, String summary, String sender) {
        this.subject = subject;
        this.summary = summary;
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Email{" +
                "subject='" + subject + '\'' +
                ", summary='" + summary + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
