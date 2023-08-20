module com.example.tests {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires com.google.gson;
  requires junit;
  requires com.example.budgettracker;
  requires org.testfx;
  requires org.testfx.junit;

  opens tests to javafx.fxml,com.google.gson ;
  exports tests;
  exports tests.controller;
  opens tests.controller to com.google.gson, javafx.fxml;
}