using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CamaraRotator : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {

        transform.localEulerAngles = new Vector3(Input.acceleration.x, Input.acceleration.y, Input.acceleration.z);
    }
}
