/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import bibliothek.util.xml.XElement;
import bibliothek.util.xml.XIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;

import com.kennycason.kumo.WordFrequency;

import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizer.WordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.CloudTags;
import mo.analysis.AnalysisProvider;
import static mo.core.Utils.getBaseFolder;
import mo.core.plugin.Extends;
import mo.core.plugin.Extension;
import mo.core.plugin.Plugin;
import mo.core.plugin.PluginRegistry;
import mo.organization.Configuration;
import mo.organization.ProjectOrganization;
import mo.organization.StagePlugin;
//import mo.transcriptionAnalysis.model.Transcription;
/**
 *
 * @author Lathy
 */




public class ClougTagsAsistAnalysisController implements Initializable {
    private final static String PLUGIN_NAME = "Assistant Analysis CloudTags";
    @FXML
    private Label labelCloud;
    @FXML
    private TextField textFieldUbicacion;
    @FXML
    private Button btnGenerate;
    @FXML
    private Spinner<Integer> spinnerWord;
    @FXML
    private RadioButton radioCloud;
    @FXML
    private ToggleGroup visualizationRadio;
    @FXML
    private RadioButton radioTable;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnGuardar;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabTable;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn wordColumn;
    @FXML
    private TableColumn lengthColumn;
    @FXML
    private TableColumn frequencyColum;
    @FXML
    private Tab tabCloud;
    @FXML
    private ScrollPane scrollPane;

