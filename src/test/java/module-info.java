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

  opens tests to javafx.fxml,com.google.gson ;
  exports tests;
}