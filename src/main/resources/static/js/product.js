


function openImageModal(imageUrl) {
    const modal = document.getElementById('imageModal');
    const modalImage = document.getElementById('modalImage');

    modalImage.src = imageUrl;
    modal.style.display = 'block';
}

function closeImageModal() {
    const modal = document.getElementById('imageModal');
    modal.style.display = 'none';
}
function displayProductDetails(product) {
    const productDetailsDiv = document.getElementById('productDetails');
    const productHTML = `
        <div style="display: flex; border: 1px solid #ccc; padding: 10px;">
            <div style="width: 310px; height: 310px; margin-right: 10px; display: flex; justify-content: center; align-items: center; overflow: hidden;">
               
                <img src="data:image/jpeg;base64,${product.imageData}" alt="${product.name}" style="max-width: 1000%; max-height: 100%; object-fit: contain; cursor: pointer;" onclick="openImageModal('data:image/jpeg;base64,${product.imageData}')">
            </div>
            <div style="flex: 3; padding-left: 10px;">
                <h2>${product.name}</h2>
                <p>Номер продукту: ${product.id}</p>
                <p>Опис: ${product.description}</p>
                <p>SKU: ${product.sku}</p>
                <p>Ціна: $${product.price}</p>
            </div>
        </div>
    `;
    productDetailsDiv.innerHTML = productHTML;

    const addToCartButton = document.getElementById('addToCartButton');
    addToCartButton.onclick = function () {
        if (isLoggedIn()) {
            addToCart(product.id);
        } else {
            alert("Щоб купити товар, вам необхідно увійти в обліковий запис!");
            goToRegistrationPage();
        }
    };
}

function isLoggedIn() {
    const token = localStorage.getItem('token');
    return !!token;
}

async function redirectToProductDetailsPage(productId) {

        try {
            const response = await fetch(`http://localhost:8081/product/${productId}`);
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

function searchProduct() {
    const searchInput = document.getElementById('searchInput').value;

    if (searchInput.trim() !== '') {
        const searchById = /^\d+$/.test(searchInput);
        const endpoint = searchById ? `product/${searchInput}` : `search/${encodeURIComponent(searchInput)}`;

        fetch(`http://localhost:8081/${endpoint}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('/*Товар не знайдено*/');
                }
                return response.json();
            })
            .then(product => {
                if (Array.isArray(product)) {
                    displaySearchResults(product);
                } else {
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
                <p>Номер продукту: ${product.id}</p>
                <p>Ціна: $${product.price}</p>    
            </div>                                               
        </div>
    `).join('');

    productsListDiv.innerHTML = productsHTML;
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('sessionId');
    goToRegistrationPage();

}

function goToRegistrationPage() {
    window.location.href = 'client.html';
}

function redirectToCartPage() {
    if (isLoggedIn()) {
        window.location.href = 'cart.html';
    } else {
        alert("In order to view the cart, you must log in to your account!");
        goToRegistrationPage();
    }
}

function goBack() {
    document.getElementById('productDetailsContainer').style.display = 'none';
    document.getElementById('productsList').style.display = 'grid';
    document.getElementById("listHeader").style.visibility = 'initial';
}

function displayErrorMessage(message) {
    const searchResultDiv = document.getElementById('searchResul');
    searchResultDiv.innerHTML = `<p>${message}</p>`;
}

function getSessionIdFromLocalStorage() {
    return localStorage.getItem('sessionId');
}

function saveSessionIdToLocalStorage(sessionId) {
    localStorage.setItem('sessionId', sessionId);
}

renderProducts();
// Отримання токену з локального сховища
const token = localStorage.getItem('token');

async function fetchCategories() {
    try {
        const response = await fetch('http://localhost:8081/product-categories', {
            headers: {
                'Authorization': `Bearer ${token}` // Додати токен у заголовки
            }
        });
        if (!response.ok) {
            throw new Error('Помилка отримання категорій');
        }
        const categories = await response.json();
        return categories;
    } catch (error) {
        console.error('Помилка отримання категорій:', error);
        return [];
    }
}

async function populateCategorySearch() {
    const categorySearchSelect = document.getElementById('categorySearch');
    categorySearchSelect.innerHTML = '<option value="">Обрати категорію</option>';
    const categories = await fetchCategories();

    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;
        option.text = category.name;
        categorySearchSelect.appendChild(option);
    });
}

function searchByCategory() {
    const categorySearchSelect = document.getElementById('categorySearch');
    const selectedCategoryId = categorySearchSelect.value;

    if (selectedCategoryId) {
        const endpoint = `products/category/${selectedCategoryId}`;
        fetch(`http://localhost:8081/${endpoint}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}` // Додати токен у заголовки
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Помилка отримання продуктів за категорією');
                }
                return response.json();
            })
            .then(products => {
                displaySearchResults(products);
            })
            .catch(error => {
                displayErrorMessage(error.message);
            });
    }
}

