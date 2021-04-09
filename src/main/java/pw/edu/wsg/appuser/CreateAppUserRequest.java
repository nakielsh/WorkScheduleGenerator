package pw.edu.wsg.appuser;


import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;


public class CreateAppUserRequest {

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

    public CreateAppUserRequest() {
    }

    public CreateAppUserRequest(String firstName, String lastName, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
