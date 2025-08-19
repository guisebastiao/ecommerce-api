package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.AccountPending;
import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.LoginPending;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.MailDTO;
import com.guisebastiao.ecommerceapi.dto.request.auth.ActiveLoginRequest;
import com.guisebastiao.ecommerceapi.dto.request.auth.LoginRequest;
import com.guisebastiao.ecommerceapi.dto.request.auth.RegisterRequest;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;
import com.guisebastiao.ecommerceapi.dto.response.auth.LoginResponse;
import com.guisebastiao.ecommerceapi.enums.AccountStatus;
import com.guisebastiao.ecommerceapi.enums.Role;
import com.guisebastiao.ecommerceapi.exception.*;
import com.guisebastiao.ecommerceapi.mapper.ClientMapper;
import com.guisebastiao.ecommerceapi.repository.AccountPendingRepository;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import com.guisebastiao.ecommerceapi.repository.LoginPendingRepository;
import com.guisebastiao.ecommerceapi.security.JWTService;
import com.guisebastiao.ecommerceapi.service.AuthService;
import com.guisebastiao.ecommerceapi.service.RabbitMailService;
import com.guisebastiao.ecommerceapi.util.CodeGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoginPendingRepository loginPendingRepository;

    @Autowired
    private AccountPendingRepository accountPendingRepository;

    @Autowired
    private JWTService jwtService;

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

    @Value("${cookie.name.access.token}")
    private String cookieNameAccessToken;

    @Value("${cookie.name.refresh.token}")
    private String cookieNameRefreshToken;

    @Override
    @Transactional
    public DefaultResponse<LoginResponse> login(LoginRequest loginRequest) {
        Client client = this.findClientByEmail(loginRequest.email());

        if(client == null){
            throw new EntityNotFoundException("Email ou senha inválidos");
        }

        if(!client.isEnabled()) {
            throw new ForbiddenException("Você precisa ativar sua conta, verifique seu email");
        }

        if(!passwordEncoder.matches(loginRequest.password(), client.getPassword())){
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

        LoginResponse loginResponse = new LoginResponse(code);
        return new DefaultResponse<LoginResponse>(true, "Um email foi enviado para você", loginResponse);
    }

    @Override
    @Transactional
    public DefaultResponse<ClientSimpleResponse> activeLogin(ActiveLoginRequest activeLoginRequest, HttpServletResponse response) {
        LoginPending loginPending = this.loginPendingRepository.findByCode(activeLoginRequest.code())
                .orElseThrow(() -> new BadRequestException("Algo deu errado ao completar seu login"));

        Client client = loginPending.getClient();

        if(client.getLoginPending() == null) {
            throw new BadRequestException("Nenhuma verificação pendente");
        }

        if(!client.getLoginPending().getVerificationCode().equals(activeLoginRequest.verificationCode())){
            throw new UnauthorizedException("Código de verificação inválido");
        }

        String accessToken = jwtService.generateAccessToken(client);
        String refreshToken = jwtService.generateRefreshToken(client);

        client.setRefreshToken(refreshToken);
        this.clientRepository.save(client);

        this.loginPendingRepository.deleteByClientId(client.getId());

        ClientSimpleResponse clientSimpleResponse = this.clientMapper.toSimpleDTO(client);

        Cookie accessTokenCookie = this.generateCookie(this.cookieNameAccessToken, accessToken);
        Cookie refreshTokenCookie = this.generateCookie(this.cookieNameRefreshToken, refreshToken);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return new DefaultResponse<ClientSimpleResponse>(true,  "Login efetuado com sucesso", clientSimpleResponse);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> register(RegisterRequest registerRequest) {
        Client client = this.findClientByEmail(registerRequest.email());

        if(client != null && client.getStatus() != AccountStatus.PENDING){
            throw new ConflictEntityException("Esse email já está cadastrado");
        }

        Client saveClient;

        if(client != null) {
            this.accountPendingRepository.deleteAccountPendingByClientId(client.getId());
            saveClient = this.clientRepository.findByEmail(registerRequest.email())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        } else {
            saveClient = this.clientMapper.toEntity(registerRequest);
            saveClient.setPassword(passwordEncoder.encode(registerRequest.password()));
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

        return new DefaultResponse<Void>(true, "Um email foi enviado para você, para confirme sua conta", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> activeAccount(String verificationCode) {
        AccountPending accountPending = this.accountPendingRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new BadRequestException("Código de veficação expirado"));

        if(!accountPending.getVerificationCode().equals(verificationCode)){
            throw new BadRequestException("Código de veficação inválido");
        }

        Client client = accountPending.getClient();
        client.setStatus(AccountStatus.ACTIVE);
        client.setAccountPending(null);

        this.accountPendingRepository.deleteAccountPendingByClientId(client.getId());

        return new DefaultResponse<Void>(true, "Conta ativada com sucesso", null);
    }

    @Override
    public DefaultResponse<Void> logout(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = this.deleteCookie(this.cookieNameAccessToken);
        ResponseCookie refreshTokenCookie = this.deleteCookie(this.cookieNameRefreshToken);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return new DefaultResponse<>(true, "Logout efetuado com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<ClientSimpleResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = this.findCookieValue(cookieNameRefreshToken, request);
        String email = jwtService.extractUsername(refreshToken);
        Client client = this.findClientByEmail(email);

        if(client == null || !refreshToken.equals(client.getRefreshToken()) ) {
            throw new UnauthorizedException("Refresh token inválido");
        }

        if(jwtService.isExpired(refreshToken)) {
            throw new UnauthorizedException("Sessão expirada, faça o login novamente");
        }

        String newAccessToken = jwtService.generateAccessToken(client);

        ClientSimpleResponse clientSimpleResponse = this.clientMapper.toSimpleDTO(client);

        Cookie accessTokenCookie = this.generateCookie(this.cookieNameAccessToken, newAccessToken);

        response.addCookie(accessTokenCookie);

        return new DefaultResponse<ClientSimpleResponse>(true, "Acesso renovado com sucesso", clientSimpleResponse);
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

    private ResponseCookie deleteCookie(String name) {
        return ResponseCookie.from(name, "")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    private Cookie generateCookie(String name, String token) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        return cookie;
    }

    private String findCookieValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) return null;

        return Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
