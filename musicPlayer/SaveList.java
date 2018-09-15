package musicPlayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveList {
	static BufferedReader reader;
	static BufferedWriter writer;
	static File listFile = new File("F:\\Projects\\workingv2\\src\\musicPlayer\\musicPlayer.txt"); 
	
	public synchronized static void write(String str) {
		
		if(!listFile.exists()) {
			try {
				listFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			writer = new BufferedWriter(new FileWriter(listFile.getAbsoluteFile(), true));  //dosyadaki bilgilerin silinmeden üstüne yazılmasını sağlar
			writer.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized static int read() {  //dosyada kaç şarkı olduğunu bulur
		int numberOfLines = 0;
		try {
			reader = new BufferedReader(new FileReader(listFile));
			String line = "";
			
			try {
				while((line = reader.readLine()) != null) {
					if(line.contains("!")) {
						numberOfLines++;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return numberOfLines;
	}
	

	public synchronized static String[] read1() {  //Dosya isimlerini array olarak döndürür okumak için array e atamak gerekir
		String nameOfSong[] = new String[100];
		int i = 0;
		try {
			reader = new BufferedReader(new FileReader(listFile));
			String line = "";
			try {
				while((line = reader.readLine()) != null) {
					nameOfSong[i] = line.substring(line.indexOf("%") + 1, line.indexOf("&"));
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return nameOfSong;
	}
}
