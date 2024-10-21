package com.payment.usermanagement.services.auth;

import com.payment.usermanagement.dtos.user.AuthRecordRequest;
import com.payment.usermanagement.dtos.user.UserRecordDto;
import com.payment.usermanagement.enums.Role;
import com.payment.usermanagement.models.User;
import com.payment.usermanagement.models.response.Response;
import com.payment.usermanagement.models.response.factories.ResponseFactory;
import com.payment.usermanagement.repositories.UserRepository;
import com.payment.usermanagement.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public Response register(UserRecordDto dto, HttpServletRequest request) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setRole(Role.USER);
        userService.create(user);
        var token = jwtService.generateToken(user);

        return responseFactory.createCreatedResponse(request.getRequestURI(), token);
    }

    public Response authenticate(AuthRecordRequest request, HttpServletRequest httpServletRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.pwd())
        );
        var user = userRepository.findByUsername(request.username())
                .orElseThrow();

        var token = jwtService.generateToken(user);
        return responseFactory.createSuccessResponse(httpServletRequest.getRequestURI(), token);
    }

}
