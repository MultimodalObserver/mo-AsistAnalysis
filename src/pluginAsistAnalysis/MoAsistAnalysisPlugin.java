/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pluginAsistAnalysis;

import bibliothek.util.xml.XElement;
import bibliothek.util.xml.XIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mo.analysis.AnalysisProvider;
import mo.core.plugin.Extends;
import mo.core.plugin.Extension;
import mo.organization.Configuration;
import mo.organization.ProjectOrganization;
import mo.organization.StagePlugin;

/**
 *
 * @author Lathy
 */

@Extension(
    xtends = {
        @Extends(extensionPointId = "mo.analysis.AnalysisProvider")
    }
)

public class MoAsistAnalysisPlugin  implements AnalysisProvider{
    private List<Configuration> configurations; 
    private final static String PLUGIN_NAME = "Assistant Analysis Plugin";
    public final static Logger logger = Logger.getLogger(MoAsistAnalysisPlugin.class.getName());

    
    public MoAsistAnalysisPlugin(){
        configurations = new ArrayList<>();
        
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public Configuration initNewConfiguration(ProjectOrganization po) {
        
//        MoTranscriptionAnalysisConfiguration config = new MoTranscriptionAnalysisConfiguration(); 
//        config.init(po.getLocation()); 
//        configs.add(config); 
//        JOptionPane.showMessageDialog(null, "HI");
//        return config;   
        
        MoAsistAnalysisConfigDialog d = new MoAsistAnalysisConfigDialog();
        
        if (d.showDialog()) {
            MoAsistAnalysisConfiguration c = new MoAsistAnalysisConfiguration();
            c.setId(d.getConfigurationName());
            
            AsistAnalysisManagement asistAnalysis = new AsistAnalysisManagement("");
            asistAnalysis.set_TranscriptionManagement(asistAnalysis);
            asistAnalysis.invoke();
            
            configurations.add(c);
            
            return c;
        }
        
        return null;
    
    
    }
    

    @Override
    public List<Configuration> getConfigurations() {
         return configurations;
    }

    @Override
    public StagePlugin fromFile(File file) {
        
        if (file.isFile()) {
            try {
                MoAsistAnalysisPlugin mc = new MoAsistAnalysisPlugin();
                XElement root = XIO.readUTF(new FileInputStream(file));
                XElement[] pathsX = root.getElements("path");
                for (XElement pathX : pathsX) {
                    String path = pathX.getString();
                    MoAsistAnalysisConfiguration c = new MoAsistAnalysisConfiguration();
                    Configuration config = c.fromFile(new File(file.getParentFile(), path));
                    if (config != null) {
                        mc.configurations.add(config);
                    }
                }
                return mc;
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return null;
        
    }

    @Override
    public File toFile(File parent) {
        
        File file = new File(parent, "asist-analysis.xml");
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        XElement root = new XElement("analysis");
        for (Configuration config : configurations) {
            File p = new File(parent, "asist-analysis");
            p.mkdirs();
            File f = config.toFile(p);

            XElement path = new XElement("path");
            Path parentPath = parent.toPath();
            Path configPath = f.toPath();
            path.setString(parentPath.relativize(configPath).toString());
            root.addElement(path);
        }
        try {
            XIO.writeUTF(root, new FileOutputStream(file));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return file;
    }
    
}
