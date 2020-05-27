package com.example.hookahshop_diploma.config;


import com.example.hookahshop_diploma.model.*;
import com.example.hookahshop_diploma.model.Character;
import com.example.hookahshop_diploma.role.Role;
import com.example.hookahshop_diploma.service.ShopService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:application.properties")
public class OnStartDataConfig extends GlobalMethodSecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner onStart(final ShopService service, final PasswordEncoder passwordEncoder){
        if (service.findProductById(1L) != null) return null;
        else return args -> {
            List<SubCatalog> subCatalogList = new ArrayList<>();
            List<ShopProduct> shopProducts = new ArrayList<>();


            // USER #1
            ShopUser user = new ShopUser(1L, "Петро","opanasenk000@ukr.net", passwordEncoder.encode("user"),
                    Role.USER, null,null,null, null);
            service.addUser(user);
            // CART FOR USER #1
            Cart cart = new Cart(1L, null);
            service.addCart(cart);
            service.addUser(user);
            service.addOrder(new ShopOrder(1L, null,null, null,
                    null,null,null, null,null, cart, user, null, null));

            // USER #2 ADMIN
            ShopUser user1 = new ShopUser(2L, "Адміністратор", "nedbail00098@gmail.com", passwordEncoder.encode("admin"),
                    Role.ADMIN, null, null, null, null);
            service.addUser(user);
            // CART FOR USER #2
            Cart cart1 = new Cart(2L, null);
            service.addCart(cart1);
            service.addUser(user1);
            service.addOrder(new ShopOrder(2L, null, null, null,
                    null, null, null, null, null, cart1, user1, null, null));


            // ADDRESS
            Address address = new Address(1L, "м.Київ, вулиця Героїв оборони, 17А");
            Address address2 = new Address(2L, "м.Київ, вулиця Пушкіна, 15");
            Address address3 = new Address(3L, "м.Київ, вулиця Степана Бендери, 4");
            service.addAddress(address);
            service.addAddress(address2);
            service.addAddress(address3);

            // SHOP DELIVERY
            ShopDelivery shopDelivery = new ShopDelivery(1L, "Нова пошта", null);
            service.addDelivery(shopDelivery);
            ShopDelivery shopDelivery2 = new ShopDelivery(2L, "Самовивоз",
                    Arrays.asList(address,address2,address3));
            service.addDelivery(shopDelivery2);

            // ORDER STATUS
            ShopOrderStatus shopOrderStatus = new ShopOrderStatus(1L, "Прийнято в замовлення", null);
            service.addShopOrderStatus(shopOrderStatus);
            ShopOrderStatus shopOrderStatus2 = new ShopOrderStatus(2L, "Комплектується", null);
            service.addOrderStatus(shopOrderStatus2);
            ShopOrderStatus shopOrderStatus3 = new ShopOrderStatus(3L, "Передано в службу доставки", null);
            service.addOrderStatus(shopOrderStatus3);
            ShopOrderStatus shopOrderStatus4 = new ShopOrderStatus(4L, "Доставлено", null);
            service.addOrderStatus(shopOrderStatus4);
            ShopOrderStatus shopOrderStatus5 = new ShopOrderStatus(5L, "Виконано", null);
            service.addOrderStatus(shopOrderStatus5);
            ShopOrderStatus shopOrderStatus6 = new ShopOrderStatus(6L, "Відмінено", null);
            service.addOrderStatus(shopOrderStatus6);



            /* ----------------------------------------Catalog #1----------------------------------------------------- */


            service.addCatalog(new Catalog(1L, "Кальяни",
                    "https://res.cloudinary.com/hxntglbil/image/upload/v1588586420/icons/calik-1_i2ntsk.jpg",
                    Collections.singletonList(new SubCatalog(1L, "Кальяни",
                            Arrays.asList(
                                    new ShopProduct(1L, "Yahya SY07", 300D,
                                            "Yahya - створює новий погляд на старе уявлення про кальянів.\n" +
                                            "\n" +
                                            "Витончені вигини кальянів американської компанії Yahya безсумнівно підійдуть до інтер'єру Вашого будинку або закладу.\n" +
                                            "Через якості використаних матеріалів, кальян зможе довго Вам прослужити, не втрачаючи свій первинний зовнішній вигляд.\n" +
                                            "\n" +
                                            "Yahya SY07 - якісний класичний кальян.\n" +
                                            "Шахта кальяну зроблена з анодованого алюмінію, цей матеріал легкий, кальян буде зручно переносити.\n" +
                                            "Діаметр шахти величиною в сімнадцяти міліметрів дає легку тягу і велику задимленість.\n" +
                                            "Великий плюс - комплектація кальяну Yahya SY07, в яку входять навіть щипці.", 0L,
                                            "https://res.cloudinary.com/hxntglbil/image/upload/v1588586421/product-icons/%D0%9A%D0%B0%D0%BB%D1%8C%D1%8F%D0%BD%D0%B8/kalik1_owbnhl.jpg",
                                            "recommended", null, null),
                                    new ShopProduct(2L, "Yahya Elegance 600", 2190D, "Кальян Yahya " +
                                            "Elegance 600 це новинка в світі кальянів! Відмінний вибір, якщо ви шукаєте" +
                                            "кальян додому, на подарунок, або до закладу. Шахта кальяну всередині " +
                                            "виконана з алюмінію, а зовні покрита хромованою сталлю, що робить кальян" +
                                            "несприйнятливим до іржі. Колба з подвійного скла. Кальян має повний комплект, " +
                                            "з з усіма необхідним ущільнювачами, чашею і силіконовим шлангом з алюмінієвим мундштуком." +
                                            "Упакований кальян Yahya Elegance 600 в фірмову коробку, що дозволяє піднести його як дорогий, " +
                                            "солідний подарунок.", 3L,
                                            "https://res.cloudinary.com/hxntglbil/image/upload/v1588590089/product-icons/%D0%9A%D0%B0%D0%BB%D1%8C%D1%8F%D0%BD%D0%B8/kalik2_j0krt6.jpg",
                                            "new", null, null),
                                    new ShopProduct(3L, "Yahya Elegance 580", 1639D, "Кальян Yahya Elegance 580 - " +
                                            "бюджетний апарат від компанії Yahya з вельми привабливим дизайном і відмінним " +
                                            "співвідношенням ціна-якість. За основу кальяну була взята шахта з нержавіючої" +
                                            " сталі, інші частини виготовлені з алюмінію. Завдяки такій конструкції девайс " +
                                            "має помірну вагу, що додає Yahya Elegance 580 зручність при роботі в закладах. " +
                                            "Також варто відзначити форму колби. Незважаючи на її розміри, вона дуже стійка." +
                                            " Це не раз врятує апарат від можливих падінь.Крім простоти і доступності кальяну," +
                                            " варто відзначити його комплектацію.", 12L,
                                            "https://res.cloudinary.com/hxntglbil/image/upload/v1589463525/product-icons/%D0%9A%D0%B0%D0%BB%D1%8C%D1%8F%D0%BD%D0%B8/Yahya_Elegance_580_black_eakiih.jpg",
                                            "new", null, null),
                                    new ShopProduct(4L, "AMY DELUXE S", 2000D, "AMY Deluxe - " +
                                            "кальяни преміум класу від німецького виробника, якому вдалося вивести виробництво " +
                                            "кальянів на принципово новий технологічний рівень. Легендарне німецьке якість дає " +
                                            "про себе знати відразу, як тільки кальян потрапляє до Вас в руки, все зроблено настільки" +
                                            " ідеально, що кальяни AMY заслужено можна назвати найкращими серед кальянів.", 5L,
                                            "https://res.cloudinary.com/hxntglbil/image/upload/v1589463528/product-icons/%D0%9A%D0%B0%D0%BB%D1%8C%D1%8F%D0%BD%D0%B8/AMY_DELUXE_ALU_ZULU_S_pbzyeq.jpg",
                                            "new", null, null))))));

            ShopProduct shopProduct = service.findProductById(1L);
            ShopProduct shopProduct2 = service.findProductById(2L);
            ShopProduct shopProduct3 = service.findProductById(3L);
            ShopProduct shopProduct4 = service.findProductById(4L);

            // CHARACTER_VALUE
            Character character = new Character(1L, "Колір");
            Value value = new Value(1L, "Синій");
            Value value2 = new Value(2L, "Чорний");

            Character character2 = new Character(2L, "Діаметр шахти");
            Value value3 = new Value(3L, "15мм");
            Value value4 = new Value(4L, "14мм");

            Character character3 = new Character(3L, "Країна");
            Value value5 = new Value(5L, "США");
            Value value6 = new Value(6L, "Німеччина");

            Character character4 = new Character(4L, "Тип");
            Value value7 = new Value(7L, "Клик");
            Value value8 = new Value(8L, "Різьба");

            Character character5 = new Character(5L, "Матеріал шахти");
            Value value9 = new Value(9L, "Алюміній");
            Value value10 = new Value(10L, "Нержавіюча сталь");

            CharacterValue characterValue1 = new CharacterValue(1L, character, value);
            CharacterValue characterValue2 = new CharacterValue(2L, character, value2);
            CharacterValue characterValue3 = new CharacterValue(3L, character2, value3);
            CharacterValue characterValue4 = new CharacterValue(4L, character2, value4);
            CharacterValue characterValue5 = new CharacterValue(5L, character3, value5);
            CharacterValue characterValue6 = new CharacterValue(6L, character3, value6);
            CharacterValue characterValue7 = new CharacterValue(7L, character4, value7);
            CharacterValue characterValue8 = new CharacterValue(8L, character4, value8);
            CharacterValue characterValue9 = new CharacterValue(9L, character5, value9);
            CharacterValue characterValue10 = new CharacterValue(10L, character5, value10);

            service.addCharacterValue(characterValue1);
            service.addCharacterValue(characterValue2);
            service.addCharacterValue(characterValue3);
            service.addCharacterValue(characterValue4);
            service.addCharacterValue(characterValue5);
            service.addCharacterValue(characterValue6);
            service.addCharacterValue(characterValue7);
            service.addCharacterValue(characterValue8);
            service.addCharacterValue(characterValue9);
            service.addCharacterValue(characterValue10);

            shopProduct.setCharacterValue(Arrays.asList(characterValue1,characterValue3, characterValue5, characterValue7, characterValue9));
            shopProduct2.setCharacterValue(Arrays.asList(characterValue2, characterValue4, characterValue6, characterValue8, characterValue10));
            shopProduct3.setCharacterValue(Arrays.asList(characterValue1,characterValue4, characterValue5, characterValue8, characterValue9));
            shopProduct4.setCharacterValue(Arrays.asList(characterValue2, characterValue3, characterValue6, characterValue7, characterValue10));

            // FEEDBACK
            ShopProductFeedback shopProductFeedback = new ShopProductFeedback(1L,
                    "Гарний кальян! Купив нещодавно, працює прекрасно", 3, null, shopProduct);
            shopProductFeedback.setUser(user);
            shopProduct.setShopProductFeedbacks(Arrays.asList(shopProductFeedback));

            service.addProduct(shopProduct);
            service.addProduct(shopProduct2);
            service.addProduct(shopProduct3);
            service.addProduct(shopProduct4);


            /* ----------------------------------------Catalog #2----------------------------------------------------- */


            subCatalogList.add(new SubCatalog(2L, "Колби",
                    Arrays.asList(
                            new ShopProduct(5L, "Yahya Luminescent", 549D, "Колба для кальяну Yahya Craft Luminescent. " +
                                    "Це незвичайної форми виріб має гарний візерунок, який надає аксесуару оригінальний вид. " +
                                    "Yahya Craft Luminescent. - приклад досконалості, який не поступається за якістю конкурентам " +
                                    "і не залишить Вас незадоволеним. Її дизайн - це поєднання сучасності і східних традицій, " +
                                    "оформлена красивими візерунками що під підсвічуванням створять феєричну атмосферу під час " +
                                    "куріння улюбленого кальяну і зробить незабутнє враження для ваших гостей. Також підходить " +
                                    "в якості подарунка для любителів кальяну, від якого просто будуть в захваті і в прямому " +
                                    "сенсі кричати від радості.", 3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589465489/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%9A%D0%BE%D0%BB%D0%B1%D0%B8/Kolba_Yahya_Craft_Luminescent_h8dc6k.jpg",
                                    "new", null, null),
                            new ShopProduct(6L, "Yahya Craft Chameleon", 495D, "Колба Yahya Craft Chameleon." +
                                    "Якщо ви курите кальян і любите ці приємні моменти, то Вам до нас! У нашому магазині " +
                                    "представлені кращі за якістю і дизайну комплектуючі. Пропонуємо Вашій увазі практичну і" +
                                    " дуже стильну колбу Yahya Craft Chameleon. Вона має міцне дно з товстого скла, а для кращої" +
                                    " тяги горло зроблено тонким і вузьким. Їй притаманні стійкість і масивні стінки, сумісність" +
                                    " з шахтою, міцність і компактні розміри. Вся конструкція продумана до дрібниць. " +
                                    "Вона забезпечує достатню кількість диму для куріння у великій компанії і має естетичний " +
                                    "характер через свого надзвичайно красивого дизайну.", 5L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589465490/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%9A%D0%BE%D0%BB%D0%B1%D0%B8/Kolba_Yahya_Craft_Chameleon_hteolr.jpg",
                                    "recommended", null, null),
                            new ShopProduct(7L, "Yahya Craft \n", 449D, "Колба для кальяну Yahya Craft. " +
                                    "Компанія Yahya тільки заходить на кальянний ринок України, але їй вже є чим Вас здивувати. " +
                                    "Хороша якість, широкий асортимент і кращі ціни. Колба Yahya Craft - один з кращих виробів" +
                                    " цього бренду. Ми пропонуємо Вам універсальність, незвичайний дизайн, оптимальну вартість," +
                                    " досконалу конструкцію, безпечні матеріали і високу якість. Крім усього цього Ви отримаєте" +
                                    " стильний і елегантний аксесуар, який буде створювати приємну атмосферу під час куріння і " +
                                    "радувати Ваші очі.", 3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589465490/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%9A%D0%BE%D0%BB%D0%B1%D0%B8/Kolba_Yahya_Craft_vlmrln.jpg",
                                    null, null, null))));
            subCatalogList.add(new SubCatalog(3L, "Чаші",
                    Arrays.asList(
                            new ShopProduct(8L, "GrynBowls Accent", 150D, "Grynbowls - це чаші для кальяну " +
                                    "ручної роботи. Однією з них є чаша GrynBowls Accent - відмінний, надійний і економний варіант." +
                                    " Ця деталь не дарма вважається універсальною. Вона відмінно підходить для використання всіляких " +
                                    "тютюнових сумішей, як мокрих, так і сухих, має дуже цікавий дизайн і форму. Відмінна чаша, " +
                                    "яка виконана з фаянсу і забезпечує рівномірний розподіл жару і хороший прогрів. Ви будете в захваті!",
                                    10L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589466346/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%A7%D0%B0%D1%88%D0%BA%D0%B8/Chasha_GrynBowls_Accent_gnh6gj.jpg",
                                    null, null, null),
                            new ShopProduct(9L, "Solaris Prometheus", 155D, "Чаша класичної форми має " +
                                    "оптимальний розмір для середньої забивання (15 грам). Чаша рівномірно просочується сиропом," +
                                    " що робить смак диму більш насиченим з кожним разом. У виробництві використовується глина. " +
                                    "Чаша не перегрівається і дозволяє зберегти смак довгий час. Поверхня чаші ідеально підходить " +
                                    "під калауд, він стикається з бортиками чаші щільно з усіх боків і не пропускає зайве повітря.",
                                    5L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589466346/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%A7%D0%B0%D1%88%D0%BA%D0%B8/Chasha_Solaris_Prometheus_tzb8sw.jpg",
                                    "recommended", null, null),
                            new ShopProduct(10L, "Solaris Deimos", 155D, "Чаші Solaris мають оптимальну пористість " +
                                    "і хорошу теплопровідність. Глянцевий, якісно відполірована поверхня чаші, яку не тільки приємно курити," +
                                    " але і тримати в руках. Внутрішня частина чаші оброблена глазур'ю, що дозволить тютюну просочуватися в " +
                                    "пори чаші і забивати їх сиропом. Така чаша буде служити значно довше і не буде текти.",
                                    3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589466346/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%A7%D0%B0%D1%88%D0%BA%D0%B8/Chasha_Solaris_Deimos_ns018p.jpg",
                                    null, null, null))));
            subCatalogList.add(new SubCatalog(4L, "Мундштуки",
                    Arrays.asList(
                            new ShopProduct(11L, "Yahya YP17", 400D, "Мундштук від виробника Yahya. " +
                                    "Виготовлений з нержавіючої сталі, що має антисептичні властивості, що ідеального для цього " +
                                    "аксесуара. Легка вага і унікальна форма забезпечує комфортне використання. Неймовірна тяга " +
                                    "підкорює з перших секунд.",
                                    5L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589467157/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%9C%D1%83%D0%BD%D0%B4%D1%88%D1%82%D1%83%D0%BA%D0%B8/Mundshtuk_Yahya_YP17_ms0qtw.jpg",
                                    null, null, null),
                            new ShopProduct(12L, "Yahya YP18", 400D, "Мундштук від виробника Yahya. " +
                                    "Виготовлений з нержавіючої сталі, що має антисептичні властивості, що ідеального для цього " +
                                    "аксесуара. Легка вага і унікальна форма забезпечує комфортне використання. Неймовірна тяга " +
                                    "підкорює з перших секунд.",
                                    5L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589467157/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%9C%D1%83%D0%BD%D0%B4%D1%88%D1%82%D1%83%D0%BA%D0%B8/Mundshtuk_Yahya_YP18_qjeyeb.jpg",
                                    null, null, null),
                            new ShopProduct(13L, "Yahya YP19", 400D, "Мундштук від виробника Yahya. " +
                                    "Виготовлений з нержавіючої сталі, що має антисептичні властивості, що ідеального для цього " +
                                    "аксесуара. Легка вага і унікальна форма забезпечує комфортне використання. Неймовірна тяга " +
                                    "підкорює з перших секунд.",
                                    3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589467157/product-icons/%D0%9A%D0%BE%D0%BC%D0%BF%D0%BB%D0%B5%D0%BA%D1%82%D1%83%D1%8E%D1%87%D1%96/%D0%9C%D1%83%D0%BD%D0%B4%D1%88%D1%82%D1%83%D0%BA%D0%B8/Mundshtuk_Yahya_YP19_vp42mg.jpg",
                                    null, null, null))));
            service.addCatalog(new Catalog(2L, "Комплектуючі",
                    "https://res.cloudinary.com/hxntglbil/image/upload/v1588586420/icons/calik-2_qcua2h.png", subCatalogList));

            ShopProduct shopProduct5 = service.findProductById(5L);
            ShopProduct shopProduct6 = service.findProductById(6L);
            ShopProduct shopProduct7 = service.findProductById(7L);
            ShopProduct shopProduct8 = service.findProductById(8L);
            ShopProduct shopProduct9 = service.findProductById(9L);
            ShopProduct shopProduct10 = service.findProductById(10L);
            ShopProduct shopProduct11 = service.findProductById(11L);
            ShopProduct shopProduct12= service.findProductById(12L);
            ShopProduct shopProduct13 = service.findProductById(13L);

            // CHARACTER_VALUE
            Character character6 = new Character(6L, "Висота колби");
            Value value11 = new Value(11L, "25.5 см");
            Value value12 = new Value(12L, "26 см");

            Character character7 = new Character(7L, "Матеріал колби");
            Value value13 = new Value(13L, "Скло");

            // Color
            Value value14 = new Value(14L, "Золотий");
            Value value15 = new Value(15L, "Прозорий");

            Character character8 = new Character(7L, "Матеріал мундштука");


            Character character9 = new Character(8L, "Тип чаші");
            Value value16 = new Value(16L, "Класика");
            Value value17 = new Value(17L, "Фанел");
            // Conutry
            Value value18 = new Value(18L, "Україна");
            // Висота колби - 25,5см
            CharacterValue characterValue11 = new CharacterValue(11L, character6, value11);
            // Висота колби - 26см
            CharacterValue characterValue12 = new CharacterValue(12L, character6, value12);
            // Матеріал колби - скло
            CharacterValue characterValue13 = new CharacterValue(13L, character7, value13);
            // Колір - Золотий
            CharacterValue characterValue14 = new CharacterValue(14L, character, value14);
            // Колір - Прозорий
            CharacterValue characterValue15 = new CharacterValue(15L, character, value15);
            // Матеріал мундштука - нержавіюча сталь
            CharacterValue characterValue16 = new CharacterValue(16L, character8, value10);
            // Тип чаші - Класика
            CharacterValue characterValue17 = new CharacterValue(17L, character9, value16);
            // Тип чаші - Фанел
            CharacterValue characterValue18 = new CharacterValue(18L, character9, value17);
            // Країна - Україна
            CharacterValue characterValue19 = new CharacterValue(19L, character3, value18);



            service.addCharacterValue(characterValue11);
            service.addCharacterValue(characterValue12);
            service.addCharacterValue(characterValue13);
            service.addCharacterValue(characterValue14);
            service.addCharacterValue(characterValue15);
            service.addCharacterValue(characterValue16);
            service.addCharacterValue(characterValue17);
            service.addCharacterValue(characterValue18);
            service.addCharacterValue(characterValue19);

            shopProduct5.setCharacterValue(Arrays.asList(characterValue11, characterValue13, characterValue5, characterValue2));
            shopProduct6.setCharacterValue(Arrays.asList(characterValue11, characterValue13, characterValue5, characterValue14));
            shopProduct7.setCharacterValue(Arrays.asList(characterValue12, characterValue13, characterValue5, characterValue15));

            shopProduct8.setCharacterValue(Arrays.asList(characterValue17, characterValue19));
            shopProduct9.setCharacterValue(Arrays.asList(characterValue18, characterValue19));
            shopProduct10.setCharacterValue(Arrays.asList(characterValue18, characterValue19));

            shopProduct11.setCharacterValue(Arrays.asList(characterValue16, characterValue5));
            shopProduct12.setCharacterValue(Arrays.asList(characterValue16, characterValue5));
            shopProduct13.setCharacterValue(Arrays.asList(characterValue16, characterValue5));

            service.addProduct(shopProduct5);
            service.addProduct(shopProduct6);
            service.addProduct(shopProduct7);
            service.addProduct(shopProduct8);
            service.addProduct(shopProduct9);
            service.addProduct(shopProduct10);
            service.addProduct(shopProduct11);
            service.addProduct(shopProduct12);
            service.addProduct(shopProduct13);


            /* ----------------------------------------Catalog #3----------------------------------------------------- */


            subCatalogList = new ArrayList<>();
            subCatalogList.add(new SubCatalog(5L, "Регулятор жару",
                    Arrays.asList(
                            new ShopProduct(14L, "Yahya Classic", 120D, "Калауд замінює фольгу " +
                                    "і полегшує весь процес приготування кальяну. Підходить для будь-якого виду чаш. " +
                                    "Складається з 2 частин. Верхівка кришки може обертатися, при цьому дає можливість" +
                                    " регулювати силу спека від вугілля. Захищає від попадання шматочків вугілля або іскор на стіл.",
                                    3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589469756/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%A0%D0%B5%D0%B3%D1%83%D0%BB%D1%8F%D1%82%D0%BE%D1%80%20%D0%B6%D0%B0%D1%80%D1%83/Regulator_Yahya_Classic_divpsv.jpg",
                                    null, null, null),
                            new ShopProduct(15L, "Kaloud Lotus II", 265D, "Калауд замінює фольгу " +
                                    "і полегшує весь процес приготування кальяну. Підходить для будь-якого виду чаш. " +
                                    "Складається з 2 частин. Верхівка кришки може обертатися, при цьому дає можливість" +
                                    " регулювати силу спека від вугілля. Захищає від попадання шматочків вугілля або іскор на стіл.",
                                    5L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589469756/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%A0%D0%B5%D0%B3%D1%83%D0%BB%D1%8F%D1%82%D0%BE%D1%80%20%D0%B6%D0%B0%D1%80%D1%83/Regulator_Kaloud_Lotus_II_osicdz.jpg",
                                    "recommended", null, null),
                            new ShopProduct(16L, "Yahya Dragon", 220D, "Калауд замінює фольгу " +
                                    "і полегшує весь процес приготування кальяну. Підходить для будь-якого виду чаш. " +
                                    "Складається з 2 частин. Верхівка кришки може обертатися, при цьому дає можливість" +
                                    " регулювати силу спека від вугілля. Захищає від попадання шматочків вугілля або іскор на стіл.",
                                    3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589469756/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%A0%D0%B5%D0%B3%D1%83%D0%BB%D1%8F%D1%82%D0%BE%D1%80%20%D0%B6%D0%B0%D1%80%D1%83/Regulator_Yahya_Dragon_onexrg.jpg",
                                    null, null, null))));
            subCatalogList.add(new SubCatalog(6L, "Кошик для вугілля",
                    Arrays.asList(
                            new ShopProduct(17L, "YAHYA Deep", 140D, "Це пристрій використовують" +
                                    " для комфортного розпалювання вугілля будинку, на дачній ділянці, в ресторані, кафе або клубі. " +
                                    "Іноді, кошик для вугілля називають підставкою для вугілля. Даний аксесуар для кальяну відмінно " +
                                    "підійде для кальян-барів, ресторанів. На кошику передбачено спеціальне вушко для фіксації щипців " +
                                    "кальянщика", 3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589470577/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%9A%D0%BE%D1%88%D0%B8%D0%BA%20%D0%B4%D0%BB%D1%8F%20%D1%83%D0%B3%D0%BB%D1%8F/Korzina_Yahya_Deep_wpxaru.jpg",
                                    null, null, null),
                            new ShopProduct(18L, "Yahya Frosted", 365D, "Це пристрій використовують" +
                                    " для комфортного розпалювання вугілля будинку, на дачній ділянці, в ресторані, кафе або клубі. " +
                                    "Іноді, кошик для вугілля називають підставкою для вугілля. Даний аксесуар для кальяну відмінно " +
                                    "підійде для кальян-барів, ресторанів. На кошику передбачено спеціальне вушко для фіксації щипців " +
                                    "кальянщика",
                                    5L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589470577/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%9A%D0%BE%D1%88%D0%B8%D0%BA%20%D0%B4%D0%BB%D1%8F%20%D1%83%D0%B3%D0%BB%D1%8F/Korzina_Yahya_Frosted_tycbvj.jpg",
                                    null, null, null),
                            new ShopProduct(19L, "Yahya Frosted Small", 300D, "Це пристрій використовують" +
                                    " для комфортного розпалювання вугілля будинку, на дачній ділянці, в ресторані, кафе або клубі. " +
                                    "Іноді, кошик для вугілля називають підставкою для вугілля. Даний аксесуар для кальяну відмінно " +
                                    "підійде для кальян-барів, ресторанів. На кошику передбачено спеціальне вушко для фіксації щипців " +
                                    "кальянщика",
                                    3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589470577/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%9A%D0%BE%D1%88%D0%B8%D0%BA%20%D0%B4%D0%BB%D1%8F%20%D1%83%D0%B3%D0%BB%D1%8F/Korzina_Yahya_Small_fuhytk.jpg",
                                    null, null, null))));
            subCatalogList.add(new SubCatalog(7L, "Розжиг",
                    Arrays.asList(
                            new ShopProduct(20L, "Yahya Horisontal", 340D, "Плитка дозволить дуже швидко" +
                                    " розпалити вугілля для кальяну і її розміри дозволяють розпалити вугілля ні на один кальян, " +
                                    "що прискорить Вашу роботу. Плитки високої якості, що забезпечить Вам їх довговічну роботу." +
                                    " Головне, що не залишайте плитку ввімкненою довгий час так, як це скоротить її служіння.",
                                    3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589470824/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%A0%D0%BE%D0%B7%D0%B6%D0%B8%D0%B3/Rozjig_Yahya_Gorizontal_uo7am2.jpg",
                                    null, null, null),
                            new ShopProduct(21L, "Elna One", 540D, "Плитка дозволить дуже швидко" +
                                    " розпалити вугілля для кальяну і її розміри дозволяють розпалити вугілля ні на один кальян, " +
                                    "що прискорить Вашу роботу. Плитки високої якості, що забезпечить Вам їх довговічну роботу." +
                                    " Головне, що не залишайте плитку ввімкненою довгий час так, як це скоротить її служіння.",
                                    5L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589470824/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%A0%D0%BE%D0%B7%D0%B6%D0%B8%D0%B3/Rozjig_Elna_One_jsvyxl.jpg",
                                    null, null, null),
                            new ShopProduct(22L, "Yahya 2.0v", 399D, "Плитка дозволить дуже швидко" +
                                    " розпалити вугілля для кальяну і її розміри дозволяють розпалити вугілля ні на один кальян, " +
                                    "що прискорить Вашу роботу. Плитки високої якості, що забезпечить Вам їх довговічну роботу." +
                                    " Головне, що не залишайте плитку ввімкненою довгий час так, як це скоротить її служіння.",
                                    3L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589470824/product-icons/%D0%90%D0%BA%D1%81%D0%B5%D1%81%D1%83%D0%B0%D1%80%D0%B8/%D0%A0%D0%BE%D0%B7%D0%B6%D0%B8%D0%B3/Rozjig_Yahya_2.0v_yyvtlc.jpg",
                                    null, null, null))));
            service.addCatalog(new Catalog(3L, "Аксесуари",
                    "https://res.cloudinary.com/hxntglbil/image/upload/v1588586420/icons/calik-3_a9gx1v.png", subCatalogList));

            ShopProduct shopProduct14 = service.findProductById(14L);
            ShopProduct shopProduct15 = service.findProductById(15L);
            ShopProduct shopProduct16 = service.findProductById(16L);
            ShopProduct shopProduct17 = service.findProductById(17L);
            ShopProduct shopProduct18 = service.findProductById(18L);
            ShopProduct shopProduct19 = service.findProductById(19L);
            ShopProduct shopProduct20 = service.findProductById(20L);
            ShopProduct shopProduct21 = service.findProductById(21L);
            ShopProduct shopProduct22 = service.findProductById(22L);

            Character character10 = new Character(9L, "Матеріал");
            Value value19 = new Value(19L, "Срібний");

            // Матеріал - Алюміній
            CharacterValue characterValue20 = new CharacterValue(20L, character10, value9);
            // Колір - срібний
            CharacterValue characterValue21 = new CharacterValue(21L, character, value19);
            // Матеріал - Нержавіюча сталь
            CharacterValue characterValue22 = new CharacterValue(22L, character10, value10);

            service.addCharacterValue(characterValue20);
            service.addCharacterValue(characterValue21);
            service.addCharacterValue(characterValue22);

            shopProduct14.setCharacterValue(Arrays.asList(characterValue5, characterValue20, characterValue21));
            shopProduct15.setCharacterValue(Arrays.asList(characterValue5, characterValue20, characterValue21));
            shopProduct16.setCharacterValue(Arrays.asList(characterValue5, characterValue20, characterValue21));

            shopProduct17.setCharacterValue(Arrays.asList(characterValue5));
            shopProduct18.setCharacterValue(Arrays.asList(characterValue22, characterValue5, characterValue2));
            shopProduct19.setCharacterValue(Arrays.asList(characterValue22, characterValue5, characterValue2));

            shopProduct20.setCharacterValue(Arrays.asList(characterValue5));
            shopProduct21.setCharacterValue(Arrays.asList(characterValue5));
            shopProduct22.setCharacterValue(Arrays.asList(characterValue5, characterValue2));

            service.addProduct(shopProduct14);
            service.addProduct(shopProduct15);
            service.addProduct(shopProduct16);
            service.addProduct(shopProduct17);
            service.addProduct(shopProduct18);
            service.addProduct(shopProduct19);
            service.addProduct(shopProduct20);
            service.addProduct(shopProduct21);
            service.addProduct(shopProduct22);

            /* ----------------------------------------Catalog #4----------------------------------------------------- */


            subCatalogList = new ArrayList<>();
            subCatalogList.add(new SubCatalog(8L, "Вугілля  ",
                    Arrays.asList(
                            new ShopProduct(23L, "Coco Yahya mini", 9D, "Високоякісне кокосове " +
                                    "вугілля для кальяну, створене з шкаралупи кокосового горіха, яке забезпечить смачне і димне" +
                                    " куріння кальяну без сторонніх запахів і зміни смаку.",
                                    500L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589472395/product-icons/%D0%9C%D0%B0%D1%82%D0%B5%D1%80%D1%96%D0%B0%D0%BB%D0%B8/%D0%A3%D0%B3%D0%BE%D0%BB%D1%8C/Ugol_Coco_Yahya_mini_gxoo2o.jpg",
                                    null, null, null),
                            new ShopProduct(24L, "CocoLux 1kg", 93D, "Високоякісне кокосове " +
                                    "вугілля для кальяну, створене з шкаралупи кокосового горіха, яке забезпечить смачне і димне" +
                                    " куріння кальяну без сторонніх запахів і зміни смаку.",
                                    30L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589472395/product-icons/%D0%9C%D0%B0%D1%82%D0%B5%D1%80%D1%96%D0%B0%D0%BB%D0%B8/%D0%A3%D0%B3%D0%BE%D0%BB%D1%8C/Ugol_CocoLux_1kg_fn2aqe.jpg",
                                    null, null, null),
                            new ShopProduct(25L, "Yahya MTD", 19D, "Високоякісне кокосове " +
                                    "вугілля для кальяну, створене з шкаралупи кокосового горіха, яке забезпечить смачне і димне" +
                                    " куріння кальяну без сторонніх запахів і зміни смаку.",
                                    400L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589472395/product-icons/%D0%9C%D0%B0%D1%82%D0%B5%D1%80%D1%96%D0%B0%D0%BB%D0%B8/%D0%A3%D0%B3%D0%BE%D0%BB%D1%8C/Ugol_Yahya_MTD_wytotv.jpg",
                                    null, null, null))));
            subCatalogList.add(new SubCatalog(9L, "Одноразові мунштуки",
                    Arrays.asList(
                            new ShopProduct(26L, "Мундштук конусний 50шт", 40D, "Одноразові мундштуки з пластику, " +
                                    "призначені для використання з кальянами в масових місцях куріння: бари, кальянні, тощо",
                                    10L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589473002/product-icons/%D0%9C%D0%B0%D1%82%D0%B5%D1%80%D1%96%D0%B0%D0%BB%D0%B8/%D0%9E%D0%B4%D0%BD%D0%BE%D1%80%D0%B0%D0%B7%D0%BE%D0%B2%D1%96%20%D0%BC%D1%83%D0%BD%D0%B4%D1%88%D1%82%D1%83%D0%BA%D0%B8/OneUseMundshtuk_Konus_vjux4v.jpg",
                                    null, null, null),
                            new ShopProduct(27L, "Мундштук конусний XXL 50шт ", 60D, "Одноразові мундштуки з пластику, " +
                                    "призначені для використання з кальянами в масових місцях куріння: бари, кальянні, тощо",
                                    30L, "https://res.cloudinary.com/hxntglbil/image/upload/v1589473002/product-icons/%D0%9C%D0%B0%D1%82%D0%B5%D1%80%D1%96%D0%B0%D0%BB%D0%B8/%D0%9E%D0%B4%D0%BD%D0%BE%D1%80%D0%B0%D0%B7%D0%BE%D0%B2%D1%96%20%D0%BC%D1%83%D0%BD%D0%B4%D1%88%D1%82%D1%83%D0%BA%D0%B8/OneUseMundshtuk_XXL_itboxy.jpg",
                                    null, null, null))));
            service.addCatalog(new Catalog(4L, "Матеріали",
                    "https://res.cloudinary.com/hxntglbil/image/upload/v1588586420/icons/calik-4_qktjff.png", subCatalogList));

            ShopProduct shopProduct23 = service.findProductById(23L);
            ShopProduct shopProduct24 = service.findProductById(24L);
            ShopProduct shopProduct25 = service.findProductById(25L);

            shopProduct23.setCharacterValue(Arrays.asList(characterValue5));
            shopProduct24.setCharacterValue(Arrays.asList(characterValue5));
            shopProduct25.setCharacterValue(Arrays.asList(characterValue5));

            service.addProduct(shopProduct23);
            service.addProduct(shopProduct24);
            service.addProduct(shopProduct25);
        };
    }
}
