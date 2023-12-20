package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.auth.dto.AuthenticationDto;
import com.oskarwiedeweg.cloudwork.auth.dto.LoginDto;
import com.oskarwiedeweg.cloudwork.auth.dto.RegisterDto;
import com.oskarwiedeweg.cloudwork.auth.sso.SSOService;
import com.oskarwiedeweg.cloudwork.auth.token.TokenService;
import com.oskarwiedeweg.cloudwork.auth.twofa.TwoFAService;
import com.oskarwiedeweg.cloudwork.exception.DuplicateUserException;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import com.oskarwiedeweg.cloudwork.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService underTest;

    private AuthenticationManager authenticationManager;
    private SSOService ssoService;
    private TokenService tokenService;
    private ModelMapper modelMapper;
    private UserService userService;
    private TwoFAService twoFAService;

    @BeforeEach
    public void setup() {
        authenticationManager = mock(AuthenticationManager.class);
        tokenService = mock(TokenService.class);
        modelMapper = mock(ModelMapper.class);
        userService = mock(UserService.class);
        twoFAService = mock(TwoFAService.class);
        ssoService = mock(SSOService.class);
        underTest = new AuthService(authenticationManager, ssoService, tokenService, modelMapper, userService, twoFAService);
    }

    @Test
    void testSuccessfulLogin() {
        //given
        String username = "Test";
        LoginDto loginDto = new LoginDto(username, "test");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername().toLowerCase(), // test username transformation
                loginDto.getPassword()
        );
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);
        UserUserDetails userDetails = mock(UserUserDetails.class);
        String jwt = "key";

        //when
        when(userDetails.getUser()).thenReturn(user);
        when(authenticationManager.authenticate(token))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        when(twoFAService.has2FAEnabled(user)).thenReturn(false);

        // then
        AuthenticationDto login = underTest.login(loginDto);

        assertEquals(jwt, login.getAccessToken());
        assertEquals(userDto, login.getUserData());
        assertFalse(login.isNeeds2FA());

        verify(authenticationManager).authenticate(token);
        verify(authenticationManager, never()).authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));
        verify(twoFAService).has2FAEnabled(user);
        verify(tokenService).generateToken(user);
    }

    @Test
    void test2FALogin() {
        //given
        LoginDto loginDto = new LoginDto("test", "test");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);
        UserUserDetails userDetails = mock(UserUserDetails.class);
        String jwt = "key";

        //when
        when(userDetails.getUser()).thenReturn(user);
        when(authenticationManager.authenticate(token))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        when(twoFAService.has2FAEnabled(user)).thenReturn(true);

        // then
        AuthenticationDto login = underTest.login(loginDto);

        assertNull(login.getAccessToken());
        assertNull(login.getUserData());
        assertTrue(login.isNeeds2FA());

        verify(authenticationManager).authenticate(token);
        verify(twoFAService).has2FAEnabled(user);
        verify(tokenService, never()).generateToken(user);
    }

    @Test
    void testInvalidCredentialsLogin() {
        //given
        LoginDto loginDto = new LoginDto("test", "test");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);
        UserUserDetails userDetails = mock(UserUserDetails.class);
        String jwt = "key";

        //when
        when(userDetails.getUser()).thenReturn(user);
        when(authenticationManager.authenticate(token))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials"));
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        // then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> underTest.login(loginDto));

        assertEquals(responseStatusException.getStatusCode(), HttpStatus.UNAUTHORIZED);

        verify(authenticationManager).authenticate(token);
        verify(tokenService, never()).generateToken(user);
        verify(modelMapper, never()).map(user, UserDto.class);
    }

    @Test
    void testUnexpectedPrincipalLogin() {
        //given
        LoginDto loginDto = new LoginDto("test", "test");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);
        Object userDetails = mock(Object.class);
        String jwt = "key";

        //when
        when(authenticationManager.authenticate(token))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        // then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> underTest.login(loginDto));

        assertEquals(runtimeException.getMessage(), "User Details are not expected UserUserDetails!");
    }

    @Test
    void testSuccessfulRegister() throws DuplicateUserException {
        //given
        String username = "test-username";
        String email = "testEmail";
        String password = "testPassword";
        String jwt = "jwt";
        Long userId = new Random().nextLong();
        User user = User.builder()
                .id(userId)
                .email(email)
                .name(username)
                .build();

        //when
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(userService.createUser(username, email, password)).thenReturn(userId);

        //then
        AuthenticationDto register = underTest.register(new RegisterDto(username, email, password));

        assertEquals(jwt, register.getAccessToken());
        assertFalse(register.isNeeds2FA());

        verify(tokenService).generateToken(user);
        verify(userService).createUser(username, email, password);
    }

    @Test
    void testDuplicateRegister() throws DuplicateUserException {
        //given
        String username = "test-username";
        String email = "testEmail";
        String password = "testPassword";
        String jwt = "jwt";
        Long userId = new Random().nextLong();
        User user = User.builder()
                .id(userId)
                .email(email)
                .name(username)
                .build();

        //when
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(userService.createUser(username, email, password)).thenThrow(new DuplicateUserException());

        //then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> underTest.register(new RegisterDto(username, email, password)));

        assertEquals(exception.getStatusCode(), HttpStatus.CONFLICT);

        verify(tokenService, never()).generateToken(user);
        verify(userService).createUser(username, email, password);
    }

    @Test
    void testSuccessful2FAVerify() {
        //given
        String token = "2FAToken";
        Long twoFACode = 123456L;
        String jwt = "jwt";
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("testEmail")
                .build();

        //when
        when(twoFAService.validate(token, twoFACode)).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn(jwt);

        //then
        AuthenticationDto verify = underTest.verify(token, twoFACode);

        assertEquals(verify.getAccessToken(), jwt);
        assertFalse(verify.isNeeds2FA());

        verify(twoFAService).validate(token, twoFACode);
        verify(tokenService).generateToken(user);
    }

    @Test
    void testInvalid2FAVerify() {
        //given
        String token = "2FAToken";
        Long twoFACode = 123456L;
        String jwt = "jwt";
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("testEmail")
                .build();
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        //when
        when(twoFAService.validate(token, twoFACode)).thenThrow(exception);
        when(tokenService.generateToken(user)).thenReturn(jwt);

        //then
        assertThrows(ResponseStatusException.class, () -> underTest.verify(token, twoFACode));

        verify(twoFAService).validate(token, twoFACode);
        verify(tokenService, never()).generateToken(user);
    }
}