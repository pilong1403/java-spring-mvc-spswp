package vn.hoidanit.laptopshop.cotroller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {
    private final ProductService productService;
    private final OrderService orderService;

    public ItemController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetail(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        productService.handleAddProductToCart(session, id, 1);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = new User();
        user.setId((Long) session.getAttribute("id"));

        Cart cart = productService.fetchByUser(user);
        List<CartDetail> cartDetails = Optional.ofNullable(cart).map(Cart::getCartDetails).orElse(new ArrayList<>());
        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        productService.handleRemoveCartDetail(session, id);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = new User();
        user.setId((Long) session.getAttribute("id"));

        Cart cart = productService.fetchByUser(user);
        List<CartDetail> cartDetails = Optional.ofNullable(cart).map(Cart::getCartDetails).orElse(new ArrayList<>());
        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String confirmCheckout(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = Optional.ofNullable(cart).map(Cart::getCartDetails).orElse(new ArrayList<>());
        productService.handleUpdateCartBeforeCheckOut(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(HttpServletRequest request, @RequestParam("receiverName") String receiverName,
                             @RequestParam("receiverAddress") String receiverAddress, @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession(false);
        User user = new User();
        user.setId((Long) session.getAttribute("id"));
        productService.handlePlaceOrder(user, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThanks() {
        return "client/cart/thanks";
    }

    @GetMapping("/order-history")
    public String getOrderHistory(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = new User();
        user.setId((Long) session.getAttribute("id"));
        model.addAttribute("orders", orderService.findByUser(user));
        return "client/cart/order-history";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(@RequestParam("id") long id, @RequestParam("quantity") long quantity,
                                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        productService.handleAddProductToCart(session, id, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/products")
    public String getProducts(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid page number");
        }
        PageRequest pageRequest = PageRequest.of(page-1, 6);
        Page<Product> products = productService.findAll(pageRequest);
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "client/product/show";
    }
}
