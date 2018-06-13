package io.fp.vokabeltrainer.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import io.fp.vokabeltrainer.dao.VokabeltrainerDAO;
import io.fp.vokabeltrainer.model.VokabeltrainerStore;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextArea;

public class GUI extends Application{

	
	private static final String DnE = "von Deutsch nach Englisch";
	private static final String EnD = "von Englisch nach Deutsch";
	private static final String ERFOLGREICH = "Die �bersetzung war richtig! N�chstes Wort wurde zuf�llig ausgew�hlt.";
	private static final String FALSCH = "Die �bersetzung war falsch. Versuche es nochmal!";
	
	//ui elemente
	private Parent root;
	private Label �bersetzeWort;
	private Label zu�bersetzendesWort;
	private Label artDer�bersetzung;
	private Label ergebnis�bersetzung;
	private TextField inputVokabel;
	private Label einf�hrungsText;
	private TextField textfeldRechts;
	private TextField textfeldLinks;
	private Button hinzuf�gen;
	private Button andere�bersetzungsrichtung;
	private Button tip;
	private Button deutschEnglisch;
	private TextField wortSuche;
	private TextArea area;
	
	
	
	// dao
	private VokabeltrainerDAO dao;
	
	// model
	private VokabeltrainerStore store;
	
	//sontiges
	private boolean �bersetzungsart = true;//true bei default-Richtung Deutsch nach Englisch
	private String vokabel;
	private boolean schonGespeichert = false;//wurde nichts dem w�rterbuch hinzugef�gt
	private String tipHilfe = null;
	private int tipZaehler = 0;
	private boolean wortSucheW�rterbuch = true;//true um ein deutsches wort einzugeben
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void init() throws Exception {

		//init dao
		Parameters params = getParameters();
		List<String> paramList = params.getRaw();
		if (paramList.size()<1) {
			throw new IOException("No parameter defined for file name!");
		}
		dao = new VokabeltrainerDAO(paramList.get(0));
		
		//init model
		try {
			store = dao.readStore();
		} catch (IOException e) {
			store = dao.createStore();
		}
	
		//init labels
		einf�hrungsText= new Label("Ein neues Wort in das W�rterbuch eintragen: ");
		�bersetzeWort = new Label("�bersetze das Wort");
		vokabel = store.zufallsVokabelDeutsch();
		zu�bersetzendesWort = new Label(vokabel);
		zu�bersetzendesWort.setFont(Font.font("Arial",FontWeight.BOLD,18));
		artDer�bersetzung = new Label(DnE);
		ergebnis�bersetzung = new Label();
		
		//init textfelder
		textfeldLinks = new TextField();
		textfeldLinks.setPromptText("Deutsch");
		textfeldRechts = new TextField();
		textfeldRechts.setPromptText("Englisch");
		inputVokabel = new TextField();
		inputVokabel.setPromptText("�bersetzung");
		inputVokabel.setPrefColumnCount(13);
		inputVokabel.setOnKeyPressed(event-> {
			 
		  
		   
		        if(event.getCode().equals(KeyCode.ENTER)) {
		             
		        	String eingabe = inputVokabel.getText().trim();
					if(eingabe.length()>0) {
						//�bersetzungsart ist true wenn Deutsch nach Englisch
						// und false bei Englisch nach Deutsch
						if(�bersetzungsart) {
								//vokabel wurde richtig eingegeben, dann richtig = true
								boolean richtig = store.�berpr�feVokabelVonDeutschNachEnglisch(vokabel, eingabe);
								if(richtig) {
									ergebnis�bersetzung.setText(ERFOLGREICH);
									inputVokabel.clear();
							
									vokabel = store.zufallsVokabelDeutsch();
									zu�bersetzendesWort.setText(vokabel);
									tipZaehler=0;
								}
								else {
									
									
									ergebnis�bersetzung.setText(FALSCH);
								}
						}
						else {
							boolean richtig = store.�berpr�feVokabelVonEnglischNachDeutsch(vokabel, eingabe);
							if(richtig) {
								ergebnis�bersetzung.setText(ERFOLGREICH);
								inputVokabel.clear();
								tipZaehler =0;
								vokabel = store.zufallsVokabelEnglisch();
								zu�bersetzendesWort.setText(vokabel);
							}
							else {
								ergebnis�bersetzung.setText(FALSCH);
							}
						}
					}
					inputVokabel.requestFocus();
				}
		        
		        
	
		});
		wortSuche = new TextField();
		wortSuche.setPromptText("Suche...");
		
			
		wortSuche.setOnKeyTyped(event-> {
			
			if(wortSucheW�rterbuch) {
				
				String[] s=store.gibDeutscheSuche(wortSuche.getText().trim());
				
				if(!s[0].equals("")) {
					for(int i =0; i<s.length;i++) {
						area.setText(s[i]);
					}
					
				}
					else {
						
						if(wortSuche.getText().trim().equals("")) {
							area.setText("");
						}
						else {
							area.setText("Nichts gefunden...");
						}
					}
					
			}
			else {
				
				String[] s = store.gibEnglischeSuche(wortSuche.getText().trim());
				if(!s[0].equals("")) {
					for(int i=0;i<s.length;i++) {
						area.setText(s[i]);
					}
				}
				else {
					if(wortSuche.getText().trim().equals("")) {
						area.setText("");
					}
					else {
						area.setText("Nichts gefunden...");
					}
				}
			}
			
		
		});
		area = new TextArea();
		area.setPrefSize(160, 75);
		
		
		//init buttons
		hinzuf�gen = new Button("Hinzuf�gen");
		hinzuf�gen.setOnAction(event ->{
			String s1;
			String s2;
			
			if(�bersetzungsart) {
				s1 = textfeldLinks.getText().trim();
				s2 = textfeldRechts.getText().trim();
				
			}else {
				s1=textfeldRechts.getText().trim();
				s2= textfeldLinks.getText().trim();
			}
			
			boolean geklappt =false;
			if(s1.length()>0&&s2.length()>0) {
				// wenn vokabeln abgespeichert wurden kommt true
				//wenn vokabeln schon existieren kommt false
				geklappt = store.vokabelnSpeichern(s1, s2);
			
				if(geklappt) {
					showAlert2("Wort wurde erfolgreich hinzugef�gt!");
					schonGespeichert = true;
				}
				else {
					
					showAlert1("Eintrag f�r "+ s2+" bereits im W�rterbuch vorhanden!");
				}
			}
			textfeldLinks.clear();
			textfeldRechts.clear();
		});
		
		
		andere�bersetzungsrichtung = new Button("�bersetzungsrichtung �ndern");
		andere�bersetzungsrichtung.setOnAction(event ->{
			
			if(�bersetzungsart==true) {
				artDer�bersetzung.setText(EnD);
				vokabel = store.zufallsVokabelEnglisch();
				zu�bersetzendesWort.setText(vokabel);
				textfeldLinks.setPromptText("Englisch");
				textfeldRechts.setPromptText("Deutsch");
				�bersetzungsart=false;
			}
			else {
				artDer�bersetzung.setText(DnE);
				vokabel = store.zufallsVokabelDeutsch();
				textfeldLinks.setPromptText("Deutsch");
				textfeldRechts.setPromptText("Englisch");
				zu�bersetzendesWort.setText(vokabel);
				�bersetzungsart = true;
			}
			inputVokabel.requestFocus();
		});
		
		tip = new Button("Tipp");
		tip.setOnAction(event->{
			
			if(�bersetzungsart) {
			
				if(tipHilfe==null) {
					tipHilfe=vokabel;
					String[] s = store.gibEnglischesWort(tipHilfe).split("");
					inputVokabel.setText(s[tipZaehler]);
					tipZaehler++;
				}
				
			
				else if(tipHilfe.equals(vokabel)) {
					String[] s = store.gibEnglischesWort(tipHilfe).split("");
					String sum = "";
					if(tipZaehler<s.length) {
					for(int i = 0; i<tipZaehler+1; i++) {
					sum+= s[i];
					}
					inputVokabel.setText(sum);
					tipZaehler++;
				}}
				else {
					tipZaehler = 0;
					tipHilfe = vokabel;
					String[] s = store.gibEnglischesWort(tipHilfe).split("");
					inputVokabel.setText(s[tipZaehler]);
					tipZaehler++;
				}
			}
			else {
				if(tipHilfe==null) {
					tipHilfe=vokabel;
					String[] s = store.gibDeutschesWort(tipHilfe).split("");
					inputVokabel.setText(s[tipZaehler]);
					tipZaehler++;
				}
				
			
				else if(tipHilfe.equals(vokabel)) {
					String[] s = store.gibDeutschesWort(tipHilfe).split("");
					String sum = "";
				
					if(tipZaehler<s.length) {
					for(int i = 0; i<tipZaehler+1; i++) {
					sum+= s[i];
					}
					inputVokabel.setText(sum);
					tipZaehler++;
				}}
				else {
					tipZaehler = 0;
					tipHilfe = vokabel;
					String[] s = store.gibDeutschesWort(tipHilfe).split("");
					inputVokabel.setText(s[tipZaehler]);
					tipZaehler++;
				}
				
			}
		});
		deutschEnglisch = new Button("Deutsch");
		deutschEnglisch.setPrefSize(60,30);
		deutschEnglisch.setOnAction(event->{
			if(wortSucheW�rterbuch) {
				wortSucheW�rterbuch = false;
				deutschEnglisch.setText("Englisch");
			}
			else {
				wortSucheW�rterbuch = true;
				deutschEnglisch.setText("Deutsch");
				
			}
		});
		
		
		inputVokabel.requestFocus();
		root= greatSceneGraph();
	}

