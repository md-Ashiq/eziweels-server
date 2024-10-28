package com.example.demo.controller;

import com.example.demo.dto.BookingResponseDTO;
import com.example.demo.model.BookingServices;
import com.example.demo.service.BookingServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin("https://eziweels.vercel.app/")
public class BookingServiceController {
    private BookingServicesService bookingServicesService;
    @Autowired
    public BookingServiceController(BookingServicesService bookingServicesService){
        this.bookingServicesService = bookingServicesService;
    }
    //Load the service providers who have the same service name

    @PostMapping
    public ResponseEntity<String> bookService(@RequestParam int customerId, @RequestParam int serviceProviderId){
        String bookingServices = bookingServicesService.bookService(customerId, serviceProviderId);
        return new ResponseEntity<>(bookingServices, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<BookingServices>> getAllBookingServices(){
        List<BookingServices> bookingServices = bookingServicesService.getBookingServices();
        return new ResponseEntity<>(bookingServices, HttpStatus.OK);
    }
    @GetMapping("/pending")
    public ResponseEntity<List<BookingResponseDTO>> getPendingServices(@RequestParam int customerId){
        List<BookingResponseDTO> bookingResponseDTOS = bookingServicesService.getPendingServices(customerId,"created");
        return new ResponseEntity<>(bookingResponseDTOS,HttpStatus.OK);
    }
    @GetMapping("/customer")
    public ResponseEntity<List<BookingResponseDTO>> getCustomerServices(@RequestParam int customerId){
        List<BookingResponseDTO> bookingResponseDTOS = bookingServicesService.getCustomerServices(customerId);
        return new ResponseEntity<>(bookingResponseDTOS,HttpStatus.OK);
    }
    @GetMapping("/serviceProvider")
    public ResponseEntity<List<BookingResponseDTO>> getServiceProviders(@RequestParam int serviceProviderId) {
        List<BookingResponseDTO> bookingResponseDTOS = bookingServicesService.getServiceProviders(serviceProviderId);
        return ResponseEntity.ok(bookingResponseDTOS);
    }
    @GetMapping("/serviceProvider/services")
    public ResponseEntity<List<BookingResponseDTO>> getServiceProvidersServices(@RequestParam int serviceProviderId) {
        List<BookingResponseDTO> bookingResponseDTOS = bookingServicesService.getServiceProvidersServices(serviceProviderId);
        return ResponseEntity.ok(bookingResponseDTOS);
    }
}
