/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.text.Highlighter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import model.Subtitle;
import model.Coding;

/**
 * FXML Controller class
 *
 * @author Lathy
 */
public class LabelTextViewController implements Initializable {

    @FXML
    private ComboBox comboCodigo;
    @FXML
    private TextField textFieldWord;
    @FXML
    private Button btnAddCodigo;
    @FXML
    private TabPane TabPaneCod;
    @FXML
    private TableView tableViewCod;
    @FXML
    private TableColumn<Subtitle, Integer> tableColumnMinuto;
    @FXML
    private TableColumn<Subtitle, String> tableColumnTexto;
    
    private List<File> listFileDocsTemporal = new ArrayList<>();
    @FXML
    private TextField textFieldNameCode;
    @FXML
    private Button btnCreatedCod;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button btnDeleteCode;
    @FXML
    private ComboBox comboDeleteCode;
    @FXML
    private ListView listViewDocs;
    Subtitle subtitle = new Subtitle();
    private final ObservableList<Subtitle> list = FXCollections.observableArrayList();
    private final ObservableList<Coding> listCoding = FXCollections.observableArrayList();
    private List<String> nameOfCode = new ArrayList<>();
    private List<String> colorOfCode = new ArrayList<>();
    @FXML
    private Label labelCoding;
    private int numberOfId;//Id del subtitle para coding
    @FXML
    private TableColumn<Coding, Integer> idColumn;
    @FXML
    private TableColumn<Coding, String> nameCodeColumn;
    @FXML
    private TableColumn<Coding, String> colorColumn;
    @FXML
    private TableColumn<Coding, String> documentColumn;
    @FXML
    private TableColumn<Coding, String> wordColumn;
    @FXML
    private TableView tableViewCoding;
    @FXML
    private TableColumn<Coding, Integer> idSubColumn;
    private String pathTemporalSubtitle;
    
    private String pathSearchFile=null;
    
    private int id_coding = 0;

    private int start_word;
    private int end_word;
    private TextArea textArea;
    @FXML
    private Label label;
    private WebView web;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnUploadDoc;
    @FXML
    private Button btnSave;

    private String pathCodeCargado;
    
