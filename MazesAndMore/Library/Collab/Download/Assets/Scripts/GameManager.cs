using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;

        // conoce todos los paquetes y a su vez los niveles
        public LevelPackage[] levelPackages;
        private LevelPackage currentPackage;

        // gestiona el progreso
        // si se pasa un nivel se guarda en el progreso

#if UNITY_EDITOR
        public int levelToPlay;
#endif

        private void Start()
        {
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                DestroyImmediate(gameObject);
                return;
            }
            // Resto de la incializacion del GameManager
            _instance = this;
            DontDestroyOnLoad(gameObject);

            //levelPackages[0] = Resources.Load<LevelPackage>("Assets/Data/Levels/classic");
            //Debug.Log(levelPackages[0].levels.Length);
            StartNewScene();
            
        }

        private void StartNewScene()
        {
            // si estoy en la escena de juego
            if (levelManager)
            {
                // si tengo el entero privado o el progreso guardado etc
                // currentPackage = levelPackages[0];
                levelManager.SetNewLevel(levelToPlay);

            }
            else
            {
                // if (scene == menu)

                // else if (scene == levels)

                //estoy en un menu
            }
        }

        public int GetCurrentLevel()
        {
            return levelToPlay;
        }

        public void SetCurrentLevel(int level)
        {
            levelToPlay = level;
        }

        public static GameManager Instance()
        {
            return _instance;
        }

        static GameManager _instance;
    }
}