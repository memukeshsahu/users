package com.techriff.userdetails.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.dto.PasswordDto;
import com.techriff.userdetails.dto.SpUsersDto;
import com.techriff.userdetails.dto.UserDTO;
import com.techriff.userdetails.entity.PasswordModel;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.pages.UsersPage;
import com.techriff.userdetails.pages.UsersSearchCriteria;
import com.techriff.userdetails.service.PasswordService;
import com.techriff.userdetails.service.UsersDetailsService;
import com.techriff.userdetails.util.Mapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class UsersController {
    @Autowired
    private UsersDetailsService usersService;
    @Autowired
    private PasswordService passwordService;

    private static Logger logger= Logger.getLogger(UsersController.class);

    //	private static Logger logger = LogManager.getLogger(UsersController.class);

    // Showing All the List of users
    //	@GetMapping("/users")
    //	public ResponseEntity<JSONObject> getUserDetails() {
    //		JSONObject list = usersService.getAllUsersDetails();
    //		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    //	}


    @GetMapping("/users/{id}")
    @Cacheable(cacheNames = "users",key = "#id")
    @Operation(summary = "Get user by Id", description = "Get user by Id")
    public ResponseEntity<Object> getUserDetailsById(@PathVariable("id") int id) throws ResourceNotFoundException {
        logger.info("UsersController:getUserById execution started..");
        //logger.info("UsersDetailsService:GetUserById request payload {} ", Mapper.mapToJsonString(id));
        UserDTO userDto = new UserDTO();
        Users getUsersById = usersService.getUserDetailsById(id, userDto);
        logger.info("UsersController:getUserById response from service ");

        return new ResponseEntity<>(userDto, new HttpHeaders(), HttpStatus.OK);

    }

    //	@PostMapping("/users")
    //	public ResponseEntity<Users> addUser(@Valid @RequestParam(name = "userData") String userData,
    //			@RequestParam(name = "file", required = false) MultipartFile[] file) throws Exception {
    //		Gson gson = new Gson();
    //		Users data = gson.fromJson(userData, Users.class);
    //
    //		Users addUser = usersService.addUserDetails(data,file);
    //	return new ResponseEntity<>(addUser, new HttpHeaders(), HttpStatus.OK);
    //
    //}
    //	
    // add users without file
    @Operation(summary = "Add users", description = "Add users details ")
    @PostMapping("/users")
    public ResponseEntity<Users> addUser(@Valid @RequestBody Users users) throws Exception {
        Users addUser = usersService.addUserDetails(users);
        return new ResponseEntity<>(addUser, new HttpHeaders(), HttpStatus.OK);

    }

    // @PutMapping("/users")
    @Operation(summary = "Delete user by Id", description = "Detelte user by Id")
    @DeleteMapping("/users/{id}")
    @CacheEvict(cacheNames = "users",key = "#id")
    public String DeleteUser(@PathVariable("id") int id) throws ResourceNotFoundException {
        return usersService.deleteuserById(id);
    }
    
    @Operation(summary = "Get list of  user details", description = "Get user details with pagination and sorting filter")
    @GetMapping("/users")
    @Cacheable(value="users")  
    public ResponseEntity<JSONObject> getUsersDetaiils(UsersPage usersPage, UsersSearchCriteria usersSearchCriteria) {

        return new ResponseEntity<>(usersService.getUserDetails(usersPage, usersSearchCriteria), new HttpHeaders(),
                HttpStatus.OK);
    }
    @PutMapping("/users/{emailAddress}/changePassword")
  public ResponseEntity<String>  resetPassword(@RequestBody PasswordDto password,@PathVariable String emailAddress) throws Exception
  {
        String resetPassword=passwordService.resetPassword(password,emailAddress);
        
        return new ResponseEntity<>(resetPassword,new HttpHeaders(),HttpStatus.OK);
  }
//    @PutMapping("/users/resetPassword/{temporaryPassword}")
//    public ResponseEntity<String>resetPassword(@PathVariable String temporaryPassword, PasswordModel passwordModel) throws Exception
//    
//    {
//        String resetPassword= passwordService.resetPassword(temporaryPassword, passwordModel);
//        return new ResponseEntity<>(resetPassword,new HttpHeaders(),HttpStatus.OK);
//        
//    }

@GetMapping("/users/spsearch")
public ResponseEntity<List<SpUsersDto>> getUserDeatailsByNameAndEmail(@RequestParam String email, @RequestParam String name)
{   
    //Map<String, String> susers =new HashMap<>();
    List<SpUsersDto> users= usersService.getUserDeatailsByNameAndEmail(email,name);
    return new ResponseEntity<>(users,new HttpHeaders(),HttpStatus.OK);
    
}
    

}
