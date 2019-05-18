package pl.codementors.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.codementors.finalProject.models.Cart;
import pl.codementors.finalProject.models.Product;
import pl.codementors.finalProject.repo.CartRepository;
import pl.codementors.finalProject.repo.ProductRepository;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/cart/")
public class CartRestController {

    //TODO move to service
    private Cart cart = new Cart();
    private Product product = new Product();

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "list")
    public List<Cart> getCart(){
        return cartRepository.findAll();
    }

    @PostMapping(value = "add")
    public Cart createCart(){
        Cart newCart = new Cart();
        cartRepository.save(newCart);
        return newCart;
    }

    @DeleteMapping(value = "delete/{cartId}")
    public Cart deleteCart(@PathVariable("cartId") Long cartId){
        cart = cartRepository.findOne(cartId);
        cartRepository.delete(cart);
        return cart;
    }

    @PutMapping(value = "{cartId}/deleteProduct/{productId}")
    public Cart deleteFromCart(@PathVariable("cartId") Long cartId,
                               @PathVariable("productId") Long productId) {
        cart = cartRepository.findOne(cartId);
        product = productRepository.findOne(productId);
        cart.removeProduct(product);
        product.setCart(null);
        cartRepository.save(cart);
        return cart;
    }

    @PutMapping(value = "{cartId}/addProduct/{productId}")
    public Cart addProduct(@PathVariable("cartId") Long cartId,
                           @PathVariable("productId") Long productId){
        cart = cartRepository.findOne(cartId);
        product = productRepository.findOne(productId);
        cart.addProduct(product);
        product.setCart(cart);
        cartRepository.save(cart);
        return cart;
    }
}