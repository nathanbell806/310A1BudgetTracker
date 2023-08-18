module com.example.budgettracker {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires com.google.gson;
  requires junit;

  opens com.example.budgettracker to javafx.fxml, com.google.gson;
  exports com.example.budgettracker;
  exports com.example.budgettracker.profiles;
  opens com.example.budgettracker.profiles to javafx.fxml,com.google.gson;

  opens tests to javafx.fxml,com.google.gson ;
  exports tests;
}