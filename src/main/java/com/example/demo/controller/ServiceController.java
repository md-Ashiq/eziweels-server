package com.example.demo.controller;

import com.example.demo.dto.ServiceResponseDTO;
import com.example.demo.model.EzServices;
import com.example.demo.service.EzServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceController {
    private EzServicesService ezServicesService;
    @Autowired
    public ServiceController(EzServicesService ezServicesService){
        this.ezServicesService = ezServicesService;
    }
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getUsers(){
        List<ServiceResponseDTO> ezServices = ezServicesService.getServices();
        return new ResponseEntity<>(ezServices, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EzServices> getUserById(@PathVariable int id){
        EzServices ezServices = ezServicesService.getServiceById(id);
        if (!ezServices.equals(null)){

            return new ResponseEntity<>(ezServices, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody EzServices ezServices){
        String response = ezServicesService.createServicer(ezServices);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<EzServices> updateUser(@RequestBody EzServices ezServices){
        EzServices newEzServices = ezServicesService.updateService(ezServices);
        return new ResponseEntity<>(newEzServices,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        String response = ezServicesService.deleteService(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
