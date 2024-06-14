package com.example.userservice.controller;

import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestLogin;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UsersController {
    private final Environment env;
    private final Greeting greeting;

    private final UserService userService;

    private static final String SUCCESS = "요청이 정상적으로 처리되었습니다.";

    /**
     * @title   : 서버 접속 확인
     * @author  : 정승현
     * @since   : 2024/06/14
     * @return  : 환경변수 선언 메시지
     */
    @GetMapping("/welcome")
    public String welcome() {
        log.info("Greeting Message ==> {}", greeting.getMessage());
        return env.getProperty("greeting.message");
    }

    /**
     * @title   : 상태체크
     * @author  : 정승현
     * @since   : 2024/06/14
     * @return  : 상태 및 환경변수 메시지
     */
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + "\nPORT(local.server.port) = " + env.getProperty("local.server.port")
                + "\nPORT(server.port) = " + env.getProperty("server.port")
                + "\ntoken secret = " + env.getProperty("token.secret")
                + "\ntoken expiration time = " + env.getProperty("token.expiration_time"));
    }

    /**
     * @title   : 회원가입
     * @author  : 정승현
     * @since   : 2024/06/14
     * @param   : 사용자 정보
     * @return  : 사용자 정보
     */
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser userInfo) {
        ResponseUser responseUser = userService.createUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    /**
     * @title   : 사용자 목록 조회
     * @author  : 정승현
     * @since   : 2024/06/14
     * @return  : 사용자 목록
     */
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUserList() {
        List<ResponseUser> userList = userService.getUserByAll();
        return ResponseEntity.ok().body(userList);
    }

    /**
     * @title   : 사용자 조회 ( 조건 : 아이디 )
     * @author  : 정승현
     * @since   : 2024/06/14
     * @param   : 사용자 아이디
     * @return  : 사용자 정보
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        ResponseUser user = userService.getUserByUserId(userId);
        return ResponseEntity.ok().body(user);
    }

    /**
     * @title   : 로그인
     * @author  : 정승현
     * @since   : 2024/06/14
     * @param   : 사용자 아이디 및 패스워드
     * @return  : 성공 메시지
     */
    @PostMapping("/login")
    public ResponseEntity saveUser(@RequestBody RequestLogin userInfo) {
        return ResponseEntity.ok().body(SUCCESS);
    }
}
