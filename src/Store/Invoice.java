package Store;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class Invoice {
private static int invoiceNum = 1;
    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;

    public static String getPdfFilename() {
        return pdfFilename;
    }

    private static String pdfFilename;

    public Invoice(){
        invoiceNum++;
    }

    public static void generateInvoice() {

       List<Purchase> itemList = Cart.getCartList();
        for (Purchase p:itemList) {

        }

        Invoice generateInvoice = new Invoice();
        pdfFilename = "JF001020"+invoiceNum+".pdf";
        if (itemList.size()<1)
        {

            System.err.println("Usage: java "+ generateInvoice.getClass().getName()+
                    " PDF_Filename");
            System.exit(1);
        }


        generateInvoice.createPDF(pdfFilename);

    }

    private void createPDF (String pdfFilename){

        Document doc = new Document();
        PdfWriter docWriter = null;
        initializeFonts();

        try {
            String path = pdfFilename;
            docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
            doc.addAuthor("Jeff");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("Jeff's Fishing Shack");
            doc.addTitle("Invoice");
            doc.setPageSize(PageSize.LETTER);

            doc.open();
            PdfContentByte cb = docWriter.getDirectContent();

            boolean beginPage = true;
            int y = 0;


            List<Purchase> itemList = Cart.getCartList();
            for (Purchase p:itemList) {
                if(beginPage) {
                    beginPage = false;
                    generateLayout(doc, cb);
                    generateHeader(doc, cb);
                    y = 615;
                }
                generateDetail(doc, cb, invoiceNum, y,p);
                y = y - 15;
                if(y < 50){
                    printPageNumber(cb);
                    doc.newPage();
                    beginPage = true;
                }
            }

            for (int i =0;i<10;i++){
                if(beginPage){
                    beginPage = false;
                    generateLayout(doc, cb);
                    generateHeader(doc, cb);
                    y = 615;
                }
                y = y - 15;
                if(y < 50){
                    printPageNumber(cb);
                    doc.newPage();
                    beginPage = true;
                }
            }
            printPageNumber(cb);

        }
        catch (DocumentException dex)
        {
            dex.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (doc != null)
            {
                doc.close();
            }
            if (docWriter != null)
            {
                docWriter.close();
            }
        }

    }

    private void generateLayout(Document doc, PdfContentByte cb)  {

        try {

            cb.setLineWidth(1f);

            // Invoice Header box layout
            cb.rectangle(420,700,150,60);
            cb.moveTo(420,720);
            cb.lineTo(570,720);
            cb.moveTo(420,740);
            cb.lineTo(570,740);
            cb.moveTo(480,700);
            cb.lineTo(480,760);
            cb.stroke();

            // Invoice Header box Text Headings
            createHeadings(cb,422,743,"Customer Id.");
            createHeadings(cb,422,723,"Invoice No.");
            createHeadings(cb,422,703,"Invoice Date");

            createHeadings(cb,450,193,"Total ");
            createContent(cb,530,193,  "  -  "+CustomerDashboard.getTotal() ,PdfContentByte.ALIGN_LEFT);
            createHeadings(cb,450,210,"Discount ");
            createHeadings(cb,450,230,"Sub Total ");

            // Invoice Detail box layout
            cb.rectangle(20,250,550,400);
          //  cb.rectangle(20,50,550,200);


            cb.moveTo(20,630);
            cb.lineTo(570,630);

            cb.moveTo(450,205);
            cb.lineTo(570,205);

            cb.moveTo(450,190);
            cb.lineTo(570,190);

            cb.moveTo(450,187);
            cb.lineTo(570,187);


            cb.moveTo(400,200);
            cb.lineTo(400,200);


            cb.moveTo(400,250);
            cb.lineTo(400,650);

            cb.moveTo(150,250);
            cb.lineTo(150,650);

            cb.moveTo(430,250);
            cb.lineTo(430,650);

            cb.moveTo(500,250);
            cb.lineTo(500,650);

            cb.stroke();

            // Invoice Detail box Text Headings

            createHeadings(cb,22,633,"ITEM ID");
            createHeadings(cb,152,633,"ITEM NAME");
            createHeadings(cb,432,633,"Price");
            createHeadings(cb,410,633,"Qty");
            createHeadings(cb,502,633,"Ext Price");

            //addStaff the images
            Image companyLogo = Image.getInstance("C:\\Users\\Azhar\\IdeaProjects\\finalpp2\\src\\Store\\productPics\\logo.jpg");
            companyLogo.setAbsolutePosition(25,700);
            companyLogo.scalePercent(25);
            doc.add(companyLogo);

        }

        catch (DocumentException dex){
            dex.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
private String getDate(){
    String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
return timeStamp;
}
    private void generateHeader(Document doc, PdfContentByte cb)  {
        try {

            createHeadings(cb,200,750,"Jeff's Fishing Shack");
            createHeadings(cb,200,735,"Trading as: Octopus Pty Ltd");
            createHeadings(cb,200,720,"Shop 4, Hillarys Boat Harbour");
            createHeadings(cb,200,705,"Hillarys, WA, 6025");
            createHeadings(cb,200,690,"Sri Lanka");

            createHeadings(cb,482,743,"CU0010-"+Customer.getCustomer().id);//customer id
            createHeadings(cb,482,723,"JF001200"+Invoice.invoiceNum);//invoice no
            createHeadings(cb,482,703,getDate());


        }

        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void generateDetail(Document doc, PdfContentByte cb, int index, int y,Purchase p)  {
        DecimalFormat df = new DecimalFormat("0.00");

        try {

            createContent(cb,415,y,p.getQuantity()+"",PdfContentByte.ALIGN_RIGHT);
            createContent(cb,22,y, p.getItem().getCode() +"  "+ p.getItem().getId() ,PdfContentByte.ALIGN_LEFT);
            createContent(cb,152,y, p.getItem().getName() + "  -  "+p.getItem().getSize() ,PdfContentByte.ALIGN_LEFT);
           // createHeadings(cb,450,193,"Total");
            BigDecimal price = p.getItem().getPrice();
            BigDecimal extPrice = p.getPurchaseAmount() ;
            createContent(cb,498,y,df.format(price) ,PdfContentByte.ALIGN_RIGHT);
            createContent(cb,568,y, df.format(extPrice),PdfContentByte.ALIGN_RIGHT);

        }

        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text){


        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();

    }

    private void printPageNumber(PdfContentByte cb){


        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber+1), 570 , 25, 0);
        cb.endText();

        pageNumber++;

    }

    private void createContent(PdfContentByte cb, float x, float y, String text, int align){


        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text.trim(), x , y, 0);
        cb.endText();

    }

    private void initializeFonts(){


        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}