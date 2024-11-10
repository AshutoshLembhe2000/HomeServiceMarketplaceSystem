package com.example.demo.DAORepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

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

    public String findUser(String name) {
    	String query = "SELECT * FROM customer WHERE name = " + name;
    	SqlRowSet match=queryTemplate.queryForRowSet(query);
		return match;
    }
    // Add additional methods for update, delete, etc.
}