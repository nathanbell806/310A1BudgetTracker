package tests;

import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.ProfileRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ProfileTest {
    ProfileRepository profileRepository;

    CurrentProfile currentProfile;

    @Before
    public void setup() throws IOException {
        profileRepository = new ProfileRepository();
        currentProfile = CurrentProfile.getInstance();

    }

    /**
     * This test tries to create 2 profiles and can only be done if you set the json at the start. So uncomment when you want to do all tests and
     * change the json by how it says to in the comments it also makes sure createprofile is working
     */
//    @Test
    public void createProfile() throws IOException {
        //make sure that slot 2 username is empty
        boolean result1 = profileRepository.createProfile("user4",2);
        assertTrue(result1);
        //make sure slot 3 has a user called user5
        boolean result2 = profileRepository.createProfile("user5",  3);
        assertFalse(result2);
    }

    /**
     * This test ensures the singleton is working and select profile is working
     */
    @Test
    public void testSingletonAndSelect() {
        currentProfile.setCurrentProfile(profileRepository.selectProfile("user5"));
        currentProfile.setProfileSlot(3);


        int userSlot = currentProfile.getProfileSlot();
        assertEquals(3, userSlot);
    }
    /**
     * This test ensures saveprofile is working and get all profiles, and that all the fields inside of profile are indeed
     * being received and given properly
     */
    @Test
    public void testSaveAndGetALlProfiles() throws IOException {
        //add food as an expense to second slot
        Expense expense = new Expense("food", 2.0);

        currentProfile.getCurrentProfile().addExpense(expense);
        currentProfile.getCurrentProfile().setBudget(10);
        currentProfile.getCurrentProfile().setSavings(5);
        profileRepository.saveProfile(currentProfile.getCurrentProfile());
        assertEquals(expense.toString(),profileRepository.getAllProfiles().get(2).getExpenses().get(0).toString());

        assertEquals(10,profileRepository.getAllProfiles().get(2).getBudget());
        assertEquals(5,profileRepository.getAllProfiles().get(2).getSavings());
    }
}
