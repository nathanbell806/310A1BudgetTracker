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

    private List<Profile> profiles;
    public ProfileRepository() throws IOException {
        this.profiles = getAllProfiles();
    }

    /**
     * This method gets all profiles from the json data file
     */
    protected static List<Profile> getAllProfiles() throws IOException {
        ArrayList<Profile> profiles = new ArrayList<>();

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
                        profiles.add(profile);
                }
            }
        }
        return profiles;
    }

    /**
     * This method gets a profile from the json data file
     * @param username the username for the profile
     */
    protected Profile selectProfile(String username) {
        for (Profile profile : profiles) {
            if (profile.getUsername().equals(username)) {
                return profile;
            }
        }
        throw new NoSuchElementException("Profile not found");
    }

    /**
     * This method creates a profile and adds it to json data file
     * @param username The username of the profile
     * @param profileSlot which slot the user should be created in
     */
    protected boolean createProfile(String username, int profileSlot) throws IOException {
        username = username.trim();
        int count = 1;
        for (Profile profile : profiles) {
            if (profile.getUsername().equals(username)) {
                return false;
            }

            if (profile.getUsername().equals("")&&(count==profileSlot)) {
                profile.setUsername(username);
                List<Expense> emptyList = new ArrayList<>();
                profile.setExpenses(emptyList);
                saveProfile(profile);
                return true;
            }
            count = count+1;
        }
        throw new IndexOutOfBoundsException("All 3 user slots are taken");
    }


    /**
     * This method saves an aldready created profile to json data file
     * @param saveProfile The profile to be saved
     */
    protected void saveProfile(Profile saveProfile) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder json = new StringBuilder("[\n");
        try (FileWriter fileWriter = new FileWriter("src/main/java/data/player_data.json")){
            // Formats the JSON into the proper format.
            for (int i = 0; i < profiles.size(); i++) {

                // Check if the current profile is the same, if not, write to JSON.
                if (profiles.get(i).getUsername().equals(saveProfile.getUsername())) {
                    json.append(gson.toJson(saveProfile, Profile.class));
                } else {
                    json.append(gson.toJson(profiles.get(i), Profile.class));
                }
                if (i != profiles.size() - 1) {
                    json.append(",\n");
                }
            }
            json.append("\n]");

            // Save to the JSON file
            fileWriter.write(String.valueOf(json));
        }catch (IOException e) {
            e.printStackTrace();
        }


    }






}

