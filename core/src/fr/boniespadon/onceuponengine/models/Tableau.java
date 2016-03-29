package fr.boniespadon.onceuponengine.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Date;
import java.util.List;

import fr.boniespadon.onceuponengine.Game;
import fr.boniespadon.onceuponengine.models.events.Event;
import fr.boniespadon.onceuponengine.models.sprites.Sprite;

/**
 * A game screen, be it a splash screen, a menu, or a playable screen.
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class Tableau {

    private String name;
    private List<Sprite> sprites;
    private List<Event> events;
    private Sprite heldItem;
    private Date heldItemChangedTime;
    private boolean isGameTableau;

    public Tableau(String tabName, boolean isGameTableau, List<Sprite> tabSprites, List<Event> tabEvents)
    {
        name = tabName;
        sprites = tabSprites;
        events = tabEvents;
        heldItemChangedTime = new Date();
        this.isGameTableau = isGameTableau;
    }

    public boolean ContainsItem(String spriteName)
    {
        for (Sprite s : sprites)
            if (s.getName().equals(spriteName))
                return true;

        return false;
    }

    public Sprite getSpriteFromName(String spriteName)
    {
        for (Sprite s : sprites)
            if (s.getName().equals(spriteName))
                return s;

        return null;
    }

    public Event getEventFromId(String id)
    {
        for (Event e : events)
            if (e.getId().equals(id))
                return e;
        return null;
    }

    public void CheckEvents(float dt)
    {
        for (int i = 0; i < events.size(); i++)
        {
            Event ev = events.get(i);

            if (!ev.ConditionsMet())
                continue;

            switch (ev.getTrigger()) {
                case onClick:
                    if/*(Gdx.input.isButtonPressed(Input.Buttons.LEFT))*/ (Gdx.input.justTouched())
                    {
                        Vector2 mousePos = Game.getMousePosInGameWorld(); //new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

                        Sprite as = getSpriteFromName(ev.getOwner());

                        if (as.getBounds().contains(mousePos) && as != getHeldItem())
                            ev.DoStuff(dt);
                    }
                    break;

                case auto:
                    if (!ev.getHasRun())
                        ev.DoStuff(dt);
                    break;

                case parallel:
                    ev.DoStuff(dt);
                    break;
            }
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Sprite> getSprites() { return sprites; }
    public void setSprites(List<Sprite> sprites) { this.sprites = sprites; }
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> evts) { this.events = evts; }
    public Sprite getHeldItem() { return heldItem; }
    public Date getHeldItemChangedTime() { return heldItemChangedTime; }
    public boolean getIsGameTableau(){ return isGameTableau; }

    public void setHeldItem(Sprite item)
    {
        if (item == null)
            this.heldItem.setPosition(this.heldItem.getInitialPosition().x, this.heldItem.getInitialPosition().y);

        this.heldItem = item;
        heldItemChangedTime = new Date();
    }
}
