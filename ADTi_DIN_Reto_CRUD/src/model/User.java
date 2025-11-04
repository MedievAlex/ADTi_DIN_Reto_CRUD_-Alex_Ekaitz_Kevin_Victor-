package model;

public class User extends Profile
{

    private Gender u_gender;
    private String u_card;

    public User(int u_id, String p_email, String p_username, String p_password, String p_name, String p_lastname, String p_telephone, Gender u_gender, String u_card)
    {
        super(u_id, p_email, p_username, p_password, p_name, p_lastname, p_telephone);
        this.u_gender = u_gender;
        this.u_card = u_card;
    }

    public User(String p_email, String p_username, String p_password, String p_name, String p_lastname, String p_telephone, Gender u_gender, String u_card)
    {
        super(p_email, p_username, p_password, p_name, p_lastname, p_telephone);
        this.u_gender = u_gender;
        this.u_card = u_card;
    }

    public Gender getGender()
    {
        return u_gender;
    }

    public void setGender(Gender u_gender)
    {
        this.u_gender = u_gender;
    }

    public String getCard()
    {
        return u_card;
    }

    public void setCard(String u_card)
    {
        this.u_card = u_card;
    }

    @Override
    public String toString()
    {
        return super.getUsername();
    }

    @Override
    public String show()
    {
        return "User {" + super.toString() + ", Gender: " + u_gender + (u_card != null ? ", Card: **** **** **** " + u_card.substring(12, 16) : "") + "}";
    }
}
