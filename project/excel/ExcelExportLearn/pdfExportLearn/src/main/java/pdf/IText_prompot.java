package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 龙鸣 on 2017/9/7.
 * Description:
 *
 * @author:龙鸣
 * @version:1.0
 */
public class IText_prompot {

    public static void main(String[] args) throws IOException {
        IText_prompot iText_prompot=new IText_prompot();
        String filename="D:/iTextPrompot.pdf";
        iText_prompot.createPdf(filename);
    }

    //设置页面大小,背景色，页边空白
    private void createPdf(String filename) throws IOException {
        //设置页大小
        Rectangle rectangle=new Rectangle(PageSize.B5.rotate());
        //页面背景颜色
        rectangle.setBackgroundColor(BaseColor.ORANGE);
        Document document=new Document(rectangle);

        try {
            PdfWriter pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(filename));
            //设置pdf的版本
            pdfWriter.setPdfVersion(PdfWriter.VERSION_1_2);

            //设置密码
            //参数一：用户名 参数二：密码 参数三：？ 参数四：加密类型
//            pdfWriter.setEncryption("aaa".getBytes(),"bbb".getBytes(),PdfWriter.ALLOW_SCREENREADERS,PdfWriter.STANDARD_ENCRYPTION_128);
            //设置文档属性
            document.addTitle("Title@sample");
            document.addAuthor("Author@rensanning");
            document.addSubject("Subject@iText sample");
            document.addKeywords("Keywords@iText");
            document.addCreator("Creator@iText");
            //设置页边空白
            document.setMargins(10,20,30,40);
            document.open();
            document.add(new Paragraph("Hello World"));


            //图片水印,将D:/table.pdf中的内容写入D:/iText.pdf中，并添加图片
            PdfReader reader = new PdfReader("D:/table.pdf");
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:/iText.pdf"));

            Image img = Image.getInstance("D:/login.jpg");
            img.setAbsolutePosition(200, 400);
            //参数：第几页
            PdfContentByte under = stamp.getUnderContent(1);
            under.addImage(img);
            LineSeparator underLine = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);

            //文字水印
            PdfContentByte over = stamp.getOverContent(1);
            over.beginText();
            //参数一：字体名字 参数二：字体编码 参数三：是否嵌入
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
                    BaseFont.EMBEDDED);
            over.setFontAndSize(bf, 18);
            over.setTextMatrix(30, 30);
            //参数二：文本内容 参数三：x坐标 参数四：y坐标 参数5：旋转角度
            over.showTextAligned(Element.ALIGN_LEFT, "DUPLICATE", 230, 430, 45);
            over.endText();

            stamp.close();
            reader.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            document.close();
        }
    }
}
