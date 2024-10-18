package com.example.Army.SecuritySystem.Token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
//    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final TokenDao tokenDao;
    public ConfirmationToken getConfirmationToken(String token) {
//        return confirmationTokenRepository.findByToken(token);
        return tokenDao.getToken(token);
    }
    public void saveToken(ConfirmationToken confirmationToken) {
//        confirmationTokenRepository.save(confirmationToken);
        tokenDao.saveToken(confirmationToken);
    }
//    @Transactional
    public void confirmToken(ConfirmationToken confirmationToken) {
//        confirmationTokenRepository.findByToken(confirmationToken.getToken()).get().setConfirmedAt(LocalDateTime.now());
         ConfirmationToken confirmedToken=tokenDao.getToken(confirmationToken.getToken());
         confirmedToken.setConfirmedAt(LocalDateTime.now());
         tokenDao.updateToken(confirmedToken);
//        tokenDao.saveToken(confirmationToken);
    }
}
