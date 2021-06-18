/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static pluginAsistAnalysis.AsistAnalysisManagement.frame;
import static pluginAsistAnalysis.AsistAnalysisManagement.fxPanel;

/**
 * FXML Controller class
 *
 * @author Lathy
 */
public class AsistAnalysisController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Button btnUploadDocs;
    
    @FXML
    private BorderPane borderpanecenter;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnCancelar;
    
    List<String> ventanas = new ArrayList<>();;
    String ventana;
    int whichIsLastClicked = -1;
    
    final List<BorderPane> border = new ArrayList<BorderPane>();
    List<Object> controllers = new ArrayList<>();
    private static int idx_cur = 0;
    private int atras = 0, siguiente=0;
    
    List<String> listOfPath = new ArrayList<String>();
    ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("properties/principal");
    @FXML
    private Text textAsist;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarVentanas();
        cargarIdioma();
        btnSiguiente.setDisable(true);
        btnAtras.setDisable(true);
        
        
        
    }    
    
    public AsistAnalysisController() {

        //System.out.println("Nombre aca nuevo");
      
    
    
    }
    
    public void setInitialContent (){
        ventanas.add("/view/U1AsistAnalysis.fxml");
        ventanas.add("/view/U2AsistAnalysis.fxml");
    }

    public void cargarIdioma(){
        textAsist.setText(dialogBundle.getString("AsistControllerTitle"));
        btnCancelar.setText(dialogBundle.getString("AsistControllerBtnCancel"));
        btnAtras.setText(dialogBundle.getString("AsistControllerBtnAtras"));
        btnSiguiente.setText(dialogBundle.getString("AsistControllerBtnNext"));
        btnUploadDocs.setText(dialogBundle.getString("AsistControllerSelectDocument"));
        
    
    }
    
    public void cargarVentanas(){
        try {
            //borderpane = (BorderPane)FXMLLoader.load(getClass().getResource("/view/TranscriptionView.fxml"));
            
            FXMLLoader loaderui1Analysis = new FXMLLoader(getClass().getResource("/view/U1AsistAnalysis.fxml"));
            U1AsistAnalysisController controllerui1Analysis = new U1AsistAnalysisController();
            loaderui1Analysis.setController(controllerui1Analysis);
            Parent rootui1Analysis = loaderui1Analysis.load();
            
            
            border.add((BorderPane) rootui1Analysis);
            
            
            
            
            controllers.add(controllerui1Analysis);
            
            
            
//            border.add((BorderPane) rootui1Analysis);
//           
//            
//            
//            
//            controllers.add(controllerui1Analysis);
            
            
            

            //borderpanecenter.getChildren().add(border.get(0));
            
//            Scene scene = new Scene(borderpane);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AsistAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BorderPane get_border(int idx){
        return border.get(idx);
    
    }

    public void set_border(int idx){
        System.out.println(border.size());
        Parent root = null;
        switch (idx) {
            case 0:
                root = border.get(0);
                break;
            case 1:
                root = border.get(1);
                break;
            case 2:
                root = border.get(2);
                break;
            default:
                break;
        }
        borderpanecenter.setCenter(root);
            
            
            //borderpanecenter.setCenter(border.get(idx));
            //borderpanecenter.getChildren().add(border.get(1));
            
            
            idx_cur = idx;
        
        
    }
    
    
    @FXML
    private void uploadDocuments(MouseEvent event) {//ventana cargar documentos de transcripcion de proyectos
        //ventana cargar documento
        siguiente = siguiente + 1;
        atras = atras + 1;
        
        btnSiguiente.setText(dialogBundle.getString("AsistControllerBtnFinish"));
        //btnSiguiente.setText("Finalizar");
        btnSiguiente.setDisable(false);
        
        
        //Se carga la ventana
        loadUI("/view/U1AsistAnalysis");
        //vetana principal
        btnUploadDocs.setStyle("-fx-background-color: #404040; -fx-text-fill:  #ffffff; ");
        //ventanas siguientes
        
        btnAtras.setDisable(true);
        
    }

    
    @FXML
    private void siguiente(MouseEvent event) throws IOException {
        Parent root=null;
        U1AsistAnalysisController controllerui1Analysis = (U1AsistAnalysisController) controllers.get(0);
        
        
        switch (siguiente) {
            case 1:
                //cargar ventana de transcripcion grande
                fxPanel.setVisible(false);
                frame.setVisible(false); //Para no mostrar la ventana de transcripcion
                
                AsistAnalysisController asistAnalysisController = new AsistAnalysisController();
                SwingUtilities.invokeLater(() -> asistAnalysisController.initAndShow(controllerui1Analysis));
                
                
                
                
                break;
            
            default:
                break;
        }
        borderpanecenter.setCenter(root);
        
    }
    
    private void initAndShow(U1AsistAnalysisController u1controller){
            // This method is invoked on the EDT thread
        frame = new JFrame(dialogBundle.getString("tittle_windows"));
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        fxPanel = new JFXPanel();
        frame.setSize(1400, 750);
        frame.getContentPane().add(fxPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        
        

        Platform.runLater(() -> initFxml(fxPanel, u1controller));
  
  
  }
    private void initFxml(JFXPanel jfxPanel, U1AsistAnalysisController u1controller) {
        try {
            
            
              
                
              
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/U3AsistAnalysis.fxml"));
            U3AsistAnalysisController controllerui3 = new U3AsistAnalysisController();
            loader.setController(controllerui3);
            controllerui3.setPathAnalisis(u1controller.getPathAnalisis());
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AsistAnalysis/U3AsistAnalysis.fxml"));
            
            Parent root = loader.load();
            //aca no pasa
            
            //Parent root = FXMLLoader.load(getClass().getResource("/fxml/transcription/ui5.fxml"));
            Scene scene = new Scene(root, 1400, 750);
            //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            
            
            
            listOfPath = u1controller.get_list();//Obtengo la lista de path desde u1
            System.out.println("Desde ui1"+listOfPath);
            
            controllerui3.set_List(listOfPath);//Modifico la lista de u3
            
            controllerui3.cargarVentanas();//
            controllerui3.cargarDocsDisponibles();
            
            
            
            jfxPanel.setScene(scene);
            
            
            
            
            
            
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }
  
    

    @FXML
    private void atras(MouseEvent event) {
    }
    
    private void loadUI (String ui){
        //Parent root = null, rootUi2, rootUi4, rootUi3;
       Parent root=null;
       
        if (ui.equals("/view/U1AsistAnalysis")){
            root = border.get(0);
                
        }
            
        borderpanecenter.setCenter(root);
       
        
    }
    
    

    
    
}
