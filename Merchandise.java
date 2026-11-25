/*
    Author: Raheem Silvera
    awawawawa
*/
public class Merchandise extends Product{
    public enum merchType {
        FLAG, JERSEY, SHORTS, CLEATS, NOTHING
    }
    private String size;
    private String team;
    private String color;
    private merchType type;


    public Merchandise(){
        super();
        this.size = "N/A";
        this.team = "Invalid";
        this.color = "N/A";
        this.type = merchType.NOTHING;
    }
    public Merchandise (int productID, String name, double price, String description, int qtyInStock,
        boolean isActive, String size, String team, String color, merchType type){
        super(productID, name, price, description, qtyInStock, isActive);
        this.size = size;
        this.team = team;
        this.color = color;
        this.type = type;
    }
    public Merchandise (Merchandise m){
        super(m);
        this.size = m.size;
        this.team = m.team;
        this.color = m.color;
        this.type = m.type;
    }
    

    public String getColor() {
        return color;
    }

    public String getTeam() {
        return team;
    }


    public String getSize() {
        return size;
    }

    public merchType getType() {
        return type;
    }
    @Override
    public void showProductDetails(){
        super.Display();
        System.out.println("Size: " + size + "Team: " + team + 
        "Color " + color + "Type: " + type);
    }
    @Override
    public long calculateRecordLength(){
        /*productid + price + qtyinstock + isActive + Name + Description
          size + team + color + type*/
        return (((4 + 8 + 4 + 1 + 122 + 452) + (14 + 152 + 92 + 23)));
    }
    public void checkSizeAvailability(){
        
    }
}