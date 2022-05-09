
import java.util.*;

/**
 * @author Aushveen Vimalathas
 * @since 5/9/2022
 * Shopify Fall 2022 Backend Developer Intern Challenge
 *
 * CRUD program creates inventory items, edits them, deletes them, prints them, and makes shipments of them.
 **/
public class crud {

    //inventory linked list keeps a list of all existing InventoryItems
    private static LinkedList<inventoryItem> inventory = new LinkedList<>();

    //shipments array list keeps a list of Shipments
    private static List<shipment> shipments = new ArrayList<shipment>();

    /**
     * This is the main method which will run the simulation.
     * @param args Unused.
     */
    public static void main(String args[]) {
        System.out.println("Welcome to CRUD! \n");
        Scanner a = new Scanner(System.in);
        System.out.println("Please type \"create\", \"edit\", \"delete\", \"list\", \"ship\", or \"EXIT\" to stop");
        String userInputMethod = a.nextLine();

        //While loop of options will run until user inputs EXIT
        while (!userInputMethod.toLowerCase().equals("exit")) {
            System.out.println(compute(userInputMethod));
            System.out.println("Please type \"create\", \"edit\", \"delete\", \"list\", \"ship\", or \"EXIT\" to stop");
            userInputMethod = a.nextLine();
        }
        System.out.println("Bye Bye!");
    }

    /**
     * compute method will determine what operation the user inputs
     * @param o user input
     * @return string on the status if the operation successfully completed
     */
    public static String compute(String o)
    {
        if (o==null)
            return("Sorry, I don't know that. Please type again :)");
        else if(o.toLowerCase().equals("create")){
            create();
         }
        else if(o.toLowerCase().equals("edit")){
            if(isEmpty())
                return("Inventory is empty! Add an item!");
            inventoryItem search = find();
            if(search == null)
                return("Error! Item not found");
            return edit(search);
        }
        else if(o.toLowerCase().equals("delete")){
            if(isEmpty())
                return("Inventory is empty! Add an item!");
            inventoryItem delete = find();
            inventory.remove(delete);
        } else if(o.toLowerCase().equals("list")) {
            printList();
        } else if(o.toLowerCase().equals("ship")){
            shipment searchShip = findShipment();
            shipment currShip = editShipments(searchShip);
            if(currShip == null)
                return ("Error! Item does not exist!");
        } else
            return("Sorry, I don't know that. Please type again :)");
        return("Successfully completed the operation:" + o);
    }

    /**
     * create method creates a new inventoryItem node into the inventory linked list
     */
    public static void create()
    {
        Scanner a = new Scanner(System.in);
        System.out.println("Enter the item name:");
        String name = a.nextLine().toLowerCase();
        System.out.println("Enter the amount of units:");
        int count = Integer.parseInt(a.nextLine());
        inventory.add(new inventoryItem(name, count));
    }

    /**
     * edit method will edit the Name or Units of an inventoryItem
     * @param item current item to edit
     * @return String if the item successfully updated
     */
    public static String edit(inventoryItem item)
    {
        System.out.println("Enter the field to edit(\"Name\" or \"Units\"):");
        Scanner a = new Scanner(System.in);
        String field = a.nextLine();
        if(field.toLowerCase().equals("name")){
            System.out.println("Enter the new name:");
            String name = a.nextLine().toLowerCase();
            item.setName(name);
        } else if(field.toLowerCase().equals("units")){
            System.out.println("Enter the updated units count:");
            String units = a.nextLine();
            item.setCount(Integer.parseInt(units));
        } else
            return("Sorry, I don't know that. Please try again :)");

        return("Successfully updated the item named: " + item.getName());
    }

    /**
     * checks if inventory linked list contains a node
     * @return boolean if inventory is empty
     */
    public static boolean isEmpty()
    {
        return inventory.size() == 0;
    }

    /**
     * Asks the user for the item name and finds it within the inventory linked list
     * @return inventoryItem if found, or null if not found
     */
    public static inventoryItem find()
    {
        Scanner a = new Scanner(System.in);
        System.out.println("Enter the item name:");
        String name = a.nextLine().toLowerCase();
        int i = 0;
        while(i < inventory.size())
        {
            if(inventory.get(i).getName().equals(name))
                return inventory.get(i);
            i++;
        }
        return null;
    }

    /**
     * prints the inventory list, if empty will say: Inventory is empty!
     */
    public static void printList()
    {
        if(isEmpty())
        {
            System.out.println("Inventory is empty!");
            return;
        }
        System.out.println("Inventory List:");
        int i = 0;
        while(i < inventory.size())
        {
            inventoryItem current = inventory.get(i);
            System.out.println("ITEM "+i+ ": Name- " + current.getName()
                    + " Units- " + current.getCount()
            );
            i++;
        }
    }

    /**
     * edit/adds an item to currShipment
     * @param currShipment shipment to be edited
     * @return currShipment if successfully updated, null if the item updated does not exist
     */
    public static shipment editShipments(shipment currShipment)
    {
        //Ask for item name once inside the shipment node
        Scanner a = new Scanner(System.in);
        inventoryItem item = find();
        if(item == null)
            return null;
        System.out.println("here");
        //ask for new item amount
        System.out.println("Enter the updated item amount:");
        int updatedCount = Integer.parseInt(a.nextLine());
        //update units = units - shipment
        //make an error if there's not enough units in the inventory
        //if not enough units, then put max units in the shipment
        //adjust updated units
        if(updatedCount <= item.getCount())
        {
            //perform as normal
            item.setCount(item.getCount()-updatedCount);
            currShipment.editInventory(item.getName(), updatedCount);
        }
        else
        {
            //the updated count exceeds the original amount
            //set item to 0
            currShipment.editInventory(item.getName(), item.getCount());
            item.setCount(0);
            System.out.println("Updated item amount exceeds the original inventory count \n" +
                    item.getName() + "'s count in inventory is: 0 and in Shipment " + currShipment.getShipmentNumber()
                    + " is: " + item.getCount());
        }
        return currShipment;
    }

    /**
     * asks the user for the shipment number and finds it in the shipments arraylist.
     * if the shipment is not found, will be created.
     * @return shipment found or new shipment created
     */
    public static shipment findShipment()
    {
        Scanner a = new Scanner(System.in);
        System.out.println("Enter the shipment number:");
        int number = Integer.parseInt(a.nextLine());
        int i = 0;
        while(i < shipments.size())
        {
            if(shipments.get(i).getShipmentNumber() == number)
                return shipments.get(i);
            i++;
        }
        //shipment not found, create a new one
        shipment newShip = new shipment(number);
        shipments.add(newShip);
        return newShip;
    }

}