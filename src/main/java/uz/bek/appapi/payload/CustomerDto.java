package uz.bek.appapi.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotNull(message = "Fullname does not to be empty ")
    private String fullName;

    @NotNull(message = "Phone number does not to be empty ")
    private String phoneNumber;

    @NotNull(message = "Address does not to be empty ")
    private String address;
}
