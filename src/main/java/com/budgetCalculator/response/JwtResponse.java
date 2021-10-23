package com.budgetCalculator.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String access_token;
    @Nullable
    private String refresh_token;
    private String username;
}
