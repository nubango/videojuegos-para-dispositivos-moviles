using System.Collections.Generic;
using System.IO;
using UnityEngine;

[System.Serializable]
public class SaveProgress
{
    public int hints;
    public List<int> levels;
    public bool ads;

    public static void SaveProgressToJSON(SaveProgress progress, string file)
    {
        string progressToString = JsonUtility.ToJson(progress);
        File.WriteAllText(Application.persistentDataPath + file, progressToString);
    }

    public static SaveProgress LoadProgressFromJSON(string file, SaveProgress progress)
    {
        if (File.Exists(Application.persistentDataPath + file))
        {
            string jsonData = File.ReadAllText(Application.persistentDataPath + file);
            progress = JsonUtility.FromJson<SaveProgress>(jsonData);
        }
        return progress;
    }
}
