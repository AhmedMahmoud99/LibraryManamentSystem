package main.models.PatronModel;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "nat_seq", sequenceName = "nat_sequence", allocationSize = 299011514)
public class ContactInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nat_seq")
    @NotNull(message = "Cannot be null")
    private Integer national_id;
    @NotBlank(message = "Cannot be blank")
    @Size(max = 100,message = "cant be more than 50 characters")
    private String address;
    @NotBlank(message = "Cannot be blank")
    @Size(max = 100,message = "cant be more than 50 characters")
    private String phone_number;
}
