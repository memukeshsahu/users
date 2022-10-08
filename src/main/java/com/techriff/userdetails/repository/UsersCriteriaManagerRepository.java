package com.techriff.userdetails.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.pages.UsersPage;
import com.techriff.userdetails.pages.UsersSearchCriteria;

@Repository
public class UsersCriteriaManagerRepository {
	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;
	public UsersCriteriaManagerRepository(EntityManager entityManager)
	{
		this.entityManager=entityManager;
		this.criteriaBuilder=entityManager.getCriteriaBuilder();
	}

	public Page<Users> findAllWithFilters(UsersPage usersPage,
			UsersSearchCriteria usersSearchCriteria)
	
	{
		CriteriaQuery<Users> criteriaQuery=criteriaBuilder.createQuery(Users.class);
		Root<Users> usersRoot=criteriaQuery.from(Users.class);
		Predicate predicate=getPredicate(usersSearchCriteria,usersRoot);
		criteriaQuery.where(predicate);
		setOrder(usersPage,criteriaQuery,usersRoot);
		TypedQuery<Users> typedQuery=entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(usersPage.getPageNumber()*usersPage.getPageSize());
		typedQuery.setMaxResults(usersPage.getPageSize());
		Pageable pageable=getPageable(usersPage);
		long usersCount=getUsersCount(predicate);
		
		return new PageImpl<>(typedQuery.getResultList(),pageable,usersCount);
	}

	private long getUsersCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery=criteriaBuilder.createQuery(Long.class);
		Root<Users> countRoot=countQuery.from(Users.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}

	private Pageable getPageable(UsersPage usersPage) {
		Sort sort=Sort.by(usersPage.getSortDirection(),usersPage.getSortBy());
		return PageRequest.of(usersPage.getPageNumber(), usersPage.getPageSize(), sort);
	}

	private void setOrder(UsersPage usersPage, CriteriaQuery<Users> criteriaQuery, Root<Users> usersRoot) {
		if(usersPage.getSortDirection().equals(Sort.Direction.ASC))
		{
			criteriaQuery.orderBy(criteriaBuilder.asc(usersRoot.get( usersPage.getSortBy())));
		}
		else
		{
			criteriaQuery.orderBy(criteriaBuilder.desc(usersRoot.get( usersPage.getSortBy())));
			
		}
		
	}

	private Predicate getPredicate(UsersSearchCriteria usersSearchCriteria, 
			Root<Users> usersRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if(Objects.nonNull(usersSearchCriteria.getFirstName()))
		{
			predicates.add(
					criteriaBuilder.like(usersRoot.get("firstName"), 
							 "%"+usersSearchCriteria.getFirstName()+ "%")
					);
			
		}
		if(Objects.nonNull(usersSearchCriteria.getLastName()))
		{
			predicates.add(
					criteriaBuilder.like(usersRoot.get("lastName"), 
							 "%"+usersSearchCriteria.getLastName()+ "%")
					);
			
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
	

}