let unreadCount = 0;

function preparePage() {
    // Add event listeners to the chat button
    const chatButton = document.getElementById('chat-button');
    if(chatButton) chatButton.addEventListener('click', showOrHideChat);

    // Add event listener to the chat close button
    const chatCloseButton = document.getElementById('chat-close-button');
    if(chatCloseButton) chatCloseButton.addEventListener('click', showOrHideChat);

    // Hide the chat window
    const chat = document.getElementById('chat');
    if(chat) chat.style.display = 'none';
}

function showOrHideChat() {
    const chat = document.getElementById('chat');
    const chatButton = document.getElementById('chat-button');
    const chatMessages = document.getElementById('chat-messages');

    if (chat.style.display === 'none') {
        chat.style.display = 'block';
        chatButton.style.display = 'none';
        chatMessages.scrollTop = chatMessages.scrollHeight; // Scroll to the bottom
    } else {
        chat.style.display = 'none';
        chatButton.style.display = 'block';
    }
}

function incrementUnreadCount() {
    unreadCount++;
    document.getElementById('unread-count').textContent = unreadCount;
}

function resetUnreadCount() {
    unreadCount = 0;
    document.getElementById('unread-count').textContent = '';
}

function filterByCategoryHandler(category_pattern) {
    clearSelectedCategory();
    document.getElementById(category_pattern).classList.add('selected-category');

    const category_id = category_pattern.split('_')[1];
    const product_items = document.querySelectorAll('.product-item');

    product_items.forEach(function(item) {
        const category = item.querySelector('.cat-id-hidden').value;

        if(category_id === '0') {
            item.style.display = 'block';
            return;
        }

        if(category === category_id) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });

}

function clearSelectedCategory() {
    const selected_category = document.querySelector('.selected-category');
    if(selected_category) selected_category.classList.remove('selected-category');
}