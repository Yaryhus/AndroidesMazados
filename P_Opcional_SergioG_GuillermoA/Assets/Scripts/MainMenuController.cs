using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine;
using System;

public class MainMenuController : MonoBehaviour {

    // Use this for initialization


    public GameObject button;

    public GameObject selectLevelMenu;

    public GameObject[] difficulties;

    public GameObject[] levels;

    public GameObject dialog;

    public GameObject inGame;

    public GameObject next;

    public GameObject message;

    public PuzzleGenerator puzzleGenerator;

    void Start () {


    }
	
	// Update is called once per frame
	void Update () {
		
	}


    public void PlayButton()
    {
        for (int j = 0; j < levels.Length; j++)
        {
            levels[j].GetComponent<Button>().interactable = true;
            levels[j].transform.GetChild(0).gameObject.SetActive(true);
    
        }

        for (int j = puzzleGenerator.level+1; j < levels.Length; j++)
        {
            levels[j].GetComponent<Button>().interactable = false;
            levels[j].transform.GetChild(0).gameObject.SetActive(false);
            Debug.Log("HOLA");
        }
        button.SetActive(false);
        selectLevelMenu.SetActive(true);



    }

    public void selectLevel(int i)
    {

      

        selectLevelMenu.SetActive(false);
        inGame.SetActive(true);
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
        inGame.SetActive(true);
    }

    public void nextLevelButton()
    {
        puzzleGenerator.nextLevel();
        dialog.SetActive(false);
        inGame.SetActive(true);
    }

    public void home()
    {
        //Destroy(puzzleGenerator.ActualMap);
        inGame.SetActive(false);
        dialog.SetActive(false);
        button.SetActive(true);
    }

    internal void lose()
    {
        dialog.SetActive(true);
        inGame.SetActive(false);
        next.SetActive(false);
        message.GetComponent<Text>().text = "You lose!";
    }

    public void pause()
    {
        inGame.SetActive(false);
        dialog.SetActive(true);
        message.GetComponent<Text>().text = "Pause menu";
        Destroy(puzzleGenerator.ActualMap);
        // if(puzzleGenerator.level>= puzzleGenerator.id)
        next.SetActive(false);
    }

    public void erase()
    {
        SaveGame.Instance.Level = 0;
        SaveGame.Save();
        puzzleGenerator.level = 0;
        PlayButton();
    }

    internal void win()
    {
        next.SetActive(true);
        dialog.SetActive(true);
        inGame.SetActive(false);
        message.GetComponent<Text>().text = "You win";
    }
}
