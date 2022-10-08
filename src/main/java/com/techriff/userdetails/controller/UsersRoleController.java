package com.techriff.userdetails.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.Exception.RoleCanNotBeDeletedException;
import com.techriff.userdetails.Exception.RoleNotFoundException;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.pages.RolesPage;
import com.techriff.userdetails.pages.RolesSearchCriteria;
import com.techriff.userdetails.service.UsersRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Bearer Authentication")

public class UsersRoleController {
	
	private static Logger log= LogManager.getLogger(UsersRoleController.class);

	@Autowired
	private UsersRoleService roleService;
//	private static Logger logger = LogManager.getLogger(UsersRoleController.class);

	@PostMapping("/role")
	@Operation(summary = "Add user role ", description = " Add role into  db ")


	public ResponseEntity<UsersRole> addUsersRole( @RequestBody UsersRole usersRole) {
		UsersRole  addRole = roleService.addUsersRole(usersRole);
		return new ResponseEntity<>(addRole, new HttpHeaders(), HttpStatus.CREATED);
	}
	@PostMapping("/roles")
    @Operation(summary = "Add list of user role ", description = " Add role into  db ")


    public ResponseEntity<List<UsersRole>> addUsersRoles( @RequestBody List<UsersRole> usersRole) {
	    List<UsersRole>  addRole = roleService.addUsersRoles(usersRole);
        return new ResponseEntity<>(addRole, new HttpHeaders(), HttpStatus.CREATED);
    }
	
	
	@GetMapping("/roles")
	@Operation(summary = "Get user role ", description = " Get list of user role available in db ")

	public ResponseEntity<Page<UsersRole>> getUserRole(RolesPage rolesPage,RolesSearchCriteria rolesSearchCriteria) {
		Page<UsersRole> getAllUsers = roleService.getAllUserRole(rolesPage,rolesSearchCriteria);
		return new ResponseEntity<>(getAllUsers, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("roles/{id}")
	@Operation(summary = "Get user role by id", description = " Get  user role by id ")

	public ResponseEntity<UsersRole> getUserRoleById(@PathVariable int id) throws Exception {
		
		UsersRole getUsers = roleService.getUserRoleById(id);
		
		return new ResponseEntity<>(getUsers, new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("roles/{id}")
	@Operation(summary = "Delete user role ", description = " Delete user role by id ")

	public String deleteRoleById(@PathVariable int id) throws Exception
	{
		String deleteUser = roleService.deleteUserRoleById(id);
		return deleteUser;
		
	}
	@PutMapping("roles/{id}")
	@Operation(summary = "Update user role ", description = " Update user role by id ")

public ResponseEntity<UsersRole> updateUserRoleById(@PathVariable int id ,@RequestBody UsersRole usersRole) throws Exception {
		
		UsersRole updateUsers = roleService.updateRole(usersRole);
		
		return new ResponseEntity<>(updateUsers, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
}
