package com.bookstore.customerservice.service;
import com.bookstore.customerservice.dto.CustomerDto;
import com.bookstore.customerservice.entity.Address;
import com.bookstore.customerservice.entity.CustomerProfile;
import com.bookstore.customerservice.repository.AddressRepository;
import com.bookstore.customerservice.repository.CustomerProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomerService {
    @Autowired private CustomerProfileRepository profileRepository;
    @Autowired private AddressRepository addressRepository;
    public CustomerDto.ProfileResponse getProfile(String userId){
        return toProfileResponse(profileRepository.findByUserId(userId).orElseThrow(()->new EntityNotFoundException("Profile not found")));
    }
    public CustomerDto.ProfileResponse createProfile(String userId,CustomerDto.ProfileRequest req){
        CustomerProfile p=CustomerProfile.builder().userId(userId).fullName(req.getFullName()).phone(req.getPhone()).preferences(req.getPreferences()).build();
        return toProfileResponse(profileRepository.save(p));
    }
    public CustomerDto.ProfileResponse updateProfile(String userId,CustomerDto.ProfileRequest req){
        CustomerProfile p=profileRepository.findByUserId(userId).orElseThrow(()->new EntityNotFoundException("Profile not found"));
        p.setFullName(req.getFullName()); p.setPhone(req.getPhone()); p.setPreferences(req.getPreferences());
        return toProfileResponse(profileRepository.save(p));
    }
    public List<CustomerDto.AddressResponse> getAddresses(String userId){
        return addressRepository.findByUserId(userId).stream().map(this::toAddressResponse).collect(Collectors.toList());
    }
    public CustomerDto.AddressResponse addAddress(String userId,CustomerDto.AddressRequest req){
        if(req.isDefault()) addressRepository.findByUserId(userId).forEach(a->{a.setDefault(false);addressRepository.save(a);});
        Address a=Address.builder().userId(userId).street(req.getStreet()).city(req.getCity()).state(req.getState()).zipCode(req.getZipCode()).country(req.getCountry()).isDefault(req.isDefault()).build();
        return toAddressResponse(addressRepository.save(a));
    }
    public void deleteAddress(Long id){ addressRepository.deleteById(id); }
    public CustomerDto.AddressResponse setDefault(Long id,String userId){
        addressRepository.findByUserId(userId).forEach(a->{a.setDefault(false);addressRepository.save(a);});
        Address a=addressRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Address not found"));
        a.setDefault(true); return toAddressResponse(addressRepository.save(a));
    }
    private CustomerDto.ProfileResponse toProfileResponse(CustomerProfile p){
        return CustomerDto.ProfileResponse.builder().id(p.getId()).userId(p.getUserId()).fullName(p.getFullName()).phone(p.getPhone()).preferences(p.getPreferences()).build();
    }
    private CustomerDto.AddressResponse toAddressResponse(Address a){
        return CustomerDto.AddressResponse.builder().id(a.getId()).street(a.getStreet()).city(a.getCity()).state(a.getState()).zipCode(a.getZipCode()).country(a.getCountry()).isDefault(a.isDefault()).build();
    }
}