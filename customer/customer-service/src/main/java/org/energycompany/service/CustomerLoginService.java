package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.CustomerEntity;
import org.energycompany.exception.CustomerNotFoundException;
import org.energycompany.exception.PasswordNotValidException;
import org.energycompany.model.Token;
import org.energycompany.model.auth.dto.request.LoginRequest;
import org.energycompany.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerLoginService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Token login(LoginRequest loginRequest) {

        Optional<CustomerEntity> optionalCustomerEntity = customerRepository
                .findCustomerEntityByEmail(loginRequest.getEmail());

        if (optionalCustomerEntity.isEmpty()) {

            throw new CustomerNotFoundException("Can't find with given email: "
                    + loginRequest.getEmail());
        }

        CustomerEntity customerEntity = optionalCustomerEntity.get();

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                customerEntity.getPassword())) {

            throw new PasswordNotValidException();
        }

        return tokenService.generateToken(customerEntity.getClaims());
    }

}
