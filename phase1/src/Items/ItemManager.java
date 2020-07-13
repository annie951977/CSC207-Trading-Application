package Items;

import Exceptions.InvalidItemException;
import Transactions.Transaction;

import java.util.*;

public class ItemManager {
    public Map<UUID, Item> allItems;

    public ItemManager(Map<UUID, Item> items) {
        this.allItems = items;
    }

    public void addItem (Item item){
        allItems.put(item.getId(), item);
    }

    public Item getItem(UUID id) throws InvalidItemException {
        if (allItems.containsKey(id)){
            return allItems.get(id);
        } else {
            throw new InvalidItemException();
        }
    }

    public List<Item> convertIdsToItems(List<UUID> ids) {
        List<Item> items = new ArrayList<>();
        for (UUID id : ids) {
            items.add(allItems.get(id));
        }
        return items;
    }

}
