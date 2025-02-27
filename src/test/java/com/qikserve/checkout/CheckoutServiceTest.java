package com.qikserve.checkout;

import com.qikserve.checkout.model.Cart;
import com.qikserve.checkout.model.CartItem;
import com.qikserve.checkout.model.Product;
import com.qikserve.checkout.model.Promotion;
import com.qikserve.checkout.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    private CheckoutService checkoutService;
    private Product burger;
    private Product pizza;
    private Product salad;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService();

        burger = new Product("PWWe3w1SDU", "Amazing Burger!", 999,
                List.of(new Promotion("ZRAwbsO2qM", "BUY_X_GET_Y_FREE", 2, 1, 0, 0)));

        pizza = new Product("Dwt5F7KAhi", "Amazing Pizza!", 1099,
                List.of(new Promotion("ibt3EEYczW", "QTY_BASED_PRICE_OVERRIDE", 2, 0, 0, 1799)));

        salad = new Product("C8GDyLrHJb", "Amazing Salad!", 499,
                List.of(new Promotion("Gm1piPn7Fg", "FLAT_PERCENT", 0, 0, 10, 0)));
    }

    @Test
    void shouldApplyBuyXGetYFreePromotion() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(burger, 3)); // Compre 2, leve 1 grátis

        int total = checkoutService.calculateTotal(cart);
        assertEquals(1998, total); // 3 Burgers pelo preço de 2 (999 * 2)
    }

    @Test
    void shouldApplyQtyBasedPriceOverridePromotion() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(pizza, 2)); // Promoção: 2 por 1799

        int total = checkoutService.calculateTotal(cart);
        assertEquals(1799, total);
    }

    @Test
    void shouldApplyFlatPercentDiscountPromotion() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(salad, 1)); // 10% de desconto

        int total = checkoutService.calculateTotal(cart);
        assertEquals(449, total); // 499 - 10% = 449
    }
}
