using UnityEngine;
using System.Collections.Generic;

public class MockAPI
{
    private static Dictionary<string, Achievement> achievements = new Dictionary<string, Achievement>();

    public static void Init(int apiKey) 
    {
        Debug.Log("Establishing conection with external API, key: " + apiKey);
        achievements.Add("Discover", new Achievement("Discovery", "Explore the menu"));
    }

    public static void ShutDown() 
    {
        Debug.Log("Disconnecting from the API");
    }

    public static void triggerAchievement(string ID) 
    {
        if (!achievements[ID].Unlocked)
        {
            GameObject.Find("Notification").GetComponent<Notification>().TriggerNotification(achievements[ID].ToString());
            achievements[ID].Unlocked = true;
        }
    }
}

class Achievement
{
    public Achievement(string name, string description)
    {
        Name = name;
        Description = description;
        Unlocked = false;
    }

    public string Name { get; }
    public string Description { get; }
    public bool Unlocked { get; set; }

    public override string ToString() 
    {
        return Name + "\n" + Description;
    }
}