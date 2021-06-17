using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Tile : MonoBehaviour
    {
        [Tooltip("Sprite que indica que la celda es de hielo")]
        public SpriteRenderer iceFloor;
        [Tooltip("Sprite del muro derecho de la casilla")]
        public SpriteRenderer rightWall;
        [Tooltip("Sprite del muro izquierdo de la casilla")]
        public SpriteRenderer leftWall;
        [Tooltip("Sprite del muro superior de la casilla")]
        public SpriteRenderer upWall;
        [Tooltip("Sprite del muro inferior de la casilla")]
        public SpriteRenderer downWall;
        [Tooltip("Sprite de la meta en casilla")]
        public SpriteRenderer goal;
        [Tooltip("Animacion del rastro")]
        public Animation animations;
        [Tooltip("Sprites renderer del rastro")]
        public SpriteRenderer[] characterTraces;

        public void EnableIce(bool b)
        {
            iceFloor.enabled = b;
        }

        public void EnableGoal(bool b)
        {
            goal.enabled = b;
        }

        // con un enumerado mejor o con una estructura
        public void EnableWalls(enabledWalls walls)
        {
            upWall.enabled = walls.up;
            downWall.enabled = walls.down;
            leftWall.enabled = walls.left;
            rightWall.enabled = walls.right;
        }
        public void rightLeftAnim(bool inverse)
        {
            if (inverse)
            {
                if (countLeft == 1)
                {
                    animations["Left_forward_right_left"].speed = -1;
                    animations["Left_forward_right_left"].time = animations["Left_forward_right_left"].length;
                    animations.Play("Left_forward_right_left");
                }
                countLeft--;
            }
            else
            {
                if (countLeft == 0)
                {
                    animations["Left_forward_right_left"].speed = 1;
                    animations["Left_forward_right_left"].time = 0;
                    bool aux = animations.Play("Left_forward_right_left");
                }
                countLeft++;
            }
        }

        public void leftRightAnim(bool inverse)
        {
            if (inverse)
            {
                if (countRight == 1)
                {
                    animations["Right_forward_left_right"].speed = -1;
                    animations["Right_forward_left_right"].time = animations["Right_forward_left_right"].length;
                    animations.Play("Right_forward_left_right");
                }
                countRight--;
            }
            else
            {
                if (countRight == 0)
                {
                    animations["Right_forward_left_right"].speed = 1;
                    animations["Right_forward_left_right"].time = 0;
                    bool aux = animations.Play("Right_forward_left_right");
                }
                countRight++;
            }
        }

        public void bottomUpAnim(bool inverse)
        {
            if (inverse)
            {
                if (countUp == 1)
                {
                    animations["Up_forward_bottom_up"].speed = -1;
                    animations["Up_forward_bottom_up"].time = animations["Up_forward_bottom_up"].length;
                    animations.Play("Up_forward_bottom_up");
                }
                countUp--;
            }
            else
            {
                if (countUp == 0)
                {
                    animations["Up_forward_bottom_up"].speed = 1;
                    animations["Up_forward_bottom_up"].time = 0;
                    bool aux = animations.Play("Up_forward_bottom_up");
                }
                countUp++;
            }
        }

        public void upBottomAnim(bool inverse)
        {
            if (inverse)
            {
                if (countBottom == 1)
                {
                    animations["Bottom_forward_up_bottom"].speed = -1;
                    animations["Bottom_forward_up_bottom"].time = animations["Bottom_forward_up_bottom"].length;
                    animations.Play("Bottom_forward_up_bottom");
                }
                countBottom--;
            }
            else
            {
                if (countBottom == 0)
                {
                    animations["Bottom_forward_up_bottom"].speed = 1;
                    animations["Bottom_forward_up_bottom"].time = 0;
                    bool aux = animations.Play("Bottom_forward_up_bottom");
                }
                countBottom++;
            }
        }

        // Hints animation 
        public void rightLeftHintAnim()
        {
            if (countLeftHint == 0)
            {
                animations["Hint_left_forward_right_left"].speed = 1;
                animations["Hint_left_forward_right_left"].time = 0;
                bool aux = animations.Play("Hint_left_forward_right_left");
            }
            countLeftHint++;
        }

        public void leftRightHintAnim()
        {
            if (countRightHint == 0)
            {
                animations["Hint_right_forward_left_right"].speed = 1;
                animations["Hint_right_forward_left_right"].time = 0;
                bool aux = animations.Play("Hint_right_forward_left_right");
            }
            countRightHint++;
        }

        public void bottomUpHintAnim()
        {
            if (countUpHint == 0)
            {
                animations["Hint_up_forward_bottom_up"].speed = 1;
                animations["Hint_up_forward_bottom_up"].time = 0;
                bool aux = animations.Play("Hint_up_forward_bottom_up");
            }
            countUpHint++;
        }

        public void upBottomHintAnim()
        {
            if (countBottomHint == 0)
            {
                animations["Hint_bottom_forward_up_bottom"].speed = 1;
                animations["Hint_bottom_forward_up_bottom"].time = 0;
                bool aux = animations.Play("Hint_bottom_forward_up_bottom");
            }
            countBottomHint++;
        }

        public struct enabledWalls
        {
            public enabledWalls(bool up, bool down, bool left, bool right)
            {
                this.up = up;
                this.down = down;
                this.left = left;
                this.right = right;
            }

            public bool up;
            public bool down;
            public bool left;
            public bool right;
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

        private void Update()
        {
            foreach (SpriteRenderer sp in characterTraces)
            {
                if (sp.size.x > 0.5 || sp.size.y > 0.5)
                {
                    sp.enabled = true;
                }
                else
                    sp.enabled = false;
            }
        }

        private int countLeft = 0, countRight = 0, countUp = 0, countBottom = 0;
        private int countLeftHint = 0, countRightHint = 0, countUpHint = 0, countBottomHint = 0;
    }
}