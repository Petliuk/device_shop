/*
window.onload = function() {

// Додаємо функцію для перетворення коми на крапку в інпутах, кі мають містити числівники
    const numberInputs = document.querySelectorAll('input[type="number"]');

    numberInputs.forEach(function(input) {
        input.addEventListener('input', function () {
            this.value = this.value.replace(',', '.');
        });
    });
*/

// Передаємо фото-файл на сервер:
    
/*
    function uploadFile() {

        const accessToken = localStorage.getItem('token');

        if (!accessToken) {
            alert('Для завантаження файлу вам потрібно увійти.');
            return;
        }

        const fileInput = document.getElementById('photo');
        if (fileInput.files.length === 0) {
            alert('Please select a file to upload.');
            return;
        }
    
        const imegFile = new imegFile();
        imegFile.append('file', fileInput.files[0]);
    
        const requestOptions = {
            method: 'POST',
            body: imegFile
        };
    
        fetch('http://localhost:8081/upload', requestOptions)
            .then(response => {
                if (response.ok) {
                    alert('The file was successfully uploaded to the server.');
                } else {
                    alert('An error occurred while uploading the file to the server.');
                }
            })
            .catch(error => {
                console.error('An error has occurred:', error);
            });
    }
    
    const uploadButton = document.querySelector('.upload');
    uploadButton.addEventListener('click', uploadFile);
*/
/*


function goToProductsPage() {
    // Implement your logic to navigate back to the products page
}

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

async function saveProductData() {
    const productName = document.querySelector('.product_name').value;
    const description = document.querySelector('.description').value;
    const sku = document.querySelector('.sku').value;
    const price = parseFloat(document.querySelector('.price').value);
    const quantity = parseInt(document.querySelector('.quantity').value);

    const productData = {
        name: productName,
        description: description,
        sku: sku,
        price: price,
        quantity: quantity,
    };

    const accessToken = localStorage.getItem('token');
    if (!accessToken) {
        alert('You need to log in to save product data.');
        return;
    }

    try {
        const productResponse = await fetch('http://localhost:8081/product', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${accessToken}`,
            },
            body: JSON.stringify(productData),
        });

        if (productResponse.ok) {
            alert('Product data saved successfully.');
            document.getElementById('editSuccess').style.display = 'block';
        } else {
            alert('An error occurred while saving product data.');
        }
    } catch (error) {
        console.error('An error has occurred:', error);
    }
}
*/


// function goToProductsPage() {
//     window.location.href = 'products.html';
// }

// async function login() {
//     const email = document.getElementById('email').value;
//     const password = document.getElementById('password').value;

//     const formData = new URLSearchParams();
//     formData.append('username', email);
//     formData.append('password', password);

//     try {
//         const response = await fetch('http://localhost:8081/login', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/x-www-form-urlencoded'
//             },
//             body: formData.toString()
//         });

//         if (!response.ok) {
//             throw new Error('Не вдалось увійти');
//         }

//         const accessToken = await response.text();

//         console.log(accessToken);
//         localStorage.setItem('token', accessToken);

//         const userResponse = await fetch('http://localhost:8081/users/principal', {
//             headers: {
//                 Authorization: `Bearer ${accessToken}`
//             }
//         });

//         if (userResponse.ok) {
//             const user = await userResponse.json();
//             localStorage.setItem('userId', user.id);
//         }

//         goToProductsPage();

//     } catch (error) {
//         console.error('Помилка входу:', error);
//         alert('Не вдалось увійти. Перевірте ваші облікові дані та спробуйте знову.');
//     }
// }

// const registrationForm = document.getElementById('registrationForm');

// function register() {
//     // Отримайте дані з форми реєстрації
//     const name = document.getElementById('name').value;
//     const surname = document.getElementById('surname').value;
//     const phone = document.getElementById('phone').value;
//     const email = document.getElementById('regEmail').value;
//     const password = document.getElementById('regPassword').value;

//     const user = {
//         name,
//         surname,
//         phone,
//         email,
//         password
//     };

//     fetch('http://localhost:8081/users', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(user)
//     })
//         .then(response => {
//             if (response.ok) {
//                 return response.json();
//             } else {
//                 throw new Error('Edit failed');
//             }
//         })
//         .then(data => {
//             console.log('Edit successful:', data);
//             const editSuccess = document.getElementById('editSuccess');
//             editSuccess.style.display = 'block';
//             document.getElementById('editSuccess').reset();
//         })
//         .catch(error => {
//             console.error('Edit error:', error);
//         });
// }

// document.getElementById('loginForm').addEventListener('submit', async (event) => {
//     event.preventDefault();
//     login();
// });


// document.getElementById('registrationForm').addEventListener('submit', async (event) => {
//     event.preventDefault();
//     register();
// });
