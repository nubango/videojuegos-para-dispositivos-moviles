﻿using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Character : MonoBehaviour
    {
        [Tooltip("Sprite del personaje")]
        public SpriteRenderer character;
        [HideInInspector]
        public BoardManager boardManager;


        /*
         * El rastro que deja el personaje se pinta con el characterTrace. 
         * Cada rastro en una msima direccion es la misma instancia del characterTrace.
         * cada vez que se cambia de direccion se instancia una nueva para poder estirarla en esa nueva direccion
         * 
         * **/

        void Update()
        {
            // miro si han pulsado para determinar la direccion a la que voy
            Vector2 newDir = new Vector2(0, 0);
            if (Input.GetAxis("Horizontal") > 0) // derecha
            {
                newDir.x = 1;
            }
            else if (Input.GetAxis("Horizontal") < 0) // izquierda
            {
                newDir.x = -1;
            }
            else if (Input.GetAxis("Vertical") > 0) // arriba
            {
                newDir.y = 1;
            }
            else if (Input.GetAxis("Vertical") < 0) // abajo
            {
                newDir.y = -1;
            }

            transform.localPosition = boardManager.GetNextPosition(transform.localPosition, newDir);

            //if (positionsNextIntersection.Count == 0) // si no hay sitio al que ir
            //{
            //    // miro si han pulsado para determinar la direccion a la que voy
            //    Vector2 newDir = new Vector2(0, 0);
            //    // escojo la direccion a donde ir
            //    if (Input.GetAxis("Horizontal") > 0) // derecha
            //    {
            //        newDir.x = 1;
            //    }
            //    else if (Input.GetAxis("Horizontal") < 0) // izquierda
            //    {
            //        newDir.x = -1;
            //    }
            //    else if (Input.GetAxis("Vertical") > 0) // arriba
            //    {
            //        newDir.y = 1;
            //    }
            //    else if (Input.GetAxis("Vertical") < 0) // abajo
            //    {
            //        newDir.y = -1;
            //    }

            //    if (newDir.x != 0 || newDir.y != 0)
            //    {
            //        List<Vector2> aux = boardManager.getIntersection(transform.position, newDir);
            //        positionsNextIntersection = aux;
            //        // si tenemos posiciones previas, comprobamos si vamos hacia atras
            //        if (positionHistory.Count > 1 && aux.Count > 0 && aux[0] == positionHistory[positionHistory.Count - 2])
            //        {
            //            goBack = true;
            //        }
            //        else
            //        {
            //            positionHistory.AddRange(aux);
            //        }
            //    }
            //}

            //if (positionsNextIntersection.Count > 0)
            //{
            //    // nos movemos hacia el siguiente punto
            //    float step = speed * Time.deltaTime;
            //    transform.position = Vector2.MoveTowards(transform.position, positionsNextIntersection[0], step);
            //    boardManager.updateCharacterPos(transform.position);

            //    // si hemos llegado al siguente punto, lo sacamos de la lista
            //    if ((Vector2)transform.position == positionsNextIntersection[0])
            //    {
            //        positionsNextIntersection.RemoveAt(0);
            //        if (goBack)
            //        {
            //            positionHistory.RemoveAt(positionHistory.Count - 1);
            //            if (positionHistory.Count == 0)
            //            {
            //                goBack = false;
            //                positionHistory.AddRange(positionsNextIntersection);
            //            }
            //        }

            //        if (positionsNextIntersection.Count == 0)
            //        {
            //            goBack = false;
            //        }
            //    }
            //}

            //Debug.Log(positionHistory.Count);

            // (distance/deltatime) - longitud del path

        }
    }
}

