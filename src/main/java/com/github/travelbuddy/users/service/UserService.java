package com.github.travelbuddy.users.service;

import com.github.travelbuddy.common.service.S3Service;
import com.github.travelbuddy.users.dto.SignupDto;
import com.github.travelbuddy.users.dto.UpdatePasswordRequest;
import com.github.travelbuddy.users.dto.UserResponse;
import com.github.travelbuddy.users.dto.UserInfoResponse;
import com.github.travelbuddy.users.entity.UserEntity;
import com.github.travelbuddy.users.enums.Gender;
import com.github.travelbuddy.users.enums.Role;
import com.github.travelbuddy.users.enums.Status;
import com.github.travelbuddy.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageService messageService;
    private final S3Service s3Service;

    @Value("${profile.url}")
    private String defaultProfileUrl;

    public ResponseEntity<UserResponse> signup(SignupDto signupDto) throws IOException {

        String email = signupDto.getEmail();
        String password = signupDto.getPassword();
        Boolean isExist = userRepository.existsByEmail(email);
        if(isExist){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UserResponse("이미 존재하는 이메일입니다."));
        }

        String residentNum = signupDto.getResidentNum();
        Gender gender;
        char genderChar = residentNum.charAt(6);

        if(genderChar == '1'|| genderChar == '3'){
            gender=Gender.MALE;
        }
        else if(genderChar == '2'|| genderChar == '4'){
            gender=Gender.FEMALE;
        }
        else{
            throw new IllegalArgumentException("유효하지 않은 주민등록번호입니다.");
        }
        log.info("gender={}",gender);

        String pictureUrl = defaultProfileUrl;

        if(signupDto.getProfilePicture() != null){
            pictureUrl = s3Service.uploadFile(signupDto.getProfilePicture());
        }

        UserEntity userEntity = UserEntity.builder()
                .name(signupDto.getName())
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .residentNum(signupDto.getResidentNum())
                .phoneNum(signupDto.getPhoneNum())
                .gender(gender)
                .status(Status.ACTIVE)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .profilePictureUrl(pictureUrl)
                .build();

        userRepository.save(userEntity);
        return ResponseEntity.ok(new UserResponse("회원가입 완료되었습니다."));
    }

    public UserInfoResponse getUserInfo(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("정보조회할 해당 ID: " + userId + "를 찾을 수 없습니다."));

        return UserInfoResponse.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .residentNum(userEntity.getResidentNum())
                .gender(userEntity.getGender())
                .profilePictureUrl(userEntity.getProfilePictureUrl())
                .build();
    }

    @Transactional
    public ResponseEntity<?> updateUserInfo(Integer userId, MultipartFile profilePicture )throws IOException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("정보수정할 해당 ID: "+ userId +"를 찾을 수 없습니다."));

        if(!userEntity.getProfilePictureUrl().equals(defaultProfileUrl)){
            s3Service.deleteFile(userEntity.getProfilePictureUrl());
        }
        String pictureUrl = s3Service.uploadFile(profilePicture);

        UserEntity updateUser = userEntity.toBuilder()
                .profilePictureUrl(pictureUrl)
                .build();
        userRepository.save(updateUser);

        return ResponseEntity.ok(pictureUrl);
    }

    public ResponseEntity<UserResponse> checkUserExist(String phoneNum) {
        Boolean isExist = userRepository.existsByPhoneNum(phoneNum);
        if(isExist){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UserResponse("이미 가입된 번호입니다."));
        }else {
            return null;
        }
    }

    public ResponseEntity<UserResponse> findPassword(String email) {
        System.out.println(email);
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity != null){
            String phoneNum = userEntity.getPhoneNum();
            return messageService.sendSms(phoneNum);
        }else {
            return null;
        }
    }

    public ResponseEntity<UserResponse> updatePassword(UpdatePasswordRequest request) {
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();
        if(!newPassword.equals(confirmPassword)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse("비밀번호가 일치하지 않습니다."));
        }
        String email = request.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email);
        System.out.println(userEntity.getPassword());
        UserEntity updatedUserEntity = userEntity.toBuilder()
                .password(bCryptPasswordEncoder.encode(newPassword)).build();
        System.out.println(updatedUserEntity.getPassword());
        userRepository.save(updatedUserEntity);
        return ResponseEntity.ok(new UserResponse("비밀번호 변경이 완료되었습니다."));
    }
}
