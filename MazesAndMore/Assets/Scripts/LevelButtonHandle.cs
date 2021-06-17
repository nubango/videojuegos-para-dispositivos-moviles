using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class LevelButtonHandle : MonoBehaviour
    {
        public int levelId;

        public void SetLevel()
        {
            GameManager.Instance().LoadLevel(levelId);
        }
    }
}
