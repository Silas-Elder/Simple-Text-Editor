package edu.apsu.mdeditor;

import org.apache.commons.text.WordUtils;

public class WordWrapTest {
    public static void main(String[] args) {
        // War of the Worlds, by H. G. Wells
        String original = "No one would have believed in the last years of the nineteenth century " +
                "that this world was being watched keenly and closely by intelligences greater " +
                "than man's and yet as mortal as his own; that as men busied themselves about " +
                "their various concerns they were scrutinised and studied, perhaps almost as " +
                "narrowly as a man with a microscope might scrutinise the transient creatures " +
                "that swarm and multiply in a drop of water.";

        String wrappedLines = WordUtils.wrap(original, 10);

        System.out.println("ORIGINAL");
        System.out.println(original);
        System.out.println();
        System.out.println("WRAPPED");
        System.out.println(wrappedLines);
    }
}
