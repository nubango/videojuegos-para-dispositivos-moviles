using UnityEngine;

[CreateAssetMenu(fileName = "Data", menuName = "ScriptableObjects/LevelGroup", order = 1)]
public class LevelPackage : ScriptableObject
{
    public TextAsset[] levels;

    Color color; // en cada paquete los colores cambian

    // los distintos botones de inicio tambien para que "se monte solo el menu"

}
