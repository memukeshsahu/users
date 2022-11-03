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

import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.pages.RolesPage;
import com.techriff.userdetails.pages.RolesSearchCriteria;

@Repository
public class RoleCriteriaManagerRepository {

	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;

	public RoleCriteriaManagerRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public Page<UsersRole> findAllWithFilters(RolesPage rolesPage, RolesSearchCriteria rolesSearchCriteria) {
		CriteriaQuery<UsersRole> criteriaQuery = criteriaBuilder.createQuery(UsersRole.class);
		Root<UsersRole> usersRoleRoot = criteriaQuery.from(UsersRole.class);
		Predicate predicate = getPredicate(rolesSearchCriteria, usersRoleRoot);
		criteriaQuery.where(predicate);
		setOrder(rolesPage, criteriaQuery, usersRoleRoot);
		TypedQuery<UsersRole> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(rolesPage.getPageNumber() * rolesPage.getPageSize());
		typedQuery.setMaxResults(rolesPage.getPageSize());
		Pageable pageable = getPageable(rolesPage);
		long usersCount = getUsersCount(predicate);

		return new PageImpl<>(typedQuery.getResultList(), pageable,usersCount);

	}

	private long getUsersCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<UsersRole> countRoot = countQuery.from(UsersRole.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}

	private Pageable getPageable(RolesPage rolesPage) {
		Sort sort = Sort.by(rolesPage.getSortDirection(), rolesPage.getSortBy());
		return PageRequest.of(rolesPage.getPageNumber(), rolesPage.getPageSize(), sort);
	}

	private Predicate getPredicate(RolesSearchCriteria rolesSearchCriteria, Root<UsersRole> usersRoleRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if (Objects.nonNull(rolesSearchCriteria.getSearchQuery())) {
			predicates.add(
					criteriaBuilder.like(usersRoleRoot.get("role"), "%" + rolesSearchCriteria.getSearchQuery() + "%"));

		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private void setOrder(RolesPage rolesPage, CriteriaQuery<UsersRole> criteriaQuery, Root<UsersRole> usersRoleRoot) {
		if (rolesPage.getSortDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(usersRoleRoot.get(rolesPage.getSortBy())));
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(usersRoleRoot.get(rolesPage.getSortBy())));

		}
	}

}
