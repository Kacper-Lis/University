using System.Collections;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    public void ExitButton() {
        Application.Quit();
    }

    public void StartGame()
    {
        SceneController.instance.LoadLevel("GameScene");
    }

    public void OptionsButton() 
    {
        Object.FindObjectOfType<AchievementService>().UnlockAchievement("Discover");
    }
}
