package model;

/**
 *
 * @author 2dami
 */
public class User extends Profile
{
    private static User instance;
    private Gender u_gender;
    private String u_card;

    private User()
    {
        super();
        this.u_gender = Gender.OTHER;
        this.u_card = "";
    }
    
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

    public static User getInstance()
    {
        if (instance == null)
        {
            instance = new User();
        }
        
        return instance;
    }
    
    public static void clearInstance()
    {
        instance = null;
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
    public String show()
    {
        return "User {" + super.toString() + ", Gender: " + u_gender + (u_card != null ? ", Card: **** **** **** " + u_card.substring(12, 16) : null) + "}";
    }
}