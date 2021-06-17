using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace MazesAndMore
{
    public class LevelManager : MonoBehaviour
    {
        [Tooltip("...")]
        public BoardManager boardManager;

        // TODO: private -> currentPackage desde GameManager
        public LevelPackage levelPackage;

        public bool levelFinished = false;
        public int numHints;

        //TODO: eliminar bool
        public bool goToMenu = false;

        private void Start()
        {
            boardManager.Init(this);

            //SetNewLevel(2);
        }

        // gestiona si se ha pasado el nivel
        // gestiona si se pulsa pausa

        public bool TakeHint()
        {
            numHints--;
            if (numHints < 0) numHints = 0;
            GameManager.Instance().SetHints(numHints);

            return numHints > 0;
        }

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
            GameManager.Instance().NextLevel();

            ChangeLevel();
        }

        // al acabar el nivel hay que sumar uno al nivel correspondiente (classic o ice) y guardar los datos
        private void ChangeLevel()
        {
            boardManager.DeleteMap();
            SetNewLevel(GameManager.Instance().GetCurrentLevel());
        }
        // gestión de pistas

        private void Update()
        {
            if (goToMenu)
            {
                goToMenu = false;
                SceneManager.LoadScene("Menu");
            }
        }
    }
}