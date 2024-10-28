package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private ServiceProviderRepo serviceProviderRepo;
    private CustomerRepo customerRepo;
    private AddressRepo addressRepo;
    private EzServiceProvidersRepo ezServiceProvidersRepo;
    private EzServiceRepo ezServiceRepo;
    private AuthenticationManager authManager;
    private JWTService jwtService;
    @Autowired
    public UserService(JWTService jwtService, AuthenticationManager authManager, EzServiceRepo ezServiceRepo,EzServiceProvidersRepo ezServiceProvidersRepo, CustomerRepo customerRepo, ServiceProviderRepo serviceProviderRepo, AddressRepo addressRepo) {
        this.customerRepo = customerRepo;
        this.serviceProviderRepo = serviceProviderRepo;
        this.addressRepo = addressRepo;
        this.ezServiceProvidersRepo = ezServiceProvidersRepo;
        this.ezServiceRepo = ezServiceRepo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }
    // Get all users
    public List<GetCustomerResponseDTO> getCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream()
                .map(this::convertToGetCustomerResponseDTO)
                .collect(Collectors.toList());
    }
    private GetCustomerResponseDTO convertToGetCustomerResponseDTO(Customer customer){
        GetCustomerResponseDTO getCustomerResponseDTO = new GetCustomerResponseDTO();
        getCustomerResponseDTO.setId(customer.getId());
        getCustomerResponseDTO.setEmail(customer.getEmail());
        getCustomerResponseDTO.setPassword(customer.getPassword());
        getCustomerResponseDTO.setMobileNumber(customer.getMobileNumber());
        getCustomerResponseDTO.setDate(customer.getDate());
        return getCustomerResponseDTO;
    }

    // Get user by ID
    public Customer getCustomerById(int id) {
        Optional<Customer> user = customerRepo.findById(id);
        return user.orElse(null); // return null if no user is found
    }

    // Create a new user
    public String createCustomer(UserDTO userDTO) {
        Customer customer = new Customer();
        customer.setEmail(userDTO.getEmail());
        customer.setPassword(encoder.encode(userDTO.getPassword()));
        customer.setMobileNumber(userDTO.getMobileNumber());
        customer.setDate(new Date());
        customerRepo.save(customer);
        return "User created successfully!";
    }

    // Update an existing user
    public String updateCustomer(UserDTO userDTO, int id) {
        Customer oldCustomer = customerRepo.findById(id).orElse(null);
        if (!oldCustomer.equals(null)){
            oldCustomer.setEmail(userDTO.getEmail());
            oldCustomer.setPassword(userDTO.getPassword());
            oldCustomer.setMobileNumber(userDTO.getMobileNumber());
            customerRepo.save(oldCustomer);
            return "service provider updated";
        }else{
            return "service provider not found";
        }
    }

    // Delete user by ID
    public String deleteCustomer(int id) {
        customerRepo.deleteById(id);
        return "User deleted successfully!";
    }

   // User login method that returns a UserResponseDTO
    public LoginResponseDTO getUserResponse(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        if (authentication.isAuthenticated()){
            Customer existingUser = customerRepo.findByEmail(loginRequestDTO.getEmail());
            ServiceProvider existingServiceProvider = serviceProviderRepo.findByEmail(loginRequestDTO.getEmail());
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            if (existingUser != null ) {
                loginResponseDTO.setEmail(existingUser.getEmail());
                loginResponseDTO.setUser(true);
                loginResponseDTO.setId(existingUser.getId());
                loginResponseDTO.setServiceProvider(false);
            } else if(existingServiceProvider != null ){
                loginResponseDTO.setEmail(existingServiceProvider.getEmail());
                loginResponseDTO.setUser(true);
                loginResponseDTO.setId(existingServiceProvider.getId());
                loginResponseDTO.setServiceProvider(true);
            }
            String res = jwtService.generateToken(loginRequestDTO.getEmail());
            loginResponseDTO.setjWtToken(res);
            return loginResponseDTO;
        }else {
            return new LoginResponseDTO(null,0,false,false);
        }
    }
    /*

    // Convert User entity to UserDTO
    public UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setServiceProvider(user.getServiceProvider());
        userDTO.setDate(user.getDate());
        return userDTO;
    }

    // Convert UserDTO to User entity
    public User convertToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setServiceProvider(userDTO.isServiceProvider());
        user.setDate(userDTO.getDate());
        return user;
    }*/



    // Service Provider Services
    public List<ServiceProvider> getServiceProviders(){
        List<ServiceProvider> serviceProviders = serviceProviderRepo.findAll();
        return serviceProviders;
    }
    public ServiceProvider getServiceProvider(int id){
        ServiceProvider serviceProvider = serviceProviderRepo.findById(id).orElse(null);
        return serviceProvider;
    }
    public String addServiceProvider(UserDTO userDTO){
        Address address = new Address();
        address.setCity(userDTO.getCity());
        address.setStreet(userDTO.getStreet());
        address.setsCName(userDTO.getName());
        address.setState(userDTO.getState());
        addressRepo.save(address);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setEmail(userDTO.getEmail());
        serviceProvider.setPassword(encoder.encode(userDTO.getPassword()));
        serviceProvider.setMobileNumber(userDTO.getMobileNumber());
        serviceProvider.setServiceTitle(userDTO.getServiceTitle());
        serviceProvider.setPrice(userDTO.getPrice());
        serviceProvider.setRating(userDTO.getRating());
        serviceProvider.setDate(new Date());
        serviceProvider.setAddress(address);
        serviceProviderRepo.save(serviceProvider);

        return "service added";
    }
    public String updateServiceProvider(UserDTO userDTO, int id) {
        ServiceProvider oldServiceProvider = serviceProviderRepo.findById(id).orElse(null);
        if (oldServiceProvider != null){
            if (userDTO.getEmail() != null){
                oldServiceProvider.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPassword() != null){
                oldServiceProvider.setPassword(userDTO.getPassword());
            }
            if (userDTO.getMobileNumber() != null){
                oldServiceProvider.setMobileNumber(userDTO.getMobileNumber());
            }
            if (userDTO.getServiceTitle() != null){
                oldServiceProvider.setServiceTitle(userDTO.getServiceTitle());
            }
            if (userDTO.getPrice() != oldServiceProvider.getPrice()){
                oldServiceProvider.setPrice(userDTO.getPrice());
            }
            if (userDTO.getRating() != oldServiceProvider.getRating()){
                oldServiceProvider.setRating(userDTO.getRating());
            }
            if (oldServiceProvider.getAddress() == null) {
                oldServiceProvider.setAddress(new Address());
            }
            Address address = oldServiceProvider.getAddress();
            if (userDTO.getCity() != null){
                address.setCity(userDTO.getCity());
            }
            if (userDTO.getState() != null){
                address.setState(userDTO.getState());
            }
            if (userDTO.getStreet() != null){
                address.setStreet(userDTO.getStreet());
            }
            if (userDTO.getName() != null){
                address.setsCName(userDTO.getName());
            }
        if (oldServiceProvider.getAddress() == null) {
            oldServiceProvider.setAddress(address);
        }
        serviceProviderRepo.save(oldServiceProvider);
        addressRepo.save(address);
            return "service provider updated successfully";
        }
        else{
            return "service provider not present";
        }
    }
    public String deleteServiceProvider(int id){
        ServiceProvider oldServiceProvider = serviceProviderRepo.findById(id).orElse(null);
        serviceProviderRepo.delete(oldServiceProvider);
        return "service provider deleted successfully";
    }

    public List<TitleResponseDTO> findByTitle(String title) {
        List<EzServiceProviders> ezServiceProviders =  ezServiceProvidersRepo.findByTitle(title);
        return ezServiceProviders.stream()
                .map(this::convertToTitleResponseDTO)
                .collect(Collectors.toList());
    }
    private TitleResponseDTO convertToTitleResponseDTO(ServiceProvider serviceProvider){
        Address address = serviceProvider.getAddress();
        TitleResponseDTO titleResponseDTO = new TitleResponseDTO();
        titleResponseDTO.setId(serviceProvider.getId());
        titleResponseDTO.setCompanyName(address.getsCName());
        titleResponseDTO.setPrice(serviceProvider.getPrice());
        titleResponseDTO.setRating(serviceProvider.getRating());
        titleResponseDTO.setCity(address.getCity());
        titleResponseDTO.setMobileNumber(serviceProvider.getMobileNumber());
        titleResponseDTO.setStreet(address.getStreet());
        return titleResponseDTO;
    }

    public List<ServiceProvider> getServiceProvidersByCity(String city){
        List<ServiceProvider> providers = serviceProviderRepo.findByAddressCity(city);
        return providers;
    }
   /* public String updateServiceProvider(ServiceProvider serviceProvider){
        ServiceProvider oldServiceProvider = serviceProviderRepo.findById(serviceProvider.getId()).orElse(null);
        if (!oldServiceProvider.equals(null)){
            oldServiceProvider.setNameOfCompany(serviceProvider.getNameOfCompany());
            oldServiceProvider.setMobileNumber(serviceProvider.getMobileNumber());
            oldServiceProvider.setAddress(serviceProvider.getAddress());
            oldServiceProvider.setPrice(serviceProvider.getPrice());
            oldServiceProvider.setRating(serviceProvider.getRating());
            oldServiceProvider.setTitle(serviceProvider.getTitle());
            serviceProviderRepo.save(oldServiceProvider);
            return "service provider updated";
        }else{
            return "service provider not found";
        }
    }



    public ServiceProvider createServiceProvider(int userId, ServiceProvider serviceProviderDetails) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getServiceProvider()) {
            throw new IllegalArgumentException("This user is not a service provider.");
        }

        // Set user details in ServiceProvider
        serviceProviderDetails.setUser(user);

        // Save the ServiceProvider entity
        return serviceProviderRepo.save(serviceProviderDetails);
    }*/
    public String createEzServiceProvider(EzServiceProvidersRequestDTO ezServiceProvidersRequestDTO){
        EzServiceProviders ezServiceProviders = new EzServiceProviders();
        ServiceProvider serviceProvider = serviceProviderRepo.findById(ezServiceProvidersRequestDTO.getServiceProviderId()).orElse(null);
        EzServices ezServices = ezServiceRepo.findById(ezServiceProvidersRequestDTO.getEzServicesId()).orElse(null);
        ezServiceProviders.setEzServices(ezServices);
        ezServiceProviders.setServiceProvider(serviceProvider);
        ezServiceProviders.setPrice(ezServiceProvidersRequestDTO.getPrice());
        ezServiceProviders.setRating(ezServiceProvidersRequestDTO.getRating());
        ezServiceProviders.setTitle(ezServiceProvidersRequestDTO.getTitle());
        ezServiceProvidersRepo.save(ezServiceProviders);
        return "EzService Provider created";
    }
    public String updateEzServiceProvider(EzServiceProvidersRequestDTO ezServiceProvidersRequestDTO){
        EzServiceProviders ezServiceProviders = ezServiceProvidersRepo.findById(ezServiceProvidersRequestDTO.getId()).orElse(null);
        ServiceProvider serviceProvider = serviceProviderRepo.findById(ezServiceProvidersRequestDTO.getServiceProviderId()).orElse(null);
        EzServices ezServices = ezServiceRepo.findById(ezServiceProvidersRequestDTO.getEzServicesId()).orElse(null);
        if (ezServiceProviders != null ){
            ezServiceProviders.setEzServices(ezServices);
            ezServiceProviders.setServiceProvider(serviceProvider);
            if(ezServiceProvidersRequestDTO.getPrice() != 0){
                ezServiceProviders.setPrice(ezServiceProvidersRequestDTO.getPrice());
            }
            if (ezServiceProvidersRequestDTO.getRating() != 0.0){
                ezServiceProviders.setRating(ezServiceProvidersRequestDTO.getRating());
            }
            if (ezServiceProvidersRequestDTO.getTitle() != null){
                ezServiceProviders.setTitle(ezServiceProvidersRequestDTO.getTitle());
            }
            ezServiceProvidersRepo.save(ezServiceProviders);
            return "EzService Provider updated";
        }else{
            return "No EzServiceProvider Found";
        }

    }
    public List<TitleResponseDTO> getAllEzServiceProviders(){
        List<EzServiceProviders> ezServiceProviders = ezServiceProvidersRepo.findAll();
        return ezServiceProviders.stream()
                .map(this::convertToTitleResponseDTO)
                .collect(Collectors.toList());
    }
    public List<TitleResponseDTO> getAllEzServiceProvidersById(int id){
        List<EzServiceProviders> ezServiceProviders = ezServiceProvidersRepo.findByServiceProvider(id);
        return ezServiceProviders.stream()
                .map(this::convertToTitleResponseDTO)
                .collect(Collectors.toList());
    }

    private TitleResponseDTO convertToTitleResponseDTO(EzServiceProviders ezServiceProviders){
        ServiceProvider serviceProvider = ezServiceProviders.getServiceProvider();
        Address address = serviceProvider.getAddress();
        EzServices ezServices = ezServiceProviders.getEzServices();
        TitleResponseDTO titleResponseDTO = new TitleResponseDTO();
        titleResponseDTO.setId(ezServiceProviders.getId());
        titleResponseDTO.setPrice(ezServiceProviders.getPrice());
        titleResponseDTO.setRating(ezServiceProviders.getRating());
        titleResponseDTO.setStreet(address.getStreet());
        titleResponseDTO.setCompanyName(address.getsCName());
        titleResponseDTO.setTitle(ezServices.getTitle());
        titleResponseDTO.setCity(address.getCity());
        titleResponseDTO.setMobileNumber(serviceProvider.getMobileNumber());
        return titleResponseDTO;
    }
}
