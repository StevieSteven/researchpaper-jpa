package net.stremo.graphqljpa;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import net.stremo.graphqljpa.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TestDataSetup {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RatingReposistory ratingReposistory;

    @Autowired
    ShoppingcardRepository shoppingcardRepository;

    @Autowired
    ShoppingcardElementRepository shoppingcardElementRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;


    @Transactional
    public void run() {
        System.out.println("Create TestData");

        Faker faker = new Faker();
        Random r = new Random();
        int step = 1;
        int numberOfUser = 40;
        int numberOfProducts = 200;

        System.out.println(step + ". Create Customer. Number: " + numberOfUser);
        for (int i = 0; i < numberOfUser; i++) {
            Name fakerName = faker.name();
            Customer c = new Customer(fakerName.firstName(), fakerName.lastName(), faker.internet().emailAddress());
            customerRepository.save(c);

            for (int j = 0; j < new Random().nextInt(10); j++) {
                com.github.javafaker.Address fakerAddress = faker.address();
                Address a = new Address(fakerAddress.streetName(), fakerAddress.buildingNumber(), fakerAddress.city(), fakerAddress.zipCode());
                addressRepository.save(a);
                c.addAddress(a);
            }

        }

        step++;

        System.out.println(step + ". create order status");
        orderStatusRepository.save(new OrderStatus("bestellt"));
        orderStatusRepository.save(new OrderStatus("verpackt"));
        orderStatusRepository.save(new OrderStatus("versendet"));
        orderStatusRepository.save(new OrderStatus("geliefert"));
        orderStatusRepository.save(new OrderStatus("abgeschlossen"));

        step++;


        System.out.println(step + ". Create Categories.");

        Category cHome = new Category("Haushalt");
        categoryRepository.save(cHome);
        Category cTechnic = new Category("Technik");
        categoryRepository.save(cTechnic);
        Category cCar = new Category("Auto");
        categoryRepository.save(cCar);
        Category cMovieSeries = new Category("Filme und Serien");
        categoryRepository.save(cMovieSeries);

        Category cMovie = new Category("Filme", cMovieSeries);
        categoryRepository.save(cMovie);

        Category cSerie = new Category("Serie", cMovieSeries);
        categoryRepository.save(cSerie);

        step++;

        System.out.println(step + ". create products. Number of Products: different of each category. Sum: 5600");

        this.createProducts(800, cHome);
        this.createProducts(4000, cTechnic);
        this.createProducts(500, cCar);
        this.createProducts(200, cMovie);
        this.createProducts(100, cSerie);


        step++;

        System.out.print(step + ": Create Ratings... ");
        final int[] ratingCounter = {0};
        customerRepository.findAll().forEach(customer -> {
            int upperBound = r.nextInt(20);
            for (int i = 0; i < upperBound; i++) {
                Long id = r.nextLong();
                if (id < 0) {
                    id = id * -1;
                }
                id = id % productRepository.count();
                Product p = productRepository.findOne(id);
                Rating rating = new Rating(r.nextInt(5), faker.lorem().sentence(), customer, p);
                ratingReposistory.save(rating);
//                customer.addRating(rating);
                ratingCounter[0]++;
            }
        });

        System.out.println(ratingCounter[0] + " Ratings in sum.");

        step++;


        System.out.println(step + ": Create shoppingCards for every second user. Fill products with 1 to 10 items.");

        int cardCounter = 0;
        for (int i = 1; i < customerRepository.count() - 1; i = i + 2) {
            Customer customer = customerRepository.findOne((long) i);

            if(customer.getShoppingcard() == null) {

                Shoppingcard card = new Shoppingcard(customer);
                shoppingcardRepository.save(card);

                int upperBound = r.nextInt(10);
                for (int j = 0; j < upperBound; j++) {
                    Long id = r.nextLong();
                    if (id < 0) {
                        id = id * -1;
                    }
                    id = id % productRepository.count();
                    Product p = productRepository.findOne(id);
                    ShoppingcardElement element = new ShoppingcardElement(r.nextInt(20), card, p);
                    shoppingcardElementRepository.save(element);
                    cardCounter++;
                }

            }

        }
        System.out.println("\t" + cardCounter + " products filled into shopping cards.");

        step++;

        System.out.println(step + " create orders. every order has 1 to 10 products. A Customer has 0 to 20 orders in History");

        customerRepository.findAll().forEach(customer -> {
            int upperBound = r.nextInt(10);
            for (int i = 0; i < upperBound; i++) {
                List<Address> addressList = customer.getAddresses();
                if (addressList.size() == 0)
                    continue;
                int addressIndex = r.nextInt(addressList.size());
                Address a = addressList.get(addressIndex);
                Order order = new Order(a, orderStatusRepository.findOne((long) r.nextInt((int) orderStatusRepository.count() - 1) + 1), customer);
                orderRepository.save(order);
                customer.addOrder(order);
//                customer.setOrder(order);
                int quantityItems = r.nextInt(10);
                int numberOfProductsInStore = (int) productRepository.count();
                for (int productIndex = 0; productIndex < quantityItems; productIndex++) {
                    long productId = (long) r.nextInt(numberOfProductsInStore - 1) + 1;
                    Product productInOrder = productRepository.findOne(productId);
                    OrderItem orderItem = new OrderItem(r.nextInt(10) + 1, order, productInOrder);
                    orderItemRepository.save(orderItem);
                    order.addOrderItem(orderItem);
                }
            }
        });


        System.out.println("Dummy data is created successfully.");
    }


    private void createProducts(int number, Category category) {
        Faker faker = new Faker();
        for (int i = 0; i < number; i++) {
            Float price = (float) (new Random().nextInt(100000)) / 100;
            Product p = new Product(faker.beer().name(), price, new Random().nextInt(10) + 1, faker.lorem().paragraph());
            p.setCategory(category);
            productRepository.save(p);
        }

    }
}
