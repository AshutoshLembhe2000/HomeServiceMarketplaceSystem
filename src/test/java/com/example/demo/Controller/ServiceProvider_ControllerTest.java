package com.example.demo.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.Model.Booking.ServiceProviderBookingDTO;
import com.example.demo.Model.SearchServices.SearchService;
import com.example.demo.Model.ServiceProvider.ServiceProviderStateManager;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Service.Customer.CustomerService;
import com.example.demo.Service.ServiceProvider.GlobalContext;
import com.example.demo.Service.ServiceProvider.ServiceProviderService;

class ServiceProvider_ControllerTest {

	@InjectMocks
	private ServiceProvider_Controller serviceProvider_Controller;
	@Mock
	private ServiceProviderService serviceproviderservice;
	@Mock
	private IUserFactory userFactory;
	@Mock
	private GlobalContext globalcontext;
	@Mock
	private ServiceProviderStateManager serviceProviderStateManager;
	@Mock
	private CustomerService customerService;

	@Mock
	private Model model;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoginForm() {
		String viewName = serviceProvider_Controller.LoginForm(model);
		assertEquals("ServiceProvider_login", viewName);
	}

	@Test
	void testLoginServiceProvider() {
		String email = "test@gmail.com";
		String correctPassword = "1234";
		String wrongPassword = "12341";
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		when(serviceproviderservice.validateLogin(email, correctPassword)).thenReturn(true);
		RedirectView result = serviceProvider_Controller.loginServiceProvider(email, correctPassword,
				redirectAttributes);

		assertThat(result).isNotNull();
		assertThat(result.getUrl()).isEqualTo("ServiceProviderWelcomeScreen");

		when(serviceproviderservice.validateLogin(email, wrongPassword)).thenReturn(false);
		RedirectView result2 = serviceProvider_Controller.loginServiceProvider(email, wrongPassword,
				redirectAttributes);

		assertThat(result2).isNotNull();
		assertThat(result2.getUrl()).isEqualTo("ServiceProviderLoginForm");
	}

	@Test
	void testServiceProviderForm() {
		String view = serviceProvider_Controller.serviceProviderForm(model);
		assertEquals("ServiceProviderWelcomeScreen", view);
	}
	
	@Test
	void testAddServiceForm() {
		String view = serviceProvider_Controller.addServiceForm(model);
		assertEquals("addServiceForm", view);
	}
	
	@Test
	void testAddServiceItem() throws SQLException {
		SearchService searchService =new SearchService();
		when(serviceproviderservice.verifyServiceAddition(searchService)).thenReturn(-1);
		String result = serviceProvider_Controller.addServiceItem(searchService);
		
		when(serviceproviderservice.verifyServiceAddition(searchService)).thenReturn(1);
		String result2 = serviceProvider_Controller.addServiceItem(searchService);
		
		assertEquals("You can only add one service per ServiceProvider.", result);
		assertEquals("Service Added Sucesfully!", result2);
	}

	@Test
	void testViewBooking() {
		ServiceProviderBookingDTO ServiceProviderBookingDTO = new ServiceProviderBookingDTO();
		ServiceProviderBookingDTO.setCustomerName("Test");
		 List<ServiceProviderBookingDTO> bookings = new ArrayList<>();
		 bookings.add(ServiceProviderBookingDTO);
		 
		 when(serviceproviderservice.getPastBookings()).thenReturn(bookings);
		 String view =  serviceProvider_Controller.viewBooking(model);
		 assertEquals("ServiceProviderBookedServices", view);
		 
	}
	
}
