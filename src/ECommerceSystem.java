import java.time.LocalDate;
import java.util.*;


//product
abstract class Product {
    protected String name;
    protected double price;
    protected int quantity;

    public Product(String name, double price, int quantity) {        //constructor to intialize product attributes
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    //getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void minus_quantity(int amount) {
        quantity -= amount;
    }
}

interface Expirable {
    boolean isExpired();                 //if product have expiration date
}

interface ShippableItem {                //if product can be shippable
    String getName();
    double getWeight();
}

//some products that extent product abstract class
class Cheese extends Product implements Expirable, ShippableItem {                      //shippable and expirable
    private LocalDate expiryDate;
    private double weight;

    public Cheese(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity);             //from abstract class
        this.expiryDate = expiryDate;             //expiry date and weight as its shippable and expirable
        this.weight = weight;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    public double getWeight() {
        return weight;
    }
}

class TV extends Product implements ShippableItem {                            //shppable , not expirable
    private double weight;

    public TV(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }
    public double getWeight() {
        return weight;
    }
}

class Biscuits extends Product implements Expirable, ShippableItem {                  //shippable and expirable
    private LocalDate expiryDate;
    private double weight;

    public Biscuits(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());    //return true if now is after the expiration date
    }
    public double getWeight() {
        return weight;
    }
}

class ScratchCard extends Product {                      //not shippable , not expirable
    public ScratchCard(String name, double price, int quantity) {
        super(name, price, quantity);
    }
}


//customer
class Customer {
    String name;
    double balance;

    public Customer(String name, double balance) {    //constructor to intialize customer attributes
        this.name = name;
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }
    public boolean Is_sufficient(double amount) {

        if (balance >= amount) {        //there's sufficient balance
            balance -= amount;
            return true;
        }
        return false;
    }


}

// cart
class Cart {
    private Map<Product, Integer> products = new LinkedHashMap<>();

    public Map<Product, Integer> getProducts() {
        return products;
    }
    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void addProduct(Product product, int quantity) {
        if (product instanceof Expirable && ((Expirable) product).isExpired()) {
            throw new IllegalArgumentException(product.getName() + " is expired.");
        }

        if (quantity > product.getQuantity()) {            //if there's sufficient quantity
            throw new IllegalArgumentException("There's no enough quantity of  " + product.getName() );
        }
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }
}

// shipping
class Shipping {
    public static void ship(List<ShippableItem> items, Map<Product, Integer> cartProducts) {
        System.out.println("** Shipment notice **");
        double TotalWeight = 0;
        for (ShippableItem item : items) {
            for (Product product : cartProducts.keySet()) {
                if (product.getName().equals(item.getName())) {           //shippable products
                    int quantity = cartProducts.get(product);
                    double weight = item.getWeight();
                    System.out.printf("%dx %s    %.0fg\n", quantity, item.getName(), weight * 1000);
                    TotalWeight += weight * quantity;
                }
            }
        }
        System.out.printf("Total package weight %.1fkg\n", TotalWeight);
    }
}
//checkout
class Checkout {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty())     //if cart is empty
            throw new IllegalArgumentException("Cart is empty.");


        double totalPerProduct = 0;
        List<ShippableItem> Shippable = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int amount = entry.getValue();

            if (product instanceof Expirable && ((Expirable) product).isExpired())      //if it's expired
                throw new IllegalArgumentException(product.getName() + " is expired.");


            if (amount > product.getQuantity())                //if no enough stock
                throw new IllegalArgumentException("No enough stock of" +product.getName() );


            totalPerProduct += product.getPrice() * amount;
            product.minus_quantity(amount);

            if (product instanceof ShippableItem)        //add it to shipping if it's shippable
                Shippable.add((ShippableItem) product);
        }

        if (!Shippable.isEmpty())
            Shipping.ship(Shippable, cart.getProducts());

        double shipping = Shippable.isEmpty() ? 0 : 30;     // shipping is 30 if there are shippable products
        double total = totalPerProduct + shipping;

        if (!customer.Is_sufficient(total))                   // if customer's balance is not enough
            throw new IllegalArgumentException("Insufficient balance.");

        //console checkout output
        System.out.println("** Checkout receipt **");
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            System.out.printf("%dx %s     %.0f\n", entry.getValue(), entry.getKey().getName(), entry.getKey().getPrice() * entry.getValue());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal     %.0f\n", totalPerProduct);
        System.out.printf("Shipping     %.0f\n", shipping);
        System.out.printf("Amount       %.0f\n", total);
    }
}

public class ECommerceSystem {
    public static void main(String[] args) {
        //provided example
        Product cheese = new Cheese("Cheese", 100, 10, LocalDate.now().plusDays(2), 0.4);
        Product biscuits = new Biscuits("Biscuits", 150, 5, LocalDate.now().plusDays(1), 0.7);
        Product expiredBiscuits = new Biscuits("Lotus Biscuits", 150, 5, LocalDate.now().minusDays(1), 0.7);
        Product tv = new TV("TV", 200, 3, 5.0);
        Product scratchCard = new ScratchCard("Scratch Card", 50, 3);

        Customer customer = new Customer("Shaza", 500);
        Cart cart = new Cart();


        try{
            //1.provided example
//            cart.addProduct(cheese, 2);
//            cart.addProduct(biscuits, 1);
//            cart.addProduct(scratchCard, 1);


            //2.example of expired product
//            cart.addProduct(cheese, 2);
//            cart.addProduct(expiredBiscuits, 1);
//            cart.addProduct(scratchCard, 2);


            //3.example on insufficient balance
//             cart.addProduct(cheese, 4);
//             cart.addProduct(biscuits, 2);


            //4. no enough quantity
            cart.addProduct(cheese, 1);
            cart.addProduct(scratchCard, 4);



        }
        catch (IllegalArgumentException e) {
            System.out.println("NOTE: " + e.getMessage());
        }

        try {
            Checkout.checkout(customer, cart);
        }
        catch (IllegalArgumentException e) {
            System.out.println("NOTE: " + e.getMessage());
        }


    }
}