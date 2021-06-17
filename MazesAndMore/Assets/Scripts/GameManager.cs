using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;

        // conoce todos los paquetes y a su vez los niveles
        public LevelPackage[] levelPackages;
        private LevelPackage currentPackage;


        public Sprite levelButtonSprite;
        public Sprite levelButtonPressedSprite;

        public Canvas canvas;
        public Button selectLevelsButton;
        public Button levelButton;
        public GenerateLevelsGrid grid;

        private string namePackageLevels;

        // nivel que ha pulsado el juegador
        private int _levelId;
        private int currentLevel;
        // numero de niveles pasados
        private int[] numLevelsProgress;
        private int hints = 5;
        private bool ads = true;

        private SaveProgress progress;
        private string saveFile = "/progressData.json";

#if UNITY_EDITOR
        public int levelToPlay;
#endif

        private void Start()
        {
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                if (levelManager != null)
                {
                    _instance.levelManager.levelPackage = _instance.currentPackage;
                    _instance.levelManager.numHints = _instance.hints;
                }
                _instance.grid = grid;
                _instance.canvas = canvas;
                _instance.StartNewScene();
                DestroyImmediate(gameObject);
                return;
            }
            // Resto de la incializacion del GameManager
            _instance = this;
            DontDestroyOnLoad(gameObject);

            progress = new SaveProgress();
            progress.ads = true;
            progress.levels = new List<int>();
            progress.hints = hints;

            progress = SaveProgress.LoadProgressFromJSON(saveFile, progress);

            if(progress.levels.Count == 0)
            {
                for (int i = 0; i < levelPackages.Length; i++)
                {
                    progress.levels.Add(0);
                }
            }

            numLevelsProgress = new int[levelPackages.Length];
            hints = progress.hints;

            for (int i = 0; i < numLevelsProgress.Length; i++)
            {
                numLevelsProgress[i] = progress.levels[i];
            }
            ads = progress.ads;

            StartNewScene();
        }

        public void LoadLevel(int levelId)
        {
            currentLevel = levelId;
            SceneManager.LoadScene("Game");
        }
        public void LoadSceneLevels(int levelId)
        {
            _levelId = levelId;
            currentPackage = levelPackages[levelId];
            namePackageLevels = levelPackages[levelId].nameLevels;
            grid.LoadGrid(currentPackage, numLevelsProgress[levelId]);
        }

        private void StartNewScene()
        {
            // si estoy en la escena de juego
            if (levelManager)
            {
                // level valores entre 0-X
                levelManager.SetNewLevel(currentLevel);
                // si tengo el entero privado o el progreso guardado etc
                // currentPackage = levelPackages[0];
                //#if UNITY_EDITOR
                //                levelManager.SetNewLevel(levelToPlay);
                //#else

                //#endif
            }
            else
            {
                for (int i = 0; i < levelPackages.Length; i++)
                {
                    Button b = Instantiate(selectLevelsButton);
                    b.transform.position = new Vector2((100 * i) + 100, (100 * i) + 100);
                    b.transform.parent = canvas.gameObject.transform;

                    b.GetComponent<SelectLevelsButtonHandle>().nameLevel = levelPackages[i].nameLevels;
                    b.GetComponent<SelectLevelsButtonHandle>().levelId = i;
                    b.GetComponent<Image>().sprite = levelPackages[i].botonNoPulsado;
                    SpriteState st = new SpriteState();
                    st.pressedSprite = levelPackages[i].botonPulsado;
                    b.spriteState = st;
                    b.GetComponentInChildren<Text>().text = "    " + levelPackages[i].nameLevels;

                }
            }
        }

        public int GetCurrentLevel()
        {
            return currentLevel;
        }

        public void SetCurrentLevel(int l)
        {
            currentLevel = l;
        }

        public void NextLevel()
        {
            currentLevel++;
            if (currentLevel >= currentPackage.levels.Length)
                SceneManager.LoadScene("Menu");

            if (currentLevel > numLevelsProgress[_levelId])
                numLevelsProgress[_levelId] = currentLevel;

            SaveProgressNow();
        }
        private void SaveProgressNow()
        {
            for (int i = 0; i < numLevelsProgress.Length; i++)
            {
                progress.levels[i] = numLevelsProgress[i];
            }
            progress.hints = hints;
            progress.ads = ads;

            SaveProgress.SaveProgressToJSON(progress, saveFile);
        }
        public void SetHints(int numHints)
        {
            hints = numHints;
            SaveProgressNow();
        }

        public static GameManager Instance()
        {
            return _instance;
        }

        static GameManager _instance;
    }
}