package br.com.freelance_management_api.service;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String mailUsername;

    public void sendEmail(String to, String subject, String body) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom(mailUsername);

        // Envio do e-mail
        mailSender.send(message);
    }

    public void enviaNotificacaoContrato(String freelancerEmail, String projectName, String companyEmail) throws MessagingException {
        String freelancerSubject = "Novo Contrato Criado";
        String freelancerBody = "Parabéns! Um novo contrato foi criado para você no projeto: " + projectName + ". Em breve entraremos em contato com mais detalhes.";

        String companySubject = "Novo Contrato para Projeto: " + projectName;
        String companyBody = "Um contrato foi criado com um freelancer para o projeto: " + projectName + ". Por favor, confirme os detalhes.";

        sendEmail(freelancerEmail, freelancerSubject, freelancerBody);
        sendEmail(companyEmail, companySubject, companyBody);
    }

    public void enviaPedidoPagamento(String freelancerEmail, String companyEmail, Double valorTotal) throws MessagingException {
        String freelancerSubject = "Solicitação de Pagamento";
        String freelancerBody = "Seu pagamento de R$" + valorTotal + " está pronto para processamento. Por favor, confirme o recebimento.";

        String companySubject = "Pagamento a Processar para o Freelancer";
        String companyBody = "Por favor, processe o pagamento de R$" + valorTotal + " ao freelancer associado.";

        sendEmail(freelancerEmail, freelancerSubject, freelancerBody);
        sendEmail(companyEmail, companySubject, companyBody);
    }
}
