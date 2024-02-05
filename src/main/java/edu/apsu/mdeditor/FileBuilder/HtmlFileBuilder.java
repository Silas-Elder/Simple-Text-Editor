package edu.apsu.mdeditor.FileBuilder;

import java.io.File;

public class HtmlFileBuilder extends FileBuilder{
    public File currentFile;
    public HtmlFileBuilder(){

    }
    public HtmlFileBuilder(File selected) {
        super();
        this.currentFile = selected;
    }

    @Override
    public String makeHeader1(String dataLine) {
        dataLine.substring(1);
        String newString = "<h1>" + dataLine + "</h1>";
        return newString;
    }

    @Override
    public String makeHeader2(String dataLine) {
        dataLine.substring(2);
        String newString = "<h2>" + dataLine + "</h2>";
        return newString;
    }

    @Override
    public String makeHeader3(String dataLine) {
        dataLine.substring(3);
        String newString = "<h3>" + dataLine + "</h3>";
        return newString;
    }

    @Override
    public String makeParagraph(String dataLine, int margin) {
        String newString = "<p>" + dataLine + "</p>";
        return newString;
    }

    @Override
    public String makeBlockQuote(String dataLine) {
        dataLine.substring(1);
        String newString = "<blockquote>" + dataLine + "</blockquote>";
        return newString;
    }

    @Override
    public String makeLineBreak(String str) {
        return null;
    }

    @Override
    public String makeHorizontalRule(String dataLine) {
        dataLine = "<hr>";
        return dataLine;
    }
}
