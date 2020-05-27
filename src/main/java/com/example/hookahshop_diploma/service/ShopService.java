package com.example.hookahshop_diploma.service;

import com.example.hookahshop_diploma.model.*;
import com.example.hookahshop_diploma.model.Character;
import com.example.hookahshop_diploma.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private CatalogDAO catalogDAO;
    @Autowired
    private SubCatalogDAO subCatalogDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ShopUserDAO shopUserDAO;
    @Autowired
    private ShopValueDAO shopValueDAO;
    @Autowired
    private ShopCharacterDAO shopCharacterDAO;
    @Autowired
    private CharacterValueDAO characterValueDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ShopOrderStatusDAO orderStatusDAO;
    @Autowired
    private ShopDeliveryDAO shopDeliveryDAO;
    @Autowired
    private SelectedItemDAO selectedItemDAO;
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private AddressDAO addressDAO;
    @Autowired
    private ShopProductFeedbackDAO shopProductFeedbackDAO;

    // Custom add
    @Transactional
    public void addCatalog(Catalog catalog){
        catalogDAO.save(catalog);
    }
    @Transactional
    public void addSubCatalog(SubCatalog subCatalog) { subCatalogDAO.save(subCatalog);}
    @Transactional
    public void addProduct(ShopProduct shopProduct) { productDAO.save(shopProduct);}
    @Transactional
    public void addUser(ShopUser user) { shopUserDAO.save(user);}
    @Transactional
    public void addValue(Value value) { shopValueDAO.save(value);}
    @Transactional
    public void addCharacter(Character character) { shopCharacterDAO.save(character);}
    @Transactional
    public void addCharacterValue(CharacterValue characterValue) { characterValueDAO.save(characterValue);}
    @Transactional
    public void addOrder(ShopOrder shopOrder) { orderDAO.save(shopOrder);}
    @Transactional
    public void addOrderStatus(ShopOrderStatus shopOrderStatus) { orderStatusDAO.save(shopOrderStatus);}
    @Transactional
    public void addDelivery(ShopDelivery shopDelivery) { shopDeliveryDAO.save(shopDelivery);}
    @Transactional
    public void addSelectedItem(SelectedItems selectedItems) { selectedItemDAO.save(selectedItems);}
    @Transactional
    public void addCart(Cart cart){cartDAO.save(cart);}
    @Transactional
    public void addShopOrderStatus(ShopOrderStatus status){ orderStatusDAO.save(status);}
    @Transactional
    public void addAddress(Address address){ addressDAO.save(address);}

    // Custom find all
    @Transactional(readOnly = true)
    public List<Catalog> findAllCatalog(){ return catalogDAO.findAll();}
    @Transactional(readOnly = true)
    public List<SubCatalog> findAllSubCatalog() { return subCatalogDAO.findAll();}
    @Transactional(readOnly = true)
    public List<ShopProduct> findAllProduct(){ return productDAO.findAll();}
    @Transactional(readOnly = true)
    public List<CharacterValue> findAllCharacterValue() { return characterValueDAO.findAll();}
    @Transactional(readOnly = true)
    public List<ShopUser> findAllChopUser() { return shopUserDAO.findAll();}
    @Transactional(readOnly = true)
    public List<ShopOrder> findAllOrder(){ return orderDAO.findAll();}
    @Transactional(readOnly = true)
    public List<ShopOrderStatus> findAllStatus(){ return orderStatusDAO.findAll();}
    @Transactional(readOnly = true)
    public List<Character> findAllCharacter(){ return shopCharacterDAO.findAll();}
    // Custom select-where SHOP_PRODUCT
    @Transactional(readOnly = true)
    public List<ShopProduct> findAllProductByType(String type) { return productDAO.findAllByType(type);}
    @Transactional(readOnly = true)
    public ShopProduct findProductById(Long id) { return productDAO.findById(id).orElse(null);}

    // Custom select-where CATALOG
    @Transactional(readOnly = true)
    public SubCatalog findSubCatalogByShopProduct(ShopProduct shopProduct){ return subCatalogDAO.findSubCatalogByShopProduct(shopProduct); }
    @Transactional(readOnly = true)
    public SubCatalog findSubCatalogByName(String name) { return subCatalogDAO.findSubCatalogBySubname(name);}

    // Custom select-where SHOP_USER
    @Transactional(readOnly = true)
    public ShopUser findUserByEmail(String email){ return shopUserDAO.findShopUserByEmail(email); }

    // Custom select-where ORDER
    @Transactional(readOnly = true)
    public List<ShopOrder> findAllByUserAndStatusIsNotNull(ShopUser user) {
        return orderDAO.findAllByUserAndStatusIsNotNull(user);
    }
    public ShopOrder findShopOrderByUserAndStatusIsNull(ShopUser shopUser){
        return orderDAO.findShopOrderByUserAndStatusIsNull(shopUser);
    }
    public List<ShopOrder> findShopOrderByStatusIsNotNull(){
        return orderDAO.findAllByStatusIsNotNull();
    }
    public ShopOrder findOrderById(Long id){
        return orderDAO.findShopOrderByIdAndStatusIsNotNull(id);
    }
    // Custom select-where SELECTED_ITEMS
    public SelectedItems findSelectedItemByShopProductAndCart(ShopProduct shopProduct, Cart cart){
        return selectedItemDAO.findSelectedItemsByShopProductAndCart(shopProduct,cart);
    }

    // Custom select-where SHOP_DELIVERY
    public ShopDelivery findByDeliveryName(String name){
        return shopDeliveryDAO.findByDeliveryName(name);
    }
    public ShopDelivery findByAddress(Address address){ return shopDeliveryDAO.findByAddress(address);}
    // Custom select-where SHOP_ORDER_STATUS
    public ShopOrderStatus findByStatusName(String name){
        return orderStatusDAO.findByStatusName(name);
    }
    public ShopOrderStatus findByStatusById(Long id) { return orderStatusDAO.findById(id).orElse(null); }
    // Custom select-where ADDRESS
    public Address findByAddress(String address){ return addressDAO.findByAddress(address);}

    // Custom select-where VALUE
    public Value findValueById(Long id) { return shopValueDAO.findById(id).orElse(null);}
    public Value findValueByName(String name) { return shopValueDAO.findValueByName(name);}
    // Custom select-where CHARACTER
    public Character findCharacterById(Long id) { return shopCharacterDAO.findById(id).orElse(null);}
    public Character findCharacterByName(String name) { return shopCharacterDAO.findCharacterByName(name);}

    // Custom select-where CHARACTER_VALUE
    public List<CharacterValue> findAllByCharacter(Character character){
        return characterValueDAO.findAllByCharacter(character);
    }
    public CharacterValue findCharacterValueByCharacterAndValue(Character character, Value value){
        return characterValueDAO.findCharacterValueByCharacterAndValue(character, value);
    }
    // DELETE FEEDBACK
    public void deleteFeedbackById(Long id){
        shopProductFeedbackDAO.deleteById(id);
    }
}
