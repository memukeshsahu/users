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
    public Users getUserDetailsById(int id, UserDTO userDto) throws ResourceNotFoundException {

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found :" + id));
        userDto.setId(user.getId());
        //userDto.setCity(user.getCity());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMiddleName(user.getMiddleName());
        //userDto.setState(user.getState());
        //      userDto.setPassword(user.getPassword());
        userDto.setEmailAddress(user.getEmailAdress());
        //userDto.setZipCode(user.getZipCode());
        userDto.setDob(user.getDob());
        //userDto.setProfilePicture(user.getProfilePicture());
        List<UserRoleMap> mapList = roleMapRepo.findByUserId(id);
        List<UserRoleMapDTO> roles = new ArrayList<UserRoleMapDTO>();

        mapList.forEach(roleList -> {
            UserRoleMapDTO dto = new UserRoleMapDTO();
            // dto.setUserId(String.valueOf(roleList.getId().getUserId()));
            dto.setUserRoleId(String.valueOf(roleList.getId().getUserRoleId()));
            Optional<UsersRole> roleMaster = roleRepo.findById(roleList.getId().getUserRoleId());
            dto.setRoleName(roleMaster.get().getRole());
            roles.add(dto);
            userDto.setRoles(roles);
        });
        List<Address> existingAddress = user.getAddress();
        List<AddressDto> addresses=new ArrayList<AddressDto>();
        for(Address mapAddress:existingAddress )
        {
            AddressDto addressDto=new AddressDto();
            addressDto.setId(mapAddress.getId());
            addressDto.setAddress(mapAddress.getAddress());
           //addressDto.setAddressType(mapAddress.getAddressTypeId());
          //  AddressType addressType=addressTypeRepository.findByAddressTypeId(mapAddress.getAddressTypeId());
            //addressDto.setAddressType(addressType.getAddressType().toString());
            
            addressDto.setCity(mapAddress.getCity());
            addressDto.setState(mapAddress.getState());
            addressDto.setZipCode(mapAddress.getZipCode());
            addresses.add(addressDto);
            userDto.setAddress(addresses);
            
        }

        return user;

    }


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
