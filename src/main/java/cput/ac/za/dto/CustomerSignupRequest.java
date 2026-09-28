package cput.ac.za.dto;

// Plain request DTO for customer signup. Binding @RequestBody directly to
// the User JPA entity doesn't work here: User only exposes a Builder, no
// setters, so Jackson has no way to populate it from incoming JSON.
public class CustomerSignupRequest {
    private String name;
    private String email;
    private String password;
    private String phone;

    public CustomerSignupRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
