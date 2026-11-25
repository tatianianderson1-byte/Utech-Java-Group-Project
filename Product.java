/*
    Author: Raheem Silvera
    awawawawa
*/
public abstract class Product{
    private int productID;
    private String name;
    private double price;
    private String description;
    private int qtyInStock;
    private boolean isActive;

    //Default 
    public Product(){
        this.productID = 0;
        this.name = "N/A";
        this.price = 0.00;
        this.description = "N/A";
        this.qtyInStock = 0;
        this.isActive = false;
    }
    //Primary
    public Product(int productID, String name, double price, String description, int qtyInStock,
        boolean isActive){
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.qtyInStock = qtyInStock;
        this.isActive = isActive;
    }
    //Copy
    public Product(Product p){
        this.productID = p.productID;
        this.name = p.name;
        this.price = p.price;
        this.description = p.description;
        this.qtyInStock = p.qtyInStock;
        this.isActive = p.isActive;
    }

    //Get&Set (none for isActive rn fix later)
    public int getProductID() {
        return productID;
    }

    /*public void setProductID(int productID) {
        this.productID = productID;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    public void setIsActive (boolean isActive){
        this.isActive = isActive;
    }

    public boolean getIsActive (){
        return isActive;
    }
    public void Display(){
        System.out.println("ID: " + productID + "Name: " + name + 
            "Price: $" + price + "Description: " + description +"Quantity in Stock: " + qtyInStock);
    }
    
    public abstract void showProductDetails();
    public abstract long calculateRecordLength();

    public void addItem(Product p){

    }

    //if qty > 0 turn item active and add to the qty if not throw exception
    public void addStock(int quantity){
        if (quantity > 0){
            qtyInStock += quantity;
            setIsActive(true);
        } else {
            throw new IllegalArgumentException ("Quantity must not be 0 or less");
        }
    }
    public void reduceStock(int quantity){
        if (quantity <= 0){
            throw new IllegalArgumentException ("Quantity cannot be reduced by 0 or less");
        } else if (quantity > qtyInStock) {
            //illegal state beacuse the argument is valid but the inventory cant smth
            throw new IllegalStateException ("Insufficient stock available");
        } else {
            setQtyInStock(qtyInStock - quantity);
        }
    }
    //accepts value and checks if enough is there returns t/f
    public boolean checkAvailability(int quantity){
        //isactive becasue the item has to be up for sale
        if(quantity > 0 && qtyInStock >= quantity && isActive){
            return true;
        } else {
            return false;
        }
    }
}