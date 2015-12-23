package jp.dip.gpsoft.lanmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.dip.gpsoft.lanmap.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByName(String name);
}
