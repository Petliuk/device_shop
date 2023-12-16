function loadProducts(page = 0, pageSize = 10) {
    fetch(`http://localhost:8081/products?page=${page}&pageSize=${pageSize}`)
        .then(response => response.json())
        .then(products => {
            const productListContainer = document.getElementById('productListContainer');
            const addButton = document.createElement('button');
            addButton.innerText = 'Відкрити ще 10 продуктів';

            // Додаємо стилі для кнопки
            addButton.style.backgroundColor = 'black';
            addButton.style.color = 'white';
            addButton.style.border = 'none';
            addButton.style.padding = '10px 20px';

            addButton.addEventListener('click', () => {
                loadProducts(page + 1, pageSize);
            });

            if (page === 0) {
                productListContainer.innerHTML = ''; // Очищаємо контейнер, якщо це перша сторінка
            }

            products.content.forEach(product => {
                const productElement = document.createElement('div');
                productElement.classList.add('product-item');
                productElement.innerHTML = `
               <h2>${product.name}</h2>
                            <div style="display: flex; border: 1px solid #ccc; padding: 10px;">
                                <div style="width: 200px; height: 200px; margin-right: 10px; display: flex; justify-content: center; align-items: center; overflow: hidden;">
                                    <img src="data:image/jpeg;base64,${product.imageData}" alt="${product.name}" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                                </div>
                                <div style="flex: 3; padding-left: 10px;">
                                    &nbsp;
                                    <button class="delete-product-button" data-id="${product.id}">Видалити</button>
                                    &nbsp;
                                    <button class="additional-info-button" data-id="${product.id}">Додаткова інформація</button>
                                    <h2>${product.name}</h2>
                                    <p><strong>ID продукту:</strong> ${product.id}</p>
                                    <p><strong>Ціна продукту:</strong> $${product.price}</p>
                                    <div class="additional-info" id="info-${product.id}"></div>
                                    <button class="collapse-info-button" data-id="${product.id}" style="display: none;">Звернути</button>
                              
                                </div>
                                
                            </div>

                        `;
                const additionalInfoButton = productElement.querySelector('.additional-info-button');
                additionalInfoButton.addEventListener('click', () => {
                    const infoContainer = productElement.querySelector('.additional-info');
                    loadAdditionalInfo(product.id, infoContainer);
                    const collapseInfoButton = productElement.querySelector('.collapse-info-button');
                    collapseInfoButton.style.display = 'block';
                });

                const collapseInfoButton = productElement.querySelector('.collapse-info-button');
                collapseInfoButton.addEventListener('click', () => {
                    // Приховуємо додаткову інформацію та показуємо кнопку "Додаткова інформація"
                    const infoContainer = productElement.querySelector('.additional-info');
                    infoContainer.innerHTML = ''; // Очищаємо додаткову інформацію
                    collapseInfoButton.style.display = 'none';
                });

                const deleteProductButton = productElement.querySelector('.delete-product-button');
                deleteProductButton.addEventListener('click', () => {
                    const productId = deleteProductButton.getAttribute('data-id');
                    if (confirm(`Ви впевнені, що хочете видалити продукт з ID ${productId}?`))
                        deleteProduct(productId);
                });

                // Додайте обробники подій тут, як ви це робили раніше

                productListContainer.appendChild(productElement);
            });

            // Додаємо кнопку "Відкрити ще 10 продуктів" до контейнера
            productListContainer.appendChild(addButton);

            // Оновлення пагінації
            const pagination = document.querySelector('.pagination');
            pagination.innerHTML = '';

            if (!products.first) {
                const prevPageButton = document.getElementById('prevPage');
                prevPageButton.addEventListener('click', () => {
                    loadProducts(products.page - 1);
                });
                pagination.appendChild(prevPageButton);
            }

            if (!products.last) {
                const nextPageButton = document.getElementById('nextPage');
                nextPageButton.addEventListener('click', () => {
                    loadProducts(products.page + 1);
                });
                pagination.appendChild(nextPageButton);
            }
        })
        .catch(error => {
            console.error('Помилка під час завантаження продуктів:', error);
        });
}



function loadAdditionalInfo(productId) {
    const token = localStorage.getItem('token');

    fetch(`http://localhost:8081/product/${productId}`)
        .then(response => response.json())
        .then(productInfo => {
            const discountId = productInfo.discountId;

            fetch(`http://localhost:8081/discount/${discountId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(response => response.json())
                .then(discountInfo => {
                    const infoContainer = document.getElementById(`info-${productId}`);
                    infoContainer.innerHTML = `
                        <p><strong>Артикул (SKU):</strong> ${productInfo.sku}</p>
                        <p><strong>Опис:</strong> ${productInfo.description}</p>
                        <p><strong>Дата створення продукту:</strong> ${productInfo.createdAt}</p>
                        <p><strong>Дата зміни продукту:</strong> ${productInfo.modifiedAt}</p>
                        <p><strong>Дата видалення продукту:</strong> ${productInfo.deletedAt}</p>
                       
                        <div class="discount-info">
                            <p class="discount-name"><strong>Назва знижки:</strong> ${discountInfo.name}</p>
                            <p class="discount-description"><strong>Опис знижки:</strong> ${discountInfo.description}</p>
                            <p class="discount-percent"><strong>Відсоток знижки:</strong> ${discountInfo.discountPercent}</p>
                            <p class="discount-created-at"><strong>Дата створення знижки:</strong> ${discountInfo.createdAt}</p>
                            <p class="discount-modified-at"><strong>Дата зміни знижки:</strong> ${discountInfo.modifiedAt}</p>
                        </div>
                    `;

                })
                .catch(error => {
                    console.error(`Помилка завантаження інформації про знижку з ID ${discountId}:`, error);
                });
        })
        .catch(error => {
            console.error(`Помилка завантаження додаткової інформації для продукту з ID ${productId}:`, error);
        });
}

function deleteProduct(productId) {
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('Токен не знайдено. Увійдіть у систему.');
        return;
    }

    fetch(`http://localhost:8081/product/${productId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.ok) {
                loadProducts();
            } else {
                console.error('Помилка під час видалення продукту.');
            }
        })
        .catch(error => {
            console.error(`Помилка під час видалення продукту з ID ${productId}:`, error);
        });
}
