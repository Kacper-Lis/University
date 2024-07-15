using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneController : MonoBehaviour
{
    public static SceneController instance { get; private set; }

    [SerializeField] private Canvas canvas;

    [SerializeField] private AudioClip menuMusic;
    [SerializeField] private AudioClip gameMusic;
    private AudioSource source;

    private Animator animator;

    private void Awake()
    {
        if (instance == null)
        {
            instance = this;
            canvas.transform.position = new Vector3(0, 0, -1);
            animator = GetComponentInChildren<Animator>();
            source = GetComponent<AudioSource>();
            DontDestroyOnLoad(this);
        }
        else
        {
            Destroy(gameObject);
        }
    }

    public void LoadLevel(string ID) 
    {
        StartCoroutine(LoadLevelAnimation(ID));
    }

    public IEnumerator LoadLevelAnimation(string ID)
    {
        animator.SetTrigger("TriggerTransition");
        yield return new WaitForSeconds(1f);
        if (ID == "Menu")
            PlayMusic(menuMusic);
        else
            PlayMusic(gameMusic);
        SceneManager.LoadScene(ID);
    }

    public IEnumerator LoadLevelAnimation(string ID, AudioClip clip)
    {
        animator.SetTrigger("TriggerTransition");
        yield return new WaitForSeconds(1f);
        PlayMusic(clip);
        SceneManager.LoadScene(ID);
    }

    public void PlayMusic(AudioClip clip)
    {
        source.Stop();
        source.clip = clip;
        source.Play();
    }
}
