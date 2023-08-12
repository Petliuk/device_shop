async function createOrGetShoppingSession() {
    const sessionId = localStorage.getItem('sessionId');
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (!token || !userId) {
        throw new Error("Користувач не авторизований або токен недійсний");
    }

    const newSessionResponse = await fetch("http://localhost:8080/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userId: userId
        })
    });

    if (newSessionResponse.ok) {
        const newSession = await newSessionResponse.json();
        const newSessionId = newSession.id;
        localStorage.setItem('sessionId', newSessionId);
        return newSessionId;
    } else {
        throw new Error("Не вдалося створити новий sessionId");
    }
}

let quantity = 1;
const quantityValueElement = document.getElementById('quantityValue');

function incrementQuantity() {
    quantity++;
    updateQuantity();
}

function decrementQuantity() {
    if (quantity > 1) {
        quantity--;
        updateQuantity();
    }
}

function updateQuantity() {
    quantityValueElement.textContent = quantity;
}

async function addToCart(productId) {
    const token = localStorage.getItem('token');
    let sessionId = getSessionIdFromLocalStorage();

    if (!token) {
        window.location.href = 'http://localhost:63342/MyProjectWithSpring2/src/main/resources/static/client.html?_ijt=nvvcca5f9fhi6ia89c8l0fr334&_ij_reload=RELOAD_ON_SAVE';
        alert('In order to buy a product, you must log in to your account!');
        return;
    }
    if (!sessionId) {
        sessionId = await createOrGetShoppingSession();
    }

    try {
        const response = await fetch(`http://localhost:8080/cart/${sessionId}/items`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                productId: productId,

                shoppingSessionId: sessionId,
                quantity: quantity
            })
        });

        if (!response.ok) {
            throw new Error('Failed to add the product to the cart.');
        }

        const notificationMessage = document.getElementById('notificationMessage');
        notificationMessage.style.display = 'block';
        setTimeout(() => {
            notificationMessage.style.display = 'none';
        }, 3000);

        getCartItems();

        updateQuantity();
    } catch (error) {
        console.error('Error adding product to cart:', error);
    }
}

async function getCartItems() {
    const sessionId = getSessionIdFromLocalStorage();
    if (!sessionId) {
        console.error('Session ID not found in local storage');
        return;
    }

    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`http://localhost:8080/cart/${sessionId}/items`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch cart items.');
        }

        const cartItems = await response.json();

        displayCartItems(cartItems);

        updateQuantity();
    } catch (error) {
        console.error('Error fetching cart items:', error);
    }
}
function displayCartItems(cartItems) {
    const cartItemsListDiv = document.getElementById('cartItemsList');

    if (cartItems.length === 0) {
        cartItemsListDiv.innerHTML = '<p class="empty-cart-msg">Кошик порожній.</p>';
        return;
    }

    const cartItemsHTML = cartItems.map(item => `
        <div style="display: flex; border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <!-- Place the product image on the left side -->
            <div style="width: 200px; height: 200px; margin-right: 10px; display: flex; justify-content: center; align-items: center; overflow: hidden;">
                <img src="data:image/jpeg;base64,${item.imageData}" alt="${item.name}" style="max-width: 100%; max-height: 100%; object-fit: contain;">
            </div>
            <div style="flex: 1;">
                <h2>${item.name}</h2>
                 <p>Cart Items ID: ${item.id}</p>
          
                <p>Quantity: ${item.quantity}</p>
                <p>Description: ${item.description} </p>
                <p>Sku: ${item.sku}</p>
                <p>Price: ${item.price}</p>
                <p>DiscountId ${item.discountId}</p>
                <button onclick="deleteProductFromCart(${item.id})" style="background-color: #FF0000; color: white;">Remove</button>
            </div>
        </div>
    `).join('');

    cartItemsListDiv.innerHTML = cartItemsHTML;

    updateQuantity();
}

async function goBackToProducts() {
    window.location.href = 'http://localhost:63342/MyProjectWithSpring2/src/main/resources/static/products.html';
}


async function deleteProductFromCart(cartItemId) {
    const token = localStorage.getItem('token');

    if (!cartItemId) {
        console.error('Cart Item ID not found.');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/cart/items/${cartItemId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            console.error('Failed to delete the product from the cart.');
            throw new Error('Failed to delete the product from the cart.');
        }

        const notificationMessage = document.getElementById('notificationMessage');
        notificationMessage.style.display = 'block';
        notificationMessage.innerText = 'Product successfully deleted from the cart';
        setTimeout(() => {
            notificationMessage.style.display = 'none';
        }, 3000);

        getCartItems();
    } catch (error) {
        console.error('Error deleting product from cart:', error);
    }
}

getCartItems();