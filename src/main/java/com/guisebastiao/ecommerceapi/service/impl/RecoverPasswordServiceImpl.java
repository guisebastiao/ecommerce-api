package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.RecoverPassword;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.MailDTO;
import com.guisebastiao.ecommerceapi.dto.request.CreateResetPasswordRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.ResetPasswordRequestDTO;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import com.guisebastiao.ecommerceapi.repository.RecoverPasswordRepository;
import com.guisebastiao.ecommerceapi.service.RabbitMailService;
import com.guisebastiao.ecommerceapi.service.RecoverPasswordService;
import com.guisebastiao.ecommerceapi.util.CodeGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
public class RecoverPasswordServiceImpl implements RecoverPasswordService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RecoverPasswordRepository recoverPasswordRepository;

    @Autowired
    private RabbitMailService rabbitMailService;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    @Transactional
    public DefaultDTO<Void> createRecoverPassword(CreateResetPasswordRequestDTO createResetPasswordRequestDTO) {
        Client client = this.findClientByEmail(createResetPasswordRequestDTO.email());

        this.recoverPasswordRepository.deleteByRecoverPasswordByUserId(client.getId());

        LocalDateTime expiresCode = LocalDateTime.now().plusMinutes(15);
        String code = this.codeGenerator.generateToken();

        RecoverPassword recoverPassword = new RecoverPassword();
        recoverPassword.setClient(client);
        recoverPassword.setCode(code);
        recoverPassword.setExpiresCode(expiresCode);

        this.recoverPasswordRepository.save(recoverPassword);

        String template = this.templateMail(this.generateLink(code));

        String subject = "Recuperar Senha";
        MailDTO mailDTO = new MailDTO(client.getEmail(), subject, template);

        this.rabbitMailService.producer(mailDTO);

        return new DefaultDTO<Void>(Boolean.TRUE, "Você recebeu um email para redefinir sua senha", null);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> resetPassword(String code, ResetPasswordRequestDTO resetPasswordRequestDTO) {
        RecoverPassword recoverPassword = this.findRecoverPasswordByCode(code);

        Client client = recoverPassword.getClient();
        client.setPassword(this.passwordEncoder.encode(resetPasswordRequestDTO.newPassword()));
        client.setRecoverPassword(null);

        this.clientRepository.save(client);

        this.recoverPasswordRepository.delete(recoverPassword);

        return new DefaultDTO(Boolean.TRUE, "Sua senha foi recuperada com sucesso", null);
    }

    private Client findClientByEmail(String email) {
        return this.clientRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    private RecoverPassword findRecoverPasswordByCode(String code) {
        return this.recoverPasswordRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("A recuperação da senha se expirou ou já foi alterada"));
    }

    private String templateMail(String link) {
        Context context = new Context();
        context.setVariable("link", link);
        return this.templateEngine.process("recover-password-template", context);
    }

    private String generateLink(String code) {
        return String.format(this.frontendUrl + "/recover-password/" + code);
    }
}
