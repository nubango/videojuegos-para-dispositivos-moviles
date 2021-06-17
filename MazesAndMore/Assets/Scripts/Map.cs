public class Map
{
    public enum TILETYPE { IDLE, START, GOAL };
    public struct tileInfo
    {
        public bool ice;
        public TILETYPE type;
        public bool upWall;
        public bool downWall;
        public bool leftWall;
        public bool rightWall;
    }

    public float[,] hints;
    public tileInfo[,] tiles;

    public static Map FromJson(string json)
    {
        JSONMap jsonMap = JSONMap.CreateFromJSON(json);

        int rows = jsonMap.r;
        int cols = jsonMap.c;

        Map map = new Map();
        // inicializo los arrays
        map.hints = new float[jsonMap.h.Count, 2];
        map.tiles = new tileInfo[rows, cols];

        // crear el map y asignar los valores por defecto
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                tileInfo t;
                t.ice = false;
                t.type = TILETYPE.IDLE;
                t.upWall = false;
                t.downWall = false;
                t.leftWall = false;
                t.rightWall = false;
                map.tiles[i, j] = t;
            }
        }

        // asignamos los valores leidos del JSONMap
        // casilla inicio
        map.tiles[(int)jsonMap.s.y, (int)jsonMap.s.x].type = TILETYPE.START;
        // casilla final
        map.tiles[(int)jsonMap.f.y, (int)jsonMap.f.x].type = TILETYPE.GOAL;

        foreach (JSONWall wall in jsonMap.w)
        {
            int xOrigen = (int)wall.o.x;
            int yOrigen = (int)wall.o.y;
            int xDestino = (int)wall.d.x;
            int yDestino = (int)wall.d.y;

            if (xOrigen != xDestino)
            {
                if (yOrigen == rows)
                    //map.tiles[yOrigen - 1, xOrigen].upWall = true;
                    if (xOrigen == cols)
                        map.tiles[yOrigen - 1, xOrigen - 1].upWall = true;
                    else
                        map.tiles[yOrigen - 1, xOrigen].upWall = true;
                else
                {
                    //map.tiles[yOrigen, xOrigen].downWall = true;
                    if (xOrigen == cols)
                        map.tiles[yOrigen, xOrigen - 1].downWall = true;
                    else
                        map.tiles[yOrigen, xOrigen].downWall = true;
                    // para ponerle el muro a la casilla anterior (pepa dice en una clase que solo hace falta poner uno)
                    if (--yOrigen > -1)
                        //map.tiles[yOrigen, xOrigen].upWall = true;
                        if (xOrigen == cols)
                            map.tiles[yOrigen, xOrigen - 1].upWall = true;
                        else
                            map.tiles[yOrigen, xOrigen].upWall = true;
                }
            }
            else
            {
                if (xDestino == cols)
                    //map.tiles[yDestino, xDestino - 1].rightWall = true;
                    if (yDestino == rows)
                        map.tiles[yDestino - 1, xDestino - 1].rightWall = true;
                    else
                        map.tiles[yDestino, xDestino - 1].rightWall = true;
                else
                {
                    //map.tiles[yDestino, xDestino].leftWall = true;
                    if (yDestino == rows)
                        map.tiles[yDestino - 1, xDestino].leftWall = true;
                    else
                        map.tiles[yDestino, xDestino].leftWall = true;
                    // para ponerle el muro a la casilla anterior (pepa dice en una clase que solo hace falta poner uno)
                    if (--xDestino > -1)
                        //map.tiles[yDestino, xDestino].rightWall = true;
                        if (yDestino == rows)
                            map.tiles[yDestino - 1, xDestino].rightWall = true;
                        else
                            map.tiles[yDestino, xDestino].rightWall = true;
                }
            }
        }

        // asignamos el hielo
        foreach (JSONPoint ice in jsonMap.i)
        {
            map.tiles[(int)ice.y, (int)ice.x].ice = true;
        }

        // asignamos las pistas
        int n = 0;
        foreach (JSONPoint hint in jsonMap.h)
        {
            map.hints[n, 0] = hint.x;
            map.hints[n, 1] = hint.y;
            n++;
        }

        return map;
    }

    public int GetWidth()
    {
        return tiles.GetLength(0);
    }

    public int GetHeight()
    {
        return tiles.GetLength(1);
    }
}
