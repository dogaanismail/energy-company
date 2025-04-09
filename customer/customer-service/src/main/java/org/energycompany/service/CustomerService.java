package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.CustomerEntity;
import org.energycompany.enums.TokenClaims;
import org.energycompany.exception.CustomerNotFoundException;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.customer.mapper.CustomerEntityToCustomerMapper;
import org.energycompany.repository.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerEntityToCustomerMapper customerEntityToCustomerMapper =
            CustomerEntityToCustomerMapper.initialize();

    public Customer getCurrentCustomer() {
        Optional<String> customerId = getCurrentIdFromJwt();

        return customerId
                .map(s -> getCustomer(UUID.fromString(s)))
                .orElse(null);
    }

    public Customer getCustomer(UUID customerId) {

        Optional<CustomerEntity> optionalCustomerEntity = customerRepository
                .findById(customerId);

        if (optionalCustomerEntity.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found by id: " + customerId);
        }

        CustomerEntity customerEntity = optionalCustomerEntity.get();
        return customerEntityToCustomerMapper.map(customerEntity);
    }

    private Optional<String> getCurrentIdFromJwt() {

        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(principal -> !"anonymousUser".equals(principal))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.CUSTOMER_ID.getValue()))
                .map(Object::toString);
    }

}
