package fun.ogre.ogredupealias.events;

import fun.ogre.ogredupealias.plugin.custom.forging.CustomTable;
import fun.ogre.ogredupealias.plugin.custom.gui.CustomGui;
import fun.ogre.ogredupealias.utils.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryActionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {
            this.handleForgeClick(e);
            CustomGui.handleRegistriesClick(e);
        }
        catch (Exception ignore) {}
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        try {
            this.handleForgeClose(e);
            CustomGui.handleRegistriesClose(e);
        }
        catch (Exception ignore) {}
    }

    private void handleForgeClick(InventoryClickEvent e) {
        final Inventory inv = e.getClickedInventory();
        final String title = e.getView().getTitle();

        if (inv == null) return;
        if (title.equals(Text.color("&eForging Table"))) CustomTable.onInventoryAction(e);
    }

    private void handleForgeClose(InventoryCloseEvent e) {
        final String title = e.getView().getTitle();
        if (title.equals(Text.color("&eForging Table"))) CustomTable.onInventoryClose(e);
    }
}
