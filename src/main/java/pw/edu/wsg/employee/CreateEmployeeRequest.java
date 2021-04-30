package pw.edu.wsg.employee;

import com.sun.istack.NotNull;
//import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class CreateEmployeeRequest {

//    @ApiModelProperty(example = "Lisek Ogrodniczek", required = true)
    @NotNull
    private String name;

//    @ApiModelProperty(required = true)
    private List<Integer> availability = new ArrayList<>();

//    @ApiModelProperty(required = true)
    private Long app_user_id;

    public CreateEmployeeRequest() {
    }

    public CreateEmployeeRequest(String name, List<Integer> availability) {
        this.name = name;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Integer> availability) {
        this.availability = availability;
    }

    public Long getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(Long app_user_id) {
        this.app_user_id = app_user_id;
    }

    @Override
    public String toString() {
        return "CreateEmployeeRequest{" +
                "name='" + name + '\'' +
                ", availability=" + availability +
                '}';
    }
}
