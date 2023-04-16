package uz.bek.appapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/customer")
public class CustomerControllerSecond {

    @Autowired
    CustomerService customerService;

    /**
     * Get Customers
     * @return List<Customer>
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Get customer by Id
     * @param id
     * @return Customer
     */
    @GetMapping("/{id}")
    public HttpEntity<Customer> getCustomer(@PathVariable Integer id){
        Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * ADD CUSTOMER
     * @param customerDto
     * @return APIRESPONSE
     * CREATE VALIDATION
     */
    @PostMapping
    public HttpEntity<ApiResponse> addCustomer(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
//        if (apiResponse.isSuccess())
//            return ResponseEntity.status(201).body(apiResponse);
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Edit Customer
     * @param customerDto
     * @param id, customerDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable Integer id){
        ApiResponse apiResponse = customerService.editCustomer(customerDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Delete Customer
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id){
        ApiResponse apiResponse = customerService.deleteCustomer(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
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
