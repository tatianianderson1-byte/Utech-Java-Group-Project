/*
    Author: Raheem Silvera
    awawawawa
*/
public class Transaction{
    private int saleID;
    private Date transactionDate;
    private TransactionItem[] items;
    private int itemCount;
    private double paymentReceived;
    private boolean completed;

    public Transaction(){
        this.saleID = 0;
        this.transactionDate = null;
        this.items = null;
        this.itemCount = 0;
        this.paymentReceived = 0.00;
        this.completed = false;
    }

    public Transaction(int saleID, Date transactionDate, TransactionItem[] items, int itemCount,
        double paymentReceived, boolean completed){
        this.saleID = saleID;
        this.transactionDate = transactionDate;
        this.items = items;
        this.itemCount = itemCount;
        this.paymentReceived = paymentReceived;
        this.completed = completed;
    }
    
    public Transaction(Transaction t){
        this.saleID = t.saleID;
        this.transactionDate = t.transactionDate;
        this.items = t.items;
        this.itemCount = t.itemCount;
        this.paymentReceived = t.paymentReceived;
        this.completed = t.completed;
    }

    public void addItem(TransactionItem item){
        if (completed){
            throw new IllegalStateException("Items cannot be added to a completed Transaction");
        } else if (item == null){
            throw new IllegalArgumentException ("No items");
        } else if (itemCount >= items.length){
            throw new IllegalStateException("No more items can be added");
        }
        items[itemCount] = item;
        itemCount++;
    }

    public double calculateSubtotal(){
        double total = 0.0;

        for (int x = 0; x < itemCount; x++){
            total += items[x].calculateLineTotal();
        }

        return total;
    }

    //all prices include gct so this gets price b4 gct
    public double calculatePreTaxAmount(){

        return calculateSubtotal()/1.15;
    }

    public double calculateTax(){
        return calculateSubtotal() - calculatePreTaxAmount();
    }

    public boolean validatePayment(){
        //if true returns true if false is false
        return paymentReceived >= calculateSubtotal();
    }

    public double calculateChange() {
        if (!validatePayment()) {
            throw new IllegalStateException("Insufficient payment");
        }

        return paymentReceived - calculateSubtotal();
    }

    public void completeTransaction(){
        if (completed) {
            throw new IllegalStateException("Transaction has already been completed");
        } else if (!validatePayment()){
            throw new IllegalStateException("Insufficient payment");
        } else {
            completed = true;
        }
    }
    //only able to cancel before payment recieved
    public void cancelTransaction(){
        if (paymentReceived > 0) {
            throw new IllegalStateException("Transaction cannot be canceled after payment received");
        } else if (completed){
            throw new IllegalStateException("Transaction has already been completed");
        } else {
            for(int x = 0; x < itemCount; x++){
                items[x] = null;
            }
            itemCount = 0;
        }
    }
}