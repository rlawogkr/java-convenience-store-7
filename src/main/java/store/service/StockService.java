package store.service;

import store.model.Product;

import java.util.List;

public class StockService {
    private List<Product> products;

    public StockService(List<Product> products) {
        this.products = products;
    }

    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다."));
    }

    public void reduceStock(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }
        product.reduceQuantity(quantity);
    }

    public void displayProducts() {
        products.forEach(System.out::println);
    }
}
