package pl.carapi.cardemo.models;

import org.springframework.hateoas.RepresentationModel;

public class CarModel extends RepresentationModel<CarModel> {

    private long id;
    private String brand;
    private String model;
    private Color color;

    public CarModel(long id, String brand, String model, Color color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public CarModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
