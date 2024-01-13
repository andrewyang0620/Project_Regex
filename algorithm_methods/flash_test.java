
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

public class flash_test {

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
        String csvFilePath = "D:\\UBC\\COSC320\\project\\1\\1-zombiesinthelab.widgets.droidpetwidget.csv";

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