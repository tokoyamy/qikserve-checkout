package com.qikserve.checkout.controller;

import com.qikserve.checkout.Dto.CartDTO;
import com.qikserve.checkout.Dto.CartItemDTO;
import com.qikserve.checkout.Dto.CheckoutResponseDTO;
import com.qikserve.checkout.model.Cart;
import com.qikserve.checkout.model.CartItem;
import com.qikserve.checkout.model.Product;
import com.qikserve.checkout.service.CheckoutService;
import com.qikserve.checkout.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/checkout")
@Tag(name = "Checkout API", description = "Endpoints to manage checkout")
public class CheckoutController {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
    private final ProductService productService;
    private final CheckoutService checkoutService;

    public CheckoutController(ProductService productService, CheckoutService checkoutService) {
        this.productService = productService;
        this.checkoutService = checkoutService;
    }

    @Operation(summary = "Add items to cart and calculate total")
    @PostMapping
    public ResponseEntity<CheckoutResponseDTO> processCheckout(@RequestBody CartDTO cartDTO) {
        logger.info("Receiving checkout request for {} items", cartDTO.getItems().size());

        Cart cart = new Cart();
        for (CartItemDTO itemDTO : cartDTO.getItems()) {
            logger.info("Processing item: {}", itemDTO.getProductId());
            Product product = productService.getProductById(itemDTO.getProductId());
            if (product == null) {
                logger.error("Product not found: {}", itemDTO.getProductId());
                return ResponseEntity.badRequest().body(new CheckoutResponseDTO(0, 0));
            }
            cart.addItem(new CartItem(product, itemDTO.getQuantity()));
        }

        int total = checkoutService.calculateTotal(cart);
        int savings = cartDTO.getItems().stream()
                .mapToInt(item -> {
                    Product product = productService.getProductById(item.getProductId());
                    if (product == null) return 0;
                    int originalPrice = product.getPrice() * item.getQuantity();
                    int discountedPrice = checkoutService.calculateTotal(new Cart() {{
                        addItem(new CartItem(product, item.getQuantity()));
                    }});
                    return originalPrice - discountedPrice;
                }).sum();

        logger.info("Checkout completed. Total: {} cents, Economy: {} cents", total, savings);
        return ResponseEntity.ok(new CheckoutResponseDTO(total, savings));
    }

}
