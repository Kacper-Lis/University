using UnityEngine;
using System;
public static class GameEvents
{
    //Signal group to move
    public static Action<Vector3> OnChangePos;
    public static Action<int> OnUsePowerUp;
    public static Action OnCastShadow;
    //Group movement on the board
    public static Predicate<Transform[]> OnTryMoving;
    public static Action<Transform[]> OnRemoveBlocks;
    public static Action<Transform[]> OnSetBlocks;
    public static Action<Transform[]> OnFinishGroup;
    public static Func<Transform[], bool> OnCheckPosition;
    public static Action OnDestroyTopRow;

    public static Action OnHoldBlock;
    public static Action<int> OnIncreaseScore;
    public static Func<int> OnGetScore;
    public static Func<int> OnGetBonusPoints;
    public static Action<int> OnChangeBonusPoints;
    public static Func<int> OnGetPowerUpThreshold;
    public static Action OnLeaveGame;
    public static Action OnGameOver;
}
