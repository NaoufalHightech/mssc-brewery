package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    //Constructor Injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerID}")
    ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerID") UUID customerID) {
        return new ResponseEntity<>(customerService.getCustomerById(customerID), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto newCustomerDto = customerService.saveNewCustomer(customerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        //Rest conventions need to provide location containing
        httpHeaders.add("Location", "/api/v1/customer/" + newCustomerDto.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{customerID}")
    public ResponseEntity handlePut(@PathVariable("customerID") UUID customerID, @Valid @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(customerID, customerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerID") UUID customerID){
        customerService.deleteById(customerID);
    }


}
