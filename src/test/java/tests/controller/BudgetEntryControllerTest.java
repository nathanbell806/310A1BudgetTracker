package tests.controller;
import com.example.budgettracker.StartApplication;
import com.example.budgettracker.controller.BudgetEntryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.Before;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * We had to comment out these tests as sonarcloud didn't seem to be able to finish its testing with javafx test types running
 */
public class BudgetEntryControllerTest extends ApplicationTest {
    private BudgetEntryController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("/com/example/budgettracker/budget-entry.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root, 0, 0));
        stage.show();
    }

    @Before
    public void setUp() throws Exception {
        // Initialize controller and UI components
        interact(() -> {
            controller.initialize();
        });
    }

    /**
     * This method tests that the expense navigation button is enabled when there are valid fields inputted.
     */
//    @Test
    public void testExpenseButtonStateForValidFields() {
        interact(() -> {
            controller.onSaving(new ActionEvent());
        });
        clickOn("#savingEntry").write("100");
        clickOn("#savingIncomeEntry").write("100");
        Button expenseButton = lookup("#expenseButton").query();
        Assertions.assertThat(lookup("#expenseButton").queryButton()).isEnabled();
    }

    /**
     * This method tests that the expense navigation button is disabled when there are invalid fields inputted.
     */
//    @Test
    public void testExpenseButtonStateForInvalidFields() {
        interact(() -> {
            controller.onSaving(new ActionEvent());
        });
        clickOn("#savingEntry").write("word");
        clickOn("#savingIncomeEntry").write("100");
        Button expenseButton = lookup("#expenseButton").query();
        Assertions.assertThat(lookup("#expenseButton").queryButton()).isDisabled();
    }
}

