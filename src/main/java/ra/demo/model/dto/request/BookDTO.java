package ra.demo.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.demo.cutom_validator.QuantityPositive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDTO {
    @NotBlank(message = "Không được để trống title")
    private String title;
    @NotBlank(message = "Không được để trống author")
    private String author;
    @NotBlank(message = "Không được để trống category")
    private String category;
    @NotNull(message = "Không được để trống quantity")
    @QuantityPositive
    private Integer quantity;
}
