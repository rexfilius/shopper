package github.com.rexfilius.shopper.modules.auth.model;

import java.util.List;

public class UserInfoResponse {
    private Integer userId;
    private String username;
    private String jwtToken;
    private List<String> roles;

    public UserInfoResponse(String username, String jwtToken, List<String> roles) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    public UserInfoResponse(Integer userId, String username, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }

    public UserInfoResponse(Integer userId, String username, String jwtToken, List<String> roles) {
        this.userId = userId;
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "userId=" + userId +
                ", jwtToken='" + jwtToken + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}


