/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lo43p;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Youness-PC
 */
public class configpup {
    
     
    
    public static void display(String title) throws FileNotFoundException{
        
        Stage windows = new Stage();
        windows.setTitle(title);
        windows.setMinWidth(350);
        windows.setResizable(false);
        Label workTime= new Label("workTime : ");
        Label extraWorkTime= new Label("extraWorkTime : ");
        Label breakTime= new Label("breakTime : ");
        TextField workTimet=new TextField();
        TextField extraWorkTimet=new TextField();
        TextField breakTimet=new TextField();
        /////////////////////////File info
        File file = new File("././instances/config");
        Scanner sc = new Scanner(file);
        sc.next();
        workTimet.setText(sc.next());
        sc.next();
        extraWorkTimet.setText(sc.next());
        sc.next();
        breakTimet.setText(sc.next());
        //////////////////////////
        Button ok=new Button("MODIFIER");
        ok.setCursor(Cursor.HAND);
        ok.setOnAction((ActionEvent event) -> {
            
            File myFoo = new File("././instances/config");
            FileWriter fooWriter;
            try {
                fooWriter = new FileWriter(myFoo, false); // true to append
                  fooWriter.write("workTime "+workTimet.getText()+"\n"
                          + "extraWorkTime "+extraWorkTimet.getText()+"\n"
                           +"breakTime "+breakTimet.getText());
                   fooWriter.close();
                   new Alert(Alert.AlertType.INFORMATION, "Modification Effectuer").showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(configpup.class.getName()).log(Level.SEVERE, null, ex);
            }
                                                     // false to overwrite.
      
          
        });
       
        VBox layout= new VBox(10);
         layout.setPadding(new Insets(10, 80, 10, 80));
        layout.getChildren().addAll(workTime,workTimet,extraWorkTime,extraWorkTimet,breakTime,breakTimet,ok);
        layout.setAlignment(Pos.CENTER);
        
        Scene scn = new Scene(layout);
        windows.setScene(scn);
        windows.showAndWait();
        
       
        
    }
    
    
}
