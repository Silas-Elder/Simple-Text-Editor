package edu.apsu.mdeditor;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import edu.apsu.mdeditor.FileBuilder.HtmlFileBuilder;
import edu.apsu.mdeditor.FileBuilder.PdfFileBuilder;
import edu.apsu.mdeditor.FileBuilder.TextFileBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collections;
import java.util.Scanner;

public class MdEditor extends Application {
    Stage stage;
    // children of nodes as class variables for easy access
    MenuItem newItem;
    MenuItem openItem;
    MenuItem saveItem;
    MenuItem exitItem;
    MenuItem textItem;
    MenuItem pdfItem;
    MenuItem htmlItem;
    TextArea centerContent;
    ChoiceBox widthChoice;
    ChoiceBox pdfChoice;
    ChoiceBox pdfMarginChoice;
    ChoiceBox htmlColorChoice;
    ChoiceBox htmlPaddChoice;

    //properties
    private ObjectProperty<File> currentFileProperty;
    File currentFile;
    private SimpleStringProperty fileName;
    public IntegerProperty textLineWidthProperty;
    private StringProperty pdfFontProperty;
    private FloatProperty pdfMarginProperty;
    private StringProperty htmlColorProperty;
    private StringProperty htmlPaddingProperty;


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        currentFile = new File("untitled.md");

        MenuBar topBar = createMenu();
        root.setTop(topBar);

        centerContent = new TextArea();
        //centerContent.setEditable(false);
        root.setCenter(centerContent);

        VBox rightContent = createVBox();
        root.setRight(rightContent);

        //file menu functionality
        newItem.setOnAction(ActionEvent -> {
            centerContent.clear();
            currentFileProperty.setValue(new File("untitled.md"));
        });
        openItem.setOnAction(new openEventHandler());
        saveItem.setOnAction(new saveEventHandler());
        exitItem.setOnAction(ActionEvent -> {
            Platform.exit();
        });

        //export menu functionality
        textItem.setOnAction(new exportEventHandler());
        pdfItem.setOnAction(new exportEventHandler());
        htmlItem.setOnAction(new exportEventHandler());

        //stage bindings
        currentFileProperty = new SimpleObjectProperty<>(currentFile);
        fileName = new SimpleStringProperty();
        fileName.setValue(currentFileProperty.get().getName());
        stage.titleProperty().bind(fileName);

        //init properties
        textLineWidthProperty = new SimpleIntegerProperty(40);
        pdfFontProperty = new SimpleStringProperty();
        pdfMarginProperty = new SimpleFloatProperty();
        htmlColorProperty = new SimpleStringProperty();
        htmlPaddingProperty = new SimpleStringProperty();

        //node bindings
        textLineWidthProperty.bind(widthChoice.valueProperty());
        pdfFontProperty.bind(pdfChoice.valueProperty());
        pdfMarginProperty.bind(pdfMarginChoice.valueProperty());
        htmlColorProperty.bind(htmlColorChoice.valueProperty());
        htmlPaddingProperty.bind(htmlPaddChoice.valueProperty());



        //notes:
        //each editor has its own class (html, text, pdf)
        //scan through a file until you reach a new line/blank, then append the string with brackets
        //Could make an abstract parent class that defines each method such as: writeheader, writeblockquote, writeparagraph, writeline as well as the file that it is taking in
        //Each type of editor (text, html, pdf) extend this class and define each method depending on its need
        //for all the save functions, bind the file names!!!


