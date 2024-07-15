using UnityEngine;
using TMPro;
using System.Collections.Generic;

public class Spawner : MonoBehaviour
{
    [SerializeField] private GameObject[] groups;
    [SerializeField] private Transform holdObject;
    [SerializeField] private Transform nextObject;

    private List<GameObject> bagOfBlocks;
    private GameObject currentBlock;
    private GameObject previous;
    private GameObject next;
    private float timeElapsed;
    private float fallTime;
    private bool holding;
    private bool end = false;

    private void Awake()
    {
        if (DataManager.instance.currentProfile.color != 0) 
        {
            int color = DataManager.instance.currentProfile.color;
            foreach (SpriteRenderer s in GetComponentsInChildren<SpriteRenderer>())
            {
                s.color = color == 1 ? new Color(0, 0, 1, 1) : new Color(1, 0, 0, 1);
            }
            GameObject canvas = GameObject.Find("Canvas");
            foreach (TextMeshProUGUI text in canvas.GetComponentsInChildren<TextMeshProUGUI>()) 
            {
                text.color = color == 1 ? new Color(0, 0, 1, 1) : new Color(1, 0, 0, 1);
            }
        }
    }

    private void OnEnable()
    {
        GameEvents.OnHoldBlock += holdBlock;
        GameEvents.OnLeaveGame += backToMenu;
        GameEvents.OnUsePowerUp += usePowerUp;
    }

    private void OnDisable()
    {
        GameEvents.OnHoldBlock -= holdBlock;
        GameEvents.OnLeaveGame -= backToMenu;
        GameEvents.OnUsePowerUp -= usePowerUp;
    }

    private void Start()
    {
        bagOfBlocks = new List<GameObject>();
        spawnNext();
        timeElapsed = Time.time;
    }

    private void Update()
    {
        if (end)
            return;

        if (currentBlock == null) 
        {
            holding = false;
            enableBlock(next);
            currentBlock = next;
            spawnNext();
        }
        if (timeElapsed + fallTime < Time.time && currentBlock != null)
        {
            timeElapsed = Time.time;
            GameEvents.OnChangePos.Invoke(Vector3.down);
        }
    }

    private void spawnNext()
    {
        if (bagOfBlocks.Count == 0)
        {
            fillBagOfBlocks();
        }
        int i = Random.Range(0, bagOfBlocks.Count);
        GameObject nextGroup = bagOfBlocks[i];
        bagOfBlocks.RemoveAt(i);

        next = Instantiate(nextGroup, nextObject.position, Quaternion.identity);
        changeLevel();
    }

    private void fillBagOfBlocks()
    {
        foreach (GameObject group in groups)
        {
            bagOfBlocks.Add(group);
        }
    }

    private void holdBlock()
    {
        if (previous == null)
        {
            previous = currentBlock;
            disableBlock(previous);
            currentBlock = null;
            return;
        }
        if (!holding) 
        {
            holding = true;
            GameObject temp = previous;
            previous = currentBlock;
            disableBlock(previous);
            currentBlock = temp;
            enableBlock(currentBlock);
        }
    }

    private void disableBlock(GameObject block) 
    {
        GameEvents.OnRemoveBlocks.Invoke(block.GetComponentsInChildren<Transform>());
        block.GetComponent<Group>().DisableGroup();
        block.transform.rotation = Quaternion.identity;
        if (block.name == "GroupI(Clone)")
        {
            block.transform.position = new Vector2(holdObject.position.x - 1, holdObject.position.y);
        }
        else
        {
            block.transform.position = holdObject.position;
        }
    }

    private void enableBlock(GameObject block)
    {
        block.transform.position = transform.position;
        Transform[] blocks = block.GetComponentsInChildren<Transform>();
        if (!GameEvents.OnCheckPosition.Invoke(blocks))
        {
            gameOver();
            return;
        }
        block.GetComponent<Group>().EnableGroup();
    }

    private void gameOver()
    {
        GameEvents.OnGameOver?.Invoke();
        end = true;
    }

    private void usePowerUp(int powerUp) 
    {
        if (end)
            return;
        int threshold = GameEvents.OnGetPowerUpThreshold.Invoke();
        switch (powerUp)
        {
            case 1:
                smallPowerUp(threshold);
                break;
            case 2:
                bigPowerUp(threshold * 2);
                break;
        }
    }

    private void smallPowerUp(int threshold)
    {
        if (GameEvents.OnGetBonusPoints.Invoke() >= threshold)
        {
            GameEvents.OnChangeBonusPoints?.Invoke(-threshold);
            GameEvents.OnRemoveBlocks.Invoke(currentBlock.GetComponentsInChildren<Transform>());
            Destroy(currentBlock);
        }
    }

    private void bigPowerUp(int threshold)
    {
        if (GameEvents.OnGetBonusPoints.Invoke() >= threshold)
        {
            GameEvents.OnChangeBonusPoints?.Invoke(-threshold);
            GameEvents.OnDestroyTopRow?.Invoke();
        }
    }

    private void backToMenu()
    {
        int score = GameEvents.OnGetScore.Invoke();
        DataManager.instance.UpdateHighScore(score);
        SceneController.instance.LoadLevel("Menu");
    }

    private void changeLevel() 
    {
        int level = GameEvents.OnGetScore.Invoke() / 10;
        fallTime = GameLevel.GetLevelSpeed(level);
    }
}
