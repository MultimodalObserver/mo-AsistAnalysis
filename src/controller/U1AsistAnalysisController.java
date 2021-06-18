/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import mo.core.Utils;
import static mo.core.Utils.getBaseFolder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Lathy
 */
public class U1AsistAnalysisController implements Initializable {

    @FXML
    private Button btnSelectDocuments;
    @FXML
    private Button btnRemoveDocuments;
    @FXML
    private Button btnRemoveAll;
    @FXML
    private ListView listDocuments;
    @FXML
    private ListView ListViewProyecto;
    @FXML
    private Text textCargarArchivos11;
    @FXML
    private ListView ListViewArchivosProyecto;
    @FXML
    private Button btnAgregarArchivos;
    public List<File> listofFiles = new ArrayList<>();
    public List<File> listofFilesFilter = new ArrayList<>();
    
     List<String> listFiles = new ArrayList<>();//Lista de path de los archivos para pasar a la siguiente ventana
     ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("properties/principal");
    @FXML
    private Text textTittle;
    @FXML
    private Text textCargarArchivos;
    @FXML
    private Text textProyecto;
    
    public String pathAnalisis;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarIdioma();
        try {
            loadProject();
            // TODO
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(U1AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void cargarIdioma(){
        textTittle.setText(dialogBundle.getString("U1ControllerTittle"));
        
        textProyecto.setText(dialogBundle.getString("U1Controllerproyecto"));
        
        textCargarArchivos11.setText(dialogBundle.getString("U1Controllerarchivosproyecto"));
        
        textCargarArchivos.setText(dialogBundle.getString("U1Controllercargararchivos"));
        
        btnAgregarArchivos.setText(dialogBundle.getString("U1ControllerbtnAgregar"));
        
        btnSelectDocuments.setText(dialogBundle.getString("U1ControllerbtnAgregarArchivos"));
        
        btnRemoveDocuments.setText(dialogBundle.getString("U1ControllerbtnRemover"));
        
        btnRemoveAll.setText(dialogBundle.getString("U1Controllerbtnremovertodos"));
    }
    
    @FXML
    private void selectDocuments(MouseEvent event) {
        
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(getBaseFolder()));
        fc.getExtensionFilters().addAll(
                                    new ExtensionFilter("Srt Files","*.srt", "*.coded"));
        List<File> selectedFile = fc.showOpenMultipleDialog(null);
        
        if (selectedFile != null){
            for (int i = 0; i<selectedFile.size(); i++){
                listDocuments.getItems().add(selectedFile.get(i).getAbsolutePath());
                
            
            }
            for (int j=0; j<selectedFile.size();j++){
                
                listFiles.add(selectedFile.get(j).getAbsolutePath());
            
            }
            
            
        }else{
            System.out.println("Archivo no valido");
        
        }
        
    }

    @FXML
    private void removeDocuments(MouseEvent event) {
        int index = listDocuments.getSelectionModel().getSelectedIndex();
        
        if(index>= 0){
            listDocuments.getItems().remove(index);
            listFiles.remove(index);
        
        }
        
    }

    @FXML
    private void removeAllDocuments(MouseEvent event) {
        listDocuments.getItems().clear();
        listFiles.clear();
        
        
        
    }

    @FXML
    private void itemClicked(MouseEvent event) {
        File file = new File(ListViewProyecto.getSelectionModel().getSelectedItem().toString());
        List<File> loadFiles = new ArrayList<>();
        listofFilesFilter.clear();
        listofFiles.clear();
        
        listofFiles = listf(file.getAbsolutePath(), loadFiles);//Obtengo todos los archivos de carpetas y subcarpetas
        
        
        
        for(int i=0; i<listofFiles.size();i++){//Se eliminan de listofFiles todos los archivos que no son .mp3 o .wav
            if(listofFiles.get(i).getAbsolutePath().endsWith(".srt") || listofFiles.get(i).getAbsolutePath().endsWith(".coded")){
                //System.out.println("Archivo list: "+listofFiles.get(i).getAbsolutePath());
                listofFilesFilter.add(listofFiles.get(i));
                //listViewDocProyecto.getItems().add(listofFiles.get(i).getAbsolutePath());//coloco los path de cada archivo en la lista de archivos
            }else if (!listofFiles.get(i).getAbsolutePath().endsWith(".srt") || !listofFiles.get(i).getAbsolutePath().endsWith(".coded")){
                //System.out.println("Archivo que no es .mp3 ni .wav "+listofFiles.get(i).getAbsolutePath());
                //listofFiles.remove(listofFiles.get(i));//listofFiles solo queda con archivos .mp3 o .wav
                }
        }
        
        
        if(listofFilesFilter.size()>0){
            ListViewArchivosProyecto.getItems().clear();//Se eliminan los archivos anteriores
            ListViewArchivosProyecto.setDisable(false);
            btnAgregarArchivos.setDisable(false);
            //labelIndicacion.setText("");
            for(int i=0; i<listofFilesFilter.size();i++){//Recorro la lista de archivos  y los agrego al listView
                //System.out.println("\n"+listofFiles.get(i).getAbsolutePath());
                ListViewArchivosProyecto.getItems().add(listofFilesFilter.get(i).getAbsolutePath());//coloco los path de cada archivo en la lista de archivos

            }
        }else{//Si no existe algun archivo .mp3 o .wav, desactivar el boton y el listview del documento
            ListViewArchivosProyecto.setDisable(true);
            btnAgregarArchivos.setDisable(true);
            //labelIndicacion.setText("No existen archivos compatibles en el proyecto.");
        
        }
    
        
    }
    
    public List<File> listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);
        
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if(fList != null){
            for (File file : fList) {      
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
        }
    
    
    return files;
    
    }
    
    //Carga los proyectos que se encuentren abiertos o creados en MO
    public void loadProject () throws ParserConfigurationException{
        //File file = new File(Utils.getBaseFolder()+"preferences.xml");
        //listDocumets.getItems().add(ProjectUtils.isProjectFolder(file));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc;
            doc = builder.parse(Utils.getBaseFolder()+"/preferences.xml");//Obtiene la carpeta donde se encuentra el jar y buscar el archivo preferences.xml
            NodeList location = doc.getElementsByTagName("location");//Busca la etiqueta de location, para ubicar el proyecto
            for(int i=0; i<location.getLength();i++){
                Node p = location.item(i);
                if(p.getNodeType()==Node.ELEMENT_NODE){
                    Element name = (Element) p;
                    String locationString = name.getTextContent();//Ubicacion donde esta el proyecto
                    pathAnalisis = locationString;
                    ListViewProyecto.getItems().add(locationString);
                }

            }
        } catch (SAXException ex) {
            Logger.getLogger(U1AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(U1AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addDocuments(MouseEvent event) {
        String pathAbsoluteFile = ListViewArchivosProyecto.getSelectionModel().getSelectedItem().toString();//SE OBTIENE EL PATH DE CADA ARCHIVO
        listDocuments.getItems().add(pathAbsoluteFile);        
        listFiles.add(pathAbsoluteFile);
        
        
    }
    
    //Obtengo la lista de transcripcion de este controllador
    public List<String> get_list(){
        
        return listFiles;
    }

    public String getPathAnalisis(){
        return pathAnalisis;
    
    }
    
    
    
}
