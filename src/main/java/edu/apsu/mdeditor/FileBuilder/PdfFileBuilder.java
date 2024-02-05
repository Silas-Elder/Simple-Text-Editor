package edu.apsu.mdeditor.FileBuilder;

public class PdfFileBuilder extends FileBuilder{
    @Override
    public String makeHeader1(String str) {
        str.substring(1);
        return str;
    }

    @Override
    public String makeHeader2(String str) {
        str.substring(2);
        return str;
    }

    @Override
    public String makeHeader3(String str) {
        str.substring(3);
        return str;
    }

    @Override
    public String makeParagraph(String str, int margin) {
        return null;
    }

    @Override
    public String makeBlockQuote(String str) {
        str.substring(1);
        String newString = "     " + str;
        return newString;
    }

    @Override
    String makeLineBreak(String str) {
        return null;
    }

    @Override
    public String makeHorizontalRule(String str) {
        str = "";
        return str;
    }
}
