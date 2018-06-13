package io.fp.vokabeltrainer.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.fp.vokabeltrainer.dao.VokabeltrainerDAO;

public class Main {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		VokabeltrainerStore store = new VokabeltrainerStore();
		
		store.vokabelnSpeichern("Hund", "dog");
		store.vokabelnSpeichern("Blau", "blue");
		store.vokabelnSpeichern("Haus", "house");
		store.vokabelnSpeichern("Baum", "tree");
		System.out.println(store);
		System.out.println(store.zufallsVokabelDeutsch());
		System.out.println(store.zufallsVokabelEnglisch());
		boolean x = store.überprüfeVokabelVonDeutschNachEnglisch("Haus", "house");
		System.out.println(x);
		boolean y = store.überprüfeVokabelVonEnglischNachDeutsch("tree", "Baum");
		System.out.println(y);
		String s = "123"; 
		String[] array = s.split("");
		System.out.println(array[0]);
		System.out.println(array[1]);
		
		/*VokabeltrainerDAO dao = new VokabeltrainerDAO("Z:\\bindewal\\test.txt");
		VokabeltrainerStore vs = new VokabeltrainerStore();
		vs.vokabelnSpeichern("Zahl", "number");
		dao.updateStore(vs);*/
		
		List<String> list = new ArrayList<>();
		
	}

}
