using UnityEngine;

public class MockAchievementService : MonoBehaviour, IAchievement
{

    public void UnlockAchievement(string ID) 
    {
        MockAPI.triggerAchievement(ID);
    } 
}
