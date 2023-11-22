let currentPage = 'products';

function switchPage(page) {
    if (page === currentPage) {
        return;
    }

    currentPage = page;

    const productListContainer = document.getElementById('productListContainer');
    const userListContainer = document.getElementById('userListContainer');
    const discountListContainer = document.getElementById('discountListContainer');
    const addProductFormContainer = document.getElementById('addProductFormContainer');
    const editUserDialog = document.getElementById('editUserDialog');

    productListContainer.innerHTML = '';
    userListContainer.innerHTML = '';
    discountListContainer.innerHTML = '';
    addProductFormContainer.style.display = 'none';
    addCategoryFormContainer.style.display = 'none';
    categoriesContainer.style.display = 'none';
    ordersContainer.style.display = 'none';

    hideCreateDiscountForm();
    hideEditDiscountForm();

    editUserDialog.style.display = 'none';

    if (page === 'products') {
        loadProducts();
    } else if (page === 'users') {
        getUsers();
    } else if (page === 'discounts') {
        loadDiscounts();
    } else if (page === 'createDiscount') {
        resetCreateDiscountForm();
        showCreateDiscountForm();
    } else if (page === 'addProduct') {
        showAddProductForm();
    } else if (page === 'editDiscount') {
        showEditDiscountForm();
    } else if (page === 'editUser') {
        showEditUserDialog();
    }else if (page === 'addCategory') {
        // Відображення форми для створення категорії
        showAddCategoryForm();
    }else if(page === 'categories'){
        loadCategories();
    }else if(page === 'orders'){
        ordersContainer.style.display = 'block';
        loadOrders();
    }
}



document.getElementById('addProductButton').addEventListener('click', function () {
    switchPage('addProduct');
});

function showAddProductForm() {
    const addProductFormContainer = document.getElementById('addProductFormContainer');
    addProductFormContainer.style.display = 'block';
}

function hideCreateDiscountForm() {
    const createDiscountFormContainer = document.getElementById('createDiscountFormContainer');
    createDiscountFormContainer.style.display = 'none';
}

document.getElementById('createDiscountButton').addEventListener('click', function () {
    switchPage('createDiscount');
});

function showCreateDiscountForm() {
    const createDiscountFormContainer = document.getElementById('createDiscountFormContainer');
    createDiscountFormContainer.style.display = 'block';
}

document.querySelector('.menu-button:nth-child(4)').addEventListener('click', function () {
    switchPage('discounts');
});

document.getElementById('productsButton').addEventListener('click', function () {
    switchPage('products');
    loadProducts();
});

document.getElementById('usersButton').addEventListener('click', function () {
    switchPage('users');
});

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('sessionId');
    window.location.href = 'registration.html';
}


const productDescriptionField = document.getElementById('productDescription');

productDescriptionField.addEventListener('input', function () {
    this.style.height = 'auto';
    this.style.height = (this.scrollHeight + 10) + 'px';
});


productDescriptionField.style.height = 'auto';
productDescriptionField.style.height = (productDescriptionField.scrollHeight + 10) + 'px';


function populateDiscountOptions() {
    const discountSelect = document.getElementById('discount');

    fetch('http://localhost:8081/discount')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch discounts.');
            }
            return response.json();
        })
        .then(data => {
            discountSelect.innerHTML = '';

            data.forEach(discount => {
                const option = document.createElement('option');
                option.value = discount.id;
                option.text = `${discount.name} - ${discount.discountPercent}`;
                discountSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching discounts:', error);
        });
}
populateDiscountOptions();



// При переході до форми для додавання продукту, запит на сервер за категоріями буде викликано автоматично
document.getElementById('addProductButton').addEventListener('click', async function () {
    switchPage('addProduct');
    await loadCategoriesForProduct();
});

document.getElementById('addProductForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    // Решта коду для додавання продукту
    // ...
});

// Функція для завантаження категорій при переході до форми для додавання продукту
async function loadCategoriesForProduct() {
    const categorySelect = document.getElementById('category');
    categorySelect.innerHTML = '';

    const token = localStorage.getItem('token');

    try {
        const response = await fetch('http://localhost:8081/product-categories', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Помилка отримання категорій.');
        }

        const data = await response.json();

        data.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.text = category.name;
            categorySelect.appendChild(option);
        });
    } catch (error) {
        console.error('Помилка завантаження категорій:', error);
    }
}

