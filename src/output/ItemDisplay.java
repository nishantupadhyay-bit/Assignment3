package output;

import item.Items;

public class ItemDisplay {

    public static void display(Items item,double[] result) {
        System.out.println("Item Name: " + item.getName());
        System.out.println("Price: " + item.getPrice());
        System.out.println("Quantity: " + item.getQuantity());
        System.out.println("Type: " + item.getType());
        System.out.println("Total Price: " + item.getPrice() * item.getQuantity());
        System.out.println("Tax: " + result[0]);
        System.out.println("Final Price: " + result[1]);
        System.out.println("-----------------------------");
    }
}