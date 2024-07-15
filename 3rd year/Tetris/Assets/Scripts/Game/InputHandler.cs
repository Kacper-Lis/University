using UnityEngine;

public class InputHandler : MonoBehaviour
{
    private float lastKeyDown;
    private float timeKeyPressed;

    private void Update()
    {
        if (GetKey(KeyCode.LeftArrow))
        {
            GameEvents.OnChangePos?.Invoke(Vector3.left);
        }
        if (GetKey(KeyCode.RightArrow))
        {
            GameEvents.OnChangePos?.Invoke(Vector3.right);
        }
        if (GetKey(KeyCode.DownArrow))
        {
            GameEvents.OnChangePos?.Invoke(Vector3.down);
        }
        if (GetKey(KeyCode.UpArrow))
        {
            GameEvents.OnChangePos?.Invoke(Vector3.zero);
        }
        if (GetKey(KeyCode.Q))
        {
            GameEvents.OnHoldBlock?.Invoke();
        }
        if (GetKey(KeyCode.Escape)) 
        {
            GameEvents.OnLeaveGame?.Invoke();
        }
        if (GetKey(KeyCode.Alpha1))
        {
            GameEvents.OnUsePowerUp?.Invoke(1);
            Debug.Log(KeyCode.Alpha1);
        }
        if (GetKey(KeyCode.Alpha2))
        {
            GameEvents.OnUsePowerUp?.Invoke(2);
        }
    }

    private bool GetKey(KeyCode key)
    {
        bool keyDown = Input.GetKeyDown(key);
        bool pressed = Input.GetKey(key) && Time.time - lastKeyDown > 0.5f && Time.time - timeKeyPressed > 0.05f;

        if (keyDown)
        {
            lastKeyDown = Time.time;
        }
        if (pressed)
        {
            timeKeyPressed = Time.time;
        }

        return keyDown || pressed;
    }
}
