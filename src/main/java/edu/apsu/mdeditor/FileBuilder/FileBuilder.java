package edu.apsu.mdeditor.FileBuilder;

public abstract class FileBuilder {
    abstract String makeHeader1(String str);
    abstract String makeHeader2(String str);
    abstract String makeHeader3(String str);
    abstract String makeParagraph(String str, int margin);
    abstract String makeBlockQuote(String str);
    abstract String makeLineBreak(String str);
    abstract String makeHorizontalRule(String str);

}
