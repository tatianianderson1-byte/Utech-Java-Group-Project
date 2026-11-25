/*
    Author: Raheem Silvera
    awawawawa
*/

import java.io.IOException;
import java.io.RandomAccessFile;

public class StadiumShop{
    private final double taxRate = 0.15;

    public StadiumShop(){

    }
    public double gettaxRate(){
        return taxRate;
    }

    public void storeProduct(Product p){
        int productId;
        if (p instanceof FoodItem){
            try (RandomAccessFile file = new RandomAccessFile ("fooditems.dat", "rw")){
                file.seek(0);

                while (file.getFilePointer() < file.length()){
                    productId = file.readInt();
                    if (productId == p.getProductID()){
                        throw new IllegalArgumentException ("Product id already exists");
                    }
                    file.readUTF();       // Name
                    file.readDouble();    // Price
                    file.readUTF();       // Description
                    file.readInt();       // Quantity
                    file.readBoolean();   // Active

                    file.readInt();       // Expiry day
                    file.readInt();       // Expiry month
                    file.readInt();       // Expiry year
                    file.readUTF();       // Category
                    file.readUTF();       // Ingredients
                    file.readBoolean();   // Perishable

                    /*record += p.calculateRecordLength();
                    file.seek(record);*/
                }

                FoodItem food = (FoodItem) p;
                
                file.seek(file.length());
                file.writeInt(p.getProductID());
                file.writeUTF(p.getName());
                file.writeDouble(p.getPrice());
                file.writeUTF(p.getDescription());
                file.writeInt(p.getQtyInStock());
                file.writeBoolean(p.getIsActive());
            
                file.writeInt(food.getExpiryDate().getDay());
                file.writeInt(food.getExpiryDate().getMonth());
                file.writeInt(food.getExpiryDate().getYear());
                file.writeUTF(food.getCategory().name());
                file.writeUTF(food.getIngredients());   
                file.writeBoolean(food.getIsPerishable());

            } catch (IOException e) {
                System.err.println (e.getMessage());
            }


        } else if (p instanceof Merchandise){
            try (RandomAccessFile file = new RandomAccessFile("merchandise.dat", "rw")) {
                file.seek(0);

                while (file.getFilePointer() < file.length()){
                    productId = file.readInt();
                    if (productId == p.getProductID()){
                        throw new IllegalArgumentException ("Product id already exists");
                    }
                    file.readUTF();       // Name
                    file.readDouble();    // Price
                    file.readUTF();       // Description
                    file.readInt();       // Quantity
                    file.readBoolean();   // Active

                    file.readUTF();       // Size
                    file.readUTF();       // Team
                    file.readUTF();       // Color
                    file.readUTF();       // Merchandise type

                    /*record += p.calculateRecordLength();
                    file.seek(record);*/
                }

                Merchandise merch = (Merchandise) p;

                file.seek(file.length());
                file.writeInt(p.getProductID());
                file.writeUTF(p.getName());
                file.writeDouble(p.getPrice());
                file.writeUTF(p.getDescription());
                file.writeInt(p.getQtyInStock());
                file.writeBoolean(p.getIsActive());

                file.writeUTF(merch.getSize());
                file.writeUTF(merch.getTeam());
                file.writeUTF(merch.getColor());
                file.writeUTF(merch.getType().name());

            } catch (IOException e) {
               System.err.println (e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Unsupported product type");
        }
    }

    
    }
    public void retrieveInventoryList()	{
        // get list of food then merch cuz stored diff files
        try (RandomAccessFile file = new RandomAccessFile("fooditems.dat", "r")) {
            file.seek(0);

            int productID;
            String name;
            double price;
            String description;
            int quantity;
            boolean isActive;
            int day, month, year;
            String category;
            String ingredients;
            boolean isPerishable;

            while (file.getFilePointer() < file.length()){

            productID = file.readInt();
            name = file.readUTF();
            price = file.readDouble();
            description = file.readUTF();
            quantity = file.readInt();
            isActive = file.readBoolean();
            day = file.readInt();
            month = file.readInt();
            year = file.readInt();
            category = file.readUTF();
            ingredients = file.readUTF();
            isPerishable = file.readBoolean();
            
            //constructor accepts date and enum so i have to convert the stuff to them
            Date expiryDate = new Date(day, month, year);

            FoodItem.foodCategory foodCat = FoodItem.foodCategory.valueOf(category);
            
            FoodItem food = new FoodItem(
                productID, name, price, description, quantity, isActive,
                expiryDate, foodCat, ingredients, isPerishable);

            food.showProductDetails();
        } 
        }catch (IOException e) {
            System.err.println (e.getMessage());
        }

        try (RandomAccessFile file = new RandomAccessFile("merchandise.dat", "r")) {
            file.seek(0);

            int productID;
            String name;
            double price;
            String description;
            int quantity;
            boolean isActive;
            String size;
            String team;
            String color;
            String type;

            while (file.getFilePointer() < file.length()){
            productID = file.readInt();
            name = file.readUTF();
            price = file.readDouble();
            description = file.readUTF();
            quantity = file.readInt();
            isActive = file.readBoolean();
            size = file.readUTF();
            team = file.readUTF();
            color = file.readUTF();
            type = file.readUTF();
            
            Merchandise.merchType merchType = Merchandise.merchType.valueOf(type);

            Merchandise merch = new Merchandise(
                productID, name, price, description, quantity,
                isActive, size, team, color, merchType);

            merch.showProductDetails();
            
            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        } 
    }

    public Product searchInventory(int findProductID, int filesearch){
        if (filesearch == 1) {
            // Search fooditems.dat
            try (RandomAccessFile file = new RandomAccessFile("fooditems.dat", "r")) {
            file.seek(0);

            int productID;
            String name;
            double price;
            String description;
            int quantity;
            boolean isActive;
            int day, month, year;
            String category;
            String ingredients;
            boolean isPerishable;

            while (file.getFilePointer() < file.length()){

                productID = file.readInt();
                name = file.readUTF();
                price = file.readDouble();
                description = file.readUTF();
                quantity = file.readInt();
                isActive = file.readBoolean();
                day = file.readInt();
                month = file.readInt();
                year = file.readInt();
                category = file.readUTF();
                ingredients = file.readUTF();
                isPerishable = file.readBoolean();

                if (productID == findProductID){
                    Date expiryDate = new Date(day, month, year);
                    FoodItem.foodCategory foodCat = FoodItem.foodCategory.valueOf(category);

                    FoodItem food = new FoodItem(
                        productID, name, price, description, quantity, isActive,
                        expiryDate, foodCat, ingredients, isPerishable);

                    return food;
                }

            }
            return null;
        } catch (IOException e) {
               System.err.println (e.getMessage());
        }
            
        } else if (filesearch == 2) {
            // Search merchandise.dat
             try (RandomAccessFile file = new RandomAccessFile("merchandise.dat", "r")) {
            file.seek(0);

            int productID;
            String name;
            double price;
            String description;
            int quantity;
            boolean isActive;
            String size;
            String team;
            String color;
            String type;

            while (file.getFilePointer() < file.length()){
            productID = file.readInt();
            name = file.readUTF();
            price = file.readDouble();
            description = file.readUTF();
            quantity = file.readInt();
            isActive = file.readBoolean();
            size = file.readUTF();
            team = file.readUTF();
            color = file.readUTF();
            type = file.readUTF();
            
            if (productID == findProductID) {
                Merchandise.merchType merchType =
                    Merchandise.merchType.valueOf(type);

                Merchandise merch = new Merchandise(
                    productID, name, price, description, quantity,
                    isActive, size, team, color, merchType);

                    return merch;
            }
        }
        }catch (IOException e){
            System.err.println(e.getMessage());
        } 
        }
         else {
            throw new IllegalArgumentException("Invalid category. Enter 1 for Food or 2 for Merchandise.");
        }

        return null;
        }
}


    public void processTransaction(){

    }

    public void recordTransaction(){

    }

    public void retrieveTransactionHistory(){

    }

    public void displayProducts(){

    }
    generateReceipt(){
        
    }
}   

