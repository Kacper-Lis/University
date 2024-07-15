using UnityEngine;

public class MockAPIController : MonoBehaviour
{
    public static MockAPIController instance { get; private set; }

    private int apiKey = 12415; //Example identification key. Shouldn't be stored inside the code
    private MockAPI api;

    private void Awake()
    {
        if (instance == null)
        {
            instance = this;
            DontDestroyOnLoad(this);
            TryEstablishingConnectionWithAPI();
        }
        else
        {
            Destroy(gameObject);
        }
    }

    private void TryEstablishingConnectionWithAPI() 
    {
        try
        {
            MockAPI.Init(apiKey);
        }
        catch (System.Exception e)
        {
            Debug.Log("Mock API failed to respond: " + e.Message);
        }
    }

    private void OnApplicationQuit()
    {
        MockAPI.ShutDown();
    }
}
