let currentPage = 1;
let totalPages = 1;
let selectedCategory = 0;

function preparePage() {

    if(sessionStorage.getItem('currentPage') != null) {
        currentPage = sessionStorage.getItem('currentPage');
    }
    else {
        currentPage = 1;
        sessionStorage.setItem('currentPage', currentPage);
    }

    if(sessionStorage.getItem('selectedCategory') != null) {
        selectedCategory = sessionStorage.getItem('selectedCategory');
    }
    else {
        selectedCategory = 0;
        sessionStorage.setItem('selectedCategory', selectedCategory);
    }

    getPageCount(selectedCategory);
    loadProducts(currentPage, selectedCategory);
}

function filterByCategoryHandler(category_pattern) {
    clearSelectedCategory();
    document.getElementById(category_pattern).classList.add('selected-category');

    selectedCategory = category_pattern.split('_')[1];
    sessionStorage.setItem('selectedCategory', selectedCategory);

    currentPage = 1;
    sessionStorage.setItem('currentPage', currentPage);

    getPageCount(selectedCategory);
    displayPageNumbers();
    loadProducts(currentPage, selectedCategory);

}

function clearSelectedCategory() {
    const selected_category = document.querySelector('.selected-category');
    if(selected_category) selected_category.classList.remove('selected-category');
}

function firstPage() {
    currentPage = 1;
    sessionStorage.setItem('currentPage', currentPage);

    displayPageNumbers();
    loadProducts(currentPage, selectedCategory);
}

function lastPage() {
    currentPage = totalPages;
    sessionStorage.setItem('currentPage', currentPage);

    displayPageNumbers();
    loadProducts(currentPage, selectedCategory);
}

function nextPage() {
    if(currentPage < totalPages) {
        currentPage++;
        sessionStorage.setItem('currentPage', currentPage);

        displayPageNumbers();
        loadProducts(currentPage, selectedCategory);
    }
}

function previousPage() {
    if(currentPage > 1) {
        currentPage--;
        sessionStorage.setItem('currentPage', currentPage);

        displayPageNumbers();
        loadProducts(currentPage, selectedCategory);
    }
}

function getPageCount(categoryId) {
    fetch(`product_servlet?action=page_count&category_id=${categoryId}`)
        .then(response => response.json())
        .then(data => {
            totalPages = data.page_count;
            displayPageNumbers();
        });
}

function loadProducts(pageNumber, categoryId) {
    fetch(`product_servlet?action=products&page=${pageNumber}&category_id=${categoryId}`)
        .then(response => response.json())
        .then(products => {
            displayProducts(products);
        });
}

function displayProducts(products) {
    const productsContainer = document.getElementById('product-grid');
    productsContainer.innerHTML = '';

    products.forEach(product => {
        const productElement = document.createElement('div');
        const imageContainer = document.createElement('div');
        const detailsContainer = document.createElement('div');

        productElement.classList.add('product-item');
        imageContainer.classList.add('product-image-container');
        detailsContainer.classList.add('product-details');

        const productImage = document.createElement('img');
        if(product.image === "no_image_123456789") { productImage.src = "assets/default-placeholder-product.png";}
        else {productImage.src = "product_servlet?action=image&name=" + product.image;}
        productImage.alt = "Product Image";
        productImage.draggable = false;
        productImage.classList.add('product-image');

        const productName = document.createElement('h2');
        productName.textContent = product.name;
        productName.textContent = product.name;

        const productPrice = document.createElement('p');
        productPrice.classList.add('product-price');
        productPrice.textContent = "$" + product.price;

        const productLink = document.createElement('a');
        productLink.classList.add('product-link');
        productLink.href = `product_details.jsp?product_id=${product.id}`;
        productLink.textContent = "View Details";

        imageContainer.appendChild(productImage);

        detailsContainer.appendChild(productName);
        detailsContainer.appendChild(productPrice);
        detailsContainer.appendChild(productLink);

        productElement.appendChild(imageContainer);
        productElement.appendChild(detailsContainer);

        productsContainer.appendChild(productElement);
    });
}

function displayPageNumbers() {
    const pageNumbersElement = document.getElementById('pageNumbers');
    pageNumbersElement.innerHTML = '';

    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, startPage + 4);

    if (endPage - startPage < 4) {
        startPage = Math.max(1, endPage - 4);
    }

    for (let i = startPage; i <= endPage; i++) {
        const pageElement = document.createElement('span');
        pageElement.textContent = "" + i;
        pageElement.classList.add('pageNumber');
        if (i == currentPage) {
            pageElement.classList.add('current');
        }
        pageElement.addEventListener('click', function() {
            currentPage = i;
            sessionStorage.setItem('currentPage', currentPage);
            displayPageNumbers();
            loadProducts(currentPage, selectedCategory);
        });
        pageNumbersElement.appendChild(pageElement);
    }
}