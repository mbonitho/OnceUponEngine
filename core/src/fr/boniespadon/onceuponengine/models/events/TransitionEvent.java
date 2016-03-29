package fr.boniespadon.onceuponengine.models.events;

import fr.boniespadon.onceuponengine.GameManager;

/**
 * Makes a transition between 2 tableaux
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class TransitionEvent extends Event {

    /**
     * Creates a new TransitionEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the name of the Tableau to transit to
     */
    public TransitionEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);
    }

    /**
     * Makes a transition between 2 tableaux
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

        //additionalData contains target Tableau name only
        String targetTableauName = (String)additionalData[0];
        GameManager.setCurrentTableau(targetTableauName);

        System.out.println(" Transitting to tableau " + targetTableauName);

        hasRun = true;
    }
}
