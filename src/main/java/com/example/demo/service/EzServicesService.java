package com.example.demo.service;
import com.example.demo.dto.ServiceResponseDTO;
import com.example.demo.model.EzServices;
import com.example.demo.repository.EzServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EzServicesService {

    private EzServiceRepo ezServiceRepo;
    @Autowired
    public EzServicesService(EzServiceRepo ezServiceRepo){
        this.ezServiceRepo =ezServiceRepo;
    }

    public List<ServiceResponseDTO> getServices(){
        List<EzServices> ezServices = ezServiceRepo.findAll();
        return ezServices.stream()
                .map(this::convertToServiceResponseDTO)
                .collect(Collectors.toList());
    }
    private ServiceResponseDTO convertToServiceResponseDTO(EzServices ezServices){
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setId(ezServices.getId());
        serviceResponseDTO.setTitle(ezServices.getTitle());
        serviceResponseDTO.setDescription(ezServices.getDescription());
        serviceResponseDTO.setImageUrl(ezServices.getImageUrl());
        return serviceResponseDTO;
    }
    public EzServices getServiceById(int id){
        EzServices ezServices = ezServiceRepo.findById(id).orElse(null);
        return ezServices;
    }
    public String createServicer(EzServices ezServices){
        ezServiceRepo.save(ezServices);
        return "Service created Successfully";
    }
    public EzServices updateService(EzServices ezServices){
        EzServices oldEzServices = ezServiceRepo.findById(ezServices.getId()).orElse(null);
        if (!oldEzServices.equals(null)){
            oldEzServices.setTitle(ezServices.getTitle());
            oldEzServices.setDescription(ezServices.getDescription());
            oldEzServices.setImageUrl(ezServices.getImageUrl());
        }
        ezServiceRepo.save(oldEzServices);
        return oldEzServices;
    }
    public String deleteService(int id){
        EzServices ezServices = ezServiceRepo.findById(id).orElse(null);
        ezServiceRepo.delete(ezServices);
        return "Service deleted Successfully";
    }
}
