package com.anuradha.loyaltyprobackend.controller.inbound;

import com.anuradha.loyaltyprobackend.dto.CustomerDto;
import com.anuradha.loyaltyprobackend.dto.PageDto;
import com.anuradha.loyaltyprobackend.service.CustomerService;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void saveCustomer(@RequestBody CustomerDto dto) {
        customerService.save(dto);
    }

    @PutMapping
    public void updateCustomer(@RequestBody CustomerDto dto) {
        customerService.update(dto);
    }

    @GetMapping
    public PageDto<CustomerDto> findAll(@RequestParam int page, @RequestParam int size) {
        return customerService.findAll(page, size);
    }

    @GetMapping("search")
    public PageDto<CustomerDto> search(@RequestParam String query, @RequestParam int page, @RequestParam int size) {
        return customerService.findAll(query, page, size);
    }


}
