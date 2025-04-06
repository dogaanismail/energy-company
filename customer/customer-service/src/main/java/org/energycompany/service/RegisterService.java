package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.CustomerEntity;
import org.energycompany.exception.CustomerAlreadyExistException;
import org.energycompany.factory.CustomerEntityFactory;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.energycompany.model.customer.mapper.CustomerEntityToCustomerMapper;
import org.energycompany.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerEntityToCustomerMapper customerEntityToCustomerMapper =
            CustomerEntityToCustomerMapper.initialize();

    public Customer registerCustomer(RegisterRequest registerRequest) {

        if (customerRepository.existsCustomerEntityByEmail(registerRequest.getEmail())) {
            throw new CustomerAlreadyExistException("The email is already used for another customer : " + registerRequest.getEmail());
        }

        CustomerEntity customerEntityToBeSave = CustomerEntityFactory
                .getCustomerEntityByRegisterRequest(registerRequest);
        customerEntityToBeSave.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntityToBeSave);
        return customerEntityToCustomerMapper.map(savedCustomerEntity);
    }

}
