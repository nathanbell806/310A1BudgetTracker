module com.example.budgettracker {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires com.google.gson;

  opens com.example.budgettracker to javafx.fxml;
  exports com.example.budgettracker;
}