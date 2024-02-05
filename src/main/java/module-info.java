module edu.apsu.mdeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires org.apache.commons.text;


    opens edu.apsu.mdeditor to javafx.fxml;
    exports edu.apsu.mdeditor;
    exports edu.apsu.mdeditor.FileBuilder;
    opens edu.apsu.mdeditor.FileBuilder to javafx.fxml;
}