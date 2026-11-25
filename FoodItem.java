/*
    Author: Raheem Silvera
    awawawawa
*/
import java.time.LocalDate;

public class FoodItem extends Product{
    public enum foodCategory{
        MEALS, SNACKS, BEVERAGES, DESSERTS, NOTHING
    }
    private Date expiryDate;
    private foodCategory category;
    private String ingredients;
    private boolean isPerishable;
    // assume food is only ordered in batches

    public FoodItem(){
        super();
        this.expiryDate = new Date();
        this.category = foodCategory.NOTHING;
        this.ingredients = "N/A";
        this.isPerishable = false;
    }

    public FoodItem (int productID, String name, double price, String description, int qtyInStock,
        boolean isActive, Date expiryDate, foodCategory category, String ingredients,
        boolean isPerishable){
        super(productID, name, price, description, qtyInStock, isActive);
        this.expiryDate = expiryDate;
        this.category = category;
        this.ingredients = ingredients;
        this.isPerishable = isPerishable;
    }

    public FoodItem (FoodItem f){
        super(f);
        this.expiryDate = f.expiryDate;
        this.category = f.category;
        this.ingredients = f.ingredients;
        this.isPerishable = f.isPerishable;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public foodCategory getCategory() {
        return category;
    }

    public String getIngredients() {
        return ingredients;
    }
    public boolean getIsPerishable (){
        return isPerishable;
    }

    //probs need to separate for spacing
    @Override
    public void showProductDetails(){
        super.Display();
        System.out.println("Category: " + category + "Expiry Date: " + expiryDate + 
        "Ingredient list: " + ingredients + "Perishable: " + isPerishable);

    }
    @Override 
    public long calculateRecordLength(){
        /*productid + price + qtyinstock + isActive + Name + Description
          expiry day, month, year + category + ingredients + isPerishable*/
        return (((4 + 8 + 4 + 1 + 122 + 452) + (4 + 4 + 4 + 29 + 602 + 1)));
    }
    public boolean isExpired(){
        LocalDate today = LocalDate.now();

        if(expiryDate.getYear() < today.getYear() || 
            expiryDate.getYear() == today.getYear() && 
            expiryDate.getMonth() < today.getMonthValue() ||
            expiryDate.getYear() == today.getYear() &&
            expiryDate.getMonth() == today.getMonthValue() &&
            expiryDate.getDay() < today.getDayOfMonth()){

            return true;
        } else {
            return false;
        }
    }
    public void checkExpiryDate(){
        LocalDate warningDate = LocalDate.now().plusDays(7);
        LocalDate expiry = LocalDate.of(
        expiryDate.getYear(),
        expiryDate.getMonth(),
        expiryDate.getDay());
        
        if (isExpired()){
            System.out.println("EXPIRED");
        } else if (!expiry.isAfter(warningDate)) {
            System.out.println("Expiring soon");
        } else{
            System.out.println("Not expired");
        }
    }

}