    Coding coding = new Coding();
    
    ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("properties/principal");
    @FXML
    private Text textTitleCoding;
    @FXML
    private Label LabelPalabraSeleccionada;
    @FXML
    private Label labelSeleccionarCodigo;
    @FXML
    private Tab textTabTexto;
    @FXML
    private Tab textTabCodigo;
    @FXML
    private Label LabelCrearCodigo;
    @FXML
    private Label LabelSeleccionarcolor;
    @FXML
    private Label LabelEliminarCodigo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarIdioma();    // TODO
    }    
    
    public void cargarIdioma(){
        textTitleCoding.setText(dialogBundle.getString("CodTittle"));
        textTabTexto.setText(dialogBundle.getString("CodPaneTexto"));
        textTabCodigo.setText(dialogBundle.getString("CodPaneCodigo"));
        LabelCrearCodigo.setText(dialogBundle.getString("CodPaneCodigoCrearCodigo"));
        textFieldNameCode.setPromptText(dialogBundle.getString("CodPaneCodigoCrearCodigoTextField"));
        LabelSeleccionarcolor.setText(dialogBundle.getString("CodPaneCodigoSeleccionarCodigo"));
        btnCreatedCod.setText(dialogBundle.getString("CodPaneCodigoBtnCrearCodigo"));
        LabelEliminarCodigo.setText(dialogBundle.getString("CodPaneCodigoEliminarCodigo"));
        btnDeleteCode.setText(dialogBundle.getString("CodPaneCodigoBtnEliminarCodigo"));
        LabelPalabraSeleccionada.setText(dialogBundle.getString("CodPalabraSeleccionada"));
        labelSeleccionarCodigo.setText(dialogBundle.getString("CodSeleccionarCodigo"));
        btnAddCodigo.setText(dialogBundle.getString("CodBtnAgregarCodigo"));
        btnUploadDoc.setText(dialogBundle.getString("CodPaneTextoBtnCargar"));        
        btnSave.setText(dialogBundle.getString("CodPaneTextoBtnGuardar"));        
        btnClose.setText(dialogBundle.getString("CodBtnClose"));        
                
                
    }
    

    public List<File> get_ListFileTemporal(){
        return listFileDocsTemporal;
    }
    
    public void set_ListFileTemporal(List<File> listFile){
        this.listFileDocsTemporal = listFile;
    }

    public void cargarDocumentosListView(){
        listViewDocs.getItems().clear();//Limpia cada vez que se cargan los documentos principales
        for (int i=0; i<listFileDocsTemporal.size();i++){
            listViewDocs.getItems().add(listFileDocsTemporal.get(i).getAbsolutePath());//Se agregan los path a los documentos en listview
        }
    
    }
    
    
    @FXML
    private void setOnMenu(ContextMenuEvent event) {
        System.out.println("selected text:"+ textFieldWord.getSelectedText());
        

    }

    @FXML
    private void clickAddCode(MouseEvent event) {
        boolean isMyComboBoxEmpty = comboCodigo.getSelectionModel().isEmpty();
        
        idColumn.setCellValueFactory(new PropertyValueFactory <Coding, Integer>("id"));
        idSubColumn.setCellValueFactory(new PropertyValueFactory <Coding, Integer>("id_subtitle"));
        nameCodeColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("nombre"));
        colorColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("color"));
        documentColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("pathDocumento"));
        wordColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("palabra"));
        
        if(!isMyComboBoxEmpty && !textFieldWord.getText().equals("")){//Comprueba que no este vacio el combobox
            String colorOfCoding = colorOfCode.get(comboCodigo.getSelectionModel().getSelectedIndex());
            labelCoding.setText("");
            listCoding.add(new Coding(
                    id_coding,
                    numberOfId,
                    comboCodigo.getSelectionModel().getSelectedItem().toString(),
                    colorOfCode.get(comboCodigo.getSelectionModel().getSelectedIndex()),
                    pathTemporalSubtitle,
                    textFieldWord.getText()
            ) );
            
           
            id_coding++;
            tableViewCoding.setItems(listCoding);//Se actualiza el tableview
            //textFieldWord.selectRange(1, 3);
            //textFieldWord.setStyle("-fx-highlight-fill:red");
            
            comboCodigo.getSelectionModel().selectFirst();//Se seleciona el primero     
            
            
            
//            textField.setText("Mi nombre es Oliver");
//            //textField.selectRange(0, 5);
//            textField.setEditable(false);
//            textField.selectRange(3, 6);
//            textField.setStyle("-fx-control-inner-background:red");
//            String inicio_Etiqueta = "<p id='root'>";
//            String inicio = "<p>";
//            String fin = "</p>";
//            String uno = "holaaaa"; //#FFFF00
//            String dos = "chao";
//             web.getEngine().loadContent(uno);
//            append(inicio, uno, dos, fin);
            
            
//            String fin_etiqueta="</p>";
//              String viejo = "<span style='background : rgba(255,0,0,0.3); '>B</span><span style='background : rgba(0,255,0,0.3);'>casi</span>";
//            String nuevo = "<span style='background : rgba(255,0,0,0.3); '> Chao</span>";
             // web.getEngine().loadContent(uno);
            //append(inicio_Etiqueta,viejo, nuevo,fin_etiqueta);
//            textField.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
//                @Override public void handle(MouseEvent t) { t.consume(); }
//              });
            
            
            for (Subtitle bean : list){
                
                if(numberOfId == bean.getId()){//Aca tengo la fila de donde saque la palabra que quiero remarcar
                    //System.out.println("hola");
                    //String color = colorOfCode.toString();//#FFFFFF
                    String etiquetaInicial = "<span style='background :"+colorOfCoding+"'>";
                    String etiquetaFinal = "</span>";
                    String textoTotal = bean.getTexto();//Se obtiene el texto total
                    String inicio = textoTotal.substring(0, start_word); //Cuando selecciono todo falla aca
                    String fin = textoTotal.substring(end_word, textoTotal.length());
                    String palabra = textFieldWord.getText();
//                    System.out.println("Frase antes de la palabra: "+inicio);
//                    System.out.println("Frase depsies de la palabra: "+fin);
//                    System.out.println("Palabra: "+palabra);
                    
                    
                    
                    String etiqueta = inicio + etiquetaInicial + palabra  + etiquetaFinal + fin;
                    bean.setTexto(etiqueta);
//                    System.out.println(etiqueta);
                    bean.setTextfield(bean.getTextfield());
                    //tableViewCod.setItems(list);
                    
                    //bean.getTextfield().setEditable(false);
                    //bean.getTextfield().setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 20px;");
                    
//                    textArea.selectRange(5, 8);
//                    textArea.setStyle("-fx-highlight-text-fill: firebrick;");
                    
                }
                
            
            }
            textFieldWord.setText("");//se vacia el textfield de palabra
        
        }else if(textFieldWord.getText().equals("")){
            labelCoding.setText(dialogBundle.getString("CodDebeSeleccionarUnaPalabra"));
        
        }else if(isMyComboBoxEmpty){
            labelCoding.setText(dialogBundle.getString("CodDebeSeleccionarCodigo"));
        }
    }

    @FXML
    private void contextMenu(ContextMenuEvent event) {
        ObservableList<Subtitle> dataRows = FXCollections.observableArrayList();
        for (Subtitle bean : list){
            //System.out.println("Context");
            //System.out.println(bean.getTextfield().getSelectedText());
            
        }
    }

    @FXML
    private void clickedSelectedText(MouseEvent event) {
        int numero =  tableViewCod.getSelectionModel().getFocusedIndex();
        //System.out.println(numero);
        Subtitle subtitleSelected = (Subtitle) tableViewCod.getSelectionModel().getSelectedItem();
        
            if(event.getClickCount() == 2){//funciona 
                System.out.println("Double clicked");
                
            }
            
        
        if(subtitleSelected==null){
            System.out.println("No hay seleccion");
        }else{
            //System.out.println(subtitleSelected.getTexto());
        }
        
        ObservableList<Subtitle> dataRows = FXCollections.observableArrayList();
        for (Subtitle bean : list){
            String selection = (String) bean.getTextfield().getEngine()
                     .executeScript("window.getSelection().toString()");
            
            String text = bean.getTexto();//sE obtiene el texto original
            bean.getTextfield().setOnKeyPressed(new EventHandler<KeyEvent>()//Press webview
                {
                    @Override
                    public void handle(KeyEvent ke){
                        if (ke.getCode().equals(KeyCode.ENTER)){
                            
                            int inicioParte = text.indexOf(selection);//Si es distinto de -1 es porque se encuentra
                            //System.out.println(inicioParte);
                            if(inicioParte!=-1 && !selection.equals(" ")){//Significa que se encuentra la palabra en el texto original
                                
                                
                                
                                //Si entra al if, esporque existe una etiqueta en la linea
                                if(text.indexOf(">", inicioParte)!=-1 && selection.length()<4){//significa que encontro la palabra en la etiqueta html <span ...>
                                    int numero = 0;
                                    String subCadenaInicio = text.substring(inicioParte-1, inicioParte);
                                    String subCadenaFin = text.substring(inicioParte+selection.length(), inicioParte+selection.length()+1);
                                    
                                    System.out.println("Inicio: "+subCadenaInicio+", Termino: "+subCadenaFin);
                                    
                                    while(!subCadenaInicio.equals(">") && !subCadenaFin.equals("<")){
                                        inicioParte = text.indexOf(selection, inicioParte+1);
                                        subCadenaInicio = text.substring(inicioParte-1, inicioParte);
                                        subCadenaFin = text.substring(inicioParte+selection.length(), inicioParte+selection.length()+1);
                                    
                                    }
                                    
                                    
                                    textFieldWord.setText(selection);
                                    numberOfId=bean.getId();

                                    //System.out.println();
                                    start_word = inicioParte;//Se obtiene donde inicia la palabra
                                    end_word = start_word + selection.length();
                                    
                                
                                }else{
                                    
                                    textFieldWord.setText(selection);
                                    numberOfId=bean.getId();

                                    //System.out.println();
                                    start_word = text.indexOf(selection);//Se obtiene donde inicia la palabra
                                    end_word = start_word + selection.length();
                                    
                                }
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                            
                            }else{
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Información");
                                alert.setHeaderText(null);
                                alert.setContentText(dialogBundle.getString("CodNoPuedeSeleccionarEspacio"));

                                alert.showAndWait();
                            
                            }
                            
                            
//                            if(selection.length()>=4 && inicioParte!=-1){//para que no sea un pronombre o palabra sola
//                                //System.out.println("Enter: "+ selection);//Funciona
//                                if(listCoding.size()==0){
//                                    textFieldWord.setText(selection);
//                                    numberOfId=bean.getId();
//
//                                    //System.out.println();
//                                    start_word = text.indexOf(selection);//Se obtiene donde inicia la palabra
//                                    end_word = start_word + selection.length();
//                                    //System.out.println("Incio: "+start_word+"Fin: "+end_word);
//                                }else{
//                                    int seleccion;
//                                    if(text.indexOf(selection)-1<0){//Significa que no existe etiqueta para la primera palabra
//                                        seleccion = 0;
//                                        System.out.println("Menor que 0");
//                                    }else{
//                                        seleccion = text.indexOf(selection)-1;
//                                        System.out.println("Mayor que 0");
//                                    
//                                    }
//                                    
//                                    //System.out.println("a: "+ (text.substring(text.indexOf(selection)-1, text.indexOf(selection))).equals(">") );
//                                    //&& !(text.substring(text.indexOf(selection)-1, text.indexOf(selection))).equals(">")
//                                    for(int i=0; i<listCoding.size();i++){//Permite buscar en la tabla de codigos si se encuentra la palabra a agregar
//                                        if(!listCoding.get(i).getPalabra().equals(selection) && !(text.substring(seleccion, text.indexOf(selection))).equals(">")  ){//Verifica si ya se encuentra agregada && !listCoding.get(i).getPalabra().contains(selection) && bean.getId()== listCoding.get(i).getId_subtitle()
//                                            System.out.println("Entro aca: "+ listCoding.get(i).getPalabra());
//                                            textFieldWord.setText(selection);
//                                            numberOfId=bean.getId();
//
//                                            //System.out.println();
//                                            start_word = text.indexOf(selection);//Se obtiene donde inicia la palabra
//                                            end_word = start_word + selection.length();
//                                            //System.out.println("Incio: "+start_word+"Fin: "+end_word);
//                                            break;
//
//                                        }else{//Aca es porque se encuentra la palabra
//                                            System.out.println("Entro alerta: "+ listCoding.get(i).getPalabra());
//                                            Alert alert = new Alert(AlertType.INFORMATION);
//                                            alert.setTitle("Información");
//                                            alert.setHeaderText(null);
//                                            alert.setContentText("La seleccion ya se encuentra agregada.");
//
//                                            alert.showAndWait();
//                                            break;
//
//                                        }
//                                
//                                }
//                                
//                                }
//                                
//                                
//                                
//                                
//                                
//                                
//                                
//                                
//                            }else{
//                                Alert alert = new Alert(AlertType.INFORMATION);
//                                alert.setTitle("Información");
//                                alert.setHeaderText(null);
//                                alert.setContentText("Debe escoger un rango mayor de selección.");
//
//                                alert.showAndWait();
//                            
//                            }
                            
                            
    
                        }
                    }
                });
//            bean.getTextfield().focusedProperty().addListener(new ChangeListener<Boolean>(){
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
//            {
//                if (newPropertyValue){
//                    bean.getTextfield().setOnKeyPressed(event -> 
//                            if (event.get == KeyCode.ENTER) {
//                                System.out.println("Seleccionado"+selection);
//                            }
//                            
//                    );
//                    //---------------------------------------------------------------
////                    bean.getTextfield().setOnMouseClicked((EventHandler<? super MouseEvent>) event{
////                    public void handle(ActionEvent e) {
////                      textFieldWord.setText(bean.getTextfield().getSelectedText());
////                      //System.out.println(bean.getTextfield().getSelectedText() + " " +bean.getId());
////                      numberOfId=bean.getId();
////                      start_word = bean.getTextfield().getText().indexOf(textFieldWord.getText());
////                      end_word = (bean.getTextfield().getText().indexOf(textFieldWord.getText())+textFieldWord.getText().length( ));
////                      //bean.getTextfield().getText().indexOf(textFieldWord.getText());//del texto completo, se obtiene donde empieza la palabra
////                      //System.out.println("La palabra se encuentra en: "+bean.getTextfield().getText().indexOf(textFieldWord.getText())+" " + (bean.getTextfield().getText().indexOf(textFieldWord.getText())+textFieldWord.getText().length( )));
////                    }
////                  });
//                     //---------------------------------------------------------------   
//                    
//                   
//                    
//                    
//                    
//                }else{
//                    //System.out.println("Textfield out focus");
//                }
//            }
//            });
            
            
            //System.out.println("Nuevo");//bean.getTextfield().getSelectedText()
            
        
        
        }
    }

    @FXML
    private void uploadDocOnTexto(ActionEvent event) throws FileNotFoundException, IOException {
       
        
        String path = listViewDocs.getSelectionModel().getSelectedItem().toString();//Se obtiene el documento seleccionado
        pathTemporalSubtitle = path;
        
        //Subtitle sub = new Subtitle();
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st; 
        List<Integer> numeros = new ArrayList<>();
        int foo;
        int numero;
        String pathCodes = "";
        String pathTable = "";
        String pathOriginal = "";
        
            
        if(path.endsWith(".coded")){//Si termina en .coding es porque es un archivo con codigos
            
            
            pathOriginal = path.substring(0, path.lastIndexOf(".coded"));
            pathCodes = pathOriginal + ".codes";
            pathTable = pathOriginal + ".table";
            
            
            
            
        
        }else{//Si es un archivo .srt y se habia precargado un archivo .coding
            //Se deben eliminar los codigos de combobox
            
        
        
        }
        
        
        
        
        
        
        if (tableViewCod.getItems().isEmpty()){
            if (!path.endsWith(".coded")){
                    tableViewCoding.getItems().clear();//Elimina los elementos de la tabla secundaria cuando es .srt
                    comboCodigo.getItems().clear();//Se elimina lo que tiene el combobox de codigos
                    comboDeleteCode.getItems().clear();//Se elimina lo que tiene el combobox de eliminar codigos
                    nameOfCode.clear();//Elimina de la lista los elementos que tenia
                    colorOfCode.clear();//Elimina de la lista los elementos que tenia
                    id_coding = 0;
            }else{//Si es .coding
                loadCodes(pathCodes);//Carga los codigos del archivo
                loadTable(pathTable);
            
            }
            
            TextFieldTableCell text = new TextFieldTableCell();
            //System.out.println("vacio");
            tableColumnMinuto.setCellValueFactory(new PropertyValueFactory <Subtitle, Integer>("id"));
            tableColumnTexto.setCellValueFactory(new PropertyValueFactory <Subtitle, String>("textfield"));
            tableViewCod.setFixedCellSize(50);
            
            //tableColumnTexto.setGraphic(text);
           

//            tableViewCod.setEditable(true);
//            tableColumnTexto.setCellFactory(TextFieldTableCell.forTableColumn());
            
            
            
            

            while ((st = br.readLine()) != null) {

                String[] splited = st.split("\\s+");//corto 
                if (splited.length==1){//si es un espacio o el numero de subtitulo && (isNumber(splited[0]))!=-1
                    
                    try {
                        foo = Integer.parseInt(splited[0]);
                    }
                    catch (NumberFormatException e)
                    {
                        foo = 0;
                    }
                    if(foo==0){ //Es un espacio o palabra
                        if(splited[0].equals("")){//espacio
                           
                        }else{//palabra
                            String texto="";
                            for(int i=0; i<splited.length;i++){
                            texto = texto+splited[i]+" ";
                            //System.out.println(texto);
                            }
                            
                            //subtitle.setTextfield(texto);
                        list.add( new Subtitle (                          
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                                
                        ));
                            
                            
                            
                        }
                        

                    }else{ //es un numero
                        //numeros.add(foo);

                        numero = foo;
                        subtitle.setId(foo);
                    }

                }else if (splited.length >=2 ){//si son las palabras o los tiempos
                    
                    String texto= "";
                    
                    
                    
                    if (splited.length>=2 && splited[1].equals("-->")){ //si el 2 elemento es una flecha se trata de los tiempos
                       
                        subtitle.setInicio(splited[0]);
                        subtitle.setTermino(splited[2]);
                    }else if(splited.length>=1 ){ //caso contrario son las palabras
                         //System.out.println(splited[0]);
                        for(int i=0; i<splited.length;i++){
                            texto = texto+splited[i]+" ";
                            //System.out.println(texto);
                        }
    //                    System.out.println("Id: "+subtitle.getId());
    //                    System.out.println("Inicio: "+subtitle.getInicio());
    //                    System.out.println("Fin: "+subtitle.getTermino());
    //                    System.out.println("Final texto: "+texto);
                        subtitle.setTexto(texto);
                        list.add( new Subtitle (
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                        ));





                    }

                }



                } //fin while
            
            tableViewCod.setItems(list); //muestro los items
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("CodParSeleccionar1")
                    + dialogBundle.getString("CodParaSeleccionar2"));

            alert.showAndWait();
            
            
        }else{ //si no esta vacio
            //System.out.println("no esta vacio");
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("Seguro_guardar"));
            Optional <ButtonType> action = alert.showAndWait();
            
            if(action.get()==ButtonType.OK){
                if (!path.endsWith(".coded")){
                    tableViewCoding.getItems().clear();//Elimina los elementos de la tabla secundaria cuando es .srt
                    comboCodigo.getItems().clear();//Se elimina lo que tiene el combobox de codigos
                    comboDeleteCode.getItems().clear();//Se elimina lo que tiene el combobox de eliminar codigos
                    nameOfCode.clear();//Elimina de la lista los elementos que tenia
                    colorOfCode.clear();//Elimina de la lista los elementos que tenia
                    id_coding = 0;
                }else{//Si es .coding
                    loadCodes(pathCodes);//Carga los codigos del archivo
                    loadTable(pathTable);

                }
                
                tableViewCod.getItems().clear();//Elimina los elementos de tabla principal
                //tableViewCoding.getItems().clear();
                    
                 TextFieldTableCell text = new TextFieldTableCell();
                //System.out.println("vacio");
                tableColumnMinuto.setCellValueFactory(new PropertyValueFactory <Subtitle, Integer>("id"));
                tableColumnTexto.setCellValueFactory(new PropertyValueFactory <Subtitle, String>("textfield"));
                tableViewCod.setFixedCellSize(50);
                
//                tableViewCod.setEditable(true);
//                tableColumnTexto.setCellFactory(TextFieldTableCell.forTableColumn());

                while ((st = br.readLine()) != null) {

                    String[] splited = st.split("\\s+");//corto 
                    //Arreglar esta parte, para saber cuando es numero
                    if (splited.length==1){//si es un espacio o el numero de subtitulo
                        
                        try {
                            foo = Integer.parseInt(splited[0]);
                        }
                        catch (NumberFormatException e)
                        {
                            foo = 0;
                        }
                        if(foo==0){ //Es un espacio o palabra de 1
                            if(splited[0].equals("")){//espacio
                           
                            }else{//palabra
                                String texto="";
                                for(int i=0; i<splited.length;i++){
                                texto = texto+splited[i]+" ";
                                //System.out.println(texto);
                                }

                                subtitle.setTexto(texto);
                            list.add( new Subtitle (
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                        ));

                            }
                            

                        }else{ //es un numero
                            //numeros.add(foo);

                            numero = foo;
                            subtitle.setId(foo);
                        }

                    }else if (splited.length >=2) {//si son las palabras o los tiempos
                        String texto= "";
                        if (splited.length>=2 && splited[1].equals("-->")){ //si el 2 elemento es una flecha se trata de los tiempos
                            subtitle.setInicio(splited[0]);
                            subtitle.setTermino(splited[2]);
                        }else { //caso contrario son las palabras
                            for(int i=0; i<splited.length;i++){
                                texto = texto+splited[i]+" ";
                                //System.out.println(texto);
                            }
        //                    System.out.println("Id: "+subtitle.getId());
        //                    System.out.println("Inicio: "+subtitle.getInicio());
        //                    System.out.println("Fin: "+subtitle.getTermino());
        //                    System.out.println("Final texto: "+texto);
                            subtitle.setTexto(texto);
                            list.add(new Subtitle (
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                            ));
                            
                        }

                    }



                    } //fin while
            tableViewCod.setItems(list); //muestro los items
                        // Create a new RowFactory to handle actions
            
            
            
//            tableColumnTexto.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent t) {
//                if(t.getButton() == MouseButton.SECONDARY) {
//                    
//                    System.out.println("Hola");
//                }
//            }
//            });
            
            }else{//si presiona cancelar no hace nada
                
            }
            
        } 
        
    }

    @FXML
    private void createdCoide(MouseEvent event) {
        if(!textFieldNameCode.getText().equals("")){
            nameOfCode.add(textFieldNameCode.getText());
            colorOfCode.add("#"+colorPicker.getValue().toString().substring(2,8));//#FFFFFF
            
            //System.out.println("Con substring"+ colorPicker.getValue().toString().substring(2,8));
            ObservableList<String> list = FXCollections.observableArrayList();
            list.addAll(nameOfCode);
            comboCodigo.setItems(list);//Se agrega al codigo generar
            comboDeleteCode.setItems(list);//Se agrega los elementos a comboeleminar
            //System.out.println("Nombre: "+textFieldNameCode.getText() + " y "+colorPicker.getValue());
            textFieldNameCode.clear();
            colorPicker.setValue(Color.WHITE);
            labelCoding.setText("");
        
        
        }else if(textFieldNameCode.getText().equals("")){
            labelCoding.setText(dialogBundle.getString("CodDebeIngresarNombreCodigo"));
        }
    }

    @FXML
    private void deleteCode(MouseEvent event) {
        ObservableList<String> list = FXCollections.observableArrayList();
        boolean isMyComboBoxEmpty = comboDeleteCode.getSelectionModel().isEmpty();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(dialogBundle.getString("CodSeEliminaranCodigo"));
        alert.setContentText(dialogBundle.getString("CodEstasSeguro"));

        Optional<ButtonType> result = alert.showAndWait();
        
        
        
        
        
        if(!isMyComboBoxEmpty){//Comprueba que no este vacio el combobox
            //for(int i=0; i<nameOfCode.size();i++){
            if (result.get() == ButtonType.OK){
            
//if(comboDeleteCode.getSelectionModel().getSelectedItem().toString().equals(nameOfCode.get(i))){
            int i=0; 
                
            while(i<nameOfCode.size()){
                if(nameOfCode.get(i).equals(comboDeleteCode.getSelectionModel().getSelectedItem().toString())){//Si hay elementos en la tabla
                    //listCoding.remove(i);//remover desde la lista el elemento
                    int j=0;
                    while(j<listCoding.size()){
                        if(listCoding.get(j).getNombre().equals(comboDeleteCode.getSelectionModel().getSelectedItem().toString())){
                            listCoding.remove(j);//remover desde la lista el elemento
                            j=0;
                        }else{
                            j++;
                        }
                    
                    }
                    
                    //Obtener el color y buscar en la lista de la tabla el color y saber
                    for (Subtitle bean : this.list) {
                        while((bean.getTexto().contains(colorOfCode.get(i)))){
//                            System.out.println(bean.getTexto());
                            int indexInicioEtiqueta = bean.getTexto().indexOf("<span style='background :"+colorOfCode.get(i)+"'>");
                            
                            
                            int indexFinEtiqueta = bean.getTexto().indexOf("</span>", indexInicioEtiqueta);
//                            
//                            System.out.println("Inicio etieuqta: "+ indexInicioEtiqueta);
//                            System.out.println("Fin etieuqta: "+ indexFinEtiqueta);
//                            
                            String texto = bean.getTexto().substring(indexInicioEtiqueta+34, indexFinEtiqueta);
                            String inicioTexto = bean.getTexto().substring(0,indexInicioEtiqueta);
                            String finTexto = bean.getTexto().substring(indexFinEtiqueta+7, bean.getTexto().length());
                            String etiqueta = inicioTexto+texto +finTexto;
                            
                            
                            bean.setTexto(etiqueta);
        //                    
                            bean.setTextfield(bean.getTextfield());
                            
//                            System.out.println("Inicio: "+inicioTexto);
//                            System.out.println("Palabra: "+texto);
//                            System.out.println("Fin: "+finTexto);
                        }
                    }
                    
                    
                    nameOfCode.remove(i);//se elimina el nombre de la lista
                    colorOfCode.remove(i);//se elimina el color de la lista
                    
                    //System.out.println("Nombre codigo: "+listCoding.get(i).getNombre());
                    i=0;
                }else{
                    i++;
                }
            
            
            }    
                
                
//                    for(int j =0; j<listCoding.size();j++){
//                        if(listCoding.get(j).getNombre().equals(comboDeleteCode.getSelectionModel().getSelectedItem().toString())){//Si hay elementos en la tabla
//                            listCoding.remove(j);//remover desde la lista el elemento
//                            nameOfCode.remove(j);//se elimina el nombre de la lista
//                            colorOfCode.remove(j);//se elimina el color de la lista
//                            list.addAll(nameOfCode);//se setea la nueva lista con los eleentos eliminados
//                            comboCodigo.setItems(list);//Se agrega al codigo generar
//                            comboDeleteCode.setItems(list);//Se agrega los elementos a comboeleminar
//                            comboDeleteCode.getSelectionModel().selectFirst();//Se selecciona el primero de la lsita eliminar, para no producir error
//
//                            System.out.println("Nombre codigo: "+listCoding.get(j).getNombre());
//                            j=0;
//                        }
//                    
//                    }
                    //System.out.println(listCoding);
                    tableViewCoding.setItems(listCoding);//Se actualiza la lista
                    list.addAll(nameOfCode);//se setea la nueva lista con los eleentos eliminados combo
                    comboCodigo.setItems(list);//Se agrega al codigo generar
                    comboDeleteCode.setItems(list);//Se agrega los elementos a comboeleminar
                    comboDeleteCode.getSelectionModel().selectFirst();//Se selecciona el primero de la lsita eliminar, para no producir error

                    labelCoding.setText("");
                //}
            //}
            }else {
                //nO HACE NADA
            
            }
        }else{
            labelCoding.setText(dialogBundle.getString("CodSeleccionarCodEliminar"));
        }
    }
    
    private void append(String inicio,String viejo,String msg, String fin) {
        
//        Document doc = web.getEngine().getDocument();
//        Element el = doc.getElementById("root");
//        String s = el.getTextContent();
        
        web.getEngine().loadContent(inicio + viejo + msg + fin);//Funciona
        //el.setTextContent(viejo + msg);
        
    }

    @FXML
    private void close(ActionEvent event) {
        String selection = (String) web.getEngine()
                     .executeScript("window.getSelection().toString()");
        System.out.println(selection);
        //String s="<p id='root'><span style='background : rgba(255,0,0,0.3); '>B</span><span style='background : rgba(0,255,0,0.3);'>casi</span></p>";  
        //append("<p><span style='background : rgba(255,0,0,0.3); '>chao</span></p>");
        //append("");
    }
    
    public void setEnableBtn() {
        btnAddCodigo.setDisable(false);
        btnUploadDoc.setDisable(false);
        btnCreatedCod.setDisable(false);
        btnDeleteCode.setDisable(false);
    
    
    
    
    }
    void setListViewClear() {
        listViewDocs.getItems().clear();
        
    
    }
    
    public void uploadFileSearch(String pathFile) throws FileNotFoundException, IOException{
        String path = pathFile;//Se obtiene el documento seleccionado
        pathTemporalSubtitle = path;
        
        //Subtitle sub = new Subtitle();
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st; 
        List<Integer> numeros = new ArrayList<>();
        int foo;
        int numero;
        String pathCodes = "";
        String pathTable = "";
        String pathOriginal = "";
        
            
        if(path.endsWith(".coded")){//Si termina en .coding es porque es un archivo con codigos
            
            
            pathOriginal = path.substring(0, path.lastIndexOf(".coded"));
            pathCodes = pathOriginal + ".codes";
            pathTable = pathOriginal + ".table";
            
            
            
            
        
        }else{//Si es un archivo .srt y se habia precargado un archivo .coding
            //Se deben eliminar los codigos de combobox
            
        
        
        }
        
        
        
        
        
        
        if (tableViewCod.getItems().isEmpty()){
            if (!path.endsWith(".coded")){
                    tableViewCoding.getItems().clear();//Elimina los elementos de la tabla secundaria cuando es .srt
                    comboCodigo.getItems().clear();//Se elimina lo que tiene el combobox de codigos
                    comboDeleteCode.getItems().clear();//Se elimina lo que tiene el combobox de eliminar codigos
                    nameOfCode.clear();//Elimina de la lista los elementos que tenia
                    colorOfCode.clear();//Elimina de la lista los elementos que tenia
                    id_coding = 0;
            }else{//Si es .coding
                loadCodes(pathCodes);//Carga los codigos del archivo
                loadTable(pathTable);
            
            }
            
            TextFieldTableCell text = new TextFieldTableCell();
            //System.out.println("vacio");
            tableColumnMinuto.setCellValueFactory(new PropertyValueFactory <Subtitle, Integer>("id"));
            tableColumnTexto.setCellValueFactory(new PropertyValueFactory <Subtitle, String>("textfield"));
            tableViewCod.setFixedCellSize(50);
            
            //tableColumnTexto.setGraphic(text);
           

//            tableViewCod.setEditable(true);
//            tableColumnTexto.setCellFactory(TextFieldTableCell.forTableColumn());
            
            
            
            

            while ((st = br.readLine()) != null) {

                String[] splited = st.split("\\s+");//corto 
                if (splited.length==1){//si es un espacio o el numero de subtitulo && (isNumber(splited[0]))!=-1
                    
                    try {
                        foo = Integer.parseInt(splited[0]);
                    }
                    catch (NumberFormatException e)
                    {
                        foo = 0;
                    }
                    if(foo==0){ //Es un espacio o palabra
                        if(splited[0].equals("")){//espacio
                           
                        }else{//palabra
                            String texto="";
                            for(int i=0; i<splited.length;i++){
                            texto = texto+splited[i]+" ";
                            //System.out.println(texto);
                            }
                            
                            //subtitle.setTextfield(texto);
                        list.add( new Subtitle (                          
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                                
                        ));
                            
                            
                            
                        }
                        

                    }else{ //es un numero
                        //numeros.add(foo);

                        numero = foo;
                        subtitle.setId(foo);
                    }

                }else if (splited.length >=2 ){//si son las palabras o los tiempos
                    
                    String texto= "";
                    
                    
                    
                    if (splited.length>=2 && splited[1].equals("-->")){ //si el 2 elemento es una flecha se trata de los tiempos
                       
                        subtitle.setInicio(splited[0]);
                        subtitle.setTermino(splited[2]);
                    }else if(splited.length>=1 ){ //caso contrario son las palabras
                         //System.out.println(splited[0]);
                        for(int i=0; i<splited.length;i++){
                            texto = texto+splited[i]+" ";
                            //System.out.println(texto);
                        }
    //                    System.out.println("Id: "+subtitle.getId());
    //                    System.out.println("Inicio: "+subtitle.getInicio());
    //                    System.out.println("Fin: "+subtitle.getTermino());
    //                    System.out.println("Final texto: "+texto);
                        subtitle.setTexto(texto);
                        list.add( new Subtitle (
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                        ));





                    }

                }



                } //fin while
            
            tableViewCod.setItems(list); //muestro los items
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("CodParSeleccionar1")
                    + dialogBundle.getString("CodParaSeleccionar2"));

            alert.showAndWait();
            
            
        }else{ //si no esta vacio
            //System.out.println("no esta vacio");
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("Seguro_guardar"));
            Optional <ButtonType> action = alert.showAndWait();
            
            if(action.get()==ButtonType.OK){
                if (!path.endsWith(".coded")){
                    tableViewCoding.getItems().clear();//Elimina los elementos de la tabla secundaria cuando es .srt
                    comboCodigo.getItems().clear();//Se elimina lo que tiene el combobox de codigos
                    comboDeleteCode.getItems().clear();//Se elimina lo que tiene el combobox de eliminar codigos
                    nameOfCode.clear();//Elimina de la lista los elementos que tenia
                    colorOfCode.clear();//Elimina de la lista los elementos que tenia
                    id_coding = 0;
                }else{//Si es .coding
                    loadCodes(pathCodes);//Carga los codigos del archivo
                    loadTable(pathTable);

                }
                
                tableViewCod.getItems().clear();//Elimina los elementos de tabla principal
                //tableViewCoding.getItems().clear();
                    
                 TextFieldTableCell text = new TextFieldTableCell();
                //System.out.println("vacio");
                tableColumnMinuto.setCellValueFactory(new PropertyValueFactory <Subtitle, Integer>("id"));
                tableColumnTexto.setCellValueFactory(new PropertyValueFactory <Subtitle, String>("textfield"));
                tableViewCod.setFixedCellSize(50);
                
//                tableViewCod.setEditable(true);
//                tableColumnTexto.setCellFactory(TextFieldTableCell.forTableColumn());

                while ((st = br.readLine()) != null) {

                    String[] splited = st.split("\\s+");//corto 
                    //Arreglar esta parte, para saber cuando es numero
                    if (splited.length==1){//si es un espacio o el numero de subtitulo
                        
                        try {
                            foo = Integer.parseInt(splited[0]);
                        }
                        catch (NumberFormatException e)
                        {
                            foo = 0;
                        }
                        if(foo==0){ //Es un espacio o palabra de 1
                            if(splited[0].equals("")){//espacio
                           
                            }else{//palabra
                                String texto="";
                                for(int i=0; i<splited.length;i++){
                                texto = texto+splited[i]+" ";
                                //System.out.println(texto);
                                }

                                subtitle.setTexto(texto);
                            list.add( new Subtitle (
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                        ));

                            }
                            

                        }else{ //es un numero
                            //numeros.add(foo);

                            numero = foo;
                            subtitle.setId(foo);
                        }

                    }else if (splited.length >=2) {//si son las palabras o los tiempos
                        String texto= "";
                        if (splited.length>=2 && splited[1].equals("-->")){ //si el 2 elemento es una flecha se trata de los tiempos
                            subtitle.setInicio(splited[0]);
                            subtitle.setTermino(splited[2]);
                        }else { //caso contrario son las palabras
                            for(int i=0; i<splited.length;i++){
                                texto = texto+splited[i]+" ";
                                //System.out.println(texto);
                            }
        //                    System.out.println("Id: "+subtitle.getId());
        //                    System.out.println("Inicio: "+subtitle.getInicio());
        //                    System.out.println("Fin: "+subtitle.getTermino());
        //                    System.out.println("Final texto: "+texto);
                            subtitle.setTexto(texto);
                            list.add(new Subtitle (
                            texto,
                            subtitle.getInicio(),
                            subtitle.getTermino(),
                            subtitle.getId()
                            ));
                            
                        }

                    }



                    } //fin while
            tableViewCod.setItems(list); //muestro los items
                        // Create a new RowFactory to handle actions
            
            
            
//            tableColumnTexto.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent t) {
//                if(t.getButton() == MouseButton.SECONDARY) {
//                    
//                    System.out.println("Hola");
//                }
//            }
//            });
            
            }else{//si presiona cancelar no hace nada
                
            }
            
        }
    }

    @FXML
    private void saveFile(ActionEvent event) {
        String pathFinal = "";
        String pathCodes = "";
        String pathTable = "";
        String nombreArchivo = "";
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(dialogBundle.getString("CodNameOfFile"));
        dialog.setHeaderText(dialogBundle.getString("CodIngresaEnElCampo"));
        dialog.setContentText(dialogBundle.getString("CodElegirNombre"));

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            
            //System.out.println("Nombre: "+ nameOfCode.get(0)+ "Codigo: "+colorOfCode.get(0) );
            
            nombreArchivo = result.get();
            
            pathFinal = pathTemporalSubtitle.substring(0, pathTemporalSubtitle.lastIndexOf("\\")) +"\\" +nombreArchivo + ".coded";
            pathCodes = pathTemporalSubtitle.substring(0, pathTemporalSubtitle.lastIndexOf("\\")) +"\\" +nombreArchivo + ".codes";
            pathTable = pathTemporalSubtitle.substring(0, pathTemporalSubtitle.lastIndexOf("\\")) +"\\" +nombreArchivo + ".table";
            
            
            
            
            //archivo con la ubicacion donde esta el mismo de trasncribir .code
            
            try {
                saveCodes(pathCodes);
                saveTableCodes(pathTable);
                
                FileWriter fileWrite = new FileWriter (pathFinal);
            
            
            for (int i=0; i<list.size();i++){
                fileWrite.write(list.get(i).getId()+"");//pasar el entero a string
                fileWrite.write("\n");
                fileWrite.write(list.get(i).getInicio());
                fileWrite.write(" --> ");
                fileWrite.write(list.get(i).getTermino());
                fileWrite.write("\n");
                fileWrite.write(list.get(i).getTexto());
                fileWrite.write("\n");
                fileWrite.write("\n");
            
            }
            
            fileWrite.close();//
            
            } catch (IOException ex) {
                Logger.getLogger(LabelTextViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else{//aCA PONER QE NO SE GUARDO
        
        }
        
        
        
        
//        if(pathTemporalSubtitle.endsWith(".srt")){
//            //System.out.println(pathTemporalSubtitle.substring(0, pathTemporalSubtitle.lastIndexOf("\\")));
//            pathFinal = pathTemporalSubtitle.substring(0, pathTemporalSubtitle.lastIndexOf("\\")) +"\\" +nombreArchivo + ".code";
//            
//        }else{//si termina en .code no hacer nada
//            pathFinal = pathTemporalSubtitle.substring(0, pathTemporalSubtitle.lastIndexOf("\\")) +"\\" +nombreArchivo + ".code";
//        }
        
        
    }
    
    private void saveCodes(String pathArchivoCodificacion){
        
        try {
            FileWriter fileWrite = new FileWriter (pathArchivoCodificacion);//Archivos con los codigos
            for (int i=0; i<nameOfCode.size();i++){
                
                fileWrite.write(nameOfCode.get(i));
                fileWrite.write(",");
                fileWrite.write(colorOfCode.get(i));
                fileWrite.write("\n");
            
            }
            
            fileWrite.close();//
            
            } catch (IOException ex) {
                Logger.getLogger(LabelTextViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void saveTableCodes(String pathArchivoCodificacion){
        
        
        try {
            FileWriter fileWrite = new FileWriter (pathArchivoCodificacion);//Archivos con los codigos
            for (int i=0; i<listCoding.size();i++){
                System.out.println();
                fileWrite.write(listCoding.get(i).getId()+"");
                fileWrite.write(",");
                fileWrite.write(listCoding.get(i).getId_subtitle()+"");
                fileWrite.write(",");
                fileWrite.write(listCoding.get(i).getNombre());
                fileWrite.write(",");
                fileWrite.write(listCoding.get(i).getColor());
                fileWrite.write(",");
                fileWrite.write(listCoding.get(i).getPathDocumento());
                fileWrite.write(",");
                fileWrite.write(listCoding.get(i).getPalabra());
                fileWrite.write("\n");
            
            }
            
            fileWrite.close();//
            
        } catch (IOException ex) {
            Logger.getLogger(LabelTextViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    //Metodo que permite cargar los codigos existentes en archivos guardados con extension .codes
    private void loadCodes(String path) throws FileNotFoundException, IOException{
        comboCodigo.getItems().clear();//Se elimina lo que tiene el combobox de codigos
        comboDeleteCode.getItems().clear();//Se elimina lo que tiene el combobox de eliminar codigos
        nameOfCode.clear();//Elimina de la lista los elementos que tenia
        colorOfCode.clear();//Elimina de la lista los elementos que tenia
        
        
        
        File file = new File(path);
        String name = file.getName();
        String[] split = name.split("\\.");
        
        String nameOfFile = split[0] +"."+split[1];//nombre del archivo de audio
        
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        //Subtitle sub = new Subtitle();
        String st; 
        
        
        
        while ((st = br.readLine()) != null) {
            String[] splited = st.split(",");//corto 
            nameOfCode.add(splited[0]);
            colorOfCode.add(splited[1]);
        
        
        }
        
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(nameOfCode);//Se agregan los nombres de codigo a la lista
        comboCodigo.setItems(list);//Se agrega al codigo generar
        comboDeleteCode.setItems(list);//Se agrega los elementos a comboeleminar
        
    
    }
    
    private void loadTable(String path) throws FileNotFoundException, IOException{
        //listCoding.clear();//Elimina los elementos de la lista
        
        
        File file = new File(path);
        String name = file.getName();
        String[] split = name.split("\\.");
        
        String nameOfFile = split[0] +"."+split[1];//nombre del archivo de audio
        
        ObservableList<Coding> listCode = FXCollections.observableArrayList();
        BufferedReader br = new BufferedReader(new FileReader(file));
        //Subtitle sub = new Subtitle();
        String st; 
        int numeroFilas = 0;
        
        
        if (tableViewCoding.getItems().isEmpty()){
            System.out.println(" vacio");
        
            idColumn.setCellValueFactory(new PropertyValueFactory <Coding, Integer>("id"));
            idSubColumn.setCellValueFactory(new PropertyValueFactory <Coding, Integer>("id_subtitle"));
            nameCodeColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("nombre"));
            colorColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("color"));
            documentColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("pathDocumento"));
            wordColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("palabra"));
            
            
            while ((st = br.readLine()) != null) {
                
                String[] splited = st.split(",");//corto 
//                System.out.println("Split: "+splited);
//                System.out.println("Vacio: "+splited[0]+" " + splited[1]+" "+ splited[2]+ " "+splited[3]+ " "+ splited[4]+ " "+ splited[5]);
                coding.setId(Integer.parseInt(splited[0]));
                coding.setId_subtitle(Integer.parseInt(splited[1]));
                coding.setNombre(splited[2]);
                coding.setColor(splited[3]);
                coding.setPathDocumento(splited[4]);
                coding.setPalabra(splited[5]);
                
                
                listCoding.add(new Coding(
                        coding.getId(),
                        coding.getId_subtitle(),
                        coding.getNombre(),
                        coding.getColor(),
                        coding.getPathDocumento(),
                        coding.getPalabra()
                ) );

                numeroFilas++;


            }//Fin while
            id_coding = numeroFilas;
            tableViewCoding.setItems(listCoding);
            
        }  
        else{
            System.out.println("no vacio");
            tableViewCoding.getItems().clear();//Elimina los elementos de la tabla
            idColumn.setCellValueFactory(new PropertyValueFactory <Coding, Integer>("id"));
            idSubColumn.setCellValueFactory(new PropertyValueFactory <Coding, Integer>("id_subtitle"));
            nameCodeColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("nombre"));
            colorColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("color"));
            documentColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("pathDocumento"));
            wordColumn.setCellValueFactory(new PropertyValueFactory <Coding, String>("palabra"));
            
            
            
            while ((st = br.readLine()) != null) {
                
                String[] splited = st.split(",");//corto 
//                System.out.println("Split: "+splited);
//                System.out.println("Vacio: "+splited[0]+" " + splited[1]+" "+ splited[2]+ " "+splited[3]+ " "+ splited[4]+ " "+ splited[5]);
                coding.setId(Integer.parseInt(splited[0]));
                coding.setId_subtitle(Integer.parseInt(splited[1]));
                coding.setNombre(splited[2]);
                coding.setColor(splited[3]);
                coding.setPathDocumento(splited[4]);
                coding.setPalabra(splited[5]);
                
                
                listCoding.add(new Coding(
                        coding.getId(),
                        coding.getId_subtitle(),
                        coding.getNombre(),
                        coding.getColor(),
                        coding.getPathDocumento(),
                        coding.getPalabra()
                ) );


                numeroFilas++;
                
            }//Fin while
            id_coding = numeroFilas;
            tableViewCoding.setItems(listCoding);
            
            
        
        }
    
    
    }
    
    public void selectRowTable(int index, String pathFileSelect){
        
        if(tableViewCod.getItems().isEmpty()){//Si esta vacia la tabla, mensaje de que carge documento
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("CodLatablaseencuentravacia"));

            alert.showAndWait();
        
        }else if(!pathFileSelect.equals(pathTemporalSubtitle)){//Si son distintos, mostrar mensaje de que no se puede
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(dialogBundle.getString("CodFilaNoContenida"));

            alert.showAndWait();
        
        
        }else{
            
            
            tableViewCod.requestFocus();//Selecciona la tabla
            tableViewCod.getSelectionModel().select(index);//selecciona la fila
            tableViewCod.getFocusModel().focus(index);//seleccionada el item para que se marque azul
            
            tableViewCod.scrollTo(index);//Baja el scroll para ir al item seleccionado
            
            
            
        }
        
        
    
    }
    
    
    
    
}