    private int INIT_VALUE = 1;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,INIT_VALUE); //Del 1 al 20, empieza en INIT_VALUE
    private List<File> listFileDocsTemporal = new ArrayList<>();
    
    public WordCloud wordCloud;
    

    private final ObservableList<CloudTags> list = FXCollections.observableArrayList();
    
    public List<String> listWordsWithoutRep = new ArrayList<>();
    
    public List<String> listOfWords = new ArrayList<>();
    public List<String> listOfWordsExcluded = new ArrayList<>();
    
    
     public List<String> listOfCloud = new ArrayList<>();
    
     
     ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("properties/principal");
    @FXML
    private Text tittleCloudTags;
    @FXML
    private Text textCargarPalExcluidas;
    @FXML
    private Text textformato;
    @FXML
    private Text textModo;
    @FXML
    private Text textUbicaciondelArchivo;
    @FXML
    private Text textLongitu;
    int numberConfiguration = 1;
    
    private List<Configuration> configurations; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initSpinner();
        setEnableBtn();
        cargarIdioma();
       
    }    
    
    public void cargarIdioma(){
        tittleCloudTags.setText(dialogBundle.getString("CloudTittle"));
        
        textCargarPalExcluidas.setText(dialogBundle.getString("CloudTextCargarPalabras"));
        
        textUbicaciondelArchivo.setText(dialogBundle.getString("CloudTextUbicacionarchivo"));
        
        btnClear.setText(dialogBundle.getString("CloudBtnLimpiar"));
        
        textformato.setText(dialogBundle.getString("CloudFormato"));
        
        textModo.setText(dialogBundle.getString("CloudTextModoVisualizacion"));
        
        radioCloud.setText(dialogBundle.getString("CloudRadioButtonNube"));
        
        radioTable.setText(dialogBundle.getString("CloudRadioButtonTable"));
        
        textLongitu.setText(dialogBundle.getString("CloudTextLongitud"));
        
        btnGenerate.setText(dialogBundle.getString("CloudBtnGenerar"));
        
        textFieldUbicacion.setPromptText(dialogBundle.getString("CloudTextField"));
        
    
    
    }
    
    public void setEnableBtn() {
        btnGenerate.setDisable(false);
        btnGuardar.setDisable(true);
        btnGuardar.setText(dialogBundle.getString("CloudGenerarTable"));
    }
    
    public void initSpinner(){
        spinnerWord.setValueFactory(svf);
    
    }
    
    public List<File> get_ListFileTemporal(){
        return listFileDocsTemporal;
    }
    
    public void set_ListFileTemporal(List<File> listFile){
        listFileDocsTemporal.clear();
        int numero = 0;
        for(int i = 0 ; i<listFile.size(); i++){
            //Si es !de coded agregar a lista termporal, caso contrario no hacer nada
            if(!listFile.get(i).getAbsolutePath().endsWith(".coded")){//Entra si es .srt
                
                listFileDocsTemporal.add(listFile.get(i));
            }else{//Si hay al menos un archivo .coded 
                numero = 1;
            }
            
        }
        
        if (numero == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("CloudInformacionCompatible"));
            alert.showAndWait();
        
        
        }
    }
    
    @FXML
    private void selectDocuments(MouseEvent event) {
        FileChooser fc = new FileChooser();
        
        
        fc.setInitialDirectory(new File(getBaseFolder()));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Txt Files","*.txt")); //"Audio Files","*.wav"
        List<File> selectedFile = fc.showOpenMultipleDialog(null);
        
        if (selectedFile != null){
            for (int i = 0; i<selectedFile.size(); i++){
                textFieldUbicacion.setText(selectedFile.get(i).getAbsolutePath());
                //listDocumets.getItems().add(selectedFile.get(i).getAbsolutePath());   
            }
        }else{
            System.out.println("Archivo no valido");
        
        }
    }

    @FXML
    private void clickGenerate(MouseEvent event) throws FileNotFoundException, IOException {
        
        if(listFileDocsTemporal.size()<=0){
            labelCloud.setText(dialogBundle.getString("CloudFileTemporal0"));
        }else{
            listWordsWithoutRep.clear();
        listOfWords.clear();
        listOfCloud.clear();//Lista con las palabras para nube de palabras
        
        String nombreArchivo = textFieldUbicacion.getText();
        leerPalabrasExcluidas(nombreArchivo);//modifica listOfWordsExcluded
        int numeroPalabrasSpinner = spinnerWord.getValue();
        
        
        if (radioCloud.isSelected()){//Si es cloud tags
            long inicio = System.currentTimeMillis();
            labelCloud.setText("");
            for(int i=0; i<listFileDocsTemporal.size();i++){
                leerArchivo(listFileDocsTemporal.get(i).getAbsolutePath(), numeroPalabrasSpinner);//
                //se guardan en listOfWords, todas las palabras y que no supereren el spinner
            }
            
            eliminarExcluidasCloud();
            
            
            
            wordCloud = getWordCloud(listOfCloud);
            BufferedImage b = wordCloud.getBufferedImage();
            Image i = SwingFXUtils.toFXImage(b, null);
            ImageView v = new ImageView(i);
            
            scrollPane.setContent(v);
            long ejecucion = System.currentTimeMillis() - inicio; 
            System.out.println("Tiempo al cargar archivos en miliseundos: "+ ejecucion + "Numero palabras: "+ listOfCloud.size());
            //paneImage.getChildren().add(v);
            btnGuardar.setDisable(false);
            
            
            
        }else if(radioTable.isSelected()){//Si es table
            labelCloud.setText("");
            
            for(int i=0; i<listFileDocsTemporal.size();i++){
                leerArchivo(listFileDocsTemporal.get(i).getAbsolutePath(), numeroPalabrasSpinner);//aca quedan las palabras del ultimo
            }
           
            if (tableView.getItems().isEmpty()){
                btnGuardar.setDisable(false);
                //System.out.println("eSTA VACIO");
            
                
                wordColumn.setCellValueFactory(new PropertyValueFactory <CloudTags, String>("word"));
                lengthColumn.setCellValueFactory(new PropertyValueFactory <CloudTags, Integer>("length"));
                frequencyColum.setCellValueFactory(new PropertyValueFactory <CloudTags, Integer>("frequency"));
                for(int i=0; i<listWordsWithoutRep.size();i++){
                    list.add( new CloudTags (
                                    listWordsWithoutRep.get(i),
                                    listWordsWithoutRep.get(i).length(),
                                    Collections.frequency(listOfWords, listWordsWithoutRep.get(i))          
                    ));
                }
                tableView.setItems(list); //muestro los items
                
                
                
            }else{
                btnGuardar.setDisable(false);
                //System.out.println("NO ESTA VACIO");
                //System.out.println("no esta vacio");
            
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText(dialogBundle.getString("Seguro_guardar"));
                Optional <ButtonType> action = alert.showAndWait();

                if(action.get()==ButtonType.OK){
                    tableView.getItems().clear();
//                    System.out.println("ok");
//                    tableView.refresh();
                    wordColumn.setCellValueFactory(new PropertyValueFactory <CloudTags, String>("word"));
                    lengthColumn.setCellValueFactory(new PropertyValueFactory <CloudTags, Integer>("length"));
                    frequencyColum.setCellValueFactory(new PropertyValueFactory <CloudTags, Integer>("frequency"));
                    for(int i=0; i<listWordsWithoutRep.size();i++){
                        list.add( new CloudTags (
                                        listWordsWithoutRep.get(i),
                                        listWordsWithoutRep.get(i).length(),
                                        Collections.frequency(listOfWords, listWordsWithoutRep.get(i))          
                        ));
                    }
                    tableView.setItems(list);


                }else{
                    //System.out.println("CANCEL");

                }
            
            
            }
        
        }else if(!radioCloud.isSelected() && !radioTable.isSelected()){
            labelCloud.setText(dialogBundle.getString("CloudDebeSeleccionarNubeoTable"));
        
        }
        }
        
        
        
        
        
        
        
        
        
        
        
    }
    
    
    private List<String> leerArchivo(String pathArchivo, int numeroSpinner) throws FileNotFoundException, IOException{
        File file = new File(pathArchivo);
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st; 
        int numero = 0;
        while ((st = br.readLine()) != null) {
                if(numero%4==2){//Se obtiene solo el texto
                    String[] splited = st.split("\\s+");//corto 
                    for(int j=0; j<splited.length;j++){
                        if(splited[j].length()>=numeroSpinner){//Para que la palabra no supere el largo indicado por el usuario
                            listOfWords.add(splited[j]);
                        }
                        
                    
                    }
                    
                    numero++;
                
                }else{
                    numero++;
                }
                
                //String[] splited = st.split("\\s+");//corto 
                
        
        }
        
  
        // Para eliminar las repetidas y agregarlas a listWordsWithoutRep
        for (String element : listOfWords) {
            if (!listWordsWithoutRep.contains(element)) {
                listWordsWithoutRep.add(element); 
            } 
        } 
        
        //Para eliminar las palabras excluidas
        if(!listOfWordsExcluded.isEmpty()){
            for (String element : listOfWordsExcluded) {
                if (listWordsWithoutRep.contains(element)) {
                    listWordsWithoutRep.remove(element);
                } 
            } 
        }else{//Nada
        
        }
        
        
        
    
        return listOfWords;
    }
    
    
    
    private List<String> leerPalabrasExcluidas(String nombreArchivo) throws FileNotFoundException, IOException{
        listOfWordsExcluded.clear();
        if(nombreArchivo.equals("")){//Si es igual a vacio, no hace nada
        
        }else{//Si es distinto de vacio, hacer algo
            File archivo = new File(nombreArchivo);
            BufferedReader br = new BufferedReader(new FileReader(archivo)); 
            String st; 
            
            while ((st = br.readLine()) != null) {
                
                String[] splited = st.split("\\s+");//corto 
                for(int j=0; j<splited.length;j++){
                        //System.out.println(splited[j]);
                        listOfWordsExcluded.add(splited[j]);
                }
                  
                
                //String[] splited = st.split("\\s+");//corto 
                
        
        }
            
        }
        
        
    
        return listOfWordsExcluded;
    }

    @FXML
    private void clearTextField(MouseEvent event) {
        textFieldUbicacion.setText("");
    }

    

    @FXML
    private void onMousePressed(MouseEvent event) {
        int selected = tabPane.getSelectionModel().getSelectedIndex();
        if(selected ==0){
            btnGuardar.setText(dialogBundle.getString("CloudGenerarTable"));
            
        }else{
            btnGuardar.setText(dialogBundle.getString("CloudGenerarNube"));
        
        }
        
        
        //tabPane.getSelectionModel().getSelectedItem().toString()
        
    }

    @FXML
    private void clickSave(MouseEvent event) throws Exception {
        String selected = btnGuardar.getText();
        String path = "";
        DirectoryChooser directoryChooser = new DirectoryChooser();
        
        directoryChooser.setInitialDirectory(new File(getBaseFolder()));
        File selectedDirectory = directoryChooser.showDialog(null);
        
        String archivo = "";
        String extensionCsv = ".csv";
        String extensionPng = ".png";
        
        for(int i = 0; i<listFileDocsTemporal.size();i++){
            
            archivo = archivo + listFileDocsTemporal.get(i).getName()+"_";
        
        }
        
        
        
        if(selectedDirectory ==null){
            
        
        }else{
            path = selectedDirectory.getAbsolutePath();
            
            
            if(selected.equals("Save .csv") || selected.equals("Guardar .csv")){//Se guarda como tabla
                archivo = archivo + extensionCsv;
                path = path+"\\"+archivo;
                if(!tableView.getItems().isEmpty()){
                    writeCsv(path);
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText(dialogBundle.getString("CloudAntesDeGuardar"));
                    alert.showAndWait();
                
                }
                
                
            
        
            }else{//Se guarda como imagen
                archivo = archivo + extensionPng;
                path = path+"\\"+archivo;
                wordCloud.writeToFile(path);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText(dialogBundle.getString("CloudArchivoGuardado"));

                alert.showAndWait();
                
                


            }
        }
        
        
        
        
        
        
        
        
        
        
        
    }
    
    public void writeCsv(String pathDirectory) throws Exception {
        Writer writer = null;
        
        
        
        try {
            File file = new File(pathDirectory);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("Word"+";"+"Length"+";"+"Frequency"+"\n");
            for (CloudTags cloudTag : list) {

                String text = cloudTag.getWord() + ";" + cloudTag.getLength() + ";" + cloudTag.getFrequency() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
            writer.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("CloudArchivoGuardado"));

            alert.showAndWait();
        } 
    }
    
    public static WordCloud getWordCloud(List<String> words) throws FileNotFoundException, IOException {
        
        for (int i=0; i<words.size();i++){
            //System.out.println(words.get(i));
        
        }
        
//        List<String> wordsHere = new ArrayList<>();
//        
////        Collections.addAll(wordsHere,"hola","hola","hola","chao" ,"chao", "caca", "casi", "hola", "puede ser",
////                "casi", "hola", "matias", "jimenez", "sapo", "perro", "auto", "hola", "chao", "sillon","frazada","silla","sona","sola"
////        ,"hola","chao","hola","cojin","planta","pared","cocina","arroz","fideos","llaves","palo");
//        
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setMinWordLength(0);//Permite tener las palabras con largo 1
//        
//        frequencyAnalyzer.setWordFrequenciesToReturn(300);
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);//carga la lista de palabras
//        final Dimension dimension = new Dimension(1280, 720);
//        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//        wordCloud.setPadding(0);
//        wordCloud.setBackground(new CircleBackground(640));
//        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
//        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        //wordCloud.writeToFile("C:\\Users\\Lathy\\Desktop\\datarank_wordcloud_circle_sqrt_font.png");
//        
//        return wordCloud;
        //--------------------------------------------------------------------------
        
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setMinWordLength(0);
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        final Dimension dimension = new Dimension(600, 600);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new CircleBackground(300));
        
        //wordCloud.setColorPalette(buildRandomColorPallete(20));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        
        return wordCloud;
        
        //--------------------------------------------------------------------------
        
        //wordCloud.writeToFile("wordcloud_circle.png");
