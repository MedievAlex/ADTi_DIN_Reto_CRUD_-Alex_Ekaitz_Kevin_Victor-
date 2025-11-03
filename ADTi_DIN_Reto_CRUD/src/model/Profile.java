package model;

/**
 *
 * @author 2dami
 */
public abstract class Profile {
    protected int p_id;
    protected String p_email;
    protected String p_username;
    protected String p_password;
    protected String p_name;
    protected String p_lastname;
    protected String p_telephone;
    
    public Profile() {
        this.p_id = 0;
        this.p_email = "";
        this.p_username = "";
        this.p_password = "";
        this.p_name = "";
        this.p_lastname = "";
        this.p_telephone = "000000000";
    }
    
    public Profile(int p_id, String p_email, String p_username, String p_password, String p_name, String p_lastname, String p_telephone) {
        this.p_id = p_id;
        this.p_email = p_email;
        this.p_username = p_username;
        this.p_password = p_password;
        this.p_name = p_name;
        this.p_lastname = p_lastname;
        this.p_telephone = p_telephone;
    }
    
    public Profile(String p_email, String p_username, String p_password, String p_name, String p_lastname, String p_telephone) {
        this.p_email = p_email;
        this.p_username = p_username;
        this.p_password = p_password;
        this.p_name = p_name;
        this.p_lastname = p_lastname;
        this.p_telephone = p_telephone;
    }
    
    public int getId() {
        return p_id;
    }
    
    public void setId(int p_id) {
        this.p_id = p_id;
    }

    public String getEmail() {
        return p_email;
    }

    public void setEmail(String p_email) {
        this.p_email = p_email;
    }

    public String getUsername() {
        return p_username;
    }

    public void setUsername(String p_username) {
        this.p_username = p_username;
    }

    public String getPassword() {
        return p_password;
    }

    public void setPassword(String p_password) {
        this.p_password = p_password;
    }

    public String getName() {
        return p_name;
    }

    public void setName(String p_name) {
        this.p_name = p_name;
    }

    public String getLastname() {
        return p_lastname;
    }

    public void setLastname(String p_lastname) {
        this.p_lastname = p_lastname;
    }

    public String getTelephone() {
        return p_telephone;
    }

    public void setTelephone(String p_telephone) {
        this.p_telephone = p_telephone;
    }

    @Override
    public String toString() {
        return "ID: " + p_id + ", Email: " + p_email + ", Username: " + p_username + ", Password: " + p_password + ", Name: " + p_name 
                + ", Last name: " + p_lastname + ", Telephone: " + p_telephone;
    }

    public abstract String show();
}