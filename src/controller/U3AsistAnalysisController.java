/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javax.sound.sampled.UnsupportedAudioFileException;
import mo.core.plugin.Plugin;
import mo.core.plugin.PluginRegistry;
import org.apache.lucene.queryparser.classic.ParseException;
import controller.ClougTagsAsistAnalysisController;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.FileUtils;


/**
 * FXML Controller class
 *
 * @author Lathy
 */
public class U3AsistAnalysisController implements Initializable {

    private String ventanaActiva;
    @FXML
    private Button btnEtiquetado;
    
    @FXML
    private Button btnNube;
    @FXML
    private BorderPane borderPaneCambio;
    @FXML
    private Button btnClose;
    @FXML
    private BorderPane borderPaneSearch;
    
    List<String> listofFiles = new ArrayList<String>();
    
    final List<BorderPane> border = new ArrayList<BorderPane>();
    List<Object> controllers = new ArrayList<>();
    Parent root=null;
    Parent rootSearch=null;
    @FXML
    private Button btnAddDocuments;
    @FXML
    private ListView listViewDocs;
    @FXML
    private Label labelDocs;
    private List<File> listFileDocsDisponible = new ArrayList<>();
    private List<File> listFileDocsTemporal = new ArrayList<>();

    ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("properties/principal");
    @FXML
    private Text textDocumentosDisponibles;
    @FXML
    private Text textEsteAsistente;
    @FXML
    private Text textCod;
    @FXML
    private Text textNube;
    @FXML
    private Text textDefinicionCod;
    @FXML
    private Text textDefinicionNube;
    @FXML
    private Text textBienvenido;
    @FXML
    private Text textTitle;
    private String pathAnalisisFolder;
    List<Plugin> listaDePluginsAnalisis = new ArrayList<>();//Todos los plugin de analisis transcription
    Plugin wordCloudPlugin, searchPlugin, codingPlugin;
    List<Method> methods;
    
    Class<?> classControllerWordCloud, classControllerCoding, classControllerSearch;
    
    Object instance;
    Object instanceCloud, instanceCoding, instanceSearch;
    
    
    Object controllerCloud = null;
    
