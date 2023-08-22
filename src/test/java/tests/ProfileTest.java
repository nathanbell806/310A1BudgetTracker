package tests;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.ProfileFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
public class ProfileTest {
    ProfileFactory profileFactory;

    CurrentProfile currentProfile;

    @Before
    public void setup() throws IOException {
        profileFactory = new ProfileFactory();
        currentProfile = CurrentProfile.getInstance();
    }

    @Test
    public void createProfile() throws IOException {
        //make sure that slot 2 username is empty
        boolean result1 = profileFactory.createProfile("user4","black",2);
        assertTrue(result1);
        //make sure slot 3 has a user called user5
        boolean result2 = profileFactory.createProfile("user5", "black", 3);
        assertFalse(result2);
    }

    @Test
    public void testSingletonAndSelect() {
        currentProfile.setCurrentProfile(profileFactory.selectProfile("user5"));
        currentProfile.setProfileSlot(3);


        int userSlot = currentProfile.getProfileSlot();
        assertEquals(3, userSlot);


    }

    @Test
    public void testSaveAndGetALlProfiles() throws IOException {
        //set the third slot color to black
        currentProfile.getCurrentProfile().setColor("orange");
        profileFactory.saveProfile(currentProfile.getCurrentProfile());
        assertEquals("orange",profileFactory.getAllProfiles().get(2).getColor());


    }
}
