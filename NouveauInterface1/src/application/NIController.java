package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NIController implements Initializable {

    @FXML
    private TextField txtPrix;

    @FXML
    private TableColumn<Errands, String> ObjColumn;

    @FXML
    private TableView<Errands> errandsTable;

    @FXML
    private Button btnClear;

    @FXML
    private TableColumn<Errands, String> DistColumn;

    @FXML
    private ComboBox<String> cboUt;

    @FXML
    private TableColumn<Errands, String> PrixColumn;

    @FXML
    private Button btnEffacer;

    @FXML
    private Button btnModifier;

    @FXML
    private TextField txtDis;

    @FXML
    private Button btnAjouter;

    @FXML
    private TableColumn<Errands, String> UtilColumn;

    @FXML
    private TextField txtNom;

    
  //Liste pour les département - cette liste permettera de te donner les valeurs de comboBox

  	private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Voiture","Maison","Metier");

  //Placer les etudiants dans u ne observable list
  public ObservableList<Errands> errandsData=FXCollections.observableArrayList();

  //Creer une methode pour accéder a la lste des Etudiants

  public ObservableList<Errands> getetudiantData()
  {
  	return errandsData;
  }

@Override
public void initialize(URL location, ResourceBundle resources) {
	cboUt.setItems(list);
	//attribuer les valeurs aux colonnes de tableView
	ObjColumn.setCellValueFactory(new PropertyValueFactory<>("Objet"));
	PrixColumn.setCellValueFactory(new PropertyValueFactory<>("Prix CAD"));
	UtilColumn.setCellValueFactory(new PropertyValueFactory<>("Importance"));
	DistColumn.setCellValueFactory(new PropertyValueFactory<>("Min du maison"));
	errandsTable.setItems(errandsData);
	
	btnModifier.setDisable(true);
	btnEffacer.setDisable(true);
	btnClear.setDisable(true);
	
	showErrands(null);
	//Mettre a jour l'affichage d'un étudiant sélectionné
	errandsTable.getSelectionModel().selectedItemProperty().addListener((
			observable,oldValue,newValue)-> showErrands(newValue));
}

//Ajouter un étudiant
@FXML
void ajouter()
{
	Errands tmp=new Errands();
	
	tmp=new Errands();
	tmp.setNom(txtNom.getText());
	tmp.setPrenom(txtNom.getText());
	tmp.setAge(Double.parseDouble(txtAge.getText()));
	tmp.setDepartement(cboDept.getValue());
	etudiantData.add(tmp);
	clearFields();
}
	
}


