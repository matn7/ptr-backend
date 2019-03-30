package com.pandatronik.backend.persistence.repositories.user.account;

import com.pandatronik.backend.persistence.domain.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends CrudRepository<Plan, Integer> {

}