//        wordCloud.setPadding(2);
//        wordCloud.setBackground(new CircleBackground(300));
//        wordCloud.setColorPalette(buildRandomColorPallete(20));
//        wordCloud.setFontScalar(new LinearFontScalar(30, 40));
////        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
////        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("wordcloud_rectangle.png");
//        //wordCloud.writeToFile("C:/Users/Lathy/Desktop/wordcloud_rectangle.png");
        






        
        
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setWordFrequencesToReturn(600);
//        
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
//        final Dimension dimension = new Dimension(600, 600);
//        final WordCloud wordCloud = new WordCloud(600,600, CollisionMode.RECTANGLE);
//        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 18);
//        
//        
//        
//        wordCloud.setPadding(0);
//        wordCloud.setBackground(new RectangleBackground(600, 600));
//        wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
//        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("C:/Users/Lathy/Desktop/wordcloud_rectangle.png");
        
        
        
        
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setMinWordLength(1);
//        frequencyAnalyzer.setWordFrequencesToReturn(300);
//        final Dimension dimension = new Dimension(500, 200);
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
//        final WordCloud wordCloud = new WordCloud(1280, 720, CollisionMode.RECTANGLE);
//        wordCloud.setPadding(0);
//        wordCloud.setBackground(new RectangleBackground(1280, 720));
//        
//        //wordCloud.setColorPalette(buildRandomColorPallete(20));
//        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
//        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        
//        return wordCloud;
        
        
        //wordCloud.writeToFile("wordcloud_circle.png");
