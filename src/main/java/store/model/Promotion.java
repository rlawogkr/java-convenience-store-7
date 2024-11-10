package store.model;

import java.time.LocalDate;

public class Promotion {
    private String name;
    private int buyQuantity;
    private int getQuantity;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buyQuantity, int getQuantity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public String getName() { return name; }
    public int getBuyQuantity() { return buyQuantity; }
    public int getGetQuantity() { return getQuantity; }
}
