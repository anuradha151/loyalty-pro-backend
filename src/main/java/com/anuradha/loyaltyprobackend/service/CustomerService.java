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

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(CustomerDto dto) {
        validateSaveRequest(dto);
        customerRepository.findByEmail(dto.getEmail())
                .ifPresent(customer -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
                });

        String cardNumber = dto.getCardNumber();
        if (dto.getCardNumber() != null && !dto.getCardNumber().isEmpty()) {
            if (dto.getCardNumber().length() > 6)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card number cannot be more than 6 digits");
            cardNumber = formatCardNumber(cardNumber);
            customerRepository.findByCardNumber(cardNumber)
                    .ifPresent(x -> {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card number already exists");
                    });

        }
        customerRepository.save(
                new Customer(
                        UUID.randomUUID().toString(),
                        cardNumber,
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

        String cardNumber = dto.getCardNumber();
        if (cardNumber != null && !cardNumber.isEmpty()) {

            if (dto.getCardNumber().length() > 6)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card number cannot be more than 6 digits");

            cardNumber = formatCardNumber(cardNumber);

            if (!customer.getCardNumber().equals(cardNumber))
                customerRepository.findByCardNumber(cardNumber)
                        .ifPresent(x -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card number already exists");
                        });
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            customerRepository.findByEmail(dto.getEmail())
                    .ifPresent(x -> {
                        if (!x.getUuid().equals(dto.getUuid()))
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists for another customer - " + x.getName());
                    });
        }

        customer.setCardNumber(formatCardNumber(dto.getCardNumber()));
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

    private CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getUuid(),
                customer.getCardNumber(),
                customer.getName(),
                customer.getEmail(),
                customer.getMobile(),
                customer.getAddress()
        );
    }

    private String formatCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return null;
        }
        return String.format("%06d", Integer.parseInt(cardNumber));
    }
}
