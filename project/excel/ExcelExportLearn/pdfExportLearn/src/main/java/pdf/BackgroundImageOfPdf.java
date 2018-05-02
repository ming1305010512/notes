package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by 龙鸣 on 2017/9/9.
 * Description:在pdf文件中添加背景图片，并指定文本在pdf中的位置
 *
 * @author:龙鸣
 * @version:1.0
 */
public class BackgroundImageOfPdf {

    public static void main(String[] args) {
        BackgroundImageOfPdf imageOfPdf=new BackgroundImageOfPdf();
        String filename="D:/image.pdf";
        imageOfPdf.createPdf(filename);
    }

    public void createPdf(String filename){
        //参数二：文档与页面的左边距为10px
        Document document=new Document(PageSize.A4.rotate(),10,0,10,0);
        try {
            PdfWriter pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(filename));
            document.addTitle("ID.NET");
            document.addAuthor("dotuian");
            document.addSubject("This is the subject of the PDF file.");
            document.addKeywords("This is the keyword of the PDF file.");
            document.open();
            Image image=Image.getInstance("D:/login.jpg");
            document.add(image);

            //通过该对象来写入文本内容
            PdfContentByte pdfContentByte=pdfWriter.getDirectContent();
            pdfContentByte.beginText();
            //参数一：字体名字 参数二：字体编码 参数三:boolean型，是否嵌入
            BaseFont baseFont=BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.WINANSI, BaseFont.EMBEDDED);
            //设置font和字体大小
            pdfContentByte.setFontAndSize(baseFont,12);

            for (int i = 0; i <= 842; i = i + 50) {
                for (int j = 0; j <= 595; j = j + 20) {
                    //创建一个文本矩阵，参数一：矩阵左上角的x坐标，参数二：矩阵左上角的y坐标
                    pdfContentByte.setTextMatrix(i, j);
                    pdfContentByte.showText("(" + i + ":" + j + ")");
                }
            }
            pdfContentByte.endText();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            document.close();
        }

    }
}
