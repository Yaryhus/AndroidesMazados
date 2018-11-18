using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;

public class PuzzleGenerator : DefaultTrackableEventHandler
{

    public GameObject[] maps;
    public GameObject ball;


    private TrackableBehaviour mTrackableBehaviour;
    GameObject ActualMap;
    GameObject OldMap;

    Vector3 savedBallposition;

	// Use this for initialization
	void Start () {

        mTrackableBehaviour = GetComponent<TrackableBehaviour>();

        if (mTrackableBehaviour)
        {
            mTrackableBehaviour.RegisterTrackableEventHandler(this);
        }

        Debug.Log("Creando un mapa");

            //Recuperamos la posicion de la bola
            ball.transform.position = savedBallposition;

            //Creamos mapa aleatorio
            ActualMap = returnRandom();
            Instantiate(ActualMap,this.transform);
            ActualMap.SetActive(false);

    }

    // Update is called once per frame
    void Update()
    {

        // Si la bola ha llegado a su meta
        if (ball.transform.GetComponent<Ball>().Scored) {
            ActualMap.SetActive(false);
            //Creamos un mapa aleatorio y recargamos la pelota.
            OldMap = ActualMap;
            ActualMap = returnRandom();
            //Hacemos invisible el mapa antiguo
            OldMap.SetActive(false);
            //Destroy(OldMap);
            //Creamos mapa nuevo
            //Instantiate(ActualMap,OldMap.transform.position,OldMap.transform.rotation,this.transform);
            ActualMap.SetActive(true);

            //Instantiate(ActualMap, this.transform);
            //Reseteamos la bola
            ball.transform.GetComponent<Ball>().resetBall();     
        }
		
	}

    
    public new void OnTrackableStateChanged(
         TrackableBehaviour.Status previousStatus,
          TrackableBehaviour.Status newStatus)
    {
        
     if (newStatus == TrackableBehaviour.Status.DETECTED ||
            newStatus == TrackableBehaviour.Status.TRACKED ||
            newStatus == TrackableBehaviour.Status.EXTENDED_TRACKED)
    {
        Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " found");
        OnTrackingFound();
    }
        else if (previousStatus == TrackableBehaviour.Status.TRACKED &&
                 newStatus == TrackableBehaviour.Status.NO_POSE)
        {
            Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " lost");
            OnTrackingLost();
}
        else
        {
            // For combo of previousStatus=UNKNOWN + newStatus=UNKNOWN|NOT_FOUND
            // Vuforia is starting, but tracking has not been lost or found yet
            // Call OnTrackingLost() to hide the augmentations
            OnTrackingLost();
        }

    }



    public override void OnTrackingFound()
    {
        base.OnTrackingFound();

        ActualMap.SetActive(true);
        
    }

    protected void OnTrackingLost()
    {
    }

    public GameObject returnRandom()
    {
        int i = Random.Range(0,maps.Length);      
        return maps[i];

    }


}
