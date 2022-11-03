package com.techriff.userdetails.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.Exception.RoleCanNotBeDeletedException;
import com.techriff.userdetails.Exception.RoleNotFoundException;
import com.techriff.userdetails.dto.RolesDto;
import com.techriff.userdetails.dto.RolesPermissionMapDto;
import com.techriff.userdetails.entity.Permission;
import com.techriff.userdetails.entity.RolePermissionMapPK;
import com.techriff.userdetails.entity.RolesPermissionMap;
import com.techriff.userdetails.entity.UserRoleMap;
import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.pages.RolesPage;
import com.techriff.userdetails.pages.RolesSearchCriteria;
import com.techriff.userdetails.repository.PermissionRepository;
import com.techriff.userdetails.repository.RoleCriteriaManagerRepository;
import com.techriff.userdetails.repository.RolePermissionMapRepository;
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
	@Autowired PermissionRepository permissionRepository;
	@Autowired RolePermissionMapRepository rolePermissionMapRepository;
	
	//private static Logger logger = LogManager.getLogger(UsersRoleService.class);

	public UsersRole addUsersRole(UsersRole usersRole) throws ResourceNotFoundException {
		UsersRole saveRoles=roleRepository.save(usersRole);
		saveRoles.setRole(usersRole.getRole());
		List<Integer> permission = usersRole.getPermissionId();
		
		for (int permissionId : permission) {
			Optional<Permission> ExistingPermission=permissionRepository.findById(permissionId);
			if(ExistingPermission.isPresent())
			{
			RolesPermissionMap roleData = new RolesPermissionMap();
			roleData.setId(new RolePermissionMapPK(saveRoles.getId(), permissionId));
		   
			rolePermissionMapRepository.save(roleData);
			}
			else
			{
				roleRepository.delete(saveRoles);
			throw new ResourceNotFoundException("Invalid Permission id");
			}
		}
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
		List<UserRoleMap> existingRoleMap=mapRepository.findByRoleId(existingRoleId);
		if(existingRoleMap!=null )
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

	public RolesDto getUserRoleById(int id, RolesDto rolesDto) throws ResourceNotFoundException {

		UsersRole existingRole=roleRepository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("Invalid Roles id"+id));
		rolesDto.setRoleId(id);
		rolesDto.setRole(existingRole.getRole());
		List<RolesPermissionMap> rolesPermissionMaps =rolePermissionMapRepository.findByRoleId(existingRole.getId());
		List<RolesPermissionMapDto> rolesPermissionMapDto=new ArrayList<RolesPermissionMapDto>();
		for (RolesPermissionMap existingRolesPermissionMap : rolesPermissionMaps) {
			RolesPermissionMapDto existingRolesPermissionMapDto=new RolesPermissionMapDto();
			Optional<Permission> maps=permissionRepository.findById(existingRolesPermissionMap.getId().getPermissionId());
			int permissionId=maps.get().getId();
			existingRolesPermissionMapDto.setPermissionId(permissionId);
			Optional<Permission> permission=permissionRepository.findById(permissionId);
			existingRolesPermissionMapDto.setPermission(permission.get().getPermission());
			rolesPermissionMapDto.add(existingRolesPermissionMapDto);
		}
		rolesDto.setPermission(rolesPermissionMapDto);



		return rolesDto;
	}

}
