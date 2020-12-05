package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSONReader {
    public JSONReader(String path){
        JSONParser jsonParser = new JSONParser();

        Reader jsonReader = null;
        JSONArray jsonArray = null;
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
            JSONObject level = (JSONObject) o;

            String name = (String) level.get("name");
            System.out.println("NOMBRE: " + name);

            JSONArray paths = (JSONArray) level.get("paths");
            System.out.println("CAMINOS: ");
            for (Object c : paths)
            {
                System.out.println(c+"");
            }

            JSONArray items = (JSONArray) level.get("items");
            System.out.println("MONEDAS: ");
            for (Object c : items)
            {
                System.out.println(c+"");
            }
            /*
            JSONArray enemies = (JSONArray) level.get("enemies");
            System.out.println("ENEMIGOS: ");
            for (Object c : enemies)
            {
                System.out.println(c+"");
            }
            */
        }
    }
}
