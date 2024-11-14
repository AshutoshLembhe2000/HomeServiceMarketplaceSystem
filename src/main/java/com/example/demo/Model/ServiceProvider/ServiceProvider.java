package com.example.demo.Model.ServiceProvider;
import org.springframework.stereotype.Component;

import com.example.demo.Model.User.User;

@Component
public class ServiceProvider extends User {

	@Override
    public String getUserType() {
        return "ServiceProvider";
    }
}
