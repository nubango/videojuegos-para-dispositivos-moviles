using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;

namespace MazesAndMore
{
    public class LevelManager : MonoBehaviour
    {
        [Tooltip("...")]
        public BoardManager boardManager;

        // TODO: private -> currentPackage desde GameManager
        public LevelPackage levelPackage;

        //public int numLevel;
        public bool levelFinished;

        private void Start()
        {
            boardManager.Init(this);
            //boardManager.SetMap(Map.FromJson(level.text));

            //SetNewLevel(2);
            //boardManager.SetMap(Map.FromJson(levelPackage.levels[numLevel].text));
            
        }


        // gestiona si se ha pasado el nivel
        // gestiona si se pulsa pausa

        // API para ser llamado desde el GameManager
        public bool isLevelFinished()
        {
            return levelFinished;
        }
        public void SetNewLevel(int level)
        {
            boardManager.SetMap(Map.FromJson(levelPackage.levels[level].text));
        }

        // API para ser llamado desde BoardManager
        public void LevelFinished()
        {
            levelFinished = true;
            // TODO: mostrar anuncio
            // TODO: mostrar popup nivel pasado
            Debug.Log("NIVEL FINALIZADO");
        }
        // gestión de pistas
    }
}