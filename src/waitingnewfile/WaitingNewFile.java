/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waitingnewfile;

import com.lightspeedleader.directorywatcher.DirectoryWatcher;
import com.lightspeedleader.directorywatcher.FileListener;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jinihong
 */
public class WaitingNewFile extends Application
{
    public static Stage primaryStage;
    DirectoryWatcher dw ;
    
    @Override
    public void stop()
    {
        System.out.println("프로그램 종료");
        dw.stop();
        
    }
    

    @Override
    public void start(Stage stage) throws Exception
    {
        this.primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));        
        Parent root = loader.load();
        FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
        
               
       String dir_loc = (String) (new CmnUtil()).readFilesInfo(CmnUtil.STR_DIR_PATH);
       
       CmnUtil.setPath(dir_loc);
        
//  시작        

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        


        if(dir_loc != null && (new File(dir_loc)).isDirectory() )
        {
            dw = new DirectoryWatcher(dir_loc, 5);

            dw.addListener(new FileListener());
            dw.addController(controller);
            dw.start();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
