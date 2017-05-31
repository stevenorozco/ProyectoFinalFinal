/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Servidor.Servidor;
import com.sun.mail.smtp.SMTPTransport;
import java.net.PasswordAuthentication;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author andres
 */
public class EnviarCorreo {

    public static void main(String[]args){
        final String username = "anfegosa359@gmail.com";
        final String password = "iugaspxpiycxyyvj";
        
        try{
            // Get a Properties object
            Properties props = System.getProperties();
            props.setProperty("mail.smtps.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            
            Session session = Session.getInstance(props);
            
            // -- Cargar la imagen --
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource("cumple.png")));
            adjunto.setFileName("cumple.png");  
        
            // -- Cargar el texto --
            MimeBodyPart texto = new MimeBodyPart();
            texto.setText("Feliz Cumpleaños");
        
            // -- Conectar el texto y la imagen -- 
            MimeMultipart mltrp = new MimeMultipart();
            mltrp.addBodyPart(texto);
            mltrp.addBodyPart(adjunto);
            
            //TO y FROM y mensaje
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("anfegosa359@gmail.com"));
            msg.setSubject("Felicidades");
            msg.setContent(mltrp);
            msg.setSentDate(new Date());
            
            Transport t = session.getTransport("smtp");
            
            t.connect(username,password);
            System.out.println(t.isConnected());
            t.send(msg, msg.getAllRecipients());
            t.close();
            
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }
}
