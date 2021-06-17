using UnityEngine;
using System.IO;
using UnityEngine.UI;

[CreateAssetMenu(fileName = "Data", menuName = "ScriptableObjects/LevelGroup", order = 1)]
public class LevelPackage : ScriptableObject
{
    public TextAsset[] levels;

    public Color color; // en cada paquete los colores cambian

    public Sprite botonNoPulsado;
    public Sprite botonPulsado;
    public Sprite levelNoPulsado;
    public Sprite levelPulsado;

    public string nameLevels;

}
