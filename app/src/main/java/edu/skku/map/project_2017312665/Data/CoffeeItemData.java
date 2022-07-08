package edu.skku.map.project_2017312665.Data;

public class CoffeeItemData {
    private String Id;              // 상품 Id
    private String name;            // 상품명
    private Integer stock;          // 상품 재고
    private double price;           // 상품 가격
    private double rating;          // 상품 평점
    private Grade grade;            // 상품 등급
    private String expiredDate;     // 상품 유통기한
    private String description;     // 상품 설명

    public CoffeeItemData(String id, String name, Integer stock, double price,
                          double rating, Grade grade, String expiredDate, String description) {
        Id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.rating = rating;
        this.grade = grade;
        this.expiredDate = expiredDate;
        this.description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}
