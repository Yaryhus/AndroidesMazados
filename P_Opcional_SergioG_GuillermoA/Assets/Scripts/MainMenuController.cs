using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MainMenuController : MonoBehaviour {

    // Use this for initialization

    public GameObject generator;
    public GameObject button;


	void Start () {


    }
	
	// Update is called once per frame
	void Update () {
		
	}


    public void PlayButton(int i)
    {
        generator.GetComponent<PuzzleGenerator>().init(i);
        button.SetActive(false);

    }
}