    int pluginWordCloud =0;
    int pluginCoding = 0;
    int pluginSearch = 0;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //cargarVentanas();
        cargarIdioma();
        initAnalisisList();
    }    

    
    public void initAnalisisList(){
        
        listaDePluginsAnalisis.clear();
        List<Plugin> pluginAnalisisTodos = new ArrayList<>();
        List<Plugin> listaDePlugins = PluginRegistry.getInstance().getPluginsFor("mo.analysis.AnalysisProvider");
        //List<Plugin> listaDePluginsAnalisis = new ArrayList<>();//Todos los plugin de analisis transcription
        for (Plugin plugin : listaDePlugins ){//Todos los plugin
            //System.out.println("Nombre plugin: "+plugin.getPath().toString() );
            //System.out.println("Nombre plugin: "+plugin.getId() );
            //System.out.println("Nombre plugin: "+plugin.getPath().toString() );
            if(plugin.getPath().toString().contains("WordCloud")){
                //System.out.println("Nombre WordCloud: "+plugin.getPath().toString() );
                wordCloudPlugin=plugin;
                
            }else if(plugin.getPath().toString().contains("Coding")){    
                codingPlugin=plugin;
                
                
            }else if(plugin.getPath().toString().contains("Search")){    
                searchPlugin=plugin;
                
                
            }else{
            
            }
        }
        
        //System.out.println("Nombre final del plguin"+wordCloudPlugin.getPath());
        
            
            //System.out.println("Tmaano de la lsita: ------- "+listaDePluginsAnalisis.size());
       
        
        
    
    
    }
    
    
    public void cargarIdioma(){
        textTitle.setText(dialogBundle.getString("tittle_windows"));
        btnClose.setText(dialogBundle.getString("U3ControllerbtnHome"));
        btnEtiquetado.setText(dialogBundle.getString("U3ControllerbtnCod"));
        btnNube.setText(dialogBundle.getString("U3ControllerbtnNube"));
        btnAddDocuments.setText(dialogBundle.getString("U3ControllerbtnAgregar"));
        textBienvenido.setText(dialogBundle.getString("U3ControllerTextBienvenido"));
        textEsteAsistente.setText(dialogBundle.getString("U3ControllerTextEsteasistente"));
        textCod.setText(dialogBundle.getString("U3ControllerTextCod"));
        textNube.setText(dialogBundle.getString("U3ControllerTextNube"));
        textDefinicionCod.setText(dialogBundle.getString("U3ControllerTextDefCod"));
        textDefinicionNube.setText(dialogBundle.getString("U3ControllerTextDefNube"));
        
        
    
    
    }
    
    
    @FXML
    private void etiquetadoAnalisis(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(dialogBundle.getString("U3ControllerEstas_salir"));
        Optional <ButtonType> action = alert.showAndWait();
        
        
        if((action.get()==ButtonType.OK) && pluginCoding==0 && pluginSearch==0){
            labelDocs.setText("");
            listFileDocsTemporal.clear();
            ventanaActiva = "codificacion";
            btnAddDocuments.setDisable(false);//Se desactiva el boton de agregar documentos en home
            root = border.get(1);//Se obtiene el root de label
            rootSearch = border.get(3);//Se obtiene el root de search



    //        LabelTextViewController controllerLabel = (LabelTextViewController)controllers.get(0);//Se obtiene controlador codificacion
    //        QueryTextController controllerSearch = (QueryTextController)controllers.get(2);//Se obtiene controlador buscador
    //        controllerLabel.set_ListFileTemporal(listFileDocsTemporal);//SE PASA LOS DOCUMENTOS


            borderPaneSearch.setVisible(true);
            borderPaneCambio.setCenter(root);
            borderPaneSearch.setCenter(rootSearch);


            //loadUI("/view/LabelTextView");
            btnEtiquetado.setStyle("-fx-background-color: #404040; -fx-text-fill:  #ffffff;-fx-border-color:black; ");
            //demas ventanas

            btnNube.setStyle("-fx-background-color: transparent; -fx-text-fill:  #ffffff; ");
            btnClose.setStyle("-fx-background-color: transparent; -fx-text-fill:  #ffffff; ");
        
        
        }else{//Si pongo cancel
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Información");
            alert1.setHeaderText(null);
            alert1.setContentText(dialogBundle.getString("PluginNoCargadoCod") + dialogBundle.getString("PluginNoCargadoSearch"));

            alert1.showAndWait();
        
        }
        
        
        
        
        
    }

    

    @FXML
    private void nubepalabrasAnalisis(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(dialogBundle.getString("U3ControllerEstas_salir"));
        Optional <ButtonType> action = alert.showAndWait();
        
        
        if((action.get()==ButtonType.OK) && pluginWordCloud==0){
            labelDocs.setText("");
            listFileDocsTemporal.clear();
            ventanaActiva = "nube";
            btnAddDocuments.setDisable(false);//Se desactiva el boton de agregar documentos en home
            root = border.get(2);//Se obtiene el root de nube palabras



            borderPaneCambio.setCenter(root);
            borderPaneSearch.setVisible(false);
            borderPaneSearch.setCenter(null);
            //labelDocs.setText("Documentos cargados nube palabras:");

            //loadUI("/view/ClougTagsAsistAnalysis");
            btnNube.setStyle("-fx-background-color: #404040; -fx-text-fill:  #ffffff; -fx-border-color:black;");
            //demas ventanas
            btnEtiquetado.setStyle("-fx-background-color: transparent; -fx-text-fill:  #ffffff; ");

            btnClose.setStyle("-fx-background-color: transparent; -fx-text-fill:  #ffffff; ");
            
        }else{
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Información");
            alert1.setHeaderText(null);
            alert1.setContentText(dialogBundle.getString("PluginNoCargadoWord"));

            alert1.showAndWait();
            
            
        }
        
        

        
    }
    
    @FXML
    private void cerrarVentana(MouseEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(dialogBundle.getString("U3ControllerIr_a_ventana_principal"));
        Optional <ButtonType> action = alert.showAndWait();
        
        
        if(action.get()==ButtonType.OK){
            labelDocs.setText("");
            listFileDocsTemporal.clear();
            ventanaActiva = "home";
            btnAddDocuments.setDisable(true);//Se desactiva el boton de agregar documentos en home
            root = border.get(0);//Se obtiene el root de home
//            --------------- aca llamar al ManagementCloud invoke y que retorne el border del initfxml
            borderPaneCambio.setCenter(root);
            borderPaneSearch.setVisible(false);
            borderPaneSearch.setCenter(null);
            //System.out.println(listofFiles.size());
    //        borderPaneCambio.setCenter(null);
    //        borderPaneSearch.setCenter(null);

            btnClose.setStyle("-fx-background-color: #404040; -fx-text-fill:  #ffffff; -fx-border-color:black; ");
            //demas ventanas
            btnEtiquetado.setStyle("-fx-background-color: transparent; -fx-text-fill:  #ffffff; ");

            btnNube.setStyle("-fx-background-color: transparent; -fx-text-fill:  #ffffff; ");
        
        
        }else{
        
        }
        
        
        
        
        
        
        
    }
    
    
    
    private void loadUI (String ui){
        Parent root = null;
        Parent rootSearch = null;
        try {
            root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
            rootSearch = FXMLLoader.load(getClass().getResource("/view/queryText.fxml"));
            
        } catch (IOException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ui.equals("/view/LabelTextView")){
            borderPaneCambio.setCenter(root);
            borderPaneSearch.setCenter(rootSearch);
            borderPaneSearch.setDisable(false);
        }else if(ui.equals("/view/ClougTagsAsistAnalysis")){
            borderPaneCambio.setCenter(root);
            borderPaneSearch.setDisable(true);
        }
        
        else{
            borderPaneCambio.setCenter(root);
        }
           
    }
    
    //Para cargar los archivos de la ventana de documentos
    public void set_List(List<String> listFile){
        this.listofFiles = listFile;
        
        
    }
    
    
    
    public void cargarVentanas(){
        try {
            
            
            
            
            
            FXMLLoader loaderHome = new FXMLLoader(getClass().getResource("/view/HomeAsistAnalysis.fxml"));
            Parent rootHome = loaderHome.load();
           
            
//            FXMLLoader loaderlabel = new FXMLLoader(getClass().getResource("/view/LabelTextView.fxml"));
//            LabelTextViewController lc = new LabelTextViewController();
//            loaderlabel.setController(lc);
//            Parent rootlabel = loaderlabel.load();
            
              FXMLLoader loaderlabel;
              Parent rootlabel = null;
              rootlabel = displayFXMLCoding(rootlabel);
            
            //------------------------------------------------------------------
            
//            FXMLLoader loaderCloud = new FXMLLoader(getClass().getResource("/view/ClougTagsAsistAnalysis.fxml"));
//            ClougTagsAsistAnalysisController cc = new ClougTagsAsistAnalysisController();
//            loaderCloud.setController(cc);
//            Parent rootCloud = loaderCloud.load();
//          -------------------------------------------------------------------  
            FXMLLoader loaderCloud;
            Parent rootCloud = null;
            rootCloud = displayFXMLWordCloud(rootCloud);
            //Object controllerCloud = null;
//            

            FXMLLoader loaderSearch;
            Parent rootSearchAsist = null;
            rootSearchAsist = displayFXMLSearchText(rootSearchAsist);


            
//            FXMLLoader loaderSearch = new FXMLLoader(getClass().getResource("/view/queryText.fxml"));
//            QueryTextController qc = new QueryTextController();
//            loaderSearch.setController(qc);
//            Parent rootSearchAsist = loaderSearch.load();
            
            
            
//                
////            
////            try{
////                URL jarUrl = new URL("file:///"+ wordCloudPlugin.getPath());//Permite cargar el .jar
////                URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl});//
////                //JarFile jar = new JarFile(""+plugin.getPath());
////                //System.out.println("Path: "+jarUrl.getPath());
////                
////                   
////                //URL url = new URL("file:///"+ wordCloudPlugin.getPath());
////                URL urlFXML = null;
////                String BUILD_PATH = wordCloudPlugin.getPath().toString();
////                ZipFile file1 = new ZipFile(wordCloudPlugin.getPath().toString());
////                System.out.println("Nombre del zip: "+file1.getName());
////                
////                if (file1 != null) {
////                   final Enumeration<? extends ZipEntry>  entries = file1.entries(); //get entries from the zip file...
////
////                   if (entries != null) {
////                      while (entries.hasMoreElements()) {
////                          
////                          ZipEntry entry = entries.nextElement();
////                          
////                          if(entry.getName().contains("ClougTagsAsistAnalysis.fxml")){
////                              System.out.println("Nombre: "+entry.getName());
////                              String nombre  = BUILD_PATH + "!/" + entry.getName();
////                              File file = new File(nombre);
////                              System.out.println("pATH NOMBREEE: "+file.getAbsolutePath());
////                              //urlFXML = new URL("file:///"+file1.getName()+"!/" + entry.getName());
////                              //urlFXML = entry.getClass().getResource("/" + entry.getName());
////                              
////                              //urlFXML = getClass().getResource("/"+entry.getName());
////                              //urlFXML = file1.getClass().getResource("/"+entry.getName());
////                                urlFXML = new URL("jar:file:" + nombre);
////                              
////                              
////                              System.out.println(urlFXML.getPath());
////                              
////                          }
////                          
////                          //use the entry to see if it's the file '1.txt'
////                          //Read from the byte using file.getInputStream(entry)
////                      }
////                    }
////                }
////                
////                
////                
////                //Busca la clase, indicar el packege en donde se encuentra mediante punto
////                //paquete1.paquete2.clase
////                classControllerWordCloud = Class.forName("controller.ClougTagsAsistAnalysisController", true, loader);
////                
////                Class[] paramInt = new Class[1];	
////                paramInt[0] = Integer.TYPE;
////                
////                Class[] paramList = new Class[1];	
////                paramList[0] = List.class;
////                
////                Class noparams[] = {};
////                Method method;
////                
////                
////                
////                
////                Constructor<?> ctorr = classControllerWordCloud.getConstructor();
////                instanceCloud = ctorr.newInstance();
////                
////                //instanceCloud = classControllerWordCloud.newInstance();
////                System.out.println("Clase: "+instanceCloud.getClass());
////                
////                
////                
////                //obtiene el metodo, y se indican los distintos parametros del metodo
//////                Class<?> atribute = classControllerWordCloud.getField("ct").getType();                    
//////                System.out.println("Atribute: "+ atribute);
////                 
////                FXMLLoader loadCloud1 = null;
////                
////                
////                
////                
////                loadCloud1 = new FXMLLoader(urlFXML);
////                loadCloud1.setController(instanceCloud);//Instancia de controlador de classCloudController
////                rootCloud = loadCloud1.load();
////
////
////
////
////            }catch (IllegalAccessException ex) {
////                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (IllegalArgumentException ex) {
////                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (InvocationTargetException ex) {
////                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (ClassNotFoundException ex) {
////                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (NoSuchMethodException ex) {
////                System.out.println("Ex: "+ex);
////                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (SecurityException ex) {
////                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (InstantiationException ex) {
////                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
////            }
                
                
                
                
//                
                
            
            
            
            
            
            
            
            
            
            
            
            
            
            border.add((BorderPane) rootHome);
            border.add((BorderPane) rootlabel);
            border.add((BorderPane) rootCloud);
            border.add((BorderPane) rootSearchAsist);
            
            
            
            controllers.add(instanceCoding);//posicion 0 label
            controllers.add(instanceCloud);//posicion 1 cloud
            controllers.add(instanceSearch);//posicion 2 search
//            

            //borderpanecenter.getChildren().add(border.get(0));
            
//            Scene scene = new Scene(borderpane);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Parent displayFXMLWordCloud(Parent rootCloud){
        Class[] paramInt = new Class[1];	
        paramInt[0] = Integer.TYPE;

        Class[] paramList = new Class[1];	
        paramList[0] = List.class;

        Class noparams[] = {};
        Method method;
            
        try {
            URL jarUrl = new URL("file:///"+ wordCloudPlugin.getPath());//Permite cargar el .jar
            URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl});//
            
            //Se carga la clase del .jar
            classControllerWordCloud = Class.forName("controller.ClougTagsAsistAnalysisController", true, loader);
            Constructor<?> ctorr = classControllerWordCloud.getConstructor();//Constructor
            instanceCloud = ctorr.newInstance();//Instancia de controladorCloudTags
            
            URL urlFXML = null;//Tendra la ubicacion del FXML del cloud
            String BUILD_PATH = wordCloudPlugin.getPath().toString();//Path del .jar
            ZipFile file1 = new ZipFile(wordCloudPlugin.getPath().toString());//Permite poder buscar archivos
            System.out.println("Nombre del zip: "+file1.getName());
            
            if (file1 != null) {
                final Enumeration<? extends ZipEntry>  entries = file1.entries(); //get entries from the zip file...
                
                if (entries != null) {
                    while (entries.hasMoreElements()) {//Hasta que haya elementos
                        
                        ZipEntry entry = entries.nextElement();
                        
                        if(entry.getName().contains("ClougTagsAsistAnalysis.fxml")){//Pregunta si es el FXML
                            System.out.println("Nombre: "+entry.getName());
                            String nombre  = BUILD_PATH + "!/" + entry.getName();//Se crea el path con el .jar!nombre.FXML
                            File file = new File(nombre);
                            System.out.println("pATH NOMBREEE: "+file.getAbsolutePath());
                            
                            urlFXML = new URL("jar:file:" + nombre);//Direccion del FXML en el .jar
                            
                            
                            System.out.println(urlFXML.getPath());
                            
                        }
                        
                        
                    }
                }
            }
            FXMLLoader loadCloud1;
            
            loadCloud1 = new FXMLLoader(urlFXML);
            loadCloud1.setController(instanceCloud);//Instancia de controlador de classCloudController
            rootCloud = loadCloud1.load();
            
            
        }catch(NullPointerException d){
            pluginWordCloud = 1;
            
        } catch (IOException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return rootCloud;
    }
    
    
    public Parent displayFXMLSearchText(Parent rootSearch){
        Class[] paramInt = new Class[1];	
        paramInt[0] = Integer.TYPE;

        Class[] paramList = new Class[1];	
        paramList[0] = List.class;

        Class noparams[] = {};
        Method method;
            
        try {
            URL jarUrl = new URL("file:///"+ searchPlugin.getPath());//Permite cargar el .jar
            URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl});//
            
            //Se carga la clase del .jar
            classControllerSearch = Class.forName("controller.QueryTextController", true, loader);
            Constructor<?> ctorr = classControllerSearch.getConstructor();//Constructor
            instanceSearch = ctorr.newInstance();//Instancia de controladorCloudTags
            
            URL urlFXML = null;//Tendra la ubicacion del FXML del cloud
            String BUILD_PATH = searchPlugin.getPath().toString();//Path del .jar
            ZipFile file1 = new ZipFile(searchPlugin.getPath().toString());//Permite poder buscar archivos
            System.out.println("Nombre del zip: "+file1.getName());
            
            if (file1 != null) {
                final Enumeration<? extends ZipEntry>  entries = file1.entries(); //get entries from the zip file...
                
                if (entries != null) {
                    while (entries.hasMoreElements()) {//Hasta que haya elementos
                        
                        ZipEntry entry = entries.nextElement();
                        
                        if(entry.getName().contains("queryText.fxml")){//Pregunta si es el FXML
                            System.out.println("Nombre: "+entry.getName());
                            String nombre  = BUILD_PATH + "!/" + entry.getName();//Se crea el path con el .jar!nombre.FXML
                            File file = new File(nombre);
                            System.out.println("pATH NOMBREEE: "+file.getAbsolutePath());
                            
                            urlFXML = new URL("jar:file:" + nombre);//Direccion del FXML en el .jar
                            
                            
                            System.out.println(urlFXML.getPath());
                            
                        }
                        
                        
                    }
                }
            }
            FXMLLoader loadSearch;
            
            loadSearch = new FXMLLoader(urlFXML);
            loadSearch.setController(instanceSearch);//Instancia de controlador de classCloudController
            rootSearch = loadSearch.load();
            
            Method setInstanceCoding = null;
            setInstanceCoding = classControllerSearch.getDeclaredMethod("setInstanceCoding", Class.class, Object.class);
            setInstanceCoding.invoke(instanceSearch, classControllerCoding, instanceCoding);
        
        
        }catch(NullPointerException d){
            pluginSearch = 1;    
            
        } catch (IOException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return rootSearch;
    }
   
    public Parent displayFXMLCoding(Parent rootCoding){
        Class[] paramInt = new Class[1];	
        paramInt[0] = Integer.TYPE;

        Class[] paramList = new Class[1];	
        paramList[0] = List.class;

        Class noparams[] = {};
        Method method;
            
        try {
            URL jarUrl = new URL("file:///"+ codingPlugin.getPath());//Permite cargar el .jar
            URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl});//
            
            //Se carga la clase del .jar
            classControllerCoding = Class.forName("controller.LabelTextViewController", true, loader);
            Constructor<?> ctorr = classControllerCoding.getConstructor();//Constructor
            instanceCoding = ctorr.newInstance();//Instancia de controladorCloudTags
            
            URL urlFXML = null;//Tendra la ubicacion del FXML del cloud
            String BUILD_PATH = codingPlugin.getPath().toString();//Path del .jar
            ZipFile file1 = new ZipFile(codingPlugin.getPath().toString());//Permite poder buscar archivos
            System.out.println("Nombre del zip: "+file1.getName());
            
            if (file1 != null) {
                final Enumeration<? extends ZipEntry>  entries = file1.entries(); //get entries from the zip file...
                
                if (entries != null) {
                    while (entries.hasMoreElements()) {//Hasta que haya elementos
                        
                        ZipEntry entry = entries.nextElement();
                        
                        if(entry.getName().contains("LabelTextView.fxml")){//Pregunta si es el FXML
                            System.out.println("Nombre: "+entry.getName());
                            String nombre  = BUILD_PATH + "!/" + entry.getName();//Se crea el path con el .jar!nombre.FXML
                            File file = new File(nombre);
                            System.out.println("pATH NOMBREEE: "+file.getAbsolutePath());
                            
                            urlFXML = new URL("jar:file:" + nombre);//Direccion del FXML en el .jar
                            
                            
                            System.out.println(urlFXML.getPath());
                            
                        }
                        
                        
                    }
                }
            }
            FXMLLoader loadCoding;
            
            loadCoding = new FXMLLoader(urlFXML);
            loadCoding.setController(instanceCoding);//Instancia de controlador de classCloudController
            rootCoding = loadCoding.load();
            
            
        }catch(NullPointerException d){
            pluginCoding = 1;    
            
        } catch (IOException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return rootCoding;
    }
    
    
    
    
    

    @FXML
    private void clickBtnAddDocuments(MouseEvent event) throws IOException, ParseException {
        String documents = "";
        listFileDocsTemporal.clear();
        
        
//        LabelTextViewController controllerLabel = (LabelTextViewController)controllers.get(0);//Se obtiene controlador codificacion
//        QueryTextController controllerSearch = (QueryTextController)controllers.get(2);//Se obtiene controlador buscador
//        controllerSearch.setControllerLabel(controllerLabel);
        
        
        
        //classControllerWordCloud = (Class<?>) controllers.get(1);
        
        //classControllerWordCloud = (Class<?>) controllers.get(1) ; //Controllador nube
        //Cloud
        Method method = null;
        
        //Coding
        Method set_ListFileTemporal = null;
        Method cargarDocumentosListView = null;
        Method setEnableBtn = null;
        
        
        //Search
        Method setPathTranscription = null;
        Method set_ListFileTemporalSearch = null;
        
        
        Class[] paramList = new Class[1];
        paramList[0] = List.class;
        
        Class noparams[] = {};
        
        try {
            
            if(pluginCoding ==0 && pluginSearch ==0 & pluginWordCloud==0){//Si los tres se encuentran
                    //instance = classControllerWordCloud.newInstance();
                //wORDcLOUD
                method = classControllerWordCloud.getDeclaredMethod("set_ListFileTemporal", paramList);

                //Coding
                set_ListFileTemporal = classControllerCoding.getDeclaredMethod("set_ListFileTemporal", paramList);
                cargarDocumentosListView = classControllerCoding.getDeclaredMethod("cargarDocumentosListView", noparams); 
                setEnableBtn = classControllerCoding.getDeclaredMethod("setEnableBtn", noparams);


                //Search
                setPathTranscription = classControllerSearch.getDeclaredMethod("setPathTranscription", String.class);
                set_ListFileTemporalSearch = classControllerSearch.getDeclaredMethod("set_ListFileTemporal", paramList);
            } else if(pluginCoding ==1 && pluginSearch ==1 & pluginWordCloud==0){//solo cloud
                //wORDcLOUD
                method = classControllerWordCloud.getDeclaredMethod("set_ListFileTemporal", paramList);
            
            } else if(pluginCoding ==0 && pluginSearch ==0 & pluginWordCloud==1){
                //Coding
                set_ListFileTemporal = classControllerCoding.getDeclaredMethod("set_ListFileTemporal", paramList);
                cargarDocumentosListView = classControllerCoding.getDeclaredMethod("cargarDocumentosListView", noparams); 
                setEnableBtn = classControllerCoding.getDeclaredMethod("setEnableBtn", noparams);


                //Search
                setPathTranscription = classControllerSearch.getDeclaredMethod("setPathTranscription", String.class);
                set_ListFileTemporalSearch = classControllerSearch.getDeclaredMethod("set_ListFileTemporal", paramList);
            }
            
            
            
            
            
            
            
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList selectedItems = listViewDocs.getSelectionModel().getSelectedItems();
        for(int i =0; i<listFileDocsDisponible.size();i++){
            for(int j=0; j<selectedItems.size();j++){
                if(listFileDocsDisponible.get(i).getAbsolutePath().equals(selectedItems.get(j   ))){
                    documents = documents + listFileDocsDisponible.get(i).getName()+", ";
                    listFileDocsTemporal.add(listFileDocsDisponible.get(i));//Tendra documentos temporales para cada plugin
                }
            }
        }
        
        

        if(ventanaActiva.equals("home")){
            labelDocs.setText("");
        
        }else if(ventanaActiva.equals("codificacion")){
            labelDocs.setText(dialogBundle.getString("U3ControllerDocCod") + documents);
            try {
                set_ListFileTemporal.invoke(instanceCoding, listFileDocsTemporal);//Para modificar la lista de archivos en Coding
                setPathTranscription.invoke(instanceSearch, pathAnalisisFolder);
                set_ListFileTemporalSearch.invoke(instanceSearch, listFileDocsTemporal);
                
                cargarDocumentosListView.invoke(instanceCoding); //Para cargar docuentos en listview coding
                
                
                setEnableBtn.invoke(instanceCoding); //Para activar botones necesarios coding
                
                
                
                
            } catch (IllegalAccessException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            controllerLabel.set_ListFileTemporal(listFileDocsTemporal);//SE PASA LOS DOCUMENTOS
//            controllerSearch.setPathTranscription(pathAnalisisFolder);
//            controllerSearch.set_ListFileTemporal(listFileDocsTemporal);//Se pasa los documentos a search tambien
//            
//            controllerLabel.cargarDocumentosListView();//Se carga los doc en listview
//            controllerLabel.setEnableBtn();//Se activan los botones
            
            
            
            
        
        }else if(ventanaActiva.equals("nube")){
            String documetsNube = "";
            String[] splited = documents.split(",");//corto 
            for(int i = 0; i< splited.length; i++){
                if(splited[i].endsWith(".srt")){
                    documetsNube = documetsNube + splited[i]+", ";
                
                }
            
            }
            
            labelDocs.setText(dialogBundle.getString("U3ControllerDocNube")+ documetsNube);
            //controllerLabel.setListViewClear();
            
            try {
               method.invoke(instanceCloud, listFileDocsTemporal);
                
//               method = classControllerWordCloud.getDeclaredMethod("getList", noparams);
//               method.invoke(instanceCloud);
                
               
                //controllerNube.set_ListFileTemporal(listFileDocsTemporal);//Se pasan los doc a la ventana de cloud tags
                //controllerNube.setEnableBtn();
            } catch (IllegalAccessException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(U3AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    

    
    //Permite cargar los documentos que fueron cargados en la ventana de seleccion
    public void cargarDocsDisponibles(){
        for(int i=0; i< listofFiles.size();i++){
            listViewDocs.getItems().add(listofFiles.get(i));
            
        
        }
        listViewDocs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//Permite seleccionar varios documentos en listview
        ventanaActiva = "home";//Se setea la ventana activa
        btnAddDocuments.setDisable(true);//Se desactiva el boton de agregar documentos en home
        
        for(int j=0; j< listofFiles.size();j++){
            File file = new File(listofFiles.get(j));
            listFileDocsDisponible.add(file);//Se agregan los archivos disponibles y los convierto en File
            
        }
        
        
    }
    
    public String setPathAnalisis(String path){
        return this.pathAnalisisFolder = path;
    }
    
}
