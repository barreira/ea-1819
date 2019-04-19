package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
