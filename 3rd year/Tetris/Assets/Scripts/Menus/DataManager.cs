using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

public class DataManager : MonoBehaviour
{
    public static DataManager instance { get; private set; }

    public List<Profile> profiles { get; private set; }
    public Profile currentProfile { get; set; }
    public int highestScore { get; set; }

    private void Awake()
    {
        if (instance == null)
        {
            instance = this;
            DontDestroyOnLoad(this);
            loadProfiles();
        }
        else
        {
            Destroy(gameObject);
        }
    }

    private void loadProfiles()
    {
        string fullPath = Path.Combine(Application.persistentDataPath, "profiles");
        try
        {
            ProfileCollection result = JsonUtility.FromJson<ProfileCollection>(File.ReadAllText(fullPath));
            profiles = result.profiles;
            if (profiles.Count > 0)
                ChooseProfile(profiles.Count - 1);
            else
                createSampleProfile();
                
                
        }
        catch (System.Exception e)
        {
            profiles = new List<Profile>();
            Debug.LogError($"Failed to load {fullPath} : {e}");
        }

        highestScore = 0;
        foreach (Profile p in profiles)
        {
            if (p.highscore > highestScore)
            {
                highestScore = p.highscore;
            }
        }
    }

    private void saveProfiles() 
    {
        string fullPath = Path.Combine(Application.persistentDataPath, "profiles");
        try
        {
            ProfileCollection saveData = new ProfileCollection();
            saveData.profiles = profiles;
            File.WriteAllText(fullPath, JsonUtility.ToJson(saveData));
        }
        catch (System.Exception e)
        {
            Debug.LogError($"Failed to save profiles {fullPath} : {e}");
        }
    }

    public void AddProfile(Profile profile)
    {
        profile.achievements = new List<string>();
        profile.highscore = 0;
        profiles.Add(profile);
    }

    public void AddAchievement(string name) 
    {
        currentProfile.achievements.Add(name);
    }

    public void UpdateHighScore(int score) 
    {
        if (currentProfile.highscore < score) 
        {
            currentProfile.highscore = score;
        }
    }

    public void UpdateColor(int color)
    {
        currentProfile.color = color;
    }

    public void ChooseProfile(int index)
    {
        currentProfile = profiles[index];
    }

    private void createSampleProfile()
    {
        Profile prof = new Profile();
        prof.name = "Anon";
        prof.color = 0;
        AddProfile(prof);
        ChooseProfile(0);
    }

    private void OnApplicationQuit()
    {
        int score = GameEvents.OnGetScore?.Invoke() != null ? (int)GameEvents.OnGetScore?.Invoke() : -1;
        UpdateHighScore(score);
        saveProfiles();
    }
}

[System.Serializable]
public class ProfileCollection 
{
    public List<Profile> profiles;
}

[System.Serializable]
public class Profile 
{
    public string name;
    public List<string> achievements;
    public int highscore;
    public int color;
}