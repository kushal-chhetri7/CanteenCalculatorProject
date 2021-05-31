package gcit.edu.personalcanteen;

public class UserHelperClass {

    String username,email,password,conpassword,phoneNo;

    public UserHelperClass() {
    }

    public UserHelperClass(String username, String email, String password, String conpassword, String phoneNo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.conpassword = conpassword;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getConpassword() {
        return conpassword;
    }

    public void setConpassword(String conpassword) {
        this.conpassword = conpassword;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
