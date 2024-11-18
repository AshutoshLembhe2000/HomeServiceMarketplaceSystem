package com.example.demo.Service.ServiceProvider;

import com.example.demo.DAORepo.ServiceProvider_Repository;
import com.example.demo.Model.Booking.ServiceProviderBookingDTO;
import com.example.demo.Model.OTPService.OTPService;
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
    
    public List<SearchService> getAllServiceProviderServices() {
        return serviceprovider_repository.getServices();
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


    public void updateBookingStatus(String bookingId, String status) {
        serviceprovider_repository.updateBookingStatus(bookingId, status);
    }


    public boolean verifyOTP(String otpCode, String bookingId) {
        OTPService otpService = serviceprovider_repository.findOTPByCode(otpCode, bookingId);
        return otpService != null;
    }

    public void updateBookingStatusToCompleted(String bookingId) {
        // Use the repository to update the booking status
        serviceprovider_repository.updateBookingStatusTOComplete(bookingId, "Completed");
    }

    // Booked_Service
    public List<ServiceProviderBookingDTO> getBookedServices()
    {
        return serviceprovider_repository.findBookedServices(globalContext.getServiceProviderId());
    }

    public List<ServiceProviderBookingDTO> getPastBookings() {
        return serviceprovider_repository.getPastBookings(globalContext.getServiceProviderId());
    }


    // Dhruv and Achyutam
    public int verifyServiceAddition(SearchService searchService) {
        List<SearchService> existingServices = serviceprovider_repository.getServices();
        if (!existingServices.isEmpty()) {
            return -1; // Return a specific value indicating that the provider already has a service
        }
        return serviceprovider_repository.addServices(searchService);
    }



}
