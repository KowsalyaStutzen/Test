package com.kows.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kows.myproject.common.Result;
import com.kows.myproject.entity.Customer;
import com.kows.myproject.repository.CustomerRepository;

import java.util.List;

@RestController
public class LoginController {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Result registerUser(@RequestBody Customer customer) {
        Customer savedCustomer = null;
        Result response = new Result();
        
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                response.addData("status",HttpStatus.CREATED);
                response.addData("message","Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response.addData("status",HttpStatus.INTERNAL_SERVER_ERROR);
            response.addData("message","An exception occured due to"+ex.getMessage());
        }
        return response;
    }

    @GetMapping("/user")
    public Result getUserDetailsAfterLogin(Authentication authentication) {
            
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("login successfully");
        

        List<Customer> customers = customerRepository.findByEmail(authentication.getName());
        if (customers.size() > 0) {
            result.addData("list",customers.get(0));
        } else {
            result.setSuccess(false);
        }

        return result;
    }

    @GetMapping("/userLogout")
    public Result logout() {
        
        System.out.println("logout");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("logout successfully");
        return result;
        
    }

}
