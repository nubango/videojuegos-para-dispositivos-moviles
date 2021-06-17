using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class SelectLevelsButtonHandle : MonoBehaviour
    {
        public string nameLevel;
        public int levelId;

        public void SetLevels()
        {
            GameManager.Instance().LoadSceneLevels(levelId);
        }
    }
}