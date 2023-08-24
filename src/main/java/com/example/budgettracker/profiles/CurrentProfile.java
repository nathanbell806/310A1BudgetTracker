package com.example.budgettracker.profiles;


/**
 * This class acts as a singleton so that the current user profile can be saved to the json file and when it is retrieved
 * you can often put it inside the singleton
 */
public class CurrentProfile {
    private static CurrentProfile instance = null;
    private Profile presentProfile;

    private int profileSlot;

    private CurrentProfile() {
    }



    public static CurrentProfile getInstance() {
        if (instance == null) {
            instance = new CurrentProfile();
        }
        return instance;
    }

    public Profile getCurrentProfile() {
        return presentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.presentProfile = currentProfile;
    }

    public int getProfileSlot(){
        return profileSlot;
    }
    public void setProfileSlot(int profileSlot){
        this.profileSlot = profileSlot;
    }

}
