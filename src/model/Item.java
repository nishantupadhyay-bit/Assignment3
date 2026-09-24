package model;

import java.util.Objects;

// Represents an Item that will be shared between worker threads.
public class Item {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private Type type;
    private double tax;

    public Item() {
    }

    public Item(int id,String name,double price,int quantity,Type type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    // Compares the current Item with the previous snapshot.
    // Returns true when the Item is new or any relevant field has changed.
    public boolean hasChanged(Item other) {
        if (other == null) {
            return true;
        }

        return !Objects.equals(name,other.name)
                || Double.compare(price,other.price) != 0
                || quantity != other.quantity
                || type != other.type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", type=" + type +
                ", tax=" + tax +
                '}';
    }
}