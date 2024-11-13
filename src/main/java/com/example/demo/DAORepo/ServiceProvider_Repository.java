package com.example.demo.DAORepo;

import com.example.demo.Model.ServiceProvider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


@Repository
public class ServiceProvider_Repository {

    @Autowired
    private JdbcTemplate jdbctemplate;

    // getter for template
    public JdbcTemplate getJdbctemplate() {
        return jdbctemplate;
    }

    //setter for template
    public void setJdbctemplate(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    // method to find if srviceProvider exist while registering
    public List<Map<String, Object>> findServiceProvider(String email) {
        String query = "SELECT email FROM ServiceProvider WHERE LOWER(email) = LOWER(?)";
        return jdbctemplate.queryForList(query, new Object[]{email});
    }

    // Method to save service provider details
    public int AddServiceProvider(ServiceProvider serviceProvider) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(serviceProvider.getPassword());

        String query = "INSERT INTO ServiceProvider (name,email,password,phone,city) VALUES(?,?,?,?,?)";
        return jdbctemplate.update(query, serviceProvider.getName(), serviceProvider.getEmail(), hashedPassword, serviceProvider.getPhoneno(), serviceProvider.getCity());
    }

    // Method to validate login
    public boolean ValidateLogin(String email, String password) {
        String query = "SELECT password FROM ServiceProvider WHERE email = LOWER(?)";
        List<Map<String, Object>> results = jdbctemplate.queryForList(query, new Object[] {email});

        if (results.isEmpty())
        {
            return false;
        }

        String storedHashedPassword = (String) results.get(0).get("password");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, storedHashedPassword);
    }
}





