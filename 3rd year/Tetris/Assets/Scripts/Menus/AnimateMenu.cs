using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AnimateMenu : MonoBehaviour
{
    private Animator animator;

    private void Awake()
    {
        animator = GetComponent<Animator>();
    }

    public void TransitionAnimation() 
    {
        animator.SetTrigger("TriggerTransition");
    }

    public void Enable(GameObject obj)
    {
        StartCoroutine(delayTransition(obj, true));
    }

    public void Disable(GameObject obj)
    {
        StartCoroutine(delayTransition(obj, false));
    }

    private IEnumerator delayTransition(GameObject obj, bool state)
    {
        yield return new WaitForSeconds(0.25f);
        obj.SetActive(state);
    }
}
