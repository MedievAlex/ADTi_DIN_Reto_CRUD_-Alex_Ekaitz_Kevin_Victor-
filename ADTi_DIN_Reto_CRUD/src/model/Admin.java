package model;

public class Admin extends Profile
{
    private static Admin instance;
    private String a_current_account;

    private Admin()
    {
        super();
        this.a_current_account = "";
    }

    public static Admin getInstance()
    {
        if (instance == null)
        {
            instance = new Admin();
        }
        
        return instance;
    }
    
    public static void clearInstance()
    {
        instance = null;
    }

    public String getCurrent_account()
    {
        return a_current_account;
    }

    public void setCurrent_account(String a_current_account)
    {
        this.a_current_account = a_current_account;
    }

    @Override
    public String show()
    {
        return "Admin {" + super.toString() + (a_current_account != null ? ", Current account: **** **** **** " + a_current_account.substring(12, 16) : null) + "}";
    }
}