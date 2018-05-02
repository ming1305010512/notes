package lm.excel;

import lm.dto.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.plaf.synth.Region;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 龙鸣 on 2017/9/8.
 * Description:
 *
 * @author:龙鸣
 * @version:1.0
 */
public class CreateSimpleExcelToDisk {

    private static List<Student> getStudent() throws Exception {
        List list=new ArrayList();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
        Student user1=new Student(1,"张三",16,format.parse("1997-03-12"));
        Student user2 = new Student(2, "李四", 17, format.parse("1996-08-12"));
        Student user3 = new Student(3, "王五", 26, format.parse("1985-11-12"));
        list.add(user1);
        list.add(user2);
        list.add(user3);

        return list;
    }

    public static void main(String[] args) throws Exception {
        //创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb=new HSSFWorkbook();
        //第二步：在webbook中添加一个sheet,对应excel中的sheet
        HSSFSheet sheet=wb.createSheet("学生表一");
        //第三步：在sheet中添加表头第0行，
        HSSFRow row=sheet.createRow((int)0);


        //第四部，创建单元格
        HSSFCellStyle style=wb.createCellStyle();

        //创建一个居中格式
        //水平居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置背景颜色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //设置字体
        HSSFFont font=wb.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short)10);
        style.setFont(font);

        //设置边框
        HSSFCellStyle style2=wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框    
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框    
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 

        //设置列宽
        sheet.setColumnWidth(0,3766);
        sheet.setColumnWidth(1,3766);
        sheet.setColumnWidth(2,3766);
        sheet.setColumnWidth(3,3766);

        //合并单元格
        //参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号    
        CellRangeAddress region1=new CellRangeAddress(0,1,0,0);
        CellRangeAddress region2=new CellRangeAddress(0,1,1,1);
        CellRangeAddress region3=new CellRangeAddress(0,1,2,2);
        CellRangeAddress region4=new CellRangeAddress(0,1,3,3);
        sheet.addMergedRegion(region1);
        sheet.addMergedRegion(region2);
        sheet.addMergedRegion(region3);
        sheet.addMergedRegion(region4);


        HSSFCell cell=row.createCell((short)0);
        cell.setCellValue("学号");
        cell.setCellStyle(style);
//        cell.setCellStyle(style2);
        cell = row.createCell((short) 1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
//        cell.setCellStyle(style2);
        cell = row.createCell((short) 2);
        cell.setCellValue("年龄");
        cell.setCellStyle(style);
//        cell.setCellStyle(style2);
        cell = row.createCell((short) 3);
        cell.setCellValue("生日");
        cell.setCellStyle(style);
//        cell.setCellStyle(style2);

        //第五步：写入实体数据
        List list=CreateSimpleExcelToDisk.getStudent();

        for (int i = 0; i < list.size(); i++) {

            row=sheet.createRow((int)i+1);
            Student stu= (Student) list.get(i);
            //创建单元格
            cell=row.createCell((short) 0);
            cell.setCellValue((double) stu.getId());
            cell.setCellStyle(style2);
            cell=row.createCell((short) 1);
            cell.setCellValue(stu.getName());
            cell.setCellStyle(style2);
            cell=row.createCell((short) 2);
            cell.setCellValue((double) stu.getAge());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 3);
            cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu
                    .getBirth()));
            cell.setCellStyle(style2);
        }

        //第六步：将文件存储到指定位置
        try
        {
            FileOutputStream fout = new FileOutputStream("D:/students.xls");
            wb.write(fout);
            fout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
