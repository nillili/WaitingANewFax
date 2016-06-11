/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waitingnewfile;

import com.lightspeedleader.directorywatcher.SerializableClass;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 *
 * @author jinihong
 */
public class CmnUtil
{
    public static HashMap param;
    public static String file_path;
    static final String STR_DIR_PATH = ".\\dirpath.properties";
    
    public static String convertFormat(long time,String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }
    
    public static void setPath(String path)
    {
        file_path = path;
    }
    
    public static String getPath()
    {
        return file_path;
    }
    
    public  void putParam(String value)
    {        
        if(param == null)
        {
            param = new HashMap();
        }
        param.put("111", value);
    }
    
    public  String getParam()
    {
        try
        {
            return  (String) param.get("111");    
        } catch (Exception e)
        {
            return "";
        }
        
        
    }
    
    public void saveFilesInfo(String file_name, Object oo_file)
    {
        ObjectOutput oo = null;
        try
        {
//            oo = new ObjectOutputStream(new FileOutputStream(".\\files.properties"));
            oo = new ObjectOutputStream(new FileOutputStream(file_name));
            oo.writeObject(oo_file);
            oo.flush();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                oo.close();
            } catch (IOException ex1)
            {
            }
        }

    }
    
    public Object readFilesInfo(String file_name)
    {
        try
        {
            SerializableClass sc = null;
            ObjectInput oi = new ObjectInputStream(new FileInputStream(file_name));
            return oi.readObject();

        } catch (IOException ex)
        {
//            ex.printStackTrace();
        } catch (ClassNotFoundException ex)
        {
//            ex.printStackTrace();
        }

        return null;
    }
    
}
