package com.example.TailorMe.API.Services.userServices;

import com.example.TailorMe.API.Models.user;
import com.example.TailorMe.API.Repositories.userRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailService implements UserDetailsService{
        private final userRepository userepo;

    public CustomUserDetailService(userRepository userepo) {
        this.userepo = userepo;
    }


    @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            user UserCredentials;
            UserCredentials = userepo.findByEmail(email);

            return User
                    .withUsername(UserCredentials.getEmail())
                    .password(UserCredentials.getPassword())
                    .authorities(UserCredentials.getUserRole().toString())
                    .build();
        }


}
