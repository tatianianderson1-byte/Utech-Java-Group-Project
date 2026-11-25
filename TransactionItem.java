/*
    Author: Raheem Silvera
    awawawawa
*/
public class TransactionItem{
    private Product product;
    private int quantity;
    private double priceAtPurchase;

    public TransactionItem(){
        this.product = null;
        this.quantity = 0;
        this.priceAtPurchase = 0.00;
    }

    public TransactionItem(Product product, int quantity, double priceAtPurchase){
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public TransactionItem(TransactionItem t){
        this.product = t.product;
        this.quantity = t.quantity;
        this.priceAtPurchase = t.priceAtPurchase;
    }

    public double calculateLineTotal(){
        return priceAtPurchase * quantity;
    }


    //throws illegalargumentexeption when qty is 0 or negative
    public void validateQuantity(){
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}