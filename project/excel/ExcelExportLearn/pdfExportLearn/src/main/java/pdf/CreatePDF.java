package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by 龙鸣 on 2017/9/8.
 * Description:使用iText生成PDF文件
 *
 * @author:龙鸣
 * @version:1.0
 */
public class CreatePDF {
    public static void main(String[] args) {
        CreatePDF p001=new CreatePDF();
        String filename="D:/p001.pdf";
        p001.createPDF(filename);
    }

    public void createPDF(String filename){
        //第一步:创建文档对象
        Document document=new Document(PageSize.A4);
        try {
            //第二步:将pdf文档写入文件中
            PdfWriter.getInstance(document,new FileOutputStream(filename));
            document.addTitle("ID.NET");
            document.addAuthor("dotuian");
            //设置主体
            document.addSubject("This is the subject of the PDF file");
            //设置关键字
            document.addKeywords("This is the keyWords of the PDF file");

            //第三步
            document.open();
            //第四步
            document.add(new Paragraph("Hello world!"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            document.close();
        }

    }
}
