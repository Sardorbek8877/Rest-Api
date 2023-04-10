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

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

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
}
