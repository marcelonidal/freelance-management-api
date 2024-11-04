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

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

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

    public void enviarNotificacaoContrato(String emailFreelance, String nomeProjeto, String emailEmpresa) throws MessagingException {
        String tituloFreelance = "Novo Contrato Criado";
        String bodyFreelance = "Parabéns! Um novo contrato foi criado para você no projeto: " + nomeProjeto + ". Em breve entraremos em contato com mais detalhes.";

        String tituloEmpresa = "Novo Contrato para Projeto: " + nomeProjeto;
        String bodyEmpresa = "Um contrato foi criado com um freelancer para o projeto: " + nomeProjeto + ". Por favor, confirme os detalhes.";

        sendEmail(emailFreelance, tituloFreelance, bodyFreelance);
        sendEmail(emailEmpresa, tituloEmpresa, bodyEmpresa);
    }

    public void enviarPedidoPagamento(String emailFreelance, String emailEmpresa, Double valorTotal) throws MessagingException {
        String tituloFreelance = "Solicitação de Pagamento";
        String bodyFreelance = "Seu pagamento de R$" + valorTotal + " está pronto para processamento. Por favor, confirme o recebimento.";

        String tituloEmpresa = "Pagamento a Processar para o Freelancer";
        String bodyEmpresa = "Por favor, processe o pagamento de R$" + valorTotal + " ao freelancer associado.";

        sendEmail(emailFreelance, tituloFreelance, bodyFreelance);
        sendEmail(emailEmpresa, tituloEmpresa, bodyEmpresa);
    }
}
