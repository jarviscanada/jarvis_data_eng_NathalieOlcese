import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Customer implements DTO {

    long id;
    String firstName;
    String lastName;
    String email;
    String phone;
    String address;
    String city;
    String state;
    String zipCode;


}