document.getElementById('addProductForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const productName = document.getElementById('productName').value;
    const productDescription = document.getElementById('productDescription').value;
    const productSKU = document.getElementById('productSKU').value;
    const productPrice = parseFloat(document.getElementById('productPrice').value);
    const productQuantity = parseInt(document.getElementById('productQuantity').value);
    const image = document.getElementById('image').files[0];


    const token = localStorage.getItem('token');

    let selectedDiscountId = null;

    const discountSelect = document.getElementById('discount');
    selectedDiscountId = parseInt(discountSelect.value);

    let photoId = null;

    if (image) {
        try {
            const photoIdResponse = await uploadImage(image, token);
            photoId = photoIdResponse;

        } catch (error) {
            console.error('Error uploading image:', error);
            return;
        }
    }

    const productData = {
        name: productName,
        description: productDescription,
        sku: productSKU,
        price: productPrice,
        createdAt: new Date().toISOString(),
        modifiedAt: new Date().toISOString(),
        deletedAt: "2023-09-19T18:00:34.345Z",
        discountId: selectedDiscountId,
        quantity: productQuantity,
        photoId: photoId,
        categoryId: parseInt(document.getElementById('category').value),
    };

    try {
        const response = await createProduct(productData, token);
    } catch (error) {
        console.error('Error creating product:', error);
    }
});

async function uploadImage(image, token) {
    const formData = new FormData();
    formData.append('image', image);

    const response = await fetch('http://localhost:8081/photos/upload', {
        method: 'POST',
        body: formData,
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error('Failed to upload image.');
    }

    return response.json();
}

async function createProduct(productData, token) {
    const response = await fetch('http://localhost:8081/product', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(productData),
    });

    if (!response.ok) {
        throw new Error('Failed to create product.');
    } else {
        document.getElementById('productName').value = '';
        document.getElementById('productDescription').value = '';
        document.getElementById('productSKU').value = '';
        document.getElementById('productPrice').value = '';
        document.getElementById('productQuantity').value = '';
        const addProductFormContainer = document.getElementById('addProductFormContainer');
        addProductFormContainer.style.display = 'none';

        loadProducts();
    }

    return response.json();
}

const imageInput = document.getElementById('image');
const productPreview = document.getElementById('productPreview');

imageInput.addEventListener('change', function () {
    if (imageInput.files && imageInput.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            productPreview.src = e.target.result;
            productPreview.style.display = 'block';
        };
        reader.readAsDataURL(imageInput.files[0]);
    } else {
        productPreview.src = '';
        productPreview.style.display = 'none';
    }
});

//------------------------------------------------------------------------------------
document.getElementById('b').addEventListener('click', function () {
    switchPage('orders');
});


async function loadOrders() {
    try {
        const token = localStorage.getItem('token');

        const response = await fetch('http://localhost:8081/orders', {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Failed to fetch orders.');
        }

        const orders = await response.json();
        displayOrders(orders);
    } catch (error) {
        console.error('Error fetching orders:', error);
    }
}

function displayOrders(orders) {
    const ordersContainer = document.getElementById('ordersContainer');
    ordersContainer.innerHTML = '';

    orders.forEach(order => {
        const orderDiv = document.createElement('div');
        orderDiv.classList.add('order-item');

        const user = order.user;
        const product = order.product;

        const orderInfo = `
            <h3>Замовлення:</h3>
            <p><strong>ID користувача:</strong> ${user.id}</p>
            <p><strong>Ім'я користувача:</strong> ${user.name} ${user.surname}</p>
            <p><strong>Email:</strong> ${user.email}</p>
            <p><strong>Телефон:</strong> ${user.phone}</p>
            <p><strong>Адреса доставки:</strong> ${order.address}</p>
            <h3>Товар:</h3>
            <p><strong>ID товару:</strong> ${product.id}</p>
            <p><strong>Назва:</strong> ${product.name}</p>
            <p><strong>Опис:</strong> ${product.description}</p>
            <p><strong>Ціна:</strong> ${product.price}</p>
            <p><strong>Кількість:</strong> ${product.quantity}</p>
            <button class="delete-order-btn" data-order-id="${order.id}">Видалити</button>
        `;

        orderDiv.innerHTML = orderInfo;
        ordersContainer.appendChild(orderDiv);

        // Додамо обробник подій для кожної кнопки видалення
        const deleteButton = orderDiv.querySelector('.delete-order-btn');
        deleteButton.addEventListener('click', async () => {
            const orderId = deleteButton.getAttribute('data-order-id');
            await deleteOrder(orderId);
        });
    });
}

async function deleteOrder(orderId) {
    const token = localStorage.getItem('token');

    try {
        const response = await fetch(`http://localhost:8081/order/${orderId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Failed to delete the order.');
        }

        await loadOrders();
    } catch (error) {
        console.error('Error deleting order:', error);
    }
}
