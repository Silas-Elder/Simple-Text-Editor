package edu.apsu.mdeditor.FileBuilder;

import java.io.*;
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

public class TextFileBuilder extends FileBuilder{
    public File currentFile;

    public TextFileBuilder(){

    }

    public TextFileBuilder(File file){
        this.currentFile = file;
    }

    @Override
    public String makeHeader1(String dataLine) {
        //put the new line into a string, modify it, and update the file
        dataLine = dataLine.substring(1, dataLine.length());
        String newString = dataLine.toUpperCase();
        return newString;


    }

    @Override
    public String makeHeader2(String dataLine) {
        dataLine = dataLine.substring(2, dataLine.length());
        String newString = dataLine.toUpperCase();
        return newString;
    }

    @Override
    public String makeHeader3(String dataLine) {
        dataLine = dataLine.substring(3);
        String newString = dataLine.toUpperCase();
        return newString;
    }

    @Override
    public String makeParagraph(String dataLine, int width) {
        //use built in string wrapper
        dataLine = WordUtils.wrap(dataLine, width);
        return dataLine;
    }

    @Override
    public String makeBlockQuote(String dataLine) {
        dataLine = dataLine.substring(1);
        String newString = "=====" + "\n" + dataLine + "\n" + "=====";
        return newString;
    }

    @Override
    public String makeLineBreak(String dataLine) {
        dataLine = dataLine.replaceAll("<br>", "\n");
        return dataLine;
    }

    @Override
    public String makeHorizontalRule(String dataLine) {
        dataLine = dataLine.replaceAll("---", "*   *   *   *");
        return dataLine;
    }

    public static void main(String[] args) {

    }
}
