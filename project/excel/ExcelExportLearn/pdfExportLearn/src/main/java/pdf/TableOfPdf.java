package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by 龙鸣 on 2017/9/8.
 * Description:在pdf文件中创建表格
 *
 * @author:龙鸣
 * @version:1.0
 */
public class TableOfPdf {
    public static void main(String[] args) {
        TableOfPdf table=new TableOfPdf();
        String filename="D:/table.pdf";
        table.createPdf(filename);
    }

    public void createPdf(String filename){

        Document document=new Document();
        try {
            PdfWriter.getInstance(document,new FileOutputStream(filename));
            document.addTitle("ID.NET");
            document.addAuthor("dotuian");
            document.addSubject("This is the subject of the PDF file.");
            document.addKeywords("This is the keyword of the PDF file.");
            document.open();
            PdfPTable table=createTable1();
            document.add(table);

            table=createTable2();
            //设置上边距为5px
            table.setSpacingBefore(5);
            //设置下边距为5px
            table.setSpacingAfter(5);
            document.add(table);

            table=createTable3();
            document.add(table);

            table=createTable4();
            table.setSpacingBefore(5);
            table.setSpacingAfter(5);
            document.add(table);

            table=createTable5();
            document.add(table);

            table=createTable6();
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            document.close();
        }
    }

    //创建一个表格
    public static PdfPTable createTable1() throws DocumentException {
        //参数3代表表格有三列
        PdfPTable table=new PdfPTable(3);
        //表格宽度占页面的百分比
        table.setWidthPercentage(288/3.23f);
        //设置每一列的相对宽度
        table.setWidths(new int[]{2,2,2});
        PdfPCell cell;
        cell=new PdfPCell(new Phrase("Table 1"));
        //当前单元格跨三列
        cell.setColspan(3);
        table.addCell(cell);

        cell=new PdfPCell(new Phrase("Cell with rowspan 2"));
        //当前单元格跨2行
        cell.setRowspan(2);
        table.addCell(cell);
        //直接设置单元格的内容
        table.addCell("row 1;cell 1");
        table.addCell("row 1;cell 2");
        table.addCell("row 2;cell 1");
        table.addCell("row 2;cell 2");
        return table;
    }

    public static PdfPTable createTable2()throws DocumentException{

        PdfPTable table=new PdfPTable(3);
        table.setTotalWidth(288);

        table.setLockedWidth(true);
        table.setWidths(new float[]{2,1,1});
        PdfPCell cell;
        cell=new PdfPCell(new Phrase("Table 2"));
        cell.setColspan(3);
        table.addCell(cell);
        cell=new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1;cell 1");
        table.addCell("row 1;cell 2");
        table.addCell("row 2;cell 1");
        table.addCell("row 2;cell 2");
        return table;
    }

    public static PdfPTable createTable3() throws DocumentException {
        PdfPTable table=new PdfPTable(new float[]{2,1,1});
        table.setWidthPercentage(55.067f);
        PdfPCell cell;
        cell=new PdfPCell(new Phrase("Table3"));
        cell.setColspan(3);
        table.addCell(cell);
        cell=new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1;cell 1");
        table.addCell("row 1;cell 2");
        table.addCell("row 2;cell 1");
        table.addCell("row 2;cell 2");
        return table;
    }

    public static PdfPTable createTable4()throws DocumentException{
        PdfPTable table=new PdfPTable(3);
        Rectangle rectangle=new Rectangle(288,770);
        //设置百分比
        table.setWidthPercentage(new float[]{144,72,72},rectangle);
        PdfPCell cell;
        cell=new PdfPCell(new Phrase("Table4"));
        cell.setColspan(3);
        table.addCell(cell);
        cell=new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);

        table.addCell("row 1;cell 1");
        table.addCell("row 1;cell 2");
        table.addCell("row 2;cell 1");
        table.addCell("row 2;cell 2");
        return table;
    }

    public static PdfPTable createTable5() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(new float[] { 144, 72, 72 });
        table.setLockedWidth(true);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 5"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
    public static PdfPTable createTable6() throws DocumentException{
        PdfPTable table = new PdfPTable(10);
        table.setTotalWidth(595);
        //table.setLockedWidth(true);


        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 6"));
        cell.setColspan(10);
        table.addCell(cell);

        for (int i = 1; i < 100; i++) {
            cell = new PdfPCell(new Phrase(String.valueOf(i)));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }


//    cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
//    cell.setRowspan(2);
//    table.addCell(cell);
//    table.addCell("row 1; cell 1");
//    table.addCell("row 1; cell 2");
//    table.addCell("row 2; cell 1");
//    table.addCell("row 2; cell 2");
        return table;
    }
}
