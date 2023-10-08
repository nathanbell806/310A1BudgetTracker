module com.example.budgettracker {
  exports com.example.budgettracker.profiles;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.swing;
  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires com.google.gson;
  requires junit;
    requires java.desktop;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  opens com.example.budgettracker to javafx.fxml, com.google.gson;
  exports com.example.budgettracker;
  opens com.example.budgettracker.profiles to javafx.fxml,com.google.gson;
    exports com.example.budgettracker.controller;
    opens com.example.budgettracker.controller to com.google.gson, javafx.fxml;

}