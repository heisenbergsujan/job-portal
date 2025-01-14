package com.sujan.jobportal.auth;

import com.sujan.jobportal.dto.EmployerDto;
import com.sujan.jobportal.dto.JobSeekerDto;
import com.sujan.jobportal.dto.UserDto;
import com.sujan.jobportal.enums.Role;
import com.sujan.jobportal.model.Employer;
import com.sujan.jobportal.model.JobSeeker;
import com.sujan.jobportal.model.User;
import com.sujan.jobportal.reponse.AuthResponse;
import com.sujan.jobportal.repository.EmployerRepository;
import com.sujan.jobportal.repository.JobSeekerRepository;
import com.sujan.jobportal.repository.UserRepository;
import com.sujan.jobportal.request.AuthRequest;
import com.sujan.jobportal.request.EmployerRegisterRequest;
import com.sujan.jobportal.request.JobSeekerRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ModelMapper mapper;


    public AuthResponse authenticate(AuthRequest request) {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         request.getEmail(),
                         request.getPassword()
                 )
         );
         User user = userRepository
                 .findByEmail(request.getEmail())
                 .orElseThrow(()->new UsernameNotFoundException("User not found!"));

        String token = jwtService.generateToken(user,String.valueOf(user.getRole()));
        UserDto userDto= convertToDto(user);
        return AuthResponse
                .builder()
                .token(token)
                .userDto(userDto)
                .build();
    }

    private UserDto convertToDto(User user) {
       if( user.getRole() == Role.EMPLOYER) {
          Employer employer= employerRepository.findById(user.getId()).orElseThrow();
          return convertToEmployerDto(employer);
       }else{
            JobSeeker jobSeeker= jobSeekerRepository
                    .findById(user.getId()).orElseThrow();
            return convertToJobSeekerDto(jobSeeker);
       }
    }

    private EmployerDto convertToEmployerDto(Employer employer){
        return this.mapper.map(employer,EmployerDto.class);
    }
    private JobSeekerDto convertToJobSeekerDto(JobSeeker jobSeeker){
        return this.mapper.map(jobSeeker,JobSeekerDto.class);
    }

    public AuthResponse registerJobSeeker(
            JobSeekerRegisterRequest request
    ) {
        JobSeeker user=JobSeeker
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNo(request.getPhoneNo())
                .role(Role.JOB_SEEKER)
                .build();
        userRepository.save(user);
        String jwtToken=jwtService.generateToken(user,String.valueOf(user.getRole()));
        UserDto jobSeekerDto= convertToJobSeekerDto(user);
        return AuthResponse
                .builder()
                .userDto(jobSeekerDto)
                .token(jwtToken)
                .build();
    }

    public AuthResponse registerEmployer(EmployerRegisterRequest request) {
        Employer user=Employer
                .builder()
                .companyName(request.getCompanyName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNo(request.getPhoneNo())
                .role(Role.EMPLOYER)
                .build();
        userRepository.save(user);
        String jwtToken=jwtService.generateToken(user,String.valueOf(user.getRole()));
        UserDto userDto=convertToEmployerDto(user);
        return AuthResponse
                    .builder()
                    .token(jwtToken)
                    .userDto(userDto)
                    .build();
    }
}
