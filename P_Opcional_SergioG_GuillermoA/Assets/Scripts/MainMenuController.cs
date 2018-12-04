using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MainMenuController : MonoBehaviour {

    // Use this for initialization


    public GameObject button;

    public GameObject selectLevelMenu;

    public GameObject[] difficulties;

    public GameObject dialog;

    public PuzzleGenerator puzzleGenerator;

    void Start () {


    }
	
	// Update is called once per frame
	void Update () {
		
	}


    public void PlayButton()
    {

        button.SetActive(false);
        selectLevelMenu.SetActive(true);

    }

    public void selectLevel(int i)
    {
        selectLevelMenu.SetActive(false);
        puzzleGenerator.init(i);
        

    }

    public void nextButton(int id)
    {
        for (int i = 0; i< difficulties.Length; i++)
        {
            difficulties[i].SetActive(false);
        }

        difficulties[id].SetActive(true);
    }


    public void repeatButton()
    {
        puzzleGenerator.repeatLevel();
        dialog.SetActive(false);
    }

    public void nextLevelButton()
    {
        puzzleGenerator.nextLevel();
        dialog.SetActive(false);
    }

    public void home()
    {
        dialog.SetActive(false);
        button.SetActive(true);
    }
}