	public Parent greatSceneGraph() {
		
		// tab1 f�rs trainieren, tab2 f�rs w�rterbuch
		
		TabPane tpane = new TabPane();
		Tab tab1 = new Tab();
		Tab tab2 = new Tab();
		tab1.setText("Trainieren");
		tab2.setText("W�rterbuch");
		tpane.getTabs().addAll(tab1,tab2);
		
		
		
		//tab f�rs trainieren
		
		VBox box1 = new VBox();
		FlowPane flow1 = new FlowPane();
		
		flow1.setHgap(7);
		flow1.getChildren().addAll(tip, inputVokabel);
		flow1.setAlignment(Pos.CENTER);
		box1.getChildren().addAll(�bersetzeWort, zu�bersetzendesWort, artDer�bersetzung, 
				flow1, andere�bersetzungsrichtung, ergebnis�bersetzung);
		box1.setAlignment(Pos.CENTER);
		box1.setSpacing(15);
		tab1.setContent(box1);
		
		//tab f�rs w�rterbuch
	
		VBox box2 = new VBox();
		FlowPane flow2 = new FlowPane();
		FlowPane flow4 = new FlowPane();
		VBox box3 = new VBox();
		box2.setPadding(new Insets(15, 15, 15, 15));
		flow2.setHgap(7);
		flow2.getChildren().addAll(textfeldLinks,textfeldRechts,hinzuf�gen);
		box3.getChildren().addAll(flow2);
		flow4.getChildren().addAll(deutschEnglisch, wortSuche, area);
		flow4.setHgap(7);
		box2.getChildren().addAll(flow4, einf�hrungsText, box3 );
		box2.setSpacing(25);
		tab2.setContent(box2);
		
		
		return tpane;
		
	}
	
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Vokabeltrainer");
		Scene scene = new Scene(root, 425, 250);
		stage.setScene(scene);
		stage.show();
		inputVokabel.requestFocus();
		
