package com.example.demo.Admin.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.DAORepo.AdminRepository;
import com.example.demo.Model.Admin.Admin;

@Service
public class AdminService {
	private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository  = adminRepository;
    }
    
    //Logic to check if User already exists or not.
    public int addAdmin(Admin admin) {
    	List<Map<String, Object>> response=adminRepository.findAdmin(admin.getName());
    	if(!response.isEmpty()) {
    		return 0;    		
    	}
    	else
    	{
    		return adminRepository.addadmin(admin);
    	}

    }
}
