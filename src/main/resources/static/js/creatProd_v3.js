
async function uploadFile() {
    const fileInput = document.getElementById('image');
    if (fileInput.files.length === 0) {
        alert('Please select a file to upload.');
        return;
    }

    const accessToken = localStorage.getItem('token');
    if (!accessToken) {
        alert('You need to log in to upload a file.');
        return;
    }

    const formData = new FormData();
    formData.append('image', fileInput.files[0]);

    const requestOptions = {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
        body: formData,
    };

    try {
        const response = await fetch('http://localhost:8081/upload', requestOptions);
        if (response.ok) {
            const { photoId } = await response.json();
            alert('File uploaded successfully.');
        } else {
            alert('An error occurred while uploading the file to the server.');
        }
    } catch (error) {
        console.error('An error has occurred:', error);
    }
}

// Відправка даних з форми information:
async function saveProductData() {
    const accessToken = localStorage.getItem('token');
    if (!accessToken) {
        alert('You need to log in to save product data.');
        return;
    }

    // Отримуємо дані з форми
    const productName = document.querySelector('.product_name').value;
    const productDescription = document.querySelector('.description').value;
    const sku = document.querySelector('.sku').value;
    const price = parseFloat(document.querySelector('.price').value.replace(',', '.'));
    const quantity = parseFloat(document.querySelector('.quantity').value.replace(',', '.'));

    // Перевіряємо, чи всі поля заповнені
    if (!productName || !sku || isNaN(price) || isNaN(quantity)) {
        alert('Please fill in all required fields with valid data.');
        return;
    }

    // Створюємо обєкт
    const productData = {
        id: productId,
        name: productName,
        description: productDescription,
        sku: sku,
        price: price,
        createdAt: new Date().toISOString(),
        modifiedAt: new Date().toISOString(),
        deletedAt: null,
        quantity: quantity,
        discountId: discountId,
        photoId: photoId
    };

    // Створюємо запит
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify(productData),
    };

    try {
        const response = await fetch('http://localhost:8081/product', requestOptions);
        if (response.ok) {
            alert('Product data saved successfully.');
        } else {
            alert('An error occurred while saving product data.');
        }
    } catch (error) {
        console.error('An error has occurred:', error);
    }
}

// // Додаємо функцію для перетворення коми на крапку в інпутах, які мають містити числівники
// const numberInputs = document.querySelectorAll('input[type="number"]');

// numberInputs.forEach(function(input) {
//     input.addEventListener('input', function () {
//         this.value = this.value.replace(',', '.');
//     });
// });
