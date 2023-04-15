package uz.bek.appapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.bek.appapi.entity.Customer;
import uz.bek.appapi.payload.ApiResponse;
import uz.bek.appapi.payload.CustomerDto;
import uz.bek.appapi.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * Get Customers
     * @return List<Customer>
     */
    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    /**
     * Get customer by Id
     * @param id
     * @return Customer
     */
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Integer id){
        return customerService.getCustomer(id);
    }

    /**
     * ADD CUSTOMER
     * @param customerDto
     * @return APIRESPONSE
     * CREATE VALIDATION
     */
    @PostMapping
    public ApiResponse addCustomer(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
        return apiResponse;
    }

    /**
     * Edit Customer
     * @param customerDto
     * @param id, customerDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ApiResponse editCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable Integer id){
        return customerService.editCustomer(customerDto, id);
    }

    /**
     * Delete Customer
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ApiResponse deleteCustomer(@PathVariable Integer id){
        return customerService.deleteCustomer(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
