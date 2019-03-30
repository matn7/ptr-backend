package com.pandatronik.backend.persistence.repositories.user.account;

import com.pandatronik.backend.persistence.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
