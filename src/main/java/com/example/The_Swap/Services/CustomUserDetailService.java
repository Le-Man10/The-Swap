package com.example.The_Swap.Services;

import com.example.The_Swap.Model.user;
import com.example.The_Swap.Repositories.userRepository;
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
