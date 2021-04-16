package pw.edu.wsg.registration;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import pw.edu.wsg.appuser.AppUserRole;

public class RegistrationRequest {

    @ApiModelProperty(example = "Jan", required = true)
    @NotNull
    private String firstName;

    @ApiModelProperty(example = "Kowalski", required = true)
    @NotNull
    private String lastName;

    @ApiModelProperty(example = "********", required = true)
    @NotNull
    private String password;

    @ApiModelProperty(example = "JanKowal", required = true)
    @NotNull
    private String username;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String firstName, String lastName, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "CreateAppUserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