        Scene scene = new Scene(root);
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.show();
    }

    private VBox createVBox() {
        VBox rightMain = new VBox();
        rightMain.setMaxWidth(300);

        //Text Config
        Label configLabel = new Label("Text Configuration");
        configLabel.setFont(Font.font("Arial", 24));

        HBox configBox = new HBox();
        Label widthLabel = new Label("Line Width");
        widthLabel.setPadding(new Insets(0, rightMain.getMaxWidth() / 3, 0, 0));
        widthChoice = new ChoiceBox<>();
        widthChoice.getItems().addAll(40, 50, 60, 70, 80);
        widthChoice.setValue(40);
        configBox.setPadding(new Insets(5));
        configBox.getChildren().addAll(widthLabel, widthChoice);

        //PDF Config
        Label pdfConfigLabel = new Label("PDF Configuration");
        pdfConfigLabel.setFont(Font.font("Arial", 24));

        HBox pdfConfigBox = new HBox();
        Label pdfFontLabel = new Label("Font");
        pdfFontLabel.setPadding(new Insets(0, rightMain.getMaxWidth() / 2.25, 0, 0));
        pdfChoice = new ChoiceBox<>();
        pdfChoice.getItems().addAll("Helvetica", "Courier", "Times");
        pdfChoice.setValue("Helvetica");
        pdfConfigBox.setPadding(new Insets(5));
        pdfConfigBox.getChildren().addAll(pdfFontLabel, pdfChoice);

        HBox pdfMarginBox = new HBox();
        Label pdfMarginLabel = new Label("Margin (inch)");
        pdfMarginLabel.setPadding(new Insets(0, rightMain.getMaxWidth() / 3.5, 0, 0));
        pdfMarginChoice = new ChoiceBox<>();
        pdfMarginChoice.getItems().addAll(.25, .5, .75, 1.0, 1.25, 1.5, 1.75, 2.0);
        pdfMarginChoice.setValue(1);
        pdfMarginBox.setPadding(new Insets(5));
        pdfMarginBox.getChildren().addAll(pdfMarginLabel, pdfMarginChoice);

        //HTML Config
        Label htmlConfigLabel = new Label("HTML Configuration");
        htmlConfigLabel.setFont(Font.font("Arial", 24));

        HBox htmlConfigBox = new HBox();
        Label htmlColorLabel = new Label("Font Color");
        htmlColorLabel.setPadding(new Insets(0, rightMain.getMaxWidth() / 3, 0, 0));
        htmlColorChoice = new ChoiceBox<>();
        htmlColorChoice.getItems().addAll("Black", "Blue", "Red");
        htmlColorChoice.setValue("Black");
        htmlConfigBox.setPadding(new Insets(5));
        htmlConfigBox.getChildren().addAll(htmlColorLabel, htmlColorChoice);

        HBox htmlPaddBox = new HBox();
        Label htmlPaddLabel = new Label("Padding");
        htmlPaddLabel.setPadding(new Insets(0, rightMain.getMaxWidth() / 2.65, 0, 0));
        htmlPaddChoice = new ChoiceBox<>();
        htmlPaddChoice.getItems().addAll("1em", "2em", "3em", "4em", "5em");
        htmlPaddChoice.setValue("2em");
        htmlPaddBox.setPadding(new Insets(5));
        htmlPaddBox.getChildren().addAll(htmlPaddLabel, htmlPaddChoice);


        rightMain.getChildren().addAll(configLabel, configBox, pdfConfigLabel, pdfConfigBox, pdfMarginBox, htmlConfigLabel, htmlConfigBox, htmlPaddBox);
        rightMain.setPadding(new Insets(20));


        return rightMain;
    }

    private MenuBar createMenu() {
        MenuBar topBar = new MenuBar();

        //file menu
        Menu fileMenu = new Menu("File");
        newItem = new MenuItem("New");
        openItem = new MenuItem("Open");
        saveItem = new MenuItem("Save");
        exitItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(newItem, openItem, saveItem, exitItem);

        //export menu
        Menu exportMenu = new Menu("Export");
        textItem = new MenuItem("as TXT");
        pdfItem = new MenuItem("as PDF");
        htmlItem = new MenuItem("as HTML");
        exportMenu.getItems().addAll(textItem, pdfItem, htmlItem);

        topBar.getMenus().addAll(fileMenu, exportMenu);

        return topBar;
    }


    private class openEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open");
            fileChooser.setInitialFileName("untitled.md");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.md"));
            File selected = fileChooser.showOpenDialog(stage);
            currentFile = selected;
            currentFileProperty.setValue(currentFile);
            fileName.setValue("MdEditor: " + currentFileProperty.get().getName());

            if (selected != null) {
                try {
                    Scanner reader = new Scanner(selected);
                    String data = "";
                    while (reader.hasNextLine()) {
                        data += reader.nextLine();
                        data += "\n";
                    }
                    centerContent.setText(data);
                } catch (FileNotFoundException e) {
                    Alert ioAlert = new Alert(Alert.AlertType.WARNING);
                    ioAlert.setTitle("Error");
                    ioAlert.setContentText("File couldn't be opened");
                    ioAlert.setHeaderText("Open data error");
                    ioAlert.showAndWait();
                }

            }
        }
    }

    private class saveEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.setInitialFileName("untitled.md");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.md"));
            String fileData = centerContent.getText();

            File selected = fileChooser.showSaveDialog(stage);

            if (selected != null) {
                try {

                    PrintWriter writer = new PrintWriter(new FileWriter(selected));
                    writer.print(fileData);
                    writer.close();
                    Alert saveAlert = new Alert(Alert.AlertType.INFORMATION);
                    saveAlert.setTitle("Success");
                    saveAlert.setContentText("Your file has been successfully saved.");
                    saveAlert.setHeaderText("File Saved.");
                    saveAlert.showAndWait();

                } catch (IOException e) {
                    Alert ioAlert = new Alert(Alert.AlertType.WARNING);
                    ioAlert.setTitle("Error");
                    ioAlert.setContentText("File couldn't be opened");
                    ioAlert.setHeaderText("Save data error");
                    ioAlert.showAndWait();
                }
            }
        }
    }

    private class exportEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export");
            String fileData = centerContent.getText();
            String fileDataArr[] = fileData.split("\n");
            for (String str: fileDataArr) {
                System.out.println(str);
            }

            if (actionEvent.getSource() == textItem) {
                fileChooser.setInitialFileName("untitled.txt");
                fileChooser.setInitialDirectory(new File("."));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

                File selected = fileChooser.showSaveDialog(stage);
                TextFileBuilder textFile = new TextFileBuilder(selected);

                if (selected != null){
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter(selected));
                        //text file building, line by line
                        for (String str : fileDataArr) {
                            if(str.length() == 0){
                                writer.println();
                            }
                            else if (str.charAt(0) == '#' && str.charAt(1) != '#' && str.charAt(2) != '#'){
                                String updated = textFile.makeHeader1(str);
                                writer.println(updated);
                            }
                            else if (str.charAt(0) == '#' && str.charAt(1) == '#' && str.charAt(2) != '#'){
                                String updated = textFile.makeHeader2(str);
                                writer.println(updated);
                            }
                            else if (str.charAt(0) == '#' && str.charAt(1) == '#' && str.charAt(2) == '#') {
                                String updated = textFile.makeHeader3(str);
                                writer.println(updated);
                            }
                            else if (str.charAt(0) == '>'){
                                String updated = textFile.makeBlockQuote(str);
                                writer.println(updated);
                            }
                            else if (str.charAt(str.length() - 1) == '>'){
                                String updated = textFile.makeLineBreak(str);
                                writer.println(updated);
                            }
                            else if (str == "---"){
                                String updated = textFile.makeHorizontalRule(str);
                                writer.println(updated);
                            }
                            else {
                                String updated = textFile.makeParagraph(str, textLineWidthProperty.get());
                                writer.println(updated);
                            }
                        }
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            } else if (actionEvent.getSource() == pdfItem) {
                Document pdfDocument = new Document();
                PdfFileBuilder pdfFile = new PdfFileBuilder();
                try {
                    PdfWriter.getInstance(pdfDocument, new FileOutputStream(currentFileProperty.get().getName()));
                    pdfDocument.open();
                    pdfDocument.setPageSize(PageSize.LETTER);

                    //pdf margin and font
                    float margin = pdfMarginProperty.get() * 72;
                    pdfDocument.setMargins(margin, margin, margin, margin);

                    for (String str : fileDataArr ) {
                        if(str.length() == 0){
                            Paragraph paragraph = new Paragraph();
                            paragraph.setSpacingAfter(1);
                            pdfDocument.add(paragraph);
                        }
                        else if (str.charAt(0) == '#' && str.charAt(1) != '#' && str.charAt(2) != '#'){
                            com.itextpdf.text.Font h1Font = FontFactory.getFont(pdfFontProperty.get(), 24, BaseColor.BLACK);
                            h1Font.setStyle(com.itextpdf.text.Font.BOLD);
                            String updated = pdfFile.makeHeader1(str);
                            Paragraph paragraph = new Paragraph(updated, h1Font);
                            paragraph.setSpacingAfter(12);
                            pdfDocument.add(paragraph);
                        }
                        else if (str.charAt(0) == '#' && str.charAt(1) == '#' && str.charAt(2) != '#'){
                            com.itextpdf.text.Font h2Font = FontFactory.getFont(pdfFontProperty.get(), 20, BaseColor.BLACK);
                            h2Font.setStyle(com.itextpdf.text.Font.BOLD);
                            String updated = pdfFile.makeHeader2(str);
                            Paragraph paragraph = new Paragraph(updated, h2Font);
                            paragraph.setSpacingAfter(12);
                            pdfDocument.add(paragraph);

                        }
                        else if (str.charAt(0) == '#' && str.charAt(1) == '#' && str.charAt(2) == '#') {
                            com.itextpdf.text.Font h3Font = FontFactory.getFont(pdfFontProperty.get(), 16, BaseColor.BLACK);
                            h3Font.setStyle(com.itextpdf.text.Font.BOLD);
                            String updated = pdfFile.makeHeader3(str);
                            Paragraph paragraph = new Paragraph(updated, h3Font);
                            paragraph.setSpacingAfter(12);
                            pdfDocument.add(paragraph);

                        }
                        else if (str.charAt(0) == '>'){
                            com.itextpdf.text.Font blockQuoteFont = FontFactory.getFont(pdfFontProperty.get(), 12, BaseColor.BLACK);
                            String updated = pdfFile.makeBlockQuote(str);
                            Paragraph paragraph = new Paragraph(updated, blockQuoteFont);
                            pdfDocument.add(paragraph);

                        }
                        else if (str == "---"){
                            com.itextpdf.text.Font horizontalFont = FontFactory.getFont(pdfFontProperty.get(), 12, BaseColor.BLACK);
                            Paragraph spacerParagraph = new Paragraph(" ", horizontalFont);
                            LineSeparator horizontalRule = new LineSeparator();
                            horizontalRule.setAlignment(Element.ALIGN_CENTER);
                            horizontalRule.setPercentage(50f);
                            pdfDocument.add(spacerParagraph);
                            pdfDocument.add(horizontalRule);
                            pdfDocument.add(spacerParagraph);
                        }
                        else {
                            com.itextpdf.text.Font paragraphFont = FontFactory.getFont(pdfFontProperty.get(), 12, BaseColor.BLACK);
                            Paragraph paragraph = new Paragraph(str, paragraphFont);
                            paragraph.setSpacingAfter(12);
                            pdfDocument.add(paragraph);
                        }

                    }
                    pdfDocument.close();
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            } else {
                fileChooser.setInitialFileName("untitled.html");
                fileChooser.setInitialDirectory(new File("."));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.html"));

                File selected = fileChooser.showSaveDialog(stage);
                HtmlFileBuilder htmlFile = new HtmlFileBuilder(selected);

                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(selected));
                    writer.print("<!DOCTYPE html>" + "\n" +
                            "<html lang='en'>" + "\n" +
                            "<head>" + "\n" +
                            "<meta charset='utf-8'>" + "\n" +
                            "<title>Produced by MdEditor</title>" + "\n" +
                            "</head>" + "\n" +
                            "<body style='color:" + htmlColorProperty.get() + ";padding:" + htmlPaddingProperty.get() + "'>" + "\n");
                    for (String str : fileDataArr) {
                        if(str.length() == 0){
                            writer.println();
                        }
                        else if (str.charAt(0) == '#' && str.charAt(1) != '#' && str.charAt(2) != '#'){
                            String updated = htmlFile.makeHeader1(str);
                            writer.println(updated);
                        }
                        else if (str.charAt(0) == '#' && str.charAt(1) == '#' && str.charAt(2) != '#'){
                            String updated = htmlFile.makeHeader2(str);
                            writer.println(updated);
                        }
                        else if (str.charAt(0) == '#' && str.charAt(1) == '#' && str.charAt(2) == '#'){
                            String updated = htmlFile.makeHeader3(str);
                            writer.println(updated);
                        }
                        else if (str.charAt(0) == '>'){
                            String updated = htmlFile.makeBlockQuote(str);
                            writer.println(updated);
                        }
                        else if(str.equals("---")){
                            String updated = htmlFile.makeHorizontalRule(str);
                            writer.println(updated);
                        }
                        else {
                            String updated = htmlFile.makeParagraph(str, 0);
                            writer.println(updated);
                        }


                    }
                    writer.print("\n" + "</body>" + "\n" + "</html>");
                    writer.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
