/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectotbd2;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bryam
 */
public class CQL_Operaciones {
    private static Cluster cluster;
    public static Session session;
    
    public static Cluster connect(String node){
        return Cluster.builder().addContactPoint(node).build();
    }
    
    public static void IniciarConexion(){
        try {
            cluster = connect("127.0.0.1");
            System.out.println("fuck");
        } catch (NoHostAvailableException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void IniciarSession(String KeySpaceName){
        try {
            session = cluster.connect(KeySpaceName);
            System.out.println("funciona");
        } catch (NoHostAvailableException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void Crear_KeySpace(String KeySpaceName){
        session = cluster.connect();
        session.execute("CREATE KEYSPACE" + KeySpaceName + "WITH replication = {'class': 'SimpleStrategy','replication_factor' :1}");
    }
    
    public static void Eliminar_keySpace(String KeySpaceName){
        session.execute("DROP KEYSPACE" + KeySpaceName);
    }
    
    public static void finalizarConexion(){
        session.close();
        cluster.close();
    }
    
    public DefaultTableModel getEquipo() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idEquipo");
        model.addColumn("Nombre del Equipo");
        try {
            IniciarConexion();
            IniciarSession("proyecto");
            System.out.println(session.execute("select *"
                    + "From equipo;"));
            ArrayList<Row> rs = (ArrayList<Row>) session.execute("select *"
                    + "From equipo;");
            for (Row r : rs) {
                model.addRow(new Object[]{r.getString(1),r.getString(2)});
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        finalizarConexion();
        return model;
    }
    
    public static void Insert(){
        
    }
}
