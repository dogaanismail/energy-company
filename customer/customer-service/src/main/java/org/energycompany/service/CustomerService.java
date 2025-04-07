package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.CustomerEntity;
import org.energycompany.exception.CustomerNotFoundException;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.customer.mapper.CustomerEntityToCustomerMapper;
import org.energycompany.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerEntityToCustomerMapper customerEntityToCustomerMapper =
            CustomerEntityToCustomerMapper.initialize();

    public Customer getCustomer(UUID customerId) {

        Optional<CustomerEntity> optionalCustomerEntity = customerRepository
                .findById(customerId);

        if (optionalCustomerEntity.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found by id: " + customerId);
        }

        CustomerEntity customerEntity = optionalCustomerEntity.get();
        return customerEntityToCustomerMapper.map(customerEntity);
    }
}
