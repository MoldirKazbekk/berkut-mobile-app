package kz.sdu.edu.berkutapp.security;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findById(Long.valueOf(id)).orElseThrow();
        return new org.springframework.security.core.userdetails.User
                (id, appUser.getPhoneNumber(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