//        wordCloud.setPadding(2);
//        wordCloud.setBackground(new CircleBackground(300));
//        wordCloud.setColorPalette(buildRandomColorPallete(20));
//        wordCloud.setFontScalar(new LinearFontScalar(30, 40));
////        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
////        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("wordcloud_rectangle.png");
//        //wordCloud.writeToFile("C:/Users/Lathy/Desktop/wordcloud_rectangle.png");
        






        
        
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setWordFrequencesToReturn(600);
//        
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
//        final Dimension dimension = new Dimension(600, 600);
//        final WordCloud wordCloud = new WordCloud(600,600, CollisionMode.RECTANGLE);
//        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 18);
//        
//        
//        
//        wordCloud.setPadding(0);
//        wordCloud.setBackground(new RectangleBackground(600, 600));
//        wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
//        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("C:/Users/Lathy/Desktop/wordcloud_rectangle.png");
        
    }
    
    private static ColorPalette buildRandomColorPallete(int n) {
        Random random = new Random();
        final Color[] colors = new Color[n];
        for(int i = 0; i < colors.length; i++) {
            colors[i] = new Color(random.nextInt(230) + 25, random.nextInt(230) + 25, random.nextInt(230) + 25);
        }
        return new ColorPalette(colors);
    }

    private void eliminarExcluidasCloud() {
        listOfCloud = listOfWords;
        //listOfWords tiene todas las palabras con repeticion y con limite de palabras
        
        
        if(!listOfWordsExcluded.isEmpty()){
            for (String element : listOfWordsExcluded) {
                
                
                while (listOfCloud.contains(element)){
                    listOfCloud.remove(element);
                }
                
                
                
            } 
        }else{//Nada
        
        }
        
        
        
        
        
        
    }

    
    
}
