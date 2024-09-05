package task.test.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dates")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolidayResponse {
    @Id
    private String date;
    private String localName;
    private String name;
    private String countryCode;
}
