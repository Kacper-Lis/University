using System.Collections;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class Notification : MonoBehaviour
{
    private void Awake()
    {
        foreach (CanvasRenderer renderer in GetComponentsInChildren<CanvasRenderer>())
        {
            renderer.SetAlpha(0);
        }
        GetComponent<RectTransform>().anchoredPosition = new Vector3(0, -50f, 0);
    }

    public void TriggerNotification(string text) 
    {
        GetComponentInChildren<TextMeshProUGUI>().text = text;
        StartCoroutine(PopUp());
    }

    private IEnumerator PopUp() 
    {
        Image image = GetComponentInChildren<Image>();
        TextMeshProUGUI text = GetComponentInChildren<TextMeshProUGUI>();
        text.CrossFadeAlpha(1f, 1f, false);
        image.CrossFadeAlpha(1f, 1f, false);
        yield return new WaitForSeconds(2f);
        text.CrossFadeAlpha(0f, 1f, false);
        image.CrossFadeAlpha(0f, 1f, false);
    }
}
