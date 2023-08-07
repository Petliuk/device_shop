// Асинхронно отримує список продуктів з сервера за допомогою токена авторизації.
async function fetchProducts(token) {
    try {
        const response = await fetch('http://localhost:8080/products', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        const products = await response.json();
        return products;
    } catch (error) {
        console.error('Error fetching products:', error);
        return [];
    }
}

function displayProductDetails(product) {
    const productDetailsDiv = document.getElementById('productDetails');
    const productHTML = `
        <div style="display: flex; border: 1px solid #ccc; padding: 10px;">
            <div style="width: 200px; height: 200px; margin-right: 10px; display: flex; justify-content: center; align-items: center; overflow: hidden;">
                <img src="data:image/jpeg;base64,${product.imageData}" alt="${product.name}" style="max-width: 1000%; max-height: 100%; object-fit: contain;">
            </div>
            <div style="flex: 3; padding-left: 10px;">
                <h2>${product.name}</h2>
                <p>ID: ${product.id}</p>
                <p>Description: ${product.description}</p>
                <p>SKU: ${product.sku}</p>
                <p>Price: $${product.price}</p>
            </div>
        </div>
    `;
    productDetailsDiv.innerHTML = productHTML;

    const addToCartButton = document.getElementById('addToCartButton');
    addToCartButton.onclick = function () {
        if (isLoggedIn()) {
            addToCart(product.id); // Call the function with the correct product ID
        } else {
            // If not logged in, show a message and redirect to the registration page
            alert("In order to buy a product, you must log in to your account!");
            goToRegistrationPage();
        }
    };
}

function isLoggedIn() {
    const token = localStorage.getItem('token');
    return !!token; // If token is present, the user is logged in
}


// Асинхронно переходить на сторінку з деталями продукту за його ідентифікатором.
async function redirectToProductDetailsPage(productId) {

        try {
            const response = await fetch(`http://localhost:8080/product/${productId}`);
            if (!response.ok) {
                throw new Error('Товар не знайдено');
            }
            const product = await response.json();
            displayProductDetails(product);
            document.getElementById('productsList').style.display = 'none';
            document.getElementById("listHeader").style.visibility = 'hidden';
            document.getElementById('productDetailsContainer').style.display = 'block';
        } catch (error) {
            displayErrorMessage(error.message);
        }

}

async function renderProducts() {
    const productsListDiv = document.getElementById('productsList');
    const token = localStorage.getItem('token');
    const products = await fetchProducts(token);

    if (products.length === 0) {
        productsListDiv.innerHTML = '<p>No products available.</p>';
        return;
    }

    const productsHTML = products.map(product => `
    <div onclick="redirectToProductDetailsPage(${product.id})" style="display: flex; border: 1px solid #ccc; padding: 10px; margin-bottom: 10px; cursor: pointer;">
        <div style="width: 200px; height: 200px; margin-right: 10px; display: flex; justify-content: center; align-items: center; overflow: hidden;">
            <img src="data:image/jpeg;base64,${product.imageData}" alt="${product.name}" style="max-width: 100%; max-height: 100%; object-fit: contain;">
        </div>
        <div style="flex: 3;">
            <h2>${product.name}</h2>
            <p>ID: ${product.id}</p>
            <p>Price: $${product.price}</p>    
        </div>
        <div class="form-g">
            <button onclick="addToCart(${product.id})" style="background-color: #4CAF50; color: white;">Buy</button>
        </div>
    </div>
`).join('');

    productsListDiv.innerHTML = productsHTML;
}

// Виконує пошук продукту за його ідентифікатором або назвою та переходить на сторінку з його деталями
// Виконує пошук продукту за його ідентифікатором або назвою та переходить на сторінку з його деталями
function searchProduct() {
    const searchInput = document.getElementById('searchInput').value;

    if (searchInput.trim() !== '') {
        const searchById = /^\d+$/.test(searchInput); // Перевірка, чи введено ID (число)
        const endpoint = searchById ? `product/${searchInput}` : `search/${encodeURIComponent(searchInput)}`;

        fetch(`http://localhost:8080/${endpoint}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Товар не знайдено');
                }
                return response.json();
            })
            .then(product => {
                if (Array.isArray(product)) {
                    // Якщо повернута багато продуктів, відобразіть список результатів пошуку
                    displaySearchResults(product);
                } else {
                    // Якщо знайдений лише один продукт, перенаправте на сторінку з деталями продукту
                    redirectToProductDetailsPage(product.id);
                }
            })
            .catch(error => {
                displayErrorMessage(error.message);
            });
    } else {
        displayErrorMessage('Введіть ID або назву товару');
    }
}

// Відобразіть список результатів пошуку
function displaySearchResults(products) {
    const productsListDiv = document.getElementById('productsList');
    if (products.length === 0) {
        productsListDiv.innerHTML = '<p>No products found.</p>';
        return;
    }

    const productsHTML = products.map(product => `
        <div onclick="redirectToProductDetailsPage(${product.id})" style="display: flex; border: 1px solid #ccc; padding: 10px; margin-bottom: 10px; cursor: pointer;">
            <div style="width: 200px; height: 200px; margin-right: 10px; display: flex; justify-content: center; align-items: center; overflow: hidden;">
                <img src="data:image/jpeg;base64,${product.imageData}" alt="${product.name}" style="max-width: 100%; max-height: 100%; object-fit: contain;">
            </div>
            <div style="flex: 3;">
                <h2>${product.name}</h2>
                <p>ID: ${product.id}</p>
                <p>Price: $${product.price}</p>    
            </div>
            <div class="form-g">
                <button onclick="addToCart(${product.id})" style="background-color: #4CAF50; color: white;">Buy</button>
            </div>
        </div>
    `).join('');

    productsListDiv.innerHTML = productsHTML;
}




// Видаляє токен з локального сховища та переадресовує на сторінку реєстрації/входу.
function logout() {
    // Видалення токена з локального сховища
    localStorage.removeItem('token');
    localStorage.removeItem('userId'); // Також видаляємо userId
    localStorage.removeItem('sessionId'); // Ідентифікатор сесії також видаляємо
    // Перенаправлення на сторінку реєстрації/входу після виходу
    goToRegistrationPage();

}
// Переходить на сторінку реєстрації/входу.

function goToRegistrationPage() {
    // Перенаправлення на сторінку реєстрації/входу (змініть URL на потрібний)
    window.location.href = 'http://localhost:63342/MyProjectWithSpring2/src/main/resources/static/client.html?_ijt=nvvcca5f9fhi6ia89c8l0fr334&_ij_reload=RELOAD_ON_SAVE';
}

// Переходить на сторінку кошика.
function redirectToCartPage() {
    if (isLoggedIn()) {
        window.location.href = 'http://localhost:63342/MyProjectWithSpring2/src/main/resources/static/cart.html';
    } else {
        // If not logged in, show a message and redirect to the registration page
        alert("In order to view the cart, you must log in to your account!");
        goToRegistrationPage();
    }
}

// Приховує контейнер з деталями продукту та відображає список продуктів.
function goBack() {
    document.getElementById('productDetailsContainer').style.display = 'none';
    document.getElementById('productsList').style.display = 'grid';
    document.getElementById("listHeader").style.visibility = 'initial';
}

// Виводить повідомлення про помилку на сторінці пошуку.
function displayErrorMessage(message) {
    const searchResultDiv = document.getElementById('searchResult');
    searchResultDiv.innerHTML = `<p>${message}</p>`;
}


// Отримує ID сесії з локального сховища.
function getSessionIdFromLocalStorage() {
    return localStorage.getItem('sessionId');
}

// Зберігає ID сесії в локальне сховище.

function saveSessionIdToLocalStorage(sessionId) {
    localStorage.setItem('sessionId', sessionId);
}
/*redirectToCartPage();*/
// Виклик функції для відображення списку продуктів
renderProducts();
