package com.qikserve.checkout.service;

import com.qikserve.checkout.model.Cart;
import com.qikserve.checkout.model.CartItem;
import com.qikserve.checkout.model.Promotion;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    public int calculateTotal(Cart cart) {
        int total = 0;
        for (CartItem item : cart.getItems()) {
            total += applyPromotions(item);
        }
        return total;
    }

    private int applyPromotions(CartItem item) {
        int price = item.getProduct().getPrice();
        int quantity = item.getQuantity();

        for (Promotion promotion : item.getProduct().getPromotions()) {
            switch (promotion.getType()) {
                case "BUY_X_GET_Y_FREE":
                    if (promotion.getRequiredQty() > 0) { // Evita divisão por zero
                        int freeItems = (quantity / promotion.getRequiredQty()) * promotion.getFreeQty();
                        quantity -= freeItems; // Remove os itens grátis do total
                    }
                    break;
                case "QTY_BASED_PRICE_OVERRIDE":
                    if (promotion.getRequiredQty() > 0) { // Evita divisão por zero
                        int bundles = quantity / promotion.getRequiredQty();
                        int remaining = quantity % promotion.getRequiredQty();
                        price = (bundles * promotion.getPrice()) + (remaining * price);
                    }
                    break;
                case "FLAT_PERCENT":
                    if (promotion.getAmount() > 0) { // Evita divisão por zero
                        price = price - (price * promotion.getAmount() / 100);
                    }
                    break;
                default:
                    break;
            }
        }

        return price * quantity;
    }
}
