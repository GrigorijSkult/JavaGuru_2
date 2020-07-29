package shoppingList.services.validations;

import org.springframework.stereotype.Component;
import shoppingList.constants.ValidationConstants;
import shoppingList.dto.ProductDto;
import shoppingList.services.validations.exception.ProductValidationException;

@Component
public class ProductDescriptionValidation implements ValidationRule<ProductDto> {

    @Override
    public void validate(ProductDto productDto) {
        if (!(productDto.getDescription() == null || productDto.getDescription().isEmpty())
                && productDto.getDescription().length() > ValidationConstants.maxProductDescriptionLength) {
            throw new ProductValidationException("Product`s description should be shorter then "
                    + ValidationConstants.maxProductDescriptionLength + " symbols");
        }
    }

}
