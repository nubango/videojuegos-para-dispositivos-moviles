using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    /*
     * Indispensable que el boardManager este en la posicion 0,0 
     * porque los calculos se hacen partiendo de este supuesto
     * 
     * **/
    public class BoardManager : MonoBehaviour
    {
        public Tile tilePrefab;
        public Character characterPrefab;

        /*
         * Este metodo crea el board (mapa con los prefab y todo eso) 
         * a partir de la informacion que se le pasa por parametro
         * 
         * **/
        public void SetMap(Map map)
        {
            this.map = map;

            int rows = map.tiles.GetLength(0);
            int cols = map.tiles.GetLength(1);

            _tiles = new Tile[rows, cols];
            _tilesInfo = new Map.tileInfo[rows, cols];

            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    // instanciamos el tile
                    _tiles[i, j] = Instantiate(tilePrefab);
                    _tiles[i, j].gameObject.transform.SetParent(this.gameObject.transform);

                    _tilesInfo[i, j] = map.tiles[i, j];

                    // iniciamos los atributos del tile correspondiente
                    _tiles[i, j].gameObject.transform.localPosition = new Vector2(j, i);
                    _tiles[i, j].EnableIce(_tilesInfo[i, j].ice);

                    if (j == cols - 1 || i == rows - 1)
                        _tiles[i, j].EnableWalls(new Tile.enabledWalls(_tilesInfo[i, j].upWall, _tilesInfo[i, j].downWall, _tilesInfo[i, j].leftWall, _tilesInfo[i, j].rightWall));
                    else
                        _tiles[i, j].EnableWalls(new Tile.enabledWalls(false, _tilesInfo[i, j].downWall, _tilesInfo[i, j].leftWall, false));

                    if (_tilesInfo[i, j].type == Map.TILETYPE.GOAL)
                    {
                        _tiles[i, j].EnableGoal(true);
                        goalPos = new Vector2(j, i);
                    }
                    else
                        _tiles[i, j].EnableGoal(false);

                    if (_tilesInfo[i, j].type == Map.TILETYPE.START)
                    {
                        c = Instantiate(characterPrefab);
                        c.gameObject.transform.SetParent(this.gameObject.transform);
                        c.transform.localPosition = _tiles[i, j].gameObject.transform.localPosition;
                        c.boardManager = this;
                        //characterPos = new Vector2(j, i);
                    }
                }
            }

            //if (characterPos == null)
            //    characterPos = new Vector2(0, 0);

            // hacer el reescalado del mapa.
            MapRescaling();
        }

        public void DeleteMap()
        {
            int rows = map.tiles.GetLength(0);
            int cols = map.tiles.GetLength(1);

            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    Destroy(_tiles[i, j].gameObject);
                    _tiles[i, j] = null;
                }
            }

            _tiles = null;
            maxIndexHint = 0;
            indexHint = 0;
            positionHistory.Clear();
            positionsNextIntersection.Clear();
            Destroy(c.gameObject);
            goBack = false;
            gameObject.transform.localScale = new Vector3(1, 1, 1);
        }

        public void Init(LevelManager levelManager)
        {
            _levelManager = levelManager;
        }
        private void TakeHint()
        {
            takeHint = false;
            if (_levelManager.TakeHint())
            {
                if (indexHint < map.hints.GetLength(0))
                {
                    for (int i = indexHint; i < map.hints.GetLength(0); i++)
                    {
                        if (i < positionHistory.Count && (i < positionHistory.Count && (map.hints[i, 0] != positionHistory[i].x || map.hints[i, 1] != positionHistory[i].y)))
                        {
                            // guardamos el index de la pista
                            indexHint = i - 1 < 0 ? i : i - 1;
                            // si estamos al principio del tablero
                            hintPosition = positionHistory[indexHint];
                            maxIndexHint = indexHint;
                            break;
                        }
                        else if (positionHistory.Count == 0 && i == 0)
                        {

                            // la posicion actual y la siguente son la misma para que empiece la animacion desde la casilla 0
                            hintPosition = (Vector2)c.transform.localPosition;
                            nextPositionHint = hintPosition;
                            indexHint = -1;
                            break;

                        }
                        else if (i >= positionHistory.Count)
                        {
                            indexHint = i - 1;
                            maxIndexHint = indexHint;
                            hintPosition.x = map.hints[indexHint, 0];
                            hintPosition.y = map.hints[indexHint, 1];
                            break;
                        }
                    }
                    setPreviousHintsPaths();
                }

                animTakeHint = true;
            }
        }
        /*
         * Este método activa el camino de las pistas que ya hemos recorrido
         * **/
        private void setPreviousHintsPaths()
        {
            for (int i = 0; i < indexHint; i++)
            {
                Vector2 aux1 = new Vector2(map.hints[i, 0], map.hints[i, 1]);
                Vector2 aux2 = new Vector2(map.hints[i + 1, 0], map.hints[i + 1, 1]);
                InitHintAnimationTrace(aux1, aux2 - aux1);
            }
        }
        /*
         * Este método devuelve la siguiente posicion cuando se está pintando una pista
         * **/
        private Vector2 GetPositionHint(Vector2 position)
        {
            if (indexHint < maxIndexHint + numHints && indexHint < map.hints.GetLength(0))
            {
                if (indexHint > -1)
                {
                    nextPositionHint.x = map.hints[indexHint, 0];
                    nextPositionHint.y = map.hints[indexHint, 1];
                }
                float step = speed * Time.deltaTime;
                position = Vector2.MoveTowards(position, nextPositionHint, step);

                if (position == nextPositionHint)
                {
                    indexHint++;
                    if (indexHint < map.hints.GetLength(0))
                    {
                        nextPositionHint.x = map.hints[indexHint, 0];
                        nextPositionHint.y = map.hints[indexHint, 1];
                        InitHintAnimationTrace(position, nextPositionHint - position);
                    }
                    else
                    {
                        animTakeHint = false;
                        maxIndexHint = indexHint;
                    }
                }

            }
            else
            {
                animTakeHint = false;
                maxIndexHint = indexHint;
            }
            return position;
        }

        /*
         * Metodo que devuelve la posicion actual del player en funcion de la direccion que le pase por parámetro
         * 
         * **/
        public Vector2 GetNextPosition(Vector2 position, Vector2 newDir)
        {
            //lastCharacterPos = characterPos;
            if (positionsNextIntersection.Count == 0)
            {
                if (newDir.x != 0 || newDir.y != 0)
                {
                    List<Vector2> aux = getIntersection(position, newDir);
                    positionsNextIntersection = aux;
                    if (aux.Count > 0)
                        // si tenemos posiciones previas, comprobamos si vamos hacia atras
                        if (positionHistory.Count > 1 && aux[0] == positionHistory[positionHistory.Count - 2])
                        {
                            goBack = true;
                            InitAnimationTrace(positionsNextIntersection[0], positionsNextIntersection[0] - position);
                        }
                        else
                        {
                            positionHistory.AddRange(aux);
                            InitAnimationTrace(position, positionsNextIntersection[0] - position);
                        }
                }
            }

            if (positionsNextIntersection.Count > 0)
            {
                if (goBack && positionsNextIntersection[0] != positionHistory[positionHistory.Count - 2])
                {
                    goBack = false;
                    positionHistory.AddRange(positionsNextIntersection);
                    InitAnimationTrace(position, positionsNextIntersection[0] - position);
                }

                // nos movemos hacia el siguiente punto
                float step = speed * Time.deltaTime;
                Vector2 removePos = new Vector2(position.x, position.y);
                position = Vector2.MoveTowards(position, positionsNextIntersection[0], step);

                // si hemos llegado al siguente punto, lo sacamos de la lista
                if (position == positionsNextIntersection[0])
                {
                    positionsNextIntersection.RemoveAt(0);
                    if (goBack)
                    {
                        if (positionsNextIntersection.Count > 0)
                        {
                            InitAnimationTrace(positionsNextIntersection[0], positionsNextIntersection[0] - position);
                        }

                        positionHistory.RemoveAt(positionHistory.Count - 1);
                        if (positionHistory.Count == 1)
                        {
                            positionHistory.RemoveAt(0);
                            goBack = false;
                            positionHistory.AddRange(positionsNextIntersection);
                        }
                    }
                    else if (positionsNextIntersection.Count > 0)
                    {
                        InitAnimationTrace(position, positionsNextIntersection[0] - position);
                    }
                    
                    if (positionsNextIntersection.Count == 0)
                    {
                        goBack = false;
                    }
                }
            }

            return position;
        }

        /*
         * Aplica al boardManager un escalado y una transformacion segun la cantidad de celdas del mapa y la resolucion de la pantalla
         * **/
        private void MapRescaling()
        {
            screenWidth = Screen.width;
            screenHeight = Screen.height;

            // agrandamos la pantalla para que quepan el baner de abajo y los botones de arriba
            float offsetLateral = 0.98f;
            float offsetVertical = 0.7f;

            float scaleFactorW, scaleFactorH, scaleFactor;
            float offsetX, offsetY;

            float cameraSize = Camera.main.orthographicSize;

            // resolucion de la cámara en unidades de unity 
            float tilesByHeight = cameraSize * 2;
            float tilesByWidth = (Screen.width * tilesByHeight) / Screen.height;

            scaleFactorW = (tilesByWidth * offsetLateral) / _tiles.GetLength(1);
            scaleFactorH = (tilesByHeight * offsetVertical) / _tiles.GetLength(0);
            scaleFactor = Mathf.Min(scaleFactorW, scaleFactorH);

            // centramos el tablero
            offsetX = (-tilesByWidth / 2) + 0.5f * scaleFactor;
            offsetY = (-tilesByHeight / 2) + 0.5f * scaleFactor;

            offsetX += (tilesByWidth - _tiles.GetLength(1) * scaleFactor) / 2;
            offsetY += (tilesByHeight - _tiles.GetLength(0) * scaleFactor) / 2;

            // asignamos los valores calculados (trasladamos y escalamos)
            gameObject.transform.position = new Vector3(offsetX, offsetY, 0);
            gameObject.transform.localScale = new Vector3(scaleFactor, scaleFactor, scaleFactor);
        }

        /*
         * Inicia la animacion del rastro del personaje al moverse
         * **/
        private void InitAnimationTrace(Vector2 position, Vector2 dir)
        {
            Tile t = _tiles[Mathf.RoundToInt(position.y), Mathf.RoundToInt(position.x)];
            if (!goBack)
            {
                if (dir.x != 0)
                {
                    // si la x es positiva (va hacia la derecha)
                    if (dir.x > 0)
                    {
                        // activamos animacion segmento derecho de izquierda a derecha
                        t.leftRightAnim(goBack);
                    }
                    // si la x es negativa (va hacia la izquierda)
                    else if (dir.x < 0)
                    {
                        // activamos animacion segmento izquierdo de derecha a izquierda
                        t.rightLeftAnim(goBack);
                    }

                }
                // eje Y
                else if (dir.y != 0)
                {
                    // si la y es positiva (va hacia arriba)
                    if (dir.y > 0)
                    {
                        // activamos animacion segmento de arriba de abajo a arriba
                        t.bottomUpAnim(goBack);
                    }
                    // si la y es negativa (va hacia abajo)
                    else if (dir.y < 0)
                    {
                        // activamos animacion segmento de abajo de arriba a abajo
                        t.upBottomAnim(goBack);
                    }

                }
            }
            else
            {
                if (dir.x != 0)
                {
                    // si la x es positiva (va hacia la derecha)
                    if (dir.x > 0)
                    {
                        // activamos animacion segmento izquierdo de izquierda a derecha
                        t.rightLeftAnim(goBack);
                    }
                    // si la x es negativa (va hacia la izquierda)
                    else if (dir.x < 0)
                    {
                        // activamos animacion segmento derecho de derecha a izquierda (animacion inversa)
                        t.leftRightAnim(goBack);
                    }

                }
                // eje Y
                else if (dir.y != 0)
                {
                    // si la y es positiva (va hacia arriba)
                    if (dir.y > 0)
                    {
                        // activamos animacion segmento de abajo de abajo a arriba (animacion inversa)
                        t.upBottomAnim(goBack);
                    }
                    // si la y es negativa (va hacia abajo)
                    else if (dir.y < 0)
                    {
                        // activamos animacion segmento de arriba de arriba a abajo (animacion inversa)
                        t.bottomUpAnim(goBack);
                    }

                }
            }
        }
        /*
         * Inicia la animacion del rastro de las pistas
         * **/
        private void InitHintAnimationTrace(Vector2 position, Vector2 dir)
        {
            Tile t = _tiles[Mathf.RoundToInt(position.y), Mathf.RoundToInt(position.x)];

            if (dir.x != 0)
            {
                // si la x es positiva (va hacia la derecha)
                if (dir.x > 0)
                {
                    // activamos animacion segmento derecho de izquierda a derecha
                    t.leftRightHintAnim();
                }
                // si la x es negativa (va hacia la izquierda)
                else if (dir.x < 0)
                {
                    // activamos animacion segmento izquierdo de derecha a izquierda
                    t.rightLeftHintAnim();
                }

            }
            // eje Y
            else if (dir.y != 0)
            {
                // si la y es positiva (va hacia arriba)
                if (dir.y > 0)
                {
                    // activamos animacion segmento de arriba de abajo a arriba
                    t.bottomUpHintAnim();
                }
                // si la y es negativa (va hacia abajo)
                else if (dir.y < 0)
                {
                    // activamos animacion segmento de abajo de arriba a abajo
                    t.upBottomHintAnim();
                }

            }
        }

        /*
         * Devuielve true si hay un muro desde la posicion position en la direccion direction
         * Devuelve false en caso contrario
         * 
         * **/
        private bool isWall(Vector2 position, Vector2 direction)
        {
            Map.tileInfo t = _tilesInfo[(int)position.y, (int)position.x];

            if (!t.upWall && !t.downWall && !t.rightWall && !t.leftWall)
                return false;

            if (direction.x == 0)
            {
                if (direction.y > 0)
                    return t.upWall;
                else if (direction.y < 0)
                    return t.downWall;
            }
            else if (direction.y == 0)
            {
                if (direction.x > 0)
                    return t.rightWall;
                else if (direction.x < 0)
                    return t.leftWall;
            }

            return true;
        }

        /*
         * Delvuelve el numero de paredes que hay en la posicion position
         * 
         * **/
        private int wallCount(Vector2 position)
        {
            Map.tileInfo t = _tilesInfo[(int)position.y, (int)position.x];

            int paredes = 0;

            if (t.upWall)
                paredes++;
            if (t.downWall)
                paredes++;
            if (t.rightWall)
                paredes++;
            if (t.leftWall)
                paredes++;

            return paredes;
        }

        /*
         * Devuelve la direccion libre a partir de la posicion position y la direccion direction
         * Este metodo da por supuesto que en la direccion direction hay un muro
         * 
         * **/
        private Vector2 getFreeDirection(Vector2 position, Vector2 currentDir)
        {
            Vector2 d = new Vector2();
            Vector2 aux = new Vector2(currentDir.y, currentDir.x);

            if (isWall(position, aux))
            {
                d = -aux;
            }
            else if (isWall(position, -aux))
            {
                d = aux;
            }

            return d;
        }

        /*
         * Devuelve una lista de posiciones a seguir para llegar a la primera interseccion
         * 
         * **/
        private List<Vector2> getIntersection(Vector2 position, Vector2 direction)
        {
            List<Vector2> stack = new List<Vector2>();
            // si en la direccion inicial no hay ningún muro
            if (!isWall(position, direction))
            {
                Vector2 nextPos = position + direction;
                stack.Add(nextPos);

                Vector2 currentDir = direction;

                bool iceWall = false;
                // mientras no hay interseccion(dos direcciones sin muros) o estamos en hielo y no hemos llegado a un muro en hielo
                while ((wallCount(nextPos) == 2 || _tilesInfo[(int)nextPos.y, (int)nextPos.x].ice) && !iceWall)
                {   // delante no hay pared y no estamos en una casilla de hielo
                    if (isWall(nextPos, currentDir) && !_tilesInfo[(int)nextPos.y, (int)nextPos.x].ice)
                    {
                        // cambio de direccion porque en la que vamos hay un muro y no estamos en hielo
                        currentDir = getFreeDirection(nextPos, currentDir);
                    }
                    else if (!isWall(nextPos, currentDir))
                    {
                        nextPos += currentDir;
                        stack.Add(nextPos);
                    }
                    else
                    {
                        iceWall = true;
                    }
                    // seguimos avanzando en la direccion que no es de la que venimos
                }
            }

            return stack;
        }


        private void Awake()
        {
            positionHistory = new List<Vector2>();
            positionsNextIntersection = new List<Vector2>();
            nextPositionHint = new Vector2(0, 0);
            hintPosition = new Vector2(0, 0);
        }

        private void Update()
        {
            if (screenWidth != Screen.width || screenHeight != Screen.height)
            {
                MapRescaling();
            }

            if (map != null && ((Vector2)c.transform.localPosition == goalPos || _levelManager.levelFinished))
                _levelManager.LevelFinished();

            if (takeHint)
                TakeHint();

            if (animTakeHint)
                hintPosition = GetPositionHint(hintPosition);
        }

        private Tile[,] _tiles;
        private Map.tileInfo[,] _tilesInfo;
        private LevelManager _levelManager;
        private Map map;

        // Character attributes
        private Character c;
        private float speed = 3.0f;
        private bool goBack = false;
        private Vector2 goalPos;

        private List<Vector2> positionHistory;                // lista de todas las posiciones que llevo
        private List<Vector2> positionsNextIntersection;      // lista de puntos e intersecciones a seguir (los puntos son los cambios de sentido e intersecciones)

        private int screenWidth;
        private int screenHeight;

        // hints 
        public bool takeHint = false;
        bool animTakeHint = false;
        int indexHint = 0;
        int maxIndexHint = 0;
        int numHints = 4;
        Vector2 nextPositionHint;
        Vector2 hintPosition;


    }
}
