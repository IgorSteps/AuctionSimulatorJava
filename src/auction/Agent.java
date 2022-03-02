package auction;
import java.util.*;

/********
 *  File name: Agent.java
 *  Date: 25.01.2022
 *  Author: Igor Stepanenko
 *  Description: Agent class for a bidder in an auction who buys, sells, offers products
 * **********/
public class Agent implements Iterable<Product>{
    private Product[] products;
    private int currentSize;
    private final int MAX_SIZE = 100;
    private double money;

    // constructor
    Agent(double money){
        this.money = money;
    }

    // getters and setters
    public double getMoney() {
        return money;
    }
    public Product[] getProducts() {
        return products;
    }
    public int getCurrentSize(){return currentSize;}
    public int getMaxSize() {
        return MAX_SIZE;
    }
    public static void setCurrentProduct(Product.ProductType type){
        currentProduct=type;
    }

    // set current product type for testing
    private static Product.ProductType currentProduct = Product.ProductType.WINE;


    public void addProducts(Product[] prod) {
        if (products==null) {
            products = prod;
            currentSize= prod.length;
        }
        else {
            Product[] pr = Arrays.copyOf(products, products.length + prod.length);
            System.arraycopy(prod, 0, pr, products.length, prod.length);
            products = pr;
            currentSize = pr.length;
        }
    }

    // iterator
    public static class ProductIterator implements Iterator<Product> {
        Product[] prod;
        int pos = 0;
        public ProductIterator(Product[] prod) {
            this.prod = prod;
        }
        @Override
        public boolean hasNext() {
            while (pos < prod.length ){
                if (prod[pos] != null && prod[pos].getType().equals(currentProduct))
                    return true;
                pos++;
            }
            return false;
        }
        @Override
        public Product next() {
            if (hasNext())
                return prod[pos++];
            throw new NoSuchElementException();
        }
    }
    @Override
    public Iterator<Product> iterator() {
        return new ProductIterator(products);
    }

    public boolean sellProduct(double price, Product product){
        Product[] copy = new Product[products.length - 1];
        for(int i=0, j=0; i< products.length; ++i){
            if (products[i]==product){
                copy[j++] = products[i];
                money += price; //Add money
                return true;
            }
        }
        // copy new array with n+1
        products = copy;
        // change currentSize
        currentSize = copy.length;
        return false;
    }

    public boolean buyProduct(double price, Product product){
       Product[] destPr = new Product[products.length+1];
        if (money > price && products.length < MAX_SIZE)
        {
            for (int i = 0; i< products.length; ++i)
            {
                destPr[i] = products[i];
                money -= price;
            }
            destPr[destPr.length-1] = product;
            // copy new array with n+1
            products = destPr;
            // change currentSize
            currentSize = destPr.length;
            return true;
        } else return false;
    }

    public Product offerProduct(){
        Product p = null;
        ProductIterator iter = new ProductIterator(products);
        while (iter.hasNext())
        {
            Product next = iter.next();
            if (next.getType().equals(currentProduct))
                p = next;
        }
        return p;
    }

    public int[] countProducts(){
        int[] arr = new int[3];
        int countWine = 0;
        int countBook = 0;
        int countWhiskey = 0;
        for (Product product : products) {
            if (product.getType().equals(Product.ProductType.WINE)) {
                countWine++;
                arr[0] = countWine;
            } else if (product.getType().equals(Product.ProductType.BOOK)) {
                countBook++;
                arr[1] = countBook;
            } else {
                countWhiskey++;
                arr[2] = countWhiskey;
            }
        }
        return arr;
    }

    public double makeBid(Product product){
        double bid = 0;
        switch (product.getType()){
            case WINE -> {
                if (countProducts()[0] ==0) {
                    bid = 0.25 * money;
                }  else if (countProducts()[0] == 1) {
                    bid = 0.2 * money;
                } else if (countProducts()[0] == 2) {
                    bid = 0.16 * money;
                } else bid = 0.1 * money;
            }
            case BOOK -> {
                if (countProducts()[1] ==0) {
                    bid = 0.25 * money;
                }  else if (countProducts()[1] == 1) {
                    bid = 0.2 * money;
                } else if (countProducts()[1] == 2) {
                    bid = 0.16 * money;
                } else bid = 0.1 * money;
            }
            case WHISKY -> {
                if (countProducts()[2] ==0) {
                    bid = 0.25 * money;
                }  else if (countProducts()[2] == 1) {
                    bid = 0.2 * money;
                } else if (countProducts()[2] == 2) {
                    bid = 0.16 * money;
                } else bid = 0.1 * money;
            }
        }
        return bid;
    }

    public static void main(String[] args) {

        /************************** Test 1 ****************************/
        Agent myAgent = new Agent(1000);
        myAgent.products = new Product[0];
        Product p1 = new Product(Product.ProductType.WINE, 10, "Rose");
        Product p2 = new Product(Product.ProductType.WINE, 15, "Red");
        Product p3 = new Product(Product.ProductType.WINE, 20, "White");
        Product p4 = new Product(Product.ProductType.BOOK, 25, "Paper");
        Product p5 = new Product(Product.ProductType.BOOK, 30, "Glass");
        Product p6 = new Product(Product.ProductType.WHISKY, 35, "JD");
        myAgent.buyProduct(50, p1);
        myAgent.buyProduct(50, p2);
        myAgent.buyProduct(50, p3);
        myAgent.buyProduct(50, p4);
        myAgent.buyProduct(50, p5);
        myAgent.buyProduct(50, p6);


        /************************** Test 2 ****************************/
        System.out.println("Iterate over and print out all products of type BOOK test");
        setCurrentProduct(Product.ProductType.BOOK);    //Set currentProduct type to BOOK
        ProductIterator iter = new ProductIterator(myAgent.products);
        while (iter.hasNext()) {
            Product p = iter.next();
            System.out.println(p);
        }

        System.out.println();

        /************************** Test 3 ****************************/
        System.out.print("Offer product test");
        myAgent.offerProduct();
        System.out.println(myAgent.offerProduct());

        System.out.println();

        /************************** Test 4 ****************************/
        System.out.println("Sell product test");
        boolean res = myAgent.sellProduct(100, myAgent.offerProduct());
        if (res) {
            System.out.println("Operation was successful");
        }  else System.out.println("Operation was unsuccessfully");

        System.out.println("Print remaining products: ");
        ProductIterator iter2 = new ProductIterator(myAgent.products);
        while (iter2.hasNext())
        {
            Product pr = iter2.next();
            System.out.println(pr);
        }
    }
}
