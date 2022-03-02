package auction;
/********
 *  File name: Auction.java
 *  Date: 25.01.2022
 *  Author: Igor Stepanenko
 *  Description: Run a simulated auction using Agent and Product classes
 * **********/
import java.util.*;

public class Auction {
    private int numAgents;
    private ArrayList<Agent> agents;
    TreeSet<Triple<Agent, Product, Double>> bids;
    Triple<Agent, Product, Double> highestBid;

    // constructor
    public Auction(int numAgents, Product[] products) {
        if (numAgents <= 0)
            throw new RuntimeException("Agents should be more than 0");
        if (products == null || products.length == 0)
            throw new RuntimeException("Products should be more than 0");
        // make agents with products
        agents= new ArrayList<>();
        for (int i = 0; i < numAgents; i++)
        {
            // Calculate random money
            Random rand = new Random();
            double randMoney  = rand.nextDouble(1000)+500;
            // Create one agent and give random money between 1000 and 500
            Agent agent = new Agent(randMoney);
            // Add products
            agent.addProducts(products);
            // Adda agent to agent list
            agents.add(agent);
        }
    }

    // getters
    public int getNumAgents()
    {
        return agents.size();
    }
    public ArrayList<Agent> getAgents()
    {
        return agents;
    }

    // triple class
    public static class Triple<Agent, Product, Double extends Comparable<Double>>
            implements Comparable<Triple<Agent, Product, Double>>{

        private Agent agent;
        private Product product;
        private Double dbl;

        //Constructor
        public Triple(Agent agent, Product product, Double dbl) {
            this.agent = agent;
            this.product = product;
            this.dbl= dbl;
        }

        //Accessors
        public Agent getAgent() {
            return agent;
        }
        public Product getProduct(){
            return product;
        }
        public Double getDouble(){
            return dbl;
        }

        @Override
        public int compareTo(Triple<Agent, Product, Double> o) {
            return o.dbl.compareTo(dbl);
        }

        @Override
        public String toString() {
            return "Triple: " +"agent= " + agent + ", product= " + product +", dbl= " + dbl + "\n";
        }
    }

    public void singleAuction(){
        // set random product type
        Product.ProductType randProduct = Product.ProductType.randomProduct();
        Agent.setCurrentProduct(randProduct);

        // select a random agent to sell product
        Random rand =  new Random();
        Agent agent = getAgents().get(rand.nextInt(getNumAgents()));
        // select a random product
        Product productOffer = agent.offerProduct();
        System.out.println("PRODUCT INFO: " + productOffer);

        // if agent doesn't have the product choose another agent
        while(productOffer==null){
            productOffer = getAgents().get(rand.nextInt(getNumAgents())).offerProduct();
        }

        // get all other agents to bid and store in triple
        bids = new TreeSet<>();
        for (int i=0; i<getNumAgents()-1; i++)
        {
            // if agent doesn't have money remove
            if(agents.get(i).getMoney()<=0){
                agents.remove(i);
            }
            double bid = agents.get(i).makeBid(productOffer);
            Triple<Agent, Product, Double> triple = new Triple<>(agents.get(i), productOffer, bid);
            bids.add(triple);
        }
        // find largest bid
        highestBid = bids.first();
        System.out.println("HIGHEST BIDDER: "+ highestBid.agent + ", BID = "+ highestBid.dbl);

        // adjust selling agent
        agent.sellProduct(highestBid.dbl, productOffer);

        // adjust the buying agent
        if (highestBid.agent.getCurrentSize()>=100){
            // could copy into a (n+1) array
            throw new RuntimeException("Agents product list is full!");
        }
        highestBid.agent.buyProduct(highestBid.dbl, highestBid.product);
    }

    public void multipleAuctionSimulation(int numAuctions){
        for (int i=0; i<numAuctions; i++){
            System.out.println("Auction "+ (i+1));
            singleAuction();
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int numAgents = 6;
        Product[] products = {
                new Product(Product.ProductType.WINE, 10, "Rose"),
                new Product(Product.ProductType.WINE, 15, "Red"),
                new Product(Product.ProductType.WINE, 20, "White"),
                new Product(Product.ProductType.BOOK, 25, "Paper"),
                new Product(Product.ProductType.BOOK, 30, "Glass"),
                new Product(Product.ProductType.WHISKY, 35, "JD")
        };

        Auction auction = new Auction(numAgents, products);
        // run 10 auctions
        auction.multipleAuctionSimulation(10);
    }
}
