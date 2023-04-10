package uz.bek.appapi.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.appapi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
