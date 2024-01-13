package milestone2_code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class sum_of_methods {

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
	
	public static String replaceWords(String sentence, HashMap<String, String> replacementDict) {
	    // Split sentence into words
	    String[] words = sentence.split("\\s+");
	    StringBuilder newSentence = new StringBuilder();
	    // Loop through each word in the sentence
	    for (String word : words) {
	        // Check if the word needs to be replaced
	        if (replacementDict.containsKey(word)) {
	            // Replace the word with its replacement
	            newSentence.append(replacementDict.get(word)).append(" ");
	        } else {
	            // Keep the original word
	            newSentence.append(word).append(" ");
	        }
	    }
	    // Remove the last space and return the modified sentence
	    return newSentence.toString().trim();
	}
	
	public static void main(String[] args) throws Exception{ 
		keywordTrieDict = getDatabase();
        String csvFilePath = "D:\\UBC\\COSC320\\project\\zombiesinthelab.widgets.droidpetwidget.csv";
        int columnIndex = 3; // index of the column you want to read
        CSVReader reader = new CSVReader(new FileReader(csvFilePath));
        CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
        RandomAccessFile raf = new RandomAccessFile(csvFilePath, "rw");
        String[] headerRow = reader.readNext(); // optional if you don't have a header row
        String[] nextLine = null;
        
        String line;
        long position = 0;
        while ((line = raf.readLine()) != null) {
        	String oldString = nextLine[columnIndex];
            line = line.replace(oldString, replaceWords(oldString, keywordTrieDict));
            byte[] bytes = line.getBytes();
            raf.seek(position);
            raf.write(bytes);
            position += bytes.length;
            raf.writeBytes("\n");
            position += 1;
                  
        }
        raf.close();
        reader.close();

}
}
