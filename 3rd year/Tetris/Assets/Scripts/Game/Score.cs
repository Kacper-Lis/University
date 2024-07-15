using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class Score : MonoBehaviour
{
    [SerializeField] private TextMeshProUGUI scoreText;
    [SerializeField] private TextMeshProUGUI bonusText;
    [SerializeField] private TextMeshProUGUI gameOverText;
    [SerializeField] private Image firstBonus;
    [SerializeField] private Image secondBonus;

    private int score;
    private int bonusPoints;
    private int firstBonusThreshold = 5;

    private void Start()
    {
        score = 0;
        bonusPoints = 0;
        scoreText.text = "Score" + "\n" + score;
        firstBonus.color = new Color(firstBonus.color.r, firstBonus.color.g, firstBonus.color.b, 0.31f);
        secondBonus.color = new Color(secondBonus.color.r, secondBonus.color.g, secondBonus.color.b, 0.62f);
        changeFill();
    }

    private void OnEnable()
    {
        GameEvents.OnIncreaseScore += changeScore;
        GameEvents.OnGetScore += getScore;
        GameEvents.OnGetBonusPoints += getBonusPoints;
        GameEvents.OnChangeBonusPoints += changeBonusPoints;
        GameEvents.OnGetPowerUpThreshold += getBonusTreshold;
        GameEvents.OnGameOver += gameOver;
    }

    private void OnDisable()
    {
        GameEvents.OnIncreaseScore -= changeScore;
        GameEvents.OnGetScore -= getScore;
        GameEvents.OnGetBonusPoints -= getBonusPoints;
        GameEvents.OnChangeBonusPoints -= changeBonusPoints;
        GameEvents.OnGetPowerUpThreshold -= getBonusTreshold;
        GameEvents.OnGameOver -= gameOver;
    }

    private void gameOver()
    {
        gameOverText.gameObject.SetActive(true);
        gameOverText.color = Color.white;
    }

    private int getBonusTreshold()
    {
        return firstBonusThreshold;
    }

    private int getScore()
    {
        return score;
    }

    private int getBonusPoints()
    {
        return bonusPoints;
    }

    private void changeBonusPoints(int value) 
    {
        bonusPoints += value;
        if (bonusPoints < 0)
            bonusPoints = 0;
        else if(bonusPoints > firstBonusThreshold*2)
            bonusPoints = firstBonusThreshold*2;
        changeFill();
    }

    private void changeScore(int rowsDestroyed)
    {
        int points = 0;
        switch (rowsDestroyed) 
        {
            case 1:
                points = 1;
                break;
            case 2:
                points = 3;
                break;
            case 3:
                points = 5;
                break;
            case 4:
                points = 8;
                break;
        }
        score += points;
        int bonusPointsToAdd = rowsDestroyed > 1 ? rowsDestroyed - 1 : 0;
        changeBonusPoints(bonusPointsToAdd);
        scoreText.text = "Score" + "\n" + score;
    }

    private void changeFill()
    {
        bonusText.text = "" + bonusPoints;
        
        if (bonusPoints <= firstBonusThreshold) 
        {
            float bonusFill = (float)(bonusPoints) / firstBonusThreshold;
            firstBonus.fillAmount = Mathf.Clamp(bonusFill, 0f, 1f);
            secondBonus.fillAmount = 0f;
        }
        else
        {
            float bonusFill = (float)(bonusPoints - firstBonusThreshold) / firstBonusThreshold;
            firstBonus.fillAmount = 1f;
            secondBonus.fillAmount = Mathf.Clamp(bonusFill, 0f, 1f);
        }
    }
}
