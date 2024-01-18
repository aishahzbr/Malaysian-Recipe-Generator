package ftmk.bitp3453.labtest;

public class Pizza {
    private String orderID, pizzaID, quantity;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPizzaID() {
        return pizzaID;
    }

    public void setPizzaID(String pizzaID) {
        this.pizzaID = pizzaID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    //constructor
    public Pizza(String orderID, String pizzaID, String quantity) {
        this.orderID = orderID;
        this.pizzaID = pizzaID;
        this.quantity = quantity;
    }
}
