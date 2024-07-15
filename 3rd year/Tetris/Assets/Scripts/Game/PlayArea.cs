using UnityEngine;

public class PlayArea : MonoBehaviour
{
    [SerializeField] private int width = 10;
    [SerializeField] private int height = 20;
    public Transform[,] grid;

    private void Awake()
    {
        grid = new Transform[width, height+1];
    }

    private void OnEnable()
    {
        GameEvents.OnTryMoving += tryMove;
        GameEvents.OnRemoveBlocks += removeBlocks;
        GameEvents.OnSetBlocks += setBlocks;
        GameEvents.OnFinishGroup += proccessRows;
        GameEvents.OnCheckPosition += checkPosition;
        GameEvents.OnDestroyTopRow += destroyTopRow;
    }

    private void OnDisable()
    {
        GameEvents.OnTryMoving -= tryMove;
        GameEvents.OnRemoveBlocks -= removeBlocks;
        GameEvents.OnSetBlocks -= setBlocks;
        GameEvents.OnFinishGroup -= proccessRows;
        GameEvents.OnCheckPosition -= checkPosition;
        GameEvents.OnDestroyTopRow -= destroyTopRow;
    }

    private Vector2 roundVec2(Vector2 v)
    {
        return new Vector2(Mathf.Round(v.x),
                           Mathf.Round(v.y));
    }

    private bool insideBorder(Vector2 pos)
    {
        return ((int)pos.x >= 0 &&
                (int)pos.x < width &&
                (int)pos.y >= 0 &&
                (int)pos.y <= height);
    }

    private void deleteRow(int y)
    {
        for (int x = 0; x < width; x++)
        {
            if (grid[x, y] != null && grid[x, y].parent == null) 
            {
                Destroy(grid[x, y].gameObject);
                grid[x, y] = null;
            }
        }
    }

    private bool decreaseRow(int y)
    {
        bool emptyRow = true;
        for (int x = 0; x < width; x++)
        {
            if (grid[x, y] != null)
            {
                // Move one towards bottom
                grid[x, y - 1] = grid[x, y];
                grid[x, y] = null;

                // Update Block position
                grid[x, y - 1].position += new Vector3(0, -1, 0);
                emptyRow = false;
            }
        }
        return emptyRow;
    }

    //Must be given deleted row
    private void decreaseRowsAbove(int y)
    {
        bool ceiling = false;
        while (!ceiling)
        {
            y++;
            ceiling = decreaseRow(y);
        }
    }

    private bool isRowFull(int y)
    {
        for (int x = 0; x < width; x++)
            if (grid[x, y] == null)
                return false;
        return true;
    }

    //List needs to be from top to bottom
    private void proccessRows(Transform[] blocks)
    {
        int[] yCords = setBlocks(blocks, true);
        int rowCounter = 0;
        for (int i = 0; i < yCords.Length; i++)
        {
            if (isRowFull(yCords[i]))
            {
                rowCounter++;
                deleteRow(yCords[i]);
                decreaseRowsAbove(yCords[i]);
            }
        }
        GameEvents.OnIncreaseScore.Invoke(rowCounter);
    }

    private void removeBlock(int x, int y)
    {
        grid[x, y] = null;
    }

    private void setBlock(int x, int y, Transform block)
    {
        grid[x, y] = block;
    }

    private void setBlocks(Transform[] blocks)
    {
        for (int i = 0; i < blocks.Length; i++)
        {
            Vector2 v = roundVec2(blocks[i].position);
            setBlock((int)v.x, (int)v.y, blocks[i]);
        }
    }

    private int[] setBlocks(Transform[] blocks, bool returnCoords)
    {
        int[] yCoords = new int[blocks.Length];
        for (int i = 0; i < blocks.Length; i++)
        {
            Vector2 v = roundVec2(blocks[i].position);
            setBlock((int)v.x, (int)v.y, blocks[i]);
            yCoords[i] = (int)v.y;
        }
        System.Array.Sort(yCoords, (a, b) => b.CompareTo(a));
        return yCoords;
    }

    private void removeBlocks(Transform[] blocks)
    {
        for (int i = 0; i < blocks.Length; i++)
        {
            Vector2 v = roundVec2(blocks[i].position);
            removeBlock((int)v.x, (int)v.y);
        }
    }

    private bool tryMove(Transform[] blocks)
    {
        //int[] yCoords = new int[blocks.Length];
        for (int i = 0; i < blocks.Length; i++)
        {
            if (!isValidPosition(blocks[i]))
                return false;
            //yCoords[i] = (int)Mathf.Round(blocks[i].position.y);
        }
        GameEvents.OnCastShadow?.Invoke();
        setBlocks(blocks);
        return true;
    }

    private bool checkPosition(Transform[] blocks)
    {
        for (int i = 0; i < blocks.Length; i++)
            if (!isValidPosition(blocks[i]))
                return false;
        return true;
    }

    private bool isValidPosition(Transform block)
    {
        Vector2 v = roundVec2(block.position);
        if (!insideBorder(v))
            return false;

        if (grid[(int)v.x, (int)v.y] != null)
            return false;

        return true;
    }

    private void destroyTopRow() 
    {
        for (int i = height - 1; i >= 0; i--)
        {
            for (int j = 0; j < width; j++)
            {
                if (grid[j, i] != null && grid[j, i].parent == null) 
                {
                    deleteRow(i);
                    return;
                }
            }
        }
    }
}
