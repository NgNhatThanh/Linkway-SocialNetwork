const commentContent = document.getElementsByClassName('comment-textarea');
const submitButton = document.getElementsByClassName('submit-button');

commentContent[0].addEventListener('input', function () {
    submitButton[0].disabled = commentContent[0].value.trim() === '';
});

function uploadImage(input) {

    const commentEditor = input.closest(".comment-editor");
    const commentContent = commentEditor.querySelector(".comment-textarea");

    var file = input.files[0];
    if (!file || !file.type.startsWith('image/')) return;

    const formData = new FormData();
    formData.append('image', file);

    fetch('/upload', {
        method: 'POST',
        body: formData,
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(response => response.json())
        .then(response => {
            commentContent.value += `\n\n![](${response.imageUrl})`;
        })
        .catch(error => console.log("Err: " + error))
}

function loadChildComments(parentId) {

    const button = document.getElementById(`get-replies-${parentId}`);
    button.onclick = () => hideChildComments(parentId);
    button.textContent = 'Ẩn phản hồi';

    fetch(`/api/comment/${parentId}/child`)
        .then(response => response.json())
        .then(childComments => {
            const childCommentsContainer = document.getElementById(`child-comments-${parentId}`);
            childCommentsContainer.innerHTML = '';

            const csrfToken = document.getElementById('csrf-token');
            const currentUsername = document.getElementById('current-username').value;

            childComments.reverse();

            childComments.forEach(comment => {
                const commentElement = document.createElement('div');
                commentElement.className = 'child-comment';

                comment.createdAt = formatDate(new Date(comment.createdAt));

                var innerAdd = `<div id="comment-container-${comment.id}">
                                            <div class="comment-meta">
                                                <div class="comment-info">
                                                    <div class="comment-author-info">
                                                        <div style="display: flex; align-item: center;">
                                                            <a class="comment-author-img"href="${'/profile/' + comment.author.username}">
                                                                <img src="${comment.author.avatarImagePath}" class="author-mini-avatar">
                                                            </a>
                                                            <a href="${'/profile/' + comment.author.username}">${comment.author.displayName}</a>
                                                        </div>
                                                        <div>
                                                            <div class="comment-date" style="display: flex">
                                                                <p class="date">${comment.createdAt}</p>
                                                            </div>
                                                        </div>
                                                    </div>
        
                                                    <div class="comment-content">
                                                        ${comment.htmlContent}
                                                    </div>
                                                </div>

                                                <div class="comment-vote">
                                                    <div class="vote-buttons">
                                                        <form action=${comment.upvoted ? `/comment/${comment.id}/unvote` : `/comment/${comment.id}/upvote`}
                                                              method="post">
                                                              ${csrfToken.outerHTML}
                                                            <button type="submit" data-rel="back" class="vote-btn up active">
                                                                <span class="upvote-count">${comment.upvotes}</span>
                                                            </button>
                                                        </form>
                                                        <form action=${comment.downvoted ? `/comment/${comment.id}/unvote` : `/comment/${comment.id}/downvote`}
                                                            method="post">
                                                            ${csrfToken.outerHTML}
                                                            <button type="submit" data-rel="back" class="vote-btn down">
                                                                <span class="downvote-count">${comment.downvotes}</span>
                                                            </button>
                                                        </form>
                                                    </div>
                                            `;
                if (comment.hasChild) {
                    innerAdd += `<button type="button" class="action-btn"
                               onclick=loadChildComments(${comment.id})
                               id=get-replies-${comment.id}> Xem Phản hồi </button>`;
                }

                innerAdd += `<button class="action-btn" onclick='showReplyForm(${comment.id})'>
                                   Phản hồi
                            </button>`;

                if (comment.author.username === currentUsername) {
                    innerAdd += `<button class="action-btn" onclick=showCommentEditForm(${comment.id})>
                                   Chỉnh sửa
                                </button>`;
                }

                innerAdd += `</div>
                        </div>`;

                innerAdd += `
                            <div id="${'reply-form-container-' + comment.id}" class="reply-form"></div>
                            <div id="${'child-comments-' + comment.id}" class="child-comments"></div>
                        </div>`;

                commentElement.innerHTML = innerAdd;
                childCommentsContainer.appendChild(commentElement);
            });
        })
        .catch(error => console.error('Error loading child comments:', error));
}

function hideChildComments(parentId) {
    const button = document.getElementById(`get-replies-${parentId}`);
    button.onclick = () => loadChildComments(parentId);
    button.textContent = 'Xem phản hồi';
    const childCommentsContainer = document.getElementById(`child-comments-${parentId}`);
    childCommentsContainer.innerHTML = '';
}

function showReplyForm(parentId) {
    const replyContainer = document.getElementById(`reply-form-container-${parentId}`);
    const commentForm = (document.getElementsByClassName('comment-form-all'))[0].cloneNode(true);
    const parentIdValue = commentForm.querySelector(".parentIdValue");
    const textArea = commentForm.querySelector("textarea");
    textArea.value = '';
    textArea.placeholder = 'Viết phản hồi...'
    parentIdValue.value = parentId;

    const submitButton = commentForm.querySelector('.submit-button');
    submitButton.disabled = true;
    textArea.addEventListener('input', function () {
        submitButton.disabled = textArea.value.trim() === '';
    });

    previewContainer = commentForm.querySelector('.preview-container');
    previewContainer.innerHTML = '';

    replyContainer.innerHTML = '';
    replyContainer.appendChild(commentForm);
}

function showPreview(button, content) {
    if (content.trim() === '') return;
    fetch('/transfer', {
        method: 'POST',
        body: content,
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(response => response.text())
        .then(html => {
            const container = button.closest('.comment-form-all').querySelector('.preview-container');
            container.innerHTML = html;
        })
}

function showCommentEditForm(commentId) {
    const commentForm = (document.getElementsByClassName('comment-form-all'))[0].cloneNode(true);
    const commentContainer = document.getElementById(`comment-container-${commentId}`);
    const commentMeta = commentContainer.querySelector('.comment-meta');

    var oldComment;

    fetch(`/api/comment/${commentId}`, {
        method: "GET",
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(response => response.json())
        .then(comment => {
            oldComment = comment;
            const textArea = commentForm.querySelector('textarea');

            textArea.value = oldComment.content;
            const parentIdValue = commentForm.querySelector(".parentIdValue");
            parentIdValue.value = comment.parentComment === null ? -1 : comment.parentComment.id;

            const commentIdValue = document.createElement('input');
            commentIdValue.name = 'id';
            commentIdValue.type = 'hidden';
            commentIdValue.value = commentId;
            commentIdValue.className = 'commentIdValue';
            commentForm.querySelector('.comment-editor').appendChild(commentIdValue);

            const exitButton = document.createElement('button');
            exitButton.classList.add('action-btn');
            exitButton.textContent = 'Hủy';
            exitButton.onclick = function () {
                const container = document.getElementById(`comment-container-${commentId}`);
                commentForm.replaceWith(commentMeta);
            }
            commentForm.appendChild(exitButton);

            const submitButton = commentForm.querySelector('.submit-button');
            submitButton.disabled = true;
            textArea.addEventListener('input', function () {
                submitButton.disabled = textArea.value.trim() === '';
            });

            commentMeta.replaceWith(commentForm);

            // commentContainer.removeChild(commentMeta);
            // commentContainer.appendChild(commentForm);
        })
        .catch(error => console.log("Error: " + error));
}