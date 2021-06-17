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

        public bool levelFinished = false;

        private void Start()
        {
            boardManager.Init(this);

            //SetNewLevel(2);
        }

        // gestiona si se ha pasado el nivel
        // gestiona si se pulsa pausa

        public void SetNewLevel(int level)
        {
            boardManager.SetMap(Map.FromJson(levelPackage.levels[level].text));
        }

        // API para ser llamado desde BoardManager
        public void LevelFinished()
        {
            levelFinished = false;
            // TODO: mostrar anuncio
            // TODO: mostrar popup nivel pasado
            GameManager.Instance().SetCurrentLevel(GameManager.Instance().GetCurrentLevel() + 1);

            ChangeLevel();
        }
        private void ChangeLevel()
        {
            boardManager.DeleteMap();
            Debug.Log("CURRENT LEVEL: " + GameManager.Instance().GetCurrentLevel());
            SetNewLevel(GameManager.Instance().GetCurrentLevel());
            Debug.Log("CURRENT LEVEL: " + GameManager.Instance().GetCurrentLevel());
        }
        // gestión de pistas
    }
}