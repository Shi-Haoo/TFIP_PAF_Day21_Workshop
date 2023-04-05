package sg.edu.nus.iss.workshop21.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import sg.edu.nus.iss.workshop21.model.Customer;
import sg.edu.nus.iss.workshop21.model.Order;
import sg.edu.nus.iss.workshop21.repository.CustomerRepository;

@RestController
@RequestMapping(path="/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestController {
    
    @Autowired
    CustomerRepository repo;

    @GetMapping
    public ResponseEntity<String> getAllCustomers(@RequestParam(required=false, defaultValue = "5") String limit, 
    @RequestParam(required=false, defaultValue = "0") String offset){
        
        List<Customer> customer = repo.getAllCustomer(Integer.parseInt(limit), Integer.parseInt(offset));
        
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for(Customer c : customer){
             jab.add(c.toJson());

        }

        JsonArray jsArr = jab.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsArr.toString());
    }

    @GetMapping(path="{customer_id}")
    public ResponseEntity<String> getCustomerById(@PathVariable String customer_id){

        Optional<Customer> cust = repo.getCustomerById(Integer.parseInt(customer_id));

        if(cust.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Json.createObjectBuilder()
                            .add("message","record not found for customer with id %s".formatted(customer_id))
                            .build()
                            .toString());
        }

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(cust.get().toJson().toString());
    }

    @GetMapping(path="/{customer_id}/orders")
    public ResponseEntity<String> getOrderByCustomerId(@PathVariable String customer_id){

        Optional<List<Order>> optionalOrders = repo.getOrderByCustomerId(Integer.parseInt(customer_id));
        
        //orElse() from Optional returns the value of the Optional if it is present, 
        //or the specified default value if it is not. The specified default value I stated it as empty list
        //Thus, the orElse(new ArrayList<>()) method call returns an empty list if the Optional is empty, 
        //or the list of orders if it is not.

        //Without using this, I wont be able to iterate over the list 
        
        List<Order> orders = optionalOrders.orElse(new ArrayList<>());

        if(orders.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("error message: Record not found");

        }

        JsonArrayBuilder jarr = Json.createArrayBuilder();

        for(Order o : orders){
            jarr.add(o.toJson());
        }

        JsonArray jResults = jarr.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jResults.toString());

    }

}
