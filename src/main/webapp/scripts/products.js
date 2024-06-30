document.addEventListener('DOMContentLoaded', function() {
    const editButtons = document.querySelectorAll('.edit');
    const editModal = document.getElementById('editModal');
    const editId = document.getElementById('edit-id');
    const editName = document.getElementById('edit-name');
    const editPrice = document.getElementById('edit-price');
    const editStock = document.getElementById('edit-stock');
    const editDescription = document.getElementById('edit-description');
    const editCategory = document.getElementById('edit-category');

    editButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const name = this.getAttribute('data-name');
            const price = this.getAttribute('data-price');
            const stock = this.getAttribute('data-stock');
            const description = this.getAttribute('data-description');
            const category = this.getAttribute('data-category');

            editId.value = id;
            editName.value = name;
            editPrice.value = price;
            editStock.value = stock;
            editDescription.value = description;
            editCategory.value = category;

            editModal.style.display = 'block';
        });
    });

    // Add an event listener for clicks on the window
    window.addEventListener('click', function(event) {
        // If the click was outside the modal, hide the modal
        if (event.target === editModal) {
            editModal.style.display = 'none';
        }
    });
});

let selectedCategory = 0;
let currentPage = 1;
let totalPages = 1;

const editModal = document.getElementById('editModal');
const editId = document.getElementById('edit-id');
const editName = document.getElementById('edit-name');
const editPrice = document.getElementById('edit-price');
const editStock = document.getElementById('edit-stock');
const editDescription = document.getElementById('edit-description');
const editCategory = document.getElementById('edit-category');

function preparePage() {
    if(sessionStorage.getItem('selectedCategoryProductsPage') != null) {
        selectedCategory = sessionStorage.getItem('selectedCategoryProductsPage');
    }
    else {
        selectedCategory = 0;
        sessionStorage.setItem('selectedCategoryProductsPage', selectedCategory);
    }
    currentPage = 1;
    totalPages = 1;

    document.getElementById("category_option_" + selectedCategory).selected = true;

    getPageCount(selectedCategory);
    loadMoreProducts(currentPage, selectedCategory);
}

function categoryChangeHandler() {
    const categorySelect = document.getElementById('selectInput');
    selectedCategory = categorySelect.value;
    currentPage = 1;

    sessionStorage.setItem('selectedCategoryProductsPage', selectedCategory);

    getPageCount(selectedCategory);
    loadMoreProducts(currentPage, selectedCategory, true);
}

function scrollHandler() {
    const productsDiv = document.getElementById('products');

    // Check if the user has scrolled to the bottom of the page
    if (productsDiv.scrollTop + productsDiv.clientHeight >= productsDiv.scrollHeight) {
        // Load more products
        // loadMoreProducts();

        console.log('Loading more products')
        currentPage++;
        loadMoreProducts(currentPage, selectedCategory);

    }
}

function getPageCount(categoryId) {
    fetch(`product_servlet?action=page_count&category_id=${categoryId}`)
        .then(response => response.json())
        .then(data => {
            totalPages = data.page_count;
        });
}

function loadMoreProducts(pageNumber, categoryId, clearPrevious = false) {
    if(pageNumber > totalPages) {
        return;
    }

    fetch(`product_servlet?action=products&page=${pageNumber}&category_id=${categoryId}`)
        .then(response => response.json())
        .then(products => {
            displayProducts(products, clearPrevious);
        });
}

function displayProducts(products, clearPrevious = false) {

    const productsListContainer = document.getElementById("products-ul");

    if(clearPrevious) {
        productsListContainer.innerHTML = '';
    }

    products.forEach(product => {

        const listElement = document.createElement('li');
        const imageContainer = document.createElement('div');
        const detailsContainer = document.createElement('div');
        const optionContainer = document.createElement('div');

        listElement.classList.add('product-item');
        imageContainer.classList.add('product-image-container');
        detailsContainer.classList.add('product-details-container');
        optionContainer.classList.add('product-option');

        // Product Image
        const productImage = document.createElement('img');
        if(product.image === "no_image_123456789") { productImage.src = "assets/default-placeholder-product.png";}
        else {productImage.src = "product_servlet?action=image&name=" + product.image;}
        productImage.alt = "Product Image";
        productImage.draggable = false;
        productImage.classList.add('product-image');

        // Product Name
        const productName = document.createElement('h3');
        productName.classList.add('product-name');
        productName.textContent = product.name;

        // Product Description
        const productDescription = document.createElement('p');
        productDescription.style.textAlign = 'justify';
        productDescription.textContent = product.description;

        // Product Price
        const productPrice = document.createElement('p');
        productPrice.textContent = `Price: $${product.price}`;

        // Product Stock
        const productStock = document.createElement('p');
        productStock.textContent = `Stock: ${product.stock}`;

        // Product Category
        const productCategory = document.createElement('input');
        productCategory.type = 'hidden';
        productCategory.classList.add('product-category');
        productCategory.value = product.category_id;

        // Edit Button
        const editButton = document.createElement('button');
        editButton.classList.add('edit');
        editButton.textContent = 'Edit';
        editButton.setAttribute('data-id', product.id);
        editButton.setAttribute('data-name', product.name);
        editButton.setAttribute('data-price', product.price);
        editButton.setAttribute('data-stock', product.stock);
        editButton.setAttribute('data-description', product.description);
        editButton.setAttribute('data-category', product.category_id);
        editButton.onclick = function() {
            editId.value = product.id;
            editName.value = product.name;
            editPrice.value = product.price;
            editStock.value = product.stock;
            editDescription.value = product.description;
            editCategory.value = product.category_id;

            editModal.style.display = 'block';
        }


        // Form for deleting a product
        const deleteForm = document.createElement('form');
        deleteForm.action = 'product_servlet';
        deleteForm.method = 'post';
        deleteForm.classList.add('delete-form');

        const deleteInput = document.createElement('input');
        deleteInput.type = 'hidden';
        deleteInput.name = 'action';
        deleteInput.value = 'delete';

        const deleteId = document.createElement('input');
        deleteId.type = 'hidden';
        deleteId.name = 'id';
        deleteId.value = product.id;

        const deleteButton = document.createElement('button');
        deleteButton.type = 'submit';
        deleteButton.textContent = 'Delete';
        deleteButton.classList.add('delete');
        deleteButton.onclick = function() {
            return confirm('Are you sure you want to delete this product?');
        }

        deleteForm.appendChild(deleteInput);
        deleteForm.appendChild(deleteId);
        deleteForm.appendChild(deleteButton);

        imageContainer.appendChild(productImage);
        detailsContainer.appendChild(productName);
        detailsContainer.appendChild(productDescription);
        detailsContainer.appendChild(productPrice);
        detailsContainer.appendChild(productStock);
        detailsContainer.appendChild(productCategory);

        optionContainer.appendChild(editButton);
        optionContainer.appendChild(deleteForm);

        listElement.appendChild(imageContainer);
        listElement.appendChild(detailsContainer);
        listElement.appendChild(optionContainer);
        productsListContainer.appendChild(listElement);

    });
}