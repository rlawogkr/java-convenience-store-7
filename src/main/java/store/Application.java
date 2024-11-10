package store;

import store.model.Product;
import store.model.Promotion;
import store.service.StockService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        List<Product> products = Arrays.asList(
                new Product("콜라", 1000, 10, "탄산2+1"),
                new Product("사이다", 1000, 8, "탄산2+1"),
                new Product("오렌지주스", 1800, 9, "MD추천상품")
                // 나머지 상품 추가
        );

        List<Promotion> promotions = Arrays.asList(
                new Promotion("탄산2+1", 2, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
                new Promotion("MD추천상품", 1, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31))
        );

        StockService stockService = new StockService(products);
        StoreService storeService = new StoreService(promotions);

        outputView.printWelcomeMessage();
        outputView.printProducts(products);

        while (true) {
            String input = inputView.readItem();
            // 입력 값 처리 및 계산 로직

            boolean continuePurchase = inputView.readYesNo("추가 구매 여부를 확인하시겠습니까?");
            if (!continuePurchase) break;
        }
    }
}
