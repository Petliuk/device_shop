package com.device.shop.test.service;

public class OrderItemServiceTest {

/* @Mock
    private OrderItemsRepository orderItemsRepository;

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OrderItemsImpl orderItemsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrderItemsById() {
        Long orderItemsId = 1L;
        OrderItems orderItems = new OrderItems();
        when(orderItemsRepository.findById(orderItemsId)).thenReturn(Optional.of(orderItems));

        OrderItems result = orderItemsService.getOrderItemsById(orderItemsId);

        assertEquals(orderItems, result);
        verify(orderItemsRepository, times(1)).findById(orderItemsId);
    }*/
 /*
    @Test
    public void testGetOrderItemsById_EntityNotFoundException() {
        Long orderItemsId = 1L;
        when(orderItemsRepository.findById(orderItemsId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderItemsService.getOrderItemsById(orderItemsId));
        verify(orderItemsRepository, times(1)).findById(orderItemsId);
    }

    @Test
    public void testAddOrderItems() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        OrderItems result = orderItemsService.addOrderItems(productId);

        assertNotNull(result);
        assertEquals(product, result.getProduct());
        verify(orderItemsRepository, times(1)).save(result);
    }

    @Test
    public void testAddOrderItems_EntityNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderItemsService.addOrderItems(productId));
        verify(orderItemsRepository, never()).save(any(OrderItems.class));
    }

    @Test
    public void testUpdateOrderItemsById() throws BadRequestException {
        Long orderItemsId = 1L;
        OrderItems orderItems = new OrderItems();
        orderItems.setId(orderItemsId);
        when(orderItemsRepository.existsById(orderItemsId)).thenReturn(true);
        when(orderItemsRepository.save(orderItems)).thenReturn(orderItems);

        OrderItems result = orderItemsService.updateOrderItemsById(orderItems, orderItemsId);

        assertEquals(orderItems, result);
        verify(orderItemsRepository, times(1)).save(orderItems);
    }

    @Test
    public void testUpdateOrderItemsById_EntityNotFoundException() {
        Long orderItemsId = 1L;
        OrderItems orderItems = new OrderItems();
        orderItems.setId(orderItemsId);
        when(orderItemsRepository.existsById(orderItemsId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderItemsService.updateOrderItemsById(orderItems, orderItemsId));
        verify(orderItemsRepository, never()).save(any(OrderItems.class));
    }

    @Test
    public void testDeleteOrderItemsById() {
        Long orderItemsId = 1L;
        when(orderItemsRepository.existsById(orderItemsId)).thenReturn(true);

        assertDoesNotThrow(() -> orderItemsService.deleteOrderItemsById(orderItemsId));
        verify(orderItemsRepository, times(1)).deleteById(orderItemsId);
    }

    @Test
    public void testDeleteOrderItemsById_EntityNotFoundException() {
        Long orderItemsId = 1L;
        when(orderItemsRepository.existsById(orderItemsId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderItemsService.deleteOrderItemsById(orderItemsId));
        verify(orderItemsRepository, never()).deleteById(orderItemsId);
    }*/

}