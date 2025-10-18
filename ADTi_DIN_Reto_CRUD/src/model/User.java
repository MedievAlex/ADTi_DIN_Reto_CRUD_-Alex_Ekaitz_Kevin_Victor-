package model;

/**
 *
 * @author 2dami
 */
public class User extends Profile {
    private Gender u_gender;
    private String u_card;

    public User(int u_id, String p_email, String p_username, String p_password, String p_name, String p_lastname, int p_telephone, Gender u_gender, String u_card) {
        super(u_id, p_email, p_username, p_password, p_name, p_lastname, p_telephone);
        this.u_gender = u_gender;
        this.u_card = u_card;
    }
    
    public User(String p_email, String p_username, String p_password, String p_name, String p_lastname, int p_telephone, Gender u_gender, String u_card) {
        super(p_email, p_username, p_password, p_name, p_lastname, p_telephone);
        this.u_gender = u_gender;
        this.u_card = u_card;
    }

    public Gender getU_gender() {
        return u_gender;
    }

    public void setU_gender(Gender u_gender) {
        this.u_gender = u_gender;
    }

    public String getU_card() {
        return u_card;
    }

    public void setU_card(String u_card) {
        this.u_card = u_card;
    }

    @Override
    public String show() {
        return "User {" + super.toString() + ", Gender: " + u_gender + ", Card: **** **** **** " + u_card.substring(12, 16) + '}';
    }
}