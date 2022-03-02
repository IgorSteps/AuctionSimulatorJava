# AuctionSimulatorJava
## Auction Simulator in Java Timed Exercise

### Comments
I made this program as a timed exrecise set in my Programming module. Time was limited and I had to follow a specification which I summmarised below.

### Description
The simplified auctions we simulate involves the buying selling of multiple instances of the same product. We assume a product is always one of three types:
wine; book; or whisky. Each participant in the auction (we call them agents), starts with a set of products and a pool of money. They then try to buy or sell
their products to other agents in a simple auction mechanism equivalent to a single round sealed bid auction.

This exercise involves three classes: a Product class to store information about what is being auctioned, an Agent class for an bidder in an auction and
an Auction class to simulate a simplified sequential auction (each agent submits at most a single bid, highest bid wins).

The exercise is designed to test my understanding of the topics covered on the Programming module, not to test whether I can simulate an auction in any style I wish.
This means the structure and design of the code may not be how you would necessarily implement this if given free reign, like using ArrayLists instead of Arrays.
Nevertheless, I followed the specification given.

### Product class
- Enum called `ProductType` consists of: WINE, BOOK or WHISKY;
- Two variables associated with each product type: `lastSalePrice` and `maxSalePrice`;
- Getters and setters for these variables:
  - Method `setLastSalePrice` should also update the value stored in `maxSalePrice` if it's bigger;
- Method `randomProduct` that returns a random `ProductType`;
- Class is `Serializable` and `Comparable`:
  - `CompareProductType` and `CompareMaxPrice`;
- Lambda that returns a `Comparator` which comapares by name;
- Main method which test the above; 

### Agent class
- Array of products called `products`, integer to record how many products agent has `currentSize`, integer specifying the maximum number of products an Agent can own `MAX_SIZE`, set to 100, funds agent has, `money`;
- `addProduct` method to add products;
- Method `setCurrentProduct` to set `currentProduct` field;
- Iterator called `ProductIterator` that iterates over `products` of type `currentProduct`;
- Methods `sellProduct`, `buyProduct`, `offerProduct`, `countProducts`, `makeBid` to manipulate `products` array and make bids;
- Main methods that test the above;

### Auction class
- ArrayList `agents` to store agents, `numAgents` to specify number of agents, `bids` TreeSet consisting of triples and a `highestBid` triple object;
- A generic class called `Triple` that stores `Agent`, `Product` and `Bid`, also `Comparable`;
- A constructor that creates agents with products based on the `numAgents` passed and `products` array;
- Method `singleAuction` for a single product and method `multipleAuctionSimulation` that takes `numAuctions` and calls `singleAuction`  that number of times;
- Main method to simulate auctions;
