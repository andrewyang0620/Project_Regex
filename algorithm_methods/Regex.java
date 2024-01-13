
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Regex {


	private static HashMap keywordTrieDict;
	
	public static HashMap getDatabase() throws Exception{
		File myFile = new File("D:\\UBC\\COSC320\\project\\Abbreviation_Dictionary.xlsx");
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		HashMap<String, String> database = new HashMap<String, String>();
		Iterator<Row> rowIterator = mySheet.iterator();
		while (rowIterator.hasNext()) {
			 Row row = rowIterator.next();
			 Iterator<Cell> cellIterator = row.cellIterator();
			 
			 while (cellIterator.hasNext()) {
				 Cell cell = cellIterator.next();
				 String s1 = cell.getStringCellValue();
				 Cell cell2 = cellIterator.next();
				 String s2 = cell2.getStringCellValue();
				// System.out.println(s1);
				//System.out.println(s2);
				 database.put(s1 , s2);
			 }
		}
		return database;
	}
	
    public static String replaceWords(String text, HashMap<String, String> abbreviationMap) {
    	// Match any word that is all capital letters with at least 2 letters
        Pattern pattern = Pattern.compile("\\b([A-Z]{2,})\\b");
        Matcher matcher = pattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String abbreviation = matcher.group(1);
            String expansion = abbreviationMap.get(abbreviation);
            if (expansion != null) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(expansion));
            }
        }
        matcher.appendTail(sb);

        // Match any word that is all capital letters and is followed by a number
        pattern = Pattern.compile("\\b([A-Z]+)\\d+\\b");
        matcher = pattern.matcher(sb.toString());
        sb = new StringBuffer();
        while (matcher.find()) {
            String abbreviation = matcher.group(1);
            String expansion = abbreviationMap.get(abbreviation);
            if (expansion != null) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(expansion));
            }
        }
        matcher.appendTail(sb);

        // Match any word that is all capital letters and is followed by a special character
        pattern = Pattern.compile("\\b([A-Z]+)\\W+\\b");
        matcher = pattern.matcher(sb.toString());
        sb = new StringBuffer();
        while (matcher.find()) {
            String abbreviation = matcher.group(1);
            String expansion = abbreviationMap.get(abbreviation);
            if (expansion != null) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(expansion));
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
    
	
	public static void main(String[] args) throws Exception{ 
		keywordTrieDict = getDatabase();
        String csvFilePath = "D:\\UBC\\COSC320\\project\\1\\2-zombiesinthelab.widgets.droidpetwidget.csv";

        CSVReader reader = new CSVReader(new FileReader(csvFilePath));
        CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
        String[] header = reader.readNext();
        writer.writeNext(header);

        // Process the remaining rows of the input file
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // Modify the 4th column of the current row
            String oldValue = nextLine[3]; // 4th column
            String newValue = oldValue.replace(oldValue, (CharSequence) keywordTrieDict);
            nextLine[3] = newValue; // set the new value to the 4th column

            // Write the modified row to the output file
            writer.writeNext(nextLine);
        }

        System.out.println("Finished replacing");
        // Close the input and output files
        reader.close();
        writer.close();
}
}
