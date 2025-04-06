package org.energycompany.model.customer.mapper;

import org.energycompany.entity.CustomerEntity;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerEntityToCustomerMapper extends BaseMapper<CustomerEntity, Customer> {

    @Override
    Customer map(CustomerEntity source);

    static CustomerEntityToCustomerMapper initialize() {
        return Mappers.getMapper(CustomerEntityToCustomerMapper.class);
    }

}
