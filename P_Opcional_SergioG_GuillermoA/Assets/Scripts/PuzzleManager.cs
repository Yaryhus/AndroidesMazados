using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PuzzleManager : MonoBehaviour {


    public bool win = false;

    public bool lose = false;

    public GameObject[] balls;

    public GameObject[] redBalls;


    // Use this for initialization
    void Start () {


		
	}
	
	// Update is called once per frame
	void Update () {

        for (int i = 0; i < redBalls.Length; i++)
        {
            if (redBalls[i].GetComponent<Ball>().Scored == true)
            {
                lose = true;
                return;
            }
        }


        for (int i = 0; i < balls.Length; i++) {
            if (balls[i].GetComponent<Ball>().Scored == false)
            {
                return;
            }
        }
        win = true;


    }
}
