package com.pethotel.Controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;

import com.pethotel.Models.HotelStaff;
import com.pethotel.Models.HotelStaffLoginCredentials;
import com.pethotel.Repositories.HotelStaffRepository;
import com.pethotel.Security.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private HotelStaffRepository hotelStaffRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register hotel staff
    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody HotelStaff staff) {
        String encodedPass = passwordEncoder.encode(staff.getPassword());
        staff.setPassword(encodedPass);
        staff = hotelStaffRepository.save(staff);
        String token = jwtUtil.generateToken(staff.getUsername());
        return Collections.singletonMap("jwt-token", token);
    }

    // Login hotel staff
    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody HotelStaffLoginCredentials body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(
                            body.getUsername(),
                            body.getPassword()
                    );
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getUsername());
            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }
}
