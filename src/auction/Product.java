package auction;
/********
 *  File name: Product.java
 *  Date: 25.01.2022
 *  Author: Igor Stepanenko
 *  Description: Product class to store information about what is being auctioned
 * **********/
import java.io.*;
import java.util.Comparator;
import java.util.Random;

public class Product implements Serializable, Comparable<Product> {

    // serial version id
    private static final long serialVersionUID = 1L;

    // enum
    public enum ProductType{
        WINE(0,0),
        BOOK(0,0),
        WHISKY(0,0);
        private int lastSalePrice;
        private int maxSalePrice;
        ProductType(int lastSalePrice, int maxSalePrice){
            this.lastSalePrice=lastSalePrice;
            this.maxSalePrice=maxSalePrice;
        }

        public int getLastSalePrice() {
            return lastSalePrice;
        }

        public void setLastSalePRice(int lastSalePrice) {
            if (getMaxSalePrice()<lastSalePrice) {
                this.maxSalePrice=lastSalePrice;
            } else this.lastSalePrice = lastSalePrice;
        }

        public int getMaxSalePrice() {
            return maxSalePrice;
        }

        public void setMaxSalePrice(int maxSalePrice) {
            this.maxSalePrice = maxSalePrice;
        }

        static ProductType randomProduct(){
            Random rand = new Random();
            final int size = ProductType.values().length;
            ProductType[] ar= values();

            return ar[rand.nextInt(size)];
        }
    }
    private ProductType type;
    private double salePrice;
    private String name;

    // constructor
    Product(ProductType type, double salePrice, String name){
        this.type = type;
        this.salePrice = salePrice;
        this.name = name;
    }

    // accessors
    public ProductType getType() {
        return type;
    }
    public double getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "Product of " + "type: " + getType() + ", salePrice: " + salePrice + ", name: " + name ;
    }

    // compare by sale price
    @Override
    public int compareTo(Product o) {
        if  (salePrice < o.salePrice)
            return (int) o.salePrice;
        else return (int) salePrice;
    }

    // compare by product type
    public static class CompareProductType implements Comparator<Product>{
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getType().compareTo(o2.getType());
        }
    }

    // compare by product max price
    public static class CompareMaxPrice implements Comparator<Product>{
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getType().maxSalePrice - o2.getType().maxSalePrice;
        }
    }

    // compare by product name
    public static Comparator<Product> createLambda(){
        return (p1, p2) -> p1.name.compareTo(p2.name);
    }

    public static void main(String[] args) {
        /************************** test 1****************************/
        Product product1 = new Product(ProductType.WINE, 10, "Repin");
        Product product2 = new Product(ProductType.BOOK, 20, "War and Peace");
        Product product3 = new Product(ProductType.WHISKY, 30, "Jack Daniels");

        /************************** testing serializable ****************************/
        String filename1 = "product1.ser";
        String filename2 = "product2.ser";
        String filename3 = "product3.ser";

        // saving product1
        try{
            FileOutputStream fos = new FileOutputStream(filename1);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(product1);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        // saving product2
        try{
            FileOutputStream fos = new FileOutputStream(filename2);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(product2);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        // saving product3
        try{
            FileOutputStream fos = new FileOutputStream(filename3);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(product3);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        /************************** setSalePrice test ****************************/
        System.out.println("setSalePrice test");
        product1.setSalePrice(100);
        product2.setSalePrice(50);
        product3.setSalePrice(200);
        System.out.println(product1 + "\n" + product2 + "\n" + product3);

        System.out.println();
        /************************** compareTo test ****************************/
        int[] compare1Test = new int[3];
        compare1Test[0] = product1.compareTo(product2);
        compare1Test[1] = product1.compareTo(product3);
        compare1Test[2] = product2.compareTo(product3);

        /************************** compareProductType/compareMaxPrice test ****************************/
        Comparator<Product> compare2 = new CompareProductType();
        Comparator<Product> compare3 = new CompareMaxPrice();
        Comparator<Product> compare4 = createLambda();

        /************************** store results of the test above ****************************/
        int[] compare2Test = new int[3];
        compare2Test[0] = compare2.compare(product1,product2);
        compare2Test[1] = compare2.compare(product1,product3);
        compare2Test[2] = compare2.compare(product2,product3);

        int[] compare3Test = new int[3];
        compare3Test[0] = compare3.compare(product1,product2);
        compare3Test[1] = compare3.compare(product1,product3);
        compare3Test[2] = compare3.compare(product2,product3);

        int[] compare4Test = new int[3];
        compare4Test[0] = compare4.compare(product1,product2);
        compare4Test[1] = compare4.compare(product1,product3);
        compare4Test[2] = compare4.compare(product2,product3);

        /************************** comparator tests ****************************/
        System.out.println("Comparators test");
        System.out.println("Compare by sale price");
        for (int p : compare1Test)
            System.out.println(p);

        System.out.println("\nCompare by type");
        for (int p : compare2Test)
            System.out.println(p);

        System.out.println("\nCompare by max price");
        for (int p : compare3Test)
            System.out.println(p);

        System.out.println("\nCompare by name");
        for (int p : compare4Test)
            System.out.println(p);


        System.out.println();
        /************************** serializable loading objects test ****************************/
        System.out.println("Loading objects");

        // loading Product 1
        try{
            FileInputStream fis = new FileInputStream(filename1);
            ObjectInputStream in = new ObjectInputStream(fis);
            product1 = (Product) in.readObject();
            in.close();
            System.out.println(product1);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        // loading Product 2
        try{
            FileInputStream fis = new FileInputStream(filename2);
            ObjectInputStream in = new ObjectInputStream(fis);
            product2 = (Product) in.readObject();
            in.close();
            System.out.println(product2);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        // loading Product 3
        try{
            FileInputStream fis = new FileInputStream(filename3);
            ObjectInputStream in = new ObjectInputStream(fis);
            product3 = (Product) in.readObject();
            in.close();
            System.out.println(product3);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }


    }
}
