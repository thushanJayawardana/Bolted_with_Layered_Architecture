package dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCategoryTm {
    private String p_Id;
    private String name;
    private String type;
    private double qty;
    private double price;
}
