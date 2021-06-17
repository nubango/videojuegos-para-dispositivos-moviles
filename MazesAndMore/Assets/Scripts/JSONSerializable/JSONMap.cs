using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class JSONMap
{
    // filas
    public int r = 0;
    // columnas
    public int c = 0;

    // casilla salida
    public JSONPoint s;
    // casilla final
    public JSONPoint f;

    // pistas
    public List<JSONPoint> h;
    // muros
    public List<JSONWall> w;

    // casillas de hielo
    public List<JSONPoint> i;
    // enemigos
    public List<JSONPoint> e;

    public static JSONMap CreateFromJSON(string jsonString)
    {
        return JsonUtility.FromJson<JSONMap>(jsonString);
    }

}
