package fr.boniespadon.onceuponengine.models.events;

import java.util.Date;

/**
 * Waits a certain amount of time using Thread.Sleep();
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class WaitEvent extends Event {

    //TODO wait a certain number of frames instead of using Thread.Sleep();

    /**
     * UNUSED YET
     * Date and time when the Event was launched, used to wait a proper amound of time
     */
    private Date launchTime;

    /**
     * Creates a new WaitEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - a Long corresponding to the number of ms to wait
     */
    public WaitEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);
    }

    /**
     * Waits a certain amount of time using Thread.Sleep();
     *
     * @param deltaTime
     *        Game time between two frames
     */
    @Override
    public void DoStuff(float deltaTime)
    {
        //If it is an auto event and it has already run, exit method
        if (trigger == TRIGGERS.auto && hasRun)
            return;

        //If it is a non-repeatable event and it has already run, exit method
        if (!repeatable && hasRun)
            return;

        //additionalData contains fade in speed
        long waitTime =  Long.parseLong(additionalData[0].toString());

        System.out.println("Waiting...");

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hasRun = true;
    }
}
