package com.example.demo.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.Model.ServiceProvider.ServiceProvider;
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

		Object serviceProviderAttribute = model.getAttribute("ServiceProvider");
		assertNotNull(serviceProviderAttribute);
		assertTrue(serviceProviderAttribute instanceof ServiceProvider);
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
		// fail("Not yet implemented");
	}

	
}
