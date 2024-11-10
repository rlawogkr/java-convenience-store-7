package store.service;

import store.model.Product;
import store.model.Promotion;

import java.time.LocalDate;
import java.util.List;

public class StoreService {
    private List<Promotion> promotions;
    private final LocalDate localDate;

    private int promotionDiscount;
    private int membershipDiscount;
    private int finalAmount;

    public StoreService(List<Promotion> promotions, LocalDate localDate) {
        this.promotions = promotions;
        this.localDate = localDate;
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
        for (Product product : products) {
            for (Promotion promotion : promotions) {
                if (promotion.isActive(localDate) && product.getPromotion().equals(promotion.getName())) {
                    int freeItems = (product.getQuantity() / (promotion.getBuyQuantity() + promotion.getGetQuantity())) * promotion.getGetQuantity();
                    discount += freeItems * product.getPrice();
                }
            }
        }
        return discount;
    }

    private int calculateMembershipDiscount(int amount) {
        int discount = (int) (amount * 0.3);
        return Math.min(discount, 8000); // 최대 할인 8000원 적용
    }

    // 할인 금액과 최종 결제 금액을 확인할 수 있는 메서드 추가
    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }


}
