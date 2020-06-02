package shoppingList.services.businessLogic;

import shoppingList.dto.ProductDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductActualPriceCalculation {

    public BigDecimal calculateProductActualPrice(ProductDto productDto) {
        if (productDto.getProductDiscount().equals(BigDecimal.valueOf(0.00))) {
            return productDto.getProductRegularPrice().setScale(3, RoundingMode.HALF_UP);
        } else {
            return productDto.getProductRegularPrice().multiply(BigDecimal.valueOf(1.00).subtract
                    (productDto.getProductDiscount().divide(BigDecimal.valueOf(100.00)))).setScale(3, RoundingMode.HALF_UP);
        }
    }
}