package com.techriff.userdetails.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.dto.UserDTO;
import com.techriff.userdetails.entity.Address;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.service.AddressService;
import com.techriff.userdetails.service.UsersDetailsService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class AddressController {
    @Autowired
    private AddressService addressService;
    //@Autowired private AddressService ;
     
//    @GetMapping("users/address/")
//    public ResponseEntity<List<Address>> getUserDetailsById(){
//       // logger.info("UsersController:getUserById execution started..");
//        //logger.info("UsersDetailsService:GetUserById request payload {} ", Mapper.mapToJsonString(id));
//       // UserDTO userDto = new UserDTO();
//        //Users getUsersById = addressService.getAddress();
//        //logger.info("UsersController:getUserById response from service ");
//
//        return new ResponseEntity<>(addressService.getAddress(), new HttpHeaders(), HttpStatus.OK);
//
//    }
    @PutMapping("users/{userId}/address/{addressId}/primary")
    public ResponseEntity<String> updateAddressType( @PathVariable int userId,@PathVariable int addressId) throws ResourceNotFoundException
    {
       String updateAddress =addressService.updateAddressType(userId,addressId)
;        return new ResponseEntity<>(updateAddress, new HttpHeaders(),HttpStatus.OK);
    }
    

}
