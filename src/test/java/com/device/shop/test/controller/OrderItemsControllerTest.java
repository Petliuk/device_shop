package com.device.shop.test.controller;

import static org.mockito.ArgumentMatchers.any;

public class OrderItemsControllerTest {

    //toDo Change all tests to work correctly

/*    private MockMvc mockMvc;

    @Mock
    private OrderItemsImpl orderItemsService;

    @InjectMocks
    private OrderItemsController orderItemsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderItemsController).build();
    }

    @Test
    void testGetOrderItemsId() throws Exception {
        Long orderItemsId = 1L;
        OrderItems orderItems = OrderItems.builder().id(orderItemsId).order_id(123L).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        when(orderItemsService.getOrderItemsById(orderItemsId)).thenReturn(orderItems);

        mockMvc.perform(get("/order/{id}/items", orderItemsId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(orderItemsId));
    }

    @Test
    public void testAddOrderItemsById() throws Exception {
        Long productId = 1L;
        OrderItems orderItems = new OrderItems();
        when(orderItemsService.addOrderItems(productId)).thenReturn(orderItems);


        mockMvc.perform(post("/order/{id}/items", productId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(orderItems.getId()));
    }

    @Test
    public void testUpdateOrderItems() throws Exception {
        Long orderItemsId = 1L;
        OrderItems orderItems = new OrderItems();
        when(orderItemsService.updateOrderItemsById(any(OrderItems.class), any(Long.class))).thenReturn(orderItems);

        mockMvc.perform(post("/order/items/{id}", orderItemsId).content("{}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(orderItems.getId()));
    }

    @Test
    public void testDeleteOrderItems() throws Exception {
        Long orderItemsId = 1L;

        mockMvc.perform(delete("/order/{id}/items", orderItemsId)).andExpect(status().isOk()).andExpect(jsonPath("$").value("Order Items successfully deleted!"));
    }*/

}