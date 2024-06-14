package com.example.userservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * @title   : 사용자 조회 ( 조건 : 아이디 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    UserEntity findByUserId(String userId);

    /**
     * @title   : 사용자 조회 ( 조건 : 이메일 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    UserEntity findByEmail(String email);
}