// Виклик функції populateCategorySearch() для заповнення випадаючого списку категорій при завантаженні сторінки
populateCategorySearch();

async function fetchProducts(token) {
    try {
        const response = await fetch('http://localhost:8081/products', {
            headers: {
                Authorization: `Bearer ${token}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }

        });

        const products = await response.json();
        return products;
    } catch (error) {
        console.error('Error fetching products:', error);
        return [];
    }
}

async function fetchProducts(token, page, pageSize) {
    try {
        const response = await fetch(`http://localhost:8081/products?page=${page}&pageSize=${pageSize}`, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

        const products = await response.json();
        return products;
    } catch (error) {
        console.error('Error fetching products:', error);
        return [];
    }
}

async function renderProducts(page = 0) {
    const productsListDiv = document.getElementById('productsList');
    const token = localStorage.getItem('token');
    const pageSize = 20; // Кількість продуктів на одній сторінці

    try {
        const products = await fetchProducts(token, page, pageSize);

        if (products.content.length === 0) {
            productsListDiv.innerHTML = '<p>No products available.</p>';
            return;
        }

        const productsHTML = products.content.map(product => `
            <div onclick="redirectToProductDetailsPage(${product.id})" style="display: flex; border: 1px solid #ccc; padding: 10px; margin-bottom: 10px; cursor: pointer;">
                <div style="width: 200px; height: 200px; margin-right: 10px; display: flex; justify-content: center; align-items: center; overflow: hidden;">
                    <img src="data:image/jpeg;base64,${product.imageData}" alt="${product.name}" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                </div>
                <div style="flex: 3;">
                    <h2>${product.name}</h2>
                    <p>Номер продукту: ${product.id}</p>
                    <p>Ціна: $${product.price}</p>
                </div>
            </div>
        `).join('');

        productsListDiv.innerHTML = productsHTML;

        const pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

        if (!products.first) {
            const prevPageButton = document.createElement('button');
            prevPageButton.textContent = 'Попередня сторінка';
            prevPageButton.addEventListener('click', () => {
                renderProducts(page - 1);
            });
            pagination.appendChild(prevPageButton);
        }

        if (!products.last) {
            const nextPageButton = document.createElement('button');
            nextPageButton.textContent = 'Наступна сторінка';
            nextPageButton.addEventListener('click', () => {
                renderProducts(page + 1);
            });
            pagination.appendChild(nextPageButton);
        }
    } catch (error) {
        console.error('Error fetching products:', error);
        productsListDiv.innerHTML = '<p>Error fetching products.</p>';
    }
}

// Початковий виклик функції для відображення першої сторінки
renderProducts();







/*
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
            <p>Номер продукту: ${product.id}</p>
            <p>Ціна: $${product.price}</p>    
        </div>
    </div>
`).join('');

    productsListDiv.innerHTML = productsHTML;
}
*/

