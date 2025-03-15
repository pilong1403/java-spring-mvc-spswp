package vn.hoidanit.laptopshop.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.domain.*;
import vn.hoidanit.laptopshop.repository.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserRepository userRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void handleAddProductToCart(HttpSession session, Long id, long quantity) {
        User user = userRepository.findByEmail(session.getAttribute("email").toString());
        if (Objects.isNull(user)) {
            System.out.println("User not found");
        }
        //Create new cart
        Cart cart = cartRepository.findByUser(user);
        if (Objects.isNull(cart)) {
            cart = new Cart();
            cart.setUser(user);
            cart.setSum(0);
            cart = cartRepository.save(cart);
        }
        //Save cart detail
        Product product = findById(id);
        CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, product);
        if (Objects.isNull(cartDetail)) {
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(quantity);
            cartDetail.setPrice(product.getPrice());

            //Update sum cart
            cart.setSum(cart.getSum() + 1);
            cartRepository.save(cart);
            session.setAttribute("sum", cart.getSum());
        } else {
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        }
        cartDetailRepository.save(cartDetail);
    }

    public Cart fetchByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(HttpSession session, Long id) {
        CartDetail cartDetail = cartDetailRepository.findById(id).orElse(null);
        if (Objects.isNull(cartDetail)) {
            System.out.println("CartDetail not found");
            return;
        }
        Cart cart = cartDetail.getCart();
        cart.setSum(cart.getSum() - 1);
        cartDetailRepository.delete(cartDetail);
        if (cart.getSum() > 0) {
            cartRepository.save(cart);
        } else {
            cartRepository.delete(cart);
        }
        session.setAttribute("sum", cart.getSum());
    }

    public void handleUpdateCartBeforeCheckOut(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            CartDetail currentCartDetail = cartDetailRepository.findById(cartDetail.getId()).orElse(null);
            if (Objects.nonNull(currentCartDetail)) {
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress, String receiverPhone) {
        Cart cart = cartRepository.findByUser(user);
        if (Objects.nonNull(cart)) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (Objects.nonNull(cartDetails)) {
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double totalPrice = 0;
                for (CartDetail cartDetail : cartDetails) {
                    totalPrice += cartDetail.getPrice();
                }
                order.setTotalPrice(totalPrice);
                orderRepository.save(order);

                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    orderDetail.setPrice(cartDetail.getPrice());
                    orderDetailRepository.save(orderDetail);
                }
                cartDetailRepository.deleteAll(cartDetails);
                cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }
        }
    }

    public long count() {
        return productRepository.count();
    }
}
