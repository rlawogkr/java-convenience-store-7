package store;

import store.model.Product;
import store.model.Promotion;
import store.service.StockService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        List<Product> products = Arrays.asList(
                new Product("콜라", 1000, 10, "탄산2+1"),
                new Product("콜라", 1000, 10, null),
                new Product("사이다", 1000, 8, "탄산2+1"),
                new Product("사이다", 1000, 7, null),
                new Product("오렌지주스", 1800, 9, "MD추천상품"),
                new Product("오렌지주스", 1800, 0, null),
                new Product("탄산수", 1200, 5, "탄산2+1"),
                new Product("탄산수", 1200, 0, null),
                new Product("물", 500, 10, null),
                new Product("비타민워터", 1500, 6, null),
                new Product("감자칩", 1500, 5, "반짝할인"),
                new Product("감자칩", 1500, 5, null),
                new Product("초코바", 1200, 5, "MD추천상품"),
                new Product("초코바", 1200, 5, null),
                new Product("에너지바", 2000, 5, null),
                new Product("정식도시락", 6400, 8, null),
                new Product("컵라면", 1700, 1, "MD추천상품"),
                new Product("컵라면", 1700, 10, null)
        );

        List<Promotion> promotions = Arrays.asList(
                new Promotion("탄산2+1", 2, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
                new Promotion("MD추천상품", 1, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
                new Promotion("반짝할인", 1, 0, LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30))
        );

        StockService stockService = new StockService(products);
        StoreService storeService = new StoreService(promotions, LocalDate.now());

        outputView.printWelcomeMessage();
        outputView.printProducts(products);

        while (true) {
            try {
                String input = inputView.readItem();
                List<Product> purchasedProducts = parseInput(input, stockService);

                // 구매할 상품의 재고 업데이트
                for (Product product : purchasedProducts) {
                    stockService.reduceStock(product, product.getQuantity());
                }

                // 멤버십 할인 여부 확인
                boolean applyMembershipDiscount = inputView.readYesNo("멤버십 할인을 받으시겠습니까?");
                int totalAmount = storeService.calculateTotal(purchasedProducts, applyMembershipDiscount);

                // 영수증 출력
                outputView.printReceipt(
                        totalAmount,
                        storeService.getPromotionDiscount(),
                        storeService.getMembershipDiscount(),
                        storeService.getFinalAmount()
                );

                // 추가 구매 여부 확인
                boolean continuePurchase = inputView.readYesNo("감사합니다. 구매하고 싶은 다른 상품이 있나요?");
                if (!continuePurchase) break;

                outputView.printProducts(products); // 재고 상태 갱신 후 상품 목록 다시 출력

            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    /**
     * 사용자 입력을 파싱하여 구매할 상품 목록을 반환하는 메서드
     */
    private static List<Product> parseInput(String input, StockService stockService) {
        List<Product> purchasedProducts = new ArrayList<>();

        String[] items = input.split(",");
        for (String item : items) {
            String[] parts = item.replace("[", "").replace("]", "").split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }

            String productName = parts[0];
            int quantity;
            try {
                quantity = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("수량은 숫자로 입력해 주세요.");
            }

            Product product = stockService.findProductByName(productName);
            if (product.getQuantity() < quantity) {
                throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }

            purchasedProducts.add(new Product(productName, product.getPrice(), quantity, product.getPromotion()));
        }
        return purchasedProducts;
    }

}
