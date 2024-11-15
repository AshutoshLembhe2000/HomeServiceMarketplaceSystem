package com.example.demo.Service.ServiceProvider;

import com.example.demo.DAORepo.ServiceProvider_Repository;
import com.example.demo.Model.Booking.ServiceProviderBookingDTO;
import com.example.demo.Model.SearchServices.SearchService;
import com.example.demo.Model.ServiceProvider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.GlobalContext;
import java.util.List;
import java.util.Map;

@Service
public class ServiceProviderService {
    @Autowired
    private final ServiceProvider_Repository serviceprovider_repository;
    @Autowired
    private final GlobalContext globalContext;

    public ServiceProviderService(ServiceProvider_Repository serviceprovider_repository, GlobalContext globalContext) {
        this.serviceprovider_repository = serviceprovider_repository;
        this.globalContext = globalContext;
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
    
    public List<SearchService> getAllServiceProviderServices() {
        return serviceprovider_repository.getServices();
    }

    // Booked_Service
    public List<ServiceProviderBookingDTO> getBookedServices()
    {

        return serviceprovider_repository.findBookedServices(globalContext.getServiceProviderId());
    }
    
    public int deleteSelectedService(String providerName, String ServiceId)
    {
        return serviceprovider_repository.deleteSelectedServiceByID( providerName,  ServiceId, globalContext.getServiceProviderId());
    }
    
    public int updateService(SearchService searchService)
    {
        return serviceprovider_repository.updateAndSave( searchService, globalContext.getServiceProviderId());
    }
    
    public List<SearchService> getServiceForModify( String providerName, String ServiceId)
    {
        return serviceprovider_repository.getServiceForModify(providerName,  ServiceId, globalContext.getServiceProviderId());
    }
}
