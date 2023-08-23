package com.example.budgettracker.profiles;

import java.io.IOException;
import java.util.List;

public class ProfileFactory {
    private final ProfileRepository profileRepository;

    public ProfileFactory() throws IOException {
        this.profileRepository = new ProfileRepository();
    }

    public Profile selectProfile(String username) {
        return profileRepository.selectProfile(username);
    }


    public Boolean createProfile(String username,int profileSlot) throws IOException {
        return profileRepository.createProfile(username, profileSlot);
    }


    public List<Profile> getAllProfiles() throws IOException {
        return ProfileRepository.getAllProfiles();
    }



    public void saveProfile(Profile profile) throws IOException {
        profileRepository.saveProfile(profile);
    }





}
