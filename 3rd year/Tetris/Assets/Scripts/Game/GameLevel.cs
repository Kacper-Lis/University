using System.Collections.Generic;

public class GameLevel
{
    private static Dictionary<int, float> gameLevels = new Dictionary<int, float>() 
    {
        { 0, 2f },
        { 1, 1.46f },
        { 2, 0.92f },
        { 3, 0.59f },
        { 4, 0.26f },
        { 5, 0.18f },
        { 6, 0.125f },
        { 7, 0.088f },
        { 8, 0.064f },
        { 9, 0.05f },
        { 10, 0.035f },
        { 11, 0.027f },
        { 12, 0.021f },
        { 13, 0.016f },
    };

    private static float maxLevel = 0.016f;

    public static float GetLevelSpeed(int level) 
    {
        if (gameLevels.ContainsKey(level))
            return gameLevels[level];
        return maxLevel;
    }
}
