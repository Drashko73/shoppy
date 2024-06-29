document.addEventListener('DOMContentLoaded', function() {
    const faqItems = document.querySelectorAll('.faq-item');

    faqItems.forEach(item => {
        const question = item.querySelector('.faq-question');
        question.addEventListener('click', () => {
            const openItem = document.querySelector('.faq-item.open');
            if (openItem && openItem !== item) {
                openItem.classList.remove('open');
            }
            item.classList.toggle('open');
        });
    });
});