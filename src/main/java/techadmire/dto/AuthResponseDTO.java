package techadmire.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String expiration;
    public AuthResponseDTO(String accessToken, Date expiration){
        this.accessToken = accessToken;
        this.expiration = expiration.toString();
    }
}
