package main.models.PatronModel;

import jakarta.persistence.*;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Patron_table")
@SequenceGenerator(name = "patron_seq", sequenceName = "patron_sequence", allocationSize = 1)
public class Patron implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patron_seq")
    private Integer id;
    @NotBlank(message = "Cannot be blank")
    @Size(max = 100,message = "cant be more than 50 characters")
    private String patron_name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "National_ID")
    private ContactInformation contactInformation;

}