		stage.setOnCloseRequest(event-> {
		
			if(schonGespeichert) {
				showAlertBeenden();
			}
		});
	}
	
	//infofenster wenn hinzuf�gen fehlschl�gt
	private void showAlert1(String message) {
		Alert alert = new Alert(AlertType.ERROR, message);
		alert.setTitle("Wort hinzuf�gen");
		alert.showAndWait()
	      .filter(response -> response == ButtonType.OK)
	      .ifPresent(response -> alert.close());
		
	}
	
	//infofenster wenn hinzuf�gen funktioniert
	private void showAlert2(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message);
		alert.setTitle("Wort hinzuf�gen");
		alert.setHeaderText("Erfolg");;
		alert.showAndWait()
	      .filter(response -> response == ButtonType.OK)
	      .ifPresent(response -> alert.close());
		
	}
	
	private void showAlertBeenden() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Vokabeltrainer");
		alert.setHeaderText("M�chten Sie die �nderungen im W�rterbuch speichern?");
		

		ButtonType buttonTypeOne = new ButtonType("Ja");
		ButtonType buttonTypeTwo = new ButtonType("Nein");
		
		ButtonType buttonTypeCancel = new ButtonType("Abbrechen"/*, ButtonData.CANCEL_CLOSE*/);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo/*, buttonTypeCancel*/);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		    try {
			dao.updateStore(store);
		    } catch(IOException e) {
		    	e.printStackTrace();
		    }
		} else if (result.get() == buttonTypeTwo) {
		    alert.close();
		}  

  		/*else {
		    
  			alert.close();
 
		  
		}*/
	}
	
	

}
