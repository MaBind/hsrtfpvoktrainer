package io.fp.vokabeltrainer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VokabeltrainerStore implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	Map<String, String> vokabeln = new HashMap<>();
//	List<String> deutschEnglisch = new ArrayList<>();
//	List<String> englischDeutsch = new ArrayList<>();
	
	public boolean vokabelnSpeichern(String deutsch, String englisch) {
		
		boolean gibtsSchon = !(vokabeln.containsKey(deutsch)||vokabeln.containsValue(englisch));
		
		if(gibtsSchon) {
			
			vokabeln.put(deutsch, englisch);
		
		}
		return gibtsSchon;
	}
	
	public String zufallsVokabelEnglisch() {
		List<String> liste = new ArrayList<>();
		
		for(String string: vokabeln.values()) {
			liste.add(string);
		}
		return liste.get(zufallsZahl());
	}
	
	public String zufallsVokabelDeutsch() {
		vokabeln.put("Haus", "house");
		
		List<String> liste = new ArrayList<>();
		
		for(String string: vokabeln.keySet()) {
			liste.add(string);
		}
		
		return liste.get(zufallsZahl());
	}
	
	public boolean überprüfeVokabelVonDeutschNachEnglisch(String vokabelDeutsch, String eingabe) {
		
		return vokabeln.get(vokabelDeutsch).equals(eingabe);
		
	}
	
	public boolean überprüfeVokabelVonEnglischNachDeutsch(String vokabelEnglisch, String eingabe) {
		
		if(vokabeln.containsKey(eingabe)) {
			return vokabeln.get(eingabe).equals(vokabelEnglisch);
		}
		return false;
	}
	public String gibEnglischesWort(String deutsch) {
		
		return vokabeln.get(deutsch);
		
		
	}
	
	public String gibDeutschesWort(String englisch) {
		for(String s:vokabeln.keySet()) {
			if(vokabeln.get(s).equals(englisch)) {
			return s;
			}
		}
		return null;
	}
	
	public String[] gibDeutscheSuche(String deutsch) {
		String[] result = new String[] {""};
		int i=0;
		for(String string: vokabeln.keySet()) {
			if(string.equals(deutsch)) {
				result[i] = vokabeln.get(string);
				i++;
			}
		}
		
		return result;
	}
	
	
	
	
	public String[] gibEnglischeSuche(String englisch) {
		String[] result = new String[] {""};
		int i = 0;
		for(String string: vokabeln.keySet()) {
			if(vokabeln.get(string).equals(englisch)) {
				result[i] = string;
				i++;
			}
		}
		return result;
		
	}
	
	
	public int zufallsZahl() {
		Random random = new Random();
		return random.nextInt(vokabeln.size());
	}
	
	public int size() {
		return vokabeln.size();
	}
	
	public String toString() {
		return vokabeln.toString();
	}
	
	public Map<String,String> getMap(){
		return vokabeln;
	}
	
	public void putMap(String s1, String s2) {
		vokabeln.put(s1, s2);
	}

}
