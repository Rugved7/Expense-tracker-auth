package org.rugved.controller;

import lombok.AllArgsConstructor;
import org.rugved.entities.RefreshToken;
import org.rugved.model.UserInfoDTO;
import org.rugved.response.JwtResponseDTO;
import org.rugved.service.JwtService;
import org.rugved.service.RefreshTokenService;
import org.rugved.service.UserDetailsServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceIMPL userDetailsServiceIMPL;

    @PostMapping("auth/v1/signup")
    public ResponseEntity Signup(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Boolean isSignedUp = userDetailsServiceIMPL.signUpUser(userInfoDTO);
            if (Boolean.FALSE.equals(isSignedUp)) {
                return new ResponseEntity<>("User Already Exists", HttpStatus.BAD_GATEWAY);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDTO.getUserName());
            String jwtToken = jwtService.GenerateToken(userInfoDTO.getUserName());
            return new ResponseEntity<>(JwtResponseDTO.builder().accesstoken(jwtToken)
                    .token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception E) {
            return new ResponseEntity<>("Something went wrong in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
