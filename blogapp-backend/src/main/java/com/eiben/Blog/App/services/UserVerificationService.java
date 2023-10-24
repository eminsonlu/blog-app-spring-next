package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.entities.UserVerification;
import com.eiben.Blog.App.repository.UserVerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class UserVerificationService {
    private UserVerificationRepository userVerificationRepository;

    public UserVerificationService(UserVerificationRepository userVerificationRepository) {
        this.userVerificationRepository = userVerificationRepository;
    }


    // bunu neden yapiyoruz
    // cunku userVerificationRepository.save(userVerification) ile userVerificationRepository.findByUser(user) ayni transaction icinde olmali
    // transaction dedigimiz nedir ?
    // transaction bir islemi basariyla tamamlamak icin gereken tum adimlari iceren bir islem dizisidir
    @Transactional
    public UserVerification findByToken(String token) {
        return userVerificationRepository.findByToken(token);
    }

    @Transactional
    public UserVerification findByUser(User user) {
        return userVerificationRepository.findByUser(user);
    }

    @Transactional
    public void save(User user, String token) {
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setToken(token);
        // set expiry date to 24 hours
        userVerification.setExpires_at(calculateExpiryDate(24 * 60));
        userVerificationRepository.save(userVerification);
    }

    // calculate expiry date
    private Timestamp calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }

}
