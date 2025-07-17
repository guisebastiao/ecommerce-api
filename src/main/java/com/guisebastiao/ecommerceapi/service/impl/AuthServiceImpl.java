package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.AccountPending;
import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.LoginPending;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.MailDTO;
import com.guisebastiao.ecommerceapi.dto.request.ActiveLoginRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.LoginRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.RefreshTokenRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.RegisterRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ActiveLoginResponseDTO;
import com.guisebastiao.ecommerceapi.dto.response.LoginResponseDTO;
import com.guisebastiao.ecommerceapi.dto.response.RefreshTokenResponseDTO;
import com.guisebastiao.ecommerceapi.enums.AccountStatus;
import com.guisebastiao.ecommerceapi.enums.Role;
import com.guisebastiao.ecommerceapi.exception.*;
import com.guisebastiao.ecommerceapi.mapper.ClientMapper;
import com.guisebastiao.ecommerceapi.repository.AccountPendingRepository;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import com.guisebastiao.ecommerceapi.repository.LoginPendingRepository;
import com.guisebastiao.ecommerceapi.security.JsonWebTokenService;
import com.guisebastiao.ecommerceapi.service.AuthService;
import com.guisebastiao.ecommerceapi.service.RabbitMailService;
import com.guisebastiao.ecommerceapi.util.CodeGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoginPendingRepository loginPendingRepository;

    @Autowired
    private AccountPendingRepository accountPendingRepository;

    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @Autowired
    private RabbitMailService rabbitMailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private CodeGenerator codeGenerator;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    @Transactional
    public DefaultDTO<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        Client client = this.findClientByEmail(loginRequestDTO.email());

        if(client == null){
            throw new EntityNotFoundException("Email ou senha inválidos");
        }

        if(!client.isEnabled()) {
            throw new ForbiddenException("Você precisa ativar sua conta, verifique seu email");
        }

        if(!passwordEncoder.matches(loginRequestDTO.password(), client.getPassword())){
            throw new UnauthorizedException("Email ou senha inválidos");
        }

        this.loginPendingRepository.deleteByClientId(client.getId());

        String verificationCode = this.generateSecureSixDigitCode();
        String code = this.codeGenerator.generateToken();

        LocalDateTime now = LocalDateTime.now().plusMinutes(15);

        LoginPending loginPending = new LoginPending();
        loginPending.setVerificationCode(verificationCode);
        loginPending.setVerificationExpires(now);
        loginPending.setCode(code);
        loginPending.setClient(client);

        this.loginPendingRepository.save(loginPending);

        String template = this.loadTemplateLoginPending(verificationCode);

        String subject = "Código de Verificação - Ecommerce";
        MailDTO mailDTO = new MailDTO(client.getEmail(), subject, template);

        this.rabbitMailService.producer(mailDTO);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(code);
        return new DefaultDTO<LoginResponseDTO>(Boolean.TRUE, "Um email foi enviado para você", loginResponseDTO);
    }

    @Override
    @Transactional
    public DefaultDTO<ActiveLoginResponseDTO> activeLogin(ActiveLoginRequestDTO activeLoginRequestDTO) {
        LoginPending loginPending = this.loginPendingRepository.findByCode(activeLoginRequestDTO.code())
                .orElseThrow(() -> new BadRequestException("Algo deu errado ao completar seu login"));

        Client client = loginPending.getClient();

        if(client.getLoginPending() == null) {
            throw new BadRequestException("Nenhuma verificação pendente");
        }

        if(!client.getLoginPending().getVerificationCode().equals(activeLoginRequestDTO.verificationCode())){
            throw new UnauthorizedException("Código de verificação inválido");
        }

        String token = jsonWebTokenService.generateToken(client);
        String refreshToken = jsonWebTokenService.generateRefreshToken(client);
        Instant expiresAt = jsonWebTokenService.extractExpiration(token);

        client.setRefreshToken(refreshToken);
        this.clientRepository.save(client);

        this.loginPendingRepository.deleteByClientId(client.getId());

        ActiveLoginResponseDTO activeLoginResponseDTO = new ActiveLoginResponseDTO(token, "Bearer", refreshToken, expiresAt, client.getId(), client.getName(), client.getEmail());
        return new DefaultDTO<ActiveLoginResponseDTO>(Boolean.TRUE,  "Login efetuado com sucesso", activeLoginResponseDTO);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> register(RegisterRequestDTO registerRequestDTO) {
        Client client = this.findClientByEmail(registerRequestDTO.email());

        if(client != null && client.getStatus() != AccountStatus.PENDING){
            throw new DuplicateEntityException("Esse email já está cadastrado");
        }

        Client saveClient;

        if(client != null) {
            this.accountPendingRepository.deleteAccountPendingByClientId(client.getId());
            saveClient = this.clientRepository.findByEmail(registerRequestDTO.email())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        } else {
            saveClient = this.clientMapper.toEntity(registerRequestDTO);
            saveClient.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
            saveClient.setStatus(AccountStatus.PENDING);
            saveClient.setRole(Role.CLIENT);
            this.clientRepository.save(saveClient);
        }

        String verificationCode = this.codeGenerator.generateToken();

        LocalDateTime now = LocalDateTime.now().plusMinutes(15);

        AccountPending accountPending = new AccountPending();
        accountPending.setClient(saveClient);
        accountPending.setVerificationCode(verificationCode);
        accountPending.setVerificationExpires(now);

        this.accountPendingRepository.save(accountPending);

        String template = this.loadTemplateAccountPending(this.generateUrl(verificationCode));

        String subject = "Ativar Minha Conta - Ecommerce";
        MailDTO mailDTO = new MailDTO(saveClient.getEmail(), subject, template);

        this.rabbitMailService.producer(mailDTO);

        return new DefaultDTO<Void>(Boolean.TRUE, "Um email foi enviado para você, para confirme sua conta", null);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> activeAccount(String verificationCode) {
        AccountPending accountPending = this.accountPendingRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new BadRequestException("Código de veficação expirado"));

        if(!accountPending.getVerificationCode().equals(verificationCode)){
            throw new BadRequestException("Código de veficação inválido");
        }

        Client client = accountPending.getClient();
        client.setStatus(AccountStatus.ACTIVE);
        client.setAccountPending(null);

        this.accountPendingRepository.deleteAccountPendingByClientId(client.getId());

        return new DefaultDTO<Void>(Boolean.TRUE, "Conta ativada com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultDTO<RefreshTokenResponseDTO> refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String refreshToken = refreshTokenRequestDTO.refreshToken();
        String email = jsonWebTokenService.extractUsername(refreshToken);
        Client client = this.findClientByEmail(email);

        if(client == null || !refreshToken.equals(client.getRefreshToken()) ) {
            throw new UnauthorizedException("Refresh token inválido");
        }

        if(jsonWebTokenService.isExpired(refreshToken)) {
            throw new UnauthorizedException("Sessão expirada, faça o login novamente");
        }

        String newToken = jsonWebTokenService.generateToken(client);
        Instant expiresAt = jsonWebTokenService.extractExpiration(refreshToken);
        RefreshTokenResponseDTO refreshTokenResponseDTO = new RefreshTokenResponseDTO(newToken, expiresAt);

        return new DefaultDTO<RefreshTokenResponseDTO>(Boolean.TRUE, "Acesso renovado com sucesso", refreshTokenResponseDTO);
    }

    private Client findClientByEmail(String email) {
        return this.clientRepository.findByEmail(email).orElse(null);
    }

    private String generateSecureSixDigitCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    private String loadTemplateLoginPending(String verificationCode) {
        Context context = new Context();
        context.setVariable("verificationCode", verificationCode);
        return templateEngine.process("active-login-template", context);
    }

    private String loadTemplateAccountPending(String verificationCode) {
        Context context = new Context();
        context.setVariable("verificationCode", verificationCode);
        return templateEngine.process("account-pending-template", context);
    }

    private String generateUrl(String verificationCode) {
        return String.format(this.frontendUrl + "/active-account/" + verificationCode);
    }
}
