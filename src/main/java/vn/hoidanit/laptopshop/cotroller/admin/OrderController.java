package vn.hoidanit.laptopshop.cotroller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrder(Model model, @PathVariable Long id) {
        model.addAttribute("order", orderService.findById(id));
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrder(Model model, @PathVariable Long id) {
        model.addAttribute("order", orderService.findById(id));
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("order")Order order) {
        orderService.handleUpdateOrder(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrder(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("order", new Order());
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String handleDeleteOrder(@ModelAttribute("order")Order order) {
        orderService.handleDeleteOrder(order.getId());
        return "redirect:/admin/order";
    }
}
