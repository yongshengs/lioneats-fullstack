package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.*;
import com.lioneats.lioneats_backend.service.*;
import com.lioneats.lioneats_backend.service.api.ShopDataInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${data.initialization.enabled}")
    private boolean dataInitializationEnabled;

    @Autowired
    private MRTService mrtService;

    @Autowired
    private CircleService circleService;

    @Autowired
    private ShopDataInitializationService shopDataInitializationService;

    @Autowired
    private CircleMRTService circleMRTService;

    @Autowired
    private DishDetailService dishDetailService;

    @Autowired
    private DishService dishesService;

    @Autowired
    private AllergyService allergyService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (dataInitializationEnabled) {
            initializeData();
        } else {
            System.out.println("Data initialization is disabled. Skipping initialization.");
        }
    }

    private void initializeData() {
//        createDishesAndDishdetails();
//        createAllergies();
//        createUsers();
//        createMRTS();
//        createCircles();
//        shopDataInitializationService.initializeShopData(circleMRTService.getAllCircles());
    }

    private void createUsers()
    {

        List<DishDetail> dishPreferences1 = dishDetailService.findByNameIn(Arrays.asList("Chicken Rice"));
        List<Allergy> allergies1 = allergyService.findByNameIn(Arrays.asList("Peanut", "Seafood"));

        User user1 = new User();
        user1.setName("Jack");
        user1.setUsername("jack123");
        user1.setPassword("password");
        user1.setEmail("jack@example.com");
        user1.setAge(20);
        user1.setGender(User.Gender.Male);
        user1.setCountry("Singapore");
        user1.setPreferredBudget(User.PreferredBudget.LOW);
        user1.setLikesSpicy(true);
        user1.setDishPreferences(dishPreferences1);
        user1.setAllergies(allergies1);

        userService.createUser(user1);

        List<DishDetail> dishPreferences2 = dishDetailService.findByNameIn(Arrays.asList("Chilli Crab", "Nasi Lemak"));
        List<Allergy> allergies2 = allergyService.findByNameIn(Arrays.asList("Peanut", "Seafood"));

        User user2 = new User();
        user2.setName("Kunkka");
        user2.setUsername("kunkka123");
        user2.setPassword("password");
        user2.setEmail("kunkka@example.com");
        user2.setAge(30);
        user2.setGender(User.Gender.Male);
        user2.setCountry("Singapore");
        user2.setPreferredBudget(User.PreferredBudget.MEDIUM);
        user2.setLikesSpicy(false);
        user2.setDishPreferences(dishPreferences2);
        user2.setAllergies(allergies2);

        userService.createUser(user2);

        List<DishDetail> dishPreferences3 = dishDetailService.findByNameIn(Arrays.asList("Satay", "Roti Prata"));
        List<Allergy> allergies3 = allergyService.findByNameIn(Arrays.asList("Soy"));

        User user3 = new User();
        user3.setName("John");
        user3.setUsername("john123");
        user3.setPassword("password");
        user3.setEmail("john@example.com");
        user3.setAge(40);
        user3.setGender(User.Gender.Male);
        user3.setCountry("Singapore");
        user3.setPreferredBudget(User.PreferredBudget.HIGH);
        user3.setLikesSpicy(true);
        user3.setDishPreferences(dishPreferences3);
        user3.setAllergies(allergies3);

        userService.createUser(user3);

    }


    private void createAllergies() {
        List<DishDetail> glutenDishes = dishDetailService.findByNameIn(Arrays.asList("Roti Prata", "Curry Puff", "Pulut Hitam"));
        List<DishDetail> seafoodDishes = dishDetailService.findByNameIn(Arrays.asList("Chilli Crab", "Nasi Lemak", "Teochew Png Kueh", "Soon Kueh"));
        List<DishDetail> peanutDishes = dishDetailService.findByNameIn(Arrays.asList("Chilli Crab", "Nasi Lemak", "Satay", "Teochew Png Kueh"));
        List<DishDetail> eggDishes = dishDetailService.findByNameIn(Arrays.asList("Chilli Crab", "Chai Tow Kway", "Curry Puff"));
        List<DishDetail> sesameDishes = dishDetailService.findByNameIn(Arrays.asList("Chicken Rice", "Teochew Png Kueh"));
        List<DishDetail> soyDishes = dishDetailService.findByNameIn(Arrays.asList("Chicken Rice", "Chilli Crab", "Chai Tow Kway", "Teochew Png Kueh", "Soon Kueh"));

        Allergy glutenAllergy = new Allergy();
        glutenAllergy.setName("Gluten");
        glutenAllergy.setDishes(glutenDishes);


        Allergy seafoodAllergy = new Allergy();
        seafoodAllergy.setName("Seafood");
        seafoodAllergy.setDishes(seafoodDishes);

        Allergy peanutAllergy = new Allergy();
        peanutAllergy.setName("Peanut");
        peanutAllergy.setDishes(peanutDishes);

        Allergy eggAllergy = new Allergy();
        eggAllergy.setName("Egg");
        eggAllergy.setDishes(eggDishes);

        Allergy sesameAllergy = new Allergy();
        sesameAllergy.setName("Sesame");
        sesameAllergy.setDishes(sesameDishes);

        Allergy soyAllergy = new Allergy();
        soyAllergy.setName("Soy");
        soyAllergy.setDishes(soyDishes);

        List<Allergy> allergies = Arrays.asList(
                glutenAllergy,
                seafoodAllergy,
                peanutAllergy,
                eggAllergy,
                sesameAllergy,
                soyAllergy
        );

        for (Allergy allergy : allergies) {
            allergyService.createAllergy(allergy);
        }
    }


    public void createDishesAndDishdetails()
    {

        DishDetail chickenRiceDetail = new DishDetail();
        chickenRiceDetail.setIsSpicy(false);
        chickenRiceDetail.setName("Chicken Rice");
        chickenRiceDetail.setIngredients("Chicken, salt, water, ginger, spring onions, jasmine rice, chicken fat, garlic, chicken stock");
        chickenRiceDetail.setDescription("Hainanese Chicken Rice exemplifies the perfect balance of textures and flavors, combining tender chicken, fragrant rice, and vibrant sauces. Its popularity extends beyond Singapore, making it a well-loved dish in Southeast Asian cuisine.");
        chickenRiceDetail.setHistory("Hainanese Chicken Rice has a storied history that reflects the cultural melting pot of Singapore. It was brought to Singapore by Chinese immigrants from Hainan province. Over time, it evolved to include local influences and preferences.\\n\\nThe dish became popular in the 1920s and 1930s, with Hainanese immigrants opening food stalls and restaurants. Today, it is considered a national dish of Singapore, enjoyed by people from all walks of life.");
        dishDetailService.saveDishDetail(chickenRiceDetail);


        DishDetail chilliCrabDetail = new DishDetail();
        chilliCrabDetail.setIsSpicy(true);
        chilliCrabDetail.setName("Chilli Crab");
        chilliCrabDetail.setIngredients("Fresh Crabs, Chilli Sauce, Tomato Sauce, Garlic, Ginger, Eggs, Cornstarch, Soy Sauce, Vinegar, Sugar, Spring Onions, Coriander");
        chilliCrabDetail.setHistory("The origin of Chilli Crab is a tale of innovation and tradition. It was created in the 1950s by a Singaporean chef named Cher Yam Tian. She initially experimented with a simple stir-fry of crabs with bottled tomato sauce. Over time, she added fresh chilli paste, and the dish evolved into the beloved Chilli Crab we know today.Cher Yam Tian's husband, Lim Choon Ngee, helped popularize the dish by selling it from a pushcart along the Kallang River. Its popularity soared, and in 1963, they opened the famous Palm Beach Seafood Restaurant, cementing Chilli Crab as a national dish of Singapore.");
        chilliCrabDetail.setDescription("Chilli Crab is a symphony of flavors and textures. The crab is typically stir-fried in a semi-thick, sweet, and savory tomato-and-chilli-based sauce. The sauce is rich and tangy, with just the right amount of spice to awaken your senses without overwhelming them.  \n" +
                "The crabs are served whole, often with their shells cracked for easier eating, allowing diners to savor the tender, juicy meat within. The beaten eggs added towards the end of cooking create silky ribbons that enhance the sauce’s lusciousness. This dish is often enjoyed with fried mantou buns, which are perfect for mopping up every last drop of the delectable sauce.");
        dishDetailService.saveDishDetail(chilliCrabDetail);

        DishDetail cendolDetail = new DishDetail();
        cendolDetail.setName("Cendol");
        cendolDetail.setIsSpicy(false);
        cendolDetail.setIngredients("Pandan Jelly Noodles, Coconut Milk, Palm Sugar Syrup(Gula Melaka), Shaved Ice, Red Beans, Sweet Corn, Palm Seeds");
        cendolDetail.setHistory("Cendol is a traditional dessert enjoyed across Southeast Asia, particularly in Singapore, Malaysia, Indonesia, and Thailand. Its origins are believed to be Javanese, with the distinctive green pandan noodles as a key feature. In Singapore, cendol has become a popular street food, commonly found in hawker centers and food stalls. Over time, it has evolved into a beloved cooling treat, perfect for the tropical climate.");
        cendolDetail.setDescription("Cendol is a refreshing dessert made with shaved ice, rich coconut milk, and sweet palm sugar syrup (gula melaka). It features green pandan jelly noodles, soft red beans, and sometimes sweet corn or attap chee (palm seeds). The combination of icy coldness, creamy coconut, and caramelized sweetness creates a delightful blend of textures and flavors, making it a perfect treat to beat the heat.");
        dishDetailService.saveDishDetail(cendolDetail);

        DishDetail pulutHitamDetail = new DishDetail();
        pulutHitamDetail.setName("Pulut Hitam");
        pulutHitamDetail.setIsSpicy(false);
        pulutHitamDetail.setIngredients("Black glutionous rice, Water, Coconut Milk, Palm sugar or Brown Sugar, Pandan leaves, Salt ");
        pulutHitamDetail.setDescription("Pulut Hitam, or black glutinous rice pudding, is a beloved dessert in Southeast Asian cuisine. This dish is characterized by its rich, creamy texture and sweet, nutty flavor. The black glutinous rice, when cooked, turns a deep purple color and becomes sticky, forming the base of the pudding. The addition of coconut milk gives the dessert a creamy consistency, while palm sugar adds a caramel-like sweetness. Pandan leaves, often used in Southeast Asian cooking, impart a subtle, fragrant aroma to the dish. Pulut Hitam is typically served warm, sometimes with an extra drizzle of coconut milk on top, making it a comforting and indulgent treat.");
        pulutHitamDetail.setHistory("Pulut Hitam, also known as black glutinous rice pudding, is a traditional dessert in Southeast Asian cuisine, particularly popular in Malaysia, Indonesia, and Singapore. The dish has its roots in the Malay and Peranakan (Straits Chinese) communities. Black glutinous rice has been cultivated in the region for centuries and is valued for its nutty flavor and chewy texture. The combination of rice with coconut milk and palm sugar creates a rich, creamy dessert that has been enjoyed for generations.");
        dishDetailService.saveDishDetail(pulutHitamDetail);

        DishDetail soonKuehDetail = new DishDetail();
        soonKuehDetail.setName("Soon Kueh");
        soonKuehDetail.setIsSpicy(true);
        soonKuehDetail.setIngredients("Rice flour, Tapioca Flour, Water, Turnip,Bamboo shoots, Dried Shrimp, Dried Mushrooms, Garlic, Shallots, Light Soy Sauce, Dark Soy Sauce, Pepper, Salt, Cooking oil ");
        soonKuehDetail.setHistory("Soon Kueh has its origins in the Teochew (Chaozhou) cuisine of southern China. It was traditionally made during festive occasions and special family gatherings. As Teochew immigrants settled in Singapore and Malaysia, they brought with them their culinary traditions, including Soon Kueh. Over time, this dish has become a beloved part of the local food culture, enjoyed as a breakfast item, snack, or light meal.");
        soonKuehDetail.setDescription("Soon Kueh is a traditional Teochew (Chaozhou) dumpling that is popular in Singapore and Malaysia. These dumplings are characterized by their translucent, chewy skin made from a mixture of rice flour and tapioca flour. The filling is typically a savory combination of shredded turnip (jicama), bamboo shoots, dried shrimp, and mushrooms, stir-fried with garlic and shallots and seasoned with light and dark soy sauce, pepper, and salt. Soon Kueh is often served steamed and can be enjoyed on its own or with a dipping sauce made from sweet soy sauce and chili.");
        dishDetailService.saveDishDetail(soonKuehDetail);

        DishDetail pngKuehDetail = new DishDetail();
        pngKuehDetail.setName("Teochew Png Kueh");
        pngKuehDetail.setIsSpicy(false);
        pngKuehDetail.setIngredients("Glutinous rice, dried shrimp, mushrooms, peanuts, shallots, Chinese sausage, rice flour, and water.");
        pngKuehDetail.setDescription("Teochew Png Kueh is a traditional Teochew rice cake shaped like a peach and typically pink in color. It features a soft, chewy rice flour skin filled with a savory mixture of glutinous rice, dried shrimp, mushrooms, peanuts, and Chinese sausage. The dumpling is often pan-fried to achieve a crispy exterior after being steamed. Png Kueh is a popular snack during festivals and special occasions, offering a delightful combination of textures and umami flavors.");
        pngKuehDetail.setHistory("Teochew Png Kueh has its origins in the Teochew community of southern China and is particularly popular among the Teochew diaspora in Southeast Asia, especially in Singapore and Malaysia. The dish was traditionally made during festivals and special occasions as an offering to ancestors, symbolizing prosperity and good fortune. Over time, it has become a staple in Teochew cuisine, enjoyed for its unique taste and cultural significance.");
        dishDetailService.saveDishDetail(pngKuehDetail);

        DishDetail curryPuffDetail = new DishDetail();
        curryPuffDetail.setName("Curry Puff");
        curryPuffDetail.setIsSpicy(false);
        curryPuffDetail.setIngredients("Pastry Dough, Potatoes, Chicken, Onions, Garlic, Curry Powder, Cumin, Turmeric, Coconut Milk, Salt, Sugar, Egg");
        curryPuffDetail.setHistory("Curry Puffs are a popular snack in Singapore, influenced by Malay, Indian, and Chinese culinary traditions. This delightful pastry is believed to have been inspired by British Cornish pasties and adapted by local communities to include familiar spices and ingredients. It has since become a beloved street food and tea-time treat in Singapore.");
        curryPuffDetail.setDescription("Curry Puff is a deep-fried or baked pastry filled with a flavorful mixture of curried potatoes, onions, and sometimes chicken or beef. The pastry dough is flaky and golden, while the filling is rich and aromatic, thanks to the blend of curry powder, cumin, and turmeric. The creamy coconut milk adds depth to the filling, making each bite a perfect balance of savory and slightly sweet flavors. Curry Puffs are enjoyed as a snack or light meal, often paired with a cup of tea.");
        dishDetailService.saveDishDetail(curryPuffDetail);

        DishDetail nasiLemakDetail = new DishDetail();
        nasiLemakDetail.setName("Nasi Lemak");
        nasiLemakDetail.setIsSpicy(true);
        nasiLemakDetail.setIngredients("Rice, Sambal, Fried Anchovies, Peanuts, Hard-Boiled Eggs, Cucumber Slices, Fried Chicken");
        nasiLemakDetail.setHistory("Nasi Lemak is a traditional Malay dish that has become a beloved staple in Singapore. Originally a simple farmers' meal, it has evolved into a popular breakfast and is often enjoyed throughout the day. Its roots trace back to the Malay Peninsula, where it was made using locally available ingredients. In Singapore, it is commonly found in hawker centers and eateries, enjoyed by people of all ethnic backgrounds.");
        nasiLemakDetail.setDescription("Nasi Lemak is a fragrant rice dish cooked in coconut milk and pandan leaves, giving it a rich aroma and creamy texture. It is typically served with a spicy sambal, crispy fried anchovies, toasted peanuts, hard-boiled eggs, and fresh cucumber slices. Optional additions like fried chicken or fish can enhance the meal. Traditionally wrapped in banana leaves, Nasi Lemak offers a delightful blend of flavors and textures, making it a satisfying and comforting dish.");
        dishDetailService.saveDishDetail(nasiLemakDetail);

        DishDetail rotiPrataDetail = new DishDetail();
        rotiPrataDetail.setName("Roti Prata");
        rotiPrataDetail.setIsSpicy(false);
        rotiPrataDetail.setIngredients("Flour, Water, Ghee, Oil, Salt, Sugar");
        rotiPrataDetail.setHistory("Roti Prata, also known as \"paratha,\" has its origins in South India and was brought to Singapore by Indian immigrants. Over time, it has become a quintessential part of Singaporean cuisine, enjoyed by locals for breakfast, dinner, or as a late-night snack. Its versatility and deliciousness have made it a staple in hawker centers and Indian eateries across Singapore.");
        rotiPrataDetail.setDescription("Roti Prata is a type of flatbread that is crispy on the outside and soft on the inside. The dough, made from flour, water, ghee, salt, and sugar, is stretched and folded multiple times to create thin layers before being cooked on a griddle. It can be enjoyed plain or with various fillings, such as egg, cheese, or even sweet options like banana. Roti Prata is typically served with a side of curry for dipping, making it a flavorful and satisfying dish that delights with every bite.");
        dishDetailService.saveDishDetail(rotiPrataDetail);

        DishDetail satayDetail = new DishDetail();
        satayDetail.setName("Satay");
        satayDetail.setIsSpicy(false);
        satayDetail.setIngredients("Meat(chicken, beef, lamb, pork), Turmeric, Coriander, Cumin, Lemongrass, Garlic, Shallots, Ginger, Brown Sugar, Soy Sauce, Peanut Sauce, Cucumber and Onion");
        satayDetail.setDescription("Satay consists of marinated meat skewers grilled to perfection over an open flame. The marinade typically includes a blend of turmeric, coriander, cumin, lemongrass, garlic, shallots, ginger, brown sugar, and soy sauce, which infuses the meat with a rich and aromatic flavor. Satay is served with a side of peanut sauce for dipping, along with rice cakes (ketupat), cucumber, and onion. The combination of the smoky, tender meat with the creamy, spicy peanut sauce creates a delightful and satisfying taste experience.");
        satayDetail.setHistory("Satay is a popular dish in Southeast Asia, with origins believed to be in Indonesia. It was brought to Singapore by Malay and Indonesian immigrants. Over time, satay has become a beloved street food, often enjoyed at hawker centers and food markets. Its smoky, grilled flavor and delicious dipping sauce have made it a favorite among locals and tourists alike.");
        dishDetailService.saveDishDetail(satayDetail);

        Dish chickenRice = new Dish();
        chickenRice.setDishDetail(chickenRiceDetail);
        chickenRice.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_1.jpeg");

        Dish chilliCrab = new Dish();
        chilliCrab.setDishDetail(chilliCrabDetail);
        chilliCrab.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_2.jpeg");

        Dish cendol = new Dish();
        cendol.setDishDetail(cendolDetail);
        cendol.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_3.jpg");

        Dish pulutHitam = new Dish();
        pulutHitam.setDishDetail(pulutHitamDetail);
        pulutHitam.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_4.jpeg");

        Dish soonKueh = new Dish();
        soonKueh.setDishDetail(soonKuehDetail);
        soonKueh.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_5.jpeg");

        Dish teochewpngkueh = new Dish();
        teochewpngkueh.setDishDetail(pngKuehDetail);
        teochewpngkueh.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_6.jpeg");

        Dish curryPuff = new Dish();
        curryPuff.setDishDetail(curryPuffDetail);
        curryPuff.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_7.jpeg");

        Dish nasiLemak = new Dish();
        nasiLemak.setDishDetail(nasiLemakDetail);
        nasiLemak.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_8.jpeg");

        Dish rotiPrata = new Dish();
        rotiPrata.setDishDetail(rotiPrataDetail);
        rotiPrata.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_9.jpeg");

        Dish satay = new Dish();
        satay.setDishDetail(satayDetail);
        satay.setImageUrl("https://lioneats.blob.core.windows.net/dishimages/dish_image_10.jpeg");

        dishesService.createAllDishes(Arrays.asList(chickenRice, chilliCrab, cendol, pulutHitam, soonKueh,
                teochewpngkueh, curryPuff,
                nasiLemak, rotiPrata, satay));
    }



    public void createMRTS() {

        MRT downtownMRT = new MRT("Downtown", 1.27949, 103.852802, Arrays.asList("BLUE"));
        MRT telokAyerMRT = new MRT("Telok Ayer", 1.282285, 103.848584, Arrays.asList("BLUE"));
        MRT shentonWayMRT = new MRT("Shenton Way", 1.276385, 103.846771, Arrays.asList("BLUE"));
        MRT orchardMRT = new MRT("Orchard", 1.304041, 103.831792, Arrays.asList("RED"));
        MRT fortCanningMRT = new MRT("Fort Canning", 1.291631, 103.844621, Arrays.asList("BLUE"));
        MRT kallangMRT = new MRT("Kallang", 1.311532, 103.871372, Arrays.asList("GREEN"));
        MRT rochorMRT = new MRT("Rochor", 1.303601, 103.852581, Arrays.asList("BLUE"));
        MRT dhobyGhautMRT = new MRT("Dhoby Ghaut", 1.299169, 103.845799, Arrays.asList("RED", "YELLOW", "PURPLE"));
        MRT brasBasahMRT = new MRT("Bras Basah", 1.296978, 103.850715, Arrays.asList("YELLOW"));
        MRT marinaSouthPierMRT = new MRT("Marina South Pier", 1.271422, 103.863581, Arrays.asList("RED"));
        MRT esplanadeMRT = new MRT("Esplanade", 1.293995, 103.855396, Arrays.asList("YELLOW"));
        MRT chinatownMRT = new MRT("Chinatown", 1.284566, 103.843626, Arrays.asList("PURPLE"));
        MRT labradorParkMRT = new MRT("Labrador Park", 1.27218, 103.802557, Arrays.asList("YELLOW"));
        MRT tanjongPagarMRT = new MRT("Tanjong Pagar", 1.276385, 103.846771, Arrays.asList("GREEN"));
        MRT bugisMRT = new MRT("Bugis", 1.300747, 103.855873, Arrays.asList("BLUE", "GREEN"));
        MRT bencoolenMRT = new MRT("Bencoolen", 1.298477, 103.849984, Arrays.asList("YELLOW"));
        MRT rafflesPlaceMRT = new MRT("Raffles Place", 1.284001, 103.85155, Arrays.asList("RED", "GREEN"));
        MRT outramParkMRT = new MRT("Outram Park", 1.280319, 103.839459, Arrays.asList("YELLOW", "GREEN"));
        MRT cityHallMRT = new MRT("City Hall", 1.293119, 103.852089, Arrays.asList("RED", "GREEN"));
        MRT redhillMRT = new MRT("Redhill", 1.289674, 103.816787, Arrays.asList("GREEN"));
        MRT bendemeerMRT = new MRT("Bendemeer", 1.313674, 103.863098, Arrays.asList("BLUE"));
        MRT tiongBahruMRT = new MRT("Tiong Bahru", 1.286555, 103.826956, Arrays.asList("GREEN"));
        MRT bayfrontMRT = new MRT("Bayfront", 1.281371, 103.858998, Arrays.asList("BLUE"));
        MRT promenadeMRT = new MRT("Promenade", 1.294063, 103.860156, Arrays.asList("BLUE"));
        MRT lavenderMRT = new MRT("Lavender", 1.307577, 103.863155, Arrays.asList("GREEN"));
        MRT harbourfrontMRT = new MRT("HarbourFront", 1.265453, 103.820514, Arrays.asList("PURPLE", "YELLOW"));
        MRT queenstownMRT = new MRT("Queenstown", 1.294867, 103.805902, Arrays.asList("GREEN"));
        MRT clarkeQuayMRT = new MRT("Clarke Quay", 1.288949, 103.847521, Arrays.asList("PURPLE"));
        MRT greatWorldMRT = new MRT("Great World", 1.2895, 103.8356, Arrays.asList("YELLOW"));
        MRT geylangBahruMRT = new MRT("Geylang Bahru", 1.321479, 103.871457, Arrays.asList("BLUE"));
        MRT pasirPanjangMRT = new MRT("Pasir Panjang", 1.276111, 103.791893, Arrays.asList("YELLOW"));
        MRT kentRidgeMRT = new MRT("Kent Ridge", 1.293629, 103.784441, Arrays.asList("YELLOW"));
        MRT clementiMRT = new MRT("Clementi", 1.314925, 103.765341, Arrays.asList("GREEN"));
        MRT hollandVillageMRT = new MRT("Holland Village", 1.311189, 103.796119, Arrays.asList("YELLOW"));

        mrtService.saveAllMRTStations(Arrays.asList(
                downtownMRT, telokAyerMRT, shentonWayMRT, orchardMRT, fortCanningMRT,
                kallangMRT, rochorMRT, dhobyGhautMRT, brasBasahMRT, marinaSouthPierMRT,
                esplanadeMRT, chinatownMRT, labradorParkMRT, tanjongPagarMRT,
                bugisMRT, bencoolenMRT, rafflesPlaceMRT, outramParkMRT, cityHallMRT,
                redhillMRT, bendemeerMRT, tiongBahruMRT, bayfrontMRT, promenadeMRT,
                lavenderMRT, harbourfrontMRT, queenstownMRT, clarkeQuayMRT,
                greatWorldMRT, geylangBahruMRT, pasirPanjangMRT, kentRidgeMRT,
                clementiMRT, hollandVillageMRT
        ));
    }

    public void createCircles() {
        // Creating circles using the MRTService method
        Circle centralCircle = new Circle(mrtService.getMRTStationByName("Chinatown"), 1000); // Central Circle
        Circle northCircle = new Circle(mrtService.getMRTStationByName("Bendemeer"), 1000); // North Circle
        Circle northeastCircle = new Circle(mrtService.getMRTStationByName("Rochor"), 1000); // Northeast Circle
        Circle eastCircle = new Circle(mrtService.getMRTStationByName("Promenade"), 1000); // East Circle
        Circle southeastCircle = new Circle(mrtService.getMRTStationByName("Marina South Pier"), 1000); // Southeast Circle
        Circle southCircle = new Circle(mrtService.getMRTStationByName("HarbourFront"), 1000); // South Circle
        Circle southwestCircle = new Circle(mrtService.getMRTStationByName("Pasir Panjang"), 1000); // Southwest Circle
        Circle westCircle = new Circle(mrtService.getMRTStationByName("Queenstown"), 1000); // West Circle
        Circle northwestCircle = new Circle(mrtService.getMRTStationByName("Clementi"), 1000); // Northwest Circle
        Circle westnorthCircle = new Circle(mrtService.getMRTStationByName("Holland Village"), 1000); // Westnorth Circle

        circleService.saveAllCircles(Arrays.asList(
                centralCircle,
                northwestCircle,
                northCircle,
                northeastCircle,
                eastCircle,
                southeastCircle,
                southCircle,
                southwestCircle,
                westCircle,
                westnorthCircle));
    }

}
