document.getElementById('forgot-dto-form').addEventListener('submit', () => {
    const email = document.getElementById('dto-email').value;
    localStorage.setItem('tempEmail', email);
})