package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Supplier {
    private String sup_Id;
    private String name;
    private String products;
    private String mobile;
    private LocalDate date;
}
