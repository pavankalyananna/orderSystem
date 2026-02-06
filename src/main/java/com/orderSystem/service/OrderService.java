package com.orderSystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.orderSystem.dto.OrderRequest;
import com.orderSystem.entity.Order;
import com.orderSystem.entity.OrderItem;
import com.orderSystem.entity.Product;
import com.orderSystem.entity.User;
import com.orderSystem.exception.BusinessException;
import com.orderSystem.repository.OrderRepository;
import com.orderSystem.repository.ProductRepository;
import com.orderSystem.repository.UserRepository;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public OrderService(UserRepository userRepo,
                        ProductRepository productRepo,
                        OrderRepository orderRepo) {

        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @Retryable(
    		  value = OptimisticLockException.class,
    		  maxAttempts = 3
    		)
    @Transactional
    public Order placeOrder(OrderRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();

        double total = 0;

        for (OrderRequest.Item itemReq : request.getItems()) {

            Product product = productRepo.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Out of stock");
            }

            product.setQuantity(
                product.getQuantity() - itemReq.getQuantity()
            );

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());

            items.add(item);

            total += product.getPrice() * itemReq.getQuantity();
        }

        order.setItems(items);
        order.setTotalAmount(total);

        return orderRepo.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepo.findByUserId(userId);
    }
    @Recover
    public Order recover(OptimisticLockException ex,
                         OrderRequest request) {

        throw new BusinessException(
            "High traffic. Please try again."
        );
    }

}

