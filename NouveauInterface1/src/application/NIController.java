package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private Button btn1;
    
    @FXML
    private Button btn2;

    @FXML
    private TableColumn<Errands, String> UtilColumn;

    @FXML
    private TextField txtNom;

    
  //Liste pour les département - cette liste permettera de te donner les valeurs de comboBox

  	private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Voiture","Maison","Metier");

  //Placer les etudiants dans u ne observable list
  public ObservableList<Errands> errandsData=FXCollections.observableArrayList();

  //Creer une methode pour accéder a la lste des Etudiants

  public ObservableList<Errands> geterrandsData()
  {
  	return errandsData;
  }

@Override
public void initialize(URL location, ResourceBundle resources) {
	cboUt.setItems(list);
	//attribuer les valeurs aux colonnes de tableView
	ObjColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	PrixColumn.setCellValueFactory(new PropertyValueFactory<>("Prix"));
	UtilColumn.setCellValueFactory(new PropertyValueFactory<>("Util"));
	DistColumn.setCellValueFactory(new PropertyValueFactory<>("Dist"));
	errandsTable.setItems(errandsData);
	
	btnModifier.setDisable(true);
	btnEffacer.setDisable(true);
	btnClear.setDisable(true);
	
	showErrands(null);
	//Mettre a jour l'affichage d'un étudiant sélectionné
	errandsTable.getSelectionModel().selectedItemProperty().addListener((
			observable,oldValue,newValue)-> showErrands(newValue));
	
	// Mettre la colonne de prénom multiligne
	
}

//Ajouter un Errand
@FXML
void ajouter()
{
	Errands tmp=new Errands();
	
	tmp=new Errands();
	tmp.setNom(txtNom.getText());
	tmp.setPrix(txtPrix.getText());
	tmp.setDist(Double.parseDouble(txtDis.getText()));
	tmp.setUtil(cboUt.getValue());
	errandsData.add(tmp);
	clearFields();
}

//Effacer le contenu des champs
@FXML
void clearFields()
{
	cboUt.setValue(null);
	txtPrix.setText("");
	txtNom.setText("");
	txtDis.setText("");
}

//Afficher les étudiants
public void showErrands(Errands errands)
{
	if(errands !=null)
	{
		cboUt.setValue(errands.getUtil());
		txtPrix.setText(errands.getPrix());
		txtNom.setText(errands.getNom());
		txtDis.setText(Double.toString(errands.getDist()));
		btnModifier.setDisable(false);
		btnEffacer.setDisable(false);
		btnClear.setDisable(false);
	}
	else
	{
		clearFields();
	}
	
	
}
//Mise a jour d'un étudiant
@FXML
public void updatedErrrands()
{
	Errands errands=errandsTable.getSelectionModel().getSelectedItem();
	
	errands.setNom(txtNom.getText());
	errands.setPrix(txtPrix.getText());
	errands.setDist(Double.parseDouble(txtDis.getText()));
	errands.setUtil(cboUt.getValue());
	errandsTable.refresh();
}

//Effacer un Errand
@FXML
public void deleteEtudiant()
{
	int selectedIndex = errandsTable.getSelectionModel().getSelectedIndex();
	if(selectedIndex >=0)
	{
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Effacer");
		alert.setContentText("Confirmer la suppresion");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==ButtonType.OK)
		{
		errandsTable.getItems().remove(selectedIndex);
		}
	}
}

//Sauvegarde des donnée

//Recupérer le chemin (path) des fichiers si ca existe
public File getErrandsFilePath()
{
	Preferences prefs = Preferences.userNodeForPackage(Main.class);
	String filePath = prefs.get("filePath", null);
	
	if (filePath != null)
	{
		return new File(filePath);
	}
	else
	{
		return null;
	}
	
}

//attribuer un chemin de fichiers
public void setErrandsFilePath(File file)
{
	Preferences prefs = Preferences.userNodeForPackage(Main.class);
			if (file !=null)
			{
				prefs.put("filePath", file.getPath());
			}
			else
			{
				prefs.remove("filePath");
			}
}

