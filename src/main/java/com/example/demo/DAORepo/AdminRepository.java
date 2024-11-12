package com.example.demo.DAORepo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Admin.Admin;

@Repository
public class AdminRepository {
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

	public List<Map<String, Object>> findAdmin(String name) {
    	String query = "SELECT name FROM admin WHERE LOWER(name) = LOWER(?)";
    	return queryTemplate.queryForList(query,new Object[]{name});
    }
	
	public int addadmin(Admin admin) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(admin.getPassword());
		String query="INSERT INTO ADMIN (name,email,password) VALUES (?,?,?)";
		return queryTemplate.update(query,admin.getName(),admin.getEmail(),hashedPassword);
	}
}
