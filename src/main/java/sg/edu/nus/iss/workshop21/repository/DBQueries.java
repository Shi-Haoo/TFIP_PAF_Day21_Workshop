package sg.edu.nus.iss.workshop21.repository;

public class DBQueries {
    public static final String SELECT_ALL_CUSTOMERS = "select id, company, first_name, last_name from customers1 limit ? offset ?";
    public static final String SELECT_CUSTOMER_BY_ID = "select id, company, first_name, last_name from customers1 where id = ?;";
    public static final String SELECT_ORDERS_BY_CUSTOMER_ID = "select c.id as customer_id, o.ship_name, o.id as order_id, o.shipping_fee from customers1 c, orders o where c.id = o.customer_id and customer_id = ?;";
}
