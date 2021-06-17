using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class LevelManager : MonoBehaviour
    {
        [Tooltip("...")]
        public BoardManager boardManager;

        // de momento esto va aqui hasta que usemos scriptableObject
        public TextAsset level;
        // luego va
        public LevelPackage levelPackage;

        private void Start()
        {
            boardManager.Init(this);
        }
    }
}