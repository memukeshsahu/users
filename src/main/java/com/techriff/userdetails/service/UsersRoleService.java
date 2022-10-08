package com.techriff.userdetails.service;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.Exception.RoleCanNotBeDeletedException;
import com.techriff.userdetails.Exception.RoleNotFoundException;
import com.techriff.userdetails.entity.UserRoleMap;
import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.pages.RolesPage;
import com.techriff.userdetails.pages.RolesSearchCriteria;
import com.techriff.userdetails.repository.RoleCriteriaManagerRepository;
import com.techriff.userdetails.repository.UserRoleMapRepository;
import com.techriff.userdetails.repository.UsersRoleRepository;

@Service
@Profile(value = { "local", "dev","test"})

public class UsersRoleService {
	private static Logger log= Logger.getLogger(UsersRoleService.class);

	@Autowired
	private UsersRoleRepository roleRepository;
	@Autowired
	private UserRoleMapRepository mapRepository;
	@Autowired
	private RoleCriteriaManagerRepository roleCriteriaManagerRepository;
	
	//private static Logger logger = LogManager.getLogger(UsersRoleService.class);

	public UsersRole addUsersRole(UsersRole usersRole) {
		// TODO Auto-generated method stub
		UsersRole saveRoles=roleRepository.save(usersRole);
		saveRoles.setRole(usersRole.getRole());
		return saveRoles;
	}

	public Page<UsersRole> getAllUserRole(RolesPage rolesPage, RolesSearchCriteria rolesSearchCriteria) {
		
		return roleCriteriaManagerRepository.findAllWithFilters(rolesPage, rolesSearchCriteria);
	}

	public UsersRole getUserRoleById(int id) throws Exception{
		return roleRepository.findById(id).orElseThrow(()-> new RoleNotFoundException("Role Not Found"));
	}

	public String deleteUserRoleById(int id) throws RoleNotFoundException, RoleCanNotBeDeletedException {
		UsersRole existingRole=roleRepository.findById(id).orElseThrow(()-> new RoleNotFoundException("Role Not Found"));
		int existingRoleId=existingRole.getId();
		UserRoleMap userRoleMap=new UserRoleMap();
		UserRoleMap existingRoleMap=mapRepository.findByRoleId(existingRoleId);
		if(existingRoleMap!=null  )
		{
			throw  new RoleCanNotBeDeletedException("Role can't be deleted as it has assigned users");
		}
		
		
		roleRepository.deleteById(id);
		 return "role deleted" +id;
	}

	public UsersRole updateRole(UsersRole usersRole) throws RoleNotFoundException {
		UsersRole existingRole=roleRepository.findById(usersRole.getId()).orElseThrow(()-> new RoleNotFoundException("Role Not Found"));
		UsersRole saveRoles=roleRepository.save(usersRole);
		saveRoles.setRole(usersRole.getRole());
		return saveRoles;
	}
	public List<UsersRole>  addUsersRoles(List<UsersRole>  usersRole) {
        // TODO Auto-generated method stub
        List<UsersRole> saveRoles=roleRepository.saveAll(usersRole);
        for(UsersRole role:saveRoles)
        {
            UsersRole  role2 = new UsersRole();
            role2.setRole(role.getRole());
            
        }
        
        return saveRoles;
    }

}
