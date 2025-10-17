package model;

/**
 *
 * @author 2dami
 */
public class Admin extends Profile {
    private int a_current_account;

    public Admin(int p_id, String p_email, String p_username, String p_password, String p_name, String p_lastname, String p_telephone, int a_current_account) {
        super(p_id, p_email, p_username, p_password, p_name, p_lastname, p_telephone);
        this.a_current_account = a_current_account;
    }

    public Admin(String p_email, String p_username, String p_password, String p_name, String p_lastname, String p_telephone) {
        super(p_email, p_username, p_password, p_name, p_lastname, p_telephone);
    }

    public int getA_current_account() {
        return a_current_account;
    }

    public void setA_current_account(int a_current_account) {
        this.a_current_account = a_current_account;
    }

    @Override
    public String show() {
        return "Admin {" + super.toString() + ", Current account: " + a_current_account + '}';
    }
}
