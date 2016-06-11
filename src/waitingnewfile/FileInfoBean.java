/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waitingnewfile;

/**
 *
 * @author jinihong
 */
public class FileInfoBean
{
   String name;
   String update_dt;
   boolean new_file;

    public boolean isNew_file()
    {
        return new_file;
    }

    public void setNew_file(boolean new_file)
    {
        this.new_file = new_file;
    }
   
   

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUpdate_dt()
    {
        return update_dt;
    }

    public void setUpdate_dt(String update_dt)
    {
        this.update_dt = update_dt;
    }
   
   
}
