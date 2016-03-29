package fr.boniespadon.onceuponengine.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fr.boniespadon.onceuponengine.models.Tableau;
import fr.boniespadon.onceuponengine.models.conditions.Condition;
import fr.boniespadon.onceuponengine.models.events.Event;
import fr.boniespadon.onceuponengine.models.sprites.Sprite;

/**
 * Reads a xml Tableau file and creates the corresponding Tableau,
 * Sprites and Events (with Conditions) objects
 *
 * @see Tableau
 * @see Sprite
 * @see Event
 * @see Condition
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class XMLTableauxParser {

    /**
     * Reads an XML file and creates the corresponding Tableau,
     * Sprites and Events (with Conditions) objects
     *
     * @param fileName
     *        XML file to be read
     * @return Tableau obtained from the XML
     */
    public static Tableau ParseXMLTableau(String fileName)
    {
        try
        {
            System.out.println("loading tableau : " + fileName);

            FileHandle xmlFileHandler = Gdx.files.internal(fileName);

            File xmlFile = xmlFileHandler.file();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = null;

            //if debug
            if (xmlFile.exists())
            {
                doc = dBuilder.parse(xmlFile);
            }
            else
            { //if prod
                InputStream in = XMLTableauxParser.class.getClass().getResourceAsStream("/" + fileName);
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader xmlReader = new BufferedReader(isr);

                String xmlString = "";
                String line;
                while ((line = xmlReader.readLine()) != null)
                    xmlString += line;

                doc = loadXMLFromString(xmlString);
            }

            doc.getDocumentElement().normalize();

            String tabName = doc.getDocumentElement().getAttribute("name");

            boolean isGameTableau = Boolean.parseBoolean(doc.getDocumentElement().getAttribute("game"));

            NodeList nlTabSprites = doc.getDocumentElement().getElementsByTagName("Sprite");

            List<Sprite> tabSprites = new ArrayList<Sprite>();

            for (int i = 0; i < nlTabSprites.getLength(); i++)
            {
                Node n = nlTabSprites.item(i);

                Sprite s = getSpriteFromNode(n);

                tabSprites.add(s);
            }

            /////////////////////////////////////////////////////////////

            NodeList nlTabEvts = doc.getDocumentElement().getElementsByTagName("Event");

            List<Event> tabEvents = new ArrayList<Event>();

            for (int i = 0; i < nlTabEvts.getLength(); i++)
            {
                Node n = nlTabEvts.item(i);

                Event e = getEventFromNode(n);

                tabEvents.add(e);
            }

            /////////////////////////////////////////////////////////////

            return new Tableau(tabName, isGameTableau, tabSprites, tabEvents);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *  Obtains a Sprite object from a XML Node
     *
     *  @see Sprite
     *
     * @param n
     *        Node to be read
     * @return Sprite read
     */
    private static Sprite getSpriteFromNode(Node n) {
        String spriteName = n.getAttributes().getNamedItem("name").getNodeValue();

        int steps = Integer.parseInt(n.getAttributes().getNamedItem("steps").getNodeValue());

        boolean active = true;
        if (n.getAttributes().getNamedItem("active") != null)
            active = Boolean.parseBoolean(n.getAttributes().getNamedItem("active").getNodeValue());

        String textureName = "";
        if (n.getAttributes().getNamedItem("texture").getNodeValue() != "")
            textureName = "sprites/" + n.getAttributes().getNamedItem("texture").getNodeValue();

        float x = Float.parseFloat(n.getAttributes().getNamedItem("x").getNodeValue());
        float y = Float.parseFloat(n.getAttributes().getNamedItem("y").getNodeValue());

        float opacity = 1.0f;
        if (n.getAttributes().getNamedItem("opacity") != null)
            opacity = Float.parseFloat(n.getAttributes().getNamedItem("opacity").getNodeValue());

        Sprite spr = new Sprite(spriteName, x, y, textureName, opacity, steps);
        spr.setActive(active);

        return spr;
    }

    /**
     * Obtains an Event object from a XML Node
     *
     * @see Event
     *
     * @param n
     *        Node to be read
     * @return Event read
     * @throws ClassNotFoundException When the Event class name is not recognised through reflection
     */
    private static Event getEventFromNode(Node n) throws ClassNotFoundException {

        String id = n.getAttributes().getNamedItem("id").getNodeValue();

        String eventType = n.getAttributes().getNamedItem("type").getNodeValue();

        String additionalData = n.getAttributes().getNamedItem("additionalData").getNodeValue();

        String[] tabData = additionalData.split(";");

        Event.TRIGGERS t = null;
        String owner = "";
        String triggerType = n.getAttributes().getNamedItem("trigger").getNodeValue();
        switch (triggerType)
        {
            case ("auto"):
                t = Event.TRIGGERS.auto;
                break;

            case ("parallel"):
                t = Event.TRIGGERS.parallel;
                break;

            case ("onClick"):
                t = Event.TRIGGERS.onClick;
                owner = n.getAttributes().getNamedItem("owner").getNodeValue();
                break;

            default:
                throw new ClassNotFoundException("Trigger type not found : " + triggerType);
        }

        boolean repeatable = false;
        if (n.getAttributes().getNamedItem("repeatable") != null)
            repeatable = Boolean.parseBoolean(n.getAttributes().getNamedItem("repeatable").getNodeValue());

        String ns = "fr.boniespadon.onceuponengine.models.events.";

        try
        {
            Class classEvent = Class.forName(ns + eventType);
            Constructor constr = classEvent.getConstructor(String.class, Object[].class);
            Object myEvent = constr.newInstance(id, tabData);

            Method setTriggerMethod = classEvent.getMethod("setTrigger", Event.TRIGGERS.class);
            setTriggerMethod.invoke(myEvent, t);

            Method setOwnerMethod = classEvent.getMethod("setOwner", String.class);
            setOwnerMethod.invoke(myEvent, owner);

            Method setRepeatableMethod = classEvent.getMethod("setRepeatable", boolean.class);
            setRepeatableMethod.invoke(myEvent, repeatable);

            List<Condition> evtCdts = new ArrayList<Condition>();
            for (int j = 0; j < n.getChildNodes().getLength(); j++)
            {
                Node nd = n.getChildNodes().item(j);

                if (nd.getNodeType() == Node.ELEMENT_NODE)
                {
                    try
                    {
                        evtCdts.add(getConditionFromNode((Event)myEvent, nd));
                    }
                    catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            Method setConditionsMethod = classEvent.getMethod("setConditions", List.class);
            setConditionsMethod.invoke(myEvent, evtCdts);

            return (Event)myEvent;
        }
        catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException ex) {
            ex.printStackTrace(); //todo exceptions

            return null;
        }
    }

    /**
     * Obtains a Condition object from a XML Node
     *
     * @see Condition
     * @see Event
     *
     * @param owner
     *        The Event whose Condition this is
     * @param n
     *        Node to be read
     * @return Condition read
     * @throws ClassNotFoundException When the Condition class name is not recognised through reflection
     */
    private static Condition getConditionFromNode(Event owner, Node n) throws ClassNotFoundException {
        String conditionType = n.getAttributes().getNamedItem("type").getNodeValue();

        String additionalData = n.getAttributes().getNamedItem("additionalData").getNodeValue();

        String[] tabData = additionalData.split(";");

        try
        {
            String ns = "fr.boniespadon.onceuponengine.models.conditions.";

            Class classCdt = Class.forName(ns + conditionType);
            Constructor constr = classCdt.getConstructor(Event.class, Object[].class);
            Object myCdt = constr.newInstance(owner, tabData);

            return (Condition)myCdt;
        }
        catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException ex)
        {
            ex.printStackTrace();

            return null;
        }
    }

    /**
     * Obtains a Document object from a String
     *
     * @param str
     *        String to transform
     * @return Document
     * @throws Exception if the String couldn't be parsed into a Document object
     */
    private static Document loadXMLFromString(String str) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new ByteArrayInputStream(str.getBytes()));
    }
}
