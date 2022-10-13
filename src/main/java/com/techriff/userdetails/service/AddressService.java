package com.techriff.userdetails.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.dto.AddressDto;
import com.techriff.userdetails.dto.UserDTO;
import com.techriff.userdetails.dto.UserRoleMapDTO;
import com.techriff.userdetails.entity.Address;
import com.techriff.userdetails.entity.AddressType;
import com.techriff.userdetails.entity.UserRoleMap;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.repository.AddressRepository;
import com.techriff.userdetails.repository.AddressTypeRepository;
import com.techriff.userdetails.repository.UserRoleMapRepository;
import com.techriff.userdetails.repository.UsersRepository;
import com.techriff.userdetails.repository.UsersRoleRepository;

@Service
public class AddressService {
    
   @Autowired private UsersRepository usersRepository;
   @Autowired private UserRoleMapRepository roleMapRepo;
  @Autowired private  AddressTypeRepository addressTypeRepository;
  @Autowired private UsersRoleRepository roleRepo;
    @Autowired private AddressRepository addressRepository; 


    public List<Address> getAddress() {
        
        return addressRepository.findAll();
    }


    public String updateAddressType(int userId,int addressesId) throws ResourceNotFoundException {
        try {
            List<Address> existingData=addressRepository.findAddress(userId);
            
            for(Address updateAddresstypeId:existingData)
            {
                updateAddresstypeId.setAddressTypeId(2);
                addressRepository.save(updateAddresstypeId);
            }
        } catch (Exception e) {
            new ResourceNotFoundException("Address Not Found with  userId :" + userId);

        }
       
       Address existingAddressTypeId=addressRepository.findById(addressesId).orElseThrow(
               () -> new ResourceNotFoundException("Address Not Found with addressId :" + addressesId)
               );
       existingAddressTypeId.setAddressTypeId(1);
       addressRepository.save(existingAddressTypeId);
        
        return "Update Complete.....";
    }


   

}
