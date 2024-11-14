package com.example.demo.DAORepo;

import com.example.demo.GlobalContext;
import com.example.demo.Model.SearchService.SearchService;
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

    @Autowired 
    private GlobalContext globalContext;
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
        String query = "SELECT password, provider_id FROM ServiceProvider WHERE email = LOWER(?)";
        List<Map<String, Object>> results = jdbctemplate.queryForList(query, new Object[] {email});

        if (results.isEmpty())
        {
            return false;
        }

        String storedHashedPassword = (String) results.get(0).get("password");
        String serviceProviderId =   results.get(0).get("provider_id").toString();
        

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (globalContext.getServiceProviderId()== null && passwordEncoder.matches(password, storedHashedPassword)) {
        	globalContext.setServiceProviderId(serviceProviderId);
        }
        return passwordEncoder.matches(password, storedHashedPassword);
    }
    
    public int addServices(SearchService searchService) {
        String query = """
            INSERT INTO searchservice 
            (service_id, provider_id, name, description, price, status, skill, category, rating) 
            VALUES 
            (1, 
             (SELECT provider_id FROM serviceprovider WHERE name = ? LIMIT 1), 
             ?, ?, ?, ?, ?, ?, ?)
        """;
        
        return jdbctemplate.update(query, 
            searchService.getName(), // This is used in the subquery for matching provider name
            searchService.getName(), 
            searchService.getDescription(), 
            searchService.getPrice(), 
            searchService.getStatus(), 
            searchService.getSkill(), 
            searchService.getCategory(), 
            searchService.getRating());
    }
   
    public List<Map<String, Object>> getServices() {
    	
        String query = "SELECT price, name, description FROM searchservice WHERE provider_id = ?";
        List<Map<String, Object>> results = jdbctemplate.queryForList(query, globalContext.getServiceProviderId());
        return results;
    }

}





