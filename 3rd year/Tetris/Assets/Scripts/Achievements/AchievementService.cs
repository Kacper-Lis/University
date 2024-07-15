using UnityEngine;

public class AchievementService : MonoBehaviour
{
    private IAchievement achievementImplementation;

    private void Awake()
    {
        achievementImplementation = GetComponent<IAchievement>();
    }

    public void UnlockAchievement(string ID)
    {
        if (!DataManager.instance.currentProfile.achievements.Contains(ID)) 
        {
            achievementImplementation.UnlockAchievement(ID);
            DataManager.instance.AddAchievement(ID);
        }
    }
}
