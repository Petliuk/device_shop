package com.device.shop.service.impl;

import com.device.shop.entity.OrderInAdmin;
import com.device.shop.mapper.OrderInAdminMapper;
import com.device.shop.model.OrderDTO;
import com.device.shop.model.ProductDTO;
import com.device.shop.model.UserDTO;
import com.device.shop.repository.OrderInAdminRepository;
import com.device.shop.service.ProductService;
import com.device.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderInAdminService {
    private final OrderInAdminRepository orderInAdminRepository;
    private final OrderInAdminMapper orderInAdminMapper;
    private final UserService userService;
    private final ProductService productService;

    public List<OrderDTO> getAllOrdersWithDetails() {
        List<OrderInAdmin> orderInAdminList = orderInAdminRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();


        for (OrderInAdmin orderInAdmin : orderInAdminList) {
            OrderDTO orderDTO = orderInAdminMapper.toDTO(orderInAdmin);

            UserDTO userDTO = userService.getUserById(orderInAdmin.getUserId());
            ProductDTO productDTO = productService.getProductById(orderInAdmin.getProductId());

            OrderDTO orderWithDetails = OrderDTO.builder()
                    .id(orderDTO.getId())
                    .userId(orderDTO.getUserId())
                    .productId(orderDTO.getProductId())
                    .address(orderDTO.getAddress())
                    .user(userDTO)
                    .product(productDTO)
                    .build();

            orderDTOList.add(orderWithDetails);
        }

        return orderDTOList;
    }



    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderInAdmin orderInAdmin = orderInAdminMapper.toEntity(orderDTO);
        OrderInAdmin savedOrder = orderInAdminRepository.save(orderInAdmin);
        return orderInAdminMapper.toDTO(savedOrder);
    }

    public void deleteOrderById(Long orderId) {
        orderInAdminRepository.deleteById(orderId);
    }


}