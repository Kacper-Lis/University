using System.Collections.Generic;
using UnityEngine;
using TMPro;
using System.Text;

public class LeaderboardMenu : MonoBehaviour
{
    [SerializeField] private TextMeshProUGUI titleText;
    [SerializeField] private TextMeshProUGUI mainText;
    [SerializeField] private TextMeshProUGUI buttonText;

    private bool achievements = false; 

    private void Awake()
    {
        displayLeaderboard();
    }

    private void OnEnable()
    {
        achievements = false;
        displayLeaderboard();
    }

    public void ChangeText() 
    {
        if (achievements)
        {
            displayLeaderboard();
        }
        else
        {
            displayAchievements();
        }
    }

    private void displayLeaderboard()
    {
        List<Profile> profiles = new List<Profile>(DataManager.instance.profiles);
        profiles.Sort(delegate (Profile p1, Profile p2) { return p2.highscore.CompareTo(p1.highscore); });
        StringBuilder profilesFormated = new StringBuilder("");
        foreach (Profile p in profiles)
        {
            profilesFormated.AppendLine(p.name + " " + p.highscore);
        }
        mainText.text = profilesFormated.ToString();
        titleText.text = "Leaderboard";
        achievements = false;
        buttonText.text = "ACHIEVEMENTS";
    }

    private void displayAchievements()
    {
        StringBuilder achievementsFormated = new StringBuilder("");
        Profile profile = DataManager.instance.currentProfile;
        achievementsFormated.AppendLine(profile.name + " " + profile.highscore);
        achievementsFormated.AppendLine("=====ACHIEVEMENTS=====");
        foreach (string ach in profile.achievements)
        {
            achievementsFormated.AppendLine(ach);
        }
        mainText.text = achievementsFormated.ToString();
        titleText.text = "Achievements";
        achievements = true;
        buttonText.text = "LEADERBOARD";
    }
}