//Prendre les données de type XML et les convertir en données de type javaFX
public void loadErrandsDataFromFile(File file)
{
	try
	{
		JAXBContext context = JAXBContext.newInstance(ErrandsListeWrapperr.class);
				Unmarshaller um = context.createUnmarshaller();
		
		ErrandsListeWrapperr wrapper = (ErrandsListeWrapperr) um.unmarshal(file);
				errandsData.clear();
				errandsData.addAll(wrapper.getErrands());
				setErrandsFilePath(file);
				
				Stage pStage = (Stage) errandsTable.getScene().getWindow();
				pStage.setTitle(file.getName());
				
	} catch (Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText("Les données n'ont pas été trouvées");
		alert.setContentText("Les donnés ne pouvaient pas etre trouvees dans le fichier : \n" + file.getPath());
		alert.showAndWait();
	}
}

//Prendre les données de type JavaFX et les convertir en type XML
public void saveErrandsDataToFile(File file)
{
	try
	{
		JAXBContext context = JAXBContext.newInstance(ErrandsListeWrapperr.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		ErrandsListeWrapperr wrapper = new ErrandsListeWrapperr();
		wrapper.setErrands(errandsData);
		
		m.marshal(wrapper, file);
		
		//Sauvegarde dans le registre
		setErrandsFilePath(file);
		//Pour donner le titre du fichier sauvegarder
		Stage pStage = (Stage) errandsTable.getScene().getWindow();
		pStage.setTitle(file.getName());
		
	} catch (Exception e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText("Les données n'ont pas été trouvées");
		alert.setContentText("Les donnés ne pouvaient pas etre trouvees dans le fichier : \n" + file.getPath());
		alert.showAndWait();
		
		
		
	}
}

//Commencer un nouveau
@FXML
private void handleNew()
{
	geterrandsData().clear();
	setErrandsFilePath(null);
}

//Le file chooser Permet a l'usager de choisir ;e fichier a ouvrire
@FXML
private void handleOpen()
{
	FileChooser fileChooser = new FileChooser();
	
	//permettre un filtre sur l'extension du fichier a chercher
	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
			"XML files (*.xml)", "*.xml");
	fileChooser.getExtensionFilters().add(extFilter);
	
	// montrer le dialogue
	File file = fileChooser.showOpenDialog(null);
	
	if(file != null)
	{
		loadErrandsDataFromFile(file);
	}
}

//Sauvegarder le fichier correspondant a l'errand actif
//S'il n y a pas de fichier, le menu sauvegarder sous ve s'afficher
@FXML
private void handleSave()
{
	File errandsFile = getErrandsFilePath();
	if (errandsFile != null)
	{
		saveErrandsDataToFile(errandsFile);
	}
	
	else
	{
		handleSaveAs();
	}
}

@FXML
private void handleSaveAs()
{
	FileChooser fileChooser = new FileChooser();
	
	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
			"XML files (*.xml)", "*.xml");
	fileChooser.getExtensionFilters().add(extFilter);
	
	// Sauvegarder
	File file = fileChooser.showSaveDialog(null);
	
	if(file != null)
	{
		//Verification de l'extention
		if(!file.getPath().endsWith(".xml"))
		{
			file = new File(file.getPath() + ".xml");
		}
		saveErrandsDataToFile(file);
	}
}

@FXML 
public void verifNum() // method pour trouver des input non numerique
{
	txtDis.textProperty().addListener((Observable,oldValue,newValue) ->
	{
		if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))
		{
			txtDis.setText(newValue.replaceAll("[^\\d*\\.]","")); // si le input est non numerique, ce le remplace
		}
	});
}

//Afficher les statistique
@FXML
void handleStats()
{
	try
	{
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("NI.fxml"));
		AnchorPane pane = loader.load();
		Scene scene = new Scene(pane);
		DisStat Disstat = loader.getController();
		Disstat.SetStats(errandsData);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Statistique");
		stage.show();
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
}

//System exit

@FXML
private void fermer()
{
	System.exit(0);
}
	
}


