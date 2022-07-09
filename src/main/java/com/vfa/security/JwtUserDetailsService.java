package com.vfa.security;

import com.vfa.model.Employee;
import com.vfa.service.interfaces.EmployeeService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component("myUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;

    public JwtUserDetailsService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        Employee employee = employeeService
                .getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find Employee with current email: " + email));

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(employee.getAuthority().getRole());
        grantedAuthorities.add(grantedAuthority);

        return new org
                .springframework
                .security
                .core.userdetails.User(employee.getEmail(), employee.getPassword(), grantedAuthorities);
    }
}
