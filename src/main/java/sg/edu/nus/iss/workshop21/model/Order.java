package sg.edu.nus.iss.workshop21.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {
    
    private int id;
    private String shipName;
    private Customer customer;
    private Double shippingFee;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getShipName() {
        return shipName;
    }
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Double getShippingFee() {
        return shippingFee;
    }
    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Order() {
    }

    public Order(int id, String shipName, Customer customer, Double shippingFee) {
        this.id = id;
        this.shipName = shipName;
        this.customer = customer;
        this.shippingFee = shippingFee;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", shipName=" + shipName + ", customer=" + customer + ", shippingFee=" + shippingFee
                + "]";
    }

    //transfer data from SQL into fields in Order object in Java
    public static Order createFromSqlResult(SqlRowSet rs){

        Order order = new Order();
        Customer c = new Customer();

        order.setId(rs.getInt("id"));
        order.setShipName(rs.getString("ship_name"));
        c.setId(rs.getInt("customer_id"));
        order.setCustomer(c);
        order.setShippingFee(rs.getDouble("shipping_fee"));
        return order;

    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("orderId", this.getId())
                .add("shipName", this.getShipName())
                .add("customer_id", this.getCustomer().getId())
                .add("shippingFee", this.getShippingFee())
                .build();
    }
    

    
    

    
}


