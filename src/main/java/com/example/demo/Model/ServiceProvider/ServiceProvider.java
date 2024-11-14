package com.example.demo.Model.ServiceProvider;
import com.example.demo.Model.User.User;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Component
public class ServiceProvider extends User {

    @Override
    public String getUserType()
    {
        return "ServiceProvider";
    }
}
