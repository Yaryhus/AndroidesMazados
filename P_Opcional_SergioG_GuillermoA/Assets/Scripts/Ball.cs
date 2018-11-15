using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;

public class Ball : MonoBehaviour, ITrackableEventHandler
{

    private TrackableBehaviour mTrackableBehaviour;

    public GameObject Floor;
    public GameObject Spawn;

    public bool Scored = false;

    Vector3 savedBallposition;


    // Use this for initialization
    void Start () {

        mTrackableBehaviour = GetComponent<TrackableBehaviour>();

        if (mTrackableBehaviour)
        {
            mTrackableBehaviour.RegisterTrackableEventHandler(this);
        }

        transform.position = Spawn.transform.position;

    }

    // Update is called once per frame
    void Update () {

        //Guardamos la posicion de la pelota
        savedBallposition = transform.position;

        //Si se saliese del mapa
        if (transform.position.y < Floor.transform.position.y - 10)
        {
            transform.position = Spawn.transform.position;         
        }


		
	}

    public void OnTrackableStateChanged(
              TrackableBehaviour.Status previousStatus,
              TrackableBehaviour.Status newStatus)
    {
        if (newStatus == TrackableBehaviour.Status.DETECTED ||
            newStatus == TrackableBehaviour.Status.TRACKED)
        {
            OnTrackingFound();
        }

    }
    private void OnTrackingFound()
    {
        transform.position = Spawn.transform.position;
        //transform.position = savedBallposition;
    }


    public void resetBall()
    {
        Scored = false;
        transform.position = Spawn.transform.position;
    }

    private void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.tag == "Exit")
            Scored = true;
    }
}
