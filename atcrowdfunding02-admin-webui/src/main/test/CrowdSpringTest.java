import com.angel.crowd.mapper.AdminMapper;
import com.angel.crowd.mapper.AuthMapper;
import com.angel.crowd.mapper.RoleMapper;
import com.angel.crowd.service.AdminService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdSpringTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper mapper;

    @Autowired
    private AdminService service;

    @Autowired
    private  RoleMapper roleMapper;

    @Autowired
    private AuthMapper authMapper;



    @Test
    public void show() throws Exception {
        Workbook wb=new HSSFWorkbook(); // 定义一个新的工作簿

        Sheet sheet =wb.createSheet("第一个Sheet页");

        Row row=sheet.createRow(0); // 创建一个行

        Cell cell=row.createCell(0); // 创建一个单元格 第一列

        cell.setCellValue(1); // 给一个单元格设置值

        row.createCell(1).setCellValue("1,2"); // 创建一个单元格，第2列的值是1,2

       for (int i=0;i<100;i++){
           row.createCell(i).setCellValue("我是"+i+"giao我里giaogiao");
       }
        for (int i = 0; i <100 ; i++) {
            Row rows=sheet.createRow(0);
        }

        FileOutputStream filOut=new FileOutputStream("D:\\用Poi生成的Excel.xls");
        ((HSSFWorkbook) wb).write(filOut);
        filOut.close();
    }


    /**
     * 1.创建一个时间格式的单元格
     * 2.处理不同内容格式的单元格
     * @throws Exception
     */
    @Test
    public void ExcleDemo2() throws Exception {
         Workbook wb =new HSSFWorkbook(); // 定义一个新的工作簿
        Sheet sheet=wb.createSheet("第一个Sheet页"); // 定义一个Sheet页
        Row row=sheet.createRow(0); // 创建一个行
        Cell cell=row.createCell(0); // 创建一个单元格
        cell.setCellValue(new Date()); // 给单元格赋值

        // 格式化日期
        CreationHelper creationHelper= wb.getCreationHelper();
        CellStyle cellStyle=wb.createCellStyle(); // 单元格样式类
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd :HH:mm:ss"));
        cell=row.createCell(1); // 第二列
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);


        // 第三列
        cell=row.createCell(2); // 第三列
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);
        FileOutputStream file=new FileOutputStream("D:\\ pio生成的excle1.xls");
        ((HSSFWorkbook) wb).write(file);
        file.close();
    }

    @Test
    /**
     *
     * 3.遍历工作簿的航和列并获取单元格的内容
     *
     */
    public void ExcleDemo3() throws Exception {
        InputStream is=new FileInputStream("D:\\ pio生成的excle1.xls");
        POIFSFileSystem fs=new POIFSFileSystem(is);
        HSSFWorkbook wb=new HSSFWorkbook(fs);
        HSSFSheet hssfSheet=wb.getSheetAt(0); // 获取第一个Sheet页
        if(hssfSheet==null){
            return;
        }
        // 遍历行row
        for (int rowNum=0;rowNum<=hssfSheet.getLastRowNum();rowNum++){
            HSSFRow hssfRow=hssfSheet.getRow(rowNum);
            if(hssfRow==null){
                continue;
            }
            // 遍历列
            for (int cellNum=0;cellNum<hssfRow.getLastCellNum();cellNum++){
                HSSFCell hssfCell=hssfRow.getCell(cellNum);
                if(hssfCell==null){
                    continue;
                }
                System.out.print(hssfCell);
            }
            System.out.print(hssfRow);
        }
    }


    /**
     * 4.文本提取
     * @throws Exception
     */
    @Test
    public void ExcleDemo4() throws Exception {
        InputStream  is=new FileInputStream("D:\\ pio生成的excle1.xls");
        POIFSFileSystem fs=new POIFSFileSystem(is);
        HSSFWorkbook wb=new HSSFWorkbook(fs);

        org.apache.poi.hssf.extractor.ExcelExtractor  excelExtractor= new org.apache.poi.hssf.extractor.ExcelExtractor(wb);
        excelExtractor.setIncludeSheetNames(false); // 不需要Sheet页名称


    }

//    /**
//     * 单元格格式化
//     * @throws Exception
//     */
//    @Test
//    public void ExcleDemo5() throws Exception {
//        Workbook wb=new HSSFWorkbook(); // 定义一个新的工作簿
//        Sheet sheet=wb.createSheet("格式化单元格"); // 创建一个Sheet页
//        Row row=sheet.createRow(0); // 创建一个行
//        Cell cell=row.createCell(0); // 创建一个单元格
//        cell.setCellValue("我楼上住那小伙可嘎达的帅了");
//        createCell(wb,row,(s));
//    }
//    private static  void createCell(Workbook wb,Row row,short column,short halign,short valign){
//        Cell cell=row.createCell(column); // 设置值
//        cell.setCellValue(new HSSFRichTextString("Align It")); // 设置值
//        CellStyle cellStyle=wb.createCellStyle(); // 创建单元格样式
//        cellStyle.setAlignment(HorizontalAlignment.forInt(halign)); // 创建单元格样式
//        cellStyle.setVerticalAlignment(VerticalAlignment.forInt(valign)); // 设置单元格水平方向对齐方式
//        cell.setCellStyle(cellStyle); // 设置单元格样式
//    }

@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void ExcleDemo1(){

   String pswd= passwordEncoder.encode("123123");
        System.out.println(pswd);
    }
}
