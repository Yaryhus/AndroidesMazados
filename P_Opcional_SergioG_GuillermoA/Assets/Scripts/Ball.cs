using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;



[RequireComponent(typeof(Rigidbody))]

public class Ball : MonoBehaviour, ITrackableEventHandler
{


   private TrackableBehaviour mTrackableBehaviour;

    GameObject Floor;
    GameObject Point;
    //public GameObject Spawn;


    public bool Scored = false;

    Vector3 savedBallposition;
    Vector3 initialBallPosition;
    Rigidbody rb;

    // Use this for initialization
    void Start () {

        //Aumentamos la velocidad de caida de la ficha.
        rb=this.GetComponent<Rigidbody>();

        mTrackableBehaviour = GetComponent<TrackableBehaviour>();

        if (mTrackableBehaviour)
        {
            mTrackableBehaviour.RegisterTrackableEventHandler(this);
        }

        initialBallPosition = transform.localPosition;

        Floor = GameObject.FindWithTag("Floor");
        Point = GameObject.FindWithTag("Point");

    }

    // Update is called once per frame
    void Update () {

        //Guardamos la posicion de la pelota
        savedBallposition = transform.position;

        //rb.velocity += new Vector3(0, -5, 0);

        //Si se saliese del mapa
        
        if (transform.localPosition.y < Floor.transform.localPosition.y - 10)
        {
            transform.localPosition = initialBallPosition;         
        }
        



        /*
             Vector3 VectorResult;

        Plane plane = new Plane();

        Debug.DrawRay(Floor.transform.position, Floor.transform.up * 100, Color.green);

        

        plane.SetNormalAndPosition(Floor.transform.up, Floor.transform.position);
        if (!plane.SameSide(transform.position, Point.transform.position))
        {
           transform.localPosition = initialBallPosition;
        }
        */

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
        //La colocamos donde la recordamos por ultima vez
        transform.position = savedBallposition;
      //  transform.localPosition = initialBallPosition;


    }
    /*

    public void resetBall()
    {
        //Reseteamos posicion y booleano de ganar
        Scored = false;
        transform.position = Spawn.transform.position;
    }

    */

    private void OnTriggerEnter(Collider collision)
    {

        if (collision.gameObject.tag == "Exit")
        {
            Scored = true;

            gameObject.SetActive(false);

        }


    }
}
