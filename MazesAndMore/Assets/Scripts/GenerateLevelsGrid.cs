using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class GenerateLevelsGrid : MonoBehaviour
    {
        public Button levelButton;
        private List<Button> buttons;

        private void Awake()
        {
            buttons = new List<Button>();
        }
        public void LoadGrid(LevelPackage currentPackage, int level)
        {
            for (int i = 0; i < buttons.Count; i++)
            {
                Destroy(buttons[i].gameObject);
            }
            buttons.Clear();

            for (int i = 0; i < currentPackage.levels.Length; i++)
            {
                Button b = Instantiate(levelButton);
                buttons.Add(b);
                b.GetComponent<RectTransform>().anchoredPosition = Vector2.zero;
                b.GetComponent<RectTransform>().anchorMax = Vector2.zero;
                b.GetComponent<RectTransform>().anchorMin = Vector2.zero;
                b.GetComponent<RectTransform>().SetSizeWithCurrentAnchors(RectTransform.Axis.Horizontal, 1);
                b.transform.parent = gameObject.transform;
                b.GetComponent<Image>().sprite = currentPackage.levelNoPulsado;
                b.GetComponentInChildren<LevelButtonHandle>().levelId = i;
                b.image.color = Color.white;

                b.transform.GetChild(1).GetComponent<Image>().enabled = i > level;

                if (i > level)
                {
                    b.GetComponentInChildren<Text>().text = "";
                    b.GetComponentInChildren<Text>().color = Color.black;
                }
                else
                {
                    b.GetComponentInChildren<Text>().text = (i + 1).ToString();
                    b.GetComponentInChildren<Text>().alignment = TextAnchor.MiddleCenter;
                    b.GetComponentInChildren<Text>().color = Color.black;
                    b.image.color = Color.white;

                    if (i < level)
                    {
                        b.image.color = new Color(currentPackage.color.r, currentPackage.color.g, currentPackage.color.b);
                        b.GetComponentInChildren<Text>().color = Color.white;
                    }
                }
            }
        }
    }
}