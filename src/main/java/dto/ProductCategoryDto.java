package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCategoryDto {
    private String p_Id;
    private String name;
    private String type;
    private double qty;
    private double price;

}
