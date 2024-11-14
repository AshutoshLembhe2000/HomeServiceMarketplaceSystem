package com.example.demo.ServiceProvider.Service;

import com.example.demo.DAORepo.ServiceProvider_Repository;
import com.example.demo.Model.SearchService.SearchService;
import com.example.demo.Model.ServiceProvider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServiceProviderService {
    @Autowired
    private final ServiceProvider_Repository serviceprovider_repository;


    public ServiceProviderService(ServiceProvider_Repository serviceprovider_repository) {
        this.serviceprovider_repository = serviceprovider_repository;
    }

    public int VerifyifServiceProviderExist(ServiceProvider serviceprovider)
    {
        List<Map<String, Object>> response=serviceprovider_repository.findServiceProvider(serviceprovider.getEmail());
        if(!response.isEmpty())
        {
            return 0;
        }
        else
        {
            return serviceprovider_repository.AddServiceProvider(serviceprovider);
        }
    }

    //
    public boolean validateLogin(String email, String password) {
        return serviceprovider_repository.ValidateLogin(email, password);
    }

    public int verifyServiceAddition(SearchService SearchService) {
        return serviceprovider_repository.addServices(SearchService);
    }
    
    public List<Map<String, Object>> getAllServiceProviderServices() {
        return serviceprovider_repository.getServices();
    }
}
