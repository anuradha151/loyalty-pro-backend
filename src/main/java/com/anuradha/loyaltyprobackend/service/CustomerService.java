package com.anuradha.loyaltyprobackend.service;

import com.anuradha.loyaltyprobackend.dto.CustomerDto;
import com.anuradha.loyaltyprobackend.dto.PageDto;
import com.anuradha.loyaltyprobackend.entity.Customer;
import com.anuradha.loyaltyprobackend.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(CustomerDto dto) {
        validateSaveRequest(dto);
        customerRepository.save(
                new Customer(
                        UUID.randomUUID().toString(),
                        dto.getCardNumber(),
                        dto.getName(),
                        dto.getEmail(),
                        dto.getMobile(),
                        dto.getAddress()
                )
        );
    }

    public void update(CustomerDto dto) {
        validateSaveRequest(dto);
        Customer customer = customerRepository.findByUuid(dto.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
        customer.setCardNumber(dto.getCardNumber());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setMobile(dto.getMobile());
        customer.setAddress(dto.getAddress());
        customerRepository.save(customer);
    }


    public PageDto<CustomerDto> findAll(int page, int size) {
        return pageToPageDto(
                page,
                customerRepository.findAll(PageRequest.of(page, size))
        );
    }

    public PageDto<CustomerDto> findAll(String query, int page, int size) {
        return pageToPageDto(
                page,
                customerRepository.search(query, PageRequest.of(page, size))
        );
    }

    public CustomerDto findById(String uuid) {
        return customerRepository.findByUuid(uuid)
                .map(this::toCustomerDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
    }

    private void validateSaveRequest(CustomerDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (dto.getMobile() == null || dto.getMobile().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mobile is required");
        }
        if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address is required");
        }
    }

    private PageDto<CustomerDto> pageToPageDto(int page, Page<Customer> paged) {
        return new PageDto<>(
                paged.getTotalElements(),
                paged.getTotalPages(),
                page,
                paged.getContent().stream()
                        .map(this::toCustomerDto)
                        .toList()

        );
    }

    private CustomerDto toCustomerDto(Customer customer){
        return new CustomerDto(
                customer.getUuid(),
                customer.getCardNumber(),
                customer.getName(),
                customer.getEmail(),
                customer.getMobile(),
                customer.getAddress()
        );
    }
}
