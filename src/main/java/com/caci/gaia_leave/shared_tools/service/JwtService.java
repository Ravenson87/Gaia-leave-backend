package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.administration.model.response.UserResponse;
import org.apache.commons.lang3.time.DateUtils;


import java.util.Date;

import static com.caci.gaia_leave.shared_tools.configuration.AppProperties.EXPIRATION_TIME;

public class JwtService {

    public String generateToken(UserResponse model) {
    Date now = new Date();
    Date expiryDate = DateUtils.addHours(now, EXPIRATION_TIME);

        return null;
    }
}
