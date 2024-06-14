package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    /**
     * @title   : 회원가입
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    ResponseUser createUser(RequestUser userInfo);

    /**
     * @title   : 사용자 목록 조회
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    List<ResponseUser> getUserByAll();

    /**
     * @title   : 사용자 조회 ( 조건 : 아이디 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    ResponseUser getUserByUserId(String userId);

    /**
     * @title   : 사용자 조회 ( 조건 : 이메일 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    UserDto getUserByEmail(String email);
}
