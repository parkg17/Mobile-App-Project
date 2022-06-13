package edu.skku.map.project_2017312665;

public class CoffeeItemData {
    private String name;
    private String description;
    private String image_name;
    private double price;

    public CoffeeItemData(String name, String description, String image_name, double price) {
        this.name = name;
        this.description = description;
        this.image_name = image_name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
