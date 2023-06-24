package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    @ApiModelProperty(value = "This is the first name", required = true)
    private String firstname;

    @ApiModelProperty(required = true)
    private String lastname;

    @JsonProperty("customer_url") //to save the information in a JSON with this name
    private String customerUrl;
}
