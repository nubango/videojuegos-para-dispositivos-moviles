package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONReader {

    JSONArray jsonArray = null;
    JSONObject level = null;

    public JSONReader(String path){
        JSONParser jsonParser = new JSONParser();

        Reader jsonReader = null;
        try {
            jsonReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            jsonArray = (JSONArray) jsonParser.parse(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Object o : jsonArray)
        {
            level = (JSONObject) o;
            // TESTEO PARA SABER QUE NIVEL ES
            System.out.println("QUE NIVEL ES?");
            if(this.getName().equals("THE BOX"))
                System.out.println("ES EL NIVEL 1");
        }

    }

    String getName(){
        String name = (String) level.get("name");
        System.out.println("NOMBRE: " + name);
        return name;
    }

    ArrayList<Path> getPaths(){
        JSONArray path = (JSONArray) level.get("path");
        Iterator<Path> itr = path.listIterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        /* EQUIVALENTE AL ITERADOR
        for (Object p : path)
        {
            System.out.println(p+"");
        }
         */
        return path;
    }

    ArrayList<Item> getItems(){
        JSONArray items = (JSONArray) level.get("items");
        Iterator<Item> itr = items.listIterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        /* EQUIVALENTE AL ITERADOR
        for (Object i : items)
        {
            System.out.println(i+"");
        }
         */
        return items;
    }

    ArrayList<Enemy> getEnemies(){
        JSONArray enemies = (JSONArray) level.get("enemies");
        Iterator<Item> itr = enemies.listIterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        /* EQUIVALENTE AL ITERADOR
        for (Object e : enemies)
        {
            System.out.println(e+"");
        }
         */
        return enemies;
    }


}
