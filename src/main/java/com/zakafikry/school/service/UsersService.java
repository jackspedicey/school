package com.zakafikry.school.service;

import com.zakafikry.school.dto.UserRegistrationDto;
import com.zakafikry.school.entity.Teachers;
import com.zakafikry.school.entity.Students;
import com.zakafikry.school.entity.Users;
import com.zakafikry.school.repository.StudentsRepository;
import com.zakafikry.school.repository.TeachersRepository;
import com.zakafikry.school.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private TeachersRepository teacherRepository;

    @Autowired
    private StudentsRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users saveUser(Users user) {
        String username1 = user.getUsername();
        Optional<Users> userDb = userRepository.findByUsername(username1);
        if (userDb.isPresent()) {
            String username2 = userDb.get().getUsername();

            if (username1.equals(username2)) {
                return userDb.get();
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void registerUser(UserRegistrationDto userRegistrationDto) {
        Users user = new Users();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRole("ROLE_" + userRegistrationDto.getRole());
        userRepository.save(user);

        if ("ROLE_TEACHER".equalsIgnoreCase(userRegistrationDto.getRole())) {
            Teachers teacher = new Teachers();
            teacher.setFirstName(userRegistrationDto.getFirstName());
            teacher.setLastName(userRegistrationDto.getLastName());
            teacher.setUsername(userRegistrationDto.getUsername());
            teacher.setEmail(userRegistrationDto.getEmail());
            teacher.setAddress(userRegistrationDto.getAddress());
            teacher.setBirthDate(userRegistrationDto.getBirthDate());
            teacher.setHiredDate(String.valueOf(new Date()));
            teacherRepository.save(teacher);
        } else if ("ROLE_STUDENT".equalsIgnoreCase(userRegistrationDto.getRole())) {
            Students student = new Students();
            student.setFirstName(userRegistrationDto.getFirstName());
            student.setLastName(userRegistrationDto.getLastName());
            student.setUsername(userRegistrationDto.getUsername());
            student.setEmail(userRegistrationDto.getEmail());
            student.setAddress(userRegistrationDto.getAddress());
            student.setBirthDate(userRegistrationDto.getBirthDate());
            studentRepository.save(student);
        }
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
