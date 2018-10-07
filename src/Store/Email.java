package Store;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Email {
    private static Properties getProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    public static void sendInvoice(String to, String fileName) {
        Invoice.generateInvoice();
        String from = "jeffsfishingshack1@gmail.com";

        Properties properties = getProperties();
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jeffsfishingshack1@gmail.com", "pass");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Purchase Invoice");
            BodyPart body = new MimeBodyPart();
            body.setText("View the attachment");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            body = new MimeBodyPart();
            String filename = "C:\\Users\\Azhar\\IdeaProjects\\emailAttachment\\src\\Store\\" + fileName;
            DataSource source = new FileDataSource(fileName);
            body.setDataHandler(new DataHandler(source));
            body.setFileName(fileName);
            multipart.addBodyPart(body);
            message.setContent(multipart);
            Transport.send(message);



        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public static void sendNewsLetter(String fileName,String to){
        String from = "jeffsfishingshack1@gmail.com";
        Properties properties  = getProperties();


        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jeffsfishingshack1@gmail.com", "pass");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Jeffs Fishing Shack Newsletter");
            BodyPart body = new MimeBodyPart();
            body.setText("Newsletter for the month");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            body = new MimeBodyPart();
            fileName = "E:\\Azhar\\IIT\\pp2\\CW\\CW1\\newsletter\\newsletter.pdf";
            DataSource source = new FileDataSource(fileName);
            body.setDataHandler(new DataHandler(source));
            body.setFileName(fileName);
            multipart.addBodyPart(body);
            message.setContent(multipart);
            Transport.send(message);



        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public static void sendEnquiry(String customerName,String customerID,String content) {
        String from = "jeffsfishingshack1@gmail.com";
        String to = "jeffsfishingshack1@gmail.com";
        Properties properties  = getProperties();

        Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jeffsfishingshack1@gmail.com","azhar001");
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Customer Enquiry");
            BodyPart body = new MimeBodyPart();
            body.setText("Customer Name - " + customerName + "\nCustomer ID - " +customerID+"\nEnquiry - "+ content);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            message.setContent(multipart);
            Transport.send(message);




        }catch (MessagingException e){
            e.printStackTrace();
        }


    }


}
