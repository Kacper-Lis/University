using UnityEngine;

public class Group : MonoBehaviour
{
    private Transform[] children;
    private GameObject shadow;
    private Transform[] shadowChildren;

    private void Awake()
    {
        children = GetComponentsInChildren<Transform>();
        if (DataManager.instance.currentProfile.color != 0)
        {
            int color = DataManager.instance.currentProfile.color;
            foreach (SpriteRenderer s in GetComponentsInChildren<SpriteRenderer>())
            {
                s.color = color == 1 ? new Color(0, 0, 1, 1) : new Color(1, 0, 0, 1);
            }
        }
    }

    private void createShadow()
    {
        shadow = Instantiate(this.gameObject);
        shadow.GetComponent<Group>().enabled = false;
        foreach (SpriteRenderer s in shadow.GetComponentsInChildren<SpriteRenderer>())
        {
            s.color = new Color(s.color.r, s.color.g, s.color.b, 0.3f);
        }
        shadowChildren = shadow.GetComponentsInChildren<Transform>();
        castShadow();
    }

    private void OnEnable()
    {
        GameEvents.OnChangePos += changePos;
        GameEvents.OnCastShadow += castShadow;
    }

    private void OnDisable()
    {
        GameEvents.OnChangePos -= changePos;
        GameEvents.OnCastShadow -= castShadow;
    }

    private void changePos(Vector3 v)
    {
        GameEvents.OnRemoveBlocks?.Invoke(children);
        if (v != Vector3.zero)
            transform.position += v;
        else if (gameObject.tag != "cube")
            transform.Rotate(0, 0, -90);
        else
            return;

        if (GameEvents.OnTryMoving?.Invoke(children) == true)
        {
            return;
        }
        else if (v == Vector3.down) 
        {
            transform.position -= v;
            Destroy(shadow);
            transform.DetachChildren();
            GameEvents.OnFinishGroup.Invoke(children);
            Destroy(gameObject);
            return;
        }


        if (v != Vector3.zero)
            transform.position -= v;
        else
            transform.Rotate(0, 0, 90);

        GameEvents.OnSetBlocks.Invoke(children);
    }

    private void castShadow() 
    {
        shadow.transform.rotation = this.transform.rotation;
        shadow.transform.position = this.transform.position;
        bool canMove = true;
        while (canMove)
        {
            shadow.transform.position += Vector3.down;
            if (!GameEvents.OnCheckPosition.Invoke(shadowChildren)) 
            {
                canMove = false;
                shadow.transform.position -= Vector3.down;
            }
        }
    }

    public void EnableGroup()
    {
        this.enabled = true;
        if(shadow == null)
            createShadow();
    }

    public void DisableGroup()
    {
        Destroy(shadow);
        this.enabled = false;
    }

    private void OnDestroy()
    {
        Destroy(shadow);
    }
}
