package com.device.shop.test.service;

/*import static org.mockito.ArgumentMatchers.any;*/

public class CartItemServiceTest {

    //toDo Change all tests to work correctly

/*    private CartItemImpl cartItemService;

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductImpl productService;
    @Mock
    private ShoppingSessionRepository shoppingSessionRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cartItemService = new CartItemImpl(cartItemRepository, null, shoppingSessionRepository);
    }

    // Дописати тест addToCart

    @Test
    void testGetProductsInCart() {
        Long cartId = 1L;

        CartItem cartItem1 = CartItem.builder()
                .product(new Product())
                .build();

        CartItem cartItem2 = CartItem.builder()
                .product(new Product())
                .build();

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        when(cartItemRepository.findAllByShoppingSessionId(cartId)).thenReturn(cartItems);

        List<ProductDTO> result = cartItemService.getProductsInCart(cartId);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(cartItemRepository, times(1)).findAllByShoppingSessionId(cartId);
    }

    @Test
    void testDeleteTheProduct() {
        Long cartItemId = 1L;

        when(cartItemRepository.existsById(cartItemId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> cartItemService.deleteTheProduct(cartItemId));

        verify(cartItemRepository, times(1)).existsById(cartItemId);
        verify(cartItemRepository, never()).deleteById(cartItemId);
    }

    @Test
    void deleteTheProduct_ProductDoesNotExist_EntityNotFoundExceptionThrown() {

        Long cartId = 1L;
        when(cartItemRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> cartItemService.deleteTheProduct(cartId));
        verify(cartItemRepository, never()).deleteById(anyLong());
    }

    @Test
    void removeAllProductsFromCart_CartItemsDeleted() {

        Long cartId = 1L;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        when(cartItemRepository.findAllByShoppingSessionId(anyLong())).thenReturn(cartItems);

        cartItemService.removeAllProductsFromCart(cartId);

        verify(cartItemRepository, times(1)).deleteAll(cartItems);
    }*/

}