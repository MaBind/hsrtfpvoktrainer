package io.fp.vokabeltrainer.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.fp.vokabeltrainer.dao.VokabeltrainerDAO;
import io.fp.vokabeltrainer.model.VokabeltrainerStore;

class VokabeltrainerTest {

	VokabeltrainerStore vt = new VokabeltrainerStore();
	@Test
	public void testVokabelnSpeichern() {
		assertEquals(true,vt.vokabelnSpeichern("Haus", "house"));
		assertEquals(false, vt.vokabelnSpeichern("Haus", "house"));
		assertEquals(true,vt.vokabelnSpeichern("Sonne", "sun"));
		assertEquals(2,vt.size());
	}
	@Test
	public void testZufallsVokabelEnglisch() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(true, vt.getMap().containsValue(vt.zufallsVokabelEnglisch()));
	}
	@Test
	public void testZufallsvokabelDeutsch() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(true, vt.getMap().containsKey(vt.zufallsVokabelDeutsch()));
	}
	@Test
	public void testÜberprüfeVonDeutschNachEnglisch() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(true, vt.überprüfeVokabelVonDeutschNachEnglisch("Sonne", "sun"));
		assertEquals(false, vt.überprüfeVokabelVonDeutschNachEnglisch("Haus", "House"));
	}
	@Test
	public void testÜberprüfeVonEnglischnachDeutsch() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(true, vt.überprüfeVokabelVonEnglischNachDeutsch("sun", "Sonne"));
		assertEquals(false, vt.überprüfeVokabelVonEnglischNachDeutsch("house", "haus"));
	}
	@Test
	public void testZufallszahl() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(true, vt.zufallsZahl()>=0 && vt.zufallsZahl()< vt.size());
	}
	@Test
	public void testSize() {
		assertEquals(0,vt.size());
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(2, vt.size());
	}
	
	@Test 
	public void testGibEnglischesWort() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals("sun", vt.gibEnglischesWort("Sonne"));
		assertEquals(null,vt.gibEnglischesWort("berlin"));
	}
	
	@Test 
	public void testGibDeutschesWort() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals("Haus", vt.gibDeutschesWort("house"));
		assertEquals(null,vt.gibDeutschesWort("berlin"));
	}
	
	/*@Test 
	public void testGibDeutscheSuche() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(1,vt.gibDeutscheSuche("Haus").length);
		
		String[] string = new String[] {"sun"};
		assertEquals(string[0],vt.gibDeutscheSuche("Sonne")[0]);
	}*/
	
	@Test 
	public void testGibEnglischeSuche() {
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		assertEquals(1,vt.gibEnglischeSuche("Sonne").length);
		
		String[] string = new String[] {"Haus"};
		assertEquals(string[0],vt.gibEnglischeSuche("house")[0]);
	}
	@Test
	public void testCreateStore() {
		VokabeltrainerDAO vokt = new VokabeltrainerDAO("Z:\\bindewal\\test.txt");
		try {
				vokt.createStore();
				
		} catch(IOException e) {
			fail("Sollte geworfen werden");
		
		}
	}
	@Test
	public void testReadStore() {
		VokabeltrainerDAO vokt = new VokabeltrainerDAO("Z:\\bindewal\\test2.txt");
		VokabeltrainerStore store = new VokabeltrainerStore();
		try {
			store = vokt.readStore();
		}catch(IOException e) {
			assertEquals(0,store.getMap().size());
		}
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		
		try{
			vokt.updateStore(vt);
		}
		catch(IOException e) {
			
		}
		try {
		store = vokt.readStore();
		}catch(IOException e) {}
		assertEquals(2,store.getMap().size());
		assertEquals("house", store.gibEnglischesWort("Haus"));
		
	}
	
	@Test
	public void testUpdateStore() {
		VokabeltrainerDAO vokt = new VokabeltrainerDAO("Z:\\bindewal\\test3.txt");
		vt.vokabelnSpeichern("Sonne", "sun");
		vt.vokabelnSpeichern("Haus", "house");
		vt.vokabelnSpeichern("eins", "one");
		
		try {
			vokt.updateStore(vt);
		}catch(IOException e) {
			fail("Sollte nicht geworfen werden");
		}
		VokabeltrainerStore store = new VokabeltrainerStore();
		try {
			store = vokt.readStore();
		} catch(IOException e) {}
	
		assertEquals("one",store.gibEnglischesWort("eins"));
		
		
	}
}
