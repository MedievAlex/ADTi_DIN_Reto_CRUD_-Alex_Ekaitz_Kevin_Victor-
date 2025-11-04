package model;

public class LoggedProfile {
    private static LoggedProfile instance;
    private Profile currentProfile;
    
    private LoggedProfile() {}
    
    public static LoggedProfile getInstance() {
        if (instance == null) {
            instance = new LoggedProfile();
        }
        return instance;
    }
    
    public void setProfile(Profile profile) {
        this.currentProfile = profile;
    }
    
    public Profile getProfile() {
        return currentProfile;
    }
    
    public void clear() {
        this.currentProfile = null;
    }
}