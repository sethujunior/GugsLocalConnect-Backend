package cput.ac.za.dto;

public class BusinessSignupRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String businessName;
    private String category;
    private String location;
    private String description;

    public BusinessSignupRequest() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
