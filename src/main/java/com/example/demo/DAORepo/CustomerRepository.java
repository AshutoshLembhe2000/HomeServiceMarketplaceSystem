package com.example.demo.DAORepo;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Customer.*;
import com.example.demo.Model.SearchServices.SearchService;
import com.example.demo.Model.SearchServices.SearchServiceRowMapper;

@Repository
public class CustomerRepository {

	private JdbcTemplate queryTemplate;
	
	//getter for template
    public JdbcTemplate getQueryTemplate() {
		return queryTemplate;
	}

    //setter for template
    @Autowired //This is managed by springboot now
	public void setQueryTemplate(JdbcTemplate queryTemplate) {
		this.queryTemplate = queryTemplate;
	}

    //Query to find if customer exists while creating new account
	public List<Map<String, Object>> findcustomer(String name) {
    	String query = "SELECT name FROM customer WHERE LOWER(name) = LOWER(?)";
    	return queryTemplate.queryForList(query,new Object[]{name});
    }
	
	//Query to new customer to the database
	public int addcustomer(Customer customer) {
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(customer.getPassword());

		String query = "INSERT INTO CUSTOMER (name,email,password,phone_number,city) VALUES (?,?,?,?,?)";
		return queryTemplate.update(query, customer.getName(), customer.getEmail(), hashedPassword, customer.getPhoneno(), customer.getCity());
	}
	
	//Query to check for login with and password
	public List<Map<String, Object>> loginCustomer(String name, String password){
		String query = "SELECT name, password FROM customer WHERE LOWER(name) = LOWER(?) and LOWER(password) = LOWER(?)";
    	return queryTemplate.queryForList(query,new Object[]{name,password});
	}
	
	//Query to display the name on the html page
	public Customer getCustomerByNamequery(String name) {
		String query = "SELECT * FROM customer WHERE name = ?";
		List<Customer> customers= queryTemplate.query(query, new CustomerRowMapper(),name);
		return customers.isEmpty() ? null : customers.get(0);
	}
}