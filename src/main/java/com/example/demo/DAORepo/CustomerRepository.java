package com.example.demo.DAORepo;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Customer.Customer;

@Repository
public class CustomerRepository {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
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

	public List<Map<String, Object>> findcustomer(String name) {
    	String query = "SELECT name FROM customer WHERE LOWER(name) = LOWER(?)";
    	return queryTemplate.queryForList(query,new Object[]{name});
    }
	
	public int addcustomer(Customer customer) {
		String hashedPassword = passwordEncoder.encode(customer.getPassword());

		String query = "INSERT INTO CUSTOMER (name,email,password,phone_number,city) VALUES (?,?,?,?,?)";
		return queryTemplate.update(query, customer.getName(), customer.getEmail(), hashedPassword, customer.getPhoneno(), customer.getCity());
	}
}