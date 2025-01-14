
document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('commentCtnr');
    const routeId = document.getElementById("route_id").value;

    const commentForm = document.getElementById('commentForm');
    commentForm.addEventListener("submit", handleCommentSubmit);

    const messageElement = document.getElementById("message");

    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

    // Fetch all comments
    const allComments = [];

    fetch(`http://localhost:8080/api/${routeId}/comments`)
        .then(response => response.json())
        .then(data => {
            for (let comment of data) {
                allComments.push(comment);
            }

            console.log(allComments);
            const nestedComments = nestComments(allComments);
            displayComments(nestedComments);
        });

    const displayComments = (comments) => {
        container.innerHTML = comments.map(
            (c) => asComment(c)
        ).join('');
    };

    function asComment(c) {
        return `<li id="comment-${c.commentId}">
            <h4 class="author">
                ${c.author}
                <span class="timestamp">(${c.created})</span>
            </h4>
            <p>${c.textContent}</p>
            <button class="reply-btn" data-comment-id="${c.commentId}">Reply</button>
            <ul class="replies">
                ${c.replies.map(asComment).join('')}
            </ul>
        </li>`;
    }

    // Handle Reply Button Click
    container.addEventListener("click", function (event) {
        if (event.target.classList.contains("reply-btn")) {
            const parentCommentId = event.target.dataset.commentId;
            showReplyForm(parentCommentId);
        }
    });

    function showReplyForm(parentCommentId) {
        const replyForm = document.createElement("form");
        replyForm.innerHTML = `
            <textarea name="textContent" required></textarea>
            <input type="hidden" name="parentCommentId" value="${parentCommentId}">
            <button type="submit">Submit Reply</button>
        `;
        replyForm.addEventListener("submit", handleCommentSubmit);
        document.getElementById(`comment-${parentCommentId}`).appendChild(replyForm);
    }

    // Post a comment or reply
    async function handleCommentSubmit(event) {
        event.preventDefault();

        const form = event.currentTarget;
        const url =  `http://localhost:8080/api/${routeId}/add-comment`;
        const formData = new FormData(form);

        try {
            const responseData = await postFormDataAsJson({ url, formData });

            const parentCommentId = formData.get("parentCommentId");
            if (parentCommentId) {
                const parentComment = document.getElementById(`comment-${parentCommentId}`).querySelector(".replies");
                parentComment.insertAdjacentHTML("beforeend", asComment(responseData));
            } else {
                container.insertAdjacentHTML("afterbegin", asComment(responseData));
            }

            if (messageElement && messageElement.classList.contains("is-invalid")) {
                messageElement.classList.remove("is-invalid");
            }

            form.remove(); // Remove the reply form after submission
        } catch (error) {
            let errorObj = JSON.parse(error.message);

            if (errorObj.fieldWithErrors) {
                errorObj.fieldWithErrors.forEach(
                    e => {
                        let elementWithError = document.getElementById(e);
                        if (elementWithError) {
                            elementWithError.classList.add("is-invalid");
                        }
                    });
            }
        }
        console.log('Comment submitted!');
    }

    async function postFormDataAsJson({ url, formData }) {
        const plainFormData = Object.fromEntries(formData.entries());
        const formDataAsJSONString = JSON.stringify(plainFormData);

        const fetchOptions = {
            method: "POST",
            headers: {
                [csrfHeaderName]: csrfHeaderValue,
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: formDataAsJSONString
        };

        const response = await fetch(url, fetchOptions);

        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(errorMessage);
        } else {
            console.log("not okay")
        }
        return response.json();
    }

    function nestComments(comments) {
        const commentMap = {};
        comments.forEach(comment => {
            comment.replies = [];
            commentMap[comment.commentId] = comment;
        });

        const nestedComments = [];
        comments.forEach(comment => {
            if (comment.parentCommentId) {
                commentMap[comment.parentCommentId].replies.push(comment);
            } else {
                nestedComments.push(comment);
            }
        });

        return nestedComments;
    }
});
