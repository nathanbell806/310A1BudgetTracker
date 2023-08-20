package com.example.budgettracker.profiles;

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
