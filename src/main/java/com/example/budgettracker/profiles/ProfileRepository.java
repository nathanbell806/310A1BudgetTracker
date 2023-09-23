package com.example.budgettracker.profiles;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProfileRepository {

    private final List<Profile> profiles;

    public ProfileRepository() throws IOException {
        this.profiles = getAllProfiles();
    }

    /**
     * This method gets all profiles from the json data file
     */
    public List<Profile> getAllProfiles() throws IOException {
        ArrayList<Profile> newProfiles = new ArrayList<>();

        Path profilePath = Path.of("src/main/java/data/player_data.json");
        String rawProfiles = Files.readString(profilePath);

        JsonStreamParser streamParser = new JsonStreamParser(rawProfiles);

        while (streamParser.hasNext()) {
            JsonElement page = streamParser.next();

            // JSON file is an array of elements so needs to convert into array.
            if (page.isJsonArray()) {
                JsonArray elements = (JsonArray) page;
                for (JsonElement element : elements) {
                    Profile profile = new Gson().fromJson(element, Profile.class);
                    newProfiles.add(profile);
                }
            }
        }
        return newProfiles;
    }

    /**
     * This method gets a profile from the json data file
     * 
     * @param username the username for the profile
     */
    public Profile selectProfile(String username) {
        for (Profile profile : profiles) {
            if (profile.getUsername().equals(username)) {
                return profile;
            }
        }
        throw new NoSuchElementException("Profile not found");
    }

    /**
     * This method creates a profile and adds it to json data file using gson
     * 
     * @param username    The username of the profile
     * @param profileSlot which slot the user should be created in
     */
    public boolean createProfile(String username, int profileSlot) {
        username = username.trim();
        int count = 1;
        for (Profile profile : profiles) {
            if (profile.getUsername().equals(username)) {
                return false;
            }

            if (profile.getUsername().equals("") && (count == profileSlot)) {
                profile.setUsername(username);
                List<Expense> emptyList = new ArrayList<>();
                profile.setExpenses(emptyList);
                profile.setBudget(0);
                profile.setSavings(0);
                saveProfile(profile);
                return true;
            }
            count = count + 1;
        }
        throw new IndexOutOfBoundsException("All 3 user slots are taken");
    }

    /**
     * This method saves an already created profile to json data file
     * 
     * @param saveProfile The profile to be saved
     */
    public void saveProfile(Profile saveProfile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder json = new StringBuilder("[\n");

        try (FileWriter fileWriter = new FileWriter("src/main/java/data/player_data.json")) {
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getUsername().equals(saveProfile.getUsername())) {
                    profiles.get(i).getExpenses().clear();
                    profiles.get(i).getExpenses().addAll(saveProfile.getExpenses());
                    profiles.get(i).setBudget(saveProfile.getBudget());
                    profiles.get(i).setSavings(saveProfile.getSavings());
                    json.append(gson.toJson(profiles.get(i), Profile.class));
                } else {
                    json.append(gson.toJson(profiles.get(i), Profile.class));
                }
                if (i != profiles.size() - 1) {
                    json.append(",\n");
                }
            }
            json.append("\n]");

            fileWriter.write(String.valueOf(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
