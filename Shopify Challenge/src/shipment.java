import java.util.ArrayList;

/**
 * shipment object which will be part of the CRUD shipments arraylist
 * this is also the additional feature I have chosen to add to the project
 */
public class shipment {

    // this identifies the shipment number
    private int shipmentNumber;

    // this is an inventoryItem array list to list all of the items that we are adding to the shipment
    private ArrayList<inventoryItem> items;

    /**
     * constructor to make a new shipment
     * @param shipmentNumber user input will give an int for the shipment number
     */
    public shipment(int shipmentNumber){
        this.shipmentNumber = shipmentNumber;
        items = new ArrayList<>();
    }

    /**
     * returns the current shipment number
     * @return int this shipment number
     */
    public int getShipmentNumber() {
        return shipmentNumber;
    }

    /**
     * adds an inventoryItem to the items list
     * @param newItem inventoryItem to add to the list
     */
    public void addItem(inventoryItem newItem)
    {
        items.add(newItem);
    }

    /**
     * tries to find the item with the name, and if it's found, will edit the count of the item
     * if not found, then will add a new item into the items list with the same name and units
     * @param name String name of the item
     * @param newCount int units of the item to update
     */
    public void editInventory(String name, int newCount)
    {
        for(inventoryItem i: items)
        {
            if(i.getName().equals(name))
            {
                i.setCount(newCount);
                return;
            }
        }
        addItem(new inventoryItem(name, newCount));
    }
}