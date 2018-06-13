package io.fp.vokabeltrainer.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


import io.fp.vokabeltrainer.model.VokabeltrainerStore;

public class VokabeltrainerDAO {
	
	private String dateiname;
	
	public VokabeltrainerDAO(String string) {
		dateiname=string;
	}
	
	public VokabeltrainerStore createStore() throws IOException{
	
		
		
		try {
			readStore();
			throw new IOException("File not empty!");
		}
		catch(IOException e){
			
			VokabeltrainerStore store = new VokabeltrainerStore();
			File file = new File(dateiname);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for(Map.Entry<String,String> x: store.getMap().entrySet()) {
				String s = x.getKey()+" " + x.getValue();
			
				bufferedWriter.write(s);
				bufferedWriter.newLine();
			}
				bufferedWriter.close();
		
				return store;
		}
	}
	
	public VokabeltrainerStore readStore() throws IOException{
		
		VokabeltrainerStore store = new VokabeltrainerStore();
		
		File file = new File(dateiname);
		FileReader reader = new FileReader(file);
			 
		BufferedReader bReader = new BufferedReader(reader);
		String line= bReader.readLine();
		String[] array;
		while(line != null) {
			array = line.split(" ");
			store.putMap(array[0], array[1]);
			line= bReader.readLine();
			
		}
		
		bReader.close();
		
		if(store.size()==0) {
			throw new IOException("File is empty");
		}
		
		return store;
	}
	
	public void updateStore(VokabeltrainerStore store) throws IOException{
		
		File file = new File(dateiname);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for(Map.Entry<String,String> x: store.getMap().entrySet()) {
			String s = x.getKey()+" " + x.getValue();
			
			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}
				
		bufferedWriter.close();
	}
}
