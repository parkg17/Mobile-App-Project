package edu.skku.map.project_2017312665.Data;

public class CoffeeItemData {
    private final String id;              // 상품 Id
    private final String name;            // 상품명
    private final Integer stock;          // 상품 재고
    private final double price;           // 상품 가격
    private final double rating;          // 상품 평점
    private final Grade grade;            // 상품 등급
    private final String expiredDate;     // 상품 유통기한
    private final String description;     // 상품 설명

    public CoffeeItemData(String id, String name, Integer stock, double price,
                          double rating, Grade grade, String expiredDate, String description) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.rating = rating;
        this.grade = grade;
        this.expiredDate = expiredDate;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Grade getGrade() {
        return grade;
    }

    public double getRating() {
        return rating;
    }

    public String getExpiredDate() {
        return expiredDate;
    }
}
