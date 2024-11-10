package store.view;

import store.model.Product;

import java.util.List;

public class OutputView {
    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printProducts(List<Product> products) {
        System.out.println("현재 보유하고 있는 상품입니다.");
        products.forEach(System.out::println);
    }

    public void printReceipt(int total, int promotionDiscount, int membershipDiscount, int finalAmount) {
        System.out.println("==============W 편의점================");
        System.out.println("총구매액\t\t\t" + total);
        System.out.println("행사할인\t\t\t-" + promotionDiscount);
        System.out.println("멤버십할인\t\t\t-" + membershipDiscount);
        System.out.println("내실돈\t\t\t" + finalAmount);
    }
}
