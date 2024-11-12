const commentContent = document.getElementsByClassName('comment-textarea');
const submitButton = document.getElementsByClassName('submit-button');

commentContent[0].addEventListener('input', function() {
    submitButton[0].disabled = commentContent[0].value.trim() === '';
});

const csrfToken = document.getElementById("csrf-token").value;

function uploadImage(input){

    const commentEditor = input.closest(".comment-editor");
    const commentContent = commentEditor.querySelector(".comment-textarea");

    var file = input.files[0];
    if(!file || !file.type.startsWith('image/')) return;

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
            commentContent.value += `\n![](${response.url})`;
        })
        .catch(error => console.log("Err: " + error))
}

function loadChildComments(parentId){

    const button = document.getElementById(`get-replies-${parentId}`);
    button.onclick = () => hideChildComments(parentId);
    button.textContent = 'Ẩn phản hồi';

    fetch(`/comment/${parentId}/child`)
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
                                                        <a href="${'/profile/' + comment.author.username}">
                                                            <img src="${comment.author.avatarImagePath}" class="author-mini-avatar">
                                                        </a>
                                                        <div>
                                                            <p class="date">${comment.createdAt}</p>
                                                            <a href="${'/profile/' + comment.author.username}">${comment.author.displayName}</a>
                                                        </div>
                                                    </div>
        
                                                    <div class="comment-content">
                                                        ${comment.htmlContent}
                                                    </div>
                                                </div>

                                                <div class="comment-vote">
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
                                                
                                                <div class="comment-option-buttons">
                                            `;
                if(comment.hasChild){
                    innerAdd += `<button type="button" 
                                onclick=loadChildComments(${comment.id})
                                id=get-replies-${comment.id}> Xem Phản hồi </button>`;
                }

                innerAdd += `<button onclick='showReplyForm(${comment.id})'>
                                    Phản hồi
                             </button>`;

                if(comment.author.username === currentUsername){
                    innerAdd += `<button onclick=showCommentEditForm(${comment.id})>
                                    Chỉnh sửa
                                 </button>`;
                }

                innerAdd += `</div>
                        </div>`;

                innerAdd += `
                            <div id="${'reply-form-container-' + comment.id}" class="reply-form"></div>
                            <div id="${'child-comments-'+comment.id}" class="child-comments"></div>
                        </div>`;

                innerAdd += `
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

function showReplyForm(parentId){
    const replyContainer = document.getElementById(`reply-form-container-${parentId}`);
    const commentForm = (document.getElementsByClassName('comment-form-all'))[0].cloneNode(true);
    const parentIdValue = commentForm.querySelector(".parentIdValue");
    const textArea = commentForm.querySelector("textarea");
    textArea.value = '';
    textArea.placeholder = 'Viết phản hồi...'
    parentIdValue.value = parentId;

    const submitButton = commentForm.querySelector('.submit-button');
    submitButton.disabled = true;
    textArea.addEventListener('input', function() {
        submitButton.disabled = textArea.value.trim() === '';
    });

    previewContainer = commentForm.querySelector('.preview-container');
    previewContainer.innerHTML = '';

    replyContainer.innerHTML = '';
    replyContainer.appendChild(commentForm);
    replyContainer.appendChild()
}

function showPreview(button, content){
    if(content.trim() === '') return;
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

function showCommentEditForm(commentId){
    const commentForm = (document.getElementsByClassName('comment-form-all'))[0].cloneNode(true);
    const commentContainer = document.getElementById(`comment-container-${commentId}`);
    const commentMeta = commentContainer.querySelector('.comment-meta');

    var oldComment;

    fetch(`/comment/${commentId}`, {
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
            exitButton.textContent = 'Hủy';
            exitButton.onclick = function (){
                const container = document.getElementById(`comment-container-${commentId}`);
                container.removeChild(commentForm);
                container.appendChild(commentMeta);
            }
            commentForm.appendChild(exitButton) ;

            const submitButton = commentForm.querySelector('.submit-button');
            submitButton.disabled = true;
            textArea.addEventListener('input', function() {
                submitButton.disabled = textArea.value.trim() === '';
            });

            commentContainer.removeChild(commentMeta);
            commentContainer.appendChild(commentForm);
        })
        .catch(error => console.log("Error: " + error));
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours() % 12 || 12).padStart(2, '0'); // For 12-hour format
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}/${month}/${day} ${hours}:${minutes}`;
}