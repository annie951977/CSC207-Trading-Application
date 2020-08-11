package Actions;

import Items.Item;
import Users.TradingUser;

import java.io.Serializable;

/**
 * Represents an "adding" or "deleting" action, including adding or deleting an Item from a TradingUser's inventory or wishlist.
 */
public class AddOrDeleteAction extends Action implements Serializable {
    private Item added = null;
    private Item removed = null;
    private boolean wishlist = false;
    private boolean inventory = false;

    public AddOrDeleteAction(TradingUser user) {
        super(user);
    }

    @Override
    public boolean isEditAction() {
        return false;
    }

    @Override
    public boolean isAddorDeleteAction() {
        return true;
    }

    /**
     * Sets the Item object of this Action if an item is being added.
     * @param added Item that is being added to a list.
     */
    public void setAdded(Item added) {
        this.added = added;
    }

    /**
     * Sets the Item object of this Action if an item is being removed.
     * @param removed Item that is being removed from a list.
     */
    public void setRemoved(Item removed) {
        this.removed = removed;
    }

    /**
     * Getter for Item being added or removed.
     */
    public Item getItem() {
        if (added != null) {
            return added;
        }
        else {
            return removed;
        }
    }

    // return true if the action was adding an item, false otherwise
    public boolean wasAdded() {
        return added != null;
    }

    // return true if the action was removing an item, false otherwise
    public boolean wasRemoved() {
        return removed != null;
    }

    public void setIsInventory() {
        inventory = true;
    }

    public void setIsWishlist() {
        wishlist = true;
    }

    // return true if this action involved the inventory, false otherwise
    public boolean isInventory() {
        return inventory;
    }

    // return true if this action involved the wishlist, false otherwise
    public boolean isWishlist() {
        return wishlist;
    }

    public String toString() {
        if (added != null) {
            if (wishlist) {
                return getUser() + " added " + added + " to their wishlist.";
            } else {
                return getUser() + " added " + added + " to their inventory.";
            }
        } else {
            if (wishlist) {
                return getUser() + " removed " + removed + " from their wishlist.";
            } else {
                return getUser() + " removed " + removed + " from their inventory.";
            }
        }
    }
}
