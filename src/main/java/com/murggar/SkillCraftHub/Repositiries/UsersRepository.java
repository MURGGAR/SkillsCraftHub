package com.murggar.SkillCraftHub.Repositiries;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.murggar.SkillCraftHub.Entities.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByUsername(String username);

    // @Query("UPDATE UsersEntity SET role =?1 WHERE id =?2")
    // void updateUserRole(String role, Integer id);
}
