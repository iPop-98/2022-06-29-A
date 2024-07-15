/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.LinkedList;
import java.util.ResourceBundle;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import it.polito.tdp.itunes.model.AlbumConBilancio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Album a = this.cmbA1.getValue();
    	List<AlbumConBilancio> adiacenze = new LinkedList<>();
    	if(a!= null) {
    		adiacenze = this.model.getAdiacenze(a);
    		if(adiacenze.size()>0) {
    			this.txtResult.setText("L'album " + a.getTitle() + " ha come adicenti i seguenti titoli, in ordine decrescente di bilancio:\n");
    			for(AlbumConBilancio ab : adiacenze)
    				this.txtResult.appendText(ab.toString() + "\n");
    		}else
    			this.txtResult.setText("L'album " + a.getTitle() + " non ha adicenti.\n");
    	}else 
    		this.txtResult.setText("ERRORE: selezionare un album dal menù a tendina 'a1', prima di richiedere le Adiacenze.\n");
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
    		Album a1 = this.cmbA1.getValue();
    		Album a2 = this.cmbA2.getValue();
        	List<Album> percorso = new LinkedList<>();
        	if(a1!= null && a2!= null && !a1.equals(a2)) {
        		int x = Integer.parseInt(this.txtX.getText());
        		if(x>0) {
        			percorso = this.model.trovaPercorso(a1,a2, x);
        			if(percorso.size()>0){
            			this.txtResult.setText("La ricerca tra i due album selezionati ha profdotto il seguente percorso come risultato:\n");
            			for(Album a : percorso)
            				this.txtResult.appendText(a.toString()+ "\n");
            		}else
            			this.txtResult.setText("La ricerca tra i due album selezionati non ha prodotto alcun risultato.\n");
        		}else
        			this.txtResult.setText("Deve inserire un valore numerico maggiore di zero come valore 'soglia'.\n");
        	}else 
        		this.txtResult.setText("ERRORE: selezionare un album dal menù a tendina 'a1' e 'a2', prima di richiedere le Adiacenze.\n");
    	
    	}catch (NumberFormatException ne) {
    		ne.printStackTrace();
    		this.txtResult.setText("Errore! Si prega di inserire un valore numerico maggiore di zero nel campo 'Soglia x'.");
    	}catch (RuntimeException rte) {
    		rte.printStackTrace();
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	this.cmbA1.getItems().clear();
    	this.cmbA2.getItems().clear();
    	
    	try {
    		int n = Integer.parseInt(this.txtN.getText());
    		if(n>0) {
    			if(this.model.creaGrafo(n)) {
    				this.txtResult.setText("Grafo creato correttamente!\n");
    				this.txtResult.appendText("Esso è composto da:\n");
    				this.txtResult.appendText("#Vertex = " + this.model.getVertex().size() +"\n");
    				this.txtResult.appendText("#Edges  = " + this.model.getEdges().size() + "\n");
    				this.cmbA1.getItems().addAll(this.model.getVertex());
    				this.cmbA2.getItems().addAll(this.model.getVertex());
    			}else
    				this.txtResult.setText("ERRORE: non è stato possibile creare un grafo per il valore richiesto.");
    					
    		}
    		else
    			this.txtResult.setText("Deve inserire un valore numerico maggiore di zero");
    	}catch (NumberFormatException ne) {
    		ne.printStackTrace();
    		this.txtResult.setText("Errore! Si prega di inserire un valore numerico maggiore di zero nel campo #Canzoni.");
    	}catch (RuntimeException rte) {
    		rte.printStackTrace();
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