/*
 void Update()
        {
            if (positionsNextIntersection.Count == 0) // si no hay sitio al que ir
            {
                // miro si han pulsado para determinar la direccion a la que voy
                Vector2 newDir = new Vector2(0, 0);
                // escojo la direccion a donde ir
                if (Input.GetAxis("Horizontal") > 0) // derecha
                {
                    newDir.x = 1;
                }
                else if (Input.GetAxis("Horizontal") < 0) // izquierda
                {
                    newDir.x = -1;
                }
                else if (Input.GetAxis("Vertical") > 0) // arriba
                {
                    newDir.y = 1;
                }
                else if (Input.GetAxis("Vertical") < 0) // abajo
                {
                    newDir.y = -1;
                }




                // si me tengo que mover
                if (newDir.x != 0 || newDir.y != 0)
                {
                    positionsNextIntersection = boardManager.getIntersection(transform.position, newDir);
                    // si vuelvo sobre mis pasos
                    if ((!goBack && newDir == -currentDir) || (tracePaths.Count != 0) && newDir == -tracePaths.Peek().direction)
                    {
                        // retrocedo y voy quitando el rastro
                        goBack = true;
                    }
                    // si voy en una direccion nueva
                    //else
                    //{
                    //    // avanzo y pongo rastro nuevo
                    //    goBack = false;
                    //}
                }
            }

            // si hay sitio al que ir nos movemos hacia él
            if (positionsNextIntersection.Count > 0)
            {
                // me traslado a la posicion correspondiente
                float step = speed * Time.deltaTime;
                transform.position = Vector2.MoveTowards(transform.position, positionsNextIntersection[0], step);

                // si hemos llegado al destino, lo sacamos de la lista
                if ((Vector2)transform.position == positionsNextIntersection[0])
                {
                    //positionHistory.Add(positionsNextIntersection[0]);
                    positionsNextIntersection.RemoveAt(0);

                    if (positionsNextIntersection.Count > 0)
                    {
                        Vector2 distance;
                        distance = positionsNextIntersection[0] - (Vector2)transform.position;
                        distance.Normalize();
                        if (currentDir != distance && goBack)
                        {
                            tracePath tp = tracePaths.Pop();
                            Destroy(tp.sr.gameObject);
                        }
                    }

                    if (positionsNextIntersection.Count == 0 && goBack)
                    {
                        tracePath tp = tracePaths.Pop();
                        Destroy(tp.sr.gameObject);
                    }
                }

                // actualizamos la direccion actual
                if (positionsNextIntersection.Count > 0)
                {
                    currentDir = positionsNextIntersection[0] - (Vector2)transform.position;
                    currentDir.Normalize();
                }

                if (!goBack)
                    updateTrace();
                else
                    backTrace();

                lastPosition = (Vector2)this.gameObject.transform.position;
                lastDir = currentDir;
            }
        }

        private void backTrace()
        {
            Vector2 currentPos = this.gameObject.transform.position;
            Vector2 distance = currentPos - lastPosition;

            if (tracePaths.Count > 0)
            {
                if (currentDir != -tracePaths.Peek().direction)
                {
                    goBack = false;
                }
                else
                {
                    if (lastDir.y != 0)
                        tracePaths.Peek().sr.size += 2 * distance;
                    else
                        tracePaths.Peek().sr.size -= 2 * distance;
                }
            }
            else if (tracePaths.Count == 0)
            {
                goBack = false;
            }
        }


        // este método se llama cuando sabemos que nos hemos movido
        private void updateTrace()
        {
            //Debug.Log("Estoy yendo hacia delante");

            // (distance/deltatime) - longitud del path
            Vector2 currentPos = this.gameObject.transform.position;
            Vector2 distance = currentPos - lastPosition;

            if (tracePaths.Count > 0)
            {
                // si cambiamos de direccion de movimiento 
                if (lastDir != currentDir)
                {
                    tracePath tp = tracePaths.Peek();
                    tp.sr.transform.parent = null;
                    SpriteRenderer newCharacterTrace = Instantiate(characterTrace);
                    newCharacterTrace.gameObject.transform.SetParent(this.gameObject.transform);
                    newCharacterTrace.transform.localPosition = new Vector3(-0.0625f, 0.0625f, 0);

                    tracePaths.Push(new tracePath(newCharacterTrace, currentDir));
                }

                if (currentDir.y != 0)
                    tracePaths.Peek().sr.size += 2 * distance;
                else
                    tracePaths.Peek().sr.size -= 2 * distance;
            }
            else
            {
                SpriteRenderer newCharacterTrace = Instantiate(characterTrace);
                newCharacterTrace.gameObject.transform.SetParent(this.gameObject.transform);
                newCharacterTrace.transform.localPosition = new Vector3(-0.0625f, 0.0625f, 0);
                tracePaths.Push(new tracePath(newCharacterTrace, currentDir));
            }
        }
     */
