using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;

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
            DontDestroyOnLoad(gameObject);
        }

        private void StartNewScene()
        {
            if (levelManager)
            {
                // si tengo el entero privado o el progreso guardado etc
            }
        }

        static GameManager _instance;
    }
}