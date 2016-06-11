package com.lightspeedleader.directorywatcher;

//import action.FileEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import waitingnewfile.CmnUtil;
import waitingnewfile.FXMLDocumentController;

public class FileListener extends BaseListener implements IFileListener
{

    /**
     * Connstructor
     */
    public FileListener()
    {
        super();
    }

    public void onStart(Object monitoredResource)
    {
        // On startup
        if (monitoredResource instanceof File)
        {
            File resource = (File) monitoredResource;
            if (resource.isDirectory())
            {

                System.out.println("Start to monitor " + resource.getAbsolutePath());

                

                File[] files = resource.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    File f = (File) files[i];
                    DirectoryWatcher.controller.addRecord(f.getName(), CmnUtil.convertFormat(f.lastModified(), "yyyy/MM/dd HH:mm"),false);
                    
                    //onAdd(f);
                }
                

            }
        }
    }

    public void onStop(Object notMonitoredResource)
    {
        System.out.println("저장");
    }

    public void onAdd(Object newResource)
    {
        if (newResource instanceof File)
        {
            File file = (File) newResource;
            if (file.isFile())
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
//                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waitingnewfile/FXMLDocument.fxml"));
//                            Parent root = (Parent)loader.load();
//                            FXMLDocumentController controller = (FXMLDocumentController) loader.getController();

                        DirectoryWatcher.controller.addRecord(file.getName(), CmnUtil.convertFormat(file.lastModified(), "yyyy/MM/dd HH:mm"),true);

                    }
                }
                );

                System.out.println(file.getAbsolutePath() + " is added");
//                (new FileEvent()).FileProcess(file.getAbsolutePath());
            }
        }
    }

    public void onChange(Object changedResource)
    {
        if (changedResource instanceof File)
        {
            File file = (File) changedResource;
            if (file.isFile())
            {
                System.out.println(file.getAbsolutePath() + " is changed");
            }

        }
    }

    public void onDelete(Object deletedResource)
    {
        if (deletedResource instanceof String)
        {
            String deletedFile = (String) deletedResource;
            System.out.println(deletedFile + " is deleted");
        }
    }
}
