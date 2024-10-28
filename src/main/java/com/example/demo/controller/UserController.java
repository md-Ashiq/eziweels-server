package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Customer;
import com.example.demo.model.ServiceProvider;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private UserService userService;
    private MyUserDetailService myUserDetailService;
    @Autowired
    public UserController( UserService userService, MyUserDetailService myUserDetailService) {
        this.userService = userService;
        this.myUserDetailService = myUserDetailService;
    }


    // Get all users with DTO conversion
    @GetMapping("/customer")
    public ResponseEntity<List<GetCustomerResponseDTO>> getUsers() {
        List<GetCustomerResponseDTO> getCustomerResponseDTOS = userService.getCustomers();
        return new ResponseEntity<>(getCustomerResponseDTOS,HttpStatus.OK);
    }

    // Get a user by ID with DTO conversion
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getUserById(@PathVariable int id) {
        Customer customer = userService.getCustomerById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // User login method with UserResponseDTO
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO res = userService.getUserResponse(loginRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        Boolean isServiceProvider = userDTO.getIsServiceProvider();
        String response = "";
        if (isServiceProvider){
             response = userService.addServiceProvider(userDTO);
        }else{
             response = userService.createCustomer(userDTO);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/customer/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO,@PathVariable int id) {
        String response = userService.updateCustomer(userDTO, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete user by ID
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        String response = userService.deleteCustomer(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // EzServiceProviders
    // Posting the service provider and his service in EzServiceProviders
    @PostMapping("/ezServiceProviders")
    public ResponseEntity<String> addEzServiceProviders(@RequestBody EzServiceProvidersRequestDTO ezServiceProvidersRequestDTO){
        String response = userService.createEzServiceProvider(ezServiceProvidersRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED );
    }
    @PutMapping("/ezServiceProviders")
    public ResponseEntity<String> updateEzServiceProvider(@RequestBody EzServiceProvidersRequestDTO ezServiceProvidersRequestDTO){
        String response = userService.updateEzServiceProvider(ezServiceProvidersRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED );
    }
    // Getting EzServiceProviders
    @GetMapping("/ezServiceProviders")
    public ResponseEntity<List<TitleResponseDTO>> getEzServiceProviders(){
        List<TitleResponseDTO> titleResponseDTOS = userService.getAllEzServiceProviders();
        return new ResponseEntity<>(titleResponseDTOS,HttpStatus.OK);
    }
    @GetMapping("/ezServiceProvidersById")
    public ResponseEntity<List<TitleResponseDTO>> getEzServiceProvidersById(@RequestParam int id){
        List<TitleResponseDTO> titleResponseDTOS = userService.getAllEzServiceProvidersById(id);
        return new ResponseEntity<>(titleResponseDTOS,HttpStatus.OK);
    }
    //Service Provider controller

    @GetMapping("/serviceProvider")
    public ResponseEntity<List<ServiceProvider>> getServiceProviders(){
        List<ServiceProvider> serviceProviders = userService.getServiceProviders();
        return new ResponseEntity<>(serviceProviders, HttpStatus.OK );
    }
    @GetMapping("/serviceProvider/{id}")
    public ResponseEntity<ServiceProvider> getServiceProvider(@PathVariable int id){
        ServiceProvider serviceProvider = userService.getServiceProvider(id);
        return new ResponseEntity<>(serviceProvider, HttpStatus.OK);
    }
    @PutMapping("/serviceProvider/{id}")
    public ResponseEntity<String> updateServiceProvider(@RequestBody UserDTO userDTO,@PathVariable int id){
        String res = userService.updateServiceProvider(userDTO, id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/serviceProvider/{id}")
    public ResponseEntity<String> deleteServiceProvider(@PathVariable int id){
        String res = userService.deleteServiceProvider(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/filterByTitle")
    public ResponseEntity<List<TitleResponseDTO>> filterByTitle(@RequestParam String title) {
        List<TitleResponseDTO> providers = userService.findByTitle(title);
        if (providers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Or return an appropriate message
        }
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping("/filterByCity")
    public ResponseEntity<List<ServiceProvider>> getServiceProviders(@RequestParam String city) {
        List<ServiceProvider> providers = userService.getServiceProvidersByCity(city);
        if (providers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(providers);
    }

}
