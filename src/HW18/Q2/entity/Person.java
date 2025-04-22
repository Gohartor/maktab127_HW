package HW18.Q2.entity;

import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {

    private String firstName;
    private String latName;
    private LocalDate birthDate;
}
