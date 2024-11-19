package com.example.demo.Model.ServiceProvider;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAORepo.ServiceProvider_Repository;
import com.example.demo.Model.Customer.Customer;
import com.example.demo.Model.Customer.ICustomerObserver;
import com.example.demo.Model.User.User;

@Component
public class ServiceProvider extends User implements IServiceProviderSubject{
	
	    private String providerId;
	    private String currentState;
	    private IServiceProviderStatusState state;
	    private String status;
	    
	    private final List<Customer> observers = new ArrayList<>();


	    public void setState(IServiceProviderStatusState state) {
	    	 if (state == null) {
	    	        throw new IllegalArgumentException("State cannot be null");
	    	    }

	        this.state = state;
	        this.currentState = state.getStateName(); // Update state name
	        notifyObservers("Service Provider state changed to: " + state.getStateName());
	    }

	    public void handleState(ServiceProvider_Repository serviceProviderRepository) {
	        state.handleStatusState(this, serviceProviderRepository);
	    }

	    public boolean canAcceptBooking() {
	        return state.canAcceptRejectBooking();
	    }

	    public String getStateName() {
	        return state.getStateName();
	    }
	    
	    
	    @Override
	    public String getUserType() {
	        return "ServiceProvider";
	    }

		public Object getProviderId() {
			// TODO Auto-generated method stub
			return providerId;
		}
		
		public void setProviderId(String providerId) {
			// TODO Auto-generated method stub
			this.providerId =providerId;
		}
		
		public String getStatus() {
	        return state.getStateName();
	    }
		
		public void setStatus(String status) {
	        this.status = status;
	    }


		@Override
		public void addObserver(Customer observer) {
			// TODO Auto-generated method stub
			observers.add(observer);
		}

		@Override
		public void notifyObservers(String message) {
			// TODO Auto-generated method stub
			for (Customer observer : observers) {
	            observer.update(message,  observer.getEmail());
	        }
		}
		
	}




