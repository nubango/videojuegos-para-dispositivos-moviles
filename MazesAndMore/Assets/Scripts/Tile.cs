using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Tile : MonoBehaviour
    {
        [Tooltip("Sprite que indica que la celda es de hielo")]
        public SpriteRenderer iceFloor;

        public void EnableIce()
        {

        }

        public void DisableIce()
        {

        }

        public void EnableStart()
        {

        }

        // con un enumerado mejor o con una estructura
        public void EnableWestWall()
        {

        }

        private void Start()
        {
#if UNITY_EDITOR
            if (iceFloor == null)
            {
                Debug.LogError("............");
                gameObject.SetActive(false);
                return;
            }
#endif
        }
    }
}