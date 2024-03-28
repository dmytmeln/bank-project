package bank.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;

    private String firstName;
    private String lastName;

    private String password;

    private String email;

    private String phoneNumber;

    private LocalDateTime creationDate;

}
