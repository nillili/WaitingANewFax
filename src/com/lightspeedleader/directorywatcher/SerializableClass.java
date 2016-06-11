/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lightspeedleader.directorywatcher;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author jinihong
 */
public class SerializableClass implements Serializable
{
    public Map map;

    public SerializableClass(Map map)
    {
        this.map = map;
    }

    public Map getMap()
    {
        return map;
    }

    public void setMap(Map map)
    {
        this.map = map;
    }
    
}
