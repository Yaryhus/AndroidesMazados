using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;

public class PuzzleGenerator : MonoBehaviour, ITrackableEventHandler
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
        Debug.Log("He creado cosas");

    }

    // Update is called once per frame
    void Update()
    {

        // Si la bola ha llegado a su meta
        if (ball.transform.GetComponent<Ball>().Scored) {

            //Creamos un mapa aleatorio y recargamos la pelota.
            OldMap = ActualMap;
            ActualMap = returnRandom();
            //Borramos el mapa antiguo
            Destroy(OldMap, 0);
            //Creamos mapa nuevo
            Instantiate(ActualMap);
            //Reseteamos la bola
            ball.transform.GetComponent<Ball>().resetBall();     
        }
		
	}

    public void OnTrackableStateChanged(
          TrackableBehaviour.Status previousStatus,
          TrackableBehaviour.Status newStatus)
    {
        if (newStatus == TrackableBehaviour.Status.DETECTED ||
            newStatus == TrackableBehaviour.Status.TRACKED)
        {
            Debug.Log("He encontrado trackeo!");

            OnTrackingFound();
        }
    }

    private void OnTrackingFound()
    {
        if (ball.transform.GetComponent<Ball>().Scored)
        {
            Debug.Log("Creando un mapa");

            //Recuperamos la posicion de la bola
            ball.transform.position = savedBallposition;

            //Creamos mapa aleatorio
            ActualMap = returnRandom();
            Instantiate(ActualMap);
        }
    }

 

    public GameObject returnRandom()
    {
        int i = Random.Range(maps.Length,0);      
        return maps[i];

    }


}
