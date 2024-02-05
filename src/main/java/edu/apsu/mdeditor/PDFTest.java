package edu.apsu.mdeditor;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;

/**
 * This code demonstrates how to yse the iText PDF library to
 * make basic PDF files.  When run, it will create the file
 * sample.pdf in the root of the project.
 */
public class PDFTest {
    public static void main(String[] args) {

        Document pdfDocument = new Document();

        // The way you save PDF files is different than what you have done before.
        // Here, you attach a PdfWriter to the document.  As you add elements,
        // for example, paragraphs, the data is output.  Look for the calls
        // to document.open() which starts the writing and document.close()
        // which finishes the writing process.
        String filename = "sample.pdf";
        try {
            PdfWriter.getInstance(pdfDocument, new FileOutputStream(filename));

            // ------------------------------------------------
            // set the paper size to 8.5 inch x 11 inch
            pdfDocument.setPageSize(PageSize.LETTER);

            // ------------------------------------------------
            // Set the margin, i.e., the space from the edge of the
            // paper to the text.
            //
            // The 72 represents 72 points. Points is a common measure of
            // units for things related to printing.
            //
            // 72 points == 1 inch
            int marginInInches = 1;
            float margin = marginInInches * 72;
            pdfDocument.setMargins(margin, margin, margin, margin);

            // ------------------------------------------------
            // prepare the document for adding text
            pdfDocument.open();

            // ------------------------------------------------
            // set the font name.  We will be using the constants
            // from the FontFactory

            final String fontName = FontFactory.HELVETICA;

            // ------------------------------------------------
            // At this point, you can start adding paragraphs.  By changing
            // fonts, we can make paragraphs look like different types of headings
            // or paragraphs.
            //
            // To make things look nice, we will also be adding some space after
            // each paragraph.  The examples show the Markdown code followed
            // the Java code for the PDF.

            // ----------------------------------------------
            // Markdown - heading 1
            //
            // # I am a heading 1
            Font h1Font = FontFactory.getFont(fontName, 24, BaseColor.BLACK);
            h1Font.setStyle(Font.BOLD);
            String text = "I am a heading 1";
            Paragraph paragraph = new Paragraph(text, h1Font);
            paragraph.setSpacingAfter(12);
            pdfDocument.add(paragraph);

            // ----------------------------------------------
            // Markdown - heading 2
            //
            // ## I am a heading 2
            Font h2Font = FontFactory.getFont(fontName, 20, BaseColor.BLACK);
            h2Font.setStyle(Font.BOLD);
            text = "I am a heading 2";
            paragraph = new Paragraph(text, h2Font);
            paragraph.setSpacingAfter(12);
            pdfDocument.add(paragraph);

            // ----------------------------------------------
            // Markdown - heading 3
            //
            // ### I am a heading 3
            Font h3Font = FontFactory.getFont(fontName, 16, BaseColor.BLACK);
            h3Font.setStyle(Font.ITALIC);
            text = "I am a heading 3";
            paragraph = new Paragraph(text, h3Font);
            paragraph.setSpacingAfter(12);
            pdfDocument.add(paragraph);

            // ----------------------------------------------
            // Markdown - a regular paragraph
            //
            // I am the start of a paragraph. Lorem ipsum dolor sit amet,
            // consectetur adipiscing elit. Maecenas venenatis pharetra
            // ornare. Vivamus eleifend odio massa. Cras ac lacinia ligula,
            // vel sagittis enim. Orci varius natoque penatibus et magnis
            // dis parturient montes, nascetur ridiculus mus. Phasellus
            // nulla felis, consectetur et vestibulum ac, accumsan eu lacus.
            // Pellentesque eget orci varius, vehicula odio sit amet,
            // rhoncus lectus. Vivamus nec malesuada mauris, at scelerisque
            // est. Maecenas rhoncus posuere aliquam.
            //
            Font paragraphFont = FontFactory.getFont(fontName, 12, BaseColor.BLACK);
            text = "I am the start of a paragraph. Lorem ipsum dolor sit amet, " +
                    "consectetur adipiscing elit. Maecenas venenatis pharetra " +
                    "ornare. Vivamus eleifend odio massa. Cras ac lacinia ligula, " +
                    "vel sagittis enim. Orci varius natoque penatibus et magnis " +
                    "dis parturient montes, nascetur ridiculus mus. Phasellus " +
                    "nulla felis, consectetur et vestibulum ac, accumsan eu lacus. " +
                    "Pellentesque eget orci varius, vehicula odio sit amet, rhoncus " +
                    "lectus. Vivamus nec malesuada mauris, at scelerisque est. " +
                    "Maecenas rhoncus posuere aliquam. ";
            paragraph = new Paragraph(text, paragraphFont);
            paragraph.setSpacingAfter(12);
            pdfDocument.add(paragraph);

            // ----------------------------------------------
            // Markdown - block quote.
            // Block quotes use the same font as paragraphs.
            // We make them block quotes by adding space to the left.
            // Here is a short paragraph followed by a block quote.
            //
            // From HGTTG:
            //
            // > "Space," it says, "is big. Really big. You just won’t believe how
            // > vastly, hugely, mindbogglingly big it is. I mean, you may think
            // > it’s a long way down the road to the chemist’s, but that’s just
            // > peanuts to space."
            //

            text = "From the HGTTG:";
            paragraph = new Paragraph(text, paragraphFont);
            paragraph.setSpacingAfter(12);
            pdfDocument.add(paragraph);

            text =  "\"Space,\" it says, \"is big. Really big. You just won’t believe how " +
                    "vastly, hugely, mindbogglingly big it is. I mean, you may think " +
                    "it’s a long way down the road to the chemist’s, but that’s just " +
                    "peanuts to space.\"";
            paragraph = new Paragraph(text, paragraphFont);
            paragraph.setSpacingAfter(12);
            paragraph.setIndentationLeft(24);
            pdfDocument.add(paragraph);

            // ----------------------------------------------
            // Markdown - horizontal rule.
            //
            // Here are two short paragraphs with a horizontal rul in between.
            //
            // This is the small paragraph BEFORE the horizontal rule.
            //
            // ---
            //
            // This is the small paragraph AFTER the horizontal rule.
            text =  "This is the small paragraph BEFORE the horizontal rule.";
            paragraph = new Paragraph(text, paragraphFont);
            paragraph.setSpacingAfter(12);
            pdfDocument.add(paragraph);

            Paragraph spacerParagraph = new Paragraph(" ", paragraphFont);
            LineSeparator horizontalRule = new LineSeparator();
            horizontalRule.setAlignment(Element.ALIGN_CENTER);
            horizontalRule.setPercentage(50f);
            pdfDocument.add(spacerParagraph);
            pdfDocument.add(horizontalRule);
            pdfDocument.add(spacerParagraph);

            text =  "This is the small paragraph AFTER the horizontal rule.";
            paragraph = new Paragraph(text, paragraphFont);
            paragraph.setSpacingAfter(12);
            pdfDocument.add(paragraph);

            // When you are done adding text, close the document
            pdfDocument.close();
        } catch (Exception e) {
            System.err.println("Error opening or writing to " + filename);
            throw new RuntimeException(e);
        }

    }
}
