package com.techriff.userdetails.jwtAutontication.security.configuration.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.dto.PermissionJwtDto;
import com.techriff.userdetails.dto.RolesJwtDto;
import com.techriff.userdetails.dto.UsersRolePermissionDto;
import com.techriff.userdetails.entity.Permission;
import com.techriff.userdetails.entity.RolesPermissionMap;
import com.techriff.userdetails.entity.UserRoleMap;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.repository.PermissionRepository;
import com.techriff.userdetails.repository.RolePermissionMapRepository;
import com.techriff.userdetails.repository.UserRoleMapRepository;
import com.techriff.userdetails.repository.UsersRepository;
import com.techriff.userdetails.repository.UsersRoleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtill {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UsersRoleRepository roleRepository;
	@Autowired
	private UserRoleMapRepository roleMapRepo;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RolePermissionMapRepository rolePermissionMapRepository;
	private String SECRET_KEY = "secret";

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String existingEmailId = userDetails.getUsername();
		Users existUsers = usersRepository.findByEmailAdress(existingEmailId);
	
		claims.put("userName", existUsers.getFirstName());
		claims.put("userId",existUsers.getId());
		List<UserRoleMap> userRoleMaps = roleMapRepo.findByUserId(existUsers.getId());
		List<String> permission = new ArrayList<String>();
		//String displayPermission="";

		for (UserRoleMap userRoleMap : userRoleMaps) {
			int roleId=userRoleMap.getId().getUserRoleId();
			List<RolesPermissionMap> rolesPermissionMaps = rolePermissionMapRepository.findByRoleId(roleId);
			for (RolesPermissionMap rolesPermissionMap : rolesPermissionMaps) 
			{
				int permissionId=rolesPermissionMap.getId().getPermissionId_FK();
				Optional<Permission> existingPermission=permissionRepository.findById(permissionId);

				permission.add(existingPermission.get().getPermission());
				//displayPermission=displayPermission+ permission.toString();
				//displayPermission = displayPermission.substring(1, displayPermission.length() - 1);

				
			}
			List<String> distinctElements = permission.stream()
			.distinct()
			.collect(Collectors.toList());
			claims.put("permissions", distinctElements);

			
		}








		return createToken(claims, userDetails.getUsername());
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// 1st step
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()
		.setClaims(claims).
		setSubject(subject).
		setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
