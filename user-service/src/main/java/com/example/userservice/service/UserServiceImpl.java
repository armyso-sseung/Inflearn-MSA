package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    /**
     * @title       : 회원가입
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 요청으로 들어온 사용자 정보를 이용해 회원가입을 한다.
     */
    @Override
    public ResponseUser createUser(RequestUser userInfo) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userInfo, UserEntity.class);
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPwd(passwordEncoder.encode(userInfo.getPwd()));

        userRepository.save(userEntity);
        return mapper.map(userEntity, ResponseUser.class);
    }

    /**
     * @title       : 사용자 조회 ( 조건 : 아이디 )
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 사용자 아이디를 통해서 사용자 정보를 조회 한다.
     */
    @Override
    public ResponseUser getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE);

        ModelMapper mapper = new ModelMapper();
        ResponseUser responseUser = mapper.map(userEntity, ResponseUser.class);

        List<ResponseOrder> orders = new ArrayList<>();
        responseUser.setOrders(orders);

        return responseUser;
    }

    /**
     * @title       : 사용자 조회 ( 조건 : 이메일 )
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 사용자 이메일을 통해서 사용자 정보를 조회 한다.
     */
    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);
        return userDto;
    }

    /**
     * @title       : 사용자 목록 조회
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 사용자 목록을 조회 한다.
     */
    @Override
    public List<ResponseUser> getUserByAll() {
        Iterable<UserEntity> dataList = userRepository.findAll();

        ModelMapper mapper = new ModelMapper();
        List<ResponseUser> userList = StreamSupport.stream(dataList.spliterator(), false)
                .map(user -> mapper.map(user, ResponseUser.class))
                .collect(Collectors.toList());

        return userList;
    }

    /**
     * @title       : Java Security Login Service
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 로그인을 통해 들어온 정보로 사용자 정보를 조회하고 Security User 로 전달한다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);
        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
