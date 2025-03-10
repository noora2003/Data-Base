package application.inventroy2;

import java.sql.Date;

public class InventoryData {
    private int InventoryItemID;
    private int Quantity;
    private double Cost;
    private Date ExpiryDate;


    public InventoryData(int InventoryItemID, int Quantity, double Cost, Date ExpiryDate) {
        this.InventoryItemID = InventoryItemID;
        this.Quantity = Quantity;
        this.Cost = Cost;
        this.ExpiryDate = ExpiryDate;
    }

    public int getInventoryItemID() {
        return InventoryItemID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public double getCost() {
        return Cost;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }
}