//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.*;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private Map<Product, Integer> cart; // map to hold all toString products added to cart with the quantity
    private Map<Product, Integer> newCart;
    private List<Map.Entry<Product, Integer>> productsbought;
    private double revenue;
    private int sales;
    private String sale;
    private double totalCart;


    public ElectronicStore(String initName) {
        revenue = 0.00;
        sales = 0;
        sale = "N/A";
        totalCart = 0.00;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        cart = new LinkedHashMap<>(); // I used linked hashmap so the order of added products would be better
        // but i know  If performance becomes an issue i may should not use it
        newCart = new LinkedHashMap<>();
        productsbought = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getTotalCart() {
        return totalCart;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getSales() {
        return sales;
    }

    public String getSale() {
        return sale;
    }

    public List<Map.Entry<Product, Integer>> getProductsbought() {
        return productsbought;
    }

    public void setRevenue(double revenue) {
        this.revenue += revenue;
    }

    public void setSale(String sale) {this.sale = sale;}

    public void setSales(int sales) {
        this.sales += sales;
    }

    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }


    public Product[] getStock() {
        Product[] filterlist = new Product[curProducts];
        for (int i = 0; i < stock.length; i++) {
            if (stock[i] != null) {
                filterlist[i] = stock[i];
            }
        }
        return filterlist;
    }


    public String[] getCartlist() {
        String[] cartList = new String[cart.size()];
        int i = 0;
        for (Product s : cart.keySet()) {
            cartList[i] = ""; // to initialize it so no null is shown
            cartList[i] += cart.get(s) + " x " + s;
            i++;
        }
        return cartList;
    }


    public void addTocart(int index) {
        Product productName = stock[index];
        stock[index].sellUnits(1);
        if (cart.containsKey(productName)) {
            int currentQuantity = cart.get(productName);
            cart.put(productName, currentQuantity + 1);
            totalCart += stock[index].getPrice();
        } else {
            cart.put(productName, 1);
            totalCart += stock[index].getPrice();
        }
        if (stock[index].getStockQuantity() == 0) {
            removeProduct(index);
        }
        System.out.println(newCart);
    }


    public void removeFromCart(String productName) {
        String[] cartList = new String[cart.size()];
        int i = 0;
        for (Product s : cart.keySet()) {
            cartList[i] = ""; // to initialize it so no null is shown
            cartList[i] += "[" + cart.get(s) + " x " + s + "]";
            if (cartList[i].equals(productName)) {
                if (cart.get(s) < 2) {
                    cart.remove(s);
                    totalCart -= s.getPrice();
                    break;
                }
                cart.put(s, cart.get(s) - 1);
                totalCart -= s.getPrice();
                break;
            }
            i++;
        }
    }

    public void deleteCartList() {
        for (Product s : cart.keySet()){
            if (newCart.containsKey(s)){
                newCart.put(s,cart.get(s)+newCart.get(s));
            }
            else {
                newCart.put(s, cart.get(s));
            }
        }
        productsbought = new ArrayList<>(newCart.entrySet());
        Collections.sort(productsbought, new Comparator<Map.Entry<Product, Integer>>() {
            public int compare(Map.Entry<Product, Integer> o1, Map.Entry<Product, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        mostPopular();
        cart = new LinkedHashMap<>();
        totalCart = 0.00;
    }

    public void removeProduct(int index) {
        Product[] newArray = new Product[stock.length - 1];
        for (int i = 0, j = 0; i < stock.length; i++) {
            if (i == index) {
                curProducts--;
                continue;
            }
            newArray[j++] = stock[i];
        }
        stock = newArray;
    }

    public void reset(){

        ElectronicStore model2 = ElectronicStore.createStore();
        this.curProducts = model2.curProducts;
        this.stock = model2.stock;
        this.cart = model2.cart;
        this.newCart = model2.newCart;
        this.productsbought = model2.productsbought;
        this.revenue = 0.00;
        this.sales = 0;
        this.sale = "N/A";
        this.totalCart = 0.00;
    }
    public Product[] mostPopular() {
        Product[] popularProducts;
        if (productsbought.size() < 3){
            popularProducts = new Product[3];
        }
        else{
             popularProducts = new Product[productsbought.size()];
        }
        int i = 0;
        if (!productsbought.isEmpty()) {
            for (Map.Entry<Product, Integer> entry : productsbought) {
                popularProducts[i] = entry.getKey();
                i++;
            }
            while (i<3){
                popularProducts[i] = stock[i];
                i++;
            }
        }
        else{
                popularProducts = stock ;
        }
        return Arrays.copyOfRange(popularProducts,0,3);
    }

        public static ElectronicStore createStore () {
            ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
            Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
            Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
            Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
            Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
            Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
            Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
            ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
            ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
            store1.addProduct(d1);
            store1.addProduct(d2);
            store1.addProduct(l1);
            store1.addProduct(l2);
            store1.addProduct(f1);
            store1.addProduct(f2);
            store1.addProduct(t1);
            store1.addProduct(t2);
            return store1;
        }
    }
