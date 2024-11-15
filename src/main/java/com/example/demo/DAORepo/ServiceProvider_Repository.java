package com.example.demo.DAORepo;

import com.example.demo.GlobalContext;

import com.example.demo.Model.SearchServices.SearchService;
import com.example.demo.Model.SearchServices.SearchServiceRowMapper;

import com.example.demo.Model.Booking.BookingRowMapper;
import com.example.demo.Model.SearchService.SearchService;

import com.example.demo.Model.ServiceProvider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import com.example.demo.Model.Booking.ServiceProviderBookingDTO;


@Repository
public class ServiceProvider_Repository {

    @Autowired
    private GlobalContext globalcontext;

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
        boolean passwordMatches = passwordEncoder.matches(password, storedHashedPassword);
        if (passwordMatches) {
            // Set serviceProviderId in GlobalContext on successful login
            globalcontext.setServiceProviderId(serviceProviderId);
        }
        return passwordMatches;
    }

    public List<ServiceProviderBookingDTO> findBookedServices(String serviceProviderId) {
        String query = """
                SELECT 
                    b.booking_id AS booking_id,
                    b.status AS booking_status,
                    b.booking_date,
                    c.name AS customer_name,
                    ss.skill
                FROM 
                    booking b
                JOIN 
                    customer c ON b.customer_id = c.customer_id
                JOIN 
                    searchservice ss ON b.service_id = ss.service_id
                WHERE
                    ss.provider_id = ?;
                """;

        return jdbctemplate.query(query, new Object[]{serviceProviderId}, new BookingRowMapper());
    }

    public int addServices(SearchService searchService) {
        String query = """
            INSERT INTO searchservice 
            (service_id, provider_id, name, price, status, skill, category, rating) 
            VALUES 
            (1, 
             (SELECT provider_id FROM serviceprovider WHERE name = ? LIMIT 1), 
             ?, ?, ?, ?, ?, ?, ?)
        """;
        
        return jdbctemplate.update(query, 
            searchService.getProviderName(), // This is used in the subquery for matching provider name
            searchService.getProviderName(),          
            searchService.getPrice(), 
            searchService.getStatus(), 
            searchService.getSkill(), 
            searchService.getCategory(), 
            searchService.getRating());
    }
   
    @SuppressWarnings("deprecation")
	public List<SearchService> getServices() {
    	String query = "SELECT ss.service_id, ss.provider_id, ss.skill, ss.rating, ss.price, ss.status, " +
                "sp.name AS provider_name, sp.city, ss.category " +
                "FROM searchservice ss " +
                "JOIN serviceprovider sp ON ss.provider_id = sp.provider_id " +
                "WHERE ss.provider_id = ?";

        //String query = "SELECT price, name, service_id description FROM searchservice WHERE provider_id = ?";
        //List<Map<String, Object>> results = jdbctemplate.queryForList(query, globalContext.getServiceProviderId(), new SearchServiceRowMapper());
        return jdbctemplate.query(query, new Object[]{globalContext.getServiceProviderId()}, new SearchServiceRowMapper());

    }


    public int deleteSelectedServiceByID(String providerName, String ServiceId, String ServiceProviderId) {
    	String query = "DELETE FROM `searchservice` WHERE (`service_id` = ?);\r\n"
    			+ "";

        //String query = "SELECT price, name, service_id description FROM searchservice WHERE provider_id = ?";
    	int rowsAffected = jdbctemplate.update(query, ServiceId);
        return rowsAffected;
    }
    
    public int updateAndSave(SearchService searchService, String ServiceProviderId) {
    	 String query = "UPDATE searchservice " +
                 "SET price = ?,  category = ?, rating = ? " +
                 "WHERE service_id = ? AND provider_id = ?";
    	 String provider_id = globalContext.getServiceProviderId();
    	return jdbctemplate.update(query, 		
    			searchService.getPrice(),   			
    			searchService.getCategory(), 
    			searchService.getRating(),
    			searchService.getServiceId(), 
    			provider_id
    			);
    }
    
    public List<SearchService>  getServiceForModify( String providerName, String ServiceId, String ServiceProviderId) {
    	
    	String query = "SELECT ss.service_id, ss.provider_id, ss.skill, ss.rating, ss.price, ss.status, " +
                "sp.name AS provider_name, sp.city, ss.category " +
                "FROM searchservice ss " +
                "JOIN serviceprovider sp ON ss.provider_id = sp.provider_id " +
                "WHERE ss.provider_id = ? and ss.service_id = ?";

    	 return jdbctemplate.query(query, new Object[]{ServiceProviderId,globalContext.getServiceProviderId() }, new SearchServiceRowMapper());

    	
    	/*String query = "SELECT ss.service_id, ss.provider_id, ss.skill, ss.rating, ss.price, ss.status, " +
                "sp.name AS provider_name, sp.city, ss.category " +
                "FROM searchservice ss " +
                "JOIN serviceprovider sp ON ss.provider_id = sp.provider_id " +
                "WHERE ss.provider_id = ? and ss.service_id = ?";
*/
    	
    }

    public void updateBookingStatus(String bookingId, String status) {
        String query = "UPDATE booking SET status = ? WHERE booking_id = ?";
        jdbctemplate.update(query, status, bookingId);
    }

}