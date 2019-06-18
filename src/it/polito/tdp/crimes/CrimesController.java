/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Model;
import it.polito.tdp.model.Neighbor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	int year = boxAnno.getValue();
    	//creo il grafo
    	model.creaGrafo(year);
    	//modello output
    	List<District> district = model.getDistrictList();
    	for(District d : district) {
    		List<Neighbor> neighbor = model.getNeighbor(d);
    		txtResult.appendText("\nI VICINI DI "+ d.getId()+" SONO: \n");
    		for(Neighbor n : neighbor) {
    			txtResult.appendText(n.toString()+";\n");
    		}
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	int year = boxAnno.getValue();
    	int month = boxMese.getValue();
    	int day = boxGiorno.getValue();
    	
    	//LocalDate date = LocalDate.of(year, month, day);
    	
    	int N = Integer.parseInt(txtN.getText());
    	if(N<1 || N >10) {
    		txtN.clear();
    		txtResult.appendText("Valore di N non accettabile, inserirlo nuovamente.");
    	} else {
    		int result = model.doSimulazione(year, month, day, N);
    		txtResult.appendText("\nGli eventi mal gestiti del giorno "+ day +" - " + month + " - " + year + " con " + N + " agenti sono: " + result +"\n");
    	}
    	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(model.selectYears());
    	boxMese.getItems().addAll(model.selectMonths());
    	boxGiorno.getItems().addAll(model.selectDays());
    	
    	/*for(int i = 0; i<12;i++) {
    		boxMese.getItems().add(i+1);
    	}*/
    }
}
