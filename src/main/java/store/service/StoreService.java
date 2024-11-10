package store.service;

import store.model.Product;
import store.model.Promotion;

import java.util.List;

public class StoreService {
    private List<Promotion> promotions;

    public StoreService(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public int calculateTotal(List<Product> products, boolean applyMembershipDiscount) {
        int total = products.stream().mapToInt(p -> p.getPrice() * p.getQuantity()).sum();
        int promotionDiscount = applyPromotionDiscount(products);
        int membershipDiscount = applyMembershipDiscount ? calculateMembershipDiscount(total - promotionDiscount) : 0;

        return total - promotionDiscount - membershipDiscount;
    }

    private int applyPromotionDiscount(List<Product> products) {
        int discount = 0;
        // 프로모션 로직 구현
        return discount;
    }

    private int calculateMembershipDiscount(int amount) {
        int discount = (int) (amount * 0.3);
        return Math.min(discount, 8000); // 최대 할인 8000원 적용
    }
}
