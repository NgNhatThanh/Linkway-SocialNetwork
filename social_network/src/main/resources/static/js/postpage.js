const commentContent = document.getElementById('comment-textarea');
const submitButton = document.getElementById('submit-button');

commentContent.addEventListener('input', function() {
    if (commentContent.value.trim() === '') {
        submitButton.disabled = true; // Disable if empty
    } else {
        submitButton.disabled = false; // Enable if not empty
    }
});

function loadChildComments(parentId){

    const button = document.getElementById(`get-replies-${parentId}`);
    button.onclick = () => hideChildComments(parentId);
    button.textContent = 'Ẩn phản hồi';

    fetch(`/comment/${parentId}/child`)
        .then(response => response.json())
        .then(childComments => {
            console.log(childComments);
            const childCommentsContainer = document.getElementById(`child-comments-${parentId}`);
            childCommentsContainer.innerHTML = '';

            const csrfToken = document.getElementById('csrf-token');
            console.log(csrfToken);

            childComments.forEach(comment => {
                const commentElement = document.createElement('div');
                commentElement.className = 'child-comment';

                comment.createdAt = formatDate(new Date(comment.createdAt));

                const csrfToken = document.getElementById('csrf-token');

                console.log(csrfToken);

                var innerAdd = `<div id="comment-container">
                                            <div class="comment-meta">
                                                <div class="comment-author-info">
                                                    <a href="${'/profile/' + comment.author.username}">
                                                        <img src="${comment.author.avatarImagePath}" class="author-mini-avatar">
                                                    </a>
                                                    <div>
                                                        <p class="date">${comment.createdAt}</p>
                                                        <a href="${'/profile/' + comment.author.username}">${comment.author.username}</a>
                                                    </div>
                                                </div>
    
                                                <div class="comment-content">
                                                    <p>${comment.content}</p>
                                                </div>`;
                if(comment.hasChild){
                    innerAdd += `<button type="button" 
                                onclick=loadChildComments(${comment.id})
                                id=get-replies-${comment.id}> Xem Phản hồi </button>`;
                }
                innerAdd += `<div id="${'child-comments-'+comment.id}" class="child-comments"></div>
                        </div>`;

                innerAdd += `<div class="comment-vote">
                                <div class="vote-interact">
                                    <form action=${comment.upvoted ? `/comment/${comment.id}/unvote` : `/comment/${comment.id}/upvote`}
                                          method="post">
                                          ${csrfToken.outerHTML}
                                        <button type="submit">
                                            <i class="${comment.upvoted ? "bx bxs-upvote" : "bx bx-upvote"}"></i>
                                        </button>
                                    </form>
                                    <p class="vote-count">${comment.upvotes}</p>
                                </div>

                                <div class="vote-interact">
                                    <form action=${comment.downvoted ? `/comment/${comment.id}/unvote` : `/comment/${comment.id}/downvote`} 
                                        method="post">
                                        ${csrfToken.outerHTML}
                                        <button type="submit">
                                            <i class="${comment.downvoted ? "bx bxs-downvote" : "bx bx-downvote"}"></i>
                                        </button>
                                    </form>
                                    <p class="vote-count">${comment.downvotes}</p>
                                </div>
                            </div>
                        </div>`;

                commentElement.innerHTML = innerAdd;
                childCommentsContainer.appendChild(commentElement);
            });
        })
        .catch(error => console.error('Error loading child comments:', error));
}

function hideChildComments(parentId){

    const button = document.getElementById(`get-replies-${parentId}`);
    button.onclick = () => loadChildComments(parentId);
    button.textContent = 'Xem phản hồi';

    const childCommentsContainer = document.getElementById(`child-comments-${parentId}`);

    childCommentsContainer.innerHTML = '';
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours() % 12 || 12).padStart(2, '0'); // For 12-hour format
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}/${month}/${day} ${hours}:${minutes}`;
}