package com.oskarwiedeweg.cloudwork.user;

import com.oskarwiedeweg.cloudwork.BitUtils;
import com.oskarwiedeweg.cloudwork.auth.twofa.TwoFAService;
import com.oskarwiedeweg.cloudwork.exception.DuplicateUserException;
import com.oskarwiedeweg.cloudwork.user.dto.SettingsDto;
import com.oskarwiedeweg.cloudwork.user.dto.Setup2FADto;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final TwoFAService twoFAService;

    public Long createUser(String name, String email, String password) throws DuplicateUserException {
        String encoded = passwordEncoder.encode(password);
        return userDao.saveUser(name, email, encoded);
    }

    public Optional<User> getUserByName(String username) {
        return userDao.findUserByName(username);
    }

    public Setup2FADto setup2FA(Long userId) {
        User user = userDao.findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist"));

        String setupQrCode = twoFAService.setup2FA(user);
        String tempToken = twoFAService.create2FAChallenge(user);

        return new Setup2FADto(setupQrCode, tempToken);
    }

    public void disable2FA(Long userId) {
        User user = userDao.findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist"));

        twoFAService.disable2FA(user);
    }


    public boolean setup2FA(Long userId, Long code, String tempToken) {
        try {
            User validate = twoFAService.validate(tempToken, code);
            return userId.equals(validate.getId());
        } catch (ResponseStatusException e) {
            return false;
        }
    }

    public SettingsDto getSettings(Long userId) {
        User user = userDao.findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist"));
        Set<BitUtils.SettingBits> activeSettings = new HashSet<>();
            for (BitUtils.SettingBits value : BitUtils.SettingBits.values()) {
                if (BitUtils.hasBit(user.getSettings(), value.getBit())) {
                    activeSettings.add(value);
                }
            }
        return new SettingsDto(activeSettings);
    }
}
