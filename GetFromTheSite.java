import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;



public class GetFromTheSite {

	// Assigns the file path
	private static final String sys = System.getProperty("user.home");
	private static final String FILE_PATH = sys + "\\Desktop\\PriceComparison.xlsx";
	private static final GetFromTheSite INSTANCE = new GetFromTheSite();
	
	public static GetFromTheSite getInstance(){
		return INSTANCE;
	}
	
	private GetFromTheSite(){
	}
	
	public static void main(String[] args) {
		
		// Assigns the urls of the products to string variables
		String url1 = "http://www.sanalmarket.com.tr/kweb/prview/6458101-30211-arbella-burgu-makarna-500-gr";
		String url2 = "http://www.sanalmarket.com.tr/kweb/prview/56853124-30173-i%CC%87ci%CC%87m-pastori%CC%87ze-si%CC%87se-sut-1-l";
		String url3="http://www.sanalmarket.com.tr/kweb/prview/1445070-33282-pinar-dogal-yogurt-1500-gr";
		String url4="http://www.sanalmarket.com.tr/kweb/prview/1452394-30181-keski%CC%87noglu-l-buyuk-boy-yumurta-30-lu-63-72-gr";
		String url5="http://www.sanalmarket.com.tr/kweb/prview/55826460-30391-kirlangic-sizma-z-yagi-500-ml";
		String url6="http://www.sanalmarket.com.tr/kweb/prview/1422213-30180-bi%CC%87zi%CC%87m-paket-margari%CC%87n-250-gr";
		String url7="http://www.sanalmarket.com.tr/kweb/prview/17625245-30175-sutas-suzme-peyni%CC%87r-500-gr";
		String url8="http://www.sanalmarket.com.tr/kweb/prview/1427439-30489-nestle-chokella-kase-500-gr";
		String url9="http://www.sanalmarket.com.tr/kweb/prview/1428090-32005-coca-cola-1-l";
		String url10="http://www.sanalmarket.com.tr/kweb/prview/56612342-30119-aytac-sipsak-macar-salam-dilimli-60-g";
		
		// Creates a list with the products
		List<Product> pricesList = new ArrayList<Product>();
		pricesList.add(new Product(url1,1));
		pricesList.add(new Product(url2,2));
		pricesList.add(new Product(url3,3));
		pricesList.add(new Product(url4,4));
		pricesList.add(new Product(url5,5));
		pricesList.add(new Product(url6,6));
		pricesList.add(new Product(url7,7));
		pricesList.add(new Product(url8,8));
		pricesList.add(new Product(url9,9));
		pricesList.add(new Product(url10,10));
		
		// Calls the function to write the prices into an excel file
		writeToExcel(pricesList);
		
		
	}
	
	public static void writeToExcel(List<Product> prices){
		
		// Gets the date and set a format for it
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		String currentDate = sdf.format(date);
		
		try {
			File f = new File(FILE_PATH);
			//Checks whether the excel file exist in the path or not
			if(!f.exists()){
				//Creates a workbook and a sheet
				Workbook workbook = new XSSFWorkbook();
				Sheet pricesSheet = workbook.createSheet("Prices");
				int rowIndex = 1;
				// Writes the headlines for columns
				Row r = pricesSheet.createRow(0);
				r.createCell(0).setCellValue("Ürün Adý");
				r.createCell(6).setCellValue(currentDate);
				
				//Writes the product's name and its price into excel file
				for(Product prodct : prices){
					Row row = pricesSheet.createRow(rowIndex++);
					row.createCell(0).setCellValue(prodct.findName());
					row.createCell(6).setCellValue(prodct.findPrice() + " TL");
				}
				
				//Keeps the index
				r.createCell(200).setCellValue("7");
				
				//Closes the file
				FileOutputStream fos = new FileOutputStream(FILE_PATH);
				workbook.write(fos);
				fos.close();
				
				//If the file already exists, it enters this block
			} else {
				//Opens the file to modify it
				FileInputStream ff = new FileInputStream(new File(FILE_PATH));
				Workbook workbook = new XSSFWorkbook(ff);
				Sheet pricesSheet = workbook.getSheetAt(0);
				//Gets the index
				Cell cell = null;
				cell = pricesSheet.getRow(0).getCell(200);
				XSSFRichTextString s = (XSSFRichTextString)cell.getRichStringCellValue();
				String str = s.getString();
				int col = Integer.parseInt(str);
				int rowIndex = 1;
				Row r = pricesSheet.getRow(0);
				//Writes the date
				r.createCell(col).setCellValue(currentDate);
				//Writes the prices
				for(Product product : prices){
					Row row = pricesSheet.getRow(rowIndex);
					row.createCell(col).setCellValue(product.findPrice() + " TL");
					rowIndex++;
				}
				//Holds the index
				str = "" + (col+1);
				cell.setCellValue(str);
				ff.close();
				FileOutputStream out = new FileOutputStream(new File(FILE_PATH));
				workbook.write(out);
				out.close();
			}
			//If it writes the data successfully, it shows a message
			JOptionPane.showMessageDialog(null, "Completed", "Success", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
