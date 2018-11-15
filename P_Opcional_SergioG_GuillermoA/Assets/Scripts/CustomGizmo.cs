using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CustomGizmo : MonoBehaviour {

    public Vector4 color = new Vector4(0.2f, 1f, 0.5f, 0.1f);

    // Use this for initialization
    void Start () {
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    private void OnDrawGizmos()
    {
        // Draw a yellow sphere at the transform's position

        Gizmos.color = color;
        Gizmos.DrawCube(transform.position, this.GetComponent<Collider>().bounds.size);
        
    }
}
