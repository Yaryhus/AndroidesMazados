using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CamaraRotator : MonoBehaviour {
    Vector3 dir = Vector3.zero;
    int isFixed = 0;
    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        if (isFixed == 1)
        {
            dir.x = -Input.acceleration.y;
            dir.z = Input.acceleration.x;

            // clamp acceleration vector to unit sphere
            if (dir.sqrMagnitude > 1)
                dir.Normalize();

            transform.localEulerAngles = dir;
        }
        else
        {
            dir = new Vector3(90, 0, 0);
            transform.localEulerAngles = dir;

        }
     //  transform.localEulerAngles = new Vector3(-Input.acceleration.y, Input.acceleration.x, Input.acceleration.z);
    }

    internal void setGameMode(int id)
    {

        
            isFixed = id;
    }
}
