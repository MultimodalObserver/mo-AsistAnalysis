/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pluginAsistAnalysis;

import bibliothek.gui.dock.common.CControl;
import controller.AsistAnalysisController;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import mo.analysis.AnalyzableConfiguration;
import mo.analysis.NotPlayableAnalyzableConfiguration;
import mo.analysis.NotesAnalysisConfig;
import mo.analysis.NotesVisualization;
import mo.analysis.PlayableAnalyzableConfiguration;
import mo.core.I18n;
import mo.core.plugin.Extends;
import mo.core.plugin.Extension;
import mo.core.ui.dockables.DockableElement;
import mo.core.ui.dockables.DockablesRegistry;
import mo.core.ui.menubar.IMenuBarItemProvider;
import mo.organization.Configuration;
import mo.organization.Participant;
import mo.organization.ProjectOrganization;
import mo.organization.StagePlugin;
//import mo.transcriptionAnalysis.Controller.TranscriptionController;
//import mo.transcriptionAnalysis.model.Transcription;

/**
 *
 * @author Lathy
 */
//@Extension(xtends = {
//  @Extends(extensionPointId = "mo.core.ui.menubar.IMenuBarItemProvider")})
public class AsistAnalysisManagement implements IMenuBarItemProvider {
    //Se agregan al menubar de la aplicacion principal
    private JMenu projectMen;// = new JMenu("Analisis");
    private JMenuItem tools = new JMenuItem("Tools of Analysis");
    
    
    public static JFrame frame;
    public static JPanel panel;
    public static JFXPanel fxPanel;
    public FXMLLoader loader;
    //public static TranscriptionController controller;
    //public Transcription model;
    public static int height = 484;
    public static int width = 654;
    private Thread[] threads;
    private JButton cancelButon;
    private JButton okButon;
    private I18n i18n;
    private boolean accepted;
    private DockableElement dockable;
    ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("properties/principal");
    //public TranscriptionManagement tm = new TranscriptionManagement() ;


    AsistAnalysisManagement am;//Para swing

    public AsistAnalysisManagement(String nombre) {

          //System.out.println("Nombre aca nuevo");

    }

    public void set_TranscriptionManagement(AsistAnalysisManagement am){
          this.am = am;
    }
    
    public void invoke(){
        SwingUtilities.invokeLater(() -> am.initAndShow());
    
    }

    
    
    public AsistAnalysisManagement() {

    tools.addActionListener((java.awt.event.ActionEvent e) -> {
      AsistAnalysisManagement asistAnalysisManagement = new AsistAnalysisManagement();
      SwingUtilities.invokeLater(() -> asistAnalysisManagement.initAndShow());
    });
    
    //projectMen = tm.getMenu();
    projectMen.add(tools);
    projectMen.setVisible(true);
    }
    
    
    @Override
  public JMenuItem getItem() {
    return projectMen;
  }

  @Override
  public int getRelativePosition() {
    return -4;
  }

  @Override
  public String getRelativeTo() {
    return "options";
  }
  
//  private void openTranscriptionAnalisis() {
//    SwingUtilities.invokeLater(new Runnable() {
//      @Override
//      public void run() {
//        initAndShow();
//      }
//    });
//  }

  private void initAndShow(){
            // This method is invoked on the EDT thread
        frame = new JFrame(dialogBundle.getString("tittle_windows"));
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        fxPanel = new JFXPanel();
        frame.setSize(width, height);
        frame.getContentPane().add(fxPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        
        

        Platform.runLater(() -> initFxml(fxPanel));
  
  
  }
  
  
  
  private void initFxml(JFXPanel jfxPanel) {
        try {
            
            FXMLLoader loaderui2 = new FXMLLoader(getClass().getResource("/view/AsistAnalysis.fxml"));
            AsistAnalysisController am = new AsistAnalysisController();
            loaderui2.setController(am);
            
            Parent root = loaderui2.load();
            
            
            
            Scene scene = new Scene(root, width, height);
            //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            jfxPanel.setScene(scene);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }
  
  
  
  
  private void initAndShowGUI() {
    // This method is invoked on the EDT thread
    frame = new JFrame(dialogBundle.getString("tittle_windows"));
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    frame.setResizable(false);
    fxPanel = new JFXPanel();
    frame.setSize(width, height);
    frame.getContentPane().add(fxPanel, BorderLayout.CENTER);
    frame.setVisible(true);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        initFX(fxPanel);
      }
    });
  }

  private void initFX(JFXPanel fxPanel) {
    try {
            loader = new FXMLLoader(getClass().getResource("/view/AsystAnalysis.fxml"));
            Parent root1 = loader.load();
            
            Scene scene = new Scene(root1, width, height);
            fxPanel.setScene(scene);
            
            
//            Stage stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            
//            stage.setScene(scene);
//            stage.showAndWait();
                
                
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    
    
  }
    
}
