package br.com.erudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.erudio.data.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUserName (String userName);

	/*
	 * @Query("Select u from User u WHERE u.userName = :userName") public User
	 * findByUserName(@Param("userName") String userName);
	 */
}
