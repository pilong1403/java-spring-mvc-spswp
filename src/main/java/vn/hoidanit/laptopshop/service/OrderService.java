package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void handleDeleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (Objects.nonNull(order)) {
            orderDetailRepository.deleteAll(order.getOrderDetails());
            orderRepository.delete(order);
        }
    }

    public void handleUpdateOrder(Order order) {
        Order currentOrder = findById(order.getId());
        if (Objects.nonNull(currentOrder)) {
            currentOrder.setStatus(order.getStatus());
            orderRepository.save(currentOrder);
        }
    }

    public long count() {
        return orderRepository.count();
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
