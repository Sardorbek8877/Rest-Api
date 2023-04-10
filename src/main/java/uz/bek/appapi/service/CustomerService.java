package uz.bek.appapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bek.appapi.entity.Customer;
import uz.bek.appapi.payload.ApiResponse;
import uz.bek.appapi.payload.CustomerDto;
import uz.bek.appapi.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    /**
     * Get Customers
     * @return List<Customer>
     */
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    /**
     * Get customer by Id
     * @param id
     * @return Customer
     */
    public Customer getCustomer(Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }

    /**
     * ADD CUSTOMER
     * @param customerDto
     * @return APIRESPONSE
     * CREATE VALIDATION
     */
    public ApiResponse addCustomer(CustomerDto customerDto){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("Phone number already exist", false);
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("Customer added", true);
    }

    /**
     * Edit Customer
     * @param customerDto
     * @param id, customerDto
     * @return ApiResponse
     */
    public ApiResponse editCustomer(CustomerDto customerDto, Integer id){
        boolean existsByPhoneNumberAndIdNot = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot)
            return new ApiResponse("Phone number already exist", false);

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            return new ApiResponse("Customer not found", false);

        Customer customer = optionalCustomer.get();
        customer.setAddress(customerDto.getAddress());
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customerRepository.save(customer);

        return new ApiResponse("Customer edited", true);
    }

    /**
     * Delete Customer
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteCustomer(Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            return new ApiResponse("Customer not found", false);
        customerRepository.deleteById(id);
        return new ApiResponse("Customer deleted", true);
    }
}
