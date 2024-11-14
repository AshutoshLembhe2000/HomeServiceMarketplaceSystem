package com.example.demo.DAORepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceProvider_Repository {

    @Autowired
    JdbcTemplate jdbctemplate;

    // Method to save service provider details
    public String setServiceProviderDetails(long id, String name, String email, String password, String phone, String city) {
        jdbctemplate.update("INSERT INTO ServiceProvider(provider_id, name, email, password, phone, city) VALUES(?,?,?,?,?,?)",
                id, name, email, password, phone, city);
        return "Data Updated";
    }

    // Method to validate login
    public boolean validateLogin(String email, String password) {
        String sql = "SELECT COUNT(*) FROM ServiceProvider WHERE email = ? AND password = ?";
        @SuppressWarnings("deprecation")
		Integer count = jdbctemplate.queryForObject(sql, new Object[]{email, password}, Integer.class);
        return count != null && count > 0;  // Returns true if a matching record is found
    }


}
