/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lo43p;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Youness-PC
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private Label label =new Label();
      @FXML
    private Button exit;
    
    @FXML
    private void exit() {
        System.out.println("exit");
        Platform.exit();
    }
    @FXML
    private void config() throws FileNotFoundException {
      System.out.println("config");
      configpup.display("Configuration");
    }
    @FXML
    private void accl() {
      System.out.println("app");
       
    // do what you have to do
       Platform.exit();

      Main.main(new String[0]);
      
      
    }
    @FXML
    private void browse() throws IOException {
      DirectoryChooser directoryChooser = new DirectoryChooser();
       File selectedDirectory = directoryChooser.showDialog(new Stage());
      if(selectedDirectory == null){
                    label.setText("Aucune instance sélectionée");
                }else{
                    label.setText(selectedDirectory.getName());
                    File srcDir = new File(selectedDirectory.getAbsolutePath());
                    File destDir = new File("././instances/"+selectedDirectory.getName());
                    copyFolder(srcDir, destDir);
                }
     
    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory()) 
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists()) 
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) 
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another 
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }
    
 }

