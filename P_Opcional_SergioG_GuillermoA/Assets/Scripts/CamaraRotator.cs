using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CamaraRotator : MonoBehaviour {
    Vector3 dir = Vector3.zero;
    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        dir.x = -Input.acceleration.y;
        dir.z = Input.acceleration.x;

        // clamp acceleration vector to unit sphere
        if (dir.sqrMagnitude > 1)
            dir.Normalize();

        transform.localEulerAngles = dir;
     //  transform.localEulerAngles = new Vector3(-Input.acceleration.y, Input.acceleration.x, Input.acceleration.z);
    }
}
