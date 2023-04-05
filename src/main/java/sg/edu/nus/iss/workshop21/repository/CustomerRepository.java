package sg.edu.nus.iss.workshop21.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop21.model.Customer;
import sg.edu.nus.iss.workshop21.model.Order;

import static sg.edu.nus.iss.workshop21.repository.DBQueries.*;

@Repository

public class CustomerRepository {
    
    @Autowired
    JdbcTemplate template;

    public List<Customer> getAllCustomer(int limit, int offset){

        List<Customer> customers = new ArrayList<>();

        SqlRowSet rs = template.queryForRowSet(SELECT_ALL_CUSTOMERS, limit, offset);

        while(rs.next()){
            customers.add(Customer.createFromSqlResults(rs));
        }
        return customers;
    }

    public Optional<Customer> getCustomerById(int id){
        
        //SqlRowSet will be empty but not NUll if id cannot be found.

        SqlRowSet rs = template.queryForRowSet(SELECT_CUSTOMER_BY_ID, id);

        //Check if rowset contain result in first row. Only use this if you expect only one result or none back

        if(rs.first()){
            return Optional.of(Customer.createFromSqlResults(rs));
        }

        return Optional.empty();
        
    }

    public Optional<List<Order>> getOrderByCustomerId(int id){

            //SqlRowSet will be empty but not NUll if id cannot be found.
            SqlRowSet results = template.queryForRowSet(SELECT_ORDERS_BY_CUSTOMER_ID, id);
            List<Order> orders = new ArrayList<>();
        
            while(results.next()){
                orders.add(Order.createFromSqlResult(results));
            }
        
            //If id is not found, SqlRowSet will be empty and thus orders will also be empty
            //Thus return Optional with no value
            
            if (orders.isEmpty()) {
                return Optional.empty();
            }
        
            return Optional.of(orders);
        }

}

