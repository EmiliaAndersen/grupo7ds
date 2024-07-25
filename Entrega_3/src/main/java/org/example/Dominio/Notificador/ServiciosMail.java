package org.example.Dominio.Notificador;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class ServiciosMail {

    public static void enviarMail(String mailTo, String mensaje) {
        Email from = new Email("grupo7ddstp@gmail.com");
        String subject = "Hello World";
        Email to = new Email(mailTo);
        Content content = new Content("text/plain", "Hello, Email!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
