package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import es.ucm.gdv.engine.Engine;

public class JSONReader {
    private Engine engine;
    private JSONArray jsonFile = null;
    private JSONObject level = null;
    private JSONParser jsonParser;
    private InputStream jsonReader =null;

    public JSONReader(Engine e){
        setJsonParser(new JSONParser());
        setEngine(e);
    }

    private String getName(){
        String name = (String) getLevel().get("name");
        return name;
    }

    private ArrayList<Path> getPaths(){
        JSONArray paths = (JSONArray) getLevel().get("paths");
        ArrayList<Path> pathArray = new ArrayList<>();

        for (int i = 0; i < paths.size(); i++) {
            ArrayList<Utils.Point> vertices = new ArrayList<Utils.Point>();
            ArrayList<Utils.Point> directions = null;

            JSONObject path = (JSONObject) paths.get(i);

            JSONArray vertArray = (JSONArray) path.get("vertices");
            for (int j = 0; j < vertArray.size(); j++) {
                JSONObject vertex = (JSONObject) vertArray.get(j);

                double x = Double.parseDouble(vertex.get("x").toString());
                double y = Double.parseDouble(vertex.get("y").toString());

                vertices.add(new Utils.Point(x + OffTheLineLogic.LOGIC_WIDTH / 2, y + OffTheLineLogic.LOGIC_HEIGHT / 2));
            }

            JSONArray dirArray = (JSONArray) path.get("direction");
            if(dirArray != null){
                directions = new ArrayList<Utils.Point>();
                for (int j = 0; j < dirArray.size(); j++) {
                    JSONObject direction = (JSONObject) dirArray.get(j);

                    double x = Double.parseDouble(direction.get("x").toString());
                    double y = Double.parseDouble(direction.get("y").toString());

                    directions.add(new Utils.Point(x, y));
                }
            }
            pathArray.add(new Path(vertices,directions));
        }

        return pathArray;
    }

    private ArrayList<Item> getItems() {
        JSONArray items = (JSONArray) getLevel().get("items");
        ArrayList<Item> itemArray = new ArrayList<>();

        for (int j = 0; j < items.size(); j++) {
            JSONObject item = (JSONObject) items.get(j);
            double x = Double.parseDouble(item.get("x").toString());
            double y = Double.parseDouble(item.get("y").toString());
            itemArray.add(new Item(x + OffTheLineLogic.LOGIC_WIDTH / 2, y + OffTheLineLogic.LOGIC_HEIGHT / 2));
        }

        return itemArray;
    }

    private ArrayList<Enemy> getEnemies(){
        JSONArray enemies = (JSONArray) getLevel().get("enemies");
        if(enemies == null) return null;
        ArrayList<Enemy> enemyArray = new ArrayList<>();

        for (int i = 0; i < enemies.size(); i++) {
            JSONObject enemy = (JSONObject) enemies.get(i);
            double x = Double.parseDouble(enemy.get("x").toString());
            double y = Double.parseDouble(enemy.get("y").toString());
            double length = Double.parseDouble(enemy.get("length").toString());
            double angle = Double.parseDouble(enemy.get("angle").toString());

            Utils.Point offset = null;
            JSONObject offsetArray = (JSONObject) enemy.get("offset");
            if(offsetArray != null)
            {
                double offsetX = Double.parseDouble(enemy.get("x").toString());
                double offsetY = Double.parseDouble(enemy.get("y").toString());
                offset = new Utils.Point(offsetX, offsetY);
            }

            // opcionales

            double time1 = 0;
            Object o1 = enemy.get("time1");
            if(o1!= null){
                time1 = Double.parseDouble(enemy.get("time1").toString());
            }

            double time2 = 0;
            Object o2 = enemy.get("time2");
            if(o2!= null){
                time2 = Double.parseDouble(enemy.get("time2").toString());
            }

            double speed = 0;
            Object o3 =  enemy.get("speed");
            if(o3 != null){
                speed = Double.parseDouble(enemy.get("speed").toString());
            }

            enemyArray.add(new Enemy(x + OffTheLineLogic.LOGIC_WIDTH / 2, y + OffTheLineLogic.LOGIC_HEIGHT / 2, (int)length, angle, speed, offset, time1, time2));
        }

        return enemyArray;
    }

    public ArrayList<Level> parserLevels(String path){
        ArrayList<Level> levels = null;

        try {
            setJsonReader(getEngine().openInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            setJsonFile((JSONArray) getJsonParser().parse(new InputStreamReader(getJsonReader())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        levels = new ArrayList<>();
        // jsonFile.size() es el numero de niveles que hay
        for(int i = 0; i < getJsonFile().size(); i++) {
            setLevel((JSONObject) getJsonFile().get(i));

            String n = getName();
            ArrayList<Path> p = getPaths();
            ArrayList<Item> itm = getItems();

            levels.add(new Level(i, n, p, itm));
            ArrayList<Enemy> en = getEnemies();
            if (en != null)
                levels.get(i).addEnemies(getEnemies());

        }
        return levels;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public JSONArray getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(JSONArray jsonFile) {
        this.jsonFile = jsonFile;
    }

    public JSONObject getLevel() {
        return level;
    }

    public void setLevel(JSONObject level) {
        this.level = level;
    }

    public JSONParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public InputStream getJsonReader() {
        return jsonReader;
    }

    public void setJsonReader(InputStream jsonReader) {
        this.jsonReader = jsonReader;
    }
}
