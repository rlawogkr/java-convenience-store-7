package store.model;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void reduceQuantity(int amount) { quantity -= amount; }
    public String getPromotion() { return promotion; }

    @Override
    public String toString() {
        if(promotion != null) {
            return String.format("- %s %d원 %d개 %s", name, price, quantity, promotion);
        }else{
            return String.format("- %s %d원 %d개 %s", name, price, quantity, "");
        }
    }
